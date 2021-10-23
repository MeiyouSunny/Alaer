package llc.metaversenetwork.app.ui.user;

import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.View;

import com.alaer.lib.api.ApiUtil;
import com.alaer.lib.api.AppConfig;
import com.alaer.lib.api.Callback;
import com.alaer.lib.api.bean.TeamDetail;
import com.alaer.lib.api.bean.UserData;
import com.alaer.lib.data.UserDataUtil;
import com.alaer.lib.util.AvatarUploader;
import llc.metaversenetwork.app.R;
import llc.metaversenetwork.app.base.BaseTitleActivity;
import llc.metaversenetwork.app.databinding.ActivityUserInfoBinding;
import llc.metaversenetwork.app.ui.dialog.DialogInputSecondPwd;
import llc.metaversenetwork.app.util.NeteaseCaptcha;
import llc.metaversenetwork.app.util.StringUtil;
import llc.metaversenetwork.app.util.ViewUtil;

import org.json.JSONException;
import org.json.JSONObject;

import androidx.annotation.Nullable;
import likly.dialogger.Dialogger;
import likly.dollar.$;

/**
 * 用户信息
 */
public class UserInfoActivity extends BaseTitleActivity<ActivityUserInfoBinding> implements AvatarUploader.OnUploadListener {
    private final int REQUEST_SELECT_PIC = 2;

    private TeamDetail mInviterInfo;
    private UserData userData;
    private String mTradePhraseCode, mPicUrl;

    @Override
    protected int titleResId() {
        return R.string.user_info;
    }

    @Override
    protected int layoutId() {
        return R.layout.activity_user_info;
    }

    @Override
    public void onViewCreated() {
        super.onViewCreated();
        getData();
    }

    private void getData() {
        userData = UserDataUtil.instance().getUserData();
        if (userData == null)
            return;

        TeamDetail teamDetail = UserDataUtil.instance().getTeamDetail();
        if (teamDetail != null) {
            bindRoot.setUser(teamDetail);
            showUserAvatar(teamDetail.avatar);
        }

        ApiUtil.apiService().getFollowInfo(userData.uuid, String.valueOf(userData.uid), userData.token, AppConfig.DIAMOND_CURRENCY,
                new Callback<TeamDetail>() {
                    @Override
                    public void onResponse(TeamDetail response) {
                        mInviterInfo = response;
                        bindRoot.setFollow(response);
                    }
                });
    }

    private void showUserAvatar(String avatarUrl) {
        if (!TextUtils.isEmpty(avatarUrl)) {
            ViewUtil.showImage(getApplicationContext(), bindRoot.icHead, avatarUrl);
        }
    }

    @Override
    public void click(View view) {
        switch (view.getId()) {
            case R.id.setWechat:
                SetProfileActivity.start(this, SetProfileActivity.WECHAT);
                break;
            case R.id.setInvitateCode:
                if (UserDataUtil.instance().isFrom3DAccount())
                    $.toast().text(R.string.function_not_open).show();
                else
                    SetProfileActivity.start(this, SetProfileActivity.INVITATE_CODE);
                break;
            case R.id.setNikeName:
                SetProfileActivity.start(this, SetProfileActivity.NIKE_NAME);
                break;
            case R.id.inviterInfo:
                goToInviterInfo();
                break;
            case R.id.selectPic:
                selectPic();
                break;
        }
    }

    private void selectPic() {
        Intent intent = new Intent(Intent.ACTION_PICK,
                android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(intent, REQUEST_SELECT_PIC);
    }

    private void goToInviterInfo() {
        if (mInviterInfo == null)
            return;
        Bundle data = new Bundle();
        data.putSerializable("inviter", mInviterInfo);
        ViewUtil.gotoActivity(getContext(), InviterInfoActivity.class, data);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            if (requestCode == SetProfileActivity.REQUST_CODE) {
                getData();
            } else if (requestCode == REQUEST_SELECT_PIC && data != null) {
                Uri selectedImage = data.getData();
                String[] filePathColumns = {MediaStore.Images.Media.DATA};
                Cursor cursor = getContentResolver().query(selectedImage, filePathColumns, null, null, null);
                cursor.moveToFirst();
                int columnIndex = cursor.getColumnIndex(filePathColumns[0]);
                String imagePath = cursor.getString(columnIndex);
                cursor.close();
                uploadPic(imagePath);
            }
        }
    }

    private void uploadPic(String imagePath) {
        AvatarUploader uploader = new AvatarUploader(this);
        uploader.setmListener(this);
        uploader.upload(imagePath);
    }

    @Override
    public void onUploadResult(boolean success, String picUrl) {
        if (!success) {
            $.toast().text("头像上传失败！").show();
            return;
        }
        if (!TextUtils.isEmpty(picUrl)) {
            mPicUrl = picUrl;
            showSecondPwdDialog();
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
                "avatar", mPicUrl,
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
                        bindRoot.setUser(teamDetail);
                        showUserAvatar(teamDetail.avatar);
                    }
                });
    }

}