package com.cinderellavip.ui.activity.mine;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.cinderellavip.R;
import com.tozzais.baselibrary.ui.BaseActivity;

import butterknife.OnClick;


/**
 * Created by Administrator on 2016/9/8.
 */
public class MessageActivity extends BaseActivity {


    public static void launch(Context from) {
        Intent intent = new Intent(from, MessageActivity.class);
        from.startActivity(intent);
    }


    @Override
    public void initView(Bundle savedInstanceState) {

        setBackTitle("消息中心");


    }


    @Override
    public void loadData() {

    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_message;
    }


    @OnClick({R.id.ll_mine_assets, R.id.ll_order_message, R.id.ll_find_message})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.ll_mine_assets:
                MineAssetsActivity.launch(mActivity,MineAssetsActivity.MINE_ASSET);
                break;
            case R.id.ll_order_message:
                MineAssetsActivity.launch(mActivity,MineAssetsActivity.ORDER);
                break;
            case R.id.ll_find_message:
                MineAssetsActivity.launch(mActivity,MineAssetsActivity.FIND);
                break;
        }
    }
}
