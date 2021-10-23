package llc.metaversenetwork.app.ui.user;

import com.alaer.lib.api.bean.FruitBill;
import llc.metaversenetwork.app.R;
import llc.metaversenetwork.app.base.repeatview.BaseViewHolder;
import llc.metaversenetwork.app.databinding.ItemFruitBillBinding;
import llc.metaversenetwork.app.util.NumberUtils;

/**
 * 果实支出账单Adapter
 */
public class FruitBillAdapter extends BaseViewHolder<ItemFruitBillBinding, FruitBill> {

    @Override
    protected void onBindData(FruitBill bill) {
        bindRoot.setNumber(NumberUtils.instance());
        bindRoot.setBill(bill);
        bindRoot.executePendingBindings();
    }

    @Override
    protected int getViewHolderLayout() {
        return R.layout.item_fruit_bill;
    }

}
