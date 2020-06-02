package com.cinderellavip.ui.activity.home;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.EditText;

import com.cinderellavip.R;
import com.cinderellavip.ui.fragment.home.BrandSearchResultFragment;
import com.cinderellavip.util.KeyboardUtils;
import com.cinderellavip.util.Utils;
import com.tozzais.baselibrary.ui.BaseActivity;

import butterknife.BindView;
import butterknife.OnClick;


/**
 * 品牌和 店铺 搜索
 * Created by Administrator on 2016/9/8.
 */
public class SearchListActivity extends BaseActivity {


    @BindView(R.id.et_search)
    EditText etSearch;

    public static final int SHOP = 0;
    public static final int BRAND = 1;
    private int type;
    private String id;

    public static void launch(Context from, String id,int type) {
        if (!Utils.isFastClick()){
            return;
        }
        Intent intent = new Intent(from, SearchListActivity.class);
        intent.putExtra("id", id);
        intent.putExtra("type", type);
        from.startActivity(intent);
    }

    public static void launch(Context from) {
        if (!Utils.isFastClick()){
            return;
        }
        Intent intent = new Intent(from, SearchListActivity.class);
        from.startActivity(intent);
    }


    @Override
    public int getLayoutId() {
        return R.layout.activity_search_result_find;
    }

    @Override
    public void initView(Bundle savedInstanceState) {
        id = getIntent().getStringExtra("id");
        type = getIntent().getIntExtra("type",0);
        if (type == SHOP){
            etSearch.setHint("搜索该店铺中的商品");
        }else  if (type == BRAND){
            etSearch.setHint("搜索该品牌中的商品");
        }

    }

    @Override
    protected int getToolbarLayout() {
        return -1;
    }

    @Override
    public void loadData() {
        type = getIntent().getIntExtra("type",0);
        id = getIntent().getStringExtra("id");
        fragment = BrandSearchResultFragment.newInstance(id,type);
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

    private BrandSearchResultFragment fragment;

    @Override
    public void initListener() {
        super.initListener();
        etSearch.setOnKeyListener((v, keyCode, event) -> {
            //是否是回车键
            if (keyCode == KeyEvent.KEYCODE_ENTER && event.getAction() == KeyEvent.ACTION_DOWN) {
                KeyboardUtils.hideKeyboard(etSearch);
                String keyword = etSearch.getText().toString().trim();
                fragment.setKeyword(keyword);
            }
            return false;
        });
    }
}
