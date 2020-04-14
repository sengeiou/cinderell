package com.cinderellavip.adapter.recycleview;

import android.view.View;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.viewholder.BaseViewHolder;
import com.cinderellavip.R;
import com.cinderellavip.ui.activity.mine.GoodsListActivity;


public class FiltSecondAdapter extends BaseQuickAdapter<String, BaseViewHolder> {


    private View.OnClickListener listener;
    public FiltSecondAdapter(View.OnClickListener listener) {
        super(R.layout.item_filter_second, null);
        this.listener = listener;
    }


    @Override
    protected void convert(BaseViewHolder helper, String item) {
        int position = helper.getAdapterPosition();
        helper.getView(R.id.rl_root).setOnClickListener(v -> {
            if (listener != null){
                listener.onClick(v);
            }
            GoodsListActivity.launch(getContext(),"粽子",0,0);
        });




    }







}
