package com.cinderellavip.adapter.recycleview;


import android.view.View;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.viewholder.BaseViewHolder;
import com.cinderellavip.R;


public class SearchTopicForPublishPostAdapter extends BaseQuickAdapter<String, BaseViewHolder> {


    public SearchTopicForPublishPostAdapter() {
        super(R.layout.item_find_hottopic_search, null);
    }


    @Override
    protected void convert(final BaseViewHolder helper, final String item) {
        int position = helper.getAdapterPosition();
        helper.getView(R.id.view_linea).setVisibility(View.VISIBLE);
    }




}
