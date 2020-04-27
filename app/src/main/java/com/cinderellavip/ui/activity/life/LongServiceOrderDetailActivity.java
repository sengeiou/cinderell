package com.cinderellavip.ui.activity.life;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.cinderellavip.R;
import com.cinderellavip.toast.CenterDialogUtil;
import com.cinderellavip.toast.DialogUtil;
import com.cinderellavip.ui.activity.WebViewActivity;
import com.tozzais.baselibrary.ui.BaseActivity;

import butterknife.BindView;
import butterknife.OnClick;


/**
 * Created by Administrator on 2016/9/8.
 */
public class LongServiceOrderDetailActivity extends BaseActivity {


    @BindView(R.id.tv_btn1)
    TextView tvBtn1;
    @BindView(R.id.tv_btn2)
    TextView tvBtn2;
    @BindView(R.id.ll_bottom)
    LinearLayout llBottom;

    private int type;

    public static void launch(Context from, int type) {
        Intent intent = new Intent(from, LongServiceOrderDetailActivity.class);
        intent.putExtra("type", type);
        from.startActivity(intent);
    }


    @Override
    public void initView(Bundle savedInstanceState) {
        type = getIntent().getIntExtra("type", 0);
        setBackTitle("订单详情");
        setRightText("联系客服");

    }


    @Override
    public void loadData() {

        setStatus();

    }




    @Override
    public int getLayoutId() {
        return R.layout.activity_longservice_order_detail;
    }


    @OnClick({R.id.tv_btn1, R.id.tv_btn2, R.id.tv_look, R.id.tv_cost_explain})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.tv_cost_explain:
                CenterDialogUtil.showCostExplain(mActivity,s -> {});
            break;
            case R.id.tv_look:
                WebViewActivity.launch(mActivity,"服务签约电子合同","https://www.baidu.com");
                break;
            case R.id.tv_btn1:
                if (type == 1 || type == 2) {
                    CenterDialogUtil.showServiceOrder(mActivity, "操作提示", "您确定要取消订单吗？\n取消后不可撤回"
                            , "取消", "确定", s -> {
                                tsg("订单已取消");
                            });
                }
                break;
            case R.id.tv_btn2:
                if (type == 1) {
                    CenterDialogUtil.showServiceOrder(mActivity, "操作提示", "请仔细核对订单信息？\n如有问题，请联系客服修改"
                            , "取消", "确定", s -> {
                                tsg("订单已确定，请完成支付");
                            });
                }else if (type == 2) {
                    PayCheckoutCounterActivity.launch(mActivity,1);
                } else if (type == 5) {
                    ServiceOrderCommentActivity.launch(mActivity);
                }
                break;
        }
    }

    @Override
    public void initListener() {
        super.initListener();
        tv_right.setOnClickListener(v -> {
            DialogUtil.showCallPhoneDialog(mActivity);
        });
    }

    @BindView(R.id.iv_progress1)
    ImageView ivProgress1;
    @BindView(R.id.tv_progress1)
    TextView tvProgress1;
    @BindView(R.id.view1)
    View view1;
    @BindView(R.id.view2)
    View view2;
    @BindView(R.id.iv_progress2)
    ImageView ivProgress2;
    @BindView(R.id.tv_progress2)
    TextView tvProgress2;
    @BindView(R.id.view3)
    View view3;
    @BindView(R.id.view4)
    View view4;
    @BindView(R.id.iv_progress3)
    ImageView ivProgress3;
    @BindView(R.id.tv_progress3)
    TextView tvProgress3;
    @BindView(R.id.view5)
    View view5;
    @BindView(R.id.view6)
    View view6;
    @BindView(R.id.iv_progress4)
    ImageView ivProgress4;
    @BindView(R.id.tv_progress4)
    TextView tvProgress4;
    private void setStatus() {
        if (type == 1) {
            ivProgress1.setImageResource(R.mipmap.progress_left_select);
            view1.setBackgroundColor(getColor(R.color.yellow_progress));
            tvBtn1.setText("取消");
            tvBtn2.setText("确定");
        }else if (type == 2) {
            ivProgress1.setImageResource(R.mipmap.progress_left_select);
            view1.setBackgroundColor(getColor(R.color.yellow_progress));
            view2.setBackgroundColor(getColor(R.color.yellow_progress));
            ivProgress2.setImageResource(R.mipmap.progress_center_select);
            view3.setBackgroundColor(getColor(R.color.yellow_progress));
            tvBtn1.setText("取消");
            tvBtn2.setText("支付");
        }else if (type == 3) {
            llBottom.setVisibility(View.GONE);
            ivProgress1.setImageResource(R.mipmap.progress_left_select);
            view1.setBackgroundColor(getColor(R.color.yellow_progress));
            view2.setBackgroundColor(getColor(R.color.yellow_progress));
            ivProgress2.setImageResource(R.mipmap.progress_center_select);
            view3.setBackgroundColor(getColor(R.color.yellow_progress));
            view4.setBackgroundColor(getColor(R.color.yellow_progress));
            ivProgress3.setImageResource(R.mipmap.progress_center_select);
            view5.setBackgroundColor(getColor(R.color.yellow_progress));
        }else if (type == 4 ||type == 5) {
            if (type == 4){
                llBottom.setVisibility(View.GONE);
            }else {
                tvBtn1.setVisibility(View.GONE);
                tvBtn2.setText("评价");
            }
            ivProgress1.setImageResource(R.mipmap.progress_left_select);
            view1.setBackgroundColor(getColor(R.color.yellow_progress));
            view2.setBackgroundColor(getColor(R.color.yellow_progress));
            ivProgress2.setImageResource(R.mipmap.progress_center_select);
            view3.setBackgroundColor(getColor(R.color.yellow_progress));
            view4.setBackgroundColor(getColor(R.color.yellow_progress));
            ivProgress3.setImageResource(R.mipmap.progress_center_select);
            view5.setBackgroundColor(getColor(R.color.yellow_progress));
            view5.setBackgroundColor(getColor(R.color.yellow_progress));
            ivProgress3.setImageResource(R.mipmap.progress_right_select);
        }
    }

}
