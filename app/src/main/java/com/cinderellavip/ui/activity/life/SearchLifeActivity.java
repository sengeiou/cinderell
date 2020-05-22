package com.cinderellavip.ui.activity.life;

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
import com.cinderellavip.bean.net.HotList;
import com.cinderellavip.bean.net.life.HotSearchItem;
import com.cinderellavip.global.GlobalParam;
import com.cinderellavip.http.ApiManager;
import com.cinderellavip.http.BaseResult;
import com.cinderellavip.http.Response;
import com.cinderellavip.ui.activity.home.SearchResultActivity;
import com.cinderellavip.util.KeyboardUtils;
import com.cinderellavip.util.ScreenUtil;
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
public class SearchLifeActivity extends BaseActivity {


    @BindView(R.id.fl_recent)
    FlowLayout flRecent;
    @BindView(R.id.fl_hot)
    FlowLayout flHot;
    @BindView(R.id.et_search)
    EditText etSearch;
    @BindView(R.id.iv_clear)
    ImageView iv_clear;
    private int type;

    @BindView(R.id.ll_history)
    LinearLayout ll_history;
    private List<String> historySet;

//    ArrayList<String> searchHistory = new ArrayList<>();

    public static void launch(Context from, int type) {
        Intent intent = new Intent(from, SearchLifeActivity.class);
        from.startActivity(intent);
    }

    public static void launch(Context from) {
        Intent intent = new Intent(from, SearchLifeActivity.class);
        from.startActivity(intent);
    }


    @Override
    public int getLayoutId() {
        return R.layout.activity_search_life;
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

        getHistoryData();
        new RxHttp<BaseResult<List<HotSearchItem>>>().send(ApiManager.getService().getLifeSearchWords(),
                new Response<BaseResult<List<HotSearchItem>>>(isLoad,mActivity) {
                    @Override
                    public void onSuccess(BaseResult<List<HotSearchItem>> result) {
                        List<HotSearchItem> data = result.data;
                        addHotData(flHot, data);
                    }
                });
    }

    //得到历史数据
    private void getHistoryData(){
        Gson gson = new Gson();
        String search = GlobalParam.getLifeSearch();
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
//    @Override
//    public void onEvent(Object o) {
//        super.onEvent(o);
//        if (o instanceof UpdateHistorySearch){
//            loadData();
//        }
//    }

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
            tv.setBackgroundResource(R.drawable.shape_gray5);

            tv.setLayoutParams(layoutParams);

            tv.setOnClickListener(v -> {
                ServiceListActivity.launch(mActivity,tv.getText().toString());
            });
            flowLayout.addView(tv, layoutParams);
        }
    }


    private void addHotData(FlowLayout flowLayout, List<HotSearchItem> list) {
        //往容器内添加TextView数据
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        layoutParams.setMargins(DpUtil.dip2px(mContext, 12), DpUtil.dip2px(mContext, 5),
                0, DpUtil.dip2px(mContext, 5));
        if (flowLayout != null) {
            flowLayout.removeAllViews();
        }
        for (HotSearchItem s : list) {
            TextView tv = new TextView(this);
            tv.setPadding(DpUtil.dip2px(mContext, 15), DpUtil.dip2px(mContext, 3),
                    DpUtil.dip2px(mContext, 15), DpUtil.dip2px(mContext, 3));
            tv.setText(s.word);
            tv.setMaxWidth(ScreenUtil.getScreenWidth(mActivity) - DpUtil.dip2px(mContext, 24));
            tv.setEllipsize(TextUtils.TruncateAt.END);
            tv.setSingleLine();
            tv.setTextSize(14);
            tv.setTextColor(getResources().getColor(R.color.black_title_color));
            tv.setBackgroundResource(R.drawable.shape_gray5);
            tv.setLayoutParams(layoutParams);
            tv.setOnClickListener(v -> {
                ServiceListActivity.launch(mActivity,tv.getText().toString());
//                SearchResultActivity.launch(mActivity, tv.getText().toString());
            });
            flowLayout.addView(tv, layoutParams);
        }
    }


    @OnClick({ R.id.ll_search, R.id.tv_cancel, R.id.iv_clear})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.ll_search:
//                SearchResultActivity.launch(mActivity, "");
                break;
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
