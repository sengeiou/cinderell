package com.cinderellavip.ui.activity.mine;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import com.cinderellavip.R;
import com.cinderellavip.ui.fragment.mine.MessageFindFragment;
import com.cinderellavip.ui.fragment.mine.MessageMineAssetFragment;
import com.cinderellavip.ui.fragment.mine.MessageOrderFragment;
import com.cinderellavip.ui.fragment.order.RefundFragment;
import com.tozzais.baselibrary.ui.BaseActivity;
import com.tozzais.baselibrary.ui.BaseFragment;


/**
 * Created by Administrator on 2016/9/8.
 */
public class MineAssetsActivity extends BaseActivity {


    public static final int MINE_ASSET = 0;
    public static final int ORDER = 1;
    public static final int FIND = 2;
    private int type;

    public static void launch(Context from,int type) {
        Intent intent = new Intent(from, MineAssetsActivity.class);
        intent.putExtra("type",type);
        from.startActivity(intent);
    }


    @Override
    public void initView(Bundle savedInstanceState) {
        type = getIntent().getIntExtra("type",0);
        if (type == MINE_ASSET){
            setBackTitle("我的资产");
        }else if (type == ORDER){
            setBackTitle("订单交易");
        }else if (type == FIND){
            setBackTitle("发现");
        }




    }


    @Override
    public void loadData() {
        BaseFragment fragment ;
        if (type == MINE_ASSET){
            fragment = new MessageMineAssetFragment();
            setBackTitle("我的资产");
        }else if (type == ORDER){
            fragment = new MessageOrderFragment();
            setBackTitle("订单交易");
        }else {
            fragment = new MessageFindFragment();
            setBackTitle("发现");
        }
        getSupportFragmentManager().beginTransaction().add(R.id.content_container, fragment).commit();
    }

    @Override
    public int getLayoutId() {
        return R.layout.layout_content;
    }


}
