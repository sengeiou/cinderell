package com.cinderellavip.adapter.recycleview;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.module.LoadMoreModule;
import com.chad.library.adapter.base.viewholder.BaseViewHolder;
import com.cinderellavip.R;


public class PostCommentAdapter extends BaseQuickAdapter<String, BaseViewHolder> implements LoadMoreModule {

    public PostCommentAdapter() {
        super(R.layout.item_comment_post, null);
    }




    @Override
    protected void convert(BaseViewHolder helper,String item) {
        int position = helper.getAdapterPosition();


    }


}
