package com.cinderellavip.ui.fragment.find;

import android.os.Bundle;
import android.os.Handler;
import android.widget.TextView;

import com.cinderellavip.R;
import com.cinderellavip.adapter.recycleview.ReportAdapter;
import com.cinderellavip.bean.net.NetCityBean;
import com.tozzais.baselibrary.ui.BaseListFragment;

import java.util.ArrayList;
import java.util.List;

import androidx.recyclerview.widget.LinearLayoutManager;
import butterknife.BindView;
import butterknife.OnClick;


public class ReportFragment extends BaseListFragment<NetCityBean> {


    @BindView(R.id.tv_add)
    TextView tvAdd;

    @Override
    public int setLayout() {
        return R.layout.fragment_mine_address;
    }

    @Override
    public void initView(Bundle savedInstanceState) {
        super.initView(savedInstanceState);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(mActivity));
        mAdapter = new ReportAdapter();
        mRecyclerView.setAdapter(mAdapter);

        tvAdd.setText("提交");
    }

    @Override
    public void loadData() {
        super.loadData();
        new Handler().postDelayed(()->{
            List<NetCityBean> list = new ArrayList<>();
            list.add(new NetCityBean(false));
            list.add(new NetCityBean(false));
            list.add(new NetCityBean(false));
            list.add(new NetCityBean(false));
            list.add(new NetCityBean(false));
            list.add(new NetCityBean(false));
            list.add(new NetCityBean(false));
            setData(list);
        },500);



    }

    //选中的item
    private NetCityBean netCityBean;

    @Override
    public void initListener() {
        super.initListener();

    }


    @OnClick(R.id.tv_add)
    public void onClick() {
        tsg("提交成功");
        mActivity.finish();
//
    }












}
