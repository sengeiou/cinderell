package com.cinderellavip.adapter.recycleview;


import android.view.View;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.module.LoadMoreModule;
import com.chad.library.adapter.base.viewholder.BaseViewHolder;
import com.cinderellavip.R;
import com.cinderellavip.bean.net.find.FindItem;
import com.cinderellavip.ui.activity.home.ShopDetailActivity;


public class CollectShopAdapter extends BaseQuickAdapter<FindItem, BaseViewHolder> implements LoadMoreModule {


    public CollectShopAdapter() {
        super(R.layout.item_collect_shop, null);
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
        helper.getView(R.id.ll_root).setOnClickListener(view -> {
            ShopDetailActivity.launchShop(getContext(),item.store_id);
        });
        helper.setText(R.id.tv_title,item.store_name);
    }




}
