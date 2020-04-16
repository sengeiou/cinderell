package com.cinderellavip.ui.activity.find;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.cinderellavip.R;
import com.cinderellavip.ui.fragment.find.SearchGoodsForPublishPostFragment;
import com.cinderellavip.util.KeyboardUtils;
import com.tozzais.baselibrary.ui.BaseActivity;
import com.tozzais.baselibrary.util.DpUtil;

import butterknife.BindView;
import butterknife.OnClick;


/**
 * Created by Administrator on 2016/9/8.
 */
public class SearchGoodsForPublishPostActivity extends BaseActivity {


    @BindView(R.id.et_search)
    EditText etSearch;
    @BindView(R.id.iv_back)
    ImageView ivBack;
    @BindView(R.id.ll_search)
    LinearLayout llSearch;
    @BindView(R.id.tv_cancel)
    TextView tvCancel;

    public static void launch(Activity from) {
        Intent intent = new Intent(from, SearchGoodsForPublishPostActivity.class);
        from.startActivityForResult(intent,1001);
    }


    private String keyword;

    @Override
    public int getLayoutId() {
        return R.layout.activity_search_goods_for_publishpost;
    }

    @Override
    public void initView(Bundle savedInstanceState) {
        ivBack.setVisibility(View.GONE);
        LinearLayout.LayoutParams lp = (LinearLayout.LayoutParams) llSearch.getLayoutParams();
        lp.leftMargin = DpUtil.dip2px(mContext,12);
        llSearch.setLayoutParams(lp);

        keyword = getIntent().getStringExtra("keyword");
        etSearch.setText(keyword);
    }

    @Override
    protected int getToolbarLayout() {
        return -1;
    }

    @Override
    public void loadData() {
        fragment = SearchGoodsForPublishPostFragment.newInstance(keyword);
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

    private SearchGoodsForPublishPostFragment fragment;

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
