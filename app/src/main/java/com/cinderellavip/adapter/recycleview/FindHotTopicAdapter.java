package com.cinderellavip.adapter.recycleview;


import android.view.View;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.viewholder.BaseViewHolder;
import com.cinderellavip.R;
import com.cinderellavip.ui.activity.find.TopicDetailActivity;
import com.cinderellavip.util.DataUtil;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;


public class FindHotTopicAdapter extends BaseQuickAdapter<String, BaseViewHolder> {

    public FindHotTopicAdapter() {
        super(R.layout.item_find_hottopic, null);
    }


    @Override
    protected void convert( BaseViewHolder helper,  String item) {
        int position = helper.getAdapterPosition();
//        helper.setText(R.id.tv_number,item);
        TextView tv_title = helper.getView(R.id.tv_title);
        View view_line = helper.getView(R.id.view_line);
        int size = getData().size();
        if (size % 2 == 0 && (position == size-1 || position == size-2)){
            view_line.setVisibility(View.GONE);
        }else if (size % 2 == 1 && position == size-1 ){
            view_line.setVisibility(View.GONE);
        }else {
            view_line.setVisibility(View.VISIBLE);

        }

    }

}
