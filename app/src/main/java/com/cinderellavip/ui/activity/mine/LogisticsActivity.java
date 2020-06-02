package com.cinderellavip.ui.activity.mine;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import com.cinderellavip.R;
import com.cinderellavip.ui.fragment.mine.LogisticsFragment;
import com.cinderellavip.util.Utils;
import com.tozzais.baselibrary.ui.BaseActivity;


public class LogisticsActivity extends BaseActivity {

    public static void launch(Activity activity, String tag){
        if (!Utils.isFastClick()){
            return;
        }
        Intent intent = new Intent(activity, LogisticsActivity.class);
        intent.putExtra("post_no",tag);
        activity.startActivity(intent);
    }


    @Override
    public int getLayoutId() {
        return R.layout.layout_content;
    }

    @Override
    public void initView(Bundle savedInstanceState) {
        setLineVisibility();
        setBackTitle("物流详情");
        String post_no = getIntent().getStringExtra("post_no");
        getSupportFragmentManager().beginTransaction().add(R.id.content_container, LogisticsFragment.newInstance(post_no)).commit();
    }

    @Override
    public void loadData() {

    }


}
