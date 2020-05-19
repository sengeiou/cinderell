package com.cinderellavip.ui.activity.find;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import com.cinderellavip.R;
import com.cinderellavip.ui.fragment.find.TopicDetailFragment;
import com.tozzais.baselibrary.ui.BaseActivity;

/**
 * 话题详情
 */
public class TopicDetailActivity extends BaseActivity {



    private String id;
    public static void launch(Activity activity,String id) {
        Intent intent = new Intent(activity, TopicDetailActivity.class);
        intent.putExtra("id",id);
        activity.startActivityForResult(intent,11);
    }


    @Override
    public int getLayoutId() {
        return R.layout.layout_content;
    }

    @Override
    public void initView(Bundle savedInstanceState) {
        setLineVisibility();
        id = getIntent().getStringExtra("id");
        setBackTitle("#我的百搭神仙单品#");
        setRightIcon(R.mipmap.icon_post_more);
    }

    @Override
    public void loadData() {
        TopicDetailFragment fragment = TopicDetailFragment.newInstance(id);
        getSupportFragmentManager()
                .beginTransaction()
                .add(R.id.content_container, fragment)
                .commit();
    }

    @Override
    public void initListener() {
        super.initListener();
        iv_right_icon.setOnClickListener(v -> {
//            ReportActivity.launch(mActivity);
        });
    }


}
