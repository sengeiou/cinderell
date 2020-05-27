package com.cinderellavip.adapter.recycleview;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.module.LoadMoreModule;
import com.chad.library.adapter.base.viewholder.BaseViewHolder;
import com.cinderellavip.R;
import com.cinderellavip.bean.potato.MinePotatoItem;


public class MinePotatoAdapter extends BaseQuickAdapter<MinePotatoItem, BaseViewHolder> implements LoadMoreModule {

    public MinePotatoAdapter() {
        super(R.layout.item_mine_potato, null);
    }


    @Override
    protected void convert(final BaseViewHolder helper, final MinePotatoItem item) {
        int position = helper.getAdapterPosition();

        helper.setText(R.id.tv_name,item.title)
                .setText(R.id.tv_time,item.create_at)
                .setText(R.id.tv_number,item.num);
//        TextView tv_time = helper.getView(R.id.tv_time);
//        TextView tv_number = helper.getView(R.id.tv_number);
//        tv_name.setText(item.getName());
//        helper.setText(R.id.tv_id,item.desc);
//        helper.setText(R.id.tv_time,item.createtime);
//
//        if (position %3 == 0){
//            tv_status.setText("审核中");
//        }else if (position %3 == 1){
//            tv_status.setText("已通过");
//        }else if (position %3 == 2){
//            tv_status.setText("未通过");
//        }

    }


}
