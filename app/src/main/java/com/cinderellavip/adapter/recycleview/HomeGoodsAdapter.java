package com.cinderellavip.adapter.recycleview;


import android.app.Activity;
import android.graphics.Paint;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.module.LoadMoreModule;
import com.chad.library.adapter.base.viewholder.BaseViewHolder;
import com.cinderellavip.R;
import com.cinderellavip.bean.local.HomeGoods;
import com.cinderellavip.global.ImageUtil;
import com.cinderellavip.ui.activity.home.GoodsDetailActivity;

import org.jetbrains.annotations.NotNull;


/**
 *
 */
public class HomeGoodsAdapter extends BaseQuickAdapter<HomeGoods, BaseViewHolder> implements LoadMoreModule {


    //精选
    public static final int FEATURED = 0;
    //拼团
    public static final int FIGHT = 1;
    //进口
    public static final int INLET = 2;
    //实惠
    public static final int AFFORDABLE = 3;
    //推荐
    public static final int RECOMMEND = 4;

    private int type;


    public void setType(int type) {
        this.type = type;
    }

    public HomeGoodsAdapter() {
        super(R.layout.item_home_goods, null);

    }


    @Override
    protected void convert(@NotNull BaseViewHolder baseViewHolder, @NotNull  HomeGoods homeGoods) {
        int position = baseViewHolder.getLayoutPosition();
       TextView tv_name =  baseViewHolder.getView(R.id.tv_name);
       TextView tv_group_number =  baseViewHolder.getView(R.id.tv_group_number);
       TextView tv_price =  baseViewHolder.getView(R.id.tv_price);
       TextView tv_former_price =  baseViewHolder.getView(R.id.tv_former_price);
       ImageView iv_national_flag =  baseViewHolder.getView(R.id.iv_national_flag);
       ImageView iv_image =  baseViewHolder.getView(R.id.iv_image);
        tv_name.setText(homeGoods.name);
        ImageUtil.loadNet2(getContext(),iv_image,homeGoods.thumb);


       if (type == FEATURED || type == RECOMMEND ){
           //精选
           tv_name.setMaxLines(2);
           tv_name.setLines(2);
           tv_group_number.setVisibility(View.GONE);
           iv_national_flag.setVisibility(View.GONE);
           tv_price.setText("￥"+homeGoods.getPrice());
           tv_former_price.setText("￥"+homeGoods.getOld_price());
           tv_former_price.getPaint().setFlags(Paint. STRIKE_THRU_TEXT_FLAG); //中划线
       }else if (type == FIGHT){
           //拼团
           tv_name.setMaxLines(1);
           tv_name.setLines(1);
           tv_group_number.setVisibility(View.VISIBLE);
           iv_national_flag.setVisibility(View.GONE);
           tv_group_number.setText("已拼"+homeGoods.number+"件");
           tv_price.setText("￥"+homeGoods.getGroup_price());
           tv_former_price.setText("单买价￥"+homeGoods.getBuy_price());
           tv_former_price.getPaint().setFlags(0); //取消划线
       }else if (type == INLET){
           //进口
           tv_name.setMaxLines(2);
           tv_name.setLines(2);
           tv_group_number.setVisibility(View.GONE);
           iv_national_flag.setVisibility(View.GONE);
           tv_price.setText("￥"+homeGoods.getPrice());
           tv_former_price.setText("￥"+homeGoods.getOld_price());
           tv_former_price.getPaint().setFlags(Paint. STRIKE_THRU_TEXT_FLAG); //取消划线
       }else if (type == AFFORDABLE){
           //实惠
           tv_name.setMaxLines(2);
           tv_name.setLines(2);
           tv_group_number.setVisibility(View.GONE);
           iv_national_flag.setVisibility(View.GONE);
           tv_price.setText("￥"+homeGoods.getPrice());
           tv_former_price.setText("￥"+homeGoods.getOld_price());
           tv_former_price.getPaint().setFlags(Paint. STRIKE_THRU_TEXT_FLAG); //取消划线
       }

        baseViewHolder.getView(R.id.ll_root).setOnClickListener(v -> {
            GoodsDetailActivity.launch((Activity) getContext(),homeGoods.id+"");
        });
    }
}
