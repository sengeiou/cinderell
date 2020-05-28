package com.cinderellavip.ui.activity.life;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import com.cinderellavip.R;
import com.cinderellavip.adapter.viewpager.GoodsDetailPagerAdapter;
import com.cinderellavip.ui.fragment.mine.OrderFragment;
import com.cinderellavip.ui.fragment.order.RefundFragment;
import com.cinderellavip.ui.fragment.order.SingleOrderFragment;
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
public class SingleServiceOrderListActivity extends BaseActivity {


    public static final int ALL = 0;
    public static final int PAY = 1;
    public static final int SERVICE = 2;
    public static final int COMMENT = 3;
    public static final int FINISH = 4;


    @BindView(R.id.viewpager)
    ViewPager viewpager;
    @BindView(R.id.tablayout)
    TabLayout tablayout;

    private GoodsDetailPagerAdapter adapter;
    private List<BaseFragment> fragmentList = new ArrayList<>();

    public static void launch(Activity activity, int type) {
        Intent intent = new Intent(activity, SingleServiceOrderListActivity.class);
        intent.putExtra("type",type);
        activity.startActivity(intent);
    }
    @Override
    public void initView(Bundle savedInstanceState) {
        setBackTitle("全部订单");
        tablayout.setTabTextColors(getColor(R.color.black_title_color),getColor(R.color.yellow_deep));
        tablayout.setSelectedTabIndicatorColor(getColor(R.color.yellow_deep));
    }
    @Override
    public void loadData() {
        fragmentList.add(SingleOrderFragment.newInstance(ALL));
        fragmentList.add(SingleOrderFragment.newInstance(PAY));
        fragmentList.add(SingleOrderFragment.newInstance(SERVICE));
        fragmentList.add(SingleOrderFragment.newInstance(COMMENT));
        List<String> list = new ArrayList<>();
        list.add("全部");
        list.add("待支付");
        list.add("待服务");
        list.add("待评价");
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
