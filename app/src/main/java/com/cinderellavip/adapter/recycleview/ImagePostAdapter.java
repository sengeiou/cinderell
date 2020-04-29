package com.cinderellavip.adapter.recycleview;

import android.widget.ImageView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.module.LoadMoreModule;
import com.chad.library.adapter.base.viewholder.BaseViewHolder;
import com.cinderellavip.R;
import com.cinderellavip.ui.BigImageActivity1;

import java.util.ArrayList;


public class ImagePostAdapter extends BaseQuickAdapter<String, BaseViewHolder> implements LoadMoreModule {

    public ImagePostAdapter() {
        super(R.layout.item_image, null);
    }


    @Override
    protected void convert(BaseViewHolder helper,String item) {
        int position = helper.getAdapterPosition();
        ImageView photoView = helper.getView(R.id.photoView);
        helper.getView(R.id.photoView).setOnClickListener(v -> {
            ArrayList<Integer> list = new ArrayList<>();
            list.add(R.mipmap.demo_comment);
            list.add(R.mipmap.demo_comment);
            list.add(R.mipmap.demo_comment);
            list.add(R.mipmap.demo_comment);
            BigImageActivity1.launch(getContext(),list,position);
        });

    }


}
