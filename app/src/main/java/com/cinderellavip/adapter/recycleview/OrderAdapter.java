package com.cinderellavip.adapter.recycleview;


import android.app.Activity;
import android.view.MotionEvent;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.module.LoadMoreModule;
import com.chad.library.adapter.base.viewholder.BaseViewHolder;
import com.cinderellavip.R;
import com.cinderellavip.bean.eventbus.ReceiveOrder;
import com.cinderellavip.bean.local.OrderBean;
import com.cinderellavip.bean.net.order.CreateOrderBean;
import com.cinderellavip.http.ApiManager;
import com.cinderellavip.http.BaseResult;
import com.cinderellavip.http.ListResult;
import com.cinderellavip.http.Response;
import com.cinderellavip.toast.CenterDialogUtil;
import com.cinderellavip.toast.ReturnUtil;
import com.cinderellavip.ui.activity.home.ShopDetailActivity;
import com.cinderellavip.ui.activity.mine.LogisticsActivity;
import com.cinderellavip.ui.activity.order.OrderCommentActivity;
import com.cinderellavip.ui.activity.order.OrderDetailActivity;
import com.cinderellavip.ui.activity.order.SelectPayWayActivity;
import com.tozzais.baselibrary.http.RxHttp;
import com.tozzais.baselibrary.util.toast.ToastCommom;

import org.greenrobot.eventbus.EventBus;

import java.util.TreeMap;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class OrderAdapter extends BaseQuickAdapter<OrderBean, BaseViewHolder> implements LoadMoreModule {


    public OrderAdapter() {
        super(R.layout.item_order, null);
    }


    @Override
    protected void convert(final BaseViewHolder helper, final OrderBean item) {
        int position = helper.getAdapterPosition();
//        RelativeLayout ll_root = helper.getView(R.id.ll_root);
        TextView tv_status = helper.getView(R.id.tv_status);
        TextView tv_btn0 = helper.getView(R.id.tv_btn0);
        TextView tv_btn1 = helper.getView(R.id.tv_btn1);
        TextView tv_btn2 = helper.getView(R.id.tv_btn2);
        RecyclerView rv_goods = helper.getView(R.id.rv_goods);
        rv_goods.setLayoutManager(new LinearLayoutManager(getContext()));
        OrderGoodsAdapter adapter = new OrderGoodsAdapter(1);
        rv_goods.setAdapter(adapter);
        adapter.setNewData(item.goods);

        helper.setText(R.id.tv_shop,item.store_name)
                .setText(R.id.tv_status,item.getStatus())
                .setText(R.id.tv_goods_number,"共"+item.goods.size()+"件 应付金额：")
                .setText(R.id.tv_money,"￥"+item.goods_amount);
        switch (item.status){
            case 0:
                tv_btn1.setText("详情");
                tv_btn1.setVisibility(View.VISIBLE);
                tv_btn2.setVisibility(View.GONE);
                tv_btn0.setVisibility(View.GONE);
                break;
            case 1:
                tv_btn1.setText("取消");
                tv_btn2.setText("付款");
                tv_btn1.setVisibility(View.VISIBLE);
                tv_btn2.setVisibility(View.VISIBLE);
                tv_btn0.setVisibility(View.GONE);
                break;
            case 2:
                tv_btn0.setVisibility(View.VISIBLE);
                tv_btn0.setText("取消");
                tv_btn1.setText("详情");
                tv_btn1.setVisibility(View.VISIBLE);
                tv_btn2.setVisibility(View.GONE);
                break;
            case 3:
                tv_btn1.setText("物流");
                tv_btn2.setText("收货");
                tv_btn1.setVisibility(View.VISIBLE);
                tv_btn2.setVisibility(View.VISIBLE);
                tv_btn0.setVisibility(View.GONE);
                break;
            case 5:
                tv_btn1.setText("详情");
                tv_btn1.setVisibility(View.VISIBLE);
                tv_btn2.setVisibility(View.GONE);
                tv_btn0.setVisibility(View.GONE);
                break;
            case 4:
                tv_btn1.setText("详情");
                tv_btn2.setText("评价");
                tv_btn1.setVisibility(View.VISIBLE);
                tv_btn2.setVisibility(View.VISIBLE);
                tv_btn0.setVisibility(View.GONE);
                break;
        }
        tv_btn1.setOnClickListener(view -> {
            switch (item.status){
                case 1:
                    CenterDialogUtil.showTwo(getContext(),"提示","确定要取消该订单吗？","取消","确定",s -> {
                        if ("1".equals(s)){
                            cancel(item.id+"");
                        }
                    });
                    break;
                case 2:
                case 5:
                case 4:
                    OrderDetailActivity.launch(getContext(),item.id);
                    break;
                case 3:
                    LogisticsActivity.launch((Activity) getContext(),item.id+"");
                    break;

            }
        });
        tv_btn0.setOnClickListener(view -> {
            if (item.status == 2){
                new RxHttp<BaseResult<ListResult<String>>>().send(ApiManager.getService().refundReason(),
                        new Response<BaseResult<ListResult<String>>>(getContext()) {
                            @Override
                            public void onSuccess(BaseResult<ListResult<String>> result) {
                                ReturnUtil.showSelectDialog(getContext(),"请选择取消理由", result.data.list, reason -> {

                                    CenterDialogUtil.showTwo(getContext(),"确定要取消订单吗？",
                                            "待发货订单，需在3小时之内取消，超过3小时无法取消",
                                            "暂不取消","确定取消",s -> {
                                        if ("1".equals(s)){
                                            cancelSend(item.id+"",reason);
                                        }
                                    });
                                });
                            }
                        });
            }

        });
        tv_btn2.setOnClickListener(view -> {
            switch (item.status){

                case 1:
                    CreateOrderBean createOrderBean = new CreateOrderBean();
                    createOrderBean.order_id = item.id+"";
                    createOrderBean.pay_amount = item.goods_amount;
                    SelectPayWayActivity.launch(getContext(),createOrderBean);
                    break;
                case 2:

                    break;
                case 3:
                    CenterDialogUtil.showTwo(getContext(),"提示","确定要收货吗？","取消","确定",s -> {
                        if ("1".equals(s)){
                                receive(item.id+"");
                        }
                    });
                    break;
                case 4:
                    OrderCommentActivity.launch(getContext(),item.id);
                    break;
            }

        });
        helper.getView(R.id.tv_shop).setOnClickListener(view -> {
            ShopDetailActivity.launchShop(getContext(),item.store_id+"");
        });


        LinearLayout ll_root = helper.getView(R.id.ll_root);
        ll_root.setOnClickListener(v -> {
            OrderDetailActivity.launch(getContext(),item.id);
        });
//        ll_root.setOnTouchListener(new View.OnTouchListener() {
//            @Override
//            public boolean onTouch(View v, MotionEvent event) {
//                return false;
//            }
//        });
        rv_goods.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (event.getAction() == MotionEvent.ACTION_UP) {
                    ll_root.performClick();  //模拟父控件的点击
                }
                return false;
            }
        });



    }


    private void receive(String order_id){
        new RxHttp<BaseResult>().send(ApiManager.getService().getOrderReceipt(order_id),
                new Response<BaseResult>(getContext()) {
                    @Override
                    public void onSuccess(BaseResult result) {
                        ToastCommom.createToastConfig().ToastShow(getContext(),"收货成功");
                        EventBus.getDefault().post(new ReceiveOrder());
                    }
                });
    }

    private void cancel(String order_id){
        new RxHttp<BaseResult>().send(ApiManager.getService().getOrderCancel(order_id),
                new Response<BaseResult>(getContext()) {
                    @Override
                    public void onSuccess(BaseResult result) {
                        ToastCommom.createToastConfig().ToastShow(getContext(),"取消成功");
                        EventBus.getDefault().post(new ReceiveOrder());
                    }
                });
    }


    private void cancelSend(String order_id,String reason){

        TreeMap<String, String> hashMap = new TreeMap<>();
        hashMap.put("id", ""+order_id);
        hashMap.put("reason", reason);
        new RxHttp<BaseResult>().send(ApiManager.getService().getSendOrderCancel(hashMap),
                new Response<BaseResult>(getContext()) {
                    @Override
                    public void onSuccess(BaseResult result) {
                        ToastCommom.createToastConfig().ToastShow(getContext(),"取消成功");
                        EventBus.getDefault().post(new ReceiveOrder());
                    }
                });
    }

}
