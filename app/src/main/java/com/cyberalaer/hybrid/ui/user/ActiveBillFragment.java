package com.cyberalaer.hybrid.ui.user;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.Gravity;

import com.alaer.lib.api.ApiUtil;
import com.alaer.lib.api.AppConfig;
import com.alaer.lib.api.Callback;
import com.alaer.lib.api.bean.ActiveBill;
import com.alaer.lib.api.bean.ActiveBillList;
import com.alaer.lib.api.bean.ActiveDetail;
import com.alaer.lib.api.bean.UserData;
import com.alaer.lib.data.UserDataUtil;
import com.cyberalaer.hybrid.R;
import com.cyberalaer.hybrid.base.BaseBindFragment;
import com.cyberalaer.hybrid.databinding.FragmentActiveDetailBinding;
import com.cyberalaer.hybrid.ui.dialog.DialogActiveDetail;
import com.cyberalaer.hybrid.util.CollectionUtils;

import java.util.List;

import likly.dialogger.Dialogger;
import likly.view.repeat.OnHolderClickListener;

/**
 * 活跃度明细列表Fragment
 */
public class ActiveBillFragment extends BaseBindFragment<FragmentActiveDetailBinding> implements OnHolderClickListener<ActiveBillAdapter> {
    private static final String TYPE = "type";

    UserData userData;

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
        return R.layout.fragment_active_detail;
    }

    @Override
    public void onViewCreated() {
        super.onViewCreated();

        queryActiveBill();
    }

    private void queryActiveBill() {
        userData = UserDataUtil.instance().getUserData();
        if (userData == null)
            return;

        ApiUtil.apiService().seedActivity(userData.uuid, String.valueOf(userData.uid), userData.token, AppConfig.DIAMOND_CURRENCY, 1, 100,
                new Callback<ActiveBillList>() {
                    @Override
                    public void onResponse(ActiveBillList bills) {
                        showActiveBill(bills.list);
                    }

                    @Override
                    public void onError(int code, String msg) {
                        super.onError(code, msg);
                    }
                });
    }

    private void showActiveBill(List<ActiveBill> bills) {
        bindRoot.repeatView.getRecyclerView().setPaddingRelative(0, 48, 0, 0);
        bindRoot.repeatView.getRecyclerView().setClipToPadding(false);
        bindRoot.repeatView.onClick(this);

        if (CollectionUtils.isEmpty(bills))
            bindRoot.repeatView.layoutAdapterManager().showEmptyView();
        else
            bindRoot.repeatView.viewManager().bind(bills);
    }

    @Override
    public void onHolderClick(ActiveBillAdapter activeBillAdapter) {
        ActiveBill bill = activeBillAdapter.getData();
        if (!hasDetail(bill.type))
            return;
        ApiUtil.apiService().activityDetail(userData.uuid, String.valueOf(userData.uid), userData.token, AppConfig.DIAMOND_CURRENCY, bill.refId,
                new Callback<ActiveDetail>() {
                    @Override
                    public void onResponse(ActiveDetail detail) {
                        if (detail == null)
                            return;
                        if (TextUtils.isEmpty(detail.note))
                            detail.note = bill.note;
                        DialogActiveDetail dialog = new DialogActiveDetail(detail);
                        Dialogger.newDialog(getContext()).holder(dialog)
                                .gravity(Gravity.CENTER)
                                .cancelable(true)
                                .show();
                    }

                    @Override
                    public void onError(int code, String msg) {
                        super.onError(code, msg);
                    }
                });
    }

    private boolean hasDetail(int type) {
        int[] hasDetailTypes = new int[]{102, 103, 3, 8, 11, 104, 6};
        for (int hasDetailType : hasDetailTypes) {
            if (type == hasDetailType)
                return true;
        }
        return false;
    }

}