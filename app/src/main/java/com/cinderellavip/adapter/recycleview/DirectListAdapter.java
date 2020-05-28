package com.cinderellavip.adapter.recycleview;

import android.widget.ImageView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.module.LoadMoreModule;
import com.chad.library.adapter.base.viewholder.BaseViewHolder;
import com.cinderellavip.R;
import com.cinderellavip.bean.direct.DirectPerson;
import com.cinderellavip.global.ImageUtil;
import com.cinderellavip.ui.activity.life.DirectAppointmentTechnicianCommentActivity;
import com.cinderellavip.ui.activity.life.DirectAppointmentTechnicianDetailActivity;
import com.cinderellavip.weight.RatingBarView;


public class DirectListAdapter extends BaseQuickAdapter<DirectPerson, BaseViewHolder> implements LoadMoreModule {

    public DirectListAdapter() {
        super(R.layout.item_direct_list, null);
    }


    @Override
    protected void convert( BaseViewHolder helper, DirectPerson item) {
        int position = helper.getAdapterPosition();

        helper.getView(R.id.ll_root).setOnClickListener(v -> {
            DirectAppointmentTechnicianDetailActivity.launch(getContext(),
                    DirectAppointmentTechnicianCommentActivity.comment_technical);
        });
        RatingBarView ratingBar = helper.getView(R.id.ratingBar);
        ImageView iv_product = helper.getView(R.id.iv_product);
        ImageUtil.loadNet(getContext(),iv_product,item.avatar);
        ratingBar.setStarCount(item.score);
        ratingBar.setEnabled(false);

        helper.setText(R.id.tv_title,item.name)
                .setText(R.id.tv_content,item.work)
                .setText(R.id.tv_distance,item.distance+"km")
                .setText(R.id.tv_status,item.getStatusText());


    }







}
