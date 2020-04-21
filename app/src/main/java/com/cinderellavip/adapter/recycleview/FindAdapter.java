package com.cinderellavip.adapter.recycleview;


import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.module.LoadMoreModule;
import com.chad.library.adapter.base.viewholder.BaseViewHolder;
import com.cinderellavip.R;

import org.jetbrains.annotations.NotNull;


/**
 *
 */
public class FindAdapter extends BaseQuickAdapter<String, BaseViewHolder> implements LoadMoreModule {

    public FindAdapter() {
        super(R.layout.item_find, null);

    }
    private boolean isStag;
    public FindAdapter(boolean isStag) {
        super(R.layout.item_find, null);
        this.isStag = isStag;

    }


    @Override
    protected void convert(@NotNull BaseViewHolder baseViewHolder, @NotNull  String homeGoods) {
        int position = baseViewHolder.getLayoutPosition();
       TextView tv_content =  baseViewHolder.getView(R.id.tv_content);
       if (position%2 == 0){
           tv_content.setText("包装高大上，宝贝小巧精致，期待有实际效果。");
       }else {
           tv_content.setText("包装高大上。");

       }



    }

}
