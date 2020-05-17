package com.cinderellavip.ui.activity.order;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.cinderellavip.R;
import com.cinderellavip.adapter.recycleview.CommentImageAdapter;
import com.cinderellavip.adapter.recycleview.OrderGoodsAdapter;
import com.cinderellavip.bean.OrderResult;
import com.cinderellavip.bean.net.order.OrderGoodsInfo;
import com.cinderellavip.bean.net.order.ReturnOrderInfoResult;
import com.cinderellavip.http.ApiManager;
import com.cinderellavip.http.BaseResult;
import com.cinderellavip.http.Response;
import com.tozzais.baselibrary.http.RxHttp;
import com.tozzais.baselibrary.ui.BaseActivity;

import java.util.ArrayList;
import java.util.List;
import java.util.TreeMap;

import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.BindView;

/**
 *
 */
public class RefundDetailActivity extends BaseActivity {

    @BindView(R.id.tv_status)
    TextView tvStatus;
    @BindView(R.id.tv_status1)
    TextView tvStatus1;
    @BindView(R.id.iv_status)
    ImageView ivStatus;
    @BindView(R.id.rv_goods)
    RecyclerView rvGoods;
    @BindView(R.id.rv_image)
    RecyclerView rv_image;
    @BindView(R.id.ll_failed_reason)
    LinearLayout ll_failed_reason;
    @BindView(R.id.tv_number)
    TextView tvNumber;
    @BindView(R.id.tv_pay_money)
    TextView tvPayMoney;
    @BindView(R.id.tv_create_time)
    TextView tvCreateTime;
    @BindView(R.id.tv_pay_time)
    TextView tvPayTime;
    @BindView(R.id.tv_send_time)
    TextView tv_send_time;
    @BindView(R.id.tv_receive_time)
    TextView tv_receive_time;
    @BindView(R.id.tv_refund_reason)
    TextView tvRefundReason;
    @BindView(R.id.tv_refund_time)
    TextView tvRefundTime;
    @BindView(R.id.tv_refund_content)
    TextView tvRefundContent;
    @BindView(R.id.ll_refund_content)
    LinearLayout llRefundContent;
    @BindView(R.id.tv_fail_reason)
    TextView tvFailReason;
    @BindView(R.id.tv_fail_time)
    TextView tvFailTime;

    private int refund_id;


    public static void launch(Context activity, int refund_id) {
        Intent intent = new Intent(activity, RefundDetailActivity.class);
        intent.putExtra("refund_id", refund_id);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP );
        activity.startActivity(intent);
    }


    @Override
    public int getLayoutId() {
        return R.layout.activity_refund_detail;
    }

    @Override
    public void initView(Bundle savedInstanceState) {
        refund_id = getIntent().getIntExtra("refund_id", -1);
//        setStatus(status);
        setBackTitle("退款详情");
    }

    @Override
    public void loadData() {
        if (!isLoad) showProress();
        TreeMap<String, String> hashMap = new TreeMap<>();
        hashMap.put("order_id", refund_id + "");
        new RxHttp<BaseResult<OrderResult<ReturnOrderInfoResult>>>().send(ApiManager.getService().refundOrderInfo(hashMap),
                new Response<BaseResult<OrderResult<ReturnOrderInfoResult>>>(isLoad, mActivity) {
                    @Override
                    public void onSuccess(BaseResult<OrderResult<ReturnOrderInfoResult>> result) {
                        showContent();
                        setData(result.data.order);

                    }

                    @Override
                    public void onErrorShow(String s) {
                        showError(s);
                    }
                });



    }

    private void setData(ReturnOrderInfoResult result) {

        tvStatus.setText(result.status_txt);
        tvStatus1.setVisibility(View.GONE);


        rvGoods.setLayoutManager(new LinearLayoutManager(mContext));
        OrderGoodsAdapter adapter = new OrderGoodsAdapter();
        rvGoods.setAdapter(adapter);
        List<OrderGoodsInfo> list = new ArrayList<>();
        list.add(result.goods);
        adapter.setNewData(list);

        tvNumber.setText("订单编号："+result.order_no);
        tvPayMoney.setText("实付金额：￥"+result.total_amount);
        tvCreateTime.setText("下单时间：￥"+result.create_at);
        tvPayTime.setText("支付时间：￥"+result.pay_at);
        tv_send_time.setText("发货时间：￥"+result.send_at);
        tv_receive_time.setText("收货时间：￥"+result.receipt_at);


        tvRefundReason.setText(result.refund_reason);
        tvRefundTime.setText("提交时间："+result.refund_submit_at);
        tvRefundContent.setText(result.refund_comment);






        rv_image.setLayoutManager(new GridLayoutManager(mActivity,4));
        CommentImageAdapter adapter1 = new CommentImageAdapter();
        rv_image.setAdapter(adapter1);
        adapter1.setNewData(result.refund_images);

        if (result.status == 1){
            ll_failed_reason.setVisibility(View.GONE);
        }else {
            ll_failed_reason.setVisibility(View.VISIBLE);
            tvFailReason.setText(result.refund_review_at);
            tvFailTime.setText(result.fail_reason);

        }


    }




}