package com.cinderellavip.adapter.recycleview;


import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.module.LoadMoreModule;
import com.chad.library.adapter.base.viewholder.BaseViewHolder;
import com.cinderellavip.R;


public class SmallVaultLeaderBoardAdapter extends BaseQuickAdapter<String, BaseViewHolder> implements LoadMoreModule {


    public SmallVaultLeaderBoardAdapter() {
        super(R.layout.item_smallvault_leaderboard, null);
    }


    @Override
    protected void convert(final BaseViewHolder helper, final String item) {
        int position = helper.getAdapterPosition();
        LinearLayout ll_header = helper.getView(R.id.ll_header);
        ImageView iv_rank_image = helper.getView(R.id.iv_rank_image);
        TextView tv_rank_text = helper.getView(R.id.tv_rank_text);
        if (position == 0){
            iv_rank_image.setImageResource(R.mipmap.icon_rank_first);
            ll_header.setVisibility(View.VISIBLE);
            iv_rank_image.setVisibility(View.VISIBLE);
            tv_rank_text.setVisibility(View.GONE);
        }else if (position == 1){
            iv_rank_image.setImageResource(R.mipmap.icon_rank_second);
            ll_header.setVisibility(View.GONE);
            iv_rank_image.setVisibility(View.VISIBLE);
            tv_rank_text.setVisibility(View.GONE);
        }else if (position == 2){
            iv_rank_image.setImageResource(R.mipmap.icon_rank_three);
            ll_header.setVisibility(View.GONE);
            iv_rank_image.setVisibility(View.VISIBLE);
            tv_rank_text.setVisibility(View.GONE);
        }else {
            tv_rank_text.setText(position+"");
            ll_header.setVisibility(View.GONE);
            iv_rank_image.setVisibility(View.GONE);
            tv_rank_text.setVisibility(View.VISIBLE);
        }
    }




}
