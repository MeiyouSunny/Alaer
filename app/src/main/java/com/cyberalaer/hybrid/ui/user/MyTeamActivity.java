package com.cyberalaer.hybrid.ui.user;

import android.os.Build;
import android.os.Handler;
import android.os.MessageQueue;
import android.view.View;

import com.alaer.lib.api.ApiUtil;
import com.alaer.lib.api.AppConfig;
import com.alaer.lib.api.Callback;
import com.alaer.lib.api.bean.TeamLevel;
import com.alaer.lib.api.bean.TeamProfile;
import com.alaer.lib.api.bean.UserData;
import com.alaer.lib.data.UserDataUtil;
import com.cyberalaer.hybrid.R;
import com.cyberalaer.hybrid.base.BaseTitleActivity;
import com.cyberalaer.hybrid.data.TeamLevelUtil;
import com.cyberalaer.hybrid.databinding.ActivityMyTeamBinding;
import com.cyberalaer.hybrid.util.ViewUtil;

import java.util.List;

import androidx.annotation.RequiresApi;

/**
 * 我的团队
 */
public class MyTeamActivity extends BaseTitleActivity<ActivityMyTeamBinding> {

    UserData userData;

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
        bindRoot.viewPager.setAdapter(adapter);
        bindRoot.tabs.setupWithViewPager(bindRoot.viewPager);

        userData = UserDataUtil.instance().getUserData();
        initData();

        new Handler().getLooper().getQueue().addIdleHandler(new MessageQueue.IdleHandler() {
            @Override
            public boolean queueIdle() {
                bindRoot.scrollView.scrollTo(0, 0);
                return false;
            }
        });
    }

    @Override
    public void click(View view) {
        switch (view.getId()) {
            case R.id.levelRule:
                ViewUtil.gotoActivity(this, LevelRuleActivity.class);
                break;
        }
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

}