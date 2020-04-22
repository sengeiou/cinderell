package com.cinderellavip.adapter.recycleview;


import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.module.LoadMoreModule;
import com.chad.library.adapter.base.viewholder.BaseViewHolder;
import com.cinderellavip.R;
import com.cinderellavip.util.DataUtil;

import org.w3c.dom.Text;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;


public class LifeAdapter extends BaseQuickAdapter<String, BaseViewHolder> implements LoadMoreModule {

    public LifeAdapter() {
        super(R.layout.item_cardsale_banner, null);
    }


    @Override
    protected void convert( BaseViewHolder helper,  String item) {
        int position = helper.getAdapterPosition();
//        helper.setText(R.id.tv_number,item);
        RecyclerView rv_goods = helper.getView(R.id.rv_goods);
        TextView tv_title = helper.getView(R.id.tv_title);


        LifeServiceAdapter adapter = new LifeServiceAdapter();
        rv_goods.setLayoutManager(new LinearLayoutManager(getContext(),RecyclerView.HORIZONTAL,false));
        rv_goods.setAdapter(adapter);
        adapter.setNewData(DataUtil.getData(3));

        if (position == 0){
            tv_title.setText("日常养护");
        }else if (position == 1){
            tv_title.setText("保姆");
        }else if (position == 2){
            tv_title.setText("清洗服务");
        }else if (position == 3){
            tv_title.setText("房屋维修");
        }else if (position == 4){
            tv_title.setText("家电维修");
        }else if (position == 5){
            tv_title.setText("家具维修");
        }





    }



}
