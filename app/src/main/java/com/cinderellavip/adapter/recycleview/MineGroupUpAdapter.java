package com.cinderellavip.adapter.recycleview;

import android.annotation.SuppressLint;
import android.view.MotionEvent;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.module.LoadMoreModule;
import com.chad.library.adapter.base.viewholder.BaseViewHolder;
import com.cinderellavip.R;
import com.cinderellavip.bean.local.OrderBean;
import com.cinderellavip.ui.activity.home.ShopDetailActivity;
import com.cinderellavip.ui.activity.mine.MineGroupDetailActivity;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;


public class MineGroupUpAdapter extends BaseQuickAdapter<OrderBean, BaseViewHolder> implements LoadMoreModule {

    public MineGroupUpAdapter() {
        super(R.layout.item_mine_group_up, null);
    }


    @SuppressLint({"DefaultLocale", "ClickableViewAccessibility"})
    @Override
    protected void convert(BaseViewHolder helper,OrderBean item) {
        LinearLayout ll_root = helper.getView(R.id.ll_root);
        TextView tv_status = helper.getView(R.id.tv_status);
        TextView tv_btn1 = helper.getView(R.id.tv_btn1);
        TextView tv_btn2 = helper.getView(R.id.tv_btn2);

        helper.setText(R.id.tv_shop,item.store_name)
                .setText(R.id.tv_goods_number,"共"+item.goods.size()+"件 应付金额：")
                .setText(R.id.tv_money,"￥"+item.goods_amount);


        RecyclerView rv_goods = helper.getView(R.id.rv_goods);
        rv_goods.setLayoutManager(new LinearLayoutManager(getContext()));
        OrderGoodsAdapter adapter = new OrderGoodsAdapter(OrderGoodsAdapter.RETURN);
        rv_goods.setAdapter(adapter);
        adapter.setNewData(item.goods);

        ll_root.setOnClickListener(v -> {
            MineGroupDetailActivity.launch(getContext(),item.id+"");

        });
        rv_goods.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (event.getAction() == MotionEvent.ACTION_UP) {
                    ll_root.performClick();  //模拟父控件的点击
                }
                return false;
            }
        });

        TextView tv_shop = helper.getView(R.id.tv_shop);
        tv_shop.setOnClickListener(view -> {
            ShopDetailActivity.launchShop(getContext(),item.store_id+"");
        });

        tv_status.setText(item.status_txt);
        if (item.status == 2){
            //待成团
            tv_btn1.setText("详情");
            tv_btn2.setText("邀请");
            tv_btn2.setVisibility(View.VISIBLE);
        }else if (item.status == 3){
            //已成团
            tv_btn1.setText("详情");
            tv_btn2.setVisibility(View.GONE);
        }else if (item.status == 4){
            //未成团
            tv_btn1.setText("详情");
            tv_btn2.setVisibility(View.GONE);

        }


    }


}
