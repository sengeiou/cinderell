package com.cinderellavip.adapter.recycleview;


import android.widget.ImageView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.module.LoadMoreModule;
import com.chad.library.adapter.base.viewholder.BaseViewHolder;
import com.cinderellavip.R;
import com.cinderellavip.bean.net.mine.MineInviteItem;
import com.cinderellavip.global.ImageUtil;


public class XiaohuiRecommentAdapter extends BaseQuickAdapter<MineInviteItem, BaseViewHolder> implements LoadMoreModule {


    public XiaohuiRecommentAdapter() {
        super(R.layout.item_xiaohui_recomment, null);
    }


    @Override
    protected void convert(final BaseViewHolder helper, final MineInviteItem item) {
        int position = helper.getAdapterPosition();
        ImageView iv_image = helper.getView(R.id.iv_image);
        ImageUtil.loadNet(getContext(),iv_image,item.avatar);
        helper.setText(R.id.tv_title,item.username)
                .setText(R.id.tv_money,"+"+item.integral);
    }




}
