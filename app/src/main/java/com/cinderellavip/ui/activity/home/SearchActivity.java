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
import com.cinderellavip.bean.local.SearchItem;
import com.cinderellavip.http.ApiManager;
import com.cinderellavip.http.BaseResult;
import com.cinderellavip.http.ListResult;
import com.cinderellavip.http.Response;
import com.cinderellavip.util.KeyboardUtils;
import com.cinderellavip.util.ScreenUtil;
import com.cinderellavip.weight.FlowLayout;
import com.tozzais.baselibrary.http.RxHttp;
import com.tozzais.baselibrary.ui.BaseActivity;
import com.tozzais.baselibrary.util.DpUtil;

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
    private int type;

//    ArrayList<String> searchHistory = new ArrayList<>();

    public static void launch(Context from, int type) {
        Intent intent = new Intent(from, SearchActivity.class);
        intent.putExtra("type", type);
        from.startActivity(intent);
    }

    public static void launch(Context from) {
        Intent intent = new Intent(from, SearchActivity.class);
        from.startActivity(intent);
    }


    @Override
    public int getLayoutId() {
        return R.layout.activity_search;
    }

    @Override
    public void initView(Bundle savedInstanceState) {
        type = getIntent().getIntExtra("type", 0);
    }

    @Override
    protected int getToolbarLayout() {
        return -1;
    }

    @Override
    public void loadData() {
//        TreeMap<String, String> hashMap = new TreeMap<>();
//        hashMap.put("user_id", GlobalParam.getUserId());
//        hashMap.put("sign", SignUtil.getMd5(hashMap));
//        new RxHttp<SearchList>().send(ApiManager.getService().getSearchList(hashMap),
//                new Response<SearchList>(isLoad,mActivity) {
//                    @Override
//                    public void onSuccess(SearchList result) {
//                        showContent();
        List<SearchItem> data = new ArrayList<>();
        data.add(new SearchItem("防晒"));
        data.add(new SearchItem("饼干"));
        data.add(new SearchItem("沐浴乳"));
        data.add(new SearchItem("拖鞋"));
        addRecent(flRecent, data);


//                        addData(tlCommon,result.data2);
//                    }
//
//                    @Override
//                    public void onErrorShow(String s) {
//                        showError(s);
//                    }
//                });


        new RxHttp<BaseResult<ListResult<String>>>().send(ApiManager.getService().getSearchWords(),
                new Response<BaseResult<ListResult<String>>>(isLoad,mActivity) {
                    @Override
                    public void onSuccess(BaseResult<ListResult<String>> result) {
                        addHotData(flHot, result.data.list);
                    }
                });
    }


    private void addRecent(FlowLayout flowLayout, List<SearchItem> list) {
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
        for (SearchItem s : list) {
            TextView tv = new TextView(this);
            tv.setPadding(DpUtil.dip2px(mContext, 15), DpUtil.dip2px(mContext, 3),
                    DpUtil.dip2px(mContext, 15), DpUtil.dip2px(mContext, 3));
            tv.setText(s.name);
            tv.setMaxWidth(ScreenUtil.getScreenWidth(mActivity) - DpUtil.dip2px(mContext, 24));
            tv.setEllipsize(TextUtils.TruncateAt.END);
            tv.setSingleLine();
            tv.setTextSize(14);
            tv.setTextColor(getResources().getColor(R.color.black_title_color));
            tv.setBackgroundResource(R.drawable.shape_gray50);

            tv.setLayoutParams(layoutParams);

//            Log.e("TAG",layoutParams.width+"=="+ScreenUtil.getScreenWidth(mActivity));
//            if (layoutParams.width>ScreenUtil.getScreenWidth(mActivity)){
//                layoutParams.width = ScreenUtil.getScreenWidth(mActivity);
//            }
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
                SearchResultActivity.launch(mActivity, tv.getText().toString());
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
//        etSearch.setOnEditorActionListener((v, actionId, event) -> {
//            if (actionId== EditorInfo.IME_ACTION_SEND ||(event!=null&&event.getKeyCode()== KeyEvent.KEYCODE_ENTER)) {
//                Log.e("TAG","搜索了");
//                String trim = etSearch.getText().toString().trim();
//                if (TextUtils.isEmpty(trim)){
//                    tsg("请输入关键字");
//                }else {
//                    SearchResultActivity.launch(mActivity, trim);
//                }
//                return true;
//            }
//            return false;
//        });

        etSearch.setOnKeyListener((v, keyCode, event) -> {
            //是否是回车键
            if (keyCode == KeyEvent.KEYCODE_ENTER && event.getAction() == KeyEvent.ACTION_DOWN) {
                String trim = etSearch.getText().toString().trim();
                if (TextUtils.isEmpty(trim)) {
                    KeyboardUtils.hideKeyboard(etSearch);
                    tsg("请输入关键字");
                } else {
                    SearchResultActivity.launch(mActivity, trim);
                }
            }
            return false;
        });
    }

    private void clean() {
//        TreeMap<String, String> hashMap = new TreeMap<>();
//        hashMap.put("user_id", GlobalParam.getUserId());
//        hashMap.put("sign", SignUtil.getMd5(hashMap));
//        new RxHttp<BaseResult>().send(ApiManager.getService().getRemoveSearch(hashMap),
//                new Response<BaseResult>(mActivity) {
//                    @Override
//                    public void onSuccess(BaseResult result) {
//                        loadData();
//                    }
//                });
    }
}
