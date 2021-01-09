package com.cyberalaer.hybrid.ui.user;

import android.os.Bundle;

import com.alaer.lib.api.ApiUtil;
import com.alaer.lib.api.AppConfig;
import com.alaer.lib.api.Callback;
import com.alaer.lib.api.bean.ActiveBill;
import com.alaer.lib.api.bean.ActiveBillList;
import com.alaer.lib.api.bean.UserData;
import com.alaer.lib.data.UserDataUtil;
import com.cyberalaer.hybrid.R;
import com.cyberalaer.hybrid.base.BaseBindFragment;
import com.cyberalaer.hybrid.databinding.FragmentActiveDetailListBinding;
import com.cyberalaer.hybrid.util.CollectionUtils;

import java.util.List;

/**
 * 活跃度明细列表Fragment
 */
public class ActiveBillFragment extends BaseBindFragment<FragmentActiveDetailListBinding> {
    private static final String TYPE = "type";

    public static ActiveBillFragment newInstance(int type) {
        ActiveBillFragment fragment = new ActiveBillFragment();
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

        queryActiveBill();
    }

    private void queryActiveBill() {
        UserData userData = UserDataUtil.instance().getUserData();
        if (userData == null)
            return;

        ApiUtil.apiService().seedActivity(userData.uuid, String.valueOf(userData.uid), userData.token, AppConfig.DIAMOND_CURRENCY, 1, 100,
                new Callback<ActiveBillList>() {
                    @Override
                    public void onResponse(ActiveBillList bills) {
                        if (bills != null && !CollectionUtils.isEmpty(bills.list)) {
                            showActiveBill(bills.list);
                        }
                    }

                    @Override
                    public void onError(int code, String msg) {
                        super.onError(code, msg);
                    }
                });
    }

    private void showActiveBill(List<ActiveBill> bills) {
        ActiveBillAdapter adapter = new ActiveBillAdapter(bills);
        bindRoot.list.setAdapter(adapter);
    }
}