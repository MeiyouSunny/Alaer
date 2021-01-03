package com.cyberalaer.hybrid.ui.user;

import android.text.TextUtils;
import android.view.View;

import com.cyberalaer.hybrid.R;
import com.cyberalaer.hybrid.base.BaseTitleActivity;
import com.cyberalaer.hybrid.databinding.ActivityWechatNoSetBinding;
import com.cyberalaer.hybrid.util.ViewUtil;

import likly.dollar.$;

/**
 * 微信号设置
 */
public class WechatNoSetActivity extends BaseTitleActivity<ActivityWechatNoSetBinding> {

    @Override
    protected int titleResId() {
        return R.string.wechat_no_set;
    }

    @Override
    protected int layoutId() {
        return R.layout.activity_wechat_no_set;
    }

    @Override
    public void onViewCreated() {
        super.onViewCreated();
    }

    @Override
    public void click(View view) {
        switch (view.getId()) {
            case R.id.icClear:
                bindRoot.etWechat.setText("");
                break;
            case R.id.submit:
                setWecahtNo();
                break;
        }
    }

    private void setWecahtNo() {
        final String wechatNo = ViewUtil.getText(bindRoot.etWechat);
        if (TextUtils.isEmpty(wechatNo)) {
            $.toast().text(R.string.pls_input_wechat_no).show();
            return;
        }

    }

}