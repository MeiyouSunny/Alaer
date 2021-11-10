package llc.metaversenetwork.app.ui.government;

import android.view.View;

import likly.dollar.$;
import llc.metaversenetwork.app.R;
import llc.metaversenetwork.app.base.BaseTitleActivity;
import llc.metaversenetwork.app.databinding.ActivityLoginBinding;
import llc.metaversenetwork.app.ui.auth.AuthActivity;
import llc.metaversenetwork.app.util.ViewUtil;

/**
 * 实名认证
 */
public class RealNameAuthActivity extends BaseTitleActivity<ActivityLoginBinding> {

    @Override
    protected int titleResId() {
        return R.string.government_hall;
    }

    @Override
    protected int layoutId() {
        return R.layout.activity_real_name_auth;
    }

    @Override
    public void click(View view) {
        switch (view.getId()) {
            case R.id.authCard:
                ViewUtil.gotoActivity(this, AuthActivity.class);
                break;
            case R.id.authPayPal:
            case R.id.authVisa:
                $.toast().text(R.string.will_open_soon).show();
                break;
        }
    }

}