package llc.metaversenetwork.app.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import llc.metaversenetwork.app.R;

import androidx.annotation.Nullable;

/**
 * 功能项View: 功能图标 / 标题 / 下一步箭头
 */
public class ItemLayoutView extends RelativeLayout {

    public ItemLayoutView(Context context) {
        this(context, null);
    }

    public ItemLayoutView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        initAttr(attrs);
    }

    private void initAttr(AttributeSet attrs) {
        LayoutInflater.from(getContext()).inflate(R.layout.item_layout_view, this, true);
        ImageView mIcon = findViewById(R.id.icon);
        TextView mTitle = findViewById(R.id.title);
        final TypedArray typedArray = getContext().obtainStyledAttributes(attrs, R.styleable.ItemLayoutView);
        String title = typedArray.getString(R.styleable.ItemLayoutView_title);
        Drawable iconDrawable = typedArray.getDrawable(R.styleable.ItemLayoutView_icon);
        if (!TextUtils.isEmpty(title))
            mTitle.setText(title);
        if (iconDrawable != null)
            mIcon.setBackground(iconDrawable);
    }

}
