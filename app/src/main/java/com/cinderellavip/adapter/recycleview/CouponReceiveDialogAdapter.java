package com.cinderellavip.adapter.recycleview;


import android.annotation.SuppressLint;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.viewholder.BaseViewHolder;
import com.cinderellavip.R;
import com.cinderellavip.bean.local.CouponsBean;


public class CouponReceiveDialogAdapter extends BaseQuickAdapter<CouponsBean, BaseViewHolder> {


    public CouponReceiveDialogAdapter() {
        super(R.layout.item_coupon_receive_dialog, null);
    }


    @SuppressLint("DefaultLocale")
    @Override
    protected void convert(BaseViewHolder helper, CouponsBean item) {
        int position = helper.getAdapterPosition();
//        RelativeLayout ll_root = helper.getView(R.id.ll_root);
//        TextView tv_money_unit = helper.getView(R.id.tv_money_unit);
//        TextView tv_money = helper.getView(R.id.tv_money);
//        TextView tv_complain = helper.getView(R.id.tv_complain);
//        TextView tv_name = helper.getView(R.id.tv_name);
//        TextView tv_time = helper.getView(R.id.tv_time);
        TextView tv_use = helper.getView(R.id.tv_use);
        ImageView iv_selete = helper.getView(R.id.iv_selete);
//        iv_selete.setVisibility(View.GONE);
//
//        tv_money.setText(item.getSale_money());
//        tv_complain.setText(String.format("满%s可用", item.getMeet_money()));
//        tv_name.setText(item.coupons_name);
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
