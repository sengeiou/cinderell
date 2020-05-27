package com.cinderellavip.ui.activity.mine;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.cinderellavip.R;
import com.cinderellavip.ui.fragment.mine.WithDrawHistoryFragment;
import com.cinderellavip.ui.fragment.order.RefundFragment;
import com.tozzais.baselibrary.ui.BaseActivity;


/**
 * Created by Administrator on 2016/9/8.
 */
public class WithDrawHistoryActivity extends BaseActivity {

    public static final int BALANCE = 0;
    public static final int SCORE = 1;
    private int type;

    public static void launch(Context from) {
        Intent intent = new Intent(from, WithDrawHistoryActivity.class);
        from.startActivity(intent);
    }

    public static void launch(Context from,int type) {
        Intent intent = new Intent(from, WithDrawHistoryActivity.class);
        intent.putExtra("type",type);
        from.startActivity(intent);
    }

    @Override
    public void initView(Bundle savedInstanceState) {
        type = getIntent().getIntExtra("type",BALANCE);
        if(type == BALANCE){
            setBackTitle("提现记录");
        }if(type == SCORE){
            setBackTitle("积分提现记录");
        }



    }


    @Override
    public void loadData() {
        WithDrawHistoryFragment fragment =  WithDrawHistoryFragment.newInstance(type);
        getSupportFragmentManager().beginTransaction().add(R.id.content_container, fragment).commit();
    }

    @Override
    public int getLayoutId() {
        return R.layout.layout_content;
    }


}
