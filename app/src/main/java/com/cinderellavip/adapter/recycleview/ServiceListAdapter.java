package com.cinderellavip.adapter.recycleview;


import android.view.View;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.viewholder.BaseViewHolder;
import com.cinderellavip.R;
import com.cinderellavip.util.DataUtil;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;


public class ServiceListAdapter extends BaseQuickAdapter<String, BaseViewHolder> {

    public ServiceListAdapter() {
        super(R.layout.item_service_list, null);
    }


    @Override
    protected void convert( BaseViewHolder helper,  String item) {
        int position = helper.getAdapterPosition();
//        helper.setText(R.id.tv_number,item);

        View view_space = helper.getView(R.id.view_space);
        if (position != getData().size())
            view_space.setVisibility(View.GONE);
        else
            view_space.setVisibility(View.VISIBLE);


        RecyclerView rv_goods = helper.getView(R.id.rv_goods);


        ServiceListProjectAdapter adapter = new ServiceListProjectAdapter();
        rv_goods.setLayoutManager(new LinearLayoutManager(getContext()));
        rv_goods.setAdapter(adapter);
        adapter.setNewData(DataUtil.getData(2));





    }



}
