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
import com.cyberalaer.hybrid.databinding.FragmentProduceListBinding;
import com.cyberalaer.hybrid.ui.dialog.DialogBuySeedSuccess;
import com.cyberalaer.hybrid.view.GradViewItemDecoration;

import java.util.List;

import likly.dialogger.Dialogger;
import likly.dollar.$;

/**
 * 种子商店:种子商店
 */
public class SeedStoreFragment extends BaseBindFragment<FragmentProduceListBinding> {

    private boolean claimNewbieMiner;
    private AdapterSeedStore adapter;

    public static SeedStoreFragment newInstance(boolean claimNewbieMiner) {
        SeedStoreFragment fragment = new SeedStoreFragment();
        Bundle bundle = new Bundle();
        bundle.putBoolean("claimNewbieMiner", claimNewbieMiner);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public int initLayoutResId() {
        return R.layout.fragment_produce_list;
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
        if (adapter == null) {
            adapter = new AdapterSeedStore(data, claimNewbieMiner, mHandler);
            bindRoot.produceList.addItemDecoration(new GradViewItemDecoration(getContext(), 4));
            bindRoot.produceList.setAdapter(adapter);
        } else {
            adapter.setmData(data);
        }
    }

    private AdapterSeedStore.OnBuySeedHandler mHandler = seed -> buySeed(seed);

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
                        adapter.notifyDataSetChanged();

                        showSuccessDialog(seed);
                    }

                    @Override
                    public void onError(int code, String msg) {
                        $.toast().text(msg).show();
                    }
                });
    }

    private void showSuccessDialog(SeedStore seed) {
        final String seedName = getString(new SeedDataUtil(getResources()).getSeedName(seed.type));
        DialogBuySeedSuccess dialog = new DialogBuySeedSuccess(seedName);
        dialog.setListener(() -> lookMySeeds());
        Dialogger.newDialog(getActivity()).holder(dialog)
                .gravity(Gravity.CENTER).cancelable(false).show();
    }

    private void lookMySeeds() {
        // 跳转到我的种子
        if (getActivity() instanceof SeedStoreActivity) {
            ((SeedStoreActivity) getActivity()).bindRoot.viewPager.setCurrentItem(0, false);
            mUIHandler.postAtTime(() -> ((SeedStoreActivity) getActivity()).refreshMySeeds(), 500);
        }

    }

    private Handler mUIHandler = new Handler();

}