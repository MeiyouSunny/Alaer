package llc.metaversenetwork.app.ui.user;

import android.view.View;

import com.alaer.lib.api.bean.UserLevel;

import llc.metaversenetwork.app.R;
import llc.metaversenetwork.app.base.repeatview.BaseViewHolder;
import llc.metaversenetwork.app.databinding.ItemUserLevelBinding;

/**
 * 市民等级Adapter
 */
public class UserLevelAdapter extends BaseViewHolder<ItemUserLevelBinding, UserLevel> {

    @Override
    protected void onBindData(UserLevel level) {
        bindRoot.setLevel(level);
        bindRoot.levelValue.setText(getContext().getString(R.string.level_is, level.level));
        bindRoot.executePendingBindings();
        int position = getPosition();
        bindRoot.levelPoint.setVisibility(getPosition() == UserLevelActivity.level ? View.VISIBLE : View.INVISIBLE);
    }

    @Override
    protected int getViewHolderLayout() {
        return R.layout.item_user_level;
    }

}

