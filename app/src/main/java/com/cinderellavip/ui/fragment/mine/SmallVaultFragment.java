package com.cinderellavip.ui.fragment.mine;

import android.os.Bundle;

import com.cinderellavip.R;
import com.cinderellavip.adapter.viewpager.GoodsDetailPagerAdapter;
import com.cinderellavip.ui.activity.mine.LeaderBoardActivity;
import com.google.android.material.appbar.AppBarLayout;
import com.google.android.material.tabs.TabLayout;
import com.tozzais.baselibrary.ui.BaseFragment;

import java.util.ArrayList;
import java.util.List;

import androidx.viewpager.widget.ViewPager;
import butterknife.BindView;
import butterknife.OnClick;


/**
 * 小金推荐
 */
public class SmallVaultFragment extends BaseFragment {


    @BindView(R.id.viewpager)
    ViewPager viewpager;
    @BindView(R.id.tablayout)
    TabLayout tablayout;

    @BindView(R.id.appbar)
    AppBarLayout appbar;

    private GoodsDetailPagerAdapter adapter;
    private List<BaseFragment> fragmentList = new ArrayList<>();

    @Override
    public int setLayout() {
        return R.layout.fragment_small_vault;
    }

    @Override
    public void loadData() {

        fragmentList.add(new SmallVaultConsumeIntegralFragment());
        fragmentList.add(new SmallVaultRecommendIntegralFragment());
        fragmentList.add(new SmallVaultMoonCrownFragment());
        fragmentList.add(new SmallVaultInterestIntegralFragment());
        List<String> list = new ArrayList<>();
        list.add("消费积分");
        list.add("推荐积分");
        list.add("月冠积分");
        list.add("利息积分");
        adapter = new GoodsDetailPagerAdapter(getChildFragmentManager(), fragmentList,list);
        viewpager.setAdapter(adapter);
        tablayout.setupWithViewPager(viewpager);

        viewpager.setOffscreenPageLimit(4);

    }

    @Override
    public void initListener() {
        super.initListener();

    }


    @Override
    public void initView(Bundle savedInstanceState) {
        super.initView(savedInstanceState);

    }


    @OnClick(R.id.tv_invite)
    public void onClick() {
        LeaderBoardActivity.launch(mActivity);
    }
}
