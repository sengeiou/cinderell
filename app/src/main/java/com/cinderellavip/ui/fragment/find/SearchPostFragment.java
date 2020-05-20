package com.cinderellavip.ui.fragment.find;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.cinderellavip.R;
import com.cinderellavip.bean.eventbus.UpdateSearchPost;
import com.cinderellavip.global.GlobalParam;
import com.cinderellavip.ui.activity.find.SearchFindResultActivity;
import com.cinderellavip.util.ScreenUtil;
import com.cinderellavip.weight.FlowLayout;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.tozzais.baselibrary.ui.BaseFragment;
import com.tozzais.baselibrary.util.DpUtil;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;


public class SearchPostFragment extends BaseFragment<UpdateSearchPost> {

    @BindView(R.id.fl_recent)
    FlowLayout flRecent;
    @BindView(R.id.iv_clear)
    ImageView iv_clear;

    @BindView(R.id.ll_history)
    LinearLayout ll_history;


    @Override
    public int setLayout() {
        return R.layout.fragment_search_find_post;
    }

    @Override
    public void initView(Bundle savedInstanceState) {
        super.initView(savedInstanceState);


    }

    @Override
    public void loadData() {
        getHistoryData();

    }

    //得到历史数据
    private List<String> historySet;

    private void getHistoryData() {
        Gson gson = new Gson();
        String search = GlobalParam.getSearchPost();
        if (!TextUtils.isEmpty(search)) {
            ll_history.setVisibility(View.VISIBLE);
            flRecent.setVisibility(View.VISIBLE);
            Type founderSetType = new TypeToken<ArrayList<String>>() {
            }.getType();
            historySet = gson.fromJson(search, founderSetType);
            addRecent(flRecent, historySet);
        } else {
            historySet = new ArrayList<>();
            ll_history.setVisibility(View.GONE);
            flRecent.setVisibility(View.GONE);
        }
    }

    private void addRecent(FlowLayout flowLayout, List<String> list) {
        if (list == null || list.size() == 0) {
            iv_clear.setVisibility(View.GONE);
        } else {
            iv_clear.setVisibility(View.VISIBLE);
        }
        //往容器内添加TextView数据
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        layoutParams.setMargins(DpUtil.dip2px(mActivity, 12), DpUtil.dip2px(mActivity, 5),
                0, DpUtil.dip2px(mActivity, 5));
        if (flowLayout != null) {
            flowLayout.removeAllViews();
        }
        for (int i=list.size()-1;i>=0;i--) {
            String s = list.get(i);
            TextView tv = new TextView(mActivity);
            tv.setPadding(DpUtil.dip2px(mActivity, 15), DpUtil.dip2px(mActivity, 3),
                    DpUtil.dip2px(mActivity, 15), DpUtil.dip2px(mActivity, 3));
            tv.setText(s);
            tv.setMaxWidth(ScreenUtil.getScreenWidth(mActivity) - DpUtil.dip2px(mActivity, 24));
            tv.setEllipsize(TextUtils.TruncateAt.END);
            tv.setSingleLine();
            tv.setTextSize(14);
            tv.setTextColor(getResources().getColor(R.color.black_title_color));
            tv.setBackgroundResource(R.drawable.shape_gray50);
            tv.setLayoutParams(layoutParams);
            tv.setOnClickListener(v -> {
                SearchFindResultActivity.launch(mActivity, tv.getText().toString(),SearchFindResultActivity.POST);
            });
            flowLayout.addView(tv, layoutParams);
        }
    }

    private void saveSearch(String data) {
        for (String s : historySet) {
            if (s.equals(data)) {
                historySet.remove(s);
                break;
            }
        }
        historySet.add(data);
        Gson gson = new Gson();
        String usersJson = gson.toJson(historySet);
        GlobalParam.setSearchPost(usersJson);
        getHistoryData();
    }

    @Override
    public void onEvent(UpdateSearchPost o) {
        super.onEvent(o);
        saveSearch(o.name);
    }


    @OnClick(R.id.iv_clear)
    public void onClick() {
        GlobalParam.setSearchPost("");
        getHistoryData();
    }
}
