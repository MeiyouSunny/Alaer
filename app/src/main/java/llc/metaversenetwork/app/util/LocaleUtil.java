package llc.metaversenetwork.app.util;

import android.content.Context;
import android.text.TextUtils;

import com.alaer.lib.api.AppConfig;

/**
 * Created by HuangJW on 2021/10/28 20:19.
 * Mail: 499655607@qq.com
 * Powered by Vcolco
 */
public class LocaleUtil {

    public static boolean isEnglish;

    public static void init(Context context) {
        final String language = context.getResources().getConfiguration().locale.getLanguage();
        isEnglish = TextUtils.equals("en", language);
        AppConfig.init(isEnglish);
    }

}
