package com.cinderellavip.ui.activity.find;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.widget.EditText;

import com.cinderellavip.R;
import com.cinderellavip.adapter.viewpager.GoodsDetailPagerAdapter;
import com.cinderellavip.ui.fragment.find.SearchPostFragment;
import com.cinderellavip.ui.fragment.find.SearchTopicFragment;
import com.cinderellavip.util.KeyboardUtils;
import com.google.android.material.tabs.TabLayout;
import com.tozzais.baselibrary.ui.BaseActivity;
import com.tozzais.baselibrary.ui.BaseFragment;

import java.util.ArrayList;
import java.util.List;

import androidx.viewpager.widget.ViewPager;
import butterknife.BindView;
import butterknife.OnClick;


/**
 * Created by Administrator on 2016/9/8.
 */
public class SearchFindActivity extends BaseActivity {


    @BindView(R.id.tablayout)
    TabLayout tablayout;
    @BindView(R.id.view_pager)
    ViewPager viewPager;
    @BindView(R.id.et_search)
    EditText etSearch;

    private List<BaseFragment> fragmentList;
    private List<String> list_Title;


    public static void launch(Context from) {
        Intent intent = new Intent(from, SearchFindActivity.class);
        from.startActivity(intent);
    }


    @Override
    public int getLayoutId() {
        return R.layout.activity_search_find;
    }

    @Override
    public void initView(Bundle savedInstanceState) {

    }

    @Override
    protected int getToolbarLayout() {
        return -1;
    }

    @Override
    public void loadData() {
        fragmentList = new ArrayList<>();
        list_Title = new ArrayList<>();
        fragmentList.add(new SearchPostFragment());
        fragmentList.add(new SearchTopicFragment());
        list_Title.add("帖子");
        list_Title.add("话题");

        GoodsDetailPagerAdapter adapter = new
                GoodsDetailPagerAdapter(getSupportFragmentManager(), fragmentList, list_Title);
        viewPager.setAdapter(adapter);
        tablayout.setupWithViewPager(viewPager);
    }


    @Override
    public void initListener() {
        super.initListener();
        etSearch.setOnKeyListener((v, keyCode, event) -> {
            //是否是回车键
            if (keyCode == KeyEvent.KEYCODE_ENTER && event.getAction() == KeyEvent.ACTION_DOWN) {
                String trim = etSearch.getText().toString().trim();
                if (TextUtils.isEmpty(trim)) {
                    KeyboardUtils.hideKeyboard(etSearch);
                    tsg("请输入关键字");
                } else {
                    SearchFindResultActivity.launch(mActivity, trim);
                }
            }
            return false;
        });
    }

    @OnClick(R.id.tv_cancel)
    public void onClick() {
        finish();
    }
}
