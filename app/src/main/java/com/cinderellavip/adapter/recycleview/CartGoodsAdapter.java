package com.cinderellavip.adapter.recycleview;

import android.app.Activity;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.viewholder.BaseViewHolder;
import com.cinderellavip.R;
import com.cinderellavip.bean.net.cart.CartGoodsItem;
import com.cinderellavip.global.ImageUtil;
import com.cinderellavip.http.ApiManager;
import com.cinderellavip.http.BaseResult;
import com.cinderellavip.http.Response;
import com.cinderellavip.listener.CartGoodsClickListener;
import com.cinderellavip.toast.CenterDialogUtil;
import com.cinderellavip.ui.activity.home.GoodsDetailActivity;
import com.cinderellavip.util.KeyboardUtils;
import com.cinderellavip.weight.CartNumberView1;
import com.tozzais.baselibrary.http.RxHttp;
import com.tozzais.baselibrary.util.log.LogUtil;

import java.util.TreeMap;


//https://blog.csdn.net/itdream88/article/details/85046989

/**
 * 解决焦点乱跑的问题
 * https://www.jianshu.com/p/9a156b48e4f6
 */
public class CartGoodsAdapter extends BaseQuickAdapter<CartGoodsItem, BaseViewHolder> {

    private CartGoodsClickListener cartClickListener;
    public CartGoodsAdapter(CartGoodsClickListener cartClickListener) {
        super(R.layout.item_cart_goods, null);
        this.cartClickListener = cartClickListener;
    }


    @Override
    protected void convert( BaseViewHolder helper, CartGoodsItem item) {
        helper.setIsRecyclable(false);
        int position = helper.getAdapterPosition();
        ImageView iv_product = helper.getView(R.id.iv_product);
        CartNumberView1 cart_view = helper.getView(R.id.cart_view);
        EditText tv_number = cart_view.getTv_number();
        helper.setText(R.id.tv_title,item.product_name)
                .setText(R.id.tv_specification,"规格："+item.product_norm)
                .setText(R.id.tv_price,"￥"+item.getProduct_price());
        ImageUtil.loadNet(getContext(),iv_product,item.product_thumb);
        ImageView iv_selete = helper.getView(R.id.iv_selete);
        if (item.isCheck){
            iv_selete.setImageResource(R.mipmap.gwcxz);
        }else {
            iv_selete.setImageResource(R.mipmap.gwcmx);
        }
        cart_view.setNumber(item.product_num+"");
        cart_view.setOnNumberClickListener(new CartNumberView1.OnNumberClickListener() {
            @Override
            public void add() {
                modify(item,item.product_num+1+"",tv_number);
            }
            @Override
            public void reduce() {
                modify(item,item.product_num-1+"",tv_number);
            }

        });

        View.OnFocusChangeListener onFocusChangeListener = (v, hasFocus) -> {
            if (!hasFocus){
                KeyboardUtils.hideKeyboard(tv_number);
                String content = tv_number.getText().toString().trim();
                if (content.length()>4){
                    content = "9999";
                }else if (TextUtils.isEmpty(content) || "0".equals(content)){
                    content = "1";
                }
                //修改后的内容
                int i = Integer.parseInt(content);
                if (item.product_num != i){
                    modify(item,i+"",tv_number);
                }
            }
        };
        tv_number.setOnFocusChangeListener(onFocusChangeListener);
        tv_number.clearFocus();
        iv_selete.setOnClickListener(v -> {
            item.isCheck = !item.isCheck;
            cartClickListener.onClick(true);
            notifyDataSetChanged();
        });

        helper.getView(R.id.iv_delete).setOnClickListener(v -> {
            CenterDialogUtil.showTwo(getContext(),"提示","确定要删除该商品吗？","取消","确定", s -> {
                if ("1".equals(s)){
                    delete(item,position);
                }
            });

        });
        helper.getView(R.id.ll_root).setOnClickListener(v -> {
            GoodsDetailActivity.launch((Activity) getContext(),item.product_id+"");
        });


    }



    private void modify(CartGoodsItem item,String number,EditText tv_number){
        LogUtil.e("content1= "+"modify"+number);
        TreeMap<String, String> hashMap = new TreeMap<>();
        hashMap.put("cart_id", item.cart_id + "");
        hashMap.put("number",   number);
        new RxHttp<BaseResult>().send(ApiManager.getService().modifyCartNumber(hashMap),
                new Response<BaseResult>(getContext(),Response.DIALOG) {
                    @Override
                    public void onSuccess(BaseResult result) {
                        item.product_num = Integer.parseInt(number);
                        //计算价格
                        tv_number.setText(number);
                        cartClickListener.onClick(false);
//                        notifyDataSetChanged();
                    }
                    @Override
                    public void onToast(String s) {
                        super.onToast(s);
                        tv_number.setText(""+item.product_num);
                    }
                });
    }

    private void delete(CartGoodsItem item,int position){
        TreeMap<String, String> hashMap = new TreeMap<>();
        hashMap.put("cart_ids", item.cart_id + "");
        new RxHttp<BaseResult>().send(ApiManager.getService().deleteCart(hashMap),
                new Response<BaseResult>(true,getContext()) {
                    @Override
                    public void onSuccess(BaseResult result) {
                        remove(position);
                        //计算价格
                        cartClickListener.onClick(true);
                    }
                });
    }




}
