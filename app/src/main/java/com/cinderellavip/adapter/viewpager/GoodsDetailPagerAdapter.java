package com.cinderellavip.adapter.viewpager;


import com.tozzais.baselibrary.ui.BaseFragment;

import java.util.List;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

/**
 * Created by 32672 on 2018/12/27.
 */

public class GoodsDetailPagerAdapter extends FragmentPagerAdapter {
    private List<BaseFragment> fragmentList;
    private List<String> list_Title;

    public GoodsDetailPagerAdapter(FragmentManager fm, List<BaseFragment> fragmentList, List<String> list_Title) {
        super(fm);
        this.fragmentList = fragmentList;
        this.list_Title = list_Title;
    }


    @Override
    public Fragment getItem(int position) {
        return fragmentList.get(position);
    }

    @Override
    public int getCount() {
        return list_Title.size();
    }

    /**
     * //此方法用来显示tab上的名字
     *
     * @param position
     * @return
     */
    @Override
    public CharSequence getPageTitle(int position) {
        return list_Title.get(position);
    }

}