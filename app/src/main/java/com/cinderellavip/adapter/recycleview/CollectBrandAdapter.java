package com.cinderellavip.adapter.recycleview;


import android.view.View;
import android.widget.ImageView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.module.LoadMoreModule;
import com.chad.library.adapter.base.viewholder.BaseViewHolder;
import com.cinderellavip.R;
import com.cinderellavip.bean.net.find.FindItem;
import com.cinderellavip.global.ImageUtil;
import com.cinderellavip.ui.activity.home.BrandDetailActivity;


public class CollectBrandAdapter extends BaseQuickAdapter<FindItem, BaseViewHolder> implements LoadMoreModule {


    public CollectBrandAdapter() {
        super(R.layout.item_collect_brand, null);
    }


    @Override
    protected void convert(final BaseViewHolder helper, final FindItem item) {
        int position = helper.getAdapterPosition();

        View view_space = helper.getView(R.id.view_space);
        if (position == 0){
            view_space.setVisibility(View.VISIBLE);
        }else {
            view_space.setVisibility(View.GONE);

        }
        ImageView merchant_icon = helper.getView(R.id.merchant_icon);
        ImageUtil.loadNet(getContext(),merchant_icon,item.brand_logo);
        helper.setText(R.id.merchant_name,item.brand_name);
        helper.getView(R.id.ll_merchant).setOnClickListener(view -> {
            BrandDetailActivity.launch(getContext(),item.brand_id);
        });
    }




}
