package com.cyberalaer.hybrid.ui.produce;

import android.os.Bundle;
import android.os.Handler;
import android.view.Gravity;

import com.alaer.lib.api.ApiUtil;
import com.alaer.lib.api.AppConfig;
import com.alaer.lib.api.Callback;
import com.alaer.lib.api.bean.SeedStore;
import com.alaer.lib.api.bean.SeedStoreList;
import com.alaer.lib.api.bean.UserData;
import com.alaer.lib.data.UserDataUtil;
import com.cyberalaer.hybrid.R;
import com.cyberalaer.hybrid.base.BaseBindFragment;
import com.cyberalaer.hybrid.data.SeedDataUtil;
import com.cyberalaer.hybrid.databinding.FragmentSeedStoreListBinding;
import com.cyberalaer.hybrid.ui.dialog.DialogBuySeedSuccess;
import com.cyberalaer.hybrid.ui.dialog.DialogNotAuth;
import com.cyberalaer.hybrid.ui.government.RealNameAuthActivity;
import com.cyberalaer.hybrid.util.CollectionUtils;
import com.cyberalaer.hybrid.util.ViewUtil;
import com.cyberalaer.hybrid.view.GradViewItemDecoration;
import com.meiyou.mvp.MvpBinder;

import java.util.List;

import likly.dialogger.Dialogger;
import likly.dollar.$;
import likly.view.repeat.OnHolderClickListener;

/**
 * 种子商店:种子商店
 */
@MvpBinder(
)
public class SeedStoreFragment extends BaseBindFragment<FragmentSeedStoreListBinding> implements OnHolderClickListener<AdapterSeedStore> {

    private boolean claimNewbieMiner;

    public static SeedStoreFragment newInstance(boolean claimNewbieMiner) {
        SeedStoreFragment fragment = new SeedStoreFragment();
        Bundle bundle = new Bundle();
        bundle.putBoolean("claimNewbieMiner", claimNewbieMiner);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public int initLayoutResId() {
        return R.layout.fragment_seed_store_list;
    }

    @Override
    public void onViewCreated() {
        super.onViewCreated();

        initData();
    }

    private void initData() {
        claimNewbieMiner = getArguments().getBoolean("claimNewbieMiner");
        UserData userData = UserDataUtil.instance().getUserData();
        if (userData == null)
            return;
        ApiUtil.apiService().seedStore(userData.uuid, String.valueOf(userData.uid), userData.token, AppConfig.DIAMOND_CURRENCY,
                1, AppConfig.PAGE_SIZE_DEFAULT,
                new Callback<SeedStoreList>() {
                    @Override
                    public void onResponse(SeedStoreList data) {
                        super.onResponse(data);
                        showData(data.list);
                    }
                });
    }

    private void showData(List<SeedStore> data) {
//        bindRoot.repeatView.getRecyclerView().setPaddingRelative(0, 32, 0, 0);
        bindRoot.repeatView.getRecyclerView().setClipToPadding(false);
        bindRoot.repeatView.getRecyclerView().addItemDecoration(new GradViewItemDecoration(getContext(), 6));

        if (!CollectionUtils.isEmpty(data))
            bindRoot.repeatView.viewManager().bind(data);
        else
            bindRoot.repeatView.layoutAdapterManager().showEmptyView();

        bindRoot.repeatView.onClick(this);
    }

    // 领取种子
    private void buySeed(SeedStore seed) {
        // 已领取试种树苗
        if (claimNewbieMiner && seed.price == 0) {
            $.toast().text(R.string.has_get_try_seed).show();
            return;
        }
        if (seed.buyNum == seed.buyMax) {
            $.toast().text(R.string.seed_sold_out_all).show();
            return;
        }

        UserData userData = UserDataUtil.instance().getUserData();
        if (userData == null)
            return;

        ApiUtil.apiService().bugSeed(userData.uuid, String.valueOf(userData.uid), userData.token,
                AppConfig.DIAMOND_CURRENCY, String.valueOf(seed.id),
                new Callback<String>() {
                    @Override
                    public void onResponse(String response) {
                        // 刷新
                        seed.buyNum++;
                        bindRoot.repeatView.getAdapter().notifyDataSetChanged();
//                        adapter.notifyDataSetChanged();

                        showSuccessDialog(seed);

                        mUIHandler.postAtTime(() -> ((SeedStoreActivity) getActivity()).refreshMySeeds(), 800);
                    }

                    @Override
                    public void onError(int code, String msg) {
                        if (code == 403) {
                            showNotAuthDialog();
                        } else {
                            $.toast().text(msg).show();
                        }
                    }
                });
    }

    // 未实名认证提示
    private void showNotAuthDialog() {
        DialogNotAuth dialogNotAuth = new DialogNotAuth();
        dialogNotAuth.setListener(new DialogNotAuth.OnConfirmListener() {
            @Override
            public void onConfirmClick() {
                ViewUtil.gotoActivity(getContext(), RealNameAuthActivity.class);
            }
        });
        Dialogger.newDialog(getContext()).holder(dialogNotAuth)
                .gravity(Gravity.CENTER)
                .show();
    }

    private void showSuccessDialog(SeedStore seed) {
        final SeedDataUtil util = new SeedDataUtil(getResources());
        final String seedName = getString(util.getSeedName(seed.type));
        final String getType = getString(util.getBuySuccessTitle(seed.type));
        DialogBuySeedSuccess dialog = new DialogBuySeedSuccess(getType, seedName);
        dialog.setListener(() -> lookMySeeds());
        Dialogger.newDialog(getActivity()).holder(dialog)
                .gravity(Gravity.CENTER).cancelable(false).show();
    }

    private void lookMySeeds() {
        // 跳转到我的种子
        if (getActivity() instanceof SeedStoreActivity) {
            ((SeedStoreActivity) getActivity()).bindRoot.viewPager.setCurrentItem(0, false);
        }

    }

    private Handler mUIHandler = new Handler();

    @Override
    public void onHolderClick(AdapterSeedStore adapterSeedStore) {
        final SeedStore seed = adapterSeedStore.getData();
        buySeed(seed);
    }

}