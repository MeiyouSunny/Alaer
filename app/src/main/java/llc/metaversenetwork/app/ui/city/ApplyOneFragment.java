package llc.metaversenetwork.app.ui.city;

import android.view.View;

import com.alaer.lib.api.bean.ApplyCityNodeParam;
import com.alaer.lib.api.bean.Province;
import llc.metaversenetwork.app.R;
import llc.metaversenetwork.app.base.BaseBindFragment;
import llc.metaversenetwork.app.databinding.FragmentApplyOneBinding;
import llc.metaversenetwork.app.ui.dialog.DialogCitySelect;
import llc.metaversenetwork.app.util.ViewUtil;
import com.meiyou.mvp.MvpBinder;

import likly.dialogger.Dialogger;

@MvpBinder(
)
public class ApplyOneFragment extends BaseBindFragment<FragmentApplyOneBinding> {

    @Override
    public int initLayoutResId() {
        return R.layout.fragment_apply_one;
    }

    @Override
    public void click(View view) {
        switch (view.getId()) {
            case R.id.next:
                setParams();
                navigate(R.id.action_applyOne_to_applyTwo);
                break;
            case R.id.selectCity:
                showNotCityMasterDialog();
                break;
        }
    }

    private void setParams() {
        ApplyCityNodeParam param = ((CityNodeApplyActivity) getActivity()).params;
        param.name = ViewUtil.getText(bindRoot.name);
        param.phone = ViewUtil.getText(bindRoot.phone);
        param.wechat = ViewUtil.getText(bindRoot.wechat);
        param.inviter = ViewUtil.getText(bindRoot.inviter);
        param.inviterPhone = ViewUtil.getText(bindRoot.inviterPhone);
        param.city = ViewUtil.getText(bindRoot.city);
        param.address = ViewUtil.getText(bindRoot.address);
    }

    private void showNotCityMasterDialog() {
        DialogCitySelect dialog = new DialogCitySelect(new DialogCitySelect.OnCitySelectListener() {
            @Override
            public void onCitySelect(Province province, Province.City city, Province.Region area) {
                final String cityInfo = province.name + city.name + area.name;
                bindRoot.city.setText(cityInfo);
            }
        });
        Dialogger.newDialog(getContext()).holder(dialog)
                .show();
    }

}