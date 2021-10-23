package llc.metaversenetwork.app.ui.user;

import android.app.Activity;
import android.content.Intent;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.View;

import com.alaer.lib.api.ApiUtil;
import com.alaer.lib.api.AppConfig;
import com.alaer.lib.api.Callback;
import com.alaer.lib.api.bean.TeamDetail;
import com.alaer.lib.api.bean.UserData;
import com.alaer.lib.data.UserDataUtil;
import llc.metaversenetwork.app.R;
import llc.metaversenetwork.app.base.BaseTitleActivity;
import llc.metaversenetwork.app.databinding.ActivityWechatNoSetBinding;
import llc.metaversenetwork.app.ui.dialog.DialogInputSecondPwd;
import llc.metaversenetwork.app.util.NeteaseCaptcha;
import llc.metaversenetwork.app.util.SimpleTextWatcher;
import llc.metaversenetwork.app.util.StringUtil;
import llc.metaversenetwork.app.util.ViewUtil;

import org.json.JSONException;
import org.json.JSONObject;

import androidx.annotation.IntDef;
import likly.dialogger.Dialogger;
import likly.dollar.$;

/**
 * 用户属性设置:微信/邀请码/昵称
 */
public class SetProfileActivity extends BaseTitleActivity<ActivityWechatNoSetBinding> {

    public static final int REQUST_CODE = 1;

    public static final int WECHAT = 0;
    public static final int INVITATE_CODE = 1;
    public static final int NIKE_NAME = 2;

    @IntDef({WECHAT, INVITATE_CODE, NIKE_NAME})
    @interface TYPE {
    }

    int type;
    String key;

    public static void start(Activity context, @TYPE int type) {
        Intent intent = new Intent(context, SetProfileActivity.class);
        Bundle bundle = new Bundle();
        bundle.putInt("type", type);
        intent.putExtras(bundle);
        context.startActivityForResult(intent, REQUST_CODE);
    }

    UserData userData;
    String mTradePhraseCode;

    @Override
    protected int titleResId() {
        return -1;
    }

    @Override
    protected int layoutId() {
        return R.layout.activity_wechat_no_set;
    }

    @Override
    public void onViewCreated() {
        super.onViewCreated();

        userData = UserDataUtil.instance().getUserData();

        type = getIntent().getIntExtra("type", 0);

        final Resources resources = getResources();
        final String title = resources.getStringArray(R.array.set_profile_title)[type];
        final String profileDesc = resources.getStringArray(R.array.set_profile_desc)[type];
        final String intputHint = resources.getStringArray(R.array.set_profile_input_hint)[type];
        final String profileConsume = resources.getStringArray(R.array.set_profile_consume)[type];
        TypedArray typedArray = resources.obtainTypedArray(R.array.set_profile_icon);
        final int iconResId = typedArray.getResourceId(type, 0);
        key = resources.getStringArray(R.array.set_profile_key)[type];

        setTitleText(title);
        bindRoot.titleBelow.setText(title);
        bindRoot.profileDesc.setText(profileDesc);
        bindRoot.input.setHint(intputHint);
        bindRoot.profileConsume.setText(profileConsume);
        bindRoot.icon.setImageResource(iconResId);

        bindRoot.input.addTextChangedListener(new SimpleTextWatcher() {
            @Override
            public void afterTextChanged(Editable editable) {
                bindRoot.submit.setEnabled(!TextUtils.isEmpty(ViewUtil.getText(bindRoot.input)));
            }
        });

        TeamDetail userData = UserDataUtil.instance().getTeamDetail();
        if (userData == null)
            return;
        String inputContent = "";
        switch (type) {
            case WECHAT:
                inputContent = userData.wechat;
                break;
            case INVITATE_CODE:
                inputContent = userData.code;
                break;
            case NIKE_NAME:
                inputContent = userData.name;
                break;
        }
        if (!TextUtils.isEmpty(inputContent)) {
            bindRoot.input.setText(inputContent);
            bindRoot.input.setSelection(inputContent.length());
        }
    }

    @Override
    public void click(View view) {
        switch (view.getId()) {
            case R.id.icClear:
                bindRoot.input.setText("");
                break;
            case R.id.submit:
                showSecondPwdDialog();
                break;
        }
    }

    private void showSecondPwdDialog() {
        DialogInputSecondPwd dialog = new DialogInputSecondPwd();
        dialog.setListener(pwd -> confirmTransactionCode(pwd));
        Dialogger.newDialog(getContext()).holder(dialog).gravity(Gravity.CENTER).cancelable(false).show();
    }

    private void confirmTransactionCode(String pwd) {
        if (userData == null)
            return;

        final String pwdMD5 = StringUtil.toMD5(pwd + AppConfig.MD5_KEY_TEMP_SECOND + userData.uid);
        ApiUtil.apiService().confirmTransactionCode(userData.uuid, String.valueOf(userData.uid), userData.token, AppConfig.DIAMOND_CURRENCY, pwdMD5,
                new Callback<String>() {
                    @Override
                    public void onResponse(String json) {
                        if (!TextUtils.isEmpty(json)) {
                            JSONObject jsonObject = null;
                            try {
                                jsonObject = new JSONObject(json);
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                            if (jsonObject.has("code")) {
                                mTradePhraseCode = jsonObject.optString("code");
                                verifyCode();
                            }
                        }
                    }

                    @Override
                    public void onError(int code, String msg) {
                        $.toast().text(msg).show();
                    }
                });
    }

    private void verifyCode() {
        NeteaseCaptcha.start(getContext(), new NeteaseCaptcha.OnCaptchaListener() {
            @Override
            public void onCaptchaSuccess(String validate) {
                modifyProfile(validate, mTradePhraseCode);
            }

            @Override
            public void onCaptchaError(String msg) {
                $.toast().text(msg).show();
            }
        });
    }

    private void modifyProfile(String validate, String tradePhraseCode) {
        ApiUtil.apiService().modifyProfile(userData.uuid, String.valueOf(userData.uid), userData.token, AppConfig.DIAMOND_CURRENCY,
                validate, AppConfig.VERIFY_ID, tradePhraseCode,
                key, ViewUtil.getText(bindRoot.input),
                new Callback<String>() {
                    @Override
                    public void onResponse(String response) {
                        $.toast().text(R.string.modify_success).show();
                        refreshProfile();
                    }

                    @Override
                    public void onError(int code, String msg) {
                        $.toast().text(msg).show();
                    }
                });
    }

    private void refreshProfile() {
        ApiUtil.apiService().getTeamDetailInfo(userData.uuid, String.valueOf(userData.uid), userData.token, AppConfig.DIAMOND_CURRENCY,
                new Callback<TeamDetail>() {
                    @Override
                    public void onResponse(TeamDetail teamDetail) {
                        UserDataUtil.instance().saveTeamDetailInfo(teamDetail);
                        setResult(RESULT_OK);
                        finish();
                    }
                });
    }

}