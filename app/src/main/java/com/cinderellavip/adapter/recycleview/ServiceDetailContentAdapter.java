package com.cinderellavip.adapter.recycleview;


import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.viewholder.BaseViewHolder;
import com.cinderellavip.R;
import com.cinderellavip.ui.activity.life.ServiceProjectDetailActivity;


public class ServiceDetailContentAdapter extends BaseQuickAdapter<String, BaseViewHolder> {

    public ServiceDetailContentAdapter() {
        super(R.layout.item_service_detail_content, null);
    }


    @Override
    protected void convert( BaseViewHolder helper,  String item) {
        int position = helper.getAdapterPosition();




    }



}
