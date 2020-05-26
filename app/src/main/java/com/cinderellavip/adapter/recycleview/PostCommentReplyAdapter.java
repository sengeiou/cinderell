package com.cinderellavip.adapter.recycleview;

import android.graphics.Color;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.TextUtils;
import android.text.style.ForegroundColorSpan;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.viewholder.BaseViewHolder;
import com.cinderellavip.R;
import com.cinderellavip.bean.ReportItem;
import com.cinderellavip.bean.net.find.DiscussCommentReply;
import com.cinderellavip.listener.OnCommentReplyClickListener;


public class PostCommentReplyAdapter extends BaseQuickAdapter<DiscussCommentReply, BaseViewHolder> {

    private OnCommentReplyClickListener onCommentReplyClickListener;
    public PostCommentReplyAdapter(OnCommentReplyClickListener onCommentReplyClickListener) {
        super(R.layout.item_comment_reply, null);
        this.onCommentReplyClickListener = onCommentReplyClickListener;
    }



    @Override
    protected void convert(final BaseViewHolder helper, final DiscussCommentReply item) {
        int position = helper.getAdapterPosition();

        TextView tv_name = helper.getView(R.id.tv_name);
        String s= "";
        if (TextUtils.isEmpty(item.u_name)){
            //被回复人为kong
            s = item.m_name + "："+item.content;
        }else {
            s = item.m_name + " 回复 "+item.u_name+"："+item.content;
        }
        SpannableString string = new SpannableString(s);
        ForegroundColorSpan colorSpan = new ForegroundColorSpan(Color.parseColor("#3E91E6"));
        ForegroundColorSpan colorSpan1 = new ForegroundColorSpan(Color.parseColor("#3E91E6"));
        string.setSpan(colorSpan, s.indexOf(item.m_name), s.indexOf(item.m_name)+item.m_name.length(), Spanned.SPAN_INCLUSIVE_EXCLUSIVE);
        if (!TextUtils.isEmpty(item.u_name))
        string.setSpan(colorSpan1, s.indexOf(item.u_name), s.indexOf(item.u_name)+item.u_name.length(), Spanned.SPAN_INCLUSIVE_EXCLUSIVE);


        tv_name.setText(string);

        helper.getView(R.id.ll_root).setOnClickListener(v -> {
            if (onCommentReplyClickListener != null)
                onCommentReplyClickListener.onSure("",item.id+"","回复 "+item.m_name);
        });



    }




}
