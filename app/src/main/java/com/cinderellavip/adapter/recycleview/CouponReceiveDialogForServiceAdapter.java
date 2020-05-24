package com.cinderellavip.adapter.recycleview;


import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.viewholder.BaseViewHolder;
import com.cinderellavip.R;
import com.cinderellavip.bean.net.life.LifeCoupon;
import com.cinderellavip.http.ApiManager;
import com.cinderellavip.http.BaseResult;
import com.cinderellavip.http.Response;
import com.tozzais.baselibrary.http.RxHttp;

import java.util.TreeMap;


public class CouponReceiveDialogForServiceAdapter extends BaseQuickAdapter<LifeCoupon, BaseViewHolder> {


    public CouponReceiveDialogForServiceAdapter() {
        super(R.layout.item_coupon_receive_dialog, null);
    }


    @Override
    protected void convert(BaseViewHolder helper, LifeCoupon item) {
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

        helper.setText(R.id.tv_title,item.title)
                .setText(R.id.tv_money,item.getLess())
                .setText(R.id.tv_money_condition,"满"+item.getFull()+"减"+item.getLess())
                .setText(R.id.tv_complain,item.getScenes());

        switch (item.receive) {
            case 0:
                iv_selete.setVisibility(View.GONE);
                tv_use.setVisibility(View.VISIBLE);
                break;
            case 1:
                //已领取
                iv_selete.setVisibility(View.VISIBLE);
                iv_selete.setImageResource(R.mipmap.yilingqu);
                tv_use.setVisibility(View.GONE);
                break;
            case 2:
                //已抢光
                iv_selete.setVisibility(View.VISIBLE);
                iv_selete.setImageResource(R.mipmap.yiqinagguang);
                tv_use.setVisibility(View.GONE);
                break;
        }
        tv_use.setOnClickListener(v -> {
            receive(item);
        });


    }

    public void receive(LifeCoupon coupon) {
        TreeMap<String, String> hashMap = new TreeMap<>();
        hashMap.put("coupon", ""+coupon.id);
        new RxHttp<BaseResult>().send(ApiManager.getService().receiveCategoryCoupon(hashMap),
                new Response<BaseResult>(getContext()) {
                    @Override
                    public void onSuccess(BaseResult result) {
                        coupon.receive = 1;
                        notifyDataSetChanged();
                    }
                });


    }




}
