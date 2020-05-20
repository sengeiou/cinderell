package com.cinderellavip.adapter.recycleview;


import android.app.Activity;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.viewholder.BaseViewHolder;
import com.cinderellavip.R;
import com.cinderellavip.bean.net.find.FindItem;
import com.cinderellavip.ui.activity.find.TopicDetailActivity;


public class SearchTopicResultAdapter extends BaseQuickAdapter<FindItem, BaseViewHolder> {


    public SearchTopicResultAdapter() {
        super(R.layout.item_search_topic_result, null);
    }


    @Override
    protected void convert(final BaseViewHolder helper, final FindItem item) {
        int position = helper.getAdapterPosition();
        helper.setText(R.id.tv_title,item.title);

        helper.getView(R.id.ll_root).setOnClickListener(view -> {
            TopicDetailActivity.launch((Activity) getContext(),item.id+"");
        });
    }




}
