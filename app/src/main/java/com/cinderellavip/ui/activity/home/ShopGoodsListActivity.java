package com.cinderellavip.ui.activity.home;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.cinderellavip.R;
import com.cinderellavip.listener.OnSureClickListener;
import com.cinderellavip.ui.fragment.ShopDetailFragment;
import com.cinderellavip.ui.fragment.home.GoodsListFragment;
import com.cinderellavip.weight.FilterView;
import com.tozzais.baselibrary.ui.BaseActivity;

import butterknife.BindView;
import butterknife.OnClick;


/**
 * Created by Administrator on 2016/9/8.
 */
public class ShopGoodsListActivity extends BaseActivity implements OnSureClickListener {


    @BindView(R.id.filter_view)
    FilterView filter_view;

    @BindView(R.id.tv_title_name)
    TextView tv_title_name;
    @BindView(R.id.iv_search)
    ImageView iv_search;


    private String name;
    /**
     *
     * @param from
     * @param name
     * @param type
     * @param type_id 店铺id
     */
    private String category_id;
    private String store_id;
    public static void launch(Context from, String name,  String category_id,  String store_id) {
        Intent intent = new Intent(from, ShopGoodsListActivity.class);
        intent.putExtra("name", name);
        intent.putExtra("category_id",category_id);
        intent.putExtra("store_id",store_id);
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
        category_id = getIntent().getStringExtra("category_id");
        store_id = getIntent().getStringExtra("store_id");
        tv_title_name.setText(name);
        iv_search.setVisibility(View.GONE);

    }

    @Override
    protected int getToolbarLayout() {
        return -1;
    }

    @Override
    public void loadData() {

        fragment = ShopDetailFragment.newInstance(store_id,category_id);

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
        }
    }


    private ShopDetailFragment fragment;

    @Override
    public void onSure() {
        fragment.setSortAndArea(filter_view.getSort()+"",filter_view.getSort_type()+"");

    }
}
