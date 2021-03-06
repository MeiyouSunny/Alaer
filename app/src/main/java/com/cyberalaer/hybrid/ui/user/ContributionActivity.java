package com.cyberalaer.hybrid.ui.user;

import com.alaer.lib.api.ApiUtil;
import com.alaer.lib.api.AppConfig;
import com.alaer.lib.api.Callback;
import com.alaer.lib.api.bean.ActiveBill;
import com.alaer.lib.api.bean.ActiveBillList;
import com.alaer.lib.api.bean.UserData;
import com.alaer.lib.data.UserDataUtil;
import com.cyberalaer.hybrid.R;
import com.cyberalaer.hybrid.base.BaseTitleActivity;
import com.cyberalaer.hybrid.databinding.ActivityContributionBinding;
import com.cyberalaer.hybrid.ui.webpage.WebPageActivity;
import com.cyberalaer.hybrid.util.CollectionUtils;
import com.cyberalaer.hybrid.util.NumberUtils;

import java.util.List;

/**
 * 贡献值明细
 */
public class ContributionActivity extends BaseTitleActivity<ActivityContributionBinding> {

    @Override
    protected int titleResId() {
        return R.string.contribution_detail;
    }

    @Override
    protected int layoutId() {
        return R.layout.activity_contribution;
    }

    @Override
    public void onViewCreated() {
        super.onViewCreated();
        setTitleRightIcon(R.drawable.ic_question);

        int value = getIntent().getIntExtra("contribution", 0);
        bindRoot.value.setText(NumberUtils.instance().parseNumber(value));

        queryRecord();
    }

    @Override
    protected void onRightClick() {
        WebPageActivity.start(this, AppConfig.CONTRIBUTION_EXPLAIN, R.string.contribution_explain);
    }

    private void queryRecord() {
        UserData userData = UserDataUtil.instance().getUserData();
        if (userData == null)
            return;

        ApiUtil.apiService().contributionRecord(userData.uuid, String.valueOf(userData.uid), userData.token, AppConfig.DIAMOND_CURRENCY, 1, 100,
                new Callback<ActiveBillList>() {
                    @Override
                    public void onResponse(ActiveBillList bills) {
                        if (bills != null) {
                            showActiveBill(bills.list);
                        }
                    }

                    @Override
                    public void onError(int code, String msg) {
                        super.onError(code, msg);
                    }
                });
    }

    private void showActiveBill(List<ActiveBill> records) {
        bindRoot.repeatView.getRecyclerView().setPaddingRelative(0, 48, 0, 0);
        bindRoot.repeatView.getRecyclerView().setClipToPadding(false);

        if (CollectionUtils.isEmpty(records))
            bindRoot.repeatView.layoutAdapterManager().showEmptyView();
        else
            bindRoot.repeatView.viewManager().bind(records);
    }

}