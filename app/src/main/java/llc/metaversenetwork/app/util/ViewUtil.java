package llc.metaversenetwork.app.util;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.widget.ImageView;
import android.widget.TextView;

import com.alaer.lib.api.AppConfig;
import com.bumptech.glide.Glide;

import java.io.Serializable;
import java.util.UUID;

import cn.udesk.UdeskSDKManager;
import cn.udesk.config.UdeskConfig;

public class ViewUtil {

    public static void gotoActivity(Context context, Class<? extends Activity> activityDes) {
        context.startActivity(new Intent(context, activityDes));
    }

    public static void gotoActivity(Context context, Class<? extends Activity> activityDes, Bundle bundle) {
        Intent intent = new Intent(context, activityDes);
        intent.putExtras(bundle);
        context.startActivity(intent);
    }

    public static void gotoActivity(Context context, Class<? extends Activity> activityDes, String key, Serializable value) {
        Intent intent = new Intent(context, activityDes);
        intent.putExtra(key, value);
        context.startActivity(intent);
    }

    public static void gotoCustomerService(Context context) {
        UdeskSDKManager.getInstance().initApiKey(context.getApplicationContext(), AppConfig.UDESK_APP_DOMAIN,
                AppConfig.UDESK_APP_SECRETKEY, AppConfig.UDESK_APP_ID);
        final String sdkToken = UUID.randomUUID().toString();
        UdeskSDKManager.getInstance().entryChat(context.getApplicationContext(), UdeskConfig.createDefualt(), sdkToken);
    }

    public static String getText(TextView textView) {
        if (textView == null)
            return "";
        return textView.getText().toString().trim();
    }

    public static void setText(TextView textView, String text) {
        if (textView == null || TextUtils.isEmpty(text))
            return;
        textView.setText(text);
    }

    public static void showImage(Context context, ImageView imageView, String imageUrl) {
        if (imageView == null || TextUtils.isEmpty(imageUrl))
            return;
        Glide.with(context)
                .load(imageUrl)
                .dontAnimate()
                .into(imageView);
    }

    public static void showImage(Context context, ImageView imageView, String imageUrl, int defaultIcon) {
        if (imageView == null || TextUtils.isEmpty(imageUrl))
            return;
        Glide.with(context)
                .load(imageUrl)
                .error(defaultIcon)
                .placeholder(defaultIcon)
                .dontAnimate()
                .into(imageView);
    }

}
