package com.cinderellavip.ui.activity.find;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.EditText;
import android.widget.FrameLayout;

import com.cinderellavip.R;
import com.cinderellavip.listener.OnFilterListener;
import com.cinderellavip.ui.fragment.find.FindAttentionFragment;
import com.cinderellavip.ui.fragment.home.SearchResultFragment;
import com.cinderellavip.util.KeyboardUtils;
import com.cinderellavip.weight.FilterView;
import com.tozzais.baselibrary.ui.BaseActivity;

import butterknife.BindView;
import butterknife.OnClick;


/**
 * Created by Administrator on 2016/9/8.
 */
public class SearchFindResultActivity extends BaseActivity {


    @BindView(R.id.et_search)
    EditText etSearch;

    public static void launch(Context from, String keyword) {
        Intent intent = new Intent(from, SearchFindResultActivity.class);
        intent.putExtra("keyword", keyword);
        from.startActivity(intent);
    }


    private String keyword;
    @Override
    public int getLayoutId() {
        return R.layout.activity_search_result_find;
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
        fragment = FindAttentionFragment.newInstance();
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

    private FindAttentionFragment fragment;

    @Override
    public void initListener() {
        super.initListener();
        etSearch.setOnKeyListener((v, keyCode, event) -> {
            //是否是回车键
            if (keyCode == KeyEvent.KEYCODE_ENTER && event.getAction() == KeyEvent.ACTION_DOWN) {
                KeyboardUtils.hideKeyboard(etSearch);
                String keyword = etSearch.getText().toString().trim();
//                fragment.setKeyword(keyword);
            }
            return false;
        });
    }

}
