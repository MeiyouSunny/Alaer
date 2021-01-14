package com.cyberalaer.hybrid.ui.user;

import android.app.Activity;
import android.content.Intent;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.view.View;

import com.cyberalaer.hybrid.R;
import com.cyberalaer.hybrid.base.BaseTitleActivity;
import com.cyberalaer.hybrid.databinding.ActivityWechatNoSetBinding;
import com.cyberalaer.hybrid.util.SimpleTextWatcher;
import com.cyberalaer.hybrid.util.ViewUtil;

import androidx.annotation.IntDef;

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

    public static void start(Activity context, @TYPE int type) {
        Intent intent = new Intent(context, SetProfileActivity.class);
        Bundle bundle = new Bundle();
        bundle.putInt("type", type);
        intent.putExtras(bundle);
        context.startActivityForResult(intent, REQUST_CODE);
    }

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

        type = getIntent().getIntExtra("type", 0);

        final Resources resources = getResources();
        final String title = resources.getStringArray(R.array.set_profile_title)[type];
        final String profileDesc = resources.getStringArray(R.array.set_profile_desc)[type];
        final String intputHint = resources.getStringArray(R.array.set_profile_input_hint)[type];
        final String profileConsume = resources.getStringArray(R.array.set_profile_consume)[type];
        TypedArray typedArray = resources.obtainTypedArray(R.array.set_profile_icon);
        final int iconResId = typedArray.getResourceId(type, 0);
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
    }

    @Override
    public void click(View view) {
        switch (view.getId()) {
            case R.id.icClear:
                bindRoot.input.setText("");
                break;
            case R.id.submit:
                setWecahtNo();
                break;
        }
    }

    private void setWecahtNo() {

    }

}