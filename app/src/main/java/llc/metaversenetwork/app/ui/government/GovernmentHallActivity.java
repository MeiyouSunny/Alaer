package llc.metaversenetwork.app.ui.government;

import android.view.View;

import llc.metaversenetwork.app.R;
import llc.metaversenetwork.app.base.BaseTitleActivity;
import llc.metaversenetwork.app.databinding.ActivityGovernmentBinding;
import llc.metaversenetwork.app.ui.user.UserMineActivity;
import llc.metaversenetwork.app.util.ViewUtil;

/**
 * 政务大厅
 */
public class GovernmentHallActivity extends BaseTitleActivity<ActivityGovernmentBinding> {

    @Override
    protected int titleResId() {
        return R.string.government_hall;
    }

    @Override
    protected int layoutId() {
        return R.layout.activity_government;
    }

    @Override
    public void click(View view) {
        switch (view.getId()) {
            case R.id.auth:
                ViewUtil.gotoAuthPage(this);
                break;
            case R.id.mine:
                ViewUtil.gotoActivity(this, UserMineActivity.class);
                break;
        }
    }

}