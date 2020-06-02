package com.cinderellavip.ui.activity.life;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import com.cinderellavip.R;
import com.cinderellavip.adapter.viewpager.GoodsDetailPagerAdapter;
import com.cinderellavip.ui.fragment.order.LongServiceOrderFragment;
import com.cinderellavip.util.Utils;
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
public class LongServiceOrderListActivity extends BaseActivity {





    @BindView(R.id.viewpager)
    ViewPager viewpager;
    @BindView(R.id.tablayout)
    TabLayout tablayout;

    private GoodsDetailPagerAdapter adapter;
    private List<BaseFragment> fragmentList = new ArrayList<>();

    public static void launch(Activity activity, int type) {
        if (!Utils.isFastClick()){
            return;
        }
        Intent intent = new Intent(activity, LongServiceOrderListActivity.class);
        intent.putExtra("type",type);
        activity.startActivity(intent);
    }
    @Override
    public void initView(Bundle savedInstanceState) {
        tablayout.setSelectedTabIndicatorColor(getColor(R.color.yellow_deep));
        tablayout.setTabTextColors(getColor(R.color.black_title_color),getColor(R.color.yellow_deep));
        setBackTitle("全部订单");
    }
    @Override
    public void loadData() {
        fragmentList.add(LongServiceOrderFragment.newInstance(0));
        fragmentList.add(LongServiceOrderFragment.newInstance(1));
        fragmentList.add(LongServiceOrderFragment.newInstance(2));
        fragmentList.add(LongServiceOrderFragment.newInstance(3));
        fragmentList.add(LongServiceOrderFragment.newInstance(4));
        List<String> list = new ArrayList<>();
        list.add("全部");
        list.add("待确认");
        list.add("待支付");
        list.add("服务中");
        list.add("已完成");
        adapter = new GoodsDetailPagerAdapter(getSupportFragmentManager(), fragmentList,list);
        viewpager.setAdapter(adapter);
        tablayout.setupWithViewPager(viewpager);

        viewpager.setCurrentItem(getIntent().getIntExtra("type",0));
        viewpager.setOffscreenPageLimit(4);
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_collect;
    }


}
