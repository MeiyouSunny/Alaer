package com.cyberalaer.hybrid.ui.auth;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;

import com.alaer.lib.api.ApiUtil;
import com.alaer.lib.api.AppConfig;
import com.alaer.lib.api.Callback;
import com.alaer.lib.api.bean.UserData;
import com.alaer.lib.data.UserDataUtil;
import com.cyberalaer.hybrid.R;
import com.cyberalaer.hybrid.base.BaseBindFragment;
import com.cyberalaer.hybrid.databinding.FragmentAuthInputInfoBinding;
import com.cyberalaer.hybrid.util.SimpleTextWatcher;
import com.cyberalaer.hybrid.util.ViewUtil;
import com.meiyou.mvp.MvpBinder;

import likly.dollar.$;

@MvpBinder(
)
public class InputInfoFragment extends BaseBindFragment<FragmentAuthInputInfoBinding> {

    UserData userData;

    @Override
    public int initLayoutResId() {
        return R.layout.fragment_auth_input_info;
    }

    @Override
    public void click(View view) {
        switch (view.getId()) {
            case R.id.next:
                createPayOrder();
                break;
        }
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
                        if (!TextUtils.isEmpty(orderId)) {
                            goConfirmPage(orderId);
                        }
                    }

                    @Override
                    public void onError(int code, String msg) {
                        $.toast().text(msg).show();
                    }
                });
    }

    private void goConfirmPage(String orderId) {
        Bundle data = new Bundle();
        data.putString("orderId", orderId);
        navigate(R.id.action_to_pay_confirm, data);
    }

}