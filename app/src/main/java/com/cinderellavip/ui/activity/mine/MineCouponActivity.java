package com.cinderellavip.ui.activity.mine;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import com.cinderellavip.R;
import com.cinderellavip.adapter.viewpager.GoodsDetailPagerAdapter;
import com.cinderellavip.ui.fragment.mine.MineCouponFragment;
import com.cinderellavip.ui.fragment.order.RefundFragment;
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
public class MineCouponActivity extends BaseActivity {


    @BindView(R.id.viewpager)
    ViewPager viewpager;
    @BindView(R.id.tablayout)
    TabLayout tablayout;

    private GoodsDetailPagerAdapter adapter;
    private List<BaseFragment> fragmentList = new ArrayList<>();

    public static void launch(Context from) {
        Intent intent = new Intent(from, MineCouponActivity.class);
        from.startActivity(intent);
    }


    @Override
    public void initView(Bundle savedInstanceState) {

        setBackTitle("我的优惠券");



    }


    @Override
    public void loadData() {
        fragmentList.add(MineCouponFragment.newInstance(MineCouponFragment.UNUSED));
        fragmentList.add(MineCouponFragment.newInstance(MineCouponFragment.USED));
        fragmentList.add(MineCouponFragment.newInstance(MineCouponFragment.EXPIRED));
        List<String> list = new ArrayList<>();
        list.add("未使用");
        list.add("已使用");
        list.add("已过期");
        adapter = new GoodsDetailPagerAdapter(getSupportFragmentManager(), fragmentList,list);
        viewpager.setAdapter(adapter);
        tablayout.setupWithViewPager(viewpager);
        viewpager.setOffscreenPageLimit(3);
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_collect;
    }


}
