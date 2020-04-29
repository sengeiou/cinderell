package com.cinderellavip.adapter.recycleview;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.viewholder.BaseViewHolder;
import com.cinderellavip.R;
import com.cinderellavip.adapter.listview.EnsureOrderGoodsAdapter;
import com.cinderellavip.util.DataUtil;
import com.cinderellavip.weight.MyListView;


public class EnsureOrderAdapter extends BaseQuickAdapter<String, BaseViewHolder> {

    public EnsureOrderAdapter() {
        super(R.layout.item_ensure_order, null);
    }


    @Override
    protected void convert( BaseViewHolder helper, String item) {
        int position = helper.getAdapterPosition();
        MyListView lv_goods = helper.getView(R.id.lv_goods);
        EnsureOrderGoodsAdapter ensureOrderAdapter = new EnsureOrderGoodsAdapter(DataUtil.getData(2), getContext());
        lv_goods.setAdapter(ensureOrderAdapter);





    }







}
