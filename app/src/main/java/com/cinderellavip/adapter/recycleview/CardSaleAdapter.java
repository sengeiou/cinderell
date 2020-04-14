package com.cinderellavip.adapter.recycleview;


import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.viewholder.BaseViewHolder;
import com.cinderellavip.R;
import com.cinderellavip.bean.local.HomeCategoryItem;
import com.cinderellavip.util.DataUtil;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;


public class CardSaleAdapter extends BaseQuickAdapter<String, BaseViewHolder> {

    public CardSaleAdapter() {
        super(R.layout.item_cardsale_banner, null);
    }


    @Override
    protected void convert( BaseViewHolder helper,  String item) {
        int position = helper.getAdapterPosition();
//        helper.setText(R.id.tv_number,item);
        RecyclerView rv_goods = helper.getView(R.id.rv_goods);

        CardSaleGoodsAdapter adapter = new CardSaleGoodsAdapter();
        rv_goods.setLayoutManager(new LinearLayoutManager(getContext(),RecyclerView.HORIZONTAL,false));
        rv_goods.setAdapter(adapter);
        adapter.setNewData(DataUtil.getData(6));





    }



}
