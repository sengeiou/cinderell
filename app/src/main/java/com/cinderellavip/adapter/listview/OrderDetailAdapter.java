package com.cinderellavip.adapter.listview;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.cinderellavip.R;
import com.cinderellavip.base.BaseAdapter;
import com.cinderellavip.bean.net.order.OrderGoodsInfo;
import com.cinderellavip.global.ImageUtil;
import com.cinderellavip.ui.activity.order.ApplyReturnActivity;
import com.cinderellavip.weight.RoundImageView;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class OrderDetailAdapter extends BaseAdapter<OrderGoodsInfo> {




    public OrderDetailAdapter(List<OrderGoodsInfo> mList, Context context) {
        super(mList, context);
    }


    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        final ViewHolder hodler;

        if (convertView == null) {
            convertView = View.inflate(context, R.layout.item_order_goods, null);
            hodler = new ViewHolder(convertView);
            convertView.setTag(hodler);
        } else {
            hodler = (ViewHolder) convertView.getTag();

        }
        OrderGoodsInfo orderGoodsInfo = mList.get(position);
        if (orderGoodsInfo.refund_num > 0) {
            hodler.tvBtn1.setVisibility(View.VISIBLE);
        } else {
            hodler.tvBtn1.setVisibility(View.GONE);

        }
        ImageUtil.loadNet(context,hodler.ivProduct,orderGoodsInfo.product_thumb);
        hodler.tvTitle.setText(orderGoodsInfo.product_name);
        hodler.tvSpecification.setText("规格："+orderGoodsInfo.product_norm);
        hodler.tvPrice.setText("￥"+orderGoodsInfo.product_price);
        hodler.tvNumber.setText("数量：X"+orderGoodsInfo.product_num);


        hodler.tvBtn1.setOnClickListener(v -> {
            ApplyReturnActivity.launch(context, orderGoodsInfo);
        });


        return convertView;
    }

    static class ViewHolder {
        @BindView(R.id.tv_btn1)
        TextView tvBtn1;
        @BindView(R.id.iv_product)
        RoundImageView ivProduct;
        @BindView(R.id.tv_title)
        TextView tvTitle;
        @BindView(R.id.tv_specification)
        TextView tvSpecification;
        @BindView(R.id.tv_price)
        TextView tvPrice;
        @BindView(R.id.tv_number)
        TextView tvNumber;

        ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }
}
