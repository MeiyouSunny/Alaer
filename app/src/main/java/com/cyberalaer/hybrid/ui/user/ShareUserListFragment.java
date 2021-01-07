package com.cyberalaer.hybrid.ui.user;

import android.os.Bundle;

import com.alaer.lib.api.ApiUtil;
import com.alaer.lib.api.AppConfig;
import com.alaer.lib.api.Callback;
import com.alaer.lib.api.bean.SharedUserList;
import com.alaer.lib.api.bean.UserData;
import com.alaer.lib.data.UserDataUtil;
import com.cyberalaer.hybrid.R;
import com.cyberalaer.hybrid.base.BaseBindFragment;
import com.cyberalaer.hybrid.databinding.FragmentActiveDetailListBinding;
import com.cyberalaer.hybrid.util.CollectionUtils;

/**
 * 分享用户列表Fragment
 */
public class ShareUserListFragment extends BaseBindFragment<FragmentActiveDetailListBinding> {
    private static final String[] types = new String[]{"team_activeness", "num", "level", "uid"};
    private static final String[] orderTypes = new String[]{"asc", "desc"};
    private static final String TYPE = "type";
    private int type, orderType;

    public static ShareUserListFragment newInstance(int type) {
        ShareUserListFragment fragment = new ShareUserListFragment();
        Bundle bundle = new Bundle();
        bundle.putString(TYPE, types[type]);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            type = getArguments().getInt(TYPE);
        }
    }

    @Override
    public int initLayoutResId() {
        return R.layout.fragment_active_detail_list;
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
        ApiUtil.apiService().teamSharedUserList(userData.uuid, String.valueOf(userData.uid), userData.token, AppConfig.DIAMOND_CURRENCY,
                1, 20, types[type], orderTypes[orderType],
                new Callback<SharedUserList>() {
                    @Override
                    public void onResponse(SharedUserList list) {
                        showSharedUserList(list);
                    }
                });
    }

    private void showSharedUserList(SharedUserList list) {
        if (list == null || CollectionUtils.isEmpty(list.list))
            return;
        SharedUserAdapter adapter = new SharedUserAdapter(getActivity(), list.list);
        bindRoot.list.setAdapter(adapter);
    }

}