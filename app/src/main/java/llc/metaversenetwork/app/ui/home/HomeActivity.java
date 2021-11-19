package llc.metaversenetwork.app.ui.home;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.os.Process;
import android.provider.Settings;
import android.text.TextUtils;
import android.view.Gravity;
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
import com.meiyou.mvp.MvpBinder;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import de.greenrobot.event.EventBus;
import de.greenrobot.event.Subscribe;
import de.greenrobot.event.ThreadMode;
import likly.dialogger.Dialogger;
import likly.dollar.$;
import llc.metaversenetwork.app.R;
import llc.metaversenetwork.app.base.BaseViewBindActivity;
import llc.metaversenetwork.app.databinding.ActivityHomeBinding;
import llc.metaversenetwork.app.ui.dialog.DialogWelcome;
import llc.metaversenetwork.app.ui.discover.DiscoverActivity;
import llc.metaversenetwork.app.ui.education.EducationHallActivity;
import llc.metaversenetwork.app.ui.government.GovernmentHallActivity;
import llc.metaversenetwork.app.ui.government.RealNameAuthActivity;
import llc.metaversenetwork.app.ui.notice.NoticeDetailActivity;
import llc.metaversenetwork.app.ui.notice.NoticeListActivity;
import llc.metaversenetwork.app.ui.produce.ProductionHallActivity;
import llc.metaversenetwork.app.ui.setting.AppUpgradeManager;
import llc.metaversenetwork.app.ui.task.TaskListFragment;
import llc.metaversenetwork.app.ui.travel.TravelHallActivity;
import llc.metaversenetwork.app.ui.user.LoginActivity;
import llc.metaversenetwork.app.ui.user.UserMineActivity;
import llc.metaversenetwork.app.ui.video.VideoActivity;
import llc.metaversenetwork.app.util.CollectionUtils;
import llc.metaversenetwork.app.util.LocaleUtil;
import llc.metaversenetwork.app.util.NumberUtils;
import llc.metaversenetwork.app.util.ViewUtil;
import llc.metaversenetwork.app.view.mapview.MapContainer;
import llc.metaversenetwork.app.view.mapview.MapView;
import llc.metaversenetwork.app.view.mapview.Marker;

@MvpBinder(
        presenter = HomePresenterImpl.class
)
public class HomeActivity extends BaseViewBindActivity<ActivityHomeBinding> implements HomeView, MapContainer.OnMarkerClickListner, MapView.OnAutoMoveListener {

    @Override
    protected int layoutId() {
        return R.layout.activity_home;
    }

    @Override
    public void showError(String content) {
    }

    List<Notice> mNotices;

    @Override
    protected void onRestart() {
        super.onRestart();
        initData();
    }

    public static boolean isFirstOpen = true;

    @Override
    public void onResume() {
        super.onResume();
        refreshProfile();
        if (isFirstOpen) {
            isFirstOpen = false;
            boolean firstUse = $.config().getBoolean("firstUse", true);
            if (firstUse) {
                $.config().putBoolean("firstUse", false);
                return;
            }
            new AppUpgradeManager(this).checkUpdate(false);
            LocaleUtil.restoreLanguage(HomeActivity.this);
        }
    }

    private void refreshProfile() {
        UserData userData = UserDataUtil.instance().getUserData();
        if (userData == null)
            return;
        ApiUtil.apiService().getTeamDetailInfo(userData.uuid, String.valueOf(userData.uid), userData.token, AppConfig.DIAMOND_CURRENCY,
                new Callback<TeamDetail>() {
                    @Override
                    public void onResponse(TeamDetail teamDetail) {
                        UserDataUtil.instance().saveTeamDetailInfo(teamDetail);
                    }
                });
    }

    private void getSaveData() {
        UserData userData = UserDataUtil.instance().getSavedUserData();
        if (userData != null) {
            UserDataUtil.instance().getSavedTeamDetail();
        }
    }

    private Class<? extends Activity>[] mPageClasses = new Class[]{
            ProductionHallActivity.class, null, DiscoverActivity.class, GovernmentHallActivity.class,
            RealNameAuthActivity.class, TravelHallActivity.class, EducationHallActivity.class};

    private void initMapView() {
        bindRoot.map.getMapView().setImageResource(R.drawable.bg_map);
        List<Marker> markers = new ArrayList<>();
        markers.add(new Marker(0.12F, 0.57F, R.string.saturn));
        markers.add(new Marker(0.27F, 0.70F, R.string.mercury));
        markers.add(new Marker(0.33F, 0.89F, R.string.earth));
        markers.add(new Marker(0.37F, 0.39F, R.string.uranus));
        markers.add(new Marker(0.83F, 0.89F, R.string.jupiter));
        markers.add(new Marker(0.87F, 0.51F, R.string.venus));
        markers.add(new Marker(0.75F, 0.21F, R.string.neptune));

        bindRoot.map.setMarkers(markers);
        bindRoot.map.setOnMarkerClickListner(this);
    }

    int noticeIndex = 0;

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
            case R.id.language:
                bindRoot.setShowLanguage(true);
                break;
            case R.id.languageZhHK:
                boolean isDefault1 = $.config().getBoolean("defaultLanguage", true);
                if (!isDefault1) {
                    $.config().putBoolean("defaultLanguage", true);
                    bindRoot.setDefaultLanguage(true);
                    LocaleUtil.changeLanguage(this, true, true);
                }
                bindRoot.setShowLanguage(false);
                break;
            case R.id.languageEnglish:
                boolean isDefault2 = $.config().getBoolean("defaultLanguage", true);
                if (isDefault2) {
                    $.config().putBoolean("defaultLanguage", false);
                    bindRoot.setDefaultLanguage(false);
                    LocaleUtil.changeLanguage(this, false, true);
                }
                bindRoot.setShowLanguage(false);
                break;
        }
    }

    Notice mNoticeNew;

    private void initData() {
        bindRoot.setDefaultLanguage($.config().getBoolean("defaultLanguage", true));

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

    private Handler mHandler = new Handler(Looper.getMainLooper()) {
        @Override
        public void handleMessage(@NonNull Message msg) {
            mNoticeNew = mNotices.get(noticeIndex);
            if (!TextUtils.isEmpty(mNoticeNew.title))
                bindRoot.notice.setText(mNoticeNew.title);

            if (noticeIndex == mNotices.size() - 1 || noticeIndex == 2)
                noticeIndex = 0;
            else
                noticeIndex++;
            mHandler.sendEmptyMessageDelayed(0, 5000);
        }
    };

    @Override
    public void onViewCreated() {
        super.onViewCreated();
        EventBus.getDefault().register(this);
        initMapView();
        getSaveData();
        initData();
        queryNotices();
        bindRoot.notice.requestFocus();

        bindRoot.map.getMapView().setAutoMoveListener(this);
    }

    @Override
    public void autoMoveComplete() {
        new AppUpgradeManager(this).checkUpdate(false);
        showWelcomeDialog();
        requestPermission();
    }

    private void showWelcomeDialog() {
        boolean showWelcome = $.config().getBoolean("showWelcome", true);
        if (showWelcome) {
            Dialogger.newDialog(getContext()).holder(new DialogWelcome())
                    .gravity(Gravity.CENTER)
                    .cancelable(false)
                    .show();
            $.config().putBoolean("showWelcome", false);
        }
    }

    @Override
    public void onClick(View view, int position) {
        if (position == 0 || position == 1 || position == 3 || position == 4 || position == 5) {
            if (!judgeLogined())
                return;
        }

        if (position == 1) {
            // 走进阿拉尔,播放视频
//            JzvdStd.startFullscreenDirectly(this, JzvdStd.class, AppConfig.GO_INTO_ALAER_VIDEO, getString(R.string.go_into_alaer));
            VideoActivity.startPlayFroResult(this, null, 1);
        } else if (position == 4) {
            ViewUtil.gotoAuthPage(this);
        } else {
            ViewUtil.gotoActivity(this, mPageClasses[position]);
        }
    }

    private void queryNotices() {
        ApiUtil.apiService().noticeList(1, 5, 1100,
                new Callback<List<Notice>>() {
                    @Override
                    public void onResponse(List<Notice> notices) {
                        if (!CollectionUtils.isEmpty(notices)) {
                            mNotices = notices;
                            mHandler.sendEmptyMessage(0);
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
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED
                || ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.ACCESS_FINE_LOCATION},
                    1);
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
        if (EventUtil.isInstallRequestPermission(event)) {
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