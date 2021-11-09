package llc.metaversenetwork.app.ui.auth;

import android.view.View;

import llc.metaversenetwork.app.R;
import llc.metaversenetwork.app.base.BaseTitleActivity;
import llc.metaversenetwork.app.databinding.ActivityAuthingBinding;
import llc.metaversenetwork.app.ui.home.HomeActivity;
import llc.metaversenetwork.app.util.ViewUtil;

/**
 * 实名认证审核中
 */
public class AuthingActivity extends BaseTitleActivity<ActivityAuthingBinding> {

    @Override
    protected int titleResId() {
        return R.string.real_name_auth;
    }

    @Override
    protected int layoutId() {
        return R.layout.activity_authing;
    }

    @Override
    public void click(View view) {
        switch (view.getId()) {
            case R.id.ok:
                ViewUtil.gotoActivity(this, HomeActivity.class);
                break;
        }
    }

}