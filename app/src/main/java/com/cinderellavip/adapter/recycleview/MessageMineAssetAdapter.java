package com.cinderellavip.adapter.recycleview;


import android.app.Activity;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.module.LoadMoreModule;
import com.chad.library.adapter.base.viewholder.BaseViewHolder;
import com.cinderellavip.R;
import com.cinderellavip.bean.local.OrderBean;
import com.cinderellavip.bean.net.mine.MessageItem;
import com.cinderellavip.global.ImageUtil;
import com.cinderellavip.ui.activity.find.PostDetailActivity;
import com.cinderellavip.ui.activity.find.TopicDetailActivity;
import com.cinderellavip.ui.activity.mine.MineAssetsActivity;

public class MessageMineAssetAdapter extends BaseQuickAdapter<MessageItem, BaseViewHolder> implements LoadMoreModule {


    private int type;
    public MessageMineAssetAdapter(int type) {
        super(R.layout.item_message_order, null);
        this.type = type;
    }

    @Override
    protected void convert(BaseViewHolder helper,  MessageItem item) {
        int position = helper.getAdapterPosition();

        helper.setText(R.id.tv_time,item.time)
                .setText(R.id.tv_name,item.title)
                .setText(R.id.tv_content,item.content);
        TextView tv_status = helper.getView(R.id.tv_status);
        ImageView iv_image = helper.getView(R.id.iv_image);
        if (type == MineAssetsActivity.ORDER){
            tv_status.setVisibility(View.VISIBLE);
            iv_image.setVisibility(View.VISIBLE);
            tv_status.setText(item.status);
            ImageUtil.loadNet(getContext(),iv_image,item.image);
        }



        LinearLayout ll_root = helper.getView(R.id.ll_root);
         ll_root.setOnClickListener(v -> {
             if (type == MineAssetsActivity.FIND){
                if (item.sub_type == 6){
                    //话题
                    TopicDetailActivity.launch((Activity) getContext(),item.obj_id);
                }else if(item.sub_type == 7){
                    //帖子
                    PostDetailActivity.launch((Activity) getContext(),item.obj_id);

                }
             }
        });





    }


}
