package llc.metaversenetwork.app.ui.dialog;

import android.view.View;

import llc.metaversenetwork.app.R;
import llc.metaversenetwork.app.base.BaseDialogHolder;
import llc.metaversenetwork.app.databinding.DialogDownloadApkBinding;
import llc.metaversenetwork.app.ui.setting.AppUpgradeManager;

public class DialogDownloadApk extends BaseDialogHolder<DialogDownloadApkBinding> implements AppUpgradeManager.DownloadProgressListener {

    public DialogDownloadApk() {
        super(R.layout.dialog_download_apk);
    }

    @Override
    public void onViewCreated(View view) {
        super.onViewCreated(view);
    }

    @Override
    public void click(View view) {
        switch (view.getId()) {
            case R.id.cancel:
                dismiss();
                break;
            case R.id.confirm:
                dismiss();
                break;
        }
    }

    @Override
    public void downloadProgress(int progress, int totalSize) {
        bindRoot.downloadedSize.setText(getContext().getString(R.string.downloaded_size, progress) + "%");
        bindRoot.progress.setProgress(progress);
    }

    @Override
    public void downloadSuccess() {
        dismiss();
    }

}
