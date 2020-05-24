package com.cinderellavip.adapter.recycleview;


import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.viewholder.BaseViewHolder;
import com.cinderellavip.R;
import com.cinderellavip.bean.net.life.LiftHomeServiceItem;
import com.cinderellavip.global.ImageUtil;
import com.cinderellavip.ui.activity.life.BuyServiceActivity;
import com.cinderellavip.ui.activity.life.ServiceDetailActivity;


public class ServiceListProjectAdapter extends BaseQuickAdapter<LiftHomeServiceItem, BaseViewHolder> {

    public ServiceListProjectAdapter() {
        super(R.layout.item_servicelist_project, null);
    }


    @Override
    protected void convert( BaseViewHolder helper,  LiftHomeServiceItem item) {
        int position = helper.getAdapterPosition();
//        View view_space = helper.getView(R.id.view_space);
        ImageView iv_product = helper.getView(R.id.iv_product);
        TextView tv_contain = helper.getView(R.id.tv_contain);
        if (item.typesof == 1){
            tv_contain.setVisibility(View.GONE);
        }else {
            tv_contain.setVisibility(View.VISIBLE);
            tv_contain.setText("服务项目："+item.contain);
        }
        ImageUtil.loadNet(getContext(),iv_product,item.thumb_nail);
        helper.setText(R.id.tv_title,item.title)
                .setText(R.id.tv_specification,"服务时长："+item.duration+"")
                .setText(R.id.tv_price,"￥"+item.price+"/"+item.unit_name);
//        if (position == 0)
//        view_space.setVisibility(View.GONE);
//        else
//        view_space.setVisibility(View.VISIBLE);

        helper.getView(R.id.tv_buy).setOnClickListener(view -> {
            BuyServiceActivity.launch(getContext(),item.id);
        });
        helper.getView(R.id.ll_root).setOnClickListener(view -> {
            ServiceDetailActivity.launch(getContext(),item.id);
        });


    }



}
