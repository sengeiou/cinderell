package com.cinderellavip.adapter.recycleview;

import android.widget.ImageView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.viewholder.BaseViewHolder;
import com.cinderellavip.R;
import com.cinderellavip.global.ImageUtil;
import com.cinderellavip.ui.BigImageActivity;

import java.util.List;


public class CommentImageAdapter extends BaseQuickAdapter<String, BaseViewHolder> {

    public CommentImageAdapter() {
        super(R.layout.item_image, null);
    }


    @Override
    protected void convert(BaseViewHolder helper,String item) {
        int position = helper.getAdapterPosition();
        ImageView photoView = helper.getView(R.id.photoView);
        ImageUtil.loadNet(getContext(),photoView,item);
//
        helper.getView(R.id.photoView).setOnClickListener(v -> {
            List<String> data = getData();
            String[] s = new String[data.size()];
            for (int i = 0; i< data.size(); i++){
                s[i] = data.get(i);
            }
            BigImageActivity.launch(getContext(),s,position);
        });




    }


}
