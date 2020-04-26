package com.cinderellavip.adapter.recycleview;


import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.viewholder.BaseViewHolder;
import com.cinderellavip.R;
import com.cinderellavip.ui.activity.life.ServiceDetailActivity;
import com.cinderellavip.ui.activity.life.ServiceProjectDetailActivity;


public class TechnicalProjectAdapter extends BaseQuickAdapter<String, BaseViewHolder> {

    public TechnicalProjectAdapter() {
        super(R.layout.item_technical_project, null);
    }


    @Override
    protected void convert( BaseViewHolder helper,  String item) {
        int position = helper.getAdapterPosition();

        helper.getView(R.id.ll_root).setOnClickListener(view -> {
            ServiceProjectDetailActivity.launch(getContext(),
                    ServiceProjectDetailActivity.package_service);
        });


    }



}
