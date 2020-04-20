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
import com.cinderellavip.toast.CenterDialogUtil;
import com.cinderellavip.ui.activity.mine.LogisticsActivity;
import com.cinderellavip.util.DataUtil;
import com.cinderellavip.weight.MyListView;
import com.tozzais.baselibrary.ui.BaseActivity;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

/**
 *
 */
public class OrderDetailActivity extends BaseActivity {
    public static final int PAY = 1, SEND = 2, RECEIVE = 3, COMPLETE = 4, EVALUATION = 5;
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
    private int status;


    /**
     * @param activity
     * @param status   订单的状态
     */
    public static void launch(Context activity, int status) {
        Intent intent = new Intent(activity, OrderDetailActivity.class);
        intent.putExtra("status", status);
        activity.startActivity(intent);
    }


    @Override
    public int getLayoutId() {
        return R.layout.activity_order_detail;
    }

    @Override
    public void initView(Bundle savedInstanceState) {
        status = getIntent().getIntExtra("status", PAY);
        setStatus(status);
        setBackTitle("订单详情");
    }

    @Override
    public void loadData() {
        List<String> list = new ArrayList<>();

        lvGoods.setAdapter(new OrderDetailAdapter(
                DataUtil.getData(2), mContext,status == COMPLETE || status == EVALUATION));

//        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(mContext);
//        linearLayoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
//        rvGoods.setLayoutManager(linearLayoutManager);
//        SettlementMoreGoodsAdapter goodsAdapter = new SettlementMoreGoodsAdapter();
//        rvGoods.setAdapter(goodsAdapter);
//        goodsAdapter.setNewData(DataUtil.getData(10));
    }

    private void setStatus(int status) {
        switch (status) {
            case PAY:
                tvStatus.setText("待付款");
                tvStatus1.setVisibility(View.GONE);
                ivStatus.setImageResource(R.mipmap.order_status_1);
                llBottom.setVisibility(View.VISIBLE);
                tvBtnBottom1.setVisibility(View.VISIBLE);
                tvBtnBottom2.setVisibility(View.VISIBLE);
                tvBtnBottom1.setText("取消订单");
                tvBtnBottom2.setText("立即付款");
                tv_pay_way.setVisibility(View.GONE);
                break;
            case SEND:
                tvStatus.setText("待发货");
                tvStatus1.setVisibility(View.GONE);
                ivStatus.setImageResource(R.mipmap.order_status_1);
                tvBtnBottom1.setVisibility(View.VISIBLE);
                tvBtnBottom2.setVisibility(View.GONE);
                tvBtnBottom1.setText("申请退款");
                tv_pay_way.setVisibility(View.VISIBLE);
                llBottom.setVisibility(View.GONE);
                break;
            case RECEIVE:
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
            case COMPLETE:
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
            case EVALUATION:
                tvStatus.setText("待评价");
                tvStatus1.setVisibility(View.GONE);
                tvBtnBottom1.setVisibility(View.VISIBLE);
                tvBtnBottom2.setVisibility(View.VISIBLE);
                tvBtnBottom1.setText("查看物流");
                tvBtnBottom2.setText("去评价");
                tv_receive_time.setVisibility(View.VISIBLE);
                tv_send_time.setVisibility(View.VISIBLE);
                tv_pay_way.setVisibility(View.VISIBLE);
                ivStatus.setImageResource(R.mipmap.order_status_1);
                break;


        }

    }


    @OnClick({R.id.tv_btn_bottom1, R.id.tv_btn_bottom2})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.tv_btn_bottom1:
                if (status == RECEIVE){
                    LogisticsActivity.launch(mActivity,"1");
                }else if (status == COMPLETE ||status == EVALUATION ){
                    LogisticsActivity.launch(mActivity,"1");
                }
                break;
            case R.id.tv_btn_bottom2:
                if (status == PAY){
                    SelectPayWayActivity.launch(mActivity,1,"");
//                    OrderCommentActivity.launch(mActivity,1);
                }else if (status == RECEIVE){
                    CenterDialogUtil.showTwo(mContext,"是否确认收货？",
                            "请确保已经收到商品，确认收货后，不可撤销，请谨慎操作。"
                            ,"暂不收货","确定收货", type1 -> {
                    });
                }else if (status == EVALUATION){
                    OrderCommentActivity.launch(mActivity,1);
                }
                break;
        }
    }
}