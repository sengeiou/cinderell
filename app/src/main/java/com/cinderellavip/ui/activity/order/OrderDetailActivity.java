package com.cinderellavip.ui.activity.order;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.cinderellavip.R;
import com.cinderellavip.adapter.listview.OrderDetailAdapter;
import com.cinderellavip.bean.eventbus.ReceiveOrder;
import com.cinderellavip.bean.net.NetCityBean;
import com.cinderellavip.bean.net.order.CreateOrderBean;
import com.cinderellavip.bean.net.order.OrderGoodsInfo;
import com.cinderellavip.bean.net.order.OrderInfo;
import com.cinderellavip.bean.net.order.OrderInfoResult;
import com.cinderellavip.http.ApiManager;
import com.cinderellavip.http.BaseResult;
import com.cinderellavip.http.Response;
import com.cinderellavip.toast.CenterDialogUtil;
import com.cinderellavip.ui.activity.home.ShopDetailActivity;
import com.cinderellavip.ui.activity.mine.LogisticsActivity;
import com.cinderellavip.util.ClipBoardUtil;
import com.cinderellavip.util.DataUtil;
import com.cinderellavip.weight.MyListView;
import com.tozzais.baselibrary.http.RxHttp;
import com.tozzais.baselibrary.ui.BaseActivity;
import com.tozzais.baselibrary.util.log.LogUtil;
import com.tozzais.baselibrary.util.toast.ToastCommom;

import org.greenrobot.eventbus.EventBus;

import java.util.List;

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
                });

    }

    private OrderInfo orderInfo;
    private void setData(OrderInfo orderInfo) {
        this.orderInfo = orderInfo;
        setStatus(orderInfo.status);
        NetCityBean address = orderInfo.address;
        List<OrderGoodsInfo> goods = orderInfo.goods;
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
        tv_send_time.setText("发货时间："+orderInfo.pay_at);
        tv_receive_time.setText("收货时间："+orderInfo.receipt_at);




    }

    private void setStatus(int status) {
        switch (status) {
            case 0:
                tvStatus.setText("已取消");
                tvStatus1.setVisibility(View.GONE);
                ivStatus.setImageResource(R.mipmap.order_status_1);
                llBottom.setVisibility(View.GONE);
                break;
            case 1:
                tvStatus.setText("待付款");
                tvStatus1.setVisibility(View.GONE);
                ivStatus.setImageResource(R.mipmap.order_status_1);
                llBottom.setVisibility(View.VISIBLE);
                tvBtnBottom1.setVisibility(View.VISIBLE);
                tvBtnBottom2.setVisibility(View.VISIBLE);
                tvBtnBottom1.setText("取消订单");
                tvBtnBottom2.setText("立即付款");
                break;
            case 2:
                tvStatus.setText("待发货");
                tvStatus1.setVisibility(View.GONE);
                ivStatus.setImageResource(R.mipmap.order_status_1);
                llBottom.setVisibility(View.GONE);
                break;
            case 3:
                tvStatus.setText("待收货");
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
                tvStatus.setText("交易完成");
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
                tvStatus.setText("待评价");
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
                    case 3:
                    case 5:
                    case 4:
                        LogisticsActivity.launch(mActivity,orderInfo.id+"");
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
                            OrderCommentActivity.launch(mActivity,1);
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
                        ToastCommom.createToastConfig().ToastShow(mActivity,"取消成功");
                        EventBus.getDefault().post(new ReceiveOrder());
                        loadData();
                    }
                });
    }
}