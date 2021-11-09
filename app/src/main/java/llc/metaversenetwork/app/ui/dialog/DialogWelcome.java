package llc.metaversenetwork.app.ui.dialog;

import android.view.View;

import cn.jzvd.Jzvd;
import cn.jzvd.JzvdStd;
import llc.metaversenetwork.app.R;
import llc.metaversenetwork.app.base.BaseDialogHolder;
import llc.metaversenetwork.app.databinding.DialogWelcomeBinding;
import llc.metaversenetwork.app.view.JZMediaSystemAssertFolder;

public class DialogWelcome extends BaseDialogHolder<DialogWelcomeBinding> {

    public DialogWelcome() {
        super(R.layout.dialog_welcome);
    }

    @Override
    public void onViewCreated(View view) {
        super.onViewCreated(view);

        bindRoot.player.fullscreenButton.setVisibility(View.GONE);
        bindRoot.player.posterImageView.setImageResource(R.drawable.img_welcome_dialog_video);
        bindRoot.player.setUp("welcom.mp4", "", JzvdStd.SCREEN_NORMAL, JZMediaSystemAssertFolder.class);
    }

    @Override
    public void click(View view) {
        switch (view.getId()) {
            case R.id.close:
                Jzvd.releaseAllVideos();
                dismiss();
                break;
        }
    }

}
