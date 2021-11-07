package llc.metaversenetwork.app.ui.user;

import com.alaer.lib.api.ApiUtil;
import com.alaer.lib.api.AppConfig;
import com.alaer.lib.api.Callback;
import com.alaer.lib.api.bean.TeamLevel;
import com.alaer.lib.api.bean.UserData;
import com.alaer.lib.data.UserDataUtil;

import java.util.List;

import llc.metaversenetwork.app.R;
import llc.metaversenetwork.app.base.BaseTitleActivity;
import llc.metaversenetwork.app.databinding.ActivityLevelRuleBinding;
import llc.metaversenetwork.app.ui.webpage.WebPageActivity;

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
        setTitleRightIcon(R.drawable.ic_question);
        initData();
    }

    @Override
    protected void onRightClick() {
        WebPageActivity.start(this, getString(R.string.team_level_desc_url), R.string.level_rule);
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