package llc.metaversenetwork.app.ui.auth;

import android.os.Bundle;

import llc.metaversenetwork.app.R;
import llc.metaversenetwork.app.base.BaseTitleActivity;
import llc.metaversenetwork.app.databinding.ActivityAuthBinding;
import com.meiyou.mvp.MvpBinder;

import androidx.annotation.Nullable;

/**
 * 认证页面
 */
@MvpBinder(
)
public class AuthActivity extends BaseTitleActivity<ActivityAuthBinding> {

    @Override
    protected int titleResId() {
        return R.string.real_name_auth;
    }

    @Override
    protected int layoutId() {
        return R.layout.activity_auth;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

}