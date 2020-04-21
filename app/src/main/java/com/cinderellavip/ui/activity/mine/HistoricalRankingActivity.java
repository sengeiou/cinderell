package com.cinderellavip.ui.activity.mine;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import com.cinderellavip.R;
import com.cinderellavip.ui.fragment.mine.SmallVaultHistoryRankFragment;
import com.cinderellavip.ui.fragment.order.RefundFragment;
import com.tozzais.baselibrary.ui.BaseActivity;


/**
 * Created by Administrator on 2016/9/8.
 */
public class HistoricalRankingActivity extends BaseActivity {



    public static void launch(Context from) {
        Intent intent = new Intent(from, HistoricalRankingActivity.class);
        from.startActivity(intent);
    }


    @Override
    public void initView(Bundle savedInstanceState) {
        setLineVisibility();
        setBackTitle("历史排行");
//        setRightIcon(R.mipmap.icon_search_black);



    }


    @Override
    public void loadData() {
        SmallVaultHistoryRankFragment fragment = new SmallVaultHistoryRankFragment();
        getSupportFragmentManager().beginTransaction().add(R.id.content_container, fragment).commit();
    }

    @Override
    public int getLayoutId() {
        return R.layout.layout_content;
    }


}
