package com.cinderellavip.adapter.recycleview;

import android.view.View;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.viewholder.BaseViewHolder;
import com.cinderellavip.R;
import com.cinderellavip.bean.net.home.CateMoreList;

import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;


public class FilterAdapter extends BaseQuickAdapter<CateMoreList, BaseViewHolder> {

    private View.OnClickListener listener;
    public FilterAdapter(View.OnClickListener listener) {
        super(R.layout.item_filter, null);
        this.listener = listener;
    }


    @Override
    protected void convert( BaseViewHolder helper,  CateMoreList item) {
        int position = helper.getAdapterPosition();

        RecyclerView rv_goods = helper.getView(R.id.rv_goods);
        TextView tv_name = helper.getView(R.id.tv_name);
        tv_name.setText(item.name);

        GridLayoutManager linearLayoutManager = new GridLayoutManager(getContext(),4);
        rv_goods.setLayoutManager(linearLayoutManager);

        FiltSecondAdapter goodsAdapter = new FiltSecondAdapter(listener);
        rv_goods.setAdapter(goodsAdapter);



        goodsAdapter.setNewData(item.next);


    }


}
