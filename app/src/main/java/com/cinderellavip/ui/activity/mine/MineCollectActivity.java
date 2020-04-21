package com.cinderellavip.ui.activity.mine;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import com.cinderellavip.R;
import com.cinderellavip.adapter.viewpager.GoodsDetailPagerAdapter;
import com.cinderellavip.ui.fragment.mine.CollectBrandFragment;
import com.cinderellavip.ui.fragment.mine.CollectFindFragment;
import com.cinderellavip.ui.fragment.mine.CollectShopFragment;
import com.google.android.material.tabs.TabLayout;
import com.tozzais.baselibrary.ui.BaseActivity;
import com.tozzais.baselibrary.ui.BaseFragment;

import java.util.ArrayList;
import java.util.List;

import androidx.appcompat.widget.Toolbar;
import androidx.viewpager.widget.ViewPager;
import butterknife.BindView;


/**
 * Created by Administrator on 2016/9/8.
 */
public class MineCollectActivity extends BaseActivity {


    @BindView(R.id.tablayout)
    TabLayout tablayout;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.viewpager)
    ViewPager viewpager;

    public static void launch(Context from) {
        Intent intent = new Intent(from, MineCollectActivity.class);
        from.startActivity(intent);
    }


    @Override
    public void initView(Bundle savedInstanceState) {
        toolbar.setNavigationIcon(R.mipmap.back);
        toolbar.setNavigationOnClickListener(view -> finish());
    }
    @Override
    protected int getToolbarLayout() {
        return -1;
    }


    private GoodsDetailPagerAdapter adapter;
    private List<BaseFragment> fragmentList = new ArrayList<>();
    @Override
    public void loadData() {
        fragmentList.add(new CollectShopFragment());
        fragmentList.add(new CollectBrandFragment());
        fragmentList.add(new CollectFindFragment());
        List<String> list = new ArrayList<>();
        list.add("店铺");
        list.add("品牌");
        list.add("发现");
        adapter = new GoodsDetailPagerAdapter(getSupportFragmentManager(), fragmentList,list);
        viewpager.setAdapter(adapter);
        tablayout.setupWithViewPager(viewpager);

        viewpager.setCurrentItem(getIntent().getIntExtra("type",0));
        viewpager.setOffscreenPageLimit(4);

    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_mine_collect;
    }


}
