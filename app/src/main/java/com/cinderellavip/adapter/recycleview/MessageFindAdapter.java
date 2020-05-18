package com.cinderellavip.adapter.recycleview;


import android.widget.LinearLayout;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.viewholder.BaseViewHolder;
import com.cinderellavip.R;
import com.cinderellavip.bean.local.OrderBean;

public class MessageFindAdapter extends BaseQuickAdapter<OrderBean, BaseViewHolder> {


    public MessageFindAdapter() {
        super(R.layout.item_message_mine_asset, null);
    }

    @Override
    protected void convert(BaseViewHolder helper,  OrderBean item) {
        int position = helper.getAdapterPosition();




        LinearLayout ll_root = helper.getView(R.id.ll_root);
         ll_root.setOnClickListener(v -> {
//            PostDetailActivity.launch((Activity) getContext());
        });





    }


}
