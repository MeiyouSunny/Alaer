package com.cyberalaer.hybrid.ui.setting;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.text.TextUtils;
import android.util.Log;

import com.alaer.lib.api.bean.UpdateInfo;
import com.alaer.lib.event.Event;
import com.alaer.lib.event.EventUtil;
import com.cyberalaer.hybrid.BuildConfig;
import com.liulishuo.filedownloader.BaseDownloadTask;
import com.liulishuo.filedownloader.FileDownloadListener;
import com.liulishuo.filedownloader.FileDownloader;

import java.io.File;

import androidx.core.content.FileProvider;
import de.greenrobot.event.EventBus;
import de.greenrobot.event.Subscribe;
import de.greenrobot.event.ThreadMode;
import likly.dollar.$;

public class AppUpgradeManager {

    private Context mContext;
    private File mApkFile;
    private DownloadProgressListener mDownloadProgressListener;

    public AppUpgradeManager(Context context) {
        this.mContext = context;
        FileDownloader.setup(context);
        EventBus.getDefault().register(this);
    }

    @Subscribe(threadMode = ThreadMode.MainThread)
    public void onEvent(Event event) {
        if (EventUtil.isInstallApk(event)) {
            installApk(mApkFile);
        }
    }

    public void startDownloadApk(final UpdateInfo updateVersion) {
        String url = updateVersion.appUrl;
        if (TextUtils.isEmpty(url)) return;
        String saveName = url.substring(url.lastIndexOf('/') + 1);
        String savePath = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS).getAbsolutePath() + File.separator + saveName;
        deleteApkIfExist(savePath);
        FileDownloader.getImpl().create(url).setPath(savePath)
                .setListener(new FileDownloadListener() {
                    @Override
                    protected void pending(BaseDownloadTask task, int soFarBytes, int totalBytes) {

                    }

                    @Override
                    protected void progress(BaseDownloadTask task, int soFarBytes, int totalBytes) {
                        if (mDownloadProgressListener != null) {
                            int progress = (int) (((float) soFarBytes / totalBytes) * 100);
                            mDownloadProgressListener.downloadProgress(progress, totalBytes);
                            Log.e("DownloadProgress", "Progress:" + (int) progress);
                        }
                    }

                    @Override
                    protected void completed(BaseDownloadTask task) {
                        if (mDownloadProgressListener != null) {
                            mDownloadProgressListener.downloadSuccess();
                            Log.e("DownloadProgress", "downloadSuccess");
                        }
                        mApkFile = new File(task.getPath());
                        startInstallApk(mApkFile);
                    }

                    @Override
                    protected void paused(BaseDownloadTask task, int soFarBytes, int totalBytes) {

                    }

                    @Override
                    protected void error(BaseDownloadTask task, Throwable e) {

                    }

                    @Override
                    protected void warn(BaseDownloadTask task) {

                    }
                }).start();

    }


    private void deleteApkIfExist(String apkPath) {
        File file = new File(apkPath);
        if (file.exists()) file.delete();
    }

    private static final long INTERVAL_TIME_HINT = 0L;

    public boolean shouldHintAppUpgrade() {
        long upgradeTs = $.config().getLong("upgradeTimestamp", 0L);
        return (System.currentTimeMillis() - upgradeTs > INTERVAL_TIME_HINT) ? true : false;
    }

    private void startInstallApk(File file) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            boolean canRequestPackageInstalls = mContext.getPackageManager().canRequestPackageInstalls();
            if (!canRequestPackageInstalls) {
                EventUtil.sendInstallRequestPermission();
                return;
            }
        }
        installApk(file);
    }

    private void installApk(File file) {
        if (file == null) return;
        Uri uri;
        Intent intentInstall = new Intent();
        intentInstall.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        intentInstall.setAction(Intent.ACTION_VIEW);
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.N) {
            uri = Uri.fromFile(file);
        } else { // Android 7.0 以上
            uri = FileProvider.getUriForFile(mContext, BuildConfig.APPLICATION_ID + ".fileprovider", file);
            intentInstall.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION | Intent.FLAG_GRANT_WRITE_URI_PERMISSION);
        }

        intentInstall.setDataAndType(uri, "application/vnd.android.package-archive");
        mContext.startActivity(intentInstall);
    }

    public void setDownloadProgressListener(DownloadProgressListener downloadProgressListener) {
        this.mDownloadProgressListener = downloadProgressListener;
    }

    public interface DownloadProgressListener {
        void downloadProgress(int progress, int totalSize);

        void downloadSuccess();
    }

}
