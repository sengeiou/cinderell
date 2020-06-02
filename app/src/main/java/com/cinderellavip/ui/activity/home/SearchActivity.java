package com.cinderellavip.ui.activity.home;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.cinderellavip.R;
import com.cinderellavip.bean.eventbus.UpdateSearch;
import com.cinderellavip.bean.net.HotList;
import com.cinderellavip.global.GlobalParam;
import com.cinderellavip.http.ApiManager;
import com.cinderellavip.http.BaseResult;
import com.cinderellavip.http.Response;
import com.cinderellavip.util.KeyboardUtils;
import com.cinderellavip.util.ScreenUtil;
import com.cinderellavip.util.Utils;
import com.cinderellavip.weight.FlowLayout;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.tozzais.baselibrary.http.RxHttp;
import com.tozzais.baselibrary.ui.BaseActivity;
import com.tozzais.baselibrary.util.DpUtil;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;


/**
 * Created by Administrator on 2016/9/8.
 */
public class SearchActivity extends BaseActivity {


    @BindView(R.id.fl_recent)
    FlowLayout flRecent;
    @BindView(R.id.fl_hot)
    FlowLayout flHot;
    @BindView(R.id.et_search)
    EditText etSearch;
    @BindView(R.id.iv_clear)
    ImageView iv_clear;
    @BindView(R.id.ll_history)
    LinearLayout ll_history;


    public static void launch(Context from) {
        if (!Utils.isFastClick()){
            return;
        }
        Intent intent = new Intent(from, SearchActivity.class);
        from.startActivity(intent);
    }

    public static void launch(Context from,String hint) {
        if (!Utils.isFastClick()){
            return;
        }
        Intent intent = new Intent(from, SearchActivity.class);
        intent.putExtra("hint",hint);
        from.startActivity(intent);
    }


    @Override
    public int getLayoutId() {
        return R.layout.activity_search;
    }

    private String hint;
    @Override
    public void initView(Bundle savedInstanceState) {
         hint = getIntent().getStringExtra("hint");
        if (!TextUtils.isEmpty(hint)){
            etSearch.setHint(hint);
        }

    }

    @Override
    protected int getToolbarLayout() {
        return -1;
    }

    private List<String> historySet;
    @Override
    public void loadData() {

       getHistoryData();
        new RxHttp<BaseResult<HotList<String>>>().send(ApiManager.getService().getSearchWords(),
                new Response<BaseResult<HotList<String>>>(isLoad,mActivity) {
                    @Override
                    public void onSuccess(BaseResult<HotList<String>> result) {
                        HotList<String> data = result.data;
                        addHotData(flHot, data.list);
//                        etSearch.setHint(data.keyword);
                    }
                });
    }

    //得到历史数据
    private void getHistoryData(){
        Gson gson = new Gson();
        String search = GlobalParam.getSearch();
        if (!TextUtils.isEmpty(search)){
            ll_history.setVisibility(View.VISIBLE);
            flRecent.setVisibility(View.VISIBLE);
            Type founderSetType = new TypeToken<ArrayList<String>>(){}.getType();
            historySet = gson.fromJson(search, founderSetType);
            addRecent(flRecent, historySet);
        }else {
            historySet = new ArrayList<>();
            ll_history.setVisibility(View.GONE);
            flRecent.setVisibility(View.GONE);
        }
    }
    private void saveSearch(String data){
        for (String s:historySet){
            if (s.equals(data)){
                historySet.remove(s);
                break;
            }
        }
        historySet.add(data);
        Gson gson = new Gson();
        String usersJson = gson.toJson(historySet);
        GlobalParam.setSearch(usersJson);
        getHistoryData();
    }


    private void addRecent(FlowLayout flowLayout, List<String> list) {
        if (list == null || list.size() == 0) {
            iv_clear.setVisibility(View.GONE);
        } else {
            iv_clear.setVisibility(View.VISIBLE);
        }
        //往容器内添加TextView数据
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        layoutParams.setMargins(DpUtil.dip2px(mContext, 12), DpUtil.dip2px(mContext, 5),
                0, DpUtil.dip2px(mContext, 5));
        if (flowLayout != null) {
            flowLayout.removeAllViews();
        }
        for (int i=list.size()-1;i>=0;i--) {
            String s = list.get(i);
            TextView tv = new TextView(this);
            tv.setPadding(DpUtil.dip2px(mContext, 15), DpUtil.dip2px(mContext, 3),
                    DpUtil.dip2px(mContext, 15), DpUtil.dip2px(mContext, 3));
            tv.setText(s);
            tv.setMaxWidth(ScreenUtil.getScreenWidth(mActivity) - DpUtil.dip2px(mContext, 24));
            tv.setEllipsize(TextUtils.TruncateAt.END);
            tv.setSingleLine();
            tv.setTextSize(14);
            tv.setTextColor(getResources().getColor(R.color.black_title_color));
            tv.setBackgroundResource(R.drawable.shape_gray50);

            tv.setLayoutParams(layoutParams);
            tv.setOnClickListener(v -> {
                SearchResultActivity.launch(mActivity, tv.getText().toString());
            });
            flowLayout.addView(tv, layoutParams);
        }
    }


    private void addHotData(FlowLayout flowLayout, List<String> list) {
        //往容器内添加TextView数据
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        layoutParams.setMargins(DpUtil.dip2px(mContext, 12), DpUtil.dip2px(mContext, 5),
                0, DpUtil.dip2px(mContext, 5));
        if (flowLayout != null) {
            flowLayout.removeAllViews();
        }
        for (String s : list) {
            TextView tv = new TextView(this);
            tv.setPadding(DpUtil.dip2px(mContext, 15), DpUtil.dip2px(mContext, 3),
                    DpUtil.dip2px(mContext, 15), DpUtil.dip2px(mContext, 3));
            tv.setText(s);
            tv.setMaxWidth(ScreenUtil.getScreenWidth(mActivity) - DpUtil.dip2px(mContext, 24));
            tv.setEllipsize(TextUtils.TruncateAt.END);
            tv.setSingleLine();
            tv.setTextSize(14);
            tv.setTextColor(getResources().getColor(R.color.black_title_color));
            tv.setBackgroundResource(R.drawable.shape_gray50);
            tv.setLayoutParams(layoutParams);
            tv.setOnClickListener(v -> {

                SearchResultActivity.launch(mActivity, s);
                saveSearch(s);
            });
            flowLayout.addView(tv, layoutParams);
        }
    }


    @OnClick({R.id.iv_back, R.id.ll_search, R.id.tv_cancel, R.id.iv_clear})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.ll_search:
//                SearchResultActivity.launch(mActivity, "");
                break;
            case R.id.iv_back:
            case R.id.tv_cancel:
                finish();
                break;
            case R.id.iv_clear:
                clean();
//                searchHistory.clear();
//                flRecent.removeAllViews();
//                GlobalParam.setSearchHistory(searchHistory);
                break;
        }
    }

    @Override
    public void initListener() {
        super.initListener();

        etSearch.setOnKeyListener((v, keyCode, event) -> {
            //是否是回车键
            if (keyCode == KeyEvent.KEYCODE_ENTER && event.getAction() == KeyEvent.ACTION_DOWN) {
                String trim = etSearch.getText().toString().trim();
                if (TextUtils.isEmpty(trim)) {
                    if (TextUtils.isEmpty(hint)) {
                        KeyboardUtils.hideKeyboard(etSearch);
                        tsg("请输入搜索内容");
                    }else {
                        saveSearch(hint);
                        SearchResultActivity.launch(mActivity, hint);
                    }
                } else {
                    saveSearch(trim);
                    SearchResultActivity.launch(mActivity, trim);
                }
            }
            return false;
        });
    }


    @Override
    public void onEvent(Object o) {
        super.onEvent(o);
        if (o instanceof UpdateSearch){
            saveSearch(((UpdateSearch)o).name);
        }
    }

    private void clean() {
        GlobalParam.setSearch("");
        getHistoryData();
//
    }
}
