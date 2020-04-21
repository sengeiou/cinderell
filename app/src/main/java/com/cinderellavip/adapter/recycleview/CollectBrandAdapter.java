package com.cinderellavip.adapter.recycleview;


import android.view.View;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.module.LoadMoreModule;
import com.chad.library.adapter.base.viewholder.BaseViewHolder;
import com.cinderellavip.R;
import com.cinderellavip.ui.activity.home.BrandDetailActivity;


public class CollectBrandAdapter extends BaseQuickAdapter<String, BaseViewHolder> implements LoadMoreModule {


    public CollectBrandAdapter() {
        super(R.layout.item_collect_brand, null);
    }


    @Override
    protected void convert(final BaseViewHolder helper, final String item) {
        int position = helper.getAdapterPosition();

        View view_space = helper.getView(R.id.view_space);
        if (position == 0){
            view_space.setVisibility(View.VISIBLE);
        }else {
            view_space.setVisibility(View.GONE);

        }
        helper.getView(R.id.ll_merchant).setOnClickListener(view -> {
            BrandDetailActivity.launch(getContext());
        });
    }




}
