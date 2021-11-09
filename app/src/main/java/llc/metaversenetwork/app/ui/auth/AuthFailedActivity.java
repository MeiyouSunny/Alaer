package llc.metaversenetwork.app.ui.auth;

import android.view.View;

import com.alaer.lib.data.UserDataUtil;

import likly.dollar.$;
import llc.metaversenetwork.app.R;
import llc.metaversenetwork.app.base.BaseTitleActivity;
import llc.metaversenetwork.app.databinding.ActivityAuthFailedBinding;
import llc.metaversenetwork.app.util.ViewUtil;

/**
 * 实名认证失败
 */
public class AuthFailedActivity extends BaseTitleActivity<ActivityAuthFailedBinding> {

    @Override
    protected int titleResId() {
        return R.string.real_name_auth;
    }

    @Override
    protected int layoutId() {
        return R.layout.activity_auth_failed;
    }

    @Override
    public void click(View view) {
        switch (view.getId()) {
            case R.id.customerService:
                $.toast().text(R.string.will_open_soon).show();
                break;
            case R.id.reTry:
                ViewUtil.gotoActivity(this, AuthActivity.class);
                finish();
                break;
        }
    }

    @Override
    public void onViewCreated() {
        super.onViewCreated();
        bindRoot.reason.setText(getString(R.string.auth_state_fail, UserDataUtil.instance().authFailedReason()));
    }
}