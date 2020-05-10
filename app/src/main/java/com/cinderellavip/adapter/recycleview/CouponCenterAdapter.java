package com.cinderellavip.adapter.recycleview;


import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.module.LoadMoreModule;
import com.chad.library.adapter.base.viewholder.BaseViewHolder;
import com.cinderellavip.R;
import com.cinderellavip.bean.local.CouponsBean;


public class CouponCenterAdapter extends BaseQuickAdapter<CouponsBean, BaseViewHolder> implements LoadMoreModule {

    private int type;

    public CouponCenterAdapter(int type) {
        super(R.layout.item_coupon_center, null);
        this.type = type;
    }


    @Override
    protected void convert(final BaseViewHolder helper, final CouponsBean item) {
        int position = helper.getAdapterPosition();


        ImageView iv_status = helper.getView(R.id.iv_status);
        TextView tv_use = helper.getView(R.id.tv_use);
//        iv_selete.setVisibility(View.GONE);


        //状态 0：正常 1：下架  2:已领取  3：已领完
        switch (item.id) {
            case 2:
                tv_use.setText("立即领取");
                iv_status.setVisibility(View.GONE);
                tv_use.setVisibility(View.VISIBLE);
                tv_use.setBackgroundResource(R.drawable.shape_red50);
                break;
            case 1:
                iv_status.setVisibility(View.VISIBLE);
                tv_use.setVisibility(View.VISIBLE);
                iv_status.setImageResource(R.mipmap.lqzx2);
                tv_use.setBackgroundResource(R.drawable.shape_graydeep50);
                tv_use.setText("立即领取");
                break;
            case 0:
                tv_use.setText("立即使用");
                tv_use.setBackgroundResource(R.drawable.shape_red50);
                iv_status.setImageResource(R.mipmap.lqzx1);
                iv_status.setVisibility(View.VISIBLE);
                tv_use.setVisibility(View.VISIBLE);
                break;

        }


    }



}
