package com.cinderellavip.adapter.recycleview;

import android.text.TextUtils;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.viewholder.BaseViewHolder;
import com.cinderellavip.R;
import com.cinderellavip.global.ImageUtil;
import com.cinderellavip.weight.CircleImageView;


public class GroupDetailUserAdapter extends BaseQuickAdapter<String, BaseViewHolder> {

    public GroupDetailUserAdapter() {
        super(R.layout.item_groupdetail_user, null);
    }


    @Override
    protected void convert(BaseViewHolder helper, String item) {
        int position = helper.getAdapterPosition();
           CircleImageView iv_avatar =  helper.getView(R.id.iv_avatar);
           if (TextUtils.isEmpty(item)){
               iv_avatar.setImageResource(R.drawable.shape_oval_gray50);
           }else {
               ImageUtil.loadNet(getContext(),iv_avatar,item);
//               iv_avatar.setImageResource(R.mipmap.avater);
           }

    }

}
