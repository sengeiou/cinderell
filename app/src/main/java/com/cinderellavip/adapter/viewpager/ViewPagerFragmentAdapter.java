package com.cinderellavip.adapter.viewpager;


import com.tozzais.baselibrary.ui.BaseFragment;

import java.util.List;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

/**
 * Created by 32672 on 2018/12/27.
 */

public class ViewPagerFragmentAdapter extends FragmentPagerAdapter {
    private List<BaseFragment> fragmentList;

    public ViewPagerFragmentAdapter(FragmentManager fm, List<BaseFragment> fragmentList) {
        super(fm);
        this.fragmentList = fragmentList;
    }


    @Override
    public Fragment getItem(int position) {
        return fragmentList.get(position);
    }

    @Override
    public int getCount() {
        return fragmentList.size();
    }


    public void setData(List<BaseFragment> fragmentList){
        this.fragmentList = fragmentList;
        notifyDataSetChanged();
    }



}