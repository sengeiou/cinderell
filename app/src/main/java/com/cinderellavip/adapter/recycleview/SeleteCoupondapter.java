package com.cinderellavip.adapter.recycleview;


import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.viewholder.BaseViewHolder;
import com.cinderellavip.R;
import com.cinderellavip.bean.local.CouponsBean;


public class SeleteCoupondapter extends BaseQuickAdapter<CouponsBean, BaseViewHolder> {


    public SeleteCoupondapter() {
        super(R.layout.item_coupon, null);
    }


    @Override
    protected void convert(final BaseViewHolder helper, final CouponsBean item) {
        int position = helper.getAdapterPosition();
//        RelativeLayout ll_root = helper.getView(R.id.ll_root);
//        TextView tv_money_unit = helper.getView(R.id.tv_money_unit);
//        TextView tv_money = helper.getView(R.id.tv_money);
//        TextView tv_complain = helper.getView(R.id.tv_complain);
//        TextView tv_name = helper.getView(R.id.tv_name);
//        TextView tv_time = helper.getView(R.id.tv_time);
//        TextView tv_type = helper.getView(R.id.tv_type);
        TextView tv_use = helper.getView(R.id.tv_use);
        ImageView iv_selete = helper.getView(R.id.iv_selete);
//
////        helper.setText(R.id.tv_money,item.getSale_money())
////                .setText(R.id.tv_complain,"满" + item.getMeet_money() + "可用")
////                .setText(R.id.tv_name,item.coupons_name)
////                .setText(R.id.tv_type,item.getType())
////                .setText(R.id.tv_time,"有效期:"+item.validtime);
//
////        ll_root.setBackgroundResource(R.mipmap.my_coupon_normal_bg);
////        tv_time.setText("有效期:"+item.validtime);
        if (item.isCheck){
            iv_selete.setVisibility(View.VISIBLE);
        }else {
            iv_selete.setVisibility(View.GONE);

        }
        tv_use.setVisibility(View.GONE);




    }


}
