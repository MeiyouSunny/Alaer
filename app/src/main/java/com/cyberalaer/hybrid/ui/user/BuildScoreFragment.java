package com.cyberalaer.hybrid.ui.user;

import android.os.Bundle;

import com.alaer.lib.api.ApiUtil;
import com.alaer.lib.api.AppConfig;
import com.alaer.lib.api.Callback;
import com.alaer.lib.api.bean.FruitBill;
import com.alaer.lib.api.bean.UserData;
import com.alaer.lib.data.UserDataUtil;
import com.cyberalaer.hybrid.R;
import com.cyberalaer.hybrid.base.BaseBindFragment;
import com.cyberalaer.hybrid.databinding.FragmentActiveDetailListBinding;

import java.util.List;

/**
 * 活跃度明细列表Fragment
 */
public class BuildScoreFragment extends BaseBindFragment<FragmentActiveDetailListBinding> {
    private static final String TYPE = "type";

    public static BuildScoreFragment newInstance(int type) {
        BuildScoreFragment fragment = new BuildScoreFragment();
        Bundle bundle = new Bundle();
        bundle.putInt(TYPE, type);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        int type = 1;
        if (getArguments() != null) {
            type = getArguments().getInt(TYPE);
        }
    }

    @Override
    public int initLayoutResId() {
        return R.layout.fragment_active_detail_list;
    }

    @Override
    public void onViewCreated() {
        super.onViewCreated();

        queryFruitBill();
    }

    private void queryFruitBill() {
        UserData userData = UserDataUtil.instance().getUserData();
        if (userData == null)
            return;

        ApiUtil.apiService().buildScoreBill(userData.uuid, String.valueOf(userData.uid), userData.token, AppConfig.DIAMOND_CURRENCY, 0, 100,
                new Callback<List<FruitBill>>() {
                    @Override
                    public void onResponse(List<FruitBill> bills) {
                        showFruitBill(bills);
                    }

                    @Override
                    public void onError(int code, String msg) {
                        super.onError(code, msg);
                    }
                });
    }

    private void showFruitBill(List<FruitBill> bills) {
        FruitBillAdapter adapter = new FruitBillAdapter(bills);
        bindRoot.list.setAdapter(adapter);
    }
}