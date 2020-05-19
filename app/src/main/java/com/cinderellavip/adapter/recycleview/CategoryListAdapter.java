package com.cinderellavip.adapter.recycleview;


import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.viewholder.BaseViewHolder;
import com.cinderellavip.R;
import com.cinderellavip.bean.net.life.LiftCategoryItem;

import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;


public class CategoryListAdapter extends BaseQuickAdapter<LiftCategoryItem, BaseViewHolder> {

    public CategoryListAdapter() {
        super(R.layout.item_category_list, null);
    }


    @Override
    protected void convert( BaseViewHolder helper,  LiftCategoryItem item) {
        int position = helper.getAdapterPosition();

        TextView tv_title = helper.getView(R.id.tv_title);
        RecyclerView rv_goods = helper.getView(R.id.rv_goods);
        tv_title.setText(item.name);

        //右边的商品
        rv_goods.setLayoutManager(new GridLayoutManager(getContext(), 3));
        CategoryGoodsAdapter mAdapter = new CategoryGoodsAdapter();
        rv_goods.setAdapter(mAdapter);
        mAdapter.setNewData(item.data);

    }


}
