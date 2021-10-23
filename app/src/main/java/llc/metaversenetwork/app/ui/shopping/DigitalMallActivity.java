package llc.metaversenetwork.app.ui.shopping;

import llc.metaversenetwork.app.R;
import llc.metaversenetwork.app.base.BaseTitleActivity;
import llc.metaversenetwork.app.databinding.ActivityLoginBinding;

/**
 * 数字商城
 */
public class DigitalMallActivity extends BaseTitleActivity<ActivityLoginBinding> {

    @Override
    protected int titleResId() {
        return R.string.digital_mall;
    }

    @Override
    protected int layoutId() {
        return R.layout.activity_digital_mall;
    }

}