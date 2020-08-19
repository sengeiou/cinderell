package com.cinderellavip.ui.activity.home;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.EditText;
import android.widget.FrameLayout;

import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.cinderellavip.R;
import com.cinderellavip.bean.eventbus.UpdateSearch;
import com.cinderellavip.listener.OnFilterShopClickListener;
import com.cinderellavip.listener.OnSureClickListener;
import com.cinderellavip.ui.fragment.CartFragment;
import com.cinderellavip.ui.fragment.FindFragment;
import com.cinderellavip.ui.fragment.LifeFragment;
import com.cinderellavip.ui.fragment.MineFragment;
import com.cinderellavip.ui.fragment.ShopFragment;
import com.cinderellavip.ui.fragment.home.SearchResultFragment;
import com.cinderellavip.ui.fragment.home.SearchShopFragment;
import com.cinderellavip.util.KeyboardUtils;
import com.cinderellavip.util.Utils;
import com.cinderellavip.weight.FilterView;
import com.tozzais.baselibrary.ui.BaseActivity;
import com.tozzais.baselibrary.util.StatusBarUtil;

import org.greenrobot.eventbus.EventBus;

import butterknife.BindView;
import butterknife.OnClick;


/**
 * Created by Administrator on 2016/9/8.
 */
public class SearchResultActivity extends BaseActivity implements OnSureClickListener, OnFilterShopClickListener {


    @BindView(R.id.et_search)
    EditText etSearch;
    @BindView(R.id.fl_container)
    FrameLayout flContainer;
    @BindView(R.id.filter_view)
    FilterView filter_view;

    public static void launch(Context from, String keyword) {
        if (!Utils.isFastClick()){
            return;
        }
        Intent intent = new Intent(from, SearchResultActivity.class);
        intent.putExtra("keyword", keyword);
        from.startActivity(intent);
    }


    private String keyword;
    @Override
    public int getLayoutId() {
        return R.layout.activity_search_result;
    }

    @Override
    public void initView(Bundle savedInstanceState) {
        keyword = getIntent().getStringExtra("keyword");
        etSearch.setText(keyword);

        if (fragmentManager == null)
            fragmentManager = getSupportFragmentManager();
        if (savedInstanceState ==null){
            selectFragment(GOODS);
        }
    }

    @Override
    protected int getToolbarLayout() {
        return -1;
    }

    @Override
    public void loadData() {

    }


    @OnClick({R.id.iv_back})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.iv_back:
                finish();
                break;

        }
    }

    private SearchResultFragment fragment;
    private SearchShopFragment shopFragment;

    @Override
    public void initListener() {
        super.initListener();
        etSearch.setOnKeyListener((v, keyCode, event) -> {
            //是否是回车键
            if (keyCode == KeyEvent.KEYCODE_ENTER && event.getAction() == KeyEvent.ACTION_DOWN) {
                KeyboardUtils.hideKeyboard(etSearch);
                String keyword = etSearch.getText().toString().trim();
                if (fragment != null )
                    fragment.setKeyword(keyword);
                if (shopFragment != null )
                    shopFragment.setKeyword(keyword);
                EventBus.getDefault().post(new UpdateSearch(keyword));
            }
            return false;
        });
        filter_view.setOnDialogClickListener(this);
        filter_view.setOnFilterShopClickListener(this);
    }


    @Override
    public void onSure() {

        selectFragment(GOODS);
    }

    @Override
    public void onClickShop() {
        selectFragment(SHOP);
    }

    public static final int GOODS = 0;
    public static final int SHOP = 1;
    private static final String TAG_GOODS = "tag_goods";
    private static final String TAG_SHOP = "tag_shop";
    private int mPosition;//当前选中的底部菜单
    private FragmentManager fragmentManager;

    public void selectFragment(int position) {
        switch (position) {
            case GOODS:
                mPosition = GOODS;
                setTabSelection(mPosition);
                break;
            case SHOP:
                mPosition = SHOP;
                setTabSelection(mPosition);
                break;
        }
    }

    private void setTabSelection(int position) {
        FragmentTransaction transaction = fragmentManager.beginTransaction();//开启Fragment事务
        hideFragments(transaction);//隐藏所有页面（）
        switch (position) {
            case GOODS:
                if (fragment == null) {
                    fragment = SearchResultFragment.newInstance(keyword);
                    transaction.add(R.id.fl_container, fragment, TAG_GOODS);
                } else {
                    transaction.show(fragment);
                    fragment.setSortAndArea(filter_view.getSort()+"",filter_view.getSort_type()+"");
                }
                break;

            case SHOP:
                if (shopFragment == null) {
                    shopFragment = SearchShopFragment.newInstance(keyword);
                    transaction.add(R.id.fl_container, shopFragment, TAG_SHOP);
                } else {
                    transaction.show(shopFragment);
                }
                break;
        }
        // 提交
        transaction.commit();
    }

    /**
     * 隐藏所有的页面
     */
    private void hideFragments(FragmentTransaction transaction) {
        if (fragment != null) {
            transaction.hide(fragment);
        }
        if (shopFragment != null) {
            transaction.hide(shopFragment);
        }
    }


    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        if (savedInstanceState != null) {
            fragmentManager = getSupportFragmentManager();
            shopFragment = (SearchShopFragment) fragmentManager.findFragmentByTag(TAG_SHOP);
            fragment = (SearchResultFragment) fragmentManager.findFragmentByTag(TAG_GOODS);
        }
        mPosition = savedInstanceState.getInt("mPosition");
        selectFragment(mPosition);
    }


    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt("mPosition", mPosition);
    }


}
