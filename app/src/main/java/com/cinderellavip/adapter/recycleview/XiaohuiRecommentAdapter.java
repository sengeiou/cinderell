package com.cinderellavip.adapter.recycleview;


import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.module.LoadMoreModule;
import com.chad.library.adapter.base.viewholder.BaseViewHolder;
import com.cinderellavip.R;


public class XiaohuiRecommentAdapter extends BaseQuickAdapter<String, BaseViewHolder> implements LoadMoreModule {


    public XiaohuiRecommentAdapter() {
        super(R.layout.item_xiaohui_recomment, null);
    }


    @Override
    protected void convert(final BaseViewHolder helper, final String item) {
        int position = helper.getAdapterPosition();
    }




}
