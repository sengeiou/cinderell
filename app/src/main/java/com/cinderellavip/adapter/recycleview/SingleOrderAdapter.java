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
import com.cinderellavip.ui.activity.life.SingleServiceOrderDetailActivity;
import com.cinderellavip.ui.activity.life.SingleServiceOrderListActivity;
import com.cinderellavip.ui.activity.order.OrderDetailActivity;
import com.cinderellavip.ui.activity.order.SelectPayWayActivity;
import com.cinderellavip.ui.fragment.mine.OrderFragment;
import com.cinderellavip.util.DataUtil;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class SingleOrderAdapter extends BaseQuickAdapter<Integer, BaseViewHolder> implements LoadMoreModule {


    public SingleOrderAdapter() {
        super(R.layout.item_order_single, null);
    }


    @Override
    protected void convert(BaseViewHolder helper, Integer item) {
        int position = helper.getAdapterPosition();
        TextView tv_status = helper.getView(R.id.tv_status);
        TextView tv_btn1 = helper.getView(R.id.tv_btn1);
        TextView tv_btn2 = helper.getView(R.id.tv_btn2);
        switch (item) {
            case SingleServiceOrderListActivity.PAY:
                tv_status.setText("待付款");
                tv_btn1.setText("取消");
                tv_btn2.setText("支付");
                tv_btn1.setVisibility(View.VISIBLE);
                tv_btn2.setVisibility(View.VISIBLE);
                break;
            case SingleServiceOrderListActivity.ALL:
                tv_status.setText("已取消");
                tv_btn1.setText("详情");
                tv_btn1.setVisibility(View.VISIBLE);
                tv_btn2.setVisibility(View.GONE);
                break;
            case SingleServiceOrderListActivity.SERVICE:
                tv_status.setText("待服务");
                tv_btn1.setText("详情");
                tv_btn1.setVisibility(View.VISIBLE);
                tv_btn2.setVisibility(View.GONE);
                break;
            case SingleServiceOrderListActivity.COMMENT:
                tv_status.setText("待评价");
                tv_btn2.setText("评价");
                tv_btn1.setVisibility(View.GONE);
                tv_btn2.setVisibility(View.VISIBLE);
                break;
            case SingleServiceOrderListActivity.FINISH:
                tv_status.setText("已完成");
                tv_btn1.setText("详情");
                tv_btn1.setVisibility(View.VISIBLE);
                tv_btn2.setVisibility(View.GONE);
                break;
        }
        tv_btn1.setOnClickListener(view -> {

        });
        LinearLayout ll_root = helper.getView(R.id.ll_root);
        ll_root.setOnClickListener(v -> {
            SingleServiceOrderDetailActivity.launch(getContext(), item);
        });
        ll_root.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                return false;
            }
        });


    }


}
