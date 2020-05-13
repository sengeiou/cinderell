package com.cinderellavip.adapter.recycleview;

import android.widget.ImageView;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.viewholder.BaseViewHolder;
import com.cinderellavip.R;
import com.cinderellavip.bean.net.order.OrderGoodsInfo;
import com.cinderellavip.global.ImageUtil;


public class OrderGoodsAdapter extends BaseQuickAdapter<OrderGoodsInfo, BaseViewHolder> {

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
    protected void convert(final BaseViewHolder helper, final OrderGoodsInfo item) {
        int position = helper.getAdapterPosition();
        TextView tv_price = helper.getView(R.id.tv_price);
        if (type == RETURN){
            tv_price.setTextColor(getContext().getColor(R.color.black_title_color));
        }else {
            tv_price.setTextColor(getContext().getColor(R.color.baseColor));
        }
        ImageView iv_product =  helper.getView(R.id.iv_product);
        ImageUtil.loadNet(getContext(),iv_product,item.product_thumb);
        helper.setText(R.id.tv_title,item.product_name)
                .setText(R.id.tv_specification,"规格："+item.product_norm)
                .setText(R.id.tv_price,"￥"+item.product_price)
                .setText(R.id.tv_number,"数量：X"+item.product_num);


    }


}
