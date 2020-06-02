package com.cinderellavip.ui.activity.home;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.cinderellavip.R;
import com.cinderellavip.listener.OnSureClickListener;
import com.cinderellavip.ui.fragment.home.GoodsListFragment;
import com.cinderellavip.util.Utils;
import com.cinderellavip.weight.FilterView;
import com.tozzais.baselibrary.ui.BaseActivity;

import butterknife.BindView;
import butterknife.OnClick;


/**
 * Created by Administrator on 2016/9/8.
 */
public class GoodsListActivity extends BaseActivity implements OnSureClickListener {


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
    private int third_category_id;
    public static void launch(Context from, String name,  int third_category_id) {
        if (!Utils.isFastClick()){
            return;
        }
        Intent intent = new Intent(from, GoodsListActivity.class);
        intent.putExtra("name", name);
        intent.putExtra("third_category_id",third_category_id);
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
        third_category_id = getIntent().getIntExtra("third_category_id",0);
        tv_title_name.setText(name);

    }

    @Override
    protected int getToolbarLayout() {
        return -1;
    }

    @Override
    public void loadData() {

        fragment = GoodsListFragment.newInstance(third_category_id);

        getSupportFragmentManager().beginTransaction().add(R.id.fl_container, fragment).commit();


    }

    @Override
    public void initListener() {
        filter_view.setOnDialogClickListener(this);
    }


    @OnClick({R.id.iv_back,
           R.id.iv_search})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.iv_back:
                finish();
                break;
            case R.id.iv_search:
                SearchActivity.launch(mActivity);
                break;
        }
    }


    private GoodsListFragment fragment;

    @Override
    public void onSure() {
        fragment.setSortAndArea(filter_view.getSort()+"",filter_view.getSort_type()+"");

    }
}
