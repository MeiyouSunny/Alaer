package com.cyberalaer.hybrid.ui.dialog;

import android.view.View;

import com.alaer.lib.api.bean.Province;
import com.contrarywind.listener.OnItemSelectedListener;
import com.cyberalaer.hybrid.R;
import com.cyberalaer.hybrid.base.BaseDialogHolder;
import com.cyberalaer.hybrid.databinding.DialogCitySelectBinding;
import com.cyberalaer.hybrid.util.RegionUtil;
import com.cyberalaer.hybrid.view.wheel.ArrayWheelAdapter;

import java.util.List;

public class DialogCitySelect extends BaseDialogHolder<DialogCitySelectBinding> {

    OnCitySelectListener mListener;
    List<Province> mProvinces;

    public DialogCitySelect(OnCitySelectListener listener) {
        super(R.layout.dialog_city_select);
        mListener = listener;
    }

    @Override
    public void onViewCreated(View view) {
        super.onViewCreated(view);

        mProvinces = new RegionUtil().parseCitys(getContext());

        bindRoot.province.setCyclic(false);
        bindRoot.city.setCyclic(false);
        bindRoot.area.setCyclic(false);
        bindRoot.province.setAdapter(new ArrayWheelAdapter(mProvinces));
        bindRoot.city.setAdapter(new ArrayWheelAdapter(mProvinces.get(0).citys));
        bindRoot.area.setAdapter(new ArrayWheelAdapter(mProvinces.get(0).citys.get(0).regions));

        bindRoot.province.setOnItemSelectedListener(new OnItemSelectedListener() {
            @Override
            public void onItemSelected(int index) {
                bindRoot.city.setAdapter(new ArrayWheelAdapter(mProvinces.get(index).citys));
                bindRoot.city.setCurrentItem(0);

                bindRoot.area.setAdapter(
                        new ArrayWheelAdapter(mProvinces.get(index).citys.get(0).regions));
                bindRoot.area.setCurrentItem(0);
            }
        });
        bindRoot.city.setOnItemSelectedListener(new OnItemSelectedListener() {
            @Override
            public void onItemSelected(int index) {
                bindRoot.area.setAdapter(
                        new ArrayWheelAdapter(mProvinces.get(bindRoot.province.getCurrentItem()).citys.get(index).regions));
                bindRoot.area.setCurrentItem(0);
            }
        });
    }

    @Override
    public void click(View view) {
        switch (view.getId()) {
            case R.id.btnCancel:
                dismiss();
                break;
            case R.id.btnSubmit:
                dismiss();
                if (mListener != null) {
                    final int i, j, k;
                    i = bindRoot.province.getCurrentItem();
                    j = bindRoot.city.getCurrentItem();
                    k = bindRoot.area.getCurrentItem();

                    mListener.onCitySelect(mProvinces.get(i),
                            mProvinces.get(i).citys.get(j),
                            mProvinces.get(i).citys.get(j).regions.get(k));
                }
                break;
        }
    }

    public interface OnCitySelectListener {
        void onCitySelect(Province province, Province.City city, Province.Region area);
    }

}
