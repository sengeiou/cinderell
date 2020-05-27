package com.cinderellavip.adapter.recycleview;


import android.widget.ImageView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.viewholder.BaseViewHolder;
import com.cinderellavip.R;
import com.cinderellavip.bean.net.life.LiftCategoryItem;
import com.cinderellavip.bean.net.life.LiftHomeCategory;
import com.cinderellavip.global.CinderellApplication;
import com.cinderellavip.global.ImageUtil;
import com.cinderellavip.ui.activity.life.ServiceDetailActivity;
import com.cinderellavip.ui.activity.life.ServiceListActivity;


public class CategoryGoodsAdapter extends BaseQuickAdapter<LiftCategoryItem, BaseViewHolder> {

    public CategoryGoodsAdapter() {
        super(R.layout.item_category_goods, null);
    }


    @Override
    protected void convert( BaseViewHolder helper,  LiftCategoryItem item) {
        int position = helper.getAdapterPosition();
        ImageView iv_image = helper.getView(R.id.iv_image);
        ImageUtil.loadNet(getContext(),iv_image,item.icon);
        helper.setText(R.id.tv_title,item.name);
        helper.getView(R.id.ll_root).setOnClickListener(view -> {
            if (item.superior == -1){
                ServiceDetailActivity.launch(getContext(),item.id, CinderellApplication.name);
            }else {
                LiftHomeCategory liftHomeCategory = new LiftHomeCategory();
                liftHomeCategory.name = item.name;
                liftHomeCategory.one = item.id;
                liftHomeCategory.three = -1;
                ServiceListActivity.launch(getContext(),liftHomeCategory);
            }

        });

    }


}
