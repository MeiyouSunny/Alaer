package llc.metaversenetwork.app.ui.setting;

import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.provider.Settings;
import android.view.View;

import com.alaer.lib.data.UserDataUtil;
import com.alaer.lib.event.Event;
import com.alaer.lib.event.EventUtil;

import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import de.greenrobot.event.EventBus;
import de.greenrobot.event.Subscribe;
import de.greenrobot.event.ThreadMode;
import likly.dollar.$;
import llc.metaversenetwork.app.R;
import llc.metaversenetwork.app.base.BaseTitleActivity;
import llc.metaversenetwork.app.databinding.ActivityAboutBinding;
import llc.metaversenetwork.app.ui.home.HomeActivity;
import llc.metaversenetwork.app.ui.webpage.WebPageActivity;
import llc.metaversenetwork.app.util.SettingUtil;

/**
 * 关于
 */
public class AboutActivity extends BaseTitleActivity<ActivityAboutBinding> {

    @Override
    protected int titleResId() {
        return R.string.about;
    }

    @Override
    protected int layoutId() {
        return R.layout.activity_about;
    }

    @Override
    public void onViewCreated() {
        super.onViewCreated();
        EventBus.getDefault().register(this);
        initData();
    }

    private void initData() {
        bindRoot.version.setText(SettingUtil.getVersionName(getApplicationContext()));
    }

    @Override
    public void click(View view) {
        switch (view.getId()) {
            case R.id.userAgreement:
                WebPageActivity.start(this, getString(R.string.user_rule_url), R.string.user_agreement);
                break;
            case R.id.mncAgreement:
                WebPageActivity.start(this, getString(R.string.mnc_service_disclaimer_url), R.string.mnc_service_disclaimer);
                break;
            case R.id.update:
                checkUpdate();
                break;
        }
    }

    private void checkUpdate() {
        new AppUpgradeManager(this).checkUpdate(true);
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Subscribe(threadMode = ThreadMode.MainThread)
    public void onEvent(Event event) {
        if (EventUtil.isInstallRequestPermission(event) && event.data == this) {
            requestInstallApkPermission();
        } else if (EventUtil.isTokenInvalid(event)) {
            UserDataUtil.instance().clearUserDatas();
            UserDataUtil.instance().setTokenInvalid(true);
            Intent intent = new Intent(this, HomeActivity.class);
            startActivity(intent);
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    private void requestInstallApkPermission() {
        boolean haveInstallPermission = getPackageManager().canRequestPackageInstalls();
        if (!haveInstallPermission) {
            Uri packageURI = Uri.parse("package:" + getPackageName());
            Intent intent = new Intent(Settings.ACTION_MANAGE_UNKNOWN_APP_SOURCES, packageURI);
            startActivityForResult(intent, REQUEST_INSTALL);

        }
    }

    private final int REQUEST_INSTALL = 11;

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_INSTALL) {
            if (resultCode == RESULT_OK)
                EventUtil.sendInstallApk();
            else
                $.toast().text("授权失败!").show();
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }

}