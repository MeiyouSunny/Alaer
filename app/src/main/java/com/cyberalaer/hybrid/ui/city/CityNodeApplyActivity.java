package com.cyberalaer.hybrid.ui.city;

import android.os.Bundle;

import com.cyberalaer.hybrid.R;
import com.cyberalaer.hybrid.base.BaseTitleActivity;
import com.cyberalaer.hybrid.databinding.ActivityAuthBinding;
import com.meiyou.mvp.MvpBinder;

import androidx.annotation.Nullable;

/**
 * 城市节点申请页面
 */
@MvpBinder(
)
public class CityNodeApplyActivity extends BaseTitleActivity<ActivityAuthBinding> {

    @Override
    protected int titleResId() {
        return R.string.city_note_apply;
    }

    @Override
    protected int layoutId() {
        return R.layout.activity_city_note_apply;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

}