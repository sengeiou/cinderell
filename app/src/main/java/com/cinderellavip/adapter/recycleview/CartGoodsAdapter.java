package com.cinderellavip.adapter.recycleview;

import android.app.Activity;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
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
import com.cinderellavip.ui.activity.home.GoodsDetailActivity;
import com.cinderellavip.weight.CartNumberView1;
import com.tozzais.baselibrary.http.RxHttp;
import com.tozzais.baselibrary.util.log.LogUtil;

import java.util.TreeMap;


//https://blog.csdn.net/itdream88/article/details/85046989
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
        LogUtil.e("convert"+position+"==="+item.product_num);
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
                modify(item,item.product_num+1+"",cart_view);
            }
            @Override
            public void reduce() {
                modify(item,item.product_num-1+"",cart_view);
            }

        });
        if (tv_number.getTag() instanceof TextWatcher){
            tv_number.removeTextChangedListener((TextWatcher) tv_number.getTag());
        }
        TextWatcher textWatcher = new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }
            @Override
            public void afterTextChanged(Editable s) {
                String content = s.toString();
                LogUtil.e("content= "+content);
                if (content.length()>4){
                    content = "9999";

                }else if (TextUtils.isEmpty(content)){
                    content = "1";

                }
            }
        };


        tv_number.setTag(textWatcher);

        tv_number.setOnFocusChangeListener((v, hasFocus) -> {
            if (hasFocus){
                tv_number.addTextChangedListener(textWatcher);
            }else {
                tv_number.removeTextChangedListener(textWatcher);
            }
            String content = tv_number.getText().toString().trim();
            LogUtil.e("setOnFocusChangeListener= "+content+"=="+hasFocus);

        });

        tv_number.clearFocus();
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
            GoodsDetailActivity.launch((Activity) getContext(),item.product_id+"");
        });


    }



    private void modify(CartGoodsItem item,String number,CartNumberView1 cart_view){
        LogUtil.e("modify");
        TreeMap<String, String> hashMap = new TreeMap<>();
        hashMap.put("cart_id", item.cart_id + "");
        hashMap.put("number",   number);
        new RxHttp<BaseResult>().send(ApiManager.getService().modifyCartNumber(hashMap),
                new Response<BaseResult>(false,getContext()) {
                    @Override
                    public void onSuccess(BaseResult result) {
                        item.product_num = Integer.parseInt(number);
                        notifyDataSetChanged();

//                        notifyItemChanged(position);
                    }
                });
    }




}
