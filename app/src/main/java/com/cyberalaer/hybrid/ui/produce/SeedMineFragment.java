package com.cyberalaer.hybrid.ui.produce;

import android.os.Bundle;

import com.alaer.lib.api.ApiUtil;
import com.alaer.lib.api.AppConfig;
import com.alaer.lib.api.Callback;
import com.alaer.lib.api.bean.SeedMine;
import com.alaer.lib.api.bean.UserData;
import com.alaer.lib.data.UserDataUtil;
import com.cyberalaer.hybrid.R;
import com.cyberalaer.hybrid.base.BaseBindFragment;
import com.cyberalaer.hybrid.databinding.FragmentSeedMineListBinding;
import com.cyberalaer.hybrid.util.CollectionUtils;
import com.cyberalaer.hybrid.view.GradViewItemDecoration;
import com.meiyou.mvp.MvpBinder;

import java.util.List;

import likly.view.repeat.RepeatView;

/**
 * 种子商店:我的树苗
 */
@MvpBinder(
)
public class SeedMineFragment extends BaseBindFragment<FragmentSeedMineListBinding> implements RepeatView.OnRetryListener {

    private boolean claimNewbieMiner;

    public static SeedMineFragment newInstance(boolean claimNewbieMiner) {
        SeedMineFragment fragment = new SeedMineFragment();
        Bundle bundle = new Bundle();
        bundle.putBoolean("claimNewbieMiner", claimNewbieMiner);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public int initLayoutResId() {
        return R.layout.fragment_seed_mine_list;
    }

    @Override
    public void onViewCreated() {
        super.onViewCreated();

        initData();
    }

    public void refresh() {
        initData();
    }

    private void initData() {
        claimNewbieMiner = getArguments().getBoolean("claimNewbieMiner");
        UserData userData = UserDataUtil.instance().getUserData();
        if (userData == null)
            return;
        ApiUtil.apiService().mySeeds(userData.uuid, String.valueOf(userData.uid), userData.token, AppConfig.DIAMOND_CURRENCY,
                1, 1, AppConfig.PAGE_SIZE_DEFAULT,
                new Callback<List<SeedMine>>() {
                    @Override
                    public void onResponse(List<SeedMine> data) {
                        showData(data);
                    }
                });
    }

    private void showData(List<SeedMine> data) {
//        bindRoot.repeatView.getRecyclerView().setPaddingRelative(0, 32, 0, 0);
        bindRoot.repeatView.getRecyclerView().setClipToPadding(false);
        bindRoot.repeatView.getRecyclerView().addItemDecoration(new GradViewItemDecoration(getContext(), 6));

        if (!CollectionUtils.isEmpty(data)) {
            bindRoot.repeatView.viewManager().bind(data);
            bindRoot.repeatView.layoutAdapterManager().showRepeatView();
        } else {
            if (claimNewbieMiner) {
                bindRoot.repeatView.layoutAdapterManager().showEmptyView();
            } else {
                bindRoot.repeatView.layoutAdapterManager().showRetryView();
                bindRoot.repeatView.onRetry(this);
            }
        }

    }

    @Override
    public void onRetry() {
        ((SeedStoreActivity) getActivity()).showPage(1);
    }

}