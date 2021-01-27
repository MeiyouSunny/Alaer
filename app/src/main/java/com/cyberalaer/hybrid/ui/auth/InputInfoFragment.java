package com.cyberalaer.hybrid.ui.auth;

import android.content.Intent;
import android.net.Uri;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.Gravity;
import android.view.View;

import com.alaer.lib.api.ApiUtil;
import com.alaer.lib.api.AppConfig;
import com.alaer.lib.api.Callback;
import com.alaer.lib.api.bean.OrderInfo;
import com.alaer.lib.api.bean.UserData;
import com.alaer.lib.data.UserDataUtil;
import com.cyberalaer.hybrid.R;
import com.cyberalaer.hybrid.base.BaseBindFragment;
import com.cyberalaer.hybrid.databinding.FragmentAuthInputInfoBinding;
import com.cyberalaer.hybrid.ui.dialog.DialogAuthInfoConfirm;
import com.cyberalaer.hybrid.ui.dialog.DialogPayConfirm;
import com.cyberalaer.hybrid.util.SimpleTextWatcher;
import com.cyberalaer.hybrid.util.ViewUtil;
import com.meiyou.mvp.MvpBinder;

import likly.dialogger.Dialogger;
import likly.dollar.$;

@MvpBinder(
)
public class InputInfoFragment extends BaseBindFragment<FragmentAuthInputInfoBinding> {

    UserData userData;
    String mOrderId;

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
        }
    }

    private void showAlipayInfoConfirm() {
        DialogAuthInfoConfirm dialogAuthInfoConfirm = new DialogAuthInfoConfirm(ViewUtil.getText(bindRoot.etName), ViewUtil.getText(bindRoot.etCard));
        dialogAuthInfoConfirm.setListener(new DialogAuthInfoConfirm.OnConfirmListener() {
            @Override
            public void onConfirmClick() {
                createPayOrder();
            }
        });
        Dialogger.newDialog(getContext()).holder(dialogAuthInfoConfirm)
                .gravity(Gravity.CENTER)
                .cancelable(false)
                .show();
    }

    private void showPayStateConfirm() {
        DialogPayConfirm dialogPayConfirm = new DialogPayConfirm();
        dialogPayConfirm.setListener(new DialogPayConfirm.OnConfirmListener() {
            @Override
            public void onConfirmClick() {
                queryPayState();
            }
        });
        Dialogger.newDialog(getContext()).holder(dialogPayConfirm)
                .gravity(Gravity.CENTER)
                .cancelable(false)
                .show();
    }

    private void queryPayState() {
        ApiUtil.apiService().queryPayState(userData.uuid, String.valueOf(userData.uid), userData.token, AppConfig.DIAMOND_CURRENCY, mOrderId,
                new Callback<OrderInfo>() {
                    @Override
                    public void onResponse(OrderInfo orderInfo) {
                        if (orderInfo != null && orderInfo.status == 1) {
                            // 支付成功
                            navigate(R.id.action_to_pay_success);
                        } else {
                            navigate(R.id.action_to_pay_failed);
                        }
                    }

                    @Override
                    public void onError(int code, String msg) {
                        $.toast().text(msg).show();
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

    private void createPayOrder() {
        if (userData == null)
            return;
        ApiUtil.apiService().createPayOrder(userData.uuid, String.valueOf(userData.uid), userData.token, AppConfig.DIAMOND_CURRENCY,
                ViewUtil.getText(bindRoot.etName), ViewUtil.getText(bindRoot.etCard),
                new Callback<String>() {
                    @Override
                    public void onResponse(String orderId) {
                        mOrderId = orderId;
                        showPayStateConfirm();
                        gotoPayWeb();
                    }

                    @Override
                    public void onError(int code, String msg) {
                        $.toast().text(msg).show();
                    }
                });
    }

    private void gotoPayWeb() {
        final String payWebUrl = AppConfig.ALIPAY_URL + mOrderId;
        Uri uri = Uri.parse(payWebUrl);
        Intent intent = new Intent(Intent.ACTION_VIEW, uri);
        startActivity(intent);
    }
}