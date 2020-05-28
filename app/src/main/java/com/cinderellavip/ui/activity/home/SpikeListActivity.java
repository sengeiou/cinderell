package com.cinderellavip.ui.activity.home;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import com.cinderellavip.R;
import com.cinderellavip.adapter.viewpager.GoodsDetailPagerAdapter;
import com.cinderellavip.bean.spike.SpikeTime;
import com.cinderellavip.http.ApiManager;
import com.cinderellavip.http.BaseResult;
import com.cinderellavip.http.ListResult;
import com.cinderellavip.http.Response;
import com.cinderellavip.ui.fragment.home.SpikeFragment;
import com.cinderellavip.weight.tab.SpikeTabLayout;
import com.tozzais.baselibrary.http.RxHttp;
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
        if (!isLoad)showProress();
        new RxHttp<BaseResult<ListResult<SpikeTime>>>().send(ApiManager.getService().spikeTime(),
                new Response<BaseResult<ListResult<SpikeTime>>>(isLoad,mActivity) {
                    @Override
                    public void onSuccess(BaseResult<ListResult<SpikeTime>> result) {
                        showContent();
                        List<SpikeTime> spikeTimes = result.data.list;
                        List<String> list = new ArrayList<>();
                        int position = 0;
                        for (int i = 0 ; i<spikeTimes.size();i++){
                            SpikeTime spikeTime = spikeTimes.get(i);
                            fragmentList.add(SpikeFragment.newInstance(spikeTime.begin_time));
                            list.add("");
                            if (spikeTime.select){
                                position = i;
                            }
                        }
                        tablayout.setTitle(spikeTimes);
                        adapter = new GoodsDetailPagerAdapter(getSupportFragmentManager(), fragmentList,list);
                        viewpager.setAdapter(adapter);
                        tablayout.setupWithViewPager(viewpager);
                        viewpager.setCurrentItem(getIntent().getIntExtra("type",0));
                        viewpager.setOffscreenPageLimit(4);
                        viewpager.setCurrentItem(position);


                    }

                    @Override
                    public void onErrorShow(String s) {
                        showError(s);
                    }
                });



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
