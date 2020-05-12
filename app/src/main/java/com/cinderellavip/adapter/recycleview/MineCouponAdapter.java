package com.cinderellavip.adapter.recycleview;


import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.viewholder.BaseViewHolder;
import com.cinderellavip.R;
import com.cinderellavip.bean.local.MineCouponsBean;
import com.cinderellavip.ui.activity.home.ShopDetailActivity;


public class MineCouponAdapter extends BaseQuickAdapter<MineCouponsBean, BaseViewHolder> {

    private int type;

    public MineCouponAdapter(int type) {
        super(R.layout.item_coupon, null);
        this.type = type;
    }


    @Override
    protected void convert(final BaseViewHolder helper, final MineCouponsBean item) {
        int position = helper.getAdapterPosition();

        RelativeLayout ll_root = helper.getView(R.id.ll_root);
        TextView tv_money_unit = helper.getView(R.id.tv_money_unit);
        TextView tv_money = helper.getView(R.id.tv_money);
        TextView tv_complain = helper.getView(R.id.tv_complain);
        TextView tv_name = helper.getView(R.id.tv_name);
        TextView tv_time = helper.getView(R.id.tv_time);
        TextView tv_type = helper.getView(R.id.tv_type);
        TextView tv_use = helper.getView(R.id.tv_use);
        ImageView iv_selete = helper.getView(R.id.iv_selete);
        iv_selete.setVisibility(View.GONE);

        helper.setText(R.id.tv_name,item.title)
                .setText(R.id.tv_money,item.getAmount())
                .setText(R.id.tv_time,item.expire_date)
                .setText(R.id.tv_type,item.type)
                .setText(R.id.tv_complain,item.condition);



//        tv_money.setText(item.getSale_money() + "");
//        tv_complain.setText("满" + item.getMeet_money() + "可用");
//        helper.setText(R.id.tv_name, item.coupons_name);
//        helper.setText(R.id.tv_type, item.getType());
//        tv_time.setText("有效期:"+item.validtime);
        switch (item.status) {
            case 1:
                ll_root.setBackgroundResource(R.mipmap.my_coupon_red_bg);
                tv_money_unit.setTextColor(getContext().getResources().getColor(R.color.baseColor));
                tv_money.setTextColor(getContext().getResources().getColor(R.color.baseColor));
                tv_complain.setTextColor(getContext().getResources().getColor(R.color.baseColor));
                tv_use.setVisibility(View.VISIBLE);
                break;

            case 2:
            case 3:
                ll_root.setBackgroundResource(R.mipmap.my_coupon_ysy_bg);
                tv_money_unit.setTextColor(getContext().getResources().getColor(R.color.grayText));
                tv_money.setTextColor(getContext().getResources().getColor(R.color.grayText));
                tv_complain.setTextColor(getContext().getResources().getColor(R.color.grayText));
                tv_use.setVisibility(View.GONE);
                break;
        }
        tv_use.setOnClickListener(v -> {
            ShopDetailActivity.launchShop(getContext(),item.store_id+"");
//            switch (item.id){
//                case 0:
//                    //通用
//                case 1:
//                    //品牌
////                    BrandDetailActivity.launch((Activity) mContext,item.coupons_type_id);
//                case 2:
//                    //一级分类
//                case 3:
//                    //二级分类
//                case 4:
//                    //商家
//                    break;
//
//            }
        });



    }


}
