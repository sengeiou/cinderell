package com.cinderellavip.adapter.recycleview;


import android.view.View;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.viewholder.BaseViewHolder;
import com.cinderellavip.R;
import com.cinderellavip.bean.net.life.LifeCoupon;


public class SelectServiceCouponAdapter extends BaseQuickAdapter<LifeCoupon, BaseViewHolder> {


    public SelectServiceCouponAdapter() {
        super(R.layout.item_coupon, null);
    }




    @Override
    protected void convert(final BaseViewHolder helper, final LifeCoupon item) {
        int position = helper.getAdapterPosition();
//        RelativeLayout ll_root = helper.getView(R.id.ll_root);
        TextView tv_money_unit = helper.getView(R.id.tv_money_unit);
        TextView tv_money = helper.getView(R.id.tv_money);
        TextView tv_complain = helper.getView(R.id.tv_complain);
        TextView tv_name = helper.getView(R.id.tv_name);
        TextView tv_time = helper.getView(R.id.tv_time);
        TextView tv_type = helper.getView(R.id.tv_type);
        TextView tv_use = helper.getView(R.id.tv_use);
        tv_money_unit.setTextColor(getContext().getColor(R.color.yellow_deep));
        tv_money.setTextColor(getContext().getColor(R.color.yellow_deep));
        tv_complain.setTextColor(getContext().getColor(R.color.yellow_deep));
        tv_use.setVisibility(View.GONE);


        tv_money.setText(item.getLess());
        tv_complain.setText("满"+item.getFull()+"元可用");
        tv_name.setText(item.title);
        tv_time.setText("有效期："+item.end);
        tv_type.setText(""+item.getScenes());




    }


}
