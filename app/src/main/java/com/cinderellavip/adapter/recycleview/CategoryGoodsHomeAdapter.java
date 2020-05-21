package com.cinderellavip.adapter.recycleview;


import android.widget.ImageView;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.viewholder.BaseViewHolder;
import com.cinderellavip.R;
import com.cinderellavip.bean.net.HomeCategoryItem;
import com.cinderellavip.global.ImageUtil;
import com.cinderellavip.ui.activity.home.GoodsListActivity;


public class CategoryGoodsHomeAdapter extends BaseQuickAdapter<HomeCategoryItem, BaseViewHolder> {

    public CategoryGoodsHomeAdapter() {
        super(R.layout.item_category_goods, null);
    }


    @Override
    protected void convert( BaseViewHolder helper,  HomeCategoryItem item) {
        int position = helper.getAdapterPosition();

        ImageView iv_image = helper.getView(R.id.iv_image);
        ImageUtil.loadNet(getContext(),iv_image,item.image);
        TextView tv_title = helper.getView(R.id.tv_title);
        tv_title.setText(item.name);
        helper.getView(R.id.ll_root).setOnClickListener(view -> {
            GoodsListActivity.launch(getContext(),item.name,item.id);
        });
//

    }


}
