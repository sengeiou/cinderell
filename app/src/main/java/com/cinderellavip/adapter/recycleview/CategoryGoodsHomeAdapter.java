package com.cinderellavip.adapter.recycleview;


import android.widget.ImageView;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.viewholder.BaseViewHolder;
import com.cinderellavip.R;


public class CategoryGoodsHomeAdapter extends BaseQuickAdapter<String, BaseViewHolder> {

    public CategoryGoodsHomeAdapter() {
        super(R.layout.item_category_goods, null);
    }


    @Override
    protected void convert( BaseViewHolder helper,  String item) {
        int position = helper.getAdapterPosition();

        ImageView iv_image = helper.getView(R.id.iv_image);
        iv_image.setImageResource(R.mipmap.demo_home_category);
        TextView tv_title = helper.getView(R.id.tv_title);
        tv_title.setText("清洁工具");
        helper.getView(R.id.ll_root).setOnClickListener(view -> {
//            ServiceListActivity.launch(getContext(),"物品整理");
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
