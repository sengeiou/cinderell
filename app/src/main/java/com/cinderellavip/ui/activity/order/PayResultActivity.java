package com.cinderellavip.ui.activity.order;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.cinderellavip.MainActivity;
import com.cinderellavip.R;
import com.cinderellavip.adapter.recycleview.LikeGoodsAdapter;
import com.cinderellavip.util.DataUtil;
import com.cinderellavip.weight.GirdSpace;
import com.tozzais.baselibrary.ui.BaseListActivity;
import com.tozzais.baselibrary.util.DpUtil;

import androidx.recyclerview.widget.GridLayoutManager;

/**
 *
 */
public class PayResultActivity extends BaseListActivity implements View.OnClickListener {



    private TextView tv_pay_result;
    private TextView tv_pay_money;
    private TextView tv_look_order;
    private TextView tv_go_home;
    private ImageView iv_code;


    private int order_id;
    private boolean success;

    public static void launch(Activity activity, int order_id, boolean success) {
        Intent intent = new Intent(activity, PayResultActivity.class);
        intent.putExtra("order_id", order_id);
        intent.putExtra("success", success);
        activity.startActivity(intent);
        activity.finish();
    }


    @Override
    public int getLayoutId() {
        return R.layout.layout_recycleview;
    }

    @Override
    public void initView(Bundle savedInstanceState) {
        super.initView(savedInstanceState);
        setBackTitle("支付结果");
        order_id = getIntent().getIntExtra("order_id",-1);
        success = getIntent().getBooleanExtra("success",false);



        mAdapter = new LikeGoodsAdapter();
        GridLayoutManager linearLayoutManager = new GridLayoutManager(mActivity, 2);
        mRecyclerView.setLayoutManager(linearLayoutManager);
        GirdSpace girdSpace = new GirdSpace(DpUtil.dip2px(mActivity, 8),2,1);
        mRecyclerView.addItemDecoration(girdSpace);
        mRecyclerView.setAdapter(mAdapter);

        View v = View.inflate(mContext, R.layout.header_pay_result, null);
        tv_pay_result = v.findViewById(R.id.tv_pay_result);
        tv_pay_money = v.findViewById(R.id.tv_pay_money);
        tv_look_order = v.findViewById(R.id.tv_look_order);
        tv_go_home = v.findViewById(R.id.tv_go_home);
        iv_code = v.findViewById(R.id.iv_code);
        tv_look_order.setOnClickListener(this);
        tv_go_home.setOnClickListener(this);
        mAdapter.addHeaderView(v);
//        if (success){
//            tv_pay_result.setText("支付成功");
//            Drawable drawableLeft = getResources().getDrawable(
//                    R.mipmap.zfcgn);
//            tv_pay_result.setCompoundDrawablesWithIntrinsicBounds(drawableLeft,
//                    null, null, null);
//        }else {
//            tv_pay_result.setText("支付失败");
//            Drawable drawableLeft = getResources().getDrawable(
//                    R.mipmap.zfsb);
//            tv_pay_result.setCompoundDrawablesWithIntrinsicBounds(drawableLeft,
//                    null, null, null);
//        }


    }


    @Override
    public void loadData() {

        getLoveData();





    }


    //得到猜你喜欢的数据
    private void getLoveData() {
        new Handler().postDelayed(() -> {
            setData(DataUtil.getData(4));
        }, 100);
    }



    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.tv_look_order:
                OrderDetailActivity.launch(mActivity,1);
                finish();
                break;
            case R.id.tv_go_home:
                MainActivity.launch(mActivity,MainActivity.SHOP);
                break;
        }
    }

    @Override
    public void back() {
        finish();
    }
    @Override
    public void onBackPressed() {
        back();
    }

}