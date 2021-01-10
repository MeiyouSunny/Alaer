package com.cyberalaer.hybrid.ui.user;

import android.text.Editable;
import android.text.TextUtils;
import android.view.View;

import com.alaer.lib.api.bean.Balance;
import com.cyberalaer.hybrid.R;
import com.cyberalaer.hybrid.base.BaseTitleActivity;
import com.cyberalaer.hybrid.databinding.ActivityExchangeScoreBinding;
import com.cyberalaer.hybrid.util.NumberUtils;
import com.cyberalaer.hybrid.util.SimpleTextWatcher;
import com.cyberalaer.hybrid.util.ViewUtil;

/**
 * 兑换建设积分
 */
public class ExchangeBuildScoreActivity extends BaseTitleActivity<ActivityExchangeScoreBinding> {

    Balance mBalance;

    @Override
    protected int titleResId() {
        return R.string.exchangge_score;
    }

    @Override
    protected int layoutId() {
        return R.layout.activity_exchange_score;
    }

    @Override
    public void onViewCreated() {
        super.onViewCreated();

        mBalance = (Balance) getIntent().getSerializableExtra("balance");
        bindRoot.setNumber(NumberUtils.instance());
        bindRoot.setBalance(mBalance);

        bindRoot.etAmount.addTextChangedListener(new SimpleTextWatcher() {
            @Override
            public void afterTextChanged(Editable editable) {
                onInputChanged();
            }
        });

    }

    private void onInputChanged() {
        final String input = ViewUtil.getText(bindRoot.etAmount);
        boolean noInput = TextUtils.isEmpty(input);
        bindRoot.layoutInfo.setVisibility(noInput ? View.GONE : View.VISIBLE);
        if (noInput) {
            return;
        }

        bindRoot.tvFruitCount.setText(getString(R.string.fruit_number_is, input));

        float inputValue = 0F;
        if (input.contains("."))
            inputValue = Float.parseFloat(input);
        else
            inputValue = (float) Integer.parseInt(input);

        float amount = 100 * inputValue / (100 + mBalance.feeRate);
        float rateAmount = inputValue - amount;

        bindRoot.amount.setText(getString(R.string.score_number_is_about, NumberUtils.instance().parseNumber(amount)));
        bindRoot.rateAmount.setText(getString(R.string.fruit_number_is_about, NumberUtils.instance().parseNumber(rateAmount)));
        System.out.println("");

    }

    @Override
    public void click(View view) {
        switch (view.getId()) {
            case R.id.exchangeAll:
                bindRoot.etAmount.setText(NumberUtils.instance().parseNumber(mBalance.diamond.amount));
                break;
            case R.id.submit:
                break;
        }
    }

}