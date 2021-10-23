package llc.metaversenetwork.app.ui.produce;

import com.alaer.lib.api.ApiUtil;
import com.alaer.lib.api.AppConfig;
import com.alaer.lib.api.Callback;
import com.alaer.lib.api.bean.SeedMine;
import com.alaer.lib.api.bean.UserData;
import com.alaer.lib.data.UserDataUtil;
import llc.metaversenetwork.app.R;
import llc.metaversenetwork.app.base.BaseBindFragment;
import llc.metaversenetwork.app.databinding.FragmentSeedExpiredListBinding;
import llc.metaversenetwork.app.util.CollectionUtils;
import llc.metaversenetwork.app.view.GradViewItemDecoration;
import com.meiyou.mvp.MvpBinder;

import java.util.List;

/**
 * 种子商店:过期失效
 */
@MvpBinder(
)
public class SeedExpiredFragment extends BaseBindFragment<FragmentSeedExpiredListBinding> {

    public static SeedExpiredFragment newInstance() {
        SeedExpiredFragment fragment = new SeedExpiredFragment();
        return fragment;
    }

    @Override
    public int initLayoutResId() {
        return R.layout.fragment_seed_expired_list;
    }

    @Override
    public void onViewCreated() {
        super.onViewCreated();

        initData();
    }

    private void initData() {
        UserData userData = UserDataUtil.instance().getUserData();
        if (userData == null)
            return;
        ApiUtil.apiService().mySeeds(userData.uuid, String.valueOf(userData.uid), userData.token, AppConfig.DIAMOND_CURRENCY,
                -1, 1, AppConfig.PAGE_SIZE_DEFAULT,
                new Callback<List<SeedMine>>() {
                    @Override
                    public void onResponse(List<SeedMine> data) {
                        showData(data);
                    }
                });
    }

    private void showData(List<SeedMine> data) {
//        bindRoot.repeatView.getRecyclerView().setPaddingRelative(0, 32, 0, 0);
        bindRoot.repeatView.getRecyclerView().setClipToPadding(false);
        bindRoot.repeatView.getRecyclerView().addItemDecoration(new GradViewItemDecoration(getContext(), 6));

        if (!CollectionUtils.isEmpty(data))
            bindRoot.repeatView.viewManager().bind(data);
        else
            bindRoot.repeatView.layoutAdapterManager().showEmptyView();
    }
}