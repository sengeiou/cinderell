package com.cinderellavip.adapter.recycleview;

import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;


import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.viewholder.BaseViewHolder;
import com.cinderellavip.R;
import com.cinderellavip.util.DataUtil;

import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;


public class FilterAdapter extends BaseQuickAdapter<String, BaseViewHolder> {

    private View.OnClickListener listener;
    public FilterAdapter(View.OnClickListener listener) {
        super(R.layout.item_filter, null);
        this.listener = listener;
    }


    @Override
    protected void convert( BaseViewHolder helper,  String item) {
        int position = helper.getAdapterPosition();

        RecyclerView rv_goods = helper.getView(R.id.rv_goods);
        TextView tv_name = helper.getView(R.id.tv_name);
        GridLayoutManager linearLayoutManager = new GridLayoutManager(getContext(),4);
        rv_goods.setLayoutManager(linearLayoutManager);

        FiltSecondAdapter goodsAdapter = new FiltSecondAdapter(listener);
        rv_goods.setAdapter(goodsAdapter);



        goodsAdapter.setNewData(DataUtil.getData(10));


    }


}
