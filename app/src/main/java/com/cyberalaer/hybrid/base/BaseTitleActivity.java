package com.cyberalaer.hybrid.base;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.cyberalaer.hybrid.R;

import androidx.annotation.Nullable;
import androidx.databinding.ViewDataBinding;

public abstract class BaseTitleActivity<T extends ViewDataBinding> extends BaseViewBindActivity implements View.OnClickListener, TitleControl {

    private TextView title;
    private ImageView titleLeft, titleRight;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        title = bindRoot.getRoot().findViewById(R.id.title);
        titleLeft = bindRoot.getRoot().findViewById(R.id.title_left);
        titleRight = bindRoot.getRoot().findViewById(R.id.title_right);

        title.setText(titleResId());
        titleLeft.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.title_left) {
            onBackPressed();
        }
    }

    protected abstract int titleResId();

    @Override
    public void setTitleText(int text) {
        title.setText(text);
    }

    @Override
    public void setTitleText(String text) {
        title.setText(text);
    }

    @Override
    public void setTitleRightIcon(int icon) {
        titleRight.setImageResource(icon);
    }

    @Override
    public void setTitleRightVisible(boolean visible) {
        titleRight.setVisibility(View.VISIBLE);
    }

}
