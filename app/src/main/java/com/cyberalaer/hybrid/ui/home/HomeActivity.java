package com.cyberalaer.hybrid.ui.home;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Process;
import android.provider.Settings;
import android.view.View;

import com.alaer.lib.api.ApiUtil;
import com.alaer.lib.api.AppConfig;
import com.alaer.lib.api.Callback;
import com.alaer.lib.api.bean.Balance;
import com.alaer.lib.api.bean.Notice;
import com.alaer.lib.api.bean.TeamDetail;
import com.alaer.lib.api.bean.TeamInfo;
import com.alaer.lib.api.bean.UserData;
import com.alaer.lib.data.UserDataUtil;
import com.alaer.lib.event.Event;
import com.alaer.lib.event.EventUtil;
import com.cyberalaer.hybrid.R;
import com.cyberalaer.hybrid.base.BaseViewBindActivity;
import com.cyberalaer.hybrid.databinding.ActivityHomeBinding;
import com.cyberalaer.hybrid.ui.auth.AuthSuccessActivity;
import com.cyberalaer.hybrid.ui.discover.DiscoverActivity;
import com.cyberalaer.hybrid.ui.education.EducationHallActivity;
import com.cyberalaer.hybrid.ui.government.GovernmentHallActivity;
import com.cyberalaer.hybrid.ui.government.RealNameAuthActivity;
import com.cyberalaer.hybrid.ui.notice.NoticeDetailActivity;
import com.cyberalaer.hybrid.ui.notice.NoticeListActivity;
import com.cyberalaer.hybrid.ui.produce.ProductionHallActivity;
import com.cyberalaer.hybrid.ui.setting.AppUpgradeManager;
import com.cyberalaer.hybrid.ui.task.TaskListFragment;
import com.cyberalaer.hybrid.ui.travel.TravelHallActivity;
import com.cyberalaer.hybrid.ui.user.LoginActivity;
import com.cyberalaer.hybrid.ui.user.UserMineActivity;
import com.cyberalaer.hybrid.util.CollectionUtils;
import com.cyberalaer.hybrid.util.NumberUtils;
import com.cyberalaer.hybrid.util.ViewUtil;
import com.cyberalaer.hybrid.view.mapview.MapContainer;
import com.cyberalaer.hybrid.view.mapview.Marker;
import com.meiyou.mvp.MvpBinder;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import cn.jzvd.JzvdStd;
import de.greenrobot.event.EventBus;
import de.greenrobot.event.Subscribe;
import de.greenrobot.event.ThreadMode;
import likly.dollar.$;

@MvpBinder(
        presenter = HomePresenterImpl.class
)
public class HomeActivity extends BaseViewBindActivity<ActivityHomeBinding> implements HomeView, MapContainer.OnMarkerClickListner {

    @Override
    protected int layoutId() {
        return R.layout.activity_home;
    }

    @Override
    public void showError(String content) {
    }

    @Override
    public void onViewCreated() {
        super.onViewCreated();
        EventBus.getDefault().register(this);
        initMapView();
        getSaveData();
        initData();
        queryNotices();
        requestPermission();
        // 版本更新
        new AppUpgradeManager(this).checkUpdate(false);
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        initData();
    }

    private void getSaveData() {
        UserData userData = UserDataUtil.instance().getSavedUserData();
        if (userData != null) {
            UserDataUtil.instance().getSavedTeamDetail();
        }
    }

    private void initMapView() {
        bindRoot.map.getMapView().setImageResource(R.drawable.bg_map);
        List<Marker> markers = new ArrayList<>();
        markers.add(new Marker(0.09F, 0.55F, R.string.produce_hall));
        markers.add(new Marker(0.27F, 0.46F, R.string.discover));
        markers.add(new Marker(0.4F, 0.27F, R.string.digital_cityer_auth));
        markers.add(new Marker(0.63F, 0.46F, R.string.government_hall));
        markers.add(new Marker(0.64F, 0.64F, R.string.travel_hall));
        markers.add(new Marker(0.57F, 0.88F, R.string.education_hall));
        markers.add(new Marker(0.82F, 0.35F, R.string.go_into_alaer));

        bindRoot.map.setMarkers(markers);
        bindRoot.map.setOnMarkerClickListner(this);
    }

    private Class<? extends Activity>[] mPageClasses = new Class[]{
            ProductionHallActivity.class, DiscoverActivity.class, RealNameAuthActivity.class,
            GovernmentHallActivity.class, TravelHallActivity.class, EducationHallActivity.class};

    @Override
    public void onClick(View view, int position) {
        if (position == 0 || position == 2 || position == 3) {
            if (!judgeLogined())
                return;
        }

        if (position == 6) {
            // 走进阿拉尔,播放视频
            JzvdStd.startFullscreenDirectly(this, JzvdStd.class, AppConfig.GO_INTO_ALAER_VIDEO, getString(R.string.go_into_alaer));
        } else if (position == 2) {
            if (UserDataUtil.instance().isAuthed())
                ViewUtil.gotoActivity(this, AuthSuccessActivity.class);
            else
                ViewUtil.gotoActivity(this, RealNameAuthActivity.class);
        } else {
            ViewUtil.gotoActivity(this, mPageClasses[position]);
        }
    }

    @Override
    public void click(View view) {
        switch (view.getId()) {
            case R.id.layoutUser:
                ViewUtil.gotoActivity(this, UserMineActivity.class);
                break;
            case R.id.layoutNotLogin:
                ViewUtil.gotoActivity(getContext(), LoginActivity.class);
                break;
            case R.id.tabMine:
                ViewUtil.gotoActivity(this, UserMineActivity.class);
                break;
            case R.id.tabTask:
                if (judgeLogined())
                    TaskListFragment.newInstance().show(getSupportFragmentManager(), "taskList");
                break;
            case R.id.tabProduce:
                if (judgeLogined())
                    ViewUtil.gotoActivity(this, ProductionHallActivity.class);
                break;
            case R.id.tabDiscover:
                ViewUtil.gotoActivity(this, DiscoverActivity.class);
                break;
            case R.id.noticeList:
                ViewUtil.gotoActivity(this, NoticeListActivity.class);
                break;
            case R.id.notice:
                if (mNoticeNew != null) {
                    Bundle bundle = new Bundle();
                    bundle.putSerializable("notice", mNoticeNew);
                    ViewUtil.gotoActivity(this, NoticeDetailActivity.class, bundle);
                }
                break;
        }
    }

    Notice mNoticeNew;

    private void initData() {
        UserData userData = UserDataUtil.instance().getUserData();
        if (userData == null) {
            bindRoot.setHasLogin(false);
            return;
        }
        bindRoot.setHasLogin(true);

        TeamDetail teamDetail = UserDataUtil.instance().getTeamDetail();
        if (teamDetail != null) {
            bindRoot.setUser(teamDetail);
            ViewUtil.showImage(getApplicationContext(), bindRoot.icHead, teamDetail.avatar);
        }

        ApiUtil.apiService().getBalance(userData.uuid, String.valueOf(userData.uid), userData.token, AppConfig.DIAMOND_CURRENCY,
                new Callback<Balance>() {
                    @Override
                    public void onResponse(Balance balance) {
                        bindRoot.buildScore.setText(NumberUtils.instance().parseNumber(balance.money.amount));
                        UserDataUtil.instance().setBalanse(balance);
                    }
                });

        ApiUtil.apiService().info(userData.uuid, String.valueOf(userData.uid), userData.token, AppConfig.DIAMOND_CURRENCY,
                new Callback<TeamInfo>() {
                    @Override
                    public void onResponse(TeamInfo teamInfo) {
                        UserDataUtil.instance().setTeamInfo(teamInfo);
                    }
                });
    }

    private void queryNotices() {
        ApiUtil.apiService().noticeList(1, 5, 1100,
                new Callback<List<Notice>>() {
                    @Override
                    public void onResponse(List<Notice> notices) {
                        if (!CollectionUtils.isEmpty(notices)) {
                            mNoticeNew = notices.get(0);
                            bindRoot.notice.setText(mNoticeNew.title);
                        }
                    }
                });
    }

    @Override
    public void onBackPressed() {
        backToExit();
    }

    private long exitTime = 0;

    private void backToExit() {
        // 再次点击返回键退出程序
        if ((System.currentTimeMillis() - exitTime) > 2000) {
            $.toast().text(R.string.press_twice_exit).show();
            exitTime = System.currentTimeMillis();
        } else {
            Process.killProcess(Process.myPid());
        }
    }

    private void requestPermission() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, 1);
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    private void requestInstallApkPermission() {
        boolean haveInstallPermission = getPackageManager().canRequestPackageInstalls();
        if (!haveInstallPermission) {
            Uri packageURI = Uri.parse("package:" + getPackageName());
            Intent intent = new Intent(Settings.ACTION_MANAGE_UNKNOWN_APP_SOURCES, packageURI);
            startActivityForResult(intent, REQUEST_INSTALL);

        }
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        if (UserDataUtil.instance().isTokenInvalid()) {
            UserDataUtil.instance().setTokenInvalid(false);
            ViewUtil.gotoActivity(this, LoginActivity.class);
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Subscribe(threadMode = ThreadMode.MainThread)
    public void onEvent(Event event) {
        if (EventUtil.isInstallRequestPermission(event) && event.data == this) {
            requestInstallApkPermission();
        } else if (EventUtil.isTokenInvalid(event)) {
            UserDataUtil.instance().clearUserDatas();
            UserDataUtil.instance().setTokenInvalid(true);
            Intent intent = new Intent(this, HomeActivity.class);
            startActivity(intent);
        }
    }

    private final int REQUEST_INSTALL = 11;

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_INSTALL) {
            if (resultCode == RESULT_OK)
                EventUtil.sendInstallApk();
            else
                $.toast().text("授权失败!").show();
        }

    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }

}