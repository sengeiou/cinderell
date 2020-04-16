package com.cinderellavip.ui.activity.find;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import com.cinderellavip.R;
import com.cinderellavip.ui.activity.home.SearchActivity;
import com.cinderellavip.ui.fragment.find.ReportFragment;
import com.cinderellavip.ui.fragment.home.CardSaleFragment;
import com.tozzais.baselibrary.ui.BaseActivity;


/**
 * Created by Administrator on 2016/9/8.
 * 举报
 */
public class ReportActivity extends BaseActivity {



    public static void launch(Context from) {
        Intent intent = new Intent(from, ReportActivity.class);
        from.startActivity(intent);
    }


    @Override
    public void initView(Bundle savedInstanceState) {

        setBackTitle("举报");


    }


    @Override
    public void loadData() {
        ReportFragment fragment = new ReportFragment();
        getSupportFragmentManager().beginTransaction().add(R.id.content_container, fragment).commit();
    }

    @Override
    public int getLayoutId() {
        return R.layout.layout_content;
    }

}
