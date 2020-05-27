package com.cinderellavip.adapter.recycleview;

import android.view.View;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.module.LoadMoreModule;
import com.chad.library.adapter.base.viewholder.BaseViewHolder;
import com.cinderellavip.R;
import com.cinderellavip.bean.net.mine.WithDrawHistoryItem;
import com.cinderellavip.ui.activity.mine.WithDrawHistoryActivity;


public class WithDrawHistoryAdapter extends BaseQuickAdapter<WithDrawHistoryItem, BaseViewHolder> implements LoadMoreModule {

    int type;
    public WithDrawHistoryAdapter(int type) {
        super(R.layout.item_withdraw_history, null);
        this.type = type;
    }


    @Override
    protected void convert(final BaseViewHolder helper, final WithDrawHistoryItem item) {
        int position = helper.getAdapterPosition();
        View view_line = helper.getView(R.id.view_line);
        View view_space = helper.getView(R.id.view_space);
            if(type == WithDrawHistoryActivity.BALANCE){
                view_line.setVisibility(View.VISIBLE);
                view_space.setVisibility(View.GONE);
            }if(type == WithDrawHistoryActivity.SCORE){
            view_line.setVisibility(View.GONE);
            view_space.setVisibility(View.VISIBLE);
        }
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
