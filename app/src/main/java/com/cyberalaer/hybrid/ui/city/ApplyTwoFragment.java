package com.cyberalaer.hybrid.ui.city;

import android.view.View;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.alaer.lib.api.bean.ApplyCityNodeParam;
import com.cyberalaer.hybrid.R;
import com.cyberalaer.hybrid.base.BaseBindFragment;
import com.cyberalaer.hybrid.databinding.FragmentApplyTwoBinding;
import com.cyberalaer.hybrid.util.ViewUtil;
import com.meiyou.mvp.MvpBinder;

@MvpBinder(
)
public class ApplyTwoFragment extends BaseBindFragment<FragmentApplyTwoBinding> {

    @Override
    public int initLayoutResId() {
        return R.layout.fragment_apply_two;
    }

    @Override
    public void click(View view) {
        switch (view.getId()) {
            case R.id.submit:
                setParams();
                ((CityNodeApplyActivity) getActivity()).uploadLocation();
                break;
        }
    }

    private int getRadioGroupCheckedIndex(RadioGroup radioGroup) {
        int count = radioGroup.getChildCount();
        for (int i = 0; i < count; i++) {
            RadioButton rb = (RadioButton) radioGroup.getChildAt(i);
            if (rb.isChecked()) {
                return i;
            }
        }
        return 0;
    }

    private void setParams() {
        int[] amountList = {50000, 100000, 150000, 200000, 250000, 300000, 500000};

        ApplyCityNodeParam param = ((CityNodeApplyActivity) getActivity()).params;
        param.amount = amountList[getRadioGroupCheckedIndex(bindRoot.amount)];
        param.cooperateType = getRadioGroupCheckedIndex(bindRoot.cooperateType);
        param.manageType = getRadioGroupCheckedIndex(bindRoot.manageType);
        param.msgJob = ViewUtil.getText(bindRoot.msgJob);
        param.msgRelation = ViewUtil.getText(bindRoot.msgRelation);
        param.star = getRadioGroupCheckedIndex(bindRoot.star);
    }

}