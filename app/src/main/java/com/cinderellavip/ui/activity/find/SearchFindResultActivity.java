package com.cinderellavip.ui.activity.find;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.View;
import android.widget.EditText;

import com.cinderellavip.R;
import com.cinderellavip.bean.eventbus.UpdateSearchPost;
import com.cinderellavip.bean.eventbus.UpdateSearchTopic;
import com.cinderellavip.ui.fragment.find.SearchPostResultFragment;
import com.cinderellavip.ui.fragment.find.SearchTopicResultFragment;
import com.cinderellavip.util.KeyboardUtils;
import com.tozzais.baselibrary.ui.BaseActivity;
import com.tozzais.baselibrary.ui.BaseFragment;

import org.greenrobot.eventbus.EventBus;

import butterknife.BindView;
import butterknife.OnClick;


/**
 * Created by Administrator on 2016/9/8.
 */
public class SearchFindResultActivity extends BaseActivity {


    @BindView(R.id.et_search)
    EditText etSearch;

    public static final int POST = 0;
    public static final int TOPIC = 1;

    public static void launch(Context from, String keyword,int type) {
        Intent intent = new Intent(from, SearchFindResultActivity.class);
        intent.putExtra("keyword", keyword);
        intent.putExtra("type", type);
        from.startActivity(intent);
    }


    private String keyword;
    private int type;
    @Override
    public int getLayoutId() {
        return R.layout.activity_search_result_find;
    }

    @Override
    public void initView(Bundle savedInstanceState) {
        keyword = getIntent().getStringExtra("keyword");
        type = getIntent().getIntExtra("type",0);
        etSearch.setText(keyword);
    }

    @Override
    protected int getToolbarLayout() {
        return -1;
    }

    @Override
    public void loadData() {
        if (type == POST){
            fragment = SearchPostResultFragment.newInstance(keyword);
        }else {
            fragment = SearchTopicResultFragment.newInstance(keyword);
        }
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

    private BaseFragment fragment;

    @Override
    public void initListener() {
        super.initListener();
        etSearch.setOnKeyListener((v, keyCode, event) -> {
            //是否是回车键
            if (keyCode == KeyEvent.KEYCODE_ENTER && event.getAction() == KeyEvent.ACTION_DOWN) {
                KeyboardUtils.hideKeyboard(etSearch);
                String keyword = etSearch.getText().toString().trim();
                if (!TextUtils.isEmpty(keyword)){
                    if (type == POST){
                        EventBus.getDefault().post(new UpdateSearchPost(keyword));
                    }else if (type == TOPIC){
                        EventBus.getDefault().post(new UpdateSearchTopic(keyword));
                    }
                    if (fragment instanceof  SearchPostResultFragment){
                        ((SearchPostResultFragment)fragment).setKeyword(keyword);
                    }else if (fragment instanceof  SearchTopicResultFragment){
                        ((SearchTopicResultFragment)fragment).setKeyword(keyword);
                    }

                }

            }
            return false;
        });
    }

}
