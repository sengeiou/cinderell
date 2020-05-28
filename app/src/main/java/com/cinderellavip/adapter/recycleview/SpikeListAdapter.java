package com.cinderellavip.adapter.recycleview;


import android.graphics.Paint;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.module.LoadMoreModule;
import com.chad.library.adapter.base.viewholder.BaseViewHolder;
import com.cinderellavip.R;

public class SpikeListAdapter extends BaseQuickAdapter<String, BaseViewHolder> implements LoadMoreModule {


    public SpikeListAdapter() {
        super(R.layout.item_spike_list, null);
    }


    @Override
    protected void convert(final BaseViewHolder helper, final String item) {
        int position = helper.getAdapterPosition();
        TextView tv_former_price = helper.getView(R.id.tv_former_price);
        tv_former_price.getPaint().setFlags(Paint. STRIKE_THRU_TEXT_FLAG); //中划线
//        RelativeLayout ll_root = helper.getView(R.id.ll_root);




    }


}
