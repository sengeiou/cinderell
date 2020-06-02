package com.cinderellavip.ui.activity.mine;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import com.cinderellavip.R;
import com.cinderellavip.ui.fragment.mine.MessageFindFragment;
import com.cinderellavip.ui.fragment.mine.MessageMineAssetFragment;
import com.cinderellavip.ui.fragment.mine.MessageOrderFragment;
import com.cinderellavip.ui.fragment.order.RefundFragment;
import com.cinderellavip.util.Utils;
import com.tozzais.baselibrary.ui.BaseActivity;
import com.tozzais.baselibrary.ui.BaseFragment;


/**
 * Created by Administrator on 2016/9/8.
 */
public class MineAssetsActivity extends BaseActivity {


    public static final int MINE_ASSET = 1;
    public static final int ORDER = 2;
    public static final int FIND = 3;
    private int type;

    public static void launch(Context from,int type) {
        if (!Utils.isFastClick()){
            return;
        }
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
        BaseFragment fragment =
        MessageMineAssetFragment.newInstance(type);
        getSupportFragmentManager().beginTransaction().add(R.id.content_container, fragment).commit();
    }

    @Override
    public int getLayoutId() {
        return R.layout.layout_content;
    }


}
