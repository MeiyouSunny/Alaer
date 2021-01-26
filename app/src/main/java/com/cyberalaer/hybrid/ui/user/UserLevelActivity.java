package com.cyberalaer.hybrid.ui.user;

import com.alaer.lib.api.ApiUtil;
import com.alaer.lib.api.AppConfig;
import com.alaer.lib.api.Callback;
import com.alaer.lib.api.bean.UserData;
import com.alaer.lib.api.bean.UserLevel;
import com.alaer.lib.api.bean.UserLevelList;
import com.alaer.lib.data.UserDataUtil;
import com.cyberalaer.hybrid.R;
import com.cyberalaer.hybrid.base.BaseTitleActivity;
import com.cyberalaer.hybrid.databinding.ActivityUserLevelBinding;
import com.cyberalaer.hybrid.ui.webpage.WebPageActivity;
import com.cyberalaer.hybrid.util.CollectionUtils;

import java.util.List;

/**
 * 市民等级规则
 */
public class UserLevelActivity extends BaseTitleActivity<ActivityUserLevelBinding> {

    int level;
    int contribution;

    @Override
    protected int titleResId() {
        return R.string.level_rule;
    }

    @Override
    protected int layoutId() {
        return R.layout.activity_user_level;
    }

    @Override
    public void onViewCreated() {
        super.onViewCreated();
        setTitleRightIcon(R.drawable.ic_question);

        level = getIntent().getIntExtra("level", 0);
        contribution = getIntent().getIntExtra("contribution", 0);

        final int[] imgs = new int[]{R.drawable.ic_user_level0, R.drawable.ic_user_level1, R.drawable.ic_user_level2,
                R.drawable.ic_user_level3, R.drawable.ic_user_level4, R.drawable.ic_user_level5, R.drawable.ic_user_level6};
        if (level < imgs.length)
            bindRoot.levelImg.setBackgroundResource(imgs[level]);

        queryLevels();
    }

    @Override
    protected void onRightClick() {
        WebPageActivity.start(this, AppConfig.LEVEL_EXPLAIN, R.string.user_level_explain);
    }

    private void queryLevels() {
        UserData userData = UserDataUtil.instance().getUserData();
        if (userData == null)
            return;

        ApiUtil.apiService().userLevels(userData.uuid, String.valueOf(userData.uid), userData.token,
                AppConfig.DIAMOND_CURRENCY,
                new Callback<UserLevelList>() {
                    @Override
                    public void onResponse(UserLevelList levels) {
                        if (levels != null) {
                            showLevels(levels.list);
                        }
                    }
                });
    }

    private void showLevels(List<UserLevel> levels) {
        if (level < levels.size()) {
            bindRoot.levelInfo.setText(getString(R.string.contribution_info_is, levels.get(level).name, contribution));
        }

        bindRoot.repeatView.getRecyclerView().setPaddingRelative(0, 48, 0, 0);
        bindRoot.repeatView.getRecyclerView().setClipToPadding(false);

        if (CollectionUtils.isEmpty(levels))
            bindRoot.repeatView.layoutAdapterManager().showEmptyView();
        else
            bindRoot.repeatView.viewManager().bind(levels);
    }

}