package llc.metaversenetwork.app.ui.hospital;

import llc.metaversenetwork.app.R;
import llc.metaversenetwork.app.base.BaseTitleActivity;
import llc.metaversenetwork.app.databinding.ActivityLoginBinding;

/**
 * 人民医院
 */
public class HospitalActivity extends BaseTitleActivity<ActivityLoginBinding> {

    @Override
    protected int titleResId() {
        return R.string.people_hospital;
    }

    @Override
    protected int layoutId() {
        return R.layout.activity_hospital;
    }

}