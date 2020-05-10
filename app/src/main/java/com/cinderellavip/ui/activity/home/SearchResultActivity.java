package com.cinderellavip.ui.activity.home;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.EditText;
import android.widget.FrameLayout;

import com.cinderellavip.R;
import com.cinderellavip.bean.eventbus.UpdateSearch;
import com.cinderellavip.listener.OnSureClickListener;
import com.cinderellavip.ui.fragment.home.SearchResultFragment;
import com.cinderellavip.util.KeyboardUtils;
import com.cinderellavip.weight.FilterView;
import com.tozzais.baselibrary.ui.BaseActivity;

import org.greenrobot.eventbus.EventBus;

import butterknife.BindView;
import butterknife.OnClick;


/**
 * Created by Administrator on 2016/9/8.
 */
public class SearchResultActivity extends BaseActivity implements OnSureClickListener {


    @BindView(R.id.et_search)
    EditText etSearch;
    @BindView(R.id.fl_container)
    FrameLayout flContainer;
    @BindView(R.id.filter_view)
    FilterView filter_view;

    public static void launch(Context from, String keyword) {
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
    }

    @Override
    protected int getToolbarLayout() {
        return -1;
    }

    @Override
    public void loadData() {
        fragment = SearchResultFragment.newInstance(keyword);
        getSupportFragmentManager().beginTransaction().add(R.id.fl_container, fragment).commit();


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

    @Override
    public void initListener() {
        super.initListener();
        etSearch.setOnKeyListener((v, keyCode, event) -> {
            //是否是回车键
            if (keyCode == KeyEvent.KEYCODE_ENTER && event.getAction() == KeyEvent.ACTION_DOWN) {
                KeyboardUtils.hideKeyboard(etSearch);
                String keyword = etSearch.getText().toString().trim();
                fragment.setKeyword(keyword);
                EventBus.getDefault().post(new UpdateSearch(keyword));
            }
            return false;
        });
        filter_view.setOnDialogClickListener(this);
    }


    @Override
    public void onSure() {
        fragment.setSortAndArea(filter_view.getSort()+"",filter_view.getSort_type()+"");

    }
}
