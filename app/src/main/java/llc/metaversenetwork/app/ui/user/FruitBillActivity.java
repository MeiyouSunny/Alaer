package llc.metaversenetwork.app.ui.user;

import com.alaer.lib.api.AppConfig;
import com.alaer.lib.api.bean.Balance;
import llc.metaversenetwork.app.R;
import llc.metaversenetwork.app.base.BaseTitleActivity;
import llc.metaversenetwork.app.databinding.ActivityFruitBillBinding;
import llc.metaversenetwork.app.ui.webpage.WebPageActivity;
import llc.metaversenetwork.app.util.NumberUtils;

/**
 * 果实支出
 */
public class FruitBillActivity extends BaseTitleActivity<ActivityFruitBillBinding> {

    @Override
    protected int titleResId() {
        return R.string.fruit_bill;
    }

    @Override
    protected int layoutId() {
        return R.layout.activity_fruit_bill;
    }

    @Override
    public void onViewCreated() {
        super.onViewCreated();
        setTitleRightIcon(R.drawable.ic_question);

        Balance balance = (Balance) getIntent().getSerializableExtra("balance");
        bindRoot.setNumber(NumberUtils.instance());
        bindRoot.setBalance(balance);
    }

    @Override
    protected void onRightClick() {
        WebPageActivity.start(this, AppConfig.FRUIT_EXPLAIN, R.string.fruit_explain);
    }

}