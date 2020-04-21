package com.cinderellavip.ui.activity.mine;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import com.cinderellavip.R;
import com.cinderellavip.ui.fragment.mine.LeaderBoardFragment;
import com.cinderellavip.ui.fragment.order.RefundFragment;
import com.tozzais.baselibrary.ui.BaseActivity;


/**
 * Created by Administrator on 2016/9/8.
 */
public class LeaderBoardActivity extends BaseActivity {



    public static void launch(Context from) {
        Intent intent = new Intent(from, LeaderBoardActivity.class);
        from.startActivity(intent);
    }


    @Override
    public void initView(Bundle savedInstanceState) {
        setLineVisibility();
        setBackTitle("月冠排行");
        setRightText("历史排行榜");
//        setRightIcon(R.mipmap.icon_search_black);



    }


    @Override
    public void loadData() {
        LeaderBoardFragment fragment = new LeaderBoardFragment();
        getSupportFragmentManager().beginTransaction().add(R.id.content_container, fragment).commit();
    }

    @Override
    public int getLayoutId() {
        return R.layout.layout_content;
    }


    @Override
    public void initListener() {
        super.initListener();
        tv_right.setOnClickListener(v -> {
            HistoricalRankingActivity.launch(mActivity);
        });

    }
}
