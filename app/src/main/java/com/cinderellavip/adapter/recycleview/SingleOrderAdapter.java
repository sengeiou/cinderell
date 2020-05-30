package com.cinderellavip.adapter.recycleview;


import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.module.LoadMoreModule;
import com.chad.library.adapter.base.viewholder.BaseViewHolder;
import com.cinderellavip.R;
import com.cinderellavip.bean.PrePayLongOrder;
import com.cinderellavip.bean.eventbus.UpdateShortServiceOrder;
import com.cinderellavip.bean.net.life.ShortOrderItem;
import com.cinderellavip.global.ImageUtil;
import com.cinderellavip.http.ApiManager;
import com.cinderellavip.http.BaseResult;
import com.cinderellavip.http.Response;
import com.cinderellavip.toast.CenterDialogUtil;
import com.cinderellavip.ui.activity.life.PayCheckoutCounterActivity;
import com.cinderellavip.ui.activity.life.ServiceOrderCommentActivity;
import com.cinderellavip.ui.activity.life.SingleServiceOrderDetailActivity;
import com.tozzais.baselibrary.http.RxHttp;
import com.tozzais.baselibrary.util.toast.ToastCommom;

import org.greenrobot.eventbus.EventBus;

import java.util.TreeMap;

public class SingleOrderAdapter extends BaseQuickAdapter<ShortOrderItem, BaseViewHolder> implements LoadMoreModule {


    /**
     * 是不是长期服务订单
     */
    private boolean isLongServiceOrder;
    public SingleOrderAdapter() {
        super(R.layout.item_order_single, null);
    }
    public SingleOrderAdapter(boolean isLongServiceOrder) {
        super(R.layout.item_order_single, null);
        this.isLongServiceOrder = isLongServiceOrder;
    }


    @Override
    protected void convert(BaseViewHolder helper, ShortOrderItem item) {
        int position = helper.getAdapterPosition();
        ImageView iv_image = helper.getView(R.id.iv_image);
        ImageUtil.loadNet(getContext(),iv_image,item.icon);
        helper.setText(R.id.tv_title,"服务编号："+item.code)
                .setText(R.id.tv_status,item.getStatus())
                .setText(R.id.tv_type,item.title)
                .setText(R.id.tv_service_type,"服务类型："+item.sortname+"-"+item.title)
                .setText(R.id.tv_service_time,"服务时间："+item.starttime)
                .setText(R.id.tv_service_address,"服务地址："+item.address.address+item.address.doorplate)
                .setText(R.id.tv_price,""+item.getPrice()+"元");
        TextView tv_btn1 = helper.getView(R.id.tv_btn1);
        TextView tv_btn2 = helper.getView(R.id.tv_btn2);
        if (item.pay == 0){
            tv_btn1.setText("取消");
            tv_btn2.setText("支付");
            tv_btn1.setVisibility(View.VISIBLE);
            tv_btn2.setVisibility(View.VISIBLE);
        }else {
            if (item.status == 2){
                tv_btn2.setText("评价");
                tv_btn1.setVisibility(View.GONE);
                tv_btn2.setVisibility(View.VISIBLE);
            }else{
                tv_btn1.setText("详情");
                tv_btn1.setVisibility(View.VISIBLE);
                tv_btn2.setVisibility(View.GONE);
            }
        }
        tv_btn1.setOnClickListener(view -> {
            if (item.pay == 0){
                CenterDialogUtil.showServiceOrder(getContext(), "确认提示", "您确定要取消订单吗？\n取消后不可撤回"
                        , "取消", "确定", s -> {
                            cancel(item.id+"");
                        });
            }else {
                SingleServiceOrderDetailActivity.launch(getContext(), item.id);
            }
        });
        tv_btn2.setOnClickListener(view -> {
            if (item.pay == 0){
                //去支付
                PrePayLongOrder prePayLongOrder = new PrePayLongOrder();
                prePayLongOrder.project = item.id+"";
                prePayLongOrder.type = PrePayLongOrder.PROJECT;
                PayCheckoutCounterActivity.launch(getContext(),prePayLongOrder);
            }else  if (item.status == 2){
                ServiceOrderCommentActivity.launch(getContext(),item.id+"");
            }
        });
        LinearLayout ll_root = helper.getView(R.id.ll_root);
        ll_root.setOnClickListener(v -> {
            SingleServiceOrderDetailActivity.launch(getContext(), item.id);
        });



    }
    /**
     */
    public void cancel(String order) {
        TreeMap<String, String> hashMap = new TreeMap<>();
        hashMap.put("order", ""+order);
        new RxHttp<BaseResult>().send(ApiManager.getService().cancelShortOrder(hashMap),
                new Response<BaseResult>(getContext()) {
                    @Override
                    public void onSuccess(BaseResult result) {
                        ToastCommom.createToastConfig().ToastShow(getContext(),"已取消");
                        EventBus.getDefault().post(new UpdateShortServiceOrder());
                    }
                });
    }



}
