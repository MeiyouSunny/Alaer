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

import java.util.List;

/**
 * 种子商店:过期失效
 */
public class SeedExpiredFragment extends BaseBindFragment<FragmentProduceListBinding> {

    public static SeedExpiredFragment newInstance() {
        SeedExpiredFragment fragment = new SeedExpiredFragment();
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
        UserData userData = UserDataUtil.instance().getUserData();
        if (userData == null)
            return;
        ApiUtil.apiService().mySeeds(userData.uuid, String.valueOf(userData.uid), userData.token, AppConfig.DIAMOND_CURRENCY,
                -1, 1, AppConfig.PAGE_SIZE_DEFAULT,
                new Callback<List<SeedMine>>() {
                    @Override
                    public void onResponse(List<SeedMine> data) {
                        showData(data);
                    }
                });
    }

    private void showData(List<SeedMine> data) {
        AdapterSeedExpired adapter = new AdapterSeedExpired(data);
        bindRoot.produceList.setAdapter(adapter);
    }
}