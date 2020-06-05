package com.cinderellavip.ui.activity.order;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.cinderellavip.R;
import com.cinderellavip.adapter.listview.OrderDetailAdapter;
import com.cinderellavip.bean.eventbus.OrderRefund;
import com.cinderellavip.bean.eventbus.ReceiveOrder;
import com.cinderellavip.bean.net.NetCityBean;
import com.cinderellavip.bean.net.order.CreateOrderBean;
import com.cinderellavip.bean.net.order.OrderGoodsInfo;
import com.cinderellavip.bean.net.order.OrderInfo;
import com.cinderellavip.bean.net.order.OrderInfoResult;
import com.cinderellavip.http.ApiManager;
import com.cinderellavip.http.BaseResult;
import com.cinderellavip.http.ListResult;
import com.cinderellavip.http.Response;
import com.cinderellavip.toast.CenterDialogUtil;
import com.cinderellavip.toast.ReturnUtil;
import com.cinderellavip.ui.activity.home.ShopDetailActivity;
import com.cinderellavip.ui.activity.mine.LogisticsActivity;
import com.cinderellavip.ui.web.AgreementWebViewActivity;
import com.cinderellavip.util.ClipBoardUtil;
import com.cinderellavip.util.Utils;
import com.cinderellavip.weight.MyListView;
import com.tozzais.baselibrary.http.RxHttp;
import com.tozzais.baselibrary.ui.BaseActivity;
import com.tozzais.baselibrary.util.log.LogUtil;
import com.tozzais.baselibrary.util.toast.ToastCommom;

import org.greenrobot.eventbus.EventBus;

import java.util.List;
import java.util.TreeMap;

import butterknife.BindView;
import butterknife.OnClick;

/**
 *
 */
public class OrderDetailActivity extends BaseActivity {

    @BindView(R.id.tv_status)
    TextView tvStatus;
    @BindView(R.id.tv_status1)
    TextView tvStatus1;
    @BindView(R.id.iv_status)
    ImageView ivStatus;
    @BindView(R.id.ll_selete_address)
    LinearLayout llSeleteAddress;
    @BindView(R.id.lv_goods)
    MyListView lvGoods;
    @BindView(R.id.tv_btn_bottom1)
    TextView tvBtnBottom1;
    @BindView(R.id.tv_btn_bottom2)
    TextView tvBtnBottom2;
    @BindView(R.id.tv_send_time)
    TextView tv_send_time; //发货时间
    @BindView(R.id.tv_receive_time)
    TextView tv_receive_time; //发货时间
    @BindView(R.id.tv_pay_way)
    TextView tv_pay_way; //支付方式
    @BindView(R.id.ll_bottom)
    LinearLayout llBottom;
    @BindView(R.id.tv_name)
    TextView tvName;
    @BindView(R.id.tv_address)
    TextView tvAddress;
    @BindView(R.id.tv_default_line)
    View tvDefaultLine;
    @BindView(R.id.tv_shop)
    TextView tvShop;
    @BindView(R.id.tv_goods_number)
    TextView tvGoodsNumber;
    @BindView(R.id.tv_goods_money)
    TextView tvGoodsMoney;
    @BindView(R.id.tv_coupon_money)
    TextView tvCouponMoney;
    @BindView(R.id.tv_tax)
    TextView tvTax;
    @BindView(R.id.tv_pay_money)
    TextView tvPayMoney;
    @BindView(R.id.tv_order_id)
    TextView tvOrderId;
    @BindView(R.id.tv_copy)
    TextView tvCopy;
    @BindView(R.id.tv_time_pay)
    TextView tvTimePay;

    private int order_id;


    /**
     * @param activity
     * @param order_id 订单ID
     */
    public static void launch(Context activity, int order_id) {
        if (!Utils.isFastClick()){
            return;
        }
        Intent intent = new Intent(activity, OrderDetailActivity.class);
        intent.putExtra("order_id", order_id);
        activity.startActivity(intent);
    }


    @Override
    public int getLayoutId() {
        return R.layout.activity_order_detail;
    }

    @Override
    public void initView(Bundle savedInstanceState) {
        order_id = getIntent().getIntExtra("order_id", 0);
        setBackTitle("订单详情");
    }

    @Override
    public void loadData() {
//
        getDetailInfo();
    }

    private void getDetailInfo() {
        new RxHttp<BaseResult<OrderInfoResult<OrderInfo>>>().send(ApiManager.getService().getOrderDetail(order_id + ""),
                new Response<BaseResult<OrderInfoResult<OrderInfo>>>(isLoad, mActivity) {
                    @Override
                    public void onSuccess(BaseResult<OrderInfoResult<OrderInfo>> result) {
                        setData(result.data.order);
                    }

                    @Override
                    public void onErrorShow(String s) {
                        showError(s);
                    }
                });

    }

    private OrderInfo orderInfo;
    private void setData(OrderInfo orderInfo) {
        this.orderInfo = orderInfo;
        setStatus(orderInfo.status);
        tvStatus.setText(orderInfo.status_txt);
        NetCityBean address = orderInfo.address;
        List<OrderGoodsInfo> goods = orderInfo.goods;
        //为了退款用的
        for (OrderGoodsInfo orderGoodsInfo:goods){
            orderGoodsInfo.order_id = order_id;
//            LogUtil.e("orderGoodsInfo  order_id== "+orderGoodsInfo.order_id +"=="+order_id );
        }
        tvName.setText(address.name+"  "+address.mobile);
        tvAddress.setText(address.province+address.city+address.area+address.address);

        tvShop.setText(orderInfo.store_name);
        tvGoodsNumber.setText("（共计"+ goods.size()+"件）");
        lvGoods.setAdapter(new OrderDetailAdapter(goods,mActivity));

        tvGoodsMoney.setText("￥"+orderInfo.goods_amount);
        tvCouponMoney.setText("￥"+orderInfo.dis_amount);
        tvTax.setText("￥"+orderInfo.ship_amount);
        tvPayMoney.setText("￥"+orderInfo.total_amount);

        tvOrderId.setText("订单编号："+orderInfo.order_no);
        tvTimePay.setText("下单时间："+orderInfo.create_at);
        tv_pay_way.setText("付款时间："+orderInfo.pay_at);
        tv_send_time.setText("发货时间："+orderInfo.send_at);
        tv_receive_time.setText("收货时间："+orderInfo.receipt_at);




    }

    private void setStatus(int status) {
        switch (status) {
            case 0:

                tvStatus1.setVisibility(View.GONE);
                ivStatus.setImageResource(R.mipmap.order_status_1);
                llBottom.setVisibility(View.GONE);
                break;
            case 1:
                tvStatus1.setVisibility(View.GONE);
                ivStatus.setImageResource(R.mipmap.order_status_1);
                llBottom.setVisibility(View.VISIBLE);
                tvBtnBottom1.setVisibility(View.VISIBLE);
                tvBtnBottom2.setVisibility(View.VISIBLE);
                tvBtnBottom1.setText("取消订单");
                tvBtnBottom2.setText("立即付款");
                break;
            case 2:
                tvStatus1.setVisibility(View.GONE);
                tvBtnBottom2.setVisibility(View.GONE);
                ivStatus.setImageResource(R.mipmap.order_status_1);
                llBottom.setVisibility(View.VISIBLE);
                tvBtnBottom1.setText("取消订单");
                tv_pay_way.setVisibility(View.VISIBLE);
                break;
            case 3:
                tvStatus1.setVisibility(View.GONE);
                ivStatus.setImageResource(R.mipmap.order_status_1);
                tv_pay_way.setVisibility(View.VISIBLE);
                tv_send_time.setVisibility(View.VISIBLE);
                llBottom.setVisibility(View.VISIBLE);
                tvBtnBottom1.setVisibility(View.VISIBLE);
                tvBtnBottom2.setVisibility(View.VISIBLE);
                tvBtnBottom1.setText("查看物流");
                tvBtnBottom2.setText("确认收货");
                break;
            case 5:
                tvStatus1.setVisibility(View.GONE);
                llBottom.setVisibility(View.VISIBLE);
                tv_receive_time.setVisibility(View.VISIBLE);
                tv_send_time.setVisibility(View.VISIBLE);
                tv_pay_way.setVisibility(View.VISIBLE);
                ivStatus.setImageResource(R.mipmap.order_status_1);
                llBottom.setVisibility(View.VISIBLE);
                tvBtnBottom2.setVisibility(View.GONE);
                tvBtnBottom1.setVisibility(View.VISIBLE);
                tvBtnBottom1.setText("查看物流");
                break;
            case 4:
                tvStatus1.setVisibility(View.GONE);
                tv_receive_time.setVisibility(View.VISIBLE);
                tv_send_time.setVisibility(View.VISIBLE);
                tv_pay_way.setVisibility(View.VISIBLE);
                ivStatus.setImageResource(R.mipmap.order_status_1);
                llBottom.setVisibility(View.VISIBLE);
                tvBtnBottom1.setVisibility(View.VISIBLE);
                tvBtnBottom2.setVisibility(View.VISIBLE);
                tvBtnBottom1.setText("查看物流");
                tvBtnBottom2.setText("立即评价");
                break;
        }

    }


    @OnClick({R.id.tv_btn_bottom1, R.id.tv_btn_bottom2, R.id.tv_shop, R.id.tv_copy})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.tv_copy:
                LogUtil.e("onClick");
                if(orderInfo != null)
                ClipBoardUtil.copy(mActivity, orderInfo.order_no);
                break;
            case R.id.tv_shop:
                if(orderInfo != null)
                ShopDetailActivity.launchShop(mActivity,orderInfo.store_id+"");
                break;
            case R.id.tv_btn_bottom1:
                if(orderInfo != null)
                switch (orderInfo.status) {
                    case 1:
                        CenterDialogUtil.showTwo(mActivity,"提示","确定要取消该订单吗？","取消","确定", s -> {
                            if ("1".equals(s)){
                                cancel(orderInfo.id+"");
                            }
                        });
                        break;
                    case 2:
                        new RxHttp<BaseResult<ListResult<String>>>().send(ApiManager.getService().refundReason(),
                                new Response<BaseResult<ListResult<String>>>(mActivity) {
                                    @Override
                                    public void onSuccess(BaseResult<ListResult<String>> result) {
                                        ReturnUtil.showSelectDialog(mActivity,"请选择取消理由", result.data.list, reason -> {

                                            CenterDialogUtil.showTwo(mActivity,"确定要取消订单吗？",
                                                    "待发货订单，需在3小时之内取消，超过3小时无法取消",
                                                    "暂不取消","确定取消",s -> {
                                                        if ("1".equals(s)){
                                                            cancelSend(orderInfo.id+"",reason);
                                                        }
                                                    });
                                        });
                                    }
                                });
                        break;
                    case 3:
                    case 5:
                    case 4:
                        if (orderInfo.virtual){
                            AgreementWebViewActivity.launch(mActivity,orderInfo.send_remark);
                        }else {
                            LogisticsActivity.launch(mActivity,orderInfo.id+"");
                        }
                        break;
                }
//
                break;
            case R.id.tv_btn_bottom2:
                if(orderInfo != null)
                    switch (orderInfo.status) {
                        case 1:
                            CreateOrderBean createOrderBean = new CreateOrderBean();
                            createOrderBean.order_id = orderInfo.id+"";
                            createOrderBean.pay_amount = orderInfo.total_amount;
                            SelectPayWayActivity.launch(mActivity,createOrderBean);
                            break;
                        case 3:
                            CenterDialogUtil.showTwo(mActivity,"提示","确定要收货吗？","取消","确定",s -> {
                                if ("1".equals(s)){
                                    receive(orderInfo.id+"");
                                }
                            });
                            break;
                        case 4:
                            OrderCommentActivity.launch(mActivity,orderInfo.id);
                            break;
                    }
                break;
        }
    }

    private void receive(String order_id){
        new RxHttp<BaseResult>().send(ApiManager.getService().getOrderReceipt(order_id),
                new Response<BaseResult>(mActivity) {
                    @Override
                    public void onSuccess(BaseResult result) {
                        ToastCommom.createToastConfig().ToastShow(mActivity,"收货成功");
                        EventBus.getDefault().post(new ReceiveOrder());
                        loadData();
                    }
                });
    }

    private void cancel(String order_id){
        new RxHttp<BaseResult>().send(ApiManager.getService().getOrderCancel(order_id),
                new Response<BaseResult>(mActivity) {
                    @Override
                    public void onSuccess(BaseResult result) {
                        tsg("取消成功");
                        EventBus.getDefault().post(new ReceiveOrder());
                        loadData();
                    }
                });
    }


    private void cancelSend(String order_id,String reason){

        TreeMap<String, String> hashMap = new TreeMap<>();
        hashMap.put("id", ""+order_id);
        hashMap.put("reason", reason);
        new RxHttp<BaseResult>().send(ApiManager.getService().getSendOrderCancel(hashMap),
                new Response<BaseResult>(mActivity) {
                    @Override
                    public void onSuccess(BaseResult result) {
                        tsg("取消成功");
                        EventBus.getDefault().post(new ReceiveOrder());
                        loadData();
                    }
                });
    }

    @Override
    public void onEvent(Object o) {
        super.onEvent(o);
        if (o instanceof OrderRefund){
            loadData();
        }
    }
}