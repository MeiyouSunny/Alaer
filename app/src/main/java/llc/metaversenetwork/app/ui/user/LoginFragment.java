package llc.metaversenetwork.app.ui.user;

import android.app.Activity;
import android.content.Intent;
import android.text.TextUtils;
import android.view.View;

import com.alaer.lib.api.ApiUtil;
import com.alaer.lib.api.AppConfig;
import com.alaer.lib.api.Callback;
import com.alaer.lib.api.bean.Region;
import com.alaer.lib.api.bean.TeamDetail;
import com.alaer.lib.api.bean.UserData;
import com.alaer.lib.data.UserDataUtil;
import com.meiyou.mvp.MvpBinder;

import androidx.annotation.Nullable;
import likly.dollar.$;
import llc.metaversenetwork.app.R;
import llc.metaversenetwork.app.base.BaseBindFragment;
import llc.metaversenetwork.app.databinding.FragmentLoginBinding;
import llc.metaversenetwork.app.ui.home.HomeActivity;
import llc.metaversenetwork.app.util.NeteaseCaptcha;
import llc.metaversenetwork.app.util.SimpleTextWatcher;
import llc.metaversenetwork.app.util.StringUtil;
import llc.metaversenetwork.app.util.ToastUtil;
import llc.metaversenetwork.app.util.ViewUtil;

@MvpBinder(
)
public class LoginFragment extends BaseBindFragment<FragmentLoginBinding> {

    private String mPhone, mPwd;

    @Override
    public int initLayoutResId() {
        return R.layout.fragment_login;
    }

    @Override
    public void onResume() {
        super.onResume();
        setTitleText(R.string.enter);
    }

    @Override
    public void click(View view) {
        switch (view.getId()) {
            case R.id.toRegist:
                navigate(R.id.action_to_regisPhoneVerify);
                break;
            case R.id.forgetPwd:
                navigate(R.id.action_to_restPwdFragment);
                break;
            case R.id.btnLogin:
                verifyCode();
                break;
            case R.id.region:
                RegionActivity.startForResult(this);
                break;
        }
    }

    @Override
    public void onViewCreated() {
        super.onViewCreated();
        bindRoot.region.setText("+" + AppConfig.DIALLING_CODE);

        bindRoot.etPhone.addTextChangedListener(new SimpleTextWatcher() {
            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                onInputChange();
            }
        });
        bindRoot.etPwd.addTextChangedListener(new SimpleTextWatcher() {
            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                onInputChange();
            }
        });

        judgeLoginedOrNot();
    }

    private void judgeLoginedOrNot() {
        UserData userData = UserDataUtil.instance().getSavedUserData();
        if (userData != null) {
            UserDataUtil.instance().getSavedTeamDetail();
            ViewUtil.gotoActivity(getContext(), HomeActivity.class);
            getActivity().finish();
        }
    }

    private void onInputChange() {
        mPhone = ViewUtil.getText(bindRoot.etPhone);
        mPwd = ViewUtil.getText(bindRoot.etPwd);
        boolean hasInput = !TextUtils.isEmpty(mPhone) && !TextUtils.isEmpty(mPwd);
        bindRoot.btnLogin.setEnabled(hasInput);
    }

    private void verifyCode() {
        NeteaseCaptcha.start(getContext(), new NeteaseCaptcha.OnCaptchaListener() {
            @Override
            public void onCaptchaSuccess(String validate) {
                login(validate);
            }

            @Override
            public void onCaptchaError(String msg) {
                ToastUtil.text(msg).show();
            }
        });
    }

    private void login(String validate) {
        mPhone = ViewUtil.getText(bindRoot.etPhone);
        mPwd = ViewUtil.getText(bindRoot.etPwd);

        if (!StringUtil.phoneIsValid(mPhone)) {
            ToastUtil.text(R.string.pls_input_valid_phone).show();
            return;
        }

        ApiUtil.apiService().login(mPhone, StringUtil.toMD5(mPwd + AppConfig.MD5_KEY_TEMP), validate,
                AppConfig.VERIFY_ID, "2", AppConfig.DIALLING_CODE,
                new Callback<UserData>() {
                    @Override
                    public void onResponse(UserData userData) {
                        $.config().putString("phone", mPhone);
                        UserDataUtil.instance().saveUserDataInfo(userData);

                        ApiUtil.apiService().getTeamDetailInfo(userData.uuid, String.valueOf(userData.uid), userData.token, AppConfig.DIAMOND_CURRENCY,
                                new Callback<TeamDetail>() {
                                    @Override
                                    public void onResponse(TeamDetail teamDetail) {
                                        UserDataUtil.instance().saveTeamDetailInfo(teamDetail);
                                        HomeActivity.isFirstOpen = true;
                                        ViewUtil.gotoActivity(getContext(), HomeActivity.class);
                                        getActivity().finish();
                                    }
                                });
                    }

                    @Override
                    public void onError(int code, String msg) {
                        ToastUtil.text(msg).show();
                    }
                });
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == RegionActivity.REQUEST_CODE && resultCode == Activity.RESULT_OK) {
            final Region region = (Region) data.getSerializableExtra("region");
            bindRoot.region.setText("+" + region.code);
        }
    }

}