package com.cinderellavip.ui.fragment;

import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;

import com.cinderellavip.R;
import com.cinderellavip.bean.net.HomeCategoryItem;
import com.cinderellavip.bean.net.HomeCategoryResult;
import com.cinderellavip.http.ApiManager;
import com.cinderellavip.http.BaseResult;
import com.cinderellavip.http.Response;
import com.cinderellavip.ui.activity.home.HomeCategoryListActivity;
import com.cinderellavip.ui.activity.home.SearchActivity;
import com.cinderellavip.weight.MyTabLayout;
import com.cinderellavip.weight.StatusBarHeightView;
import com.tozzais.baselibrary.http.RxHttp;
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
    @BindView(R.id.status)
    StatusBarHeightView status;

    private List<String>myTitle;
    private List<BaseFragment>myFragment;


    @Override
    public void initView(Bundle savedInstanceState) {
        super.initView(savedInstanceState);
        LinearLayout.LayoutParams linearParams =(LinearLayout.LayoutParams) status.getLayoutParams(); //取控件textView当前的布局参数 linearParams.height = 20;// 控件的高强制设成20
        linearParams.height = getStatusBarByReflex(mActivity);// 控件的宽强制设成30
        status.setLayoutParams(linearParams); //使设置好的布局参数应用到控件
    }

    private  int getStatusBarByReflex(Context context) {
        int statusBarHeight = 0;
        try {
            Class<?> clazz = Class.forName("com.android.internal.R$dimen");
            Object object = clazz.newInstance();
            int height = Integer.parseInt(clazz.getField("status_bar_height")
                    .get(object).toString());
            statusBarHeight = context.getResources().getDimensionPixelSize(height);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return statusBarHeight;
    }

    @Override
    public int setLayout() {
        return R.layout.fragment_shop;
    }

    @Override
    public void loadData() {
        getCategory();

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

    /**
     * 获取一级分类
     */
    private void getCategory(){
        new RxHttp<BaseResult<HomeCategoryResult>>().send(ApiManager.getService().getHomeCategory(),
                new Response<BaseResult<HomeCategoryResult>>(mActivity) {
                    @Override
                    public void onSuccess(BaseResult<HomeCategoryResult> result) {
                        setTabCategory(result.data.list);
                    }
                });

    }

    private void  setTabCategory(List<HomeCategoryItem> category){
        myTitle = new ArrayList<>();
        myFragment = new ArrayList<>();
        myTitle.add("首页");
        myFragment.add(ShopMainGoodsFragment.newInstance(0));
        for (HomeCategoryItem category1:category){
            myTitle.add(category1.name);
            myFragment.add(ShopMainGoodsFragment.newInstance(category1.id));
        }
        tabCategory.setTitle(myTitle);
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
}
