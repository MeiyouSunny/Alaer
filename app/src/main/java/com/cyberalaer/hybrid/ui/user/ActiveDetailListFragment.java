package com.cyberalaer.hybrid.ui.user;

import android.os.Bundle;

import com.cyberalaer.hybrid.R;
import com.cyberalaer.hybrid.base.BaseBindFragment;
import com.cyberalaer.hybrid.data.entity.Produce;
import com.cyberalaer.hybrid.databinding.FragmentActiveDetailListBinding;

import java.util.ArrayList;
import java.util.List;

/**
 * 活跃度明细列表Fragment
 */
public class ActiveDetailListFragment extends BaseBindFragment<FragmentActiveDetailListBinding> {
    private static final String TYPE = "type";

    public static ActiveDetailListFragment newInstance(int type) {
        ActiveDetailListFragment fragment = new ActiveDetailListFragment();
        Bundle bundle = new Bundle();
        bundle.putInt(TYPE, type);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        int type = 1;
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

        List<Produce> data = new ArrayList<>();
        data.add(new Produce());
        data.add(new Produce());
        data.add(new Produce());
        data.add(new Produce());
        data.add(new Produce());
        data.add(new Produce());
        data.add(new Produce());
        ActiveDetailAdapter produceAdapter = new ActiveDetailAdapter(data);
        bindRoot.list.setAdapter(produceAdapter);
    }
}