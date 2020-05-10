package com.cinderellavip.adapter.recycleview;

import android.graphics.Paint;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.module.LoadMoreModule;
import com.chad.library.adapter.base.viewholder.BaseViewHolder;
import com.cinderellavip.R;
import com.cinderellavip.bean.local.HomeGoods;


public class CartEmptyAdapter extends BaseQuickAdapter<HomeGoods, BaseViewHolder> implements LoadMoreModule {

    public CartEmptyAdapter() {
        super(R.layout.item_home_goods, null);
    }


    @Override
    protected void convert(BaseViewHolder baseViewHolder, HomeGoods homeGoods) {

        int position = baseViewHolder.getLayoutPosition();
        TextView tv_name =  baseViewHolder.getView(R.id.tv_name);
        TextView tv_group_number =  baseViewHolder.getView(R.id.tv_group_number);
        TextView tv_price =  baseViewHolder.getView(R.id.tv_price);
        TextView tv_former_price =  baseViewHolder.getView(R.id.tv_former_price);
        ImageView iv_national_flag =  baseViewHolder.getView(R.id.iv_national_flag);

            tv_name.setMaxLines(2);
            tv_name.setLines(2);
            tv_group_number.setVisibility(View.GONE);
            iv_national_flag.setVisibility(View.GONE);
            tv_price.setText("￥88");
            tv_former_price.setText("￥128");
            tv_former_price.getPaint().setFlags(Paint. STRIKE_THRU_TEXT_FLAG); //中划线


        baseViewHolder.getView(R.id.ll_root).setOnClickListener(v -> {
//            GoodsDetailActivity.launch((Activity) getContext(),0);
        });


    }


}
