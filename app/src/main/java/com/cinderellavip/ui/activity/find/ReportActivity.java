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


    /**
     *
     * @param from
     * @param id 举报的id
     * @param type 1:帖子、话题， 2:用户
     */
    public static void launch(Context from,String id,String type) {
        Intent intent = new Intent(from, ReportActivity.class);
        intent.putExtra("id",id);
        intent.putExtra("type",type);
        from.startActivity(intent);
    }


    @Override
    public void initView(Bundle savedInstanceState) {

        setBackTitle("举报");


    }


    @Override
    public void loadData() {
        String id = getIntent().getStringExtra("id");
        String type = getIntent().getStringExtra("type");
        ReportFragment fragment = ReportFragment.newInstance(id,type);

        getSupportFragmentManager().beginTransaction().add(R.id.content_container, fragment).commit();
    }

    @Override
    public int getLayoutId() {
        return R.layout.layout_content;
    }

}
