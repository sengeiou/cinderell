package com.cinderellavip.ui.fragment.mine;

import android.os.Bundle;

import com.cinderellavip.R;
import com.cinderellavip.adapter.recycleview.OrderAdapter;
import com.cinderellavip.bean.local.OrderBean;
import com.tozzais.baselibrary.ui.BaseListFragment;

import java.util.ArrayList;
import java.util.List;

import androidx.recyclerview.widget.LinearLayoutManager;


public class OrderFragment extends BaseListFragment<OrderBean> {


    public static final int ALL = 0,UNPAY = 1,UNSEND = 2,UNRECEIVE = 3,FINISH = 4,EVALUATION = 5;
    private int type;

    public static OrderFragment newInstance(int type){
        OrderFragment cartFragment = new OrderFragment();
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
        mAdapter = new OrderAdapter();
        mRecyclerView.setAdapter(mAdapter);

        setEmptyView(R.mipmap.empty_view,"您还没有相关订单哦~","去逛逛", view->{

        });



    }

    @Override
    public void loadData() {
        if (swipeLayout != null)
        swipeLayout.setRefreshing(false);
        List<OrderBean> list = new ArrayList<>();
        if (type == ALL){
            list.add(new OrderBean(UNPAY));
            list.add(new OrderBean(UNSEND));
            list.add(new OrderBean(UNRECEIVE));
            list.add(new OrderBean(EVALUATION));
            list.add(new OrderBean(FINISH));
        }else if (type == UNPAY){
            list.add(new OrderBean(UNPAY));
            list.add(new OrderBean(UNPAY));
            list.add(new OrderBean(UNPAY));
        }else if (type == UNSEND){
            list.add(new OrderBean(UNSEND));
            list.add(new OrderBean(UNSEND));
            list.add(new OrderBean(UNSEND));
        }else if (type == UNRECEIVE){
            list.add(new OrderBean(UNRECEIVE));
            list.add(new OrderBean(UNRECEIVE));
            list.add(new OrderBean(UNRECEIVE));
        }else if (type == FINISH){
            list.add(new OrderBean(EVALUATION));
            list.add(new OrderBean(EVALUATION));
            list.add(new OrderBean(FINISH));
            list.add(new OrderBean(FINISH));
        }
       setData(true, list);

//        swipeLayout.setRefreshing(false);
//        List<MessageItem> list = new ArrayList<>();
//        if (type == ORDER){
//            list.add(new MessageItem("发货提醒","2019-4-19","您的订单 2019337892 已发货"));
//            list.add(new MessageItem("确认收货提醒","2019-4-19","您的订单 2019337892 已收货货"));
//            list.add(new MessageItem("系统通知","2019-4-09","尊敬的用户，为了给你带来更好的体验，秒杀专区将于2019年4月22日22:00至2019年4月25日9:00期间进行维护"));
//
//        }else {
//            list.add(new MessageItem("系统通知","2019-4-09","尊敬的用户，为了给你带来更好的体验，秒杀专区将于2019年4月22日22:00至2019年4月25日9:00期间进行维护"));
//            list.add(new MessageItem("系统通知","2019-4-09","尊敬的用户，为了给你带来更好的体验，秒杀专区将于2019年4月22日22:00至2019年4月25日9:00期间进行维护"));
//        }
//
//        setData(true,list);

    }

    @Override
    public void initListener() {
        super.initListener();
        mAdapter.getLoadMoreModule().setEnableLoadMore(false);

    }
}
