package com.cinderellavip.adapter.recycleview;


import android.app.Activity;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.viewholder.BaseViewHolder;
import com.cinderellavip.R;
import com.cinderellavip.bean.local.HomeGoods;
import com.cinderellavip.global.ImageUtil;
import com.cinderellavip.util.ScreenUtil;


public class RecommentGoodsAdapter extends BaseQuickAdapter<HomeGoods, BaseViewHolder> {

    public RecommentGoodsAdapter() {
        super(R.layout.item_recommend_goods, null);
    }


    @Override
    protected void convert( BaseViewHolder helper,  HomeGoods item) {
        int position = helper.getAdapterPosition();
//        helper.setText(R.id.tv_number,item);
        LinearLayout ll_root = helper.getView(R.id.ll_root);
        ViewGroup.LayoutParams linearParams = ll_root.getLayoutParams(); //取控件textView当前的布局参数 linearParams.height = 20;// 控件的高强制设成20
        int screenWidth = ScreenUtil.getScreenWidth((Activity) getContext());
        linearParams.width = (int) (screenWidth/3.5);// 控件的宽强制设成30
        ll_root.setLayoutParams(linearParams); //使设置好的布局参数应用到控件


        ImageView iv_image = helper.getView(R.id.iv_image);
        ImageUtil.loadNet(getContext(),iv_image,item.thumb);
        helper.setText(R.id.tv_name,item.name)
                .setText(R.id.tv_price,"￥"+item.getPrice());



    }



}
