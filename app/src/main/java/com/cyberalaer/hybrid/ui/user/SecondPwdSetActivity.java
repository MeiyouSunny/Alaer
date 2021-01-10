package com.cyberalaer.hybrid.ui.user;

import android.view.View;

import com.cyberalaer.hybrid.R;
import com.cyberalaer.hybrid.base.BaseTitleActivity;
import com.cyberalaer.hybrid.databinding.ActivitySecondPwdSetBinding;

/**
 * 设置二级密码
 */
public class SecondPwdSetActivity extends BaseTitleActivity<ActivitySecondPwdSetBinding> {

    @Override
    protected int titleResId() {
        return R.string.second_pwd;
    }

    @Override
    protected int layoutId() {
        return R.layout.activity_second_pwd_set;
    }

    @Override
    public void onViewCreated() {
        super.onViewCreated();
    }

    @Override
    public void click(View view) {
        switch (view.getId()) {
//            case R.id.icClear:
//                bindRoot.etWechat.setText("");
//                break;
//            case R.id.submit:
//                setWecahtNo();
//                break;
        }
    }

//    private void setWecahtNo() {
//        final String wechatNo = ViewUtil.getText(bindRoot.etWechat);
//        if (TextUtils.isEmpty(wechatNo)) {
//            $.toast().text(R.string.pls_input_wechat_no).show();
//            return;
//        }
//
//    }

}