package com.cinderellavip.adapter.recycleview;


import android.view.MotionEvent;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.module.LoadMoreModule;
import com.chad.library.adapter.base.viewholder.BaseViewHolder;
import com.cinderellavip.R;
import com.cinderellavip.ui.activity.life.LongServiceOrderDetailActivity;

public class LongServiceOrderAdapter extends BaseQuickAdapter<Integer, BaseViewHolder> implements LoadMoreModule {


    /**
     * 是不是长期服务订单
     */
    private boolean isLongServiceOrder;
    public LongServiceOrderAdapter() {
        super(R.layout.item_order_single, null);
    }
    public LongServiceOrderAdapter(boolean isLongServiceOrder) {
        super(R.layout.item_order_single, null);
        this.isLongServiceOrder = isLongServiceOrder;
    }


    @Override
    protected void convert(BaseViewHolder helper, Integer item) {
        int position = helper.getAdapterPosition();
        TextView tv_status = helper.getView(R.id.tv_status);
        TextView tv_btn1 = helper.getView(R.id.tv_btn1);
        TextView tv_btn2 = helper.getView(R.id.tv_btn2);
        switch (item) {
            case 0:
                tv_status.setText("已取消");
                tv_btn1.setText("详情");
                tv_btn1.setVisibility(View.VISIBLE);
                tv_btn2.setVisibility(View.GONE);
                break;
            case 1:
                tv_status.setText("待确认");
                tv_btn1.setText("取消");
                tv_btn2.setText("确认");
                tv_btn1.setVisibility(View.VISIBLE);
                tv_btn2.setVisibility(View.VISIBLE);
                break;
            case 2:
                tv_status.setText("待支付");
                tv_btn1.setText("详情");
                tv_btn2.setText("支付");
                tv_btn1.setVisibility(View.VISIBLE);
                tv_btn2.setVisibility(View.VISIBLE);
                break;
            case 3:
                tv_status.setText("服务中");
                tv_btn1.setText("详情");
                tv_btn1.setVisibility(View.VISIBLE);
                tv_btn2.setVisibility(View.GONE);
                break;
            case 4:
                tv_status.setText("已完成");
                tv_btn1.setText("详情");
                tv_btn1.setVisibility(View.VISIBLE);
                tv_btn2.setVisibility(View.GONE);
                break;
            case 5:
                tv_status.setText("待评价");
                tv_btn2.setText("评价");
                tv_btn1.setVisibility(View.GONE);
                tv_btn2.setVisibility(View.VISIBLE);
                break;
        }
        tv_btn1.setOnClickListener(view -> {

        });
        LinearLayout ll_root = helper.getView(R.id.ll_root);
        ll_root.setOnClickListener(v -> {
            LongServiceOrderDetailActivity.launch(getContext(), item);
        });
        ll_root.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                return false;
            }
        });


    }


}
