package com.cyberalaer.hybrid.base;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.cyberalaer.hybrid.R;

import androidx.databinding.ViewDataBinding;

public abstract class BaseTitleActivity<T extends ViewDataBinding> extends BaseViewBindActivity<T> implements View.OnClickListener, TitleControl {

    private TextView title;
    private ImageView titleLeft, titleRight;

    @Override
    public void onViewCreated() {
        initTitleBar();
    }

    private void initTitleBar() {
        title = bindRoot.getRoot().findViewById(R.id.title);
        titleLeft = bindRoot.getRoot().findViewById(R.id.title_left);
        titleRight = bindRoot.getRoot().findViewById(R.id.title_right);

        if (titleResId() != -1)
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
        titleRight.setOnClickListener((view) -> onRightClick());
    }

    protected void onRightClick() {
    }

    @Override
    public void setTitleLeftIcon(int icon) {
        titleLeft.setImageResource(icon);
    }

    @Override
    public void setTitleRightVisible(boolean visible) {
        titleRight.setVisibility(View.VISIBLE);
    }

}
