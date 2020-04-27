package com.cinderellavip.ui.fragment;

import android.view.View;

import com.cinderellavip.R;
import com.cinderellavip.ui.activity.home.HomeCategoryListActivity;
import com.cinderellavip.ui.activity.home.SearchActivity;
import com.cinderellavip.weight.MyTabLayout;
import com.tozzais.baselibrary.ui.BaseFragment;

import java.util.ArrayList;
import java.util.List;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;
import butterknife.BindView;
import butterknife.OnClick;


public class ShopFragment extends BaseFragment {


    @BindView(R.id.tab_category)
    MyTabLayout tabCategory;
    @BindView(R.id.view_pager)
    ViewPager viewPager;

    private List<String>myTitle;
    private List<BaseFragment>myFragment;

    @Override
    public int setLayout() {
        return R.layout.fragment_shop;
    }

    @Override
    public void loadData() {
        myTitle = new ArrayList<>();
        myTitle.add("首页");
        myTitle.add("食品生鲜");
        myTitle.add("居家百货");
        myTitle.add("美妆日化");
        myTitle.add("数码电气");
        myTitle.add("母婴产品");
        myTitle.add("休闲娱乐");
        tabCategory.setTitle(myTitle);


        myFragment = new ArrayList<>();
        myFragment.add(new ShopMainGoodsFragment());
        myFragment.add(new ShopCategoryGoodsFragment());
        myFragment.add(new ShopCategoryGoodsFragment());
        myFragment.add(new ShopCategoryGoodsFragment());
        myFragment.add(new ShopCategoryGoodsFragment());
        myFragment.add(new ShopCategoryGoodsFragment());
        myFragment.add(new ShopCategoryGoodsFragment());

        //预加载
        viewPager.setOffscreenPageLimit(myFragment.size());
        //适配器（容器都需要适配器）
        viewPager.setAdapter(new FragmentPagerAdapter(getChildFragmentManager()) {
            //选中的item
            @Override
            public Fragment getItem(int position) {
                return myFragment.get(position);
            }

            @Override
            public int getCount() {
                return myFragment.size();
            }

            @Override
            public CharSequence getPageTitle(int position) {
                return myTitle.get(position);
            }
        });

        tabCategory.setupWithViewPager(viewPager);

    }

    @OnClick({R.id.ll_search, R.id.iv_category})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.ll_search:
                SearchActivity.launch(mActivity);
                break;
            case R.id.iv_category:
                HomeCategoryListActivity.launch(mActivity);
                break;
        }
    }
}
