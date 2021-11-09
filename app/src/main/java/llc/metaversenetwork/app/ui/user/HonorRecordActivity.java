package llc.metaversenetwork.app.ui.user;

import com.alaer.lib.api.ApiUtil;
import com.alaer.lib.api.AppConfig;
import com.alaer.lib.api.Callback;
import com.alaer.lib.api.bean.ActiveBill;
import com.alaer.lib.api.bean.ActiveBillList;
import com.alaer.lib.api.bean.UserData;
import com.alaer.lib.data.UserDataUtil;

import java.util.List;

import llc.metaversenetwork.app.R;
import llc.metaversenetwork.app.base.BaseTitleActivity;
import llc.metaversenetwork.app.databinding.ActivityHonorRecordBindingImpl;
import llc.metaversenetwork.app.ui.webpage.WebPageActivity;
import llc.metaversenetwork.app.util.CollectionUtils;
import llc.metaversenetwork.app.util.NumberUtils;

/**
 * 荣誉值明细
 */
public class HonorRecordActivity extends BaseTitleActivity<ActivityHonorRecordBindingImpl> {

    @Override
    protected int titleResId() {
        return R.string.honor_value_record;
    }

    @Override
    protected int layoutId() {
        return R.layout.activity_honor_record;
    }

    @Override
    public void onViewCreated() {
        super.onViewCreated();
        setTitleRightIcon(R.drawable.ic_question);

        float value = getIntent().getFloatExtra("honor", 0);
        bindRoot.value.setText(NumberUtils.instance().parseNumber(value));

        queryRecord();
    }

    @Override
    protected void onRightClick() {
        WebPageActivity.start(this, getString(R.string.honor_desc_url), R.string.honor_value_desc);
    }

    private void queryRecord() {
        UserData userData = UserDataUtil.instance().getUserData();
        if (userData == null)
            return;

        ApiUtil.apiService().honorRecord(userData.uuid, String.valueOf(userData.uid), userData.token, AppConfig.DIAMOND_CURRENCY, 1, 100,
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
                        showActiveBill(null);
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