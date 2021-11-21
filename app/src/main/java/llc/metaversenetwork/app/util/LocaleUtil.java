package llc.metaversenetwork.app.util;

import android.app.Activity;
import android.content.Context;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.text.TextUtils;
import android.util.DisplayMetrics;

import java.util.Locale;

import likly.dollar.$;
import llc.metaversenetwork.app.ui.App;

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
    }

    public static Context restoreLanguage(Context context) {
        boolean isDefault = $.config().getBoolean("defaultLanguage", true);
        return changeLanguage(context, isDefault, true);
    }

    public static Context changeLanguage(Context context, boolean isDefaultLanguage, boolean isHomePage) {
        Locale locale = Locale.TRADITIONAL_CHINESE;
        if (!isDefaultLanguage)
            locale = Locale.ENGLISH;

        Resources resources = context.getResources();
        DisplayMetrics dm = resources.getDisplayMetrics();
        Configuration config = resources.getConfiguration();
        config.setLocale(locale);
        resources.updateConfiguration(config, dm);

//        if (isHomePage) {
//            Intent intent = new Intent(context, HomeActivity.class);
//        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
//            context.startActivity(intent);
        if (context instanceof Activity) {
            ((Activity) context).recreate();
//            ((Activity) context).overridePendingTransition(0, 0);
        }
//        }

        context = context.createConfigurationContext(config);
        $.initialize(App.mApp);

        return context;
    }

    public static boolean isDefaultLanguage() {
        return $.config().getBoolean("defaultLanguage", true);
    }

}
