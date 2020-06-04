package com.cinderellavip.adapter.recycleview;


import android.app.Activity;
import android.graphics.Paint;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.viewholder.BaseViewHolder;
import com.cinderellavip.R;
import com.cinderellavip.bean.net.home.HomeSpikeItem;
import com.cinderellavip.global.ImageUtil;
import com.cinderellavip.ui.activity.home.GoodsDetailActivity;
import com.cinderellavip.util.ScreenUtil;


public class SpikeHomeAdapter extends BaseQuickAdapter<HomeSpikeItem, BaseViewHolder> {

    public SpikeHomeAdapter() {
        super(R.layout.item_spike_home, null);
    }


    @Override
    protected void convert( BaseViewHolder helper,  HomeSpikeItem item) {
        int position = helper.getAdapterPosition();
//        helper.setText(R.id.tv_number,item);
        LinearLayout ll_root = helper.getView(R.id.ll_root);
        ViewGroup.LayoutParams linearParams = ll_root.getLayoutParams(); //取控件textView当前的布局参数 linearParams.height = 20;// 控件的高强制设成20
        int screenWidth = ScreenUtil.getScreenWidth((Activity) getContext());
        linearParams.width = (int) (screenWidth/4);// 控件的宽强制设成30
        ll_root.setLayoutParams(linearParams); //使设置好的布局参数应用到控件

        TextView tv_former_price =  helper.getView(R.id.tv_former_price);
        tv_former_price.getPaint().setFlags(Paint. STRIKE_THRU_TEXT_FLAG); //中划线

        ImageView iv_image = helper.getView(R.id.iv_image);
        ImageUtil.loadNet(getContext(),iv_image,item.thumb);

        helper.setText(R.id.tv_name,item.name)
                .setText(R.id.tv_price,"￥"+item.getSpikePrice())
                .setText(R.id.tv_former_price,"￥"+item.getBuyPrice());


        ll_root.setOnClickListener(v -> {
            GoodsDetailActivity.launch((Activity) getContext(),item.id+"");
        });

    }



}
