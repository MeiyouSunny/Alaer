package llc.metaversenetwork.app.ui.dialog;

import android.view.View;

import llc.metaversenetwork.app.R;
import llc.metaversenetwork.app.base.BaseDialogHolder;
import llc.metaversenetwork.app.databinding.DialogWelcomeBinding;
import llc.metaversenetwork.app.util.ViewUtil;

public class DialogWelcome extends BaseDialogHolder<DialogWelcomeBinding> {

    public DialogWelcome() {
        super(R.layout.dialog_welcome);
    }

    @Override
    public void onViewCreated(View view) {
        super.onViewCreated(view);

        bindRoot.player.fullscreenButton.setVisibility(View.GONE);
        ViewUtil.showImage(getContext(), bindRoot.player.posterImageView,
                "https://t-app.linker.world/meta/image/welcome.jpg");
        bindRoot.player.setUp("https://t-app.linker.world/meta/video/official/welcome.mp4", "");
    }

    @Override
    public void click(View view) {
        switch (view.getId()) {
            case R.id.close:
                dismiss();
                break;
        }
    }

}
