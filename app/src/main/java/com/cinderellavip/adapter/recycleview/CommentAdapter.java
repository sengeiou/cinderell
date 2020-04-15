package com.cinderellavip.adapter.recycleview;

import android.view.View;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.viewholder.BaseViewHolder;
import com.cinderellavip.R;
import com.cinderellavip.util.DataUtil;

import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;


public class CommentAdapter extends BaseQuickAdapter<String, BaseViewHolder> {

    public CommentAdapter() {
        super(R.layout.item_comment, null);
    }

    private boolean havaTopSpace;
    public CommentAdapter(boolean havaTopSpace) {
        super(R.layout.item_comment, null);
        this.havaTopSpace = havaTopSpace;
    }


    @Override
    protected void convert(BaseViewHolder helper,String item) {
        int position = helper.getAdapterPosition();
        View space = helper.getView(R.id.space);
        if (havaTopSpace && position == 0){
            space.setVisibility(View.VISIBLE);
        }else {
            space.setVisibility(View.GONE);
        }
        RecyclerView rv_image = helper.getView(R.id.rv_image);
        rv_image.setLayoutManager(new GridLayoutManager(getContext(),4));
        CommentImageAdapter adapter = new CommentImageAdapter();
        rv_image.setAdapter(adapter);
        adapter.setNewData(DataUtil.getData(4));
    }


}
