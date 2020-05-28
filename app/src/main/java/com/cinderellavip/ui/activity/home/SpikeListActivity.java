package com.cinderellavip.ui.activity.home;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import com.cinderellavip.R;
import com.cinderellavip.adapter.viewpager.GoodsDetailPagerAdapter;
import com.cinderellavip.bean.local.CommnetTabItem;
import com.cinderellavip.ui.fragment.home.SpikeFragment;
import com.cinderellavip.ui.fragment.mine.OrderFragment;
import com.cinderellavip.weight.tab.SpikeTabLayout;
import com.google.android.material.tabs.TabLayout;
import com.tozzais.baselibrary.ui.BaseActivity;
import com.tozzais.baselibrary.ui.BaseFragment;
import com.tozzais.baselibrary.util.StatusBarUtil;

import java.util.ArrayList;
import java.util.List;

import androidx.viewpager.widget.ViewPager;
import butterknife.BindView;

public class SpikeListActivity extends BaseActivity {

    @BindView(R.id.viewpager)
    ViewPager viewpager;
    @BindView(R.id.tablayout)
    SpikeTabLayout tablayout;

    private GoodsDetailPagerAdapter adapter;
    private List<BaseFragment> fragmentList = new ArrayList<>();

    public static void launch(Activity activity) {
        Intent intent = new Intent(activity, SpikeListActivity.class);
        activity.startActivity(intent);
    }




    @Override
    public int getLayoutId() {
        return R.layout.activity_spike;
    }

    @Override
    public void initView(Bundle savedInstanceState) {
        setBackTitle("今日秒杀");
        mTitle.setTextColor(getResources().getColor(R.color.white));
        getToolbar().setBackgroundColor(getResources().getColor(R.color.baseColor));
        getToolbar().setNavigationIcon(R.mipmap.back_white);

    }

    @Override
    public void loadData() {

        fragmentList.add(SpikeFragment.newInstance(OrderFragment.ALL));
        fragmentList.add(SpikeFragment.newInstance(OrderFragment.UNPAY));
        fragmentList.add(SpikeFragment.newInstance(OrderFragment.UNSEND));
        fragmentList.add(SpikeFragment.newInstance(OrderFragment.UNRECEIVE));
        fragmentList.add(SpikeFragment.newInstance(OrderFragment.FINISH));
        List<CommnetTabItem> tabList = new ArrayList<>();
        tabList.add(new CommnetTabItem("09:00", "抢购中"));
        tabList.add(new CommnetTabItem("12:00", "抢购中"));
        tabList.add(new CommnetTabItem("15:00", "抢购中"));
        tabList.add(new CommnetTabItem("18:00", "未开始"));
        tabList.add(new CommnetTabItem("21:00", "未开始"));
        tablayout.setTitle(tabList);

        List<String> list = new ArrayList<>();
        list.add("");
        list.add("");
        list.add("");
        list.add("");
        list.add("");
        adapter = new GoodsDetailPagerAdapter(getSupportFragmentManager(), fragmentList,list);
        viewpager.setAdapter(adapter);
        tablayout.setupWithViewPager(viewpager);

        viewpager.setCurrentItem(getIntent().getIntExtra("type",0));
        viewpager.setOffscreenPageLimit(4);

    }

    @Override
    public void initListener() {

    }

    @Override
    protected void setStatusBar() {
        int mColor = getResources().getColor(R.color.baseColor);
        StatusBarUtil.setColor(this, mColor, 0);
        StatusBarUtil.setDarkMode(this);
    }



}
