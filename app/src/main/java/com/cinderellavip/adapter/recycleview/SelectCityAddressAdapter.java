package com.cinderellavip.adapter.recycleview;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.viewholder.BaseViewHolder;
import com.cinderellavip.R;
import com.cinderellavip.bean.net.NetCityBean;


public class SelectCityAddressAdapter extends BaseQuickAdapter<NetCityBean, BaseViewHolder> {


    public SelectCityAddressAdapter() {
        super(R.layout.item_select_city, null);
    }


    @Override
    protected void convert(final BaseViewHolder helper, final NetCityBean item) {
        int position = helper.getAdapterPosition();

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
