package com.cinderellavip.adapter.recycleview;

import android.view.View;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.viewholder.BaseViewHolder;
import com.cinderellavip.R;
import com.cinderellavip.bean.net.goods.GoodsCommentItem;
import com.cinderellavip.weight.RatingBarView;

import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;


public class CommentAdapter extends BaseQuickAdapter<GoodsCommentItem, BaseViewHolder> {

    public CommentAdapter() {
        super(R.layout.item_comment, null);
    }

    private boolean havaTopSpace;
    public CommentAdapter(boolean havaTopSpace) {
        super(R.layout.item_comment, null);
        this.havaTopSpace = havaTopSpace;
    }


    @Override
    protected void convert(BaseViewHolder helper,GoodsCommentItem item) {
        int position = helper.getAdapterPosition();
        View space = helper.getView(R.id.space);
        RatingBarView ratingbar = helper.getView(R.id.ratingbar);

        if (havaTopSpace && position == 0){
            space.setVisibility(View.VISIBLE);
        }else {
            space.setVisibility(View.GONE);
        }

        helper.setText(R.id.tv_name,item.username)
                .setText(R.id.tv_time,item.create_at)
                .setText(R.id.tv_content,item.content);


        ratingbar.setClickable(false);
        ratingbar.setStar(item.star,false);

        RecyclerView rv_image = helper.getView(R.id.rv_image);
        rv_image.setLayoutManager(new GridLayoutManager(getContext(),4));
        CommentImageAdapter adapter = new CommentImageAdapter();
        rv_image.setAdapter(adapter);
        adapter.setNewData(item.images);


    }


}
