package llc.metaversenetwork.app.ui.auth;

import android.view.View;

import llc.metaversenetwork.app.R;
import llc.metaversenetwork.app.base.BaseBindFragment;
import llc.metaversenetwork.app.databinding.FragmentAuthPayFailedBinding;
import com.meiyou.mvp.MvpBinder;

@MvpBinder(
)
public class PayFailedFragment extends BaseBindFragment<FragmentAuthPayFailedBinding> {

    @Override
    public int initLayoutResId() {
        return R.layout.fragment_auth_pay_failed;
    }

    @Override
    public void click(View view) {
        switch (view.getId()) {
            case R.id.ok:
//                navigate(R.id.action_to_input_page);
                getActivity().onBackPressed();
                break;
        }
    }

}