package com.cyberalaer.hybrid.util;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.widget.TextView;

public class ViewUtil {

    public static void gotoActivity(Context context, Class<? extends Activity> activityDes) {
        context.startActivity(new Intent(context, activityDes));
    }

    public static String getText(TextView textView) {
        if (textView == null)
            return "";
        return textView.getText().toString().trim();
    }

}
