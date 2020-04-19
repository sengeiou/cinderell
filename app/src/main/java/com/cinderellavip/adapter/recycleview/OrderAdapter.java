package com.cinderellavip.adapter.recycleview;


import android.view.MotionEvent;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.module.LoadMoreModule;
import com.chad.library.adapter.base.viewholder.BaseViewHolder;
import com.cinderellavip.R;
import com.cinderellavip.bean.local.OrderBean;
import com.cinderellavip.ui.activity.order.OrderDetailActivity;
import com.cinderellavip.ui.activity.order.SelectPayWayActivity;
import com.cinderellavip.ui.fragment.mine.OrderFragment;
import com.cinderellavip.util.DataUtil;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class OrderAdapter extends BaseQuickAdapter<OrderBean, BaseViewHolder> implements LoadMoreModule {


    public OrderAdapter() {
        super(R.layout.item_order, null);
    }


    @Override
    protected void convert(final BaseViewHolder helper, final OrderBean item) {
        int position = helper.getAdapterPosition();
//        RelativeLayout ll_root = helper.getView(R.id.ll_root);
        TextView tv_status = helper.getView(R.id.tv_status);
        TextView tv_btn1 = helper.getView(R.id.tv_btn1);
        TextView tv_btn2 = helper.getView(R.id.tv_btn2);
        RecyclerView rv_goods = helper.getView(R.id.rv_goods);
        rv_goods.setLayoutManager(new LinearLayoutManager(getContext()));
        OrderGoodsAdapter adapter = new OrderGoodsAdapter();
        rv_goods.setAdapter(adapter);
        adapter.setNewData(DataUtil.getData(2));
        switch (item.type){
            case OrderFragment.UNPAY:
                tv_status.setText("待付款");
                tv_btn1.setText("取消");
                tv_btn2.setText("付款");
                tv_btn1.setVisibility(View.VISIBLE);
                tv_btn2.setVisibility(View.VISIBLE);
                break;
            case OrderFragment.UNSEND:
                tv_status.setText("待发货");
                tv_btn1.setText("详情");
                tv_btn1.setVisibility(View.VISIBLE);
                tv_btn2.setVisibility(View.GONE);
                break;
            case OrderFragment.UNRECEIVE:
                tv_status.setText("待收货");
                tv_btn1.setText("物流");
                tv_btn1.setText("收货");
                tv_btn1.setVisibility(View.VISIBLE);
                tv_btn2.setVisibility(View.VISIBLE);
                break;
            case OrderFragment.FINISH:
                tv_status.setText("已完成");
                tv_btn1.setText("详情");
                tv_btn2.setText("评价");
                tv_btn1.setVisibility(View.VISIBLE);
                tv_btn2.setVisibility(View.GONE);
                break;
            case OrderFragment.EVALUATION:
                tv_status.setText("已完成");
                tv_btn1.setText("详情");
                tv_btn2.setText("评价");
                tv_btn1.setVisibility(View.VISIBLE);
                tv_btn2.setVisibility(View.VISIBLE);
                break;
        }
        tv_btn1.setOnClickListener(view -> {
            switch (item.type){
                case OrderFragment.UNPAY:
                    SelectPayWayActivity.launch(getContext(),1,"");
                    break;

                case OrderFragment.UNRECEIVE:
                   //物流
                    break;
            }
        });


        LinearLayout ll_root = helper.getView(R.id.ll_root);
        ll_root.setOnClickListener(v -> {
            OrderDetailActivity.launch(getContext(),item.type);
        });
        ll_root.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                return false;
            }
        });


    }


}
