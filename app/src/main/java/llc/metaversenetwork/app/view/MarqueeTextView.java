package llc.metaversenetwork.app.view;

import android.content.Context;
import android.util.AttributeSet;

import androidx.appcompat.widget.AppCompatTextView;

/**
 * Created by HuangJW on 2021/11/9 0:30.
 * Mail: huangjingwei@gwm.cn
 * Powered by GDC
 */
public class MarqueeTextView extends AppCompatTextView {
    private String empty_char = "                                                     ";

    public MarqueeTextView(Context context) {
        this(context, null);
    }

    public MarqueeTextView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public MarqueeTextView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    public boolean isSelected() {
        return true;
    }

    @Override
    public boolean isFocused() {
        return true;
    }

    @Override
    public void setText(CharSequence text, BufferType type) {
        super.setText(text + empty_char, type);
    }
}