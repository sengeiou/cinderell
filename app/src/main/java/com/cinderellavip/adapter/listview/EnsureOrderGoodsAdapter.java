package com.cinderellavip.adapter.listview;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.cinderellavip.R;
import com.cinderellavip.base.BaseAdapter;
import com.cinderellavip.bean.net.order.OrderSettleGoods;
import com.cinderellavip.global.ImageUtil;
import com.cinderellavip.weight.RoundImageView;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class EnsureOrderGoodsAdapter extends BaseAdapter<OrderSettleGoods> {



    public EnsureOrderGoodsAdapter(List<OrderSettleGoods> mList, Context context) {
        super(mList, context);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        final ViewHolder hodler;

        if (convertView == null) {
            convertView = View.inflate(context, R.layout.item_ensure_order_goods, null);
            hodler = new ViewHolder(convertView);
            convertView.setTag(hodler);
        } else {
            hodler = (ViewHolder) convertView.getTag();
        }
        OrderSettleGoods item = mList.get(position);
        ImageUtil.loadNet(context,hodler.ivProduct,item.product_thumb);
        hodler.tvTitle.setText(item.product_name);
        hodler.tvSpecification.setText("规格："+item.product_norm);
        hodler.tvNumber.setText("数量：X"+item.product_num);
        hodler.tvPrice.setText("￥"+item.product_price);

        return convertView;
    }

    static class ViewHolder {

        @BindView(R.id.iv_product)
        RoundImageView ivProduct;
        @BindView(R.id.tv_title)
        TextView tvTitle;
        @BindView(R.id.tv_specification)
        TextView tvSpecification;
        @BindView(R.id.tv_number)
        TextView tvNumber;
        @BindView(R.id.tv_price)
        TextView tvPrice;
        ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }
}
