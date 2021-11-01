package llc.metaversenetwork.app.util;

import android.content.Context;
import android.graphics.Typeface;

import java.lang.reflect.Field;

/**
 * Created by HuangJW on 2021/10/30 22:20.
 * Mail: 499655607@qq.com
 * Powered by Vcolco
 */
public class FontUtil {

    public static void changeDefaultFont(Context context) {
        try{
            Typeface newTypeface = Typeface.createFromAsset(context.getAssets(), "font/HKLST-W7.TTF");
            Field field = Typeface.class.getDeclaredField("SERIF");
            field.setAccessible(true);
            field.set(null, newTypeface);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
