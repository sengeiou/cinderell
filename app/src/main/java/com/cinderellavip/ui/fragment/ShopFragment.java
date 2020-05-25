package com.cinderellavip.ui.fragment;

import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.cinderellavip.R;
import com.cinderellavip.bean.eventbus.UpdateShopPage;
import com.cinderellavip.bean.net.HomeCategoryItem;
import com.cinderellavip.bean.net.HomeCategoryResult;
import com.cinderellavip.bean.net.HotList;
import com.cinderellavip.http.ApiManager;
import com.cinderellavip.http.BaseResult;
import com.cinderellavip.http.Response;
import com.cinderellavip.ui.activity.home.HomeCategoryListActivity;
import com.cinderellavip.ui.activity.home.SearchActivity;
import com.cinderellavip.weight.MyTabLayout;
import com.cinderellavip.weight.StatusBarHeightView;
import com.tozzais.baselibrary.http.RxHttp;
import com.tozzais.baselibrary.ui.BaseFragment;
import com.tozzais.baselibrary.util.log.LogUtil;

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
    @BindView(R.id.tv_hint)
    TextView tv_hint;

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
        if (!isLoad){
            showProress();
        }
        getCategory();


    }

    @Override
    public void onResume() {
        LogUtil.e("onResume");
        super.onResume();
        getSearchHint();
    }

    private void getSearchHint(){
        new RxHttp<BaseResult<HotList<String>>>().send(ApiManager.getService().getSearchWords(),
                new Response<BaseResult<HotList<String>>>(mActivity,Response.BOTH) {
                    @Override
                    public void onSuccess(BaseResult<HotList<String>> result) {
                        HotList<String> data = result.data;
                        tv_hint.setText(data.keyword);
                    }
                });
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
                new Response<BaseResult<HomeCategoryResult>>(isLoad,mActivity) {
                    @Override
                    public void onSuccess(BaseResult<HomeCategoryResult> result) {
                        showContent();
                        isLoad = true;
                        setTabCategory(result.data.list);
                    }
                    @Override
                    public void onErrorShow(String s) {
                        showError(s);
                    }
                });

    }

    List<HomeCategoryItem> categoryItemList;
    private void  setTabCategory(List<HomeCategoryItem> category){
        categoryItemList = category;
        myFragment = new ArrayList<>();
        HomeCategoryItem homeCategoryItem = new HomeCategoryItem();
        homeCategoryItem.name = "首页";
        myFragment.add(ShopMainGoodsFragment.newInstance(null));
        for (HomeCategoryItem category1:category){
            myFragment.add(ShopCategoryGoodsFragment.newInstance(category1));
        }
        categoryItemList.add(0,homeCategoryItem);
        tabCategory.setTitle(categoryItemList);
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
                return categoryItemList.get(position).name;
            }
        });
        tabCategory.setupWithViewPager(viewPager);
    }

    @Override
    public void onEvent(Object o) {
        super.onEvent(o);
        if (o instanceof UpdateShopPage){
            //id
            String name = ((UpdateShopPage)o).name;
            if ("-1".equals(name)){
                //回到首页
                viewPager.setCurrentItem(0);
                return;
            }
            for (int i = 0;i<categoryItemList.size();i++){
                HomeCategoryItem item = categoryItemList.get(i);
                if (name.equals(item.id+"")){
                    viewPager.setCurrentItem(i);
                    break;
                }
            }
        }
    }
}
