package com.cinderellavip.adapter.recycleview;

import android.view.View;
import android.widget.ImageView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.viewholder.BaseViewHolder;
import com.cinderellavip.R;
import com.cinderellavip.bean.ReportItem;
import com.cinderellavip.bean.net.NetCityBean;


public class ReportAdapter extends BaseQuickAdapter<ReportItem, BaseViewHolder> {


    public ReportAdapter() {
        super(R.layout.item_report, null);
    }


    @Override
    protected void convert(final BaseViewHolder helper, final ReportItem item) {
        int position = helper.getAdapterPosition();
        View view_space = helper.getView(R.id.view_space);
        view_space.setVisibility(position == 0?View.VISIBLE:View.GONE);
        ImageView iv_select = helper.getView(R.id.iv_select);
        helper.setText(R.id.tv_name,item.name);
        if (item.isCheck){
            iv_select.setImageResource(R.mipmap.gwcxz);
        }else {
            iv_select.setImageResource(R.mipmap.gwcmx);
        }
        helper.getView(R.id.ll_root).setOnClickListener(v -> {
            for (ReportItem reportItem:getData()){
                if (reportItem.equals(item)){
                    reportItem.isCheck = true;
                }else {
                    reportItem.isCheck = false;
                }
            }
            notifyDataSetChanged();
        });



    }




}
