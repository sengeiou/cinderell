package com.cinderellavip.adapter.recycleview;


import android.view.View;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.viewholder.BaseViewHolder;
import com.cinderellavip.R;


public class ServiceListProjectAdapter extends BaseQuickAdapter<String, BaseViewHolder> {

    public ServiceListProjectAdapter() {
        super(R.layout.item_servicelist_project, null);
    }


    @Override
    protected void convert( BaseViewHolder helper,  String item) {
        int position = helper.getAdapterPosition();
        View view_space = helper.getView(R.id.view_space);
        if (position == 0)
        view_space.setVisibility(View.GONE);
        else
        view_space.setVisibility(View.VISIBLE);
    }



}