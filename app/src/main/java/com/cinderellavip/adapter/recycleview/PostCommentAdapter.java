package com.cinderellavip.adapter.recycleview;

import android.view.View;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.module.LoadMoreModule;
import com.chad.library.adapter.base.viewholder.BaseViewHolder;
import com.cinderellavip.R;
import com.cinderellavip.bean.net.find.DiscussComment;
import com.cinderellavip.global.ImageUtil;
import com.cinderellavip.listener.OnCommentReplyClickListener;
import com.cinderellavip.weight.CircleImageView;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;


public class PostCommentAdapter extends BaseQuickAdapter<DiscussComment, BaseViewHolder> implements LoadMoreModule {

    private OnCommentReplyClickListener onCommentReplyClickListener;
    public PostCommentAdapter(OnCommentReplyClickListener onCommentReplyClickListener) {
        super(R.layout.item_comment_post, null);
        this.onCommentReplyClickListener = onCommentReplyClickListener;
    }




    @Override
    protected void convert(BaseViewHolder helper,DiscussComment item) {
        int position = helper.getAdapterPosition();
        CircleImageView iv_avater = helper.getView(R.id.iv_avater);
        ImageUtil.loadNet(getContext(),iv_avater,item.avatar);
        helper.setText(R.id.tv_name,item.nickname)
                .setText(R.id.tv_content,item.content)
                .setText(R.id.tv_time,item.create_at);

        RecyclerView rv_image = helper.getView(R.id.rv_image);
        if (item.replies != null && item.replies.size()>0){
            rv_image.setVisibility(View.VISIBLE);
            rv_image.setLayoutManager(new LinearLayoutManager(getContext()));
            PostCommentReplyAdapter adapter = new PostCommentReplyAdapter((id, reply_id, hint) -> {
                if (onCommentReplyClickListener != null){
                    onCommentReplyClickListener.onSure(item.id+"",reply_id,hint);
                }
            });
            rv_image.setAdapter(adapter);
            adapter.setNewData(item.replies);
        }else {
            rv_image.setVisibility(View.GONE);
        }



        helper.getView(R.id.ll_root).setOnClickListener(v -> {
            if (onCommentReplyClickListener != null){
                onCommentReplyClickListener.onSure(item.id+"","","回复 "+item.nickname);
            }
        });



    }


}
