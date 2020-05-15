package com.cinderellavip.ui.activity.mine;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.cinderellavip.R;
import com.cinderellavip.bean.net.mine.MessageItem;
import com.cinderellavip.http.ApiManager;
import com.cinderellavip.http.BaseResult;
import com.cinderellavip.http.ListResult;
import com.cinderellavip.http.Response;
import com.cinderellavip.ui.fragment.mine.MessageCenterFragment;
import com.cinderellavip.ui.fragment.mine.MineAddressFragment;
import com.tozzais.baselibrary.http.RxHttp;
import com.tozzais.baselibrary.ui.BaseActivity;
import com.tozzais.baselibrary.util.toast.ToastCommom;

import java.util.List;

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
        getSupportFragmentManager().beginTransaction().add(R.id.content_container,
                MessageCenterFragment.newInstance()).commit();

    }

    @Override
    public int getLayoutId() {
        return R.layout.layout_content;
    }

//
//    @OnClick({R.id.ll_mine_assets, R.id.ll_order_message, R.id.ll_find_message})
//    public void onClick(View view) {
//        switch (view.getId()) {
//            case R.id.ll_mine_assets:
//                MineAssetsActivity.launch(mActivity,MineAssetsActivity.MINE_ASSET);
//                break;
//            case R.id.ll_order_message:
//                MineAssetsActivity.launch(mActivity,MineAssetsActivity.ORDER);
//                break;
//            case R.id.ll_find_message:
//                MineAssetsActivity.launch(mActivity,MineAssetsActivity.FIND);
//                break;
//        }
//    }
}
