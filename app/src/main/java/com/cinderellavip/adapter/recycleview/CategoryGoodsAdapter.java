package com.cinderellavip.adapter.recycleview;


import android.widget.ImageView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.viewholder.BaseViewHolder;
import com.cinderellavip.R;
import com.cinderellavip.bean.net.life.LiftCategoryItem;
import com.cinderellavip.global.ImageUtil;
import com.cinderellavip.ui.activity.life.ServiceListActivity;


public class CategoryGoodsAdapter extends BaseQuickAdapter<LiftCategoryItem, BaseViewHolder> {

    public CategoryGoodsAdapter() {
        super(R.layout.item_category_goods, null);
    }


    @Override
    protected void convert( BaseViewHolder helper,  LiftCategoryItem item) {
        int position = helper.getAdapterPosition();
        ImageView iv_image = helper.getView(R.id.iv_image);
        ImageUtil.loadNet(getContext(),iv_image,item.icon);
        helper.setText(R.id.tv_title,item.name);
        helper.getView(R.id.ll_root).setOnClickListener(view -> {
            ServiceListActivity.launch(getContext(),"物品整理");
//            GoodsListActivity.launch(mContext,item.type_name,item.parent_id ,item.type_id);
        });
//        switch (position%3){
//            case 0:
//                iv_iamge.setImageResource(R.mipmap.demo_kind_icon);
//                tv_title.setText("底霜/隔离");
//                break;
//            case 1:
//                iv_iamge.setImageResource(R.mipmap.demo_kind_icon);
//                tv_title.setText("遮瑕");
//                break;
//            case 2:
//                iv_iamge.setImageResource(R.mipmap.demo_kind_icon);
//                tv_title.setText("粉底/液");
//                break;
//
//        }

    }


}
