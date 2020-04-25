package com.cinderellavip.ui.activity.life;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.cinderellavip.R;
import com.cinderellavip.adapter.viewpager.ViewPagerFragmentAdapter;
import com.cinderellavip.ui.fragment.life.DirectAppointListFragment;
import com.cinderellavip.ui.fragment.life.DirectAppointMapFragment;
import com.cinderellavip.weight.IndexViewPager;
import com.google.android.material.tabs.TabLayout;
import com.tozzais.baselibrary.ui.BaseActivity;
import com.tozzais.baselibrary.ui.BaseFragment;

import java.util.ArrayList;
import java.util.List;

import androidx.appcompat.widget.Toolbar;
import butterknife.BindView;
import butterknife.OnClick;


/**
 * Created by Administrator on 2016/9/8.
 */
public class DirectAppointmentActivity extends BaseActivity {


    @BindView(R.id.tv_left)
    TextView tvLeft;
    @BindView(R.id.tv_right)
    TextView tvRight;
    @BindView(R.id.toolbar)
    Toolbar toolbar;

        @BindView(R.id.viewpager)
        IndexViewPager viewpager;
    @BindView(R.id.tablayout)
    TabLayout tablayout;
//    @BindView(R.id.map)
//    MapView mMapView;
//    初始化地图控制器对象
//    AMap aMap;

    private String[] title = {"家电维修", "企业服务", "家庭保洁", "家教培训", "家政保洁", "便民服务", "美体按摩", "美容美妆",};

    private ViewPagerFragmentAdapter adapter;
    private List<BaseFragment> fragmentList = new ArrayList<>();

    public static void launch(Context from) {
        Intent intent = new Intent(from, DirectAppointmentActivity.class);
        from.startActivity(intent);
    }


    @Override
    public void initView(Bundle savedInstanceState) {
        toolbar.setNavigationIcon(R.mipmap.back);
        toolbar.setNavigationOnClickListener(view -> back());

//        mMapView.onCreate(savedInstanceState);
//        if (aMap == null) {
//            aMap = mMapView.getMap();
//        }
    }


    @Override
    public void loadData() {
        for (String s : title) {
            tablayout.addTab(tablayout.newTab().setText(s));
        }

        fragmentList.add(new DirectAppointMapFragment());
        fragmentList.add(new DirectAppointListFragment());
        adapter = new ViewPagerFragmentAdapter(getSupportFragmentManager(), fragmentList);
        viewpager.setAdapter(adapter);


    }


    @Override
    public int getLayoutId() {
        return -1;
    }

    @Override
    protected int getBaseLayout() {
        return R.layout.activity_directappointment;
    }


    @OnClick({R.id.tv_left, R.id.tv_right})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.tv_left:
                if (!isLeft) {
                    tvLeft.setBackgroundResource(R.drawable.shape_solid_left5);
                    tvLeft.setTextColor(getColor(R.color.white));
                    tvRight.setTextColor(getColor(R.color.yellow_deep));
                    tvRight.setBackgroundColor(getColor(android.R.color.transparent));
                    isLeft = true;
                }
                viewpager.setCurrentItem(0);

                break;
            case R.id.tv_right:
                if (isLeft) {
                    tvRight.setBackgroundResource(R.drawable.shape_solid_right5);
                    tvRight.setTextColor(getColor(R.color.white));
                    tvLeft.setTextColor(getColor(R.color.yellow_deep));
                    tvLeft.setBackgroundColor(getColor(android.R.color.transparent));
                    isLeft = false;
                }
                viewpager.setCurrentItem(1);
                break;
        }
    }

    private boolean isLeft = true;




}
