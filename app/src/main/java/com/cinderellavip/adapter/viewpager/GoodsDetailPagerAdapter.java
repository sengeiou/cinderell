package com.cinderellavip.adapter.viewpager;


import android.view.ViewGroup;

import com.tozzais.baselibrary.ui.BaseFragment;
import com.tozzais.baselibrary.util.log.LogUtil;

import java.util.List;

import androidx.annotation.NonNull;
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


//    @NonNull
//    @Override
//    public Object instantiateItem(@NonNull ViewGroup container, int position) {
//        return list_Title.get(position).hashCode();
//    }


    @Override
    public long getItemId(int position) {
        return list_Title.get(position).hashCode();
    }



    @Override
    public Fragment getItem(int position) {
        LogUtil.e("标题=="+ list_Title.get(position));
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

    public void setFragmentList(List<BaseFragment> fragmentList,List<String> list_Title) {
        this.fragmentList = fragmentList;
        this.list_Title = list_Title;
        notifyDataSetChanged();
    }


    public void setList_Title(List<String> list_Title) {
        this.list_Title = list_Title;
    }
}