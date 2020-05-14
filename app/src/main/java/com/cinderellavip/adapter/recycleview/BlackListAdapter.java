package com.cinderellavip.adapter.recycleview;


import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.viewholder.BaseViewHolder;
import com.cinderellavip.R;
import com.cinderellavip.bean.net.mine.BlacklistItem;
import com.cinderellavip.global.ImageUtil;
import com.cinderellavip.http.ApiManager;
import com.cinderellavip.http.BaseResult;
import com.cinderellavip.http.Response;
import com.tozzais.baselibrary.http.RxHttp;
import com.tozzais.baselibrary.util.toast.ToastCommom;

import java.util.TreeMap;

public class BlackListAdapter extends BaseQuickAdapter<BlacklistItem, BaseViewHolder> {


    public BlackListAdapter() {
        super(R.layout.item_black_list, null);
    }

    @Override
    protected void convert(BaseViewHolder helper,  BlacklistItem item) {
        int position = helper.getAdapterPosition();

        View view_space = helper.getView(R.id.view_space);
        view_space.setVisibility(position ==0?View.VISIBLE:View.GONE);


        ImageView merchant_icon = helper.getView(R.id.merchant_icon);
        ImageUtil.loadNet(getContext(),merchant_icon,item.avatar);
        helper.setText(R.id.tv_name,item.username);


        TextView tv_status = helper.getView(R.id.tv_status);
        tv_status.setOnClickListener(v -> {
            shield(item.user_id+"",position);
        });





    }

    private void shield(String user_id,int position){
        TreeMap<String, String> hashMap = new TreeMap<>();
        hashMap.put("user_id", user_id + "");
        new RxHttp<BaseResult>().send(ApiManager.getService().getShield(hashMap),
                new Response<BaseResult>( getContext()) {
                    @Override
                    public void onSuccess(BaseResult result) {
                        remove(position);
                        ToastCommom.createToastConfig().ToastShow(getContext(),"已解除拉黑");


                    }
                });
    }


}
