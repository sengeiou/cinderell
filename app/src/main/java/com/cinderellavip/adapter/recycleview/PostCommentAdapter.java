package com.cinderellavip.adapter.recycleview;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.module.LoadMoreModule;
import com.chad.library.adapter.base.viewholder.BaseViewHolder;
import com.cinderellavip.R;
import com.cinderellavip.bean.net.find.DiscussComment;
import com.cinderellavip.global.ImageUtil;
import com.cinderellavip.weight.CircleImageView;


public class PostCommentAdapter extends BaseQuickAdapter<DiscussComment, BaseViewHolder> implements LoadMoreModule {

    public PostCommentAdapter() {
        super(R.layout.item_comment_post, null);
    }




    @Override
    protected void convert(BaseViewHolder helper,DiscussComment item) {
        int position = helper.getAdapterPosition();
        CircleImageView iv_avater = helper.getView(R.id.iv_avater);
        ImageUtil.loadNet(getContext(),iv_avater,item.avatar);
        helper.setText(R.id.tv_name,item.nickname)
                .setText(R.id.tv_content,item.content)
                .setText(R.id.tv_time,item.create_at);


    }


}
