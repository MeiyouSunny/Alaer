package com.cyberalaer.hybrid.ui.user;

import com.alaer.lib.api.ApiUtil;
import com.alaer.lib.api.AppConfig;
import com.alaer.lib.api.Callback;
import com.alaer.lib.api.bean.TeamLevel;
import com.alaer.lib.api.bean.UserData;
import com.alaer.lib.data.UserDataUtil;
import com.cyberalaer.hybrid.R;
import com.cyberalaer.hybrid.base.BaseTitleActivity;
import com.cyberalaer.hybrid.databinding.ActivityLevelRuleBinding;

import java.util.List;

/**
 * 等级规则
 */
public class LevelRuleActivity extends BaseTitleActivity<ActivityLevelRuleBinding> {

    @Override
    protected int titleResId() {
        return R.string.level_rule;
    }

    @Override
    protected int layoutId() {
        return R.layout.activity_level_rule;
    }

    @Override
    public void onViewCreated() {
        super.onViewCreated();
        initData();
    }

    private void initData() {
        UserData userData = UserDataUtil.instance().getUserData();
        if (userData == null)
            return;
        ApiUtil.apiService().teamStarLevel(userData.uuid, String.valueOf(userData.uid), userData.token,
                AppConfig.DIAMOND_CURRENCY,
                new Callback<List<TeamLevel>>() {
                    @Override
                    public void onResponse(List<TeamLevel> levels) {
                        showLevels(levels);
                    }
                });
    }

    private void showLevels(List<TeamLevel> levels) {
        TeamLevelAdapter adapter = new TeamLevelAdapter(levels);
        bindRoot.list.setAdapter(adapter);
    }

}