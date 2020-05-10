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
import com.tozzais.baselibrary.http.RxHttp;

import java.util.TreeMap;


public class CouponReceiveDialogAdapter extends BaseQuickAdapter<CouponsBean, BaseViewHolder> {


    public CouponReceiveDialogAdapter() {
        super(R.layout.item_coupon_receive_dialog, null);
    }


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

        helper.setText(R.id.tv_title,item.title)
                .setText(R.id.tv_money,item.getAmount())
                .setText(R.id.tv_money_condition,item.condition)
                .setText(R.id.tv_complain,item.type);
//        iv_selete.setVisibility(View.GONE);
//
//        tv_money.setText(item.getSale_money());
//        tv_complain.setText(String.format("满%s可用", item.getMeet_money()));
//        tv_name.setText(item.coupons_name);
        if (!item.status){
            //已领取
            iv_selete.setVisibility(View.VISIBLE);
            iv_selete.setImageResource(R.mipmap.yilingqu);
            tv_use.setVisibility(View.GONE);
        }else {
            iv_selete.setVisibility(View.GONE);
            tv_use.setVisibility(View.VISIBLE);
        }

        tv_use.setOnClickListener(view -> {
            receiveCoupons(item);
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
