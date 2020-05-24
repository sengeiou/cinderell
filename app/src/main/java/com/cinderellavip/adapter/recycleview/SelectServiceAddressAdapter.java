package com.cinderellavip.adapter.recycleview;

import android.app.Activity;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.viewholder.BaseViewHolder;
import com.cinderellavip.R;
import com.cinderellavip.bean.net.LifeCityBean;
import com.cinderellavip.ui.activity.life.AddServiceAddressActivity;


public class SelectServiceAddressAdapter extends BaseQuickAdapter<LifeCityBean, BaseViewHolder> {

    private int type;

    public SelectServiceAddressAdapter(int type) {
        super(R.layout.item_address_service, null);
        this.type = type;
    }


    @Override
    protected void convert(final BaseViewHolder helper, final LifeCityBean item) {
        int position = helper.getAdapterPosition();
        ImageView iv_edit = helper.getView(R.id.iv_edit);

        iv_edit.setOnClickListener(v -> {
            AddServiceAddressActivity.launch((Activity) getContext(), AddServiceAddressActivity.EDIT,item);
//            EditAddressActivity.launch((Activity) mContext, EditAddressActivity.EDIT, item);
        });
        TextView tv_name = helper.getView(R.id.tv_name);
        TextView tv_id_number = helper.getView(R.id.tv_id_number);
        TextView tv_detail_address = helper.getView(R.id.tv_detail_address);
        TextView tv_default_status = helper.getView(R.id.tv_default_status);
//

        tv_name.setText(item.title);
        tv_id_number.setText(item.address+item.doorplate);
        tv_detail_address.setText(item.name + " " + item.phone);

        tv_default_status.setVisibility(item.isDefault()? View.VISIBLE:View.GONE);



    }




}
