package com.cinderellavip.adapter.recycleview;

import android.app.Activity;
import android.graphics.drawable.Drawable;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.viewholder.BaseViewHolder;
import com.cinderellavip.R;
import com.cinderellavip.adapter.listview.EnsureOrderGoodsAdapter;
import com.cinderellavip.bean.net.order.OrderSettleShopAmount;
import com.cinderellavip.bean.net.order.OrderSettleShopBean;
import com.cinderellavip.ui.activity.mine.SelectCouponActivity;
import com.cinderellavip.util.DataUtil;
import com.cinderellavip.weight.MyListView;

import androidx.core.content.ContextCompat;


public class EnsureOrderAdapter extends BaseQuickAdapter<OrderSettleShopBean, BaseViewHolder> {

    public EnsureOrderAdapter() {
        super(R.layout.item_ensure_order, null);
    }


    @Override
    protected void convert( BaseViewHolder helper, OrderSettleShopBean item) {
        int position = helper.getAdapterPosition();

        OrderSettleShopAmount amount = item.amount;
        helper.setText(R.id.tv_shop,item.store_name)
                .setText(R.id.tv_tax,"￥"+amount.ship)
                .setText(R.id.tv_goods_money,"￥"+amount.total);


        MyListView lv_goods = helper.getView(R.id.lv_goods);
        EnsureOrderGoodsAdapter ensureOrderAdapter = new EnsureOrderGoodsAdapter(item.products, getContext());
        lv_goods.setAdapter(ensureOrderAdapter);



        LinearLayout ll_coupon = helper.getView(R.id.ll_coupon);
        TextView tv_coupon = helper.getView(R.id.tv_coupon);
        Drawable nav_up = ContextCompat.getDrawable(getContext(),R.mipmap.right_black_icon);
        nav_up.setBounds(0, 0, nav_up.getMinimumWidth(), nav_up.getMinimumHeight());
        if (!item.has_coupon){
            ll_coupon.setEnabled(false);
            tv_coupon.setText("暂无优惠券");
            tv_coupon.setTextColor(getContext().getColor(R.color.grayText));
            tv_coupon.setCompoundDrawables(null, null, null, null);
        }else {
            ll_coupon.setEnabled(true);
            if (amount.isSelectCoupon()){
                tv_coupon.setText("￥"+amount.coupon);
            }else {
                tv_coupon.setText("选择优惠券");
            }
            tv_coupon.setTextColor(getContext().getColor(R.color.black_title_color));
            tv_coupon.setCompoundDrawables(null, null, nav_up, null);
        }

        TextView tv_active = helper.getView(R.id.tv_active);
        if (amount.isActive()){
            tv_active.setText("暂无活动");
            tv_active.setTextColor(getContext().getColor(R.color.grayText));
        }else {
            tv_active.setText("￥"+amount.active);
            tv_active.setTextColor(getContext().getColor(R.color.black_title_color));
        }
        ll_coupon.setOnClickListener(v -> {
            SelectCouponActivity.launch((Activity) getContext());
        });


    }







}
