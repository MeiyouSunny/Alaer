package com.cyberalaer.hybrid.ui.user;

import com.alaer.lib.api.bean.Balance;
import com.cyberalaer.hybrid.R;
import com.cyberalaer.hybrid.base.BaseTitleActivity;
import com.cyberalaer.hybrid.databinding.ActivityFruitBillBinding;
import com.cyberalaer.hybrid.util.NumberUtils;

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
        setTitleRightVisible(true);
        setTitleRightIcon(R.drawable.ic_question);

        Balance balance = (Balance) getIntent().getSerializableExtra("balance");
        bindRoot.setNumber(NumberUtils.instance());
        bindRoot.setBalance(balance);

    }

}