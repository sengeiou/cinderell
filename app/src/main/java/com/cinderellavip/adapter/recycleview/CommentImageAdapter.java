package com.cinderellavip.adapter.recycleview;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.viewholder.BaseViewHolder;
import com.cinderellavip.R;
import com.cinderellavip.ui.BigImageActivity;
import com.cinderellavip.ui.BigImageActivity1;

import java.util.ArrayList;

import androidx.recyclerview.widget.RecyclerView;


public class CommentImageAdapter extends BaseQuickAdapter<String, BaseViewHolder> {

    public CommentImageAdapter() {
        super(R.layout.item_image, null);
    }


    @Override
    protected void convert(BaseViewHolder helper,String item) {
        int position = helper.getAdapterPosition();
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
