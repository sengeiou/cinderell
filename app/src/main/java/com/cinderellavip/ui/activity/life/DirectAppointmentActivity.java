package com.cinderellavip.ui.activity.life;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.cinderellavip.R;
import com.cinderellavip.adapter.viewpager.ViewPagerFragmentAdapter;
import com.cinderellavip.bean.net.life.DirectCategory;
import com.cinderellavip.http.ApiManager;
import com.cinderellavip.http.BaseResult;
import com.cinderellavip.http.Response;
import com.cinderellavip.ui.fragment.life.DirectAppointListFragment;
import com.cinderellavip.ui.fragment.life.DirectAppointMapFragment;
import com.cinderellavip.weight.IndexViewPager;
import com.google.android.material.tabs.TabLayout;
import com.tozzais.baselibrary.http.RxHttp;
import com.tozzais.baselibrary.ui.BaseActivity;
import com.tozzais.baselibrary.ui.BaseFragment;
import com.tozzais.baselibrary.weight.ProgressLayout;

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
    @BindView(R.id.progress)
    ProgressLayout progress;

        @BindView(R.id.viewpager)
        IndexViewPager viewpager;
    @BindView(R.id.tablayout)
    TabLayout tablayout;
//    @BindView(R.id.map)
//    MapView mMapView;
//    初始化地图控制器对象
//    AMap aMap;

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


    }


    @Override
    public void loadData() {
        if (!isLoad)progress.showLoading();
        new RxHttp<BaseResult<List<DirectCategory>>>().send(ApiManager.getService().directCate(),
                new Response<BaseResult<List<DirectCategory>>>(isLoad,mActivity) {
                    @Override
                    public void onSuccess(BaseResult<List<DirectCategory>> result) {
                        isLoad = true;
                        progress.showContent();
                        categories = result.data;
                        for (DirectCategory s :categories) {
                            tablayout.addTab(tablayout.newTab().setText(s.name));
                        }
                        directAppointMapFragment =  DirectAppointMapFragment.newInstance(categories.get(0).id);
                        fragmentList.add(directAppointMapFragment);
                        directAppointListFragment = DirectAppointListFragment.newInstance(categories.get(0).id);
                        fragmentList.add(directAppointListFragment);
                        adapter = new ViewPagerFragmentAdapter(getSupportFragmentManager(), fragmentList);
                        viewpager.setAdapter(adapter);
                    }

                    @Override
                    public void onErrorShow(String s) {
                        progress.showError(s,view -> loadData());
                    }
                });

    }
    DirectAppointMapFragment directAppointMapFragment;
    DirectAppointListFragment directAppointListFragment;

    @Override
    public void initListener() {
        super.initListener();
        tablayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                int position = tablayout.getSelectedTabPosition();

                DirectCategory category = categories.get(position);
                if (directAppointListFragment != null)
                    directAppointListFragment.setId(category.id);
                if (directAppointMapFragment != null)
                    directAppointMapFragment.setId(category.id);


            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
    }

    List<DirectCategory> categories;


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
