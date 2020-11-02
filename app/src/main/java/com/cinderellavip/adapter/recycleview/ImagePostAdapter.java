package com.cinderellavip.adapter.recycleview;

import android.widget.ImageView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.module.LoadMoreModule;
import com.chad.library.adapter.base.viewholder.BaseViewHolder;
import com.cinderellavip.R;
import com.cinderellavip.global.ImageUtil;
import com.cinderellavip.ui.BigImageActivity;


public class ImagePostAdapter extends BaseQuickAdapter<String, BaseViewHolder> implements LoadMoreModule {

    public ImagePostAdapter() {
        super(R.layout.item_image, null);
    }


    @Override
    protected void convert(BaseViewHolder helper,String item) {
        int position = helper.getAdapterPosition();
        ImageView photoView = helper.getView(R.id.photoView);
        ImageUtil.loadNet(getContext(),photoView,item);
        helper.getView(R.id.photoView).setOnClickListener(v -> {
            String[] array=getData().toArray(new String[getData().size()]);
            BigImageActivity.launch(getContext(),array,position);

        });

    }


}
