package llc.metaversenetwork.app.ui.user;

import com.alaer.lib.api.ApiUtil;
import com.alaer.lib.api.AppConfig;
import com.alaer.lib.api.Callback;
import com.alaer.lib.api.bean.FruitBill;
import com.alaer.lib.api.bean.UserData;
import com.alaer.lib.data.UserDataUtil;
import llc.metaversenetwork.app.R;
import llc.metaversenetwork.app.base.BaseBindFragment;
import llc.metaversenetwork.app.databinding.FragmentBuildScoreBinding;
import llc.metaversenetwork.app.util.CollectionUtils;

import java.util.List;

/**
 * 建设功分明细列表Fragment
 */
public class BuildScoreFragment extends BaseBindFragment<FragmentBuildScoreBinding> {

    @Override
    public int initLayoutResId() {
        return R.layout.fragment_build_score;
    }

    @Override
    public void onViewCreated() {
        super.onViewCreated();

        queryBuildScoreBill();
    }

    private void queryBuildScoreBill() {
        UserData userData = UserDataUtil.instance().getUserData();
        if (userData == null)
            return;

        ApiUtil.apiService().buildScoreBill(userData.uuid, String.valueOf(userData.uid), userData.token, AppConfig.DIAMOND_CURRENCY, 0, 100,
                new Callback<List<FruitBill>>() {
                    @Override
                    public void onResponse(List<FruitBill> bills) {
                        showFruitBill(bills);
                    }

                    @Override
                    public void onError(int code, String msg) {
                        super.onError(code, msg);
                    }
                });
    }

    private void showFruitBill(List<FruitBill> bills) {
        bindRoot.repeatView.getRecyclerView().setPaddingRelative(0, 48, 0, 0);
        bindRoot.repeatView.getRecyclerView().setClipToPadding(false);

        if (CollectionUtils.isEmpty(bills))
            bindRoot.repeatView.layoutAdapterManager().showEmptyView();
        else
            bindRoot.repeatView.viewManager().bind(bills);
    }

}