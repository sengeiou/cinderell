package com.cinderellavip.adapter.recycleview;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.module.LoadMoreModule;
import com.chad.library.adapter.base.viewholder.BaseViewHolder;
import com.cinderellavip.R;
import com.cinderellavip.ui.activity.life.DirectAppointmentTechnicianCommentActivity;
import com.cinderellavip.ui.activity.life.DirectAppointmentTechnicianDetailActivity;
import com.cinderellavip.weight.RatingBarView;


public class DirectListAdapter extends BaseQuickAdapter<String, BaseViewHolder> implements LoadMoreModule {

    public DirectListAdapter() {
        super(R.layout.item_direct_list, null);
    }


    @Override
    protected void convert( BaseViewHolder helper, String item) {
        int position = helper.getAdapterPosition();

        helper.getView(R.id.ll_root).setOnClickListener(v -> {
            DirectAppointmentTechnicianDetailActivity.launch(getContext(),
                    DirectAppointmentTechnicianCommentActivity.comment_technical);
        });
        RatingBarView ratingBar = helper.getView(R.id.ratingBar);
        ratingBar.setStarCount(4);


    }







}
