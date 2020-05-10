package com.cinderellavip.adapter.recycleview;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.viewholder.BaseViewHolder;
import com.cinderellavip.R;
import com.cinderellavip.bean.net.HomeCategoryItem;
import com.cinderellavip.global.ImageUtil;
import com.cinderellavip.ui.activity.home.GoodsListActivity;


public class FiltSecondAdapter extends BaseQuickAdapter<HomeCategoryItem, BaseViewHolder> {


    private View.OnClickListener listener;
    public FiltSecondAdapter(View.OnClickListener listener) {
        super(R.layout.item_filter_second, null);
        this.listener = listener;
    }


    @Override
    protected void convert(BaseViewHolder helper, HomeCategoryItem item) {
        int position = helper.getAdapterPosition();
        ImageView imageView = helper.getView(R.id.iv_image);
        TextView tv_number = helper.getView(R.id.tv_number);
        ImageUtil.loadNet1(getContext(),imageView,item.image);
        tv_number.setText(item.name);
        helper.getView(R.id.rl_root).setOnClickListener(v -> {
            if (listener != null){
                listener.onClick(v);
            }
            GoodsListActivity.launch(getContext(),item.name,item.id);
        });




    }







}
