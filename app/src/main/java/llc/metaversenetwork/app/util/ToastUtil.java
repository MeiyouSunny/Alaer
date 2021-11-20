package llc.metaversenetwork.app.util;

import android.content.Context;
import android.widget.Toast;

/**
 * Created by HuangJW on 2021/11/20 16:18.
 * Mail: huangjingwei@gwm.cn
 * Powered by GDC
 */
public class ToastUtil {

    private static Context mContext;

    public static void refreshContext(Context context) {
        mContext = context;
    }

    public static Toast text(int stringResId) {
        return Toast.makeText(mContext, stringResId, Toast.LENGTH_SHORT);
    }

    public static Toast text(String text) {
        return Toast.makeText(mContext, text, Toast.LENGTH_SHORT);
    }

}