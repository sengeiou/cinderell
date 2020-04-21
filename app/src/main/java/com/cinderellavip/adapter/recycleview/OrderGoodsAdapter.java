package com.cinderellavip.adapter.recycleview;

import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.viewholder.BaseViewHolder;
import com.cinderellavip.R;


public class OrderGoodsAdapter extends BaseQuickAdapter<String, BaseViewHolder> {

    public OrderGoodsAdapter() {
        super(R.layout.item_order_goods, null);
    }
    //退款列表 拼团列表
    public static final int RETURN = 1;
    int type;
    public OrderGoodsAdapter(int type) {
        super(R.layout.item_order_goods, null);
        this.type = type;
    }



    @Override
    protected void convert(final BaseViewHolder helper, final String item) {
        int position = helper.getAdapterPosition();
        TextView tv_price = helper.getView(R.id.tv_price);
        if (type == RETURN){
            tv_price.setTextColor(getContext().getColor(R.color.black_title_color));
        }else {
            tv_price.setTextColor(getContext().getColor(R.color.baseColor));
        }

    }


}
