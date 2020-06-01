package com.cinderellavip.adapter.recycleview;


import android.app.Activity;
import android.graphics.Paint;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.module.LoadMoreModule;
import com.chad.library.adapter.base.viewholder.BaseViewHolder;
import com.cinderellavip.R;
import com.cinderellavip.bean.net.home.HomeSpikeItem;
import com.cinderellavip.global.ImageUtil;
import com.cinderellavip.http.ApiManager;
import com.cinderellavip.http.BaseResult;
import com.cinderellavip.http.Response;
import com.cinderellavip.ui.activity.home.GoodsDetailActivity;
import com.tozzais.baselibrary.http.RxHttp;
import com.tozzais.baselibrary.util.toast.ToastCommom;

import java.util.TreeMap;

public class SpikeListAdapter extends BaseQuickAdapter<HomeSpikeItem, BaseViewHolder> implements LoadMoreModule {


    public SpikeListAdapter() {
        super(R.layout.item_spike_list, null);
    }


    @Override
    protected void convert(final BaseViewHolder helper, final HomeSpikeItem item) {
        int position = helper.getAdapterPosition();
        TextView tv_former_price = helper.getView(R.id.tv_former_price);
        tv_former_price.getPaint().setFlags(Paint.STRIKE_THRU_TEXT_FLAG); //中划线

        TextView tv_status = helper.getView(R.id.tv_status);
        ImageView iv_product = helper.getView(R.id.iv_product);
        RelativeLayout rl_robbed = helper.getView(R.id.rl_robbed);
        ProgressBar progressbar = helper.getView(R.id.progressbar);
        ImageUtil.loadNet(getContext(), iv_product, item.thumb);

        rl_robbed.setVisibility(item.is_sold_out ? View.VISIBLE : View.GONE);
        progressbar.setProgress((int) (100 * item.rate));

        helper.setText(R.id.tv_title, item.name)
                .setText(R.id.tv_progress, item.spike_rate)
                .setText(R.id.tv_specification, "已抢" + item.spike_num + "件")
                .setText(R.id.tv_price, "" + item.getSpikePrice() + "")
                .setText(R.id.tv_former_price, "" + item.getBuyPrice() + "");


        if (item.status == 1) {
            tv_status.setText("去抢购");
            tv_status.setBackgroundResource(R.drawable.shape_red50);
        } else if (item.status == 2) {
            tv_status.setText("提醒我");
            tv_status.setBackgroundResource(R.drawable.shape_yellow50_life_deep);
        } else if (item.status == 3) {
            tv_status.setText("已设置");
            tv_status.setBackgroundResource(R.drawable.shape_yellow50_life_shadow);
        }
        tv_status.setOnClickListener(view -> {
            if (item.status == 1){
                GoodsDetailActivity.launch((Activity) getContext(),item.id+"");
            }else {
                alert(item);
            }
        });
        helper.getView(R.id.ll_root).setOnClickListener(v -> {
            GoodsDetailActivity.launch((Activity) getContext(),item.id+"");
        });
//        RelativeLayout ll_root = helper.getView(R.id.ll_root);


    }



    public void  alert(HomeSpikeItem item){
        TreeMap<String, String> hashMap = new TreeMap<>();
        hashMap.put("spike_id", ""+item.spike_id);
        new RxHttp<BaseResult>().send(ApiManager.getService().spikeAlert(hashMap),
                new Response<BaseResult>(getContext()) {
                    @Override
                    public void onSuccess(BaseResult result) {
                        if (item.status == 2){
                            item.status = 3;
                        }else {
                            item.status = 2;
                        }
                        ToastCommom.createToastConfig().ToastShow(getContext(),result.message);
                        notifyDataSetChanged();
                    }
                });
    }


}
