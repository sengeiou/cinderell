package com.cinderellavip.adapter.recycleview;


import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.viewholder.BaseViewHolder;
import com.cinderellavip.R;
import com.cinderellavip.bean.local.CouponsBean;
import com.cinderellavip.http.ApiManager;
import com.cinderellavip.http.BaseResult;
import com.cinderellavip.http.Response;
import com.cinderellavip.ui.activity.home.ShopDetailActivity;
import com.tozzais.baselibrary.http.RxHttp;

import java.util.TreeMap;


public class CouponCenterAdapter extends BaseQuickAdapter<CouponsBean, BaseViewHolder> {

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

        helper.setText(R.id.tv_name,item.title)
                .setText(R.id.tv_money,item.getAmount())
                .setText(R.id.tv_meet_money,item.condition)
                .setText(R.id.tv_complain,item.type);
        if (!item.status){
            tv_use.setText("立即使用");
            tv_use.setBackgroundResource(R.drawable.shape_red50);
            iv_status.setImageResource(R.mipmap.lqzx1);
            iv_status.setVisibility(View.VISIBLE);
            tv_use.setVisibility(View.VISIBLE);
        }else {
            tv_use.setText("立即领取");
            iv_status.setVisibility(View.GONE);
            tv_use.setVisibility(View.VISIBLE);
            tv_use.setBackgroundResource(R.drawable.shape_red50);
        }

        tv_use.setOnClickListener(view -> {
            if(!item.status){
                ShopDetailActivity.launchShop(getContext(),item.store_id+"");
            }else {
                receiveCoupons(item);
            }

        });



    }

    private void receiveCoupons(CouponsBean couponsBean){
        TreeMap<String, String> hashMap = new TreeMap<>();
        hashMap.put("id", couponsBean.id+"");
        new RxHttp<BaseResult>().send(ApiManager.getService().getReceiveCoupons(hashMap),
                new Response<BaseResult>(getContext()) {
                    @Override
                    public void onSuccess(BaseResult result) {
                        couponsBean.status = false;
                        notifyDataSetChanged();

                    }
                });
    }



}
