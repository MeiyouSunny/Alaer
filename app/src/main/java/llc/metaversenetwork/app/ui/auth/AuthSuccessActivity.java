package llc.metaversenetwork.app.ui.auth;

import android.view.View;

import com.alaer.lib.data.UserDataUtil;
import llc.metaversenetwork.app.R;
import llc.metaversenetwork.app.base.BaseTitleActivity;
import llc.metaversenetwork.app.databinding.ActivityLoginBinding;
import llc.metaversenetwork.app.ui.share.ShareActivity;
import llc.metaversenetwork.app.util.ViewUtil;

import likly.dollar.$;

/**
 * 实名认证完成
 */
public class AuthSuccessActivity extends BaseTitleActivity<ActivityLoginBinding> {

    @Override
    protected int titleResId() {
        return R.string.real_name_auth;
    }

    @Override
    protected int layoutId() {
        return R.layout.activity_auth_success;
    }

    @Override
    public void click(View view) {
        switch (view.getId()) {
            case R.id.share:
                if (UserDataUtil.instance().isFrom3DAccount())
                    $.toast().text(R.string.function_not_open).show();
                else
                    ViewUtil.gotoActivity(this, ShareActivity.class);
                break;
        }
    }

}