package com.cinderellavip.adapter.recycleview;

import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.module.LoadMoreModule;
import com.chad.library.adapter.base.viewholder.BaseViewHolder;
import com.cinderellavip.R;


public class WithDrawHistoryAdapter extends BaseQuickAdapter<String, BaseViewHolder> implements LoadMoreModule {

    public WithDrawHistoryAdapter() {
        super(R.layout.item_withdraw_history, null);
    }


    @Override
    protected void convert(final BaseViewHolder helper, final String item) {
        int position = helper.getAdapterPosition();
        TextView tv_status = helper.getView(R.id.tv_status);
//        TextView tv_time = helper.getView(R.id.tv_time);
//        TextView tv_number = helper.getView(R.id.tv_number);
//        tv_name.setText(item.getName());
//        helper.setText(R.id.tv_id,item.desc);
//        helper.setText(R.id.tv_time,item.createtime);
//
        if (position %3 == 0){
            tv_status.setText("审核中");
        }else if (position %3 == 1){
            tv_status.setText("已通过");
        }else if (position %3 == 2){
            tv_status.setText("未通过");
        }

    }


}
