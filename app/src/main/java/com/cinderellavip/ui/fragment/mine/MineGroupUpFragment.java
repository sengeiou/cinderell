package com.cinderellavip.ui.fragment.mine;

import android.os.Bundle;
import android.os.Handler;

import com.cinderellavip.adapter.recycleview.MineGroupUpAdapter;
import com.tozzais.baselibrary.ui.BaseListFragment;

import java.util.ArrayList;
import java.util.List;

import androidx.recyclerview.widget.LinearLayoutManager;


public class MineGroupUpFragment extends BaseListFragment<String> {


    private int type;

    public static MineGroupUpFragment newInstance(int type){
        MineGroupUpFragment cartFragment = new MineGroupUpFragment();
        Bundle bundle = new Bundle();
        bundle.putInt("type",type);
        cartFragment.setArguments(bundle);
        return cartFragment;

    }


    @Override
    public void initView(Bundle savedInstanceState) {
        super.initView(savedInstanceState);
        type = getArguments().getInt("type");
        mRecyclerView.setLayoutManager(new LinearLayoutManager(mActivity));
        mAdapter = new MineGroupUpAdapter();
        mRecyclerView.setAdapter(mAdapter);

        setEmptyView("您还没有相关拼团哦~");

    }

    private String category = "1";
    @Override
    public void loadData() {
        super.loadData();
        new Handler().postDelayed(()->{
            List<String> list = new ArrayList<>();
            list.add(""+type);
            list.add(""+type);
            list.add(""+type);
            setData(list);
        },500);


    }

}
