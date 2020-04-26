package com.cinderellavip.ui.activity.life;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.cinderellavip.R;
import com.cinderellavip.toast.CenterDialogUtil;
import com.tozzais.baselibrary.ui.BaseActivity;

import butterknife.BindView;
import butterknife.OnClick;


/**
 * Created by Administrator on 2016/9/8.
 */
public class SingleServiceOrderDetailActivity extends BaseActivity {


    @BindView(R.id.tv_btn1)
    TextView tvBtn1;
    @BindView(R.id.tv_btn2)
    TextView tvBtn2;
    @BindView(R.id.ll_bottom)
    LinearLayout llBottom;

    private int type;
    public static void launch(Context from,int type) {
        Intent intent = new Intent(from, SingleServiceOrderDetailActivity.class);
        intent.putExtra("type",type);
        from.startActivity(intent);
    }


    @Override
    public void initView(Bundle savedInstanceState) {
        type = getIntent().getIntExtra("type",0);
        setBackTitle("订单详情");
        setRightText("联系客服");

    }


    @Override
    public void loadData() {
        if (type == 0|| type == 2 || type == 4){
            llBottom.setVisibility(View.GONE);
        }else if (type == 3){
            tvBtn1.setVisibility(View.GONE);
            tvBtn2.setText("评价");
        }

    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_single_order_detail;
    }


    @OnClick({R.id.tv_btn1, R.id.tv_btn2})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.tv_btn1:
                CenterDialogUtil.showServiceOrder(mActivity,"操作提示","您确定要取消订单吗？\n取消后不可撤回"
                ,"取消","确定",s -> {
                    tsg("订单已取消");
                        });
                break;
            case R.id.tv_btn2:
                if (type == 1){
                    PayCheckoutCounterActivity.launch(mActivity);
                }else if (type == 3){

                }
                break;
        }
    }
}
