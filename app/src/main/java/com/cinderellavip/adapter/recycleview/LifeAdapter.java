package com.cinderellavip.adapter.recycleview;


import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.module.LoadMoreModule;
import com.chad.library.adapter.base.viewholder.BaseViewHolder;
import com.cinderellavip.R;
import com.cinderellavip.bean.net.life.LiftHomeCategory;
import com.cinderellavip.bean.net.life.LiftHomeListItem;
import com.cinderellavip.ui.activity.life.ServiceListActivity;
import com.cinderellavip.util.DataUtil;

import org.w3c.dom.Text;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;


public class LifeAdapter extends BaseQuickAdapter<LiftHomeListItem, BaseViewHolder> implements LoadMoreModule {

    public LifeAdapter() {
        super(R.layout.item_life, null);
    }


    @Override
    protected void convert( BaseViewHolder helper,  LiftHomeListItem item) {
        int position = helper.getAdapterPosition();
//        helper.setText(R.id.tv_number,item);
        RecyclerView rv_goods = helper.getView(R.id.rv_goods);
        TextView tv_title = helper.getView(R.id.tv_title);
        tv_title.setText(item.title);


        LifeServiceAdapter adapter = new LifeServiceAdapter();
        rv_goods.setLayoutManager(new LinearLayoutManager(getContext(),RecyclerView.HORIZONTAL,false));
        rv_goods.setAdapter(adapter);
        adapter.setNewData(item.data);



        helper.getView(R.id.tv_more).setOnClickListener(v -> {

            LiftHomeCategory liftHomeCategory = new LiftHomeCategory();
            liftHomeCategory.name = item.title;
            liftHomeCategory.one = item.id;
            liftHomeCategory.three = -1;

            ServiceListActivity.launch(getContext(),liftHomeCategory);

        });




    }



}
