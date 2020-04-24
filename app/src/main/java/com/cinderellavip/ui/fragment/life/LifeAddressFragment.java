package com.cinderellavip.ui.fragment.life;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.widget.TextView;

import com.cinderellavip.R;
import com.cinderellavip.adapter.recycleview.SelectServiceAddressAdapter;
import com.cinderellavip.bean.net.NetCityBean;
import com.cinderellavip.ui.activity.life.AddServiceAddressActivity;
import com.cinderellavip.ui.activity.life.SelectServiceAddressActivity;
import com.cinderellavip.ui.activity.mine.MineAddressActivity;
import com.tozzais.baselibrary.ui.BaseListFragment;

import java.util.ArrayList;
import java.util.List;

import androidx.recyclerview.widget.LinearLayoutManager;
import butterknife.BindView;
import butterknife.OnClick;

public class LifeAddressFragment extends BaseListFragment<NetCityBean> {


    @BindView(R.id.tv_add)
    TextView tvAdd;
    private int type;

    @Override
    public int setLayout() {
        return R.layout.fragment_select_address_life;
    }

    @Override
    public void initView(Bundle savedInstanceState) {
        super.initView(savedInstanceState);
        type = getArguments().getInt("type");
        mRecyclerView.setLayoutManager(new LinearLayoutManager(mActivity));
        mAdapter = new SelectServiceAddressAdapter(type);
        mRecyclerView.setAdapter(mAdapter);

        setEmptyView("暂无地址");

    }

    @Override
    public void loadData() {
        super.loadData();
        new Handler().postDelayed(()->{
            List<NetCityBean> list = new ArrayList<>();
            list.add(new NetCityBean(true));
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
        mAdapter.setOnItemClickListener(((baseQuickAdapter, view, position) -> {

            List<NetCityBean> data = mAdapter.getData();
            netCityBean = data.get(position);
            if (type == SelectServiceAddressActivity.SELECT) {
                Intent intent = new Intent();
                intent.putExtra("netCityBean", netCityBean);
                mActivity.setResult(Activity.RESULT_OK, intent);
                mActivity.finish();
            } else if (type == SelectServiceAddressActivity.LOOK) {
                AddServiceAddressActivity.launch(mActivity, AddServiceAddressActivity.EDIT);
            }
        }));
    }


    @OnClick(R.id.tv_add)
    public void onClick() {
        if (type == MineAddressActivity.LOOK || type == MineAddressActivity.SELETE ) {
            AddServiceAddressActivity.launch(mActivity, AddServiceAddressActivity.ADD);
        }
    }


}
