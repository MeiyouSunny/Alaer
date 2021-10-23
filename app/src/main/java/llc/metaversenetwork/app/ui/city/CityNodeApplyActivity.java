package llc.metaversenetwork.app.ui.city;

import android.os.Bundle;

import com.alaer.lib.api.ApiUtil;
import com.alaer.lib.api.AppConfig;
import com.alaer.lib.api.Callback;
import com.alaer.lib.api.bean.ApplyCityNodeParam;
import com.alaer.lib.api.bean.UserData;
import com.alaer.lib.data.UserDataUtil;
import com.amap.api.maps.model.LatLng;
import llc.metaversenetwork.app.R;
import llc.metaversenetwork.app.base.BaseTitleActivity;
import llc.metaversenetwork.app.databinding.ActivityAuthBinding;
import com.meiyou.mvp.MvpBinder;

import androidx.annotation.Nullable;
import likly.dollar.$;

/**
 * 城市节点申请页面
 */
@MvpBinder(
)
public class CityNodeApplyActivity extends BaseTitleActivity<ActivityAuthBinding> {
    public ApplyCityNodeParam params = new ApplyCityNodeParam();

    @Override
    protected int titleResId() {
        return R.string.city_note_apply;
    }

    @Override
    protected int layoutId() {
        return R.layout.activity_city_note_apply;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    public void uploadLocation() {
        LatLng location = getIntent().getParcelableExtra("location");

        UserData userData = UserDataUtil.instance().getUserData();
        ApiUtil.apiService().uploadLocation(userData.uuid, String.valueOf(userData.uid), userData.token, AppConfig.DIAMOND_CURRENCY,
                location.longitude, location.latitude,
                new Callback<String>() {
                    @Override
                    public void onResponse(String response) {
                        applyCityNode();
                    }
                });
    }

    private void applyCityNode() {
        UserData userData = UserDataUtil.instance().getUserData();
        ApiUtil.apiService().applyCityNode(userData.uuid, String.valueOf(userData.uid), userData.token, AppConfig.DIAMOND_CURRENCY,
                params.name, params.phone, params.wechat,
                params.inviter, params.inviterPhone, params.city,
                params.address, params.amount, params.cooperateType,
                params.manageType, params.msgJob, params.msgRelation, params.star,
                new Callback<String>() {
                    @Override
                    public void onResponse(String response) {
                        $.toast().text("提交成功！").show();
                        finish();
                    }

                    @Override
                    public void onError(int code, String msg) {
                        $.toast().text(msg).show();
                    }
                });
    }

}