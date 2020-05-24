package com.cinderellavip.ui.activity.life;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.View;
import android.widget.EditText;

import com.cinderellavip.R;
import com.cinderellavip.bean.eventbus.UpdateSearchLife;
import com.cinderellavip.ui.fragment.life.SearchLifeResultFragment;
import com.cinderellavip.util.KeyboardUtils;
import com.tozzais.baselibrary.ui.BaseActivity;

import org.greenrobot.eventbus.EventBus;

import butterknife.BindView;
import butterknife.OnClick;


/**
 * Created by Administrator on 2016/9/8.
 */
public class SearchLifeResultActivity extends BaseActivity {


    @BindView(R.id.et_search)
    EditText etSearch;


    public static void launch(Context from, String keyword) {
        Intent intent = new Intent(from, SearchLifeResultActivity.class);
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
        fragment = SearchLifeResultFragment.newInstance(keyword);
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

    private SearchLifeResultFragment fragment;

    @Override
    public void initListener() {
        super.initListener();
        etSearch.setOnKeyListener((v, keyCode, event) -> {
            //是否是回车键
            if (keyCode == KeyEvent.KEYCODE_ENTER && event.getAction() == KeyEvent.ACTION_DOWN) {
                KeyboardUtils.hideKeyboard(etSearch);
                String keyword = etSearch.getText().toString().trim();
                if (!TextUtils.isEmpty(keyword)){
//                    if (type == POST){
                        EventBus.getDefault().post(new UpdateSearchLife(keyword));
//                    }else if (type == TOPIC){
//                        EventBus.getDefault().post(new UpdateSearchTopic(keyword));
//                    }
                    fragment.setKeyword(keyword);


                }

            }
            return false;
        });
    }

}
