package com.cyberalaer.hybrid.ui.setting;

import android.view.Gravity;
import android.view.View;

import com.alaer.lib.api.ApiUtil;
import com.alaer.lib.api.AppConfig;
import com.alaer.lib.api.Callback;
import com.alaer.lib.api.bean.UpdateInfo;
import com.cyberalaer.hybrid.R;
import com.cyberalaer.hybrid.base.BaseTitleActivity;
import com.cyberalaer.hybrid.databinding.ActivityAboutBinding;
import com.cyberalaer.hybrid.ui.dialog.DialogDownloadApk;
import com.cyberalaer.hybrid.ui.dialog.DialogNewVersion;
import com.cyberalaer.hybrid.ui.webpage.WebPageActivity;
import com.cyberalaer.hybrid.util.SettingUtil;

import likly.dialogger.Dialogger;

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
        initData();
    }

    private void initData() {
        bindRoot.version.setText(SettingUtil.getVersionName(getApplicationContext()));
    }

    @Override
    public void click(View view) {
        switch (view.getId()) {
            case R.id.userAgreement:
                WebPageActivity.start(this, AppConfig.USER_AGREEMENT, R.string.user_agreement);
                break;
            case R.id.update:
                checkUpdate();
                break;
        }
    }

    private void checkUpdate() {
        ApiUtil.apiService().checkUpdate(1100,
                new Callback<UpdateInfo>() {
                    @Override
                    public void onResponse(UpdateInfo updateInfo) {
                        super.onResponse(updateInfo);
                        if (updateInfo != null) {
                            showNewVersionDialog(updateInfo);
                        }
                    }

                    @Override
                    public void onError(int code, String msg) {
                        super.onError(code, msg);
                    }
                });
    }

    private void showNewVersionDialog(UpdateInfo updateInfo) {
        DialogNewVersion dialog = new DialogNewVersion(updateInfo);
        dialog.setListener(() -> startDownload(updateInfo));
        Dialogger.newDialog(this).holder(dialog)
                .gravity(Gravity.CENTER).cancelable(false).show();
    }

    private void startDownload(UpdateInfo updateInfo) {
        DialogDownloadApk dialogDownloadApk = new DialogDownloadApk();
        Dialogger.newDialog(this).holder(dialogDownloadApk)
                .gravity(Gravity.CENTER).cancelable(false).show();

        AppUpgradeManager upgradeManager = new AppUpgradeManager(this);
        upgradeManager.setDownloadProgressListener(dialogDownloadApk);
        upgradeManager.startDownloadApk(updateInfo);
    }

}