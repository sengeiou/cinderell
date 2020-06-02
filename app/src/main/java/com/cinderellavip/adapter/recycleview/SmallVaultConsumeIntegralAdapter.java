package com.cinderellavip.adapter.recycleview;


import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.module.LoadMoreModule;
import com.chad.library.adapter.base.viewholder.BaseViewHolder;
import com.cinderellavip.R;
import com.cinderellavip.bean.net.mine.IntegralItem;
import com.cinderellavip.global.ImageUtil;
import com.cinderellavip.weight.CircleImageView;


public class SmallVaultConsumeIntegralAdapter extends BaseQuickAdapter<IntegralItem, BaseViewHolder> implements LoadMoreModule {

    public int type;

    public SmallVaultConsumeIntegralAdapter(int type) {
        super(R.layout.item_smallvault_consume_integral, null);
        this.type = type;
    }


    @Override
    protected void convert(final BaseViewHolder helper, final IntegralItem item) {
        int position = helper.getAdapterPosition();
        ImageView iv_rank_image = helper.getView(R.id.iv_rank_image);
        TextView tv_rank_text = helper.getView(R.id.tv_rank_text);
        RelativeLayout rl_rank = helper.getView(R.id.rl_rank);

        CircleImageView iv_image = helper.getView(R.id.iv_image);
        TextView tv_id = helper.getView(R.id.tv_id);
        TextView tv_time = helper.getView(R.id.tv_time);
        helper.setText(R.id.tv_title,item.title)
                .setText(R.id.tv_number,item.integral);
        if (type == 1){
            tv_id.setVisibility(View.VISIBLE);
            tv_time.setVisibility(View.VISIBLE);
            tv_id.setText(item.desc);
            tv_time.setText(item.create_at);
        }else if (type == 2){
            iv_image.setVisibility(View.VISIBLE);
            ImageUtil.loadNet(getContext(),iv_image,item.avatar);
            tv_time.setVisibility(View.VISIBLE);
            tv_time.setText(item.create_at);
            tv_id.setVisibility(View.VISIBLE);
            tv_id.setText(item.desc);
        }else if (type == 3){
            iv_image.setVisibility(View.VISIBLE);
            ImageUtil.loadNet(getContext(),iv_image,item.avatar);
            rl_rank.setVisibility(View.VISIBLE);
            tv_time.setVisibility(View.VISIBLE);
            tv_time.setText(item.create_at);
            if (position == 0){
                iv_rank_image.setImageResource(R.mipmap.icon_rank_first);
                iv_rank_image.setVisibility(View.VISIBLE);
                tv_rank_text.setVisibility(View.GONE);
            }else if (position == 1){
                iv_rank_image.setImageResource(R.mipmap.icon_rank_second);
                iv_rank_image.setVisibility(View.VISIBLE);
                tv_rank_text.setVisibility(View.GONE);
            }else if (position == 2){
                iv_rank_image.setImageResource(R.mipmap.icon_rank_three);
                iv_rank_image.setVisibility(View.VISIBLE);
                tv_rank_text.setVisibility(View.GONE);
            }else {
                tv_rank_text.setText((position+1)+"");
                iv_rank_image.setVisibility(View.GONE);
                tv_rank_text.setVisibility(View.VISIBLE);
            }
        }else if (type == 4){

        }
    }




}
