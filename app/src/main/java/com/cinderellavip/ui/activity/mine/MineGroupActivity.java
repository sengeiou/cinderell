package com.cinderellavip.ui.activity.mine;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import com.cinderellavip.R;
import com.cinderellavip.adapter.viewpager.GoodsDetailPagerAdapter;
import com.cinderellavip.ui.fragment.mine.MineGroupUpFragment;
import com.google.android.material.tabs.TabLayout;
import com.tozzais.baselibrary.ui.BaseActivity;
import com.tozzais.baselibrary.ui.BaseFragment;

import java.util.ArrayList;
import java.util.List;

import androidx.viewpager.widget.ViewPager;
import butterknife.BindView;


/**
 * Created by Administrator on 2016/9/8.
 */
public class MineGroupActivity extends BaseActivity {


    @BindView(R.id.viewpager)
    ViewPager viewpager;
    @BindView(R.id.tablayout)
    TabLayout tablayout;

    private GoodsDetailPagerAdapter adapter;
    private List<BaseFragment> fragmentList = new ArrayList<>();

    public static void launch(Activity activity) {
        Intent intent = new Intent(activity, MineGroupActivity.class);
        activity.startActivity(intent);
    }




    @Override
    public int getLayoutId() {
        return R.layout.activity_collect;
    }

    @Override
    public void initView(Bundle savedInstanceState) {
        setBackTitle("我的拼团");
    }

    @Override
    public void loadData() {

        fragmentList.add(MineGroupUpFragment.newInstance(2));
        fragmentList.add(MineGroupUpFragment.newInstance(3));
        fragmentList.add(MineGroupUpFragment.newInstance(4));
        List<String> list = new ArrayList<>();
        list.add("待成团");
        list.add("已成团");
        list.add("未成团");
        adapter = new GoodsDetailPagerAdapter(getSupportFragmentManager(), fragmentList,list);
        viewpager.setAdapter(adapter);
        tablayout.setupWithViewPager(viewpager);

        viewpager.setCurrentItem(getIntent().getIntExtra("type",0));
        viewpager.setOffscreenPageLimit(4);

    }

    @Override
    public void initListener() {

    }

}
