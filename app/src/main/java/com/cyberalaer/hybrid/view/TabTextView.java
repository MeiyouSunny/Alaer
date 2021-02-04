package com.cyberalaer.hybrid.view;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.view.View;

import com.cyberalaer.hybrid.R;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

import androidx.annotation.IntDef;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatTextView;

public class TabTextView extends AppCompatTextView implements View.OnClickListener {
    private final int[] STATE_ICONS = new int[]{R.drawable.ic_tab_state_unselected, R.drawable.ic_tab_state_asc, R.drawable.ic_tab_state_desc};

    // 未选中
    private static final int UNSELECTED = 0;
    // 升序
    private static final int ORDER_ASC = 1;
    // 降序
    private static final int ORDER_DESC = 2;

    @IntDef({UNSELECTED, ORDER_ASC, ORDER_DESC})
    @Retention(RetentionPolicy.SOURCE)
    @interface STATE {
    }

    // 当前状态
    @STATE
    private int mState;
    // 类型名
    private String type;

    private StateChangedListener stateListener;

    public TabTextView(@NonNull Context context) {
        this(context, null);
    }

    public TabTextView(@NonNull Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public TabTextView(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        setState(UNSELECTED);
        setOnClickListener(this);
    }

    public void setState(@STATE int state) {
        mState = state;
        Drawable drawableRight = getResources().getDrawable(STATE_ICONS[state]);

        drawableRight.setBounds(0, 0, drawableRight.getMinimumWidth(), drawableRight.getMinimumHeight());
        setCompoundDrawables(null, null, drawableRight, null);

        int textColor = Color.parseColor(mState == UNSELECTED ? "#7E7E7E" : "#52C883");
        setTextColor(textColor);

        if (state != UNSELECTED && stateListener != null) {
            final String order = (mState == ORDER_ASC) ? "asc" : "desc";
            stateListener.onStateChanged(this, type, order);
        }
    }

    public void setUnselected() {
        setState(UNSELECTED);
    }

    public void setSelected() {
        setState(ORDER_ASC);
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    @Override
    public void onClick(View v) {
        changeState();
    }

    private void changeState() {
        switch (mState) {
            case UNSELECTED:
                setState(ORDER_ASC);
                break;
            case ORDER_ASC:
                setState(ORDER_DESC);
                break;
            case ORDER_DESC:
                setState(ORDER_ASC);
                break;
        }
    }

    public void setStateListener(StateChangedListener stateListener) {
        this.stateListener = stateListener;
    }

    public interface StateChangedListener {
        void onStateChanged(TabTextView tabView, String type, String order);
    }

}
