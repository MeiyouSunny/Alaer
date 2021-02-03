package com.cyberalaer.hybrid.ui.user;

import com.alaer.lib.api.ApiUtil;
import com.alaer.lib.api.AppConfig;
import com.alaer.lib.api.Callback;
import com.alaer.lib.api.bean.FruitBill;
import com.alaer.lib.api.bean.UserData;
import com.alaer.lib.data.UserDataUtil;
import com.cyberalaer.hybrid.R;
import com.cyberalaer.hybrid.base.BaseBindFragment;
import com.cyberalaer.hybrid.databinding.FragmentFruitBillBinding;
import com.cyberalaer.hybrid.util.CollectionUtils;

import java.util.List;

/**
 * 活跃度明细列表Fragment
 */
public class FruitBillFragment extends BaseBindFragment<FragmentFruitBillBinding> {

    @Override
    public int initLayoutResId() {
        return R.layout.fragment_fruit_bill;
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

        ApiUtil.apiService().fruitBill(userData.uuid, String.valueOf(userData.uid), userData.token, AppConfig.DIAMOND_CURRENCY, 0, 100,
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
        bindRoot.repeatView.getRecyclerView().setPaddingRelative(0, 48, 0, 0);
        bindRoot.repeatView.getRecyclerView().setClipToPadding(false);

        if (CollectionUtils.isEmpty(bills))
            bindRoot.repeatView.layoutAdapterManager().showEmptyView();
        else
            bindRoot.repeatView.viewManager().bind(bills);
    }

}