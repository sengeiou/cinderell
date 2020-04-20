package com.cinderellavip.adapter.recycleview;


import android.annotation.SuppressLint;
import android.view.MotionEvent;
import android.widget.LinearLayout;
import android.widget.TextView;


import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.viewholder.BaseViewHolder;
import com.cinderellavip.R;
import com.cinderellavip.bean.local.OrderBean;
import com.cinderellavip.ui.activity.order.RefundDetailActivity;
import com.cinderellavip.util.DataUtil;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class RefundAdapter extends BaseQuickAdapter<OrderBean, BaseViewHolder> {


    public RefundAdapter() {
        super(R.layout.item_refund, null);
    }

    @SuppressLint("ClickableViewAccessibility")
    @Override
    protected void convert(BaseViewHolder helper,  OrderBean item) {
        int position = helper.getAdapterPosition();

        TextView tv_btn1 = helper.getView(R.id.tv_btn1);
        TextView tv_status = helper.getView(R.id.tv_status);
//
//        rv_goods.setAdapter(adapter);

        RecyclerView rv_goods = helper.getView(R.id.rv_goods);
        rv_goods.setLayoutManager(new LinearLayoutManager(getContext()));
        OrderGoodsAdapter adapter = new OrderGoodsAdapter(OrderGoodsAdapter.RETURN);
        rv_goods.setAdapter(adapter);
        adapter.setNewData(DataUtil.getData(2));
        switch (item.type){
            case 0:
                tv_status.setText("审核中");
                break;
            case 1:
                tv_status.setText("审核通过");
                break;
            case 2:
                tv_status.setText("审核失败");
                break;
        }



        LinearLayout ll_root = helper.getView(R.id.ll_root);
        tv_btn1.setOnClickListener(v -> {
            RefundDetailActivity.launch(getContext(),item.type);
        }); ll_root.setOnClickListener(v -> {
            RefundDetailActivity.launch(getContext(),item.type);
        });

        rv_goods.setOnTouchListener((v, event) -> {
            if (event.getAction() == MotionEvent.ACTION_UP) {
                ll_root.performClick();  //模拟父控件的点击
            }
            return false;
        });



    }


}
