package llc.metaversenetwork.app.ui.auth;

import android.os.Bundle;
import android.view.View;

import com.meiyou.mvp.MvpBinder;

import androidx.annotation.Nullable;
import llc.metaversenetwork.app.R;
import llc.metaversenetwork.app.base.BaseBindFragment;
import llc.metaversenetwork.app.databinding.FragmentAuthPayFailedBinding;

@MvpBinder(
)
public class PayFailedFragment extends BaseBindFragment<FragmentAuthPayFailedBinding> {

    @Override
    public int initLayoutResId() {
        return R.layout.fragment_auth_pay_failed;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        String error = getArguments().getString("error");
        bindRoot.error.setText(getString(R.string.auth_state_fail, error));
    }

    @Override
    public void click(View view) {
        switch (view.getId()) {
            case R.id.customerService:
                getActivity().onBackPressed();
                break;
            case R.id.reTry:
                getActivity().onBackPressed();
                break;
        }
    }

}