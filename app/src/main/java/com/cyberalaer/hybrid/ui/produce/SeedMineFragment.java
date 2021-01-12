package com.cyberalaer.hybrid.ui.produce;

import com.alaer.lib.api.ApiUtil;
import com.alaer.lib.api.AppConfig;
import com.alaer.lib.api.Callback;
import com.alaer.lib.api.bean.SeedMine;
import com.alaer.lib.api.bean.UserData;
import com.alaer.lib.data.UserDataUtil;
import com.cyberalaer.hybrid.R;
import com.cyberalaer.hybrid.base.BaseBindFragment;
import com.cyberalaer.hybrid.databinding.FragmentProduceListBinding;
import com.cyberalaer.hybrid.view.GradViewItemDecoration;

import java.util.List;

/**
 * 种子商店:我的树苗
 */
public class SeedMineFragment extends BaseBindFragment<FragmentProduceListBinding> {

    private AdapterSeedMine adapter;

    public static SeedMineFragment newInstance() {
        SeedMineFragment fragment = new SeedMineFragment();
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
        if (adapter == null) {
            adapter = new AdapterSeedMine(data);
            bindRoot.produceList.setAdapter(adapter);
            bindRoot.produceList.addItemDecoration(new GradViewItemDecoration(getContext(), 4));
        } else {
            adapter.setmData(data);
        }

    }
}