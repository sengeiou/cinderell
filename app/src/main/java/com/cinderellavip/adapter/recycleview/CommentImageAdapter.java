package com.cinderellavip.adapter.recycleview;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.viewholder.BaseViewHolder;
import com.cinderellavip.R;

import androidx.recyclerview.widget.RecyclerView;


public class CommentImageAdapter extends BaseQuickAdapter<String, BaseViewHolder> {

    public CommentImageAdapter() {
        super(R.layout.item_image, null);
    }


    @Override
    protected void convert(BaseViewHolder helper,String item) {
        int position = helper.getAdapterPosition();

    }


}
