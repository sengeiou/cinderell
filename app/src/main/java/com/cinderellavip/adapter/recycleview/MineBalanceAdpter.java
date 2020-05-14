package com.cinderellavip.adapter.recycleview;

import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.module.LoadMoreModule;
import com.chad.library.adapter.base.viewholder.BaseViewHolder;
import com.cinderellavip.R;
import com.cinderellavip.bean.net.mine.MineBalanceItem;


public class MineBalanceAdpter extends BaseQuickAdapter<MineBalanceItem, BaseViewHolder> implements LoadMoreModule {

    public MineBalanceAdpter() {
        super(R.layout.item_balance, null);
    }


    @Override
    protected void convert(final BaseViewHolder helper, final MineBalanceItem item) {
        int position = helper.getAdapterPosition();
        TextView tv_name = helper.getView(R.id.tv_name);
        TextView tv_time = helper.getView(R.id.tv_time);
        TextView tv_number = helper.getView(R.id.tv_number);
        tv_name.setText(item.message);
        helper.setText(R.id.tv_time,item.create_at);
        tv_number.setText(item.getAmount());
        if (item.type == 1){
            //收入
//            helper.setText(R.id.tv_number,"+"+item.getBalance_money()+"");
//            tv_number.setTextColor(getContext().getResources().getColor(R.color.baseColor));
        }else {
            //支出
//            helper.setText(R.id.tv_number,"-"+item.getBalance_money()+"");
//            tv_number.setTextColor(getContext().getResources().getColor(R.color.baseColor));

        }



    }


}
