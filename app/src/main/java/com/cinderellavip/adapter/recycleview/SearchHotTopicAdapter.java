package com.cinderellavip.adapter.recycleview;


import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.viewholder.BaseViewHolder;
import com.cinderellavip.R;
import com.cinderellavip.bean.net.NetCityBean;


public class SearchHotTopicAdapter extends BaseQuickAdapter<String, BaseViewHolder> {


    public SearchHotTopicAdapter() {
        super(R.layout.item_find_hottopic_search, null);
    }


    @Override
    protected void convert(final BaseViewHolder helper, final String item) {
        int position = helper.getAdapterPosition();
    }




}
