package llc.metaversenetwork.app.ui.dialog;

import android.view.View;

import com.alaer.lib.api.bean.ActiveDetail;
import llc.metaversenetwork.app.R;
import llc.metaversenetwork.app.base.BaseDialogHolder;
import llc.metaversenetwork.app.databinding.DialogAvtivityDetailBinding;
import llc.metaversenetwork.app.util.NumberUtils;

public class DialogActiveDetail extends BaseDialogHolder<DialogAvtivityDetailBinding> {

    ActiveDetail detail;

    public DialogActiveDetail(ActiveDetail detail) {
        super(R.layout.dialog_avtivity_detail);
        this.detail = detail;
    }

    @Override
    public void onViewCreated(View view) {
        super.onViewCreated(view);
        bindRoot.setNumber(NumberUtils.instance());
        bindRoot.setData(detail);
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
