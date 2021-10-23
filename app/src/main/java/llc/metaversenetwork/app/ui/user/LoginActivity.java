package llc.metaversenetwork.app.ui.user;

import android.os.Bundle;

import llc.metaversenetwork.app.R;
import llc.metaversenetwork.app.base.BaseTitleActivity;
import llc.metaversenetwork.app.databinding.ActivityLoginBinding;
import com.meiyou.mvp.MvpBinder;

import androidx.annotation.Nullable;

@MvpBinder(
)
public class LoginActivity extends BaseTitleActivity<ActivityLoginBinding> {

    @Override
    protected int titleResId() {
        return R.string.enter;
    }

    @Override
    protected int layoutId() {
        return R.layout.activity_login;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

}