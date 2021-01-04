package com.cyberalaer.hybrid.ui.produce;

import com.alaer.lib.api.ApiUtil;
import com.alaer.lib.api.AppConfig;
import com.alaer.lib.api.Callback;
import com.alaer.lib.api.bean.SeedStore;
import com.alaer.lib.api.bean.SeedStoreList;
import com.alaer.lib.api.bean.UserData;
import com.alaer.lib.data.UserDataUtil;
import com.cyberalaer.hybrid.R;
import com.cyberalaer.hybrid.base.BaseBindFragment;
import com.cyberalaer.hybrid.databinding.FragmentProduceListBinding;

import java.util.List;

/**
 * 种子商店:种子商店
 */
public class SeedStoreFragment extends BaseBindFragment<FragmentProduceListBinding> {

    public static SeedStoreFragment newInstance() {
        SeedStoreFragment fragment = new SeedStoreFragment();
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
        AdapterSeedStore adapter = new AdapterSeedStore(data);
        bindRoot.produceList.setAdapter(adapter);
    }
}