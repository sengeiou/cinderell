package com.cinderellavip.adapter.recycleview;

import android.app.Activity;
import android.widget.ImageView;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.viewholder.BaseViewHolder;
import com.cinderellavip.R;
import com.cinderellavip.bean.local.CartGoodsItem;
import com.cinderellavip.bean.local.CartItem;
import com.cinderellavip.listener.CartClickListener;
import com.cinderellavip.listener.CartGoodsClickListener;
import com.cinderellavip.ui.activity.home.GoodsDetailActivity;

import androidx.recyclerview.widget.RecyclerView;


public class CartGoodsAdapter extends BaseQuickAdapter<CartGoodsItem, BaseViewHolder> {

    private CartGoodsClickListener cartClickListener;
    public CartGoodsAdapter(CartGoodsClickListener cartClickListener) {
        super(R.layout.item_cart_goods, null);
        this.cartClickListener = cartClickListener;
    }


    @Override
    protected void convert( BaseViewHolder helper, CartGoodsItem item) {
        int position = helper.getAdapterPosition();
        ImageView iv_product = helper.getView(R.id.iv_product);


        //特色产品的标签

        ImageView iv_selete = helper.getView(R.id.iv_selete);
        TextView tv_numer = helper.getView(R.id.tv_number);
//        tv_numer.setOnClickListener(v -> {
//
//            View view;
//            if (getContext() instanceof MainActivity){
//                MainActivity mContext = (MainActivity) this.mContext;
//                view = mContext.ll_main;
//            }else  {
//                BaseActivity mContext = (BaseActivity) this.mContext;
//                view = mContext.parentView;
//            }
//            BaseActivity mContext = (BaseActivity) this.mContext;
//            PopUtil.showUpdateNumber(this.mContext,mContext.getWindow(),view,item.getNum()+"",number -> {
//                modifyData(item,number);
//            });
//
//        });


        if (item.isCheck){
            iv_selete.setImageResource(R.mipmap.gwcxz);
        }else {
            iv_selete.setImageResource(R.mipmap.gwcmx);
        }
        iv_selete.setOnClickListener(v -> {
            item.isCheck = !item.isCheck;
            cartClickListener.onClick(position);
            notifyDataSetChanged();
        });

        helper.getView(R.id.iv_delete).setOnClickListener(v -> {
            remove(position);
            cartClickListener.onClick(position);
        });
        helper.getView(R.id.ll_root).setOnClickListener(v -> {
            GoodsDetailActivity.launch((Activity) getContext(),1);
        });


    }







}
