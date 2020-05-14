package com.cinderellavip.adapter.recycleview;

import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.module.LoadMoreModule;
import com.chad.library.adapter.base.viewholder.BaseViewHolder;
import com.cinderellavip.R;
import com.cinderellavip.bean.net.mine.WithDrawHistoryItem;


public class WithDrawHistoryAdapter extends BaseQuickAdapter<WithDrawHistoryItem, BaseViewHolder> implements LoadMoreModule {

    public WithDrawHistoryAdapter() {
        super(R.layout.item_withdraw_history, null);
    }


    @Override
    protected void convert(final BaseViewHolder helper, final WithDrawHistoryItem item) {
        int position = helper.getAdapterPosition();
        TextView tv_status = helper.getView(R.id.tv_status);
        helper.setText(R.id.tv_name,item.title)
                .setText(R.id.tv_id,item.account)
                .setText(R.id.tv_time,item.create_at)
                .setText(R.id.tv_status,item.getStatus())
                .setText(R.id.tv_number,"-"+item.money);
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
