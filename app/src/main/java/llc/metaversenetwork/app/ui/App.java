package llc.metaversenetwork.app.ui;

import android.app.Application;
import android.content.Context;

import com.alaer.lib.api.HttpManager;
import com.alaer.lib.util.NetworkUtil;

import likly.dollar.$;
import llc.metaversenetwork.app.util.LocaleUtil;

public class App extends Application {

    public static Context mAppContext;

    @Override
    public void onCreate() {
        super.onCreate();
        mAppContext = getApplicationContext();

        $.initialize(this);
        HttpManager.initHttp(getApplicationContext());
        NetworkUtil.init(getApplicationContext());
        LocaleUtil.init(this);
    }

}
