package llc.metaversenetwork.app.ui.setting;

import android.view.View;

import com.alaer.lib.api.bean.UserData;
import com.alaer.lib.data.UserDataUtil;
import llc.metaversenetwork.app.R;
import llc.metaversenetwork.app.base.BaseTitleActivity;
import llc.metaversenetwork.app.databinding.ActivitySecurityCenterBinding;
import llc.metaversenetwork.app.ui.user.LoginPwdSetActivity;
import llc.metaversenetwork.app.ui.user.SecondPwdSetActivity;
import llc.metaversenetwork.app.util.ViewUtil;

/**
 * 安全中心
 */
public class SecurityCenterActivity extends BaseTitleActivity<ActivitySecurityCenterBinding> {

    @Override
    protected int titleResId() {
        return R.string.security_center;
    }

    @Override
    protected int layoutId() {
        return R.layout.activity_security_center;
    }

    @Override
    public void onViewCreated() {
        super.onViewCreated();

        UserData userData = UserDataUtil.instance().getUserData();
        if (userData != null)
            bindRoot.phone.setText(userData.phone);
    }

    @Override
    public void click(View view) {
        switch (view.getId()) {
            case R.id.secondPwd:
                ViewUtil.gotoActivity(getContext(), SecondPwdSetActivity.class);
                break;
            case R.id.loginPwd:
                ViewUtil.gotoActivity(getContext(), LoginPwdSetActivity.class);
                break;
        }
    }

}