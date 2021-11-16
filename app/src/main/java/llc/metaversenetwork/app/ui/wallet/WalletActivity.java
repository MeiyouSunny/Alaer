package llc.metaversenetwork.app.ui.wallet;

import android.view.Gravity;
import android.view.View;

import com.alaer.lib.api.ApiUtil;
import com.alaer.lib.api.AppConfig;
import com.alaer.lib.api.Callback;
import com.alaer.lib.api.bean.AssetsTotalInfo;
import com.alaer.lib.api.bean.UserData;
import com.alaer.lib.data.UserDataUtil;

import java.util.List;

import likly.dialogger.Dialogger;
import likly.view.repeat.OnHolderClickListener;
import llc.metaversenetwork.app.R;
import llc.metaversenetwork.app.base.BaseTitleActivity;
import llc.metaversenetwork.app.databinding.ActivityWalletBinding;
import llc.metaversenetwork.app.ui.dialog.DialogNotAuth;
import llc.metaversenetwork.app.util.CollectionUtils;
import llc.metaversenetwork.app.util.NumberUtils;
import llc.metaversenetwork.app.util.ViewUtil;
import llc.metaversenetwork.app.util.WalletDataUtil;

/**
 * 我的钱包
 */
public class WalletActivity extends BaseTitleActivity<ActivityWalletBinding> implements OnHolderClickListener<WalletAdapter> {
    // USDT:4
    // MNC:173

    @Override
    protected int titleResId() {
        return R.string.my_wallet;
    }

    @Override
    protected int layoutId() {
        return R.layout.activity_wallet;
    }

    @Override
    public void onViewCreated() {
        super.onViewCreated();
    }

    @Override
    public void onResume() {
        super.onResume();
        getData();
    }

    private void getData() {
        UserData userData = UserDataUtil.instance().getUserData();
        if (userData == null)
            return;
        ApiUtil.apiService().queryTotalAssets(userData.uuid, String.valueOf(userData.uid), userData.token, AppConfig.DIAMOND_CURRENCY,
                1, 100, "CNY", AppConfig.DIAMOND_CURRENCY,
                new Callback<AssetsTotalInfo>() {
                    @Override
                    public void onResponse(AssetsTotalInfo assetsTotalInfo) {
                        if (assetsTotalInfo != null) {
                            bindRoot.total.setText(NumberUtils.instance().parseNumber(assetsTotalInfo.totalUsdt));
                            List<AssetsTotalInfo.Assets> list = WalletDataUtil.parseAssetsList(assetsTotalInfo);
                            showWalletList(list);
                        }
                    }
                });
    }

    private void showWalletList(List<AssetsTotalInfo.Assets> assets) {
        bindRoot.repeatView.onClick(this);

        if (CollectionUtils.isEmpty(assets))
            bindRoot.repeatView.layoutAdapterManager().showEmptyView();
        else
            bindRoot.repeatView.viewManager().bind(assets);
    }

    @Override
    public void onHolderClick(WalletAdapter walletAdapter) {
        AssetsTotalInfo.Assets assets = walletAdapter.getData();
        ViewUtil.gotoActivity(this, CurrencyDetailActivity.class, "asset", assets);
    }

    @Override
    public void click(View view) {
        switch (view.getId()) {
            case R.id.recharge:
                ViewUtil.gotoActivity(this, RechargeActivity.class);
                break;
            case R.id.withdrawal:
                if (!UserDataUtil.instance().isAuthed()) {
                    showNotAuthDialog();
                } else {
                    ViewUtil.gotoActivity(this, WithdrawalActivity.class);
                }
                break;
        }
    }

    // 未实名认证提示
    private void showNotAuthDialog() {
        DialogNotAuth dialogNotAuth = new DialogNotAuth();
        dialogNotAuth.setListener(new DialogNotAuth.OnConfirmListener() {
            @Override
            public void onConfirmClick() {
                ViewUtil.gotoAuthPage(getContext());
            }
        });
        Dialogger.newDialog(getContext()).holder(dialogNotAuth)
                .gravity(Gravity.CENTER)
                .show();
    }

}