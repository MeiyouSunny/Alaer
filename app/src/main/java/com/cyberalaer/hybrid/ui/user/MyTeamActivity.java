package com.cyberalaer.hybrid.ui.user;

import android.os.Build;
import android.os.Bundle;
import android.view.View;

import com.alaer.lib.api.ApiUtil;
import com.alaer.lib.api.AppConfig;
import com.alaer.lib.api.Callback;
import com.alaer.lib.api.bean.SharedUserList;
import com.alaer.lib.api.bean.TeamDetail;
import com.alaer.lib.api.bean.TeamLevel;
import com.alaer.lib.api.bean.TeamProfile;
import com.alaer.lib.api.bean.UserData;
import com.alaer.lib.data.UserDataUtil;
import com.cyberalaer.hybrid.R;
import com.cyberalaer.hybrid.base.BaseTitleActivity;
import com.cyberalaer.hybrid.data.TeamLevelUtil;
import com.cyberalaer.hybrid.databinding.ActivityMyTeamBinding;
import com.cyberalaer.hybrid.util.CollectionUtils;
import com.cyberalaer.hybrid.util.ViewUtil;
import com.cyberalaer.hybrid.view.FullyLinearLayoutManager;
import com.cyberalaer.hybrid.view.TabTextView;

import java.util.List;

import androidx.annotation.RequiresApi;

/**
 * 我的伙伴
 */
public class MyTeamActivity extends BaseTitleActivity<ActivityMyTeamBinding> implements TabTextView.StateChangedListener {
    private static final String[] types = new String[]{"team_activeness", "num", "level", "uid"};

    private TabTextView[] mTabs;

    UserData userData;
    TeamDetail mInviterInfo;

    @Override
    protected int titleResId() {
        return R.string.my_team;
    }

    @Override
    protected int layoutId() {
        return R.layout.activity_my_team;
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    public void onViewCreated() {
        super.onViewCreated();
        TabShareUserListAdapter adapter = new TabShareUserListAdapter(this, getSupportFragmentManager(),
                getResources().getStringArray(R.array.my_team_tabs));
//        bindRoot.viewPager.setAdapter(adapter);
//        bindRoot.tabs.setupWithViewPager(bindRoot.viewPager);

        initViews();

        userData = UserDataUtil.instance().getUserData();
        initData();

    }

    private void initViews() {
        mTabs = new TabTextView[]{bindRoot.tab1, bindRoot.tab2, bindRoot.tab3, bindRoot.tab4};
        for (int i = 0; i < mTabs.length; i++) {
            TabTextView tab = mTabs[i];
            tab.setType(types[i]);
            tab.setStateListener(this);
        }

        bindRoot.tab1.setSelected();
    }

    private void requestDataList(String type, String order) {
        UserData userData = UserDataUtil.instance().getUserData();
        if (userData == null)
            return;
        ApiUtil.apiService().teamSharedUserList(userData.uuid, String.valueOf(userData.uid), userData.token, AppConfig.DIAMOND_CURRENCY,
                1, 100, type, order,
                new Callback<SharedUserList>() {
                    @Override
                    public void onResponse(SharedUserList list) {
                        showSharedUserList(list);
                    }
                });
    }

    private void showSharedUserList(SharedUserList list) {
        if (list == null || CollectionUtils.isEmpty(list.list)) {
            bindRoot.list.setVisibility(View.GONE);
            bindRoot.noData.setVisibility(View.VISIBLE);
            return;
        } else {
            bindRoot.noData.setVisibility(View.GONE);
            bindRoot.list.setVisibility(View.VISIBLE);
        }

        SharedUserAdapter adapter = new SharedUserAdapter(this, list.list);
        FullyLinearLayoutManager layoutManager = new FullyLinearLayoutManager(getContext(), list.list.size());

        bindRoot.list.setLayoutManager(layoutManager);
        bindRoot.list.setAdapter(adapter);

    }

    @Override
    public void click(View view) {
        switch (view.getId()) {
            case R.id.levelRule:
                ViewUtil.gotoActivity(this, LevelRuleActivity.class);
                break;
            case R.id.myInviter:
                if (mInviterInfo == null)
                    queryMyInviter();
                else
                    goToInviterInfo();
                break;
        }
    }

    private void queryMyInviter() {
        ApiUtil.apiService().getFollowInfo(userData.uuid, String.valueOf(userData.uid), userData.token, AppConfig.DIAMOND_CURRENCY,
                new Callback<TeamDetail>() {
                    @Override
                    public void onResponse(TeamDetail response) {
                        mInviterInfo = response;
                        goToInviterInfo();
                    }
                });
    }

    private void goToInviterInfo() {
        if (mInviterInfo == null)
            return;
        Bundle data = new Bundle();
        data.putSerializable("inviter", mInviterInfo);
        ViewUtil.gotoActivity(getContext(), InviterInfoActivity.class, data);
    }

    private void initData() {
        userData = UserDataUtil.instance().getUserData();
        if (userData == null)
            return;

        ApiUtil.apiService().teamProfile(userData.uuid, String.valueOf(userData.uid), userData.token,
                AppConfig.DIAMOND_CURRENCY,
                new Callback<TeamProfile>() {
                    @Override
                    public void onResponse(TeamProfile teamProfile) {
                        if (teamProfile == null)
                            return;

                        bindRoot.setTeamProfile(teamProfile);
                        bindRoot.setLevelUtil(new TeamLevelUtil(getResources()));
                        if (teamProfile.level == 0) {
                            bindRoot.setTeamLevel(null);
                        } else {
                            queryTeamLevels(teamProfile.level);
                        }
                    }

                });
    }

    private void queryTeamLevels(int level) {
        if (userData == null)
            return;
        ApiUtil.apiService().teamStarLevel(userData.uuid, String.valueOf(userData.uid), userData.token,
                AppConfig.DIAMOND_CURRENCY,
                new Callback<List<TeamLevel>>() {
                    @Override
                    public void onResponse(List<TeamLevel> levels) {
                        if (level <= levels.size()) {
                            bindRoot.setTeamLevel(levels.get(level - 1));
                        }
                    }
                });
    }

    @Override
    public void onStateChanged(TabTextView tabView, String type, String order) {
        requestDataList(type, order);

        // TAB 切换
        for (TabTextView tab : mTabs) {
            if (tab != tabView)
                tab.setUnselected();
        }
    }

}