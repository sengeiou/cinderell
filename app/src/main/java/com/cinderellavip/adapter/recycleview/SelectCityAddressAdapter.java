package com.cinderellavip.adapter.recycleview;

import android.widget.TextView;

import com.amap.api.services.core.PoiItem;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.viewholder.BaseViewHolder;
import com.cinderellavip.R;


public class SelectCityAddressAdapter extends BaseQuickAdapter<PoiItem, BaseViewHolder> {


    public SelectCityAddressAdapter() {
        super(R.layout.item_select_city, null);
    }


    @Override
    protected void convert(final BaseViewHolder helper, final PoiItem item) {
        int position = helper.getAdapterPosition();
//        if (position == 0){
//            LogUtil.e(item.getAdName()+"\n"
//            +item.getBusinessArea()+"\n"
//            +item.getCityName()+"\n"
//            +item.getDirection()+"\n"
//            +item.getEmail()+"\n"
//            +item.getWebsite()+"\n"
//            +item.getSnippet()+"\n"
//            +item.getShopID()+"\n"
//            +item.getTel()+"\n"
//            +item.getTitle()+"\n"
//            +item.getTypeDes()+"\n"
//            +item.getPoiExtension().getmRating()+"\n"
//            +item.getParkingType()
//            );
//
//        }
        TextView tv_name = helper.getView(R.id.tv_name);
        TextView tv_id_number = helper.getView(R.id.tv_id_number);
        tv_name.setText(item.getTitle());
        tv_id_number.setText(item.getProvinceName()+item.getCityName()+item.getAdName()+item.getSnippet());

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
