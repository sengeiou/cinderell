package com.cinderellavip.adapter.recycleview;


import android.widget.ImageView;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.viewholder.BaseViewHolder;
import com.cinderellavip.R;
import com.cinderellavip.bean.direct.DirectProjectItem;
import com.cinderellavip.bean.request.TechnicalComment;
import com.cinderellavip.global.ImageUtil;
import com.cinderellavip.ui.activity.life.ServiceDetailActivity;
import com.cinderellavip.ui.activity.life.ServiceProjectDetailActivity;


public class TechnicalProjectAdapter extends BaseQuickAdapter<DirectProjectItem, BaseViewHolder> {

    private int waiter;
    public TechnicalProjectAdapter( int waiter) {
        super(R.layout.item_technical_project, null);
        this.waiter = waiter;
    }


    @Override
    protected void convert( BaseViewHolder helper,  DirectProjectItem item) {
        int position = helper.getAdapterPosition();
        ImageView iv_product = helper.getView(R.id.iv_product);
        ImageUtil.loadNet(getContext(),iv_product,item.thumb_nail);
        helper.setText(R.id.tv_title,item.title)
                .setText(R.id.tv_specification,item.getContent())
                .setText(R.id.tv_price,item.getPrice());



        helper.getView(R.id.ll_root).setOnClickListener(view -> {
            TechnicalComment technicalComment = new TechnicalComment(
                    waiter + "", item.id+"",
                    item.typesof == 1?TechnicalComment.comment_technical:TechnicalComment.comment_project);
            ServiceProjectDetailActivity.launch(getContext(),technicalComment);
        });


    }



}
