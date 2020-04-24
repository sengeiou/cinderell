package com.cinderellavip.adapter.recycleview;


import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.viewholder.BaseViewHolder;
import com.cinderellavip.R;
import com.cinderellavip.bean.local.CouponsBean;


public class CouponReceiveDialogForServiceAdapter extends BaseQuickAdapter<CouponsBean, BaseViewHolder> {


    public CouponReceiveDialogForServiceAdapter() {
        super(R.layout.item_coupon_receive_dialog, null);
    }


    @Override
    protected void convert(BaseViewHolder helper, CouponsBean item) {
        int position = helper.getAdapterPosition();
//        RelativeLayout ll_root = helper.getView(R.id.ll_root);
        TextView tv_money_unit = helper.getView(R.id.tv_money_unit);
        TextView tv_money = helper.getView(R.id.tv_money);
        TextView tv_money_condition = helper.getView(R.id.tv_money_condition);
        TextView tv_use = helper.getView(R.id.tv_use);

        tv_money_unit.setTextColor(getContext().getColor(R.color.yellow_deep));
        tv_money.setTextColor(getContext().getColor(R.color.yellow_deep));
        tv_money_condition.setTextColor(getContext().getColor(R.color.yellow_deep));
        tv_use.setBackgroundResource(R.drawable.shape_yellow50_life_deep);
//
        ImageView iv_selete = helper.getView(R.id.iv_selete);

//
        switch (item.status) {
            case CouponsBean.NORMAL:
            case CouponsBean.OBTAINED:
                iv_selete.setVisibility(View.GONE);
                tv_use.setVisibility(View.VISIBLE);
                break;
            case CouponsBean.RECEIVED:
                //已领取
                iv_selete.setVisibility(View.VISIBLE);
                iv_selete.setImageResource(R.mipmap.yilingqu);
                tv_use.setVisibility(View.GONE);
                break;
            case CouponsBean.NO_HAVE:
                //已抢光
                iv_selete.setVisibility(View.VISIBLE);
                iv_selete.setImageResource(R.mipmap.yiqinagguang);
                tv_use.setVisibility(View.GONE);
                break;
        }
//        tv_use.setOnClickListener(v -> {
//            CouponUtil.receiveCoupon(mContext,item.coupons_id+"",()->{
//                item.setStatus(CouponsBean.RECEIVED);
//                notifyDataSetChanged();
//            });
//        });

    }




}
