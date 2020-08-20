package com.cinderellavip.adapter.recycleview;


import android.widget.ImageView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.module.LoadMoreModule;
import com.chad.library.adapter.base.viewholder.BaseViewHolder;
import com.cinderellavip.R;
import com.cinderellavip.bean.SearchStore;
import com.cinderellavip.global.ImageUtil;
import com.cinderellavip.ui.activity.home.ShopDetailActivity;

public class SearchShopAdapter extends BaseQuickAdapter<SearchStore, BaseViewHolder> implements LoadMoreModule {


    public SearchShopAdapter() {
        super(R.layout.item_search_shop, null);
    }


    @Override
    protected void convert(final BaseViewHolder helper, final SearchStore item) {
        int position = helper.getAdapterPosition();
        ImageView iv_product = helper.getView(R.id.iv_product);
        ImageUtil.load(getContext(),iv_product,item.logo);
        helper.setText(R.id.tv_title,item.name);
        helper.getView(R.id.ll_root).setOnClickListener(v -> {
            ShopDetailActivity.launchShop(getContext(),item.id+"");
        });
//
    }

}
