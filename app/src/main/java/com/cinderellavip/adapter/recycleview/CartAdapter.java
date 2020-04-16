package com.cinderellavip.adapter.recycleview;

import android.app.Activity;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.viewholder.BaseViewHolder;
import com.cinderellavip.MainActivity;
import com.cinderellavip.R;
import com.cinderellavip.bean.local.CartItem;
import com.cinderellavip.global.GlobalParam;
import com.cinderellavip.global.ImageUtil;
import com.cinderellavip.http.ApiManager;
import com.cinderellavip.http.BaseResult;
import com.cinderellavip.listener.CartClickListener;
import com.cinderellavip.ui.activity.home.GoodsDetailActivity;
import com.tozzais.baselibrary.http.RxHttp;
import com.tozzais.baselibrary.ui.BaseActivity;
import com.tozzais.baselibrary.util.sign.SignUtil;

import org.greenrobot.eventbus.EventBus;

import java.util.TreeMap;


public class CartAdapter extends BaseQuickAdapter<CartItem, BaseViewHolder> {

    private CartClickListener cartClickListener;
    public CartAdapter(CartClickListener cartClickListener) {
        super(R.layout.item_cart, null);
        this.cartClickListener = cartClickListener;
    }


    @Override
    protected void convert(final BaseViewHolder helper, final CartItem item) {
        int position = helper.getAdapterPosition();
        ImageView iv_product = helper.getView(R.id.iv_product);
//        ImageUtil.loadNetRound(mContext,iv_product,item.getLogo());
//        helper.setText(R.id.tv_title,item.getProduct_name());
//        helper.setText(R.id.tv_specification,"规格："+item.getNorm());
//        helper.setText(R.id.tv_price,"￥"+item.getShowPrice());

        //特色产品的标签

        ImageView iv_selete = helper.getView(R.id.iv_selete);
        TextView tv_numer = helper.getView(R.id.tv_number);
        tv_numer.setText(item.getNum()+"");
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
            boolean isSeleteAll = true;
            for (CartItem cartItem:getData()){
                if (cartItem.isCheck){
                    continue;
                }else {
                    isSeleteAll = false;
                    break;
                }
            }
            cartClickListener.onChildSelete(isSeleteAll);
            notifyDataSetChanged();
        });
        helper.getView(R.id.iv_reduce).setOnClickListener(v -> {
            if (item.getNum()>1){
                item.setNum(item.getNum()-1);
                notifyDataSetChanged();
            }
        });
        helper.getView(R.id.iv_add).setOnClickListener(v -> {
            item.setNum(item.getNum()+1);
            notifyDataSetChanged();
        });
        helper.getView(R.id.iv_delete).setOnClickListener(v -> {
           remove(position);
        });
        helper.getView(R.id.ll_root).setOnClickListener(v -> {
        });


    }







}
