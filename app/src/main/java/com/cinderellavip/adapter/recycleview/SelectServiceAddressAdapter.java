package com.cinderellavip.adapter.recycleview;

import android.app.Activity;
import android.view.View;
import android.widget.ImageView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.module.LoadMoreModule;
import com.chad.library.adapter.base.viewholder.BaseViewHolder;
import com.cinderellavip.R;
import com.cinderellavip.bean.net.NetCityBean;
import com.cinderellavip.ui.activity.life.AddServiceAddressActivity;
import com.cinderellavip.ui.activity.mine.MineAddressActivity;


public class SelectServiceAddressAdapter extends BaseQuickAdapter<NetCityBean, BaseViewHolder> {

    private int type;

    public SelectServiceAddressAdapter(int type) {
        super(R.layout.item_address_service, null);
        this.type = type;
    }


    @Override
    protected void convert(final BaseViewHolder helper, final NetCityBean item) {
        int position = helper.getAdapterPosition();
        ImageView iv_edit = helper.getView(R.id.iv_edit);

        iv_edit.setOnClickListener(v -> {
            AddServiceAddressActivity.launch((Activity) getContext(), AddServiceAddressActivity.EDIT);
//            EditAddressActivity.launch((Activity) mContext, EditAddressActivity.EDIT, item);
        });
//        TextView tv_name = helper.getView(R.id.tv_name);
//        TextView tv_phone = helper.getView(R.id.tv_phone);
//        TextView tv_id_number = helper.getView(R.id.tv_id_number);
//        TextView tv_default_status = helper.getView(R.id.tv_default_status);
//        TextView tv_detail_address = helper.getView(R.id.tv_detail_address);
////
//        View tv_default_line = helper.getView(R.id.tv_default_line);
//        tv_default_status.setVisibility(item.isDefault() ? View.VISIBLE : View.GONE);
//
//        tv_name.setText(item.truename);
//        tv_phone.setText(item.phone);
//        tv_id_number.setText("身份证号：" + item.getId_card());
//        tv_detail_address.setText(item.prov + " " + item.city + " " + item.dist + " " + item.detail);



    }




}
