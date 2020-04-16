package com.cinderellavip.ui.fragment;

import android.os.Bundle;

import com.cinderellavip.R;
import com.cinderellavip.adapter.viewpager.GoodsDetailPagerAdapter;
import com.cinderellavip.ui.activity.find.SearchFindActivity;
import com.cinderellavip.ui.fragment.find.FindAttentionFragment;
import com.cinderellavip.ui.fragment.find.FindFindFragment;
import com.cinderellavip.ui.fragment.goods.CommentFragment;
import com.cinderellavip.ui.fragment.goods.GoodsDetailFragment;
import com.cinderellavip.ui.fragment.goods.GraphicFragment;
import com.google.android.material.tabs.TabLayout;
import com.tozzais.baselibrary.ui.BaseFragment;

import java.util.ArrayList;
import java.util.List;

import androidx.viewpager.widget.ViewPager;
import butterknife.BindView;
import butterknife.OnClick;


public class FindFragment extends BaseFragment {


    @BindView(R.id.tablayout)
    TabLayout tablayout;
    @BindView(R.id.view_pager)
    ViewPager viewPager;

    private List<BaseFragment> fragmentList;
    private List<String> list_Title;

    @Override
    public int setLayout() {
        return R.layout.fragment_find;
    }

    @Override
    public void loadData() {
        fragmentList = new ArrayList<>();
        list_Title = new ArrayList<>();
        fragmentList.add(FindFindFragment.newInstance());
        fragmentList.add(FindAttentionFragment.newInstance());
        list_Title.add("发现");
        list_Title.add("关注");

        GoodsDetailPagerAdapter adapter = new
                GoodsDetailPagerAdapter(getChildFragmentManager(), fragmentList, list_Title);
        viewPager.setAdapter(adapter);
        tablayout.setupWithViewPager(viewPager);
    }


    @OnClick(R.id.iv_search)
    public void onClick() {
        SearchFindActivity.launch(mActivity);
    }
}
