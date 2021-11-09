package llc.metaversenetwork.app.util;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.widget.ImageView;
import android.widget.TextView;

import com.alaer.lib.api.ApiUtil;
import com.alaer.lib.api.AppConfig;
import com.alaer.lib.api.Callback;
import com.alaer.lib.api.bean.TeamDetail;
import com.alaer.lib.api.bean.UserData;
import com.alaer.lib.data.UserDataUtil;
import com.bumptech.glide.Glide;

import java.io.Serializable;

import likly.dollar.$;
import llc.metaversenetwork.app.R;
import llc.metaversenetwork.app.ui.auth.AuthFailedActivity;
import llc.metaversenetwork.app.ui.auth.AuthSuccessActivity;
import llc.metaversenetwork.app.ui.auth.AuthingActivity;
import llc.metaversenetwork.app.ui.government.RealNameAuthActivity;

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
//        UdeskSDKManager.getInstance().initApiKey(context.getApplicationContext(), AppConfig.UDESK_APP_DOMAIN,
//                AppConfig.UDESK_APP_SECRETKEY, AppConfig.UDESK_APP_ID);
//        final String sdkToken = UUID.randomUUID().toString();
//        UdeskSDKManager.getInstance().entryChat(context.getApplicationContext(), UdeskConfig.createDefualt(), sdkToken);
        $.toast().text(R.string.will_open_soon).show();
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

    public static void gotoAuthPage(Context context) {
        UserData userData = UserDataUtil.instance().getUserData();
        if (userData == null)
            return;
        ApiUtil.apiService().getTeamDetailInfo(userData.uuid, String.valueOf(userData.uid), userData.token, AppConfig.DIAMOND_CURRENCY,
                new Callback<TeamDetail>() {
                    @Override
                    public void onResponse(TeamDetail teamDetail) {
                        UserDataUtil.instance().saveTeamDetailInfo(teamDetail);

                        if (UserDataUtil.instance().isAuthed()) {
                            ViewUtil.gotoActivity(context, AuthSuccessActivity.class);
                        } else if (UserDataUtil.instance().isAuthing()) {
                            ViewUtil.gotoActivity(context, AuthingActivity.class);
                        } else if (UserDataUtil.instance().isAuthFailed()) {
                            ViewUtil.gotoActivity(context, AuthFailedActivity.class);
                        } else {
                            ViewUtil.gotoActivity(context, RealNameAuthActivity.class);
                        }
                    }
                });

    }

}
