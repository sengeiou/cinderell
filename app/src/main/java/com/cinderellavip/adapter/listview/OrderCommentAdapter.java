package com.cinderellavip.adapter.listview;

import android.content.Context;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;


import com.cinderellavip.R;
import com.cinderellavip.adapter.recycleview.MineOrderCommentImageAdpter;
import com.cinderellavip.base.BaseAdapter;
import com.cinderellavip.bean.OrderCommentItemBean;
import com.cinderellavip.global.ImageUtil;
import com.cinderellavip.listener.OnDoublePositionClickListener;
import com.cinderellavip.weight.RatingBarView;
import com.cinderellavip.weight.RoundImageView;

import java.util.List;

import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.BindView;
import butterknife.ButterKnife;

public class OrderCommentAdapter extends BaseAdapter<OrderCommentItemBean> {



    private OnDoublePositionClickListener listener;

    public OrderCommentAdapter(List<OrderCommentItemBean> mList, Context context, OnDoublePositionClickListener listener) {
        super(mList, context);
        this.listener = listener;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        final ViewHolder hodler;
        //去掉复用，不然etComment获取会出现重新的bug
//        if (convertView == null) {
            convertView = View.inflate(context, R.layout.item_order_comment, null);
            hodler = new ViewHolder(convertView);
            convertView.setTag(hodler);
//        } else {
//            hodler = (ViewHolder) convertView.getTag();
//        }
        OrderCommentItemBean bean = mList.get(position);
//        ImageUtil.loadNet(context, hodler.ivImage, bean.logo);
//        hodler.tvTitle.setText(bean.name);
//        if (!TextUtils.isEmpty(bean.content))
//        hodler.etComment.setText(bean.content);
//        if (!TextUtils.isEmpty(bean.score))
//        hodler.rbScore.setStar(Integer.parseInt(bean.score),false);
        hodler.rbScore.setOnRatingListener((bindObject, RatingScore) -> {
            bean.score = RatingScore+"";
        });
        hodler.etComment.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }
            @Override
            public void afterTextChanged(Editable s) {
                bean.content = s.toString();
            }
        });


        hodler.rv_comment.setLayoutManager(new GridLayoutManager(context, 4));
        MineOrderCommentImageAdpter mAdapter = new MineOrderCommentImageAdpter(listener, position,4);
        hodler.rv_comment.setAdapter(mAdapter);
        mAdapter.setNewData(bean.mDatas);


        return convertView;
    }

    static class ViewHolder {

        @BindView(R.id.rv_comment)
        RecyclerView rv_comment;
        @BindView(R.id.iv_image)
        RoundImageView ivImage;
        @BindView(R.id.tv_title)
        TextView tvTitle;
        @BindView(R.id.rb_score)
        RatingBarView rbScore;
        @BindView(R.id.et_comment)
        EditText etComment;

        ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }
}
