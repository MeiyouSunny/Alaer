package llc.metaversenetwork.app.ui.leisure;

import llc.metaversenetwork.app.R;
import llc.metaversenetwork.app.base.BaseTitleActivity;
import llc.metaversenetwork.app.databinding.ActivityLoginBinding;

/**
 * 休闲大厅
 */
public class LeisureHallActivity extends BaseTitleActivity<ActivityLoginBinding> {

    @Override
    protected int titleResId() {
        return R.string.leisure_hall;
    }

    @Override
    protected int layoutId() {
        return R.layout.activity_leisure;
    }

}