package com.cinderellavip.adapter.recycleview;

import android.app.Activity;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;


import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.viewholder.BaseViewHolder;
import com.cinderellavip.R;
import com.cinderellavip.bean.net.NetCityBean;
import com.cinderellavip.global.GlobalParam;
import com.cinderellavip.http.ApiManager;
import com.cinderellavip.http.BaseResult;
import com.cinderellavip.ui.activity.mine.EditAddressActivity;
import com.cinderellavip.ui.activity.mine.MineAddressActivity;
import com.tozzais.baselibrary.http.RxHttp;
import com.tozzais.baselibrary.util.sign.SignUtil;

import java.util.TreeMap;


public class MineAddressAdpter extends BaseQuickAdapter<NetCityBean, BaseViewHolder> {

    private int type;

    public MineAddressAdpter(int type) {
        super(R.layout.item_address, null);
        this.type = type;
    }


    @Override
    protected void convert(final BaseViewHolder helper, final NetCityBean item) {
        int position = helper.getAdapterPosition();
        ImageView iv_selete = helper.getView(R.id.iv_selete);
        ImageView iv_edit = helper.getView(R.id.iv_edit);
        iv_selete.setVisibility(
                (item.isCheck && (type == MineAddressActivity.SELETE ||type == MineAddressActivity.EXCHANGE ))
                        ? View.VISIBLE : View.GONE);
//
        iv_edit.setOnClickListener(v -> {
            EditAddressActivity.launch((Activity) getContext(), EditAddressActivity.EDIT, item);
        });
        TextView tv_name = helper.getView(R.id.tv_name);
        TextView tv_phone = helper.getView(R.id.tv_phone);
//        TextView tv_id_number = helper.getView(R.id.tv_id_number);
        TextView tv_default_status = helper.getView(R.id.tv_default_status);
        if (item.is_default){
            tv_default_status.setVisibility(View.VISIBLE);
        }else {
            tv_default_status.setVisibility(View.GONE);
        }
        TextView tv_detail_address = helper.getView(R.id.tv_detail_address);
//
//        View tv_default_line = helper.getView(R.id.tv_default_line);
//        tv_default_status.setVisibility(item.isDefault() ? View.VISIBLE : View.GONE);

        tv_name.setText(item.name);
        tv_phone.setText(item.mobile);
        tv_detail_address.setText(item.province + "" + item.city + "" + item.area + "" + item.address);



    }




}
