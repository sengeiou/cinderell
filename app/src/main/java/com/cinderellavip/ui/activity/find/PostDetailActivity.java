package com.cinderellavip.ui.activity.find;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import com.cinderellavip.R;
import com.cinderellavip.ui.fragment.find.PostDetailFragment;
import com.tozzais.baselibrary.ui.BaseActivity;

/**
 * 帖子详情
 */
public class PostDetailActivity extends BaseActivity {




    private String id;
    public static void launch(Activity activity,String id) {
        Intent intent = new Intent(activity, PostDetailActivity.class);
        intent.putExtra("id",id);
        activity.startActivityForResult(intent,11);
    }

    @Override
    public int getLayoutId() {
        return R.layout.layout_content;
    }

    @Override
    public void initView(Bundle savedInstanceState) {
        setBackTitle("冬季护肤秘籍");
        setRightIcon(R.mipmap.icon_post_more);
        id = getIntent().getStringExtra("id");
    }

    @Override
    public void loadData() {

        getSupportFragmentManager()
                .beginTransaction()
                .add(R.id.content_container, PostDetailFragment.newInstance(id))
                .commit();
    }

    @Override
    public void initListener() {
        super.initListener();
        iv_right_icon.setOnClickListener(v -> {
            ReportActivity.launch(mActivity);
        });
    }
}
