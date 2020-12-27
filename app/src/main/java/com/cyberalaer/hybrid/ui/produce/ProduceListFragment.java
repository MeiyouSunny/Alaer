package com.cyberalaer.hybrid.ui.produce;

import android.os.Bundle;

import com.cyberalaer.hybrid.R;
import com.cyberalaer.hybrid.base.BaseBindFragment;
import com.cyberalaer.hybrid.data.entity.Produce;
import com.cyberalaer.hybrid.databinding.FragmentProduceListBinding;
import com.cyberalaer.hybrid.ui.user.ActiveDetailAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * 生产作物列表Fragment
 */
public class ProduceListFragment extends BaseBindFragment<FragmentProduceListBinding> {
    private static final String TYPE = "type";

    public static ProduceListFragment newInstance(int type) {
        ProduceListFragment fragment = new ProduceListFragment();
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
        return R.layout.fragment_produce_list;
    }

    @Override
    public void onViewCreated() {
        super.onViewCreated();

        List<Produce> produceList = new ArrayList<>();
        produceList.add(new Produce());
        produceList.add(new Produce());
        produceList.add(new Produce());
        produceList.add(new Produce());
        produceList.add(new Produce());
        ActiveDetailAdapter produceAdapter = new ActiveDetailAdapter(produceList);
        bindRoot.produceList.setAdapter(produceAdapter);
    }
}