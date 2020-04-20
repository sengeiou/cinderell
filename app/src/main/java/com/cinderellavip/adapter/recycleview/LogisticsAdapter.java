package com.cinderellavip.adapter.recycleview;

import android.view.View;
import android.widget.LinearLayout;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.module.LoadMoreModule;
import com.chad.library.adapter.base.viewholder.BaseViewHolder;
import com.cinderellavip.R;
import com.cinderellavip.bean.net.IntegralExchangeLogistics;


public class LogisticsAdapter extends BaseQuickAdapter<IntegralExchangeLogistics.ListBean, BaseViewHolder>
implements LoadMoreModule {

    public LogisticsAdapter() {
        super(R.layout.item_logistics, null);
    }


    @Override
    protected void convert(BaseViewHolder helper,IntegralExchangeLogistics.ListBean item) {
        int position = helper.getAdapterPosition();
        LinearLayout ll_list_header = helper.getView(R.id.ll_list_header);
        if (position==1){
            ll_list_header.setVisibility(View.VISIBLE);
        }else {
            ll_list_header.setVisibility(View.GONE);
        }
//
//        String[] split = item.time.split(" ");
//        helper.setText(R.id.tv_time1,split[0])
//                .setText(R.id.tv_time2,split[1])
//                .setText(R.id.tv_content,item.status);


    }


}
