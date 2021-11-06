package llc.metaversenetwork.app.ui.wallet;

import com.alaer.lib.api.bean.AssetsTotalInfo;

import llc.metaversenetwork.app.R;
import llc.metaversenetwork.app.base.repeatview.BaseViewHolder;
import llc.metaversenetwork.app.databinding.ItemWalletBinding;

/**
 * 钱包Adapter
 */
public class WalletAdapter extends BaseViewHolder<ItemWalletBinding, AssetsTotalInfo.Assets> {

    @Override
    protected void onBindData(AssetsTotalInfo.Assets assets) {
        bindRoot.setAssets(assets);
        bindRoot.icon.setImageResource(assets.iconResId);
        bindRoot.executePendingBindings();
    }

    @Override
    protected int getViewHolderLayout() {
        return R.layout.item_wallet;
    }

}
