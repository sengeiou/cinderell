package com.cinderellavip.ui.activity.life;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import com.cinderellavip.R;
import com.cinderellavip.adapter.viewpager.GoodsDetailPagerAdapter;
import com.cinderellavip.global.RequestCode;
import com.cinderellavip.ui.fragment.life.SelectServiceTimeFragment;
import com.google.android.material.tabs.TabLayout;
import com.tozzais.baselibrary.ui.BaseActivity;
import com.tozzais.baselibrary.ui.BaseFragment;

import java.util.ArrayList;
import java.util.List;

import androidx.viewpager.widget.ViewPager;
import butterknife.BindView;
import butterknife.OnClick;


/**
 * Created by Administrator on 2016/9/8.
 */
public class SelectServiceTimeActivity extends BaseActivity {


    @BindView(R.id.viewpager)
    ViewPager viewpager;
    @BindView(R.id.tablayout)
    TabLayout tablayout;

    private GoodsDetailPagerAdapter adapter;
    private List<BaseFragment> fragmentList = new ArrayList<>();

    public static void launch(Activity from) {
        Intent intent = new Intent(from, SelectServiceTimeActivity.class);
        from.startActivityForResult(intent, RequestCode.request_service_time);
    }


    @Override
    public void initView(Bundle savedInstanceState) {

        setBackTitle("选择服务时间");


    }


    @Override
    public void loadData() {

        List<String> list = new ArrayList<>();
        list.add("今天\n12月23号");
        list.add("明天\n12月24号");
        list.add("周三\n12月25号");
        list.add("周四\n12月26号");
        list.add("周五\n12月27号");
        list.add("周六\n12月28号");
        list.add("周日\n12月29号");
        for (String s : list) {
            fragmentList.add(SelectServiceTimeFragment.newInstance(s));
        }

        adapter = new GoodsDetailPagerAdapter(getSupportFragmentManager(), fragmentList, list);
        viewpager.setAdapter(adapter);
        tablayout.setupWithViewPager(viewpager);
        viewpager.setCurrentItem(getIntent().getIntExtra("type", 0));

    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_select_service_time;
    }


    @OnClick(R.id.tv_sure)
    public void onClick() {
        Intent intent = new Intent();
        setResult(RESULT_OK,intent);
        finish();
        }
}
