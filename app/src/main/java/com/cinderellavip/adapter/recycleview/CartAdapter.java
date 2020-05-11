package com.cinderellavip.adapter.recycleview;

import android.widget.ImageView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.viewholder.BaseViewHolder;
import com.cinderellavip.R;
import com.cinderellavip.bean.local.CartGoodsItem;
import com.cinderellavip.bean.local.CartItem;
import com.cinderellavip.listener.CartGoodsClickListener;
import com.cinderellavip.ui.activity.home.ShopDetailActivity;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;


public class CartAdapter extends BaseQuickAdapter<CartItem, BaseViewHolder> {

    private CartGoodsClickListener cartClickListener;
    public CartAdapter(CartGoodsClickListener cartClickListener) {
        super(R.layout.item_cart, null);
        this.cartClickListener = cartClickListener;
    }


    @Override
    protected void convert( BaseViewHolder helper,final CartItem item) {
        int position = helper.getAdapterPosition();
        RecyclerView rv_list = helper.getView(R.id.rv_list);
        ImageView iv_selete = helper.getView(R.id.iv_selete);
        LinearLayoutManager manager = new LinearLayoutManager(getContext());
        rv_list.setLayoutManager(manager);
        CartGoodsAdapter cartGoodsAdapter =  new CartGoodsAdapter(position1 -> {
            if (item.list.size() == 0){
                //全部删除
                remove(position);
            }else {
                boolean allSelect = true;
                for (CartGoodsItem cartItem:item.list){
                    if (!cartItem.isCheck){
                        allSelect = false;
                        break;
                    }
                }
                item.isCheck = allSelect;
                notifyDataSetChanged();
            }
            cartClickListener.onClick(position);

        });
        rv_list.setAdapter(cartGoodsAdapter);
        cartGoodsAdapter.setNewData(item.list);
        if (item.isCheck){
            iv_selete.setImageResource(R.mipmap.gwcxz);
        }else {
            iv_selete.setImageResource(R.mipmap.gwcmx);
        }
        iv_selete.setOnClickListener(v -> {
            item.isCheck = !item.isCheck;
            for (CartGoodsItem cartItem:item.list){
                cartItem.isCheck = item.isCheck;
            }
            notifyDataSetChanged();
            cartClickListener.onClick(position);
        });
        helper.getView(R.id.tv_shop).setOnClickListener(v -> {
            ShopDetailActivity.launch(getContext());
        });



    }







}
