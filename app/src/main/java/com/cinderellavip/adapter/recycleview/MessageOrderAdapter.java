package com.cinderellavip.adapter.recycleview;


import android.app.Activity;
import android.widget.LinearLayout;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.viewholder.BaseViewHolder;
import com.cinderellavip.R;
import com.cinderellavip.bean.local.OrderBean;
import com.cinderellavip.ui.activity.mine.MineOrderActivity;

public class MessageOrderAdapter extends BaseQuickAdapter<OrderBean, BaseViewHolder> {


    public MessageOrderAdapter() {
        super(R.layout.item_message_order, null);
    }

    @Override
    protected void convert(BaseViewHolder helper,  OrderBean item) {
        int position = helper.getAdapterPosition();




        LinearLayout ll_root = helper.getView(R.id.ll_root);
         ll_root.setOnClickListener(v -> {
            MineOrderActivity.launch((Activity) getContext(),item.id);
        });





    }


}
