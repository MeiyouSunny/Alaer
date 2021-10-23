package llc.metaversenetwork.app.ui.dialog;

import android.view.View;

import com.alaer.lib.api.bean.SharedUserDetail;
import llc.metaversenetwork.app.R;
import llc.metaversenetwork.app.base.BaseDialogHolder;
import llc.metaversenetwork.app.databinding.DialogSharedUserDetailBinding;
import llc.metaversenetwork.app.util.NumberUtils;

public class DialogUserDetail extends BaseDialogHolder<DialogSharedUserDetailBinding> {

    SharedUserDetail userDetail;

    public DialogUserDetail(SharedUserDetail userDetail) {
        super(R.layout.dialog_shared_user_detail);
        this.userDetail = userDetail;
    }

    @Override
    public void onViewCreated(View view) {
        super.onViewCreated(view);
        bindRoot.setNumber(NumberUtils.instance());
        bindRoot.setData(userDetail);
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
