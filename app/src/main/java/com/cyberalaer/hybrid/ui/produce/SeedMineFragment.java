package com.cyberalaer.hybrid.ui.produce;

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
import com.meiyou.mvp.MvpBinder;

import java.util.List;

import likly.view.repeat.RepeatView;

/**
 * 种子商店:我的树苗
 */
@MvpBinder(
)
public class SeedMineFragment extends BaseBindFragment<FragmentSeedMineListBinding> implements RepeatView.OnRetryListener {

    public static SeedMineFragment newInstance() {
        SeedMineFragment fragment = new SeedMineFragment();
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
        bindRoot.repeatView.getRecyclerView().setPaddingRelative(0, 32, 0, 0);
        bindRoot.repeatView.getRecyclerView().setClipToPadding(false);

        if (!CollectionUtils.isEmpty(data)) {
            bindRoot.repeatView.viewManager().bind(data);
            bindRoot.repeatView.layoutAdapterManager().showRepeatView();
        } else {
            bindRoot.repeatView.layoutAdapterManager().showRetryView();
            bindRoot.repeatView.onRetry(this);
        }

    }

    @Override
    public void onRetry() {
        ((SeedStoreActivity) getActivity()).showPage(1);
    }

}