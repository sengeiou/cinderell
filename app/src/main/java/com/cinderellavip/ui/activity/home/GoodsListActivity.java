package com.cinderellavip.ui.activity.home;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;


import com.cinderellavip.R;
import com.cinderellavip.listener.OnFilterListener;
import com.cinderellavip.ui.fragment.home.SearchResultFragment;
import com.cinderellavip.weight.FilterView;
import com.tozzais.baselibrary.ui.BaseActivity;

import butterknife.BindView;
import butterknife.OnClick;


/**
 * Created by Administrator on 2016/9/8.
 */
public class GoodsListActivity extends BaseActivity implements OnFilterListener {


    @BindView(R.id.filter_view)
    FilterView filter_view;

    @BindView(R.id.tv_title_name)
    TextView tv_title_name;


    private String name;
    /**
     *
     * @param from
     * @param name
     * @param type
     * @param type_id 一级分类ID
     */
    private int type_id;
    private int parent_id;
    public static void launch(Context from, String name, int parent_id, int type_id) {
        Intent intent = new Intent(from, GoodsListActivity.class);
        intent.putExtra("name", name);
        intent.putExtra("parent_id",parent_id);
        intent.putExtra("type_id",type_id);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK );
        from.startActivity(intent);
    }




    @Override
    public int getLayoutId() {
        return R.layout.activity_goods_list;
    }

    @Override
    public void initView(Bundle savedInstanceState) {
        name = getIntent().getStringExtra("name");
        parent_id = getIntent().getIntExtra("parent_id",0);
        type_id = getIntent().getIntExtra("type_id",0);
        tv_title_name.setText(name);
        if (parent_id == 0){
            //一级
            filter_view.setFilterCondition(type_id+"","");
        }else {
            //二级
        }
    }

    @Override
    protected int getToolbarLayout() {
        return -1;
    }

    @Override
    public void loadData() {

        fragment = SearchResultFragment.newInstance(type_id);

        getSupportFragmentManager().beginTransaction().add(R.id.fl_container, fragment).commit();


    }

    @Override
    public void initListener() {
        filter_view.setOnFilterListener(this);
    }


    @OnClick({R.id.iv_back,
           R.id.iv_search})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.iv_back:
                finish();
                break;
            case R.id.iv_search:
//                SearchActivity.launch(mActivity);
                break;
        }
    }


    private String sort = "0";
    private String area = "0";
    private boolean isGrid = false;
    private SearchResultFragment fragment;

    @Override
    public void onComplex() {
        sort = "0";
        fragment.setSortAndArea(sort,area);
    }

    @Override
    public void onPrice(boolean isDown) {
        if (isDown){
            sort = "4";
        }else {
            sort = "3";
        }
        fragment.setSortAndArea(sort,area);
    }

    @Override
    public void onCategray(boolean isDown) {

    }


    @Override
    public void onSaleVolume(boolean isDown) {
        if (isDown){
            sort = "2";
        }else {
            sort = "1";
        }
        fragment.setSortAndArea(sort,area);
    }



}
