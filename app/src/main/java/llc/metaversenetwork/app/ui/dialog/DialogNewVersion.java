package llc.metaversenetwork.app.ui.dialog;

import android.view.View;

import com.alaer.lib.api.bean.UpdateInfo;

import llc.metaversenetwork.app.R;
import llc.metaversenetwork.app.base.BaseDialogHolder;
import llc.metaversenetwork.app.databinding.DialogNewVersionBinding;
import llc.metaversenetwork.app.ui.setting.AppUpgradeManager;
import llc.metaversenetwork.app.util.LocaleUtil;

public class DialogNewVersion extends BaseDialogHolder<DialogNewVersionBinding> implements AppUpgradeManager.DownloadProgressListener {

    UpdateInfo updateInfo;
    boolean forceUpdate;
    OnConfirmListener listener;

    public DialogNewVersion(UpdateInfo updateInfo, boolean forceUpdate) {
        super(R.layout.dialog_new_version);
        this.updateInfo = updateInfo;
        this.forceUpdate = forceUpdate;
    }

    @Override
    public void onViewCreated(View view) {
        super.onViewCreated(view);

        bindRoot.setInfo(updateInfo);
        bindRoot.executePendingBindings();
        bindRoot.title.setText(getContext().getString(R.string.new_version, updateInfo.nowVersion));
        bindRoot.content.setText(LocaleUtil.isDefaultLanguage() ? updateInfo.msgContent : updateInfo.msgContentEn);

//        if (forceUpdate) {
//            bindRoot.cancel.setVisibility(View.GONE);
//            bindRoot.divider.setVisibility(View.GONE);
//        }
    }

    public void setListener(OnConfirmListener listener) {
        this.listener = listener;
    }

    @Override
    public void click(View view) {
        switch (view.getId()) {
            case R.id.confirm:
                if (listener != null)
                    listener.onConfirmClick();
                bindRoot.confirm.setVisibility(View.GONE);
                bindRoot.progress.setVisibility(View.VISIBLE);
                bindRoot.progressBar.setVisibility(View.VISIBLE);
                break;
        }
    }

    @Override
    public void downloadProgress(int progress, int totalSize) {
        bindRoot.progress.setText(progress + "%");
        bindRoot.progressBar.setProgress(progress);
    }

    @Override
    public void downloadSuccess() {
        dismiss();
    }

    public interface OnConfirmListener {
        void onCancelClick();

        void onConfirmClick();
    }

}
