package com.cyberalaer.hybrid.ui.user;

import com.alaer.lib.api.bean.UserLevel;
import com.cyberalaer.hybrid.R;
import com.cyberalaer.hybrid.base.repeatview.BaseViewHolder;
import com.cyberalaer.hybrid.databinding.ItemUserLevelBinding;

/**
 * 市民等级Adapter
 */
public class UserLevelAdapter extends BaseViewHolder<ItemUserLevelBinding, UserLevel> {

    final int[] imgs = new int[]{R.drawable.ic_user_level0, R.drawable.ic_user_level1, R.drawable.ic_user_level2,
            R.drawable.ic_user_level3, R.drawable.ic_user_level4, R.drawable.ic_user_level5, R.drawable.ic_user_level6};

    @Override
    protected void onBindData(UserLevel level) {
        bindRoot.setImgs(imgs);
        bindRoot.setLevel(level);
        bindRoot.executePendingBindings();
    }

    @Override
    protected int getViewHolderLayout() {
        return R.layout.item_user_level;
    }

}

