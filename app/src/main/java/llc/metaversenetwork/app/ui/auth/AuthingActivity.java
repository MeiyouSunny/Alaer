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

    boolean needBackHome;

    @Override
    protected int titleResId() {
        return R.string.auth_status;
    }

    @Override
    protected int layoutId() {
        return R.layout.activity_authing;
    }

    @Override
    public void onViewCreated() {
        super.onViewCreated();
        needBackHome = getIntent().getBooleanExtra("needBackHome", false);
    }

    @Override
    public void click(View view) {
        switch (view.getId()) {
            case R.id.ok:
                if (needBackHome)
                    ViewUtil.gotoActivity(this, HomeActivity.class);
                else
                    finish();
                break;
        }
    }

}