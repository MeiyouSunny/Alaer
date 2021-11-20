package llc.metaversenetwork.app.ui.auth;

import android.content.Intent;
import android.net.Uri;
import android.view.View;

import com.alaer.lib.api.ApiUtil;
import com.alaer.lib.api.AppConfig;
import com.alaer.lib.api.Callback;
import com.alaer.lib.api.bean.OrderInfo;
import com.alaer.lib.api.bean.UserData;
import com.alaer.lib.data.UserDataUtil;
import com.meiyou.mvp.MvpBinder;

import llc.metaversenetwork.app.R;
import llc.metaversenetwork.app.base.BaseBindFragment;
import llc.metaversenetwork.app.databinding.FragmentAuthPayConfirmBinding;
import llc.metaversenetwork.app.util.ToastUtil;

@MvpBinder(
)
public class PayConfirmFragment extends BaseBindFragment<FragmentAuthPayConfirmBinding> {

    String orderId;
    boolean needGetOrderState;

    @Override
    public int initLayoutResId() {
        return R.layout.fragment_auth_pay_confirm;
    }

    @Override
    public void click(View view) {
        switch (view.getId()) {
            case R.id.confirm:
                gotoPayWeb();
                break;
        }
    }

    @Override
    public void onViewCreated() {
        super.onViewCreated();

        orderId = getArguments().getString("orderId");
        bindRoot.orderInfo.setText(getString(R.string.order_id_is, orderId));
    }

    private void gotoPayWeb() {
        final String payWebUrl = AppConfig.ALIPAY_URL + orderId;
        Uri uri = Uri.parse(payWebUrl);
        Intent intent = new Intent(Intent.ACTION_VIEW, uri);
        startActivity(intent);
        needGetOrderState = true;
    }

    @Override
    public void onResume() {
        super.onResume();
        if (needGetOrderState) {
            queryPayState();
        }
    }

    private void queryPayState() {
        needGetOrderState = false;
        UserData userData = UserDataUtil.instance().getUserData();
        if (userData == null)
            return;

        ApiUtil.apiService().queryPayState(userData.uuid, String.valueOf(userData.uid), userData.token, AppConfig.DIAMOND_CURRENCY, orderId,
                new Callback<OrderInfo>() {
                    @Override
                    public void onResponse(OrderInfo orderInfo) {
                        if (orderInfo != null && orderInfo.status == 1) {
                            // 支付成功
                            navigate(R.id.action_to_pay_success);
                        } else {
                            navigate(R.id.action_to_pay_failed);
                        }
                    }

                    @Override
                    public void onError(int code, String msg) {
                        ToastUtil.text(msg).show();
                    }
                });
    }

}