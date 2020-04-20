package com.cinderellavip.ui.activity.order;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;


import com.cinderellavip.R;
import com.cinderellavip.adapter.recycleview.CommentImageAdapter;
import com.cinderellavip.adapter.recycleview.OrderGoodsAdapter;
import com.cinderellavip.global.GlobalParam;
import com.cinderellavip.http.ApiManager;
import com.cinderellavip.util.DataUtil;
import com.tozzais.baselibrary.ui.BaseActivity;
import com.tozzais.baselibrary.util.sign.SignUtil;

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

        setData();

    }

    private void setData() {


        switch (refund_id) {
            case 0:
                tvStatus.setText("审核中");
                tvStatus1.setVisibility(View.VISIBLE);
                ll_failed_reason.setVisibility(View.GONE);
                break;
            case 1:
                tvStatus.setText("审核通过");
                tvStatus1.setVisibility(View.GONE);
                ll_failed_reason.setVisibility(View.GONE);
                break;
            case 2:
                tvStatus.setText("审核失败");
                tvStatus1.setVisibility(View.GONE);
                ll_failed_reason.setVisibility(View.VISIBLE);
                break;

        }


        rvGoods.setLayoutManager(new LinearLayoutManager(mContext));
        OrderGoodsAdapter adapter = new OrderGoodsAdapter();
        rvGoods.setAdapter(adapter);
        adapter.setNewData(DataUtil.getData(1));


        rv_image.setLayoutManager(new GridLayoutManager(mActivity,4));
        CommentImageAdapter adapter1 = new CommentImageAdapter();
        rv_image.setAdapter(adapter1);
        adapter1.setNewData(DataUtil.getData(4));


    }




}