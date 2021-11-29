package llc.metaversenetwork.app.ui.user;

import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;

import com.alaer.lib.api.ApiUtil;
import com.alaer.lib.api.AppConfig;
import com.alaer.lib.api.Callback;
import com.alaer.lib.api.bean.TeamDetail;
import com.alaer.lib.api.bean.UserData;
import com.alaer.lib.data.UserDataUtil;
import com.meiyou.mvp.MvpBinder;

import likly.dollar.$;
import llc.metaversenetwork.app.R;
import llc.metaversenetwork.app.base.BaseBindFragment;
import llc.metaversenetwork.app.databinding.FragmentRegistConfirmPwdBinding;
import llc.metaversenetwork.app.ui.home.HomeActivity;
import llc.metaversenetwork.app.ui.webpage.WebPageActivity;
import llc.metaversenetwork.app.util.NeteaseCaptcha;
import llc.metaversenetwork.app.util.SimpleTextWatcher;
import llc.metaversenetwork.app.util.StringUtil;
import llc.metaversenetwork.app.util.ToastUtil;
import llc.metaversenetwork.app.util.ViewUtil;

@MvpBinder(
)
public class RegistConfirmPwdFragment extends BaseBindFragment<FragmentRegistConfirmPwdBinding> {

    private String mPhone, mVerifyCode;

    @Override
    public int initLayoutResId() {
        return R.layout.fragment_regist_confirm_pwd;
    }

    @Override
    public void onResume() {
        super.onResume();
        setTitleText(R.string.apply);

        TextWatcher textWatcher = new SimpleTextWatcher() {
            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                onInputChanged();
            }
        };
        bindRoot.etPwd.addTextChangedListener(textWatcher);
        bindRoot.etPwdConfirm.addTextChangedListener(textWatcher);
        bindRoot.etInvitateCode.addTextChangedListener(textWatcher);
    }

    private void onInputChanged() {
        String pwd = ViewUtil.getText(bindRoot.etPwd);
        String pwdConfirm = ViewUtil.getText(bindRoot.etPwdConfirm);
        String invitateCode = ViewUtil.getText(bindRoot.etInvitateCode);
        final boolean hasInput = !TextUtils.isEmpty(pwd) && !TextUtils.isEmpty(pwdConfirm)
                && StringUtil.pwdIsValid(pwd) && StringUtil.pwdIsValid(pwdConfirm)
                && !TextUtils.isEmpty(invitateCode);
        bindRoot.next.setEnabled(hasInput);
    }

    @Override
    public void onViewCreated() {
        super.onViewCreated();
        mPhone = getArguments().getString("phone");
        mVerifyCode = getArguments().getString("verifyCode");
    }

    @Override
    public void click(View view) {
        switch (view.getId()) {
            case R.id.next:
                if (!TextUtils.equals(ViewUtil.getText(bindRoot.etPwd), ViewUtil.getText(bindRoot.etPwdConfirm))) {
                    ToastUtil.text(R.string.pwd_not_same).show();
                    return;
                }
                verifyCode();
                break;
            case R.id.agreement:
                WebPageActivity.start(getActivity(), getString(R.string.user_rule_url), R.string.user_agreement);
                break;
            case R.id.toLogin:
                navigate(R.id.action_to_login);
                break;
        }
    }

    private void regist(String validate) {
        ApiUtil.apiService().regist(mPhone, mVerifyCode,
//                StringUtil.toMD5(ViewUtil.getText(bindRoot.etPwd) + AppConfig.MD5_KEY_TEMP),
                ViewUtil.getText(bindRoot.etPwd),
                ViewUtil.getText(bindRoot.etInvitateCode),
                validate, AppConfig.VERIFY_ID, AppConfig.DIALLING_CODE,
                new Callback<UserData>() {
                    @Override
                    public void onResponse(UserData userInfo) {
                        ToastUtil.text(R.string.regist_success).show();
                        $.config().putString("phone", mPhone);
                        UserDataUtil.instance().setUserData(userInfo);

                        ApiUtil.apiService().getTeamDetailInfo(userInfo.uuid, String.valueOf(userInfo.uid), userInfo.token, AppConfig.DIAMOND_CURRENCY,
                                new Callback<TeamDetail>() {
                                    @Override
                                    public void onResponse(TeamDetail teamDetail) {
                                        UserDataUtil.instance().saveTeamDetailInfo(teamDetail);
                                        ViewUtil.gotoActivity(getContext(), HomeActivity.class);
                                    }
                                });
                    }

                    @Override
                    public void onError(int code, String msg) {
                        ToastUtil.text(msg).show();
                    }
                });
    }

    private void verifyCode() {
        NeteaseCaptcha.start(getContext(), new NeteaseCaptcha.OnCaptchaListener() {
            @Override
            public void onCaptchaSuccess(String validate) {
                regist(validate);
            }

            @Override
            public void onCaptchaError(String msg) {
                ToastUtil.text(msg).show();
            }
        });
    }

}