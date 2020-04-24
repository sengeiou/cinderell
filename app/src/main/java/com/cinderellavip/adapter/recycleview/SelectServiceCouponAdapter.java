package com.cinderellavip.adapter.recycleview;


import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.viewholder.BaseViewHolder;
import com.cinderellavip.R;
import com.cinderellavip.bean.local.CouponsBean;


public class SelectServiceCouponAdapter extends BaseQuickAdapter<CouponsBean, BaseViewHolder> {


    public SelectServiceCouponAdapter() {
        super(R.layout.item_coupon, null);
    }




    @Override
    protected void convert(final BaseViewHolder helper, final CouponsBean item) {
        int position = helper.getAdapterPosition();
//        RelativeLayout ll_root = helper.getView(R.id.ll_root);
        TextView tv_money_unit = helper.getView(R.id.tv_money_unit);
        TextView tv_money = helper.getView(R.id.tv_money);
        TextView tv_complain = helper.getView(R.id.tv_complain);
//        TextView tv_name = helper.getView(R.id.tv_name);
//        TextView tv_time = helper.getView(R.id.tv_time);
//        TextView tv_type = helper.getView(R.id.tv_type);
        TextView tv_use = helper.getView(R.id.tv_use);
        tv_money_unit.setTextColor(getContext().getColor(R.color.yellow_deep));
        tv_money.setTextColor(getContext().getColor(R.color.yellow_deep));
        tv_complain.setTextColor(getContext().getColor(R.color.yellow_deep));
        tv_use.setVisibility(View.GONE);




    }


}
