package com.cinderellavip.ui.activity.home;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.cinderellavip.R;
import com.cinderellavip.ui.fragment.BrandDetailFragment;
import com.tozzais.baselibrary.ui.BaseActivity;

import butterknife.BindView;
import butterknife.OnClick;


/**
 * Created by Administrator on 2016/9/8.
 */
public class BrandDetailActivity extends BaseActivity{


    @BindView(R.id.tv_title_name)
    TextView tvTitleName;
    @BindView(R.id.iv_search)
    ImageView ivSearch;
    @BindView(R.id.iv_collect)
    ImageView ivCollect;

    public static void launch(Context from) {
        Intent intent = new Intent(from, BrandDetailActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        from.startActivity(intent);
    }


    @Override
    public int getLayoutId() {
        return R.layout.activity_brand_detail;
    }

    @Override
    public void initView(Bundle savedInstanceState) {

    }

    @Override
    protected int getToolbarLayout() {
        return -1;
    }

    @Override
    public void loadData() {

//        fragment = SearchResultFragment.newInstance(type_id);
//
        getSupportFragmentManager().beginTransaction().add(R.id.fl_container, new BrandDetailFragment()).commit();


    }

    @Override
    public void initListener() {
    }



    @OnClick({R.id.iv_back, R.id.iv_search, R.id.iv_collect})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.iv_back:
                finish();
                break;
            case R.id.iv_search:
                SearchActivity.launch(mActivity);
                break;
            case R.id.iv_collect:
                break;
        }
    }
}
