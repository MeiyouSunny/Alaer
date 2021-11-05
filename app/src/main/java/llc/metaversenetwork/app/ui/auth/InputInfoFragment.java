package llc.metaversenetwork.app.ui.auth;

import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.Gravity;
import android.view.View;

import com.alaer.lib.api.ApiUtil;
import com.alaer.lib.api.AppConfig;
import com.alaer.lib.api.Callback;
import com.alaer.lib.api.bean.UserData;
import com.alaer.lib.data.UserDataUtil;
import com.alaer.lib.util.AvatarUploader;
import com.meiyou.mvp.MvpBinder;

import androidx.annotation.Nullable;
import likly.dialogger.Dialogger;
import likly.dollar.$;
import llc.metaversenetwork.app.R;
import llc.metaversenetwork.app.base.BaseBindFragment;
import llc.metaversenetwork.app.databinding.FragmentAuthInputInfoBinding;
import llc.metaversenetwork.app.ui.dialog.DialogAuthInfoConfirm;
import llc.metaversenetwork.app.util.SimpleTextWatcher;
import llc.metaversenetwork.app.util.ViewUtil;

import static android.app.Activity.RESULT_OK;

@MvpBinder(
)
public class InputInfoFragment extends BaseBindFragment<FragmentAuthInputInfoBinding> implements AvatarUploader.OnUploadListener {
    private final int REQUEST_SELECT_PIC = 2;

    UserData userData;
    String mOrderId, mPhotoPath, mPhotoUrl;

    @Override
    public int initLayoutResId() {
        return R.layout.fragment_auth_input_info;
    }

    @Override
    public void click(View view) {
        switch (view.getId()) {
            case R.id.next:
                showAlipayInfoConfirm();
                break;
            case R.id.selectPhoto:
                selectPic();
                break;
        }
    }

    private void selectPic() {
        Intent intent = new Intent(Intent.ACTION_PICK,
                android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(intent, REQUEST_SELECT_PIC);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK && requestCode == REQUEST_SELECT_PIC && data != null) {
            Uri selectedImage = data.getData();
            String[] filePathColumns = {MediaStore.Images.Media.DATA};
            Cursor cursor = getContext().getContentResolver().query(selectedImage, filePathColumns, null, null, null);
            cursor.moveToFirst();
            int columnIndex = cursor.getColumnIndex(filePathColumns[0]);
            mPhotoPath = cursor.getString(columnIndex);
            cursor.close();
            showPhoto(selectedImage);
        }
    }

    private void showPhoto(Uri imgeUri) {
        bindRoot.photo.setImageURI(imgeUri);
    }

    private void showAlipayInfoConfirm() {
        String cardType = (String) bindRoot.cardTypeSpinner.getSelectedItem();
        DialogAuthInfoConfirm dialogAuthInfoConfirm = new DialogAuthInfoConfirm(
                cardType, ViewUtil.getText(bindRoot.etName), ViewUtil.getText(bindRoot.etCard));
        dialogAuthInfoConfirm.setListener(new DialogAuthInfoConfirm.OnConfirmListener() {
            @Override
            public void onConfirmClick() {
                uploadPic();
            }
        });
        Dialogger.newDialog(getContext()).holder(dialogAuthInfoConfirm)
                .gravity(Gravity.CENTER)
                .cancelable(false)
                .show();
    }

    private void uploadPic() {
        AvatarUploader uploader = new AvatarUploader(getContext());
        uploader.setmListener(this);
        uploader.upload(mPhotoPath);
        $.toast().text(R.string.uploading).show();
    }

    @Override
    public void onUploadResult(boolean success, String picUrl) {
        if (success && !TextUtils.isEmpty(picUrl)) {
            mPhotoUrl = picUrl;
            submitAuthInfo();
        } else {
            $.toast().text(R.string.upload_failed).show();
        }
    }

    private void submitAuthInfo() {
        String cardType = (String) bindRoot.cardTypeSpinner.getSelectedItem();
        String name = ViewUtil.getText(bindRoot.etName);
        String cardNo = ViewUtil.getText(bindRoot.etCard);

        ApiUtil.apiService().submitCardInfo(userData.uuid, String.valueOf(userData.uid), userData.token, AppConfig.DIAMOND_CURRENCY,
                "-", "-",
                name, cardNo, mPhotoUrl, cardType,
                new Callback<String>() {
                    @Override
                    public void onResponse(String orderId) {
                        navigate(R.id.action_to_pay_success);
                    }

                    @Override
                    public void onError(int code, String msg) {
                        Bundle data = new Bundle();
                        data.putString("error", msg);
                        navigate(R.id.action_to_pay_failed, data);
                    }
                });
    }

    @Override
    public void onViewCreated() {
        super.onViewCreated();

        final TextWatcher watcher = new SimpleTextWatcher() {
            @Override
            public void afterTextChanged(Editable editable) {
                onInputChanged();
            }
        };
        bindRoot.etName.addTextChangedListener(watcher);
        bindRoot.etCard.addTextChangedListener(watcher);

        userData = UserDataUtil.instance().getUserData();
    }

    private void onInputChanged() {
        final String name = ViewUtil.getText(bindRoot.etName);
        final String cardID = ViewUtil.getText(bindRoot.etCard);
        boolean hasInput = !TextUtils.isEmpty(name) && !TextUtils.isEmpty(cardID);
        bindRoot.next.setEnabled(hasInput);
    }

    private void gotoPayWeb() {
        final String payWebUrl = AppConfig.ALIPAY_URL + mOrderId;
        Uri uri = Uri.parse(payWebUrl);
        Intent intent = new Intent(Intent.ACTION_VIEW, uri);
        startActivity(intent);
    }
}