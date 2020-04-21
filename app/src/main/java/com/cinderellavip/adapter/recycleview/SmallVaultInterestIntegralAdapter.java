package com.cinderellavip.adapter.recycleview;


import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.module.LoadMoreModule;
import com.chad.library.adapter.base.viewholder.BaseViewHolder;
import com.cinderellavip.R;


public class SmallVaultInterestIntegralAdapter extends BaseQuickAdapter<String, BaseViewHolder> implements LoadMoreModule {


    public SmallVaultInterestIntegralAdapter() {
        super(R.layout.item_smallvault_interest_integral, null);
    }


    @Override
    protected void convert(final BaseViewHolder helper, final String item) {
        int position = helper.getAdapterPosition();
    }




}
