package com.cinderellavip.ui.fragment.find;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.cinderellavip.R;
import com.cinderellavip.adapter.recycleview.SearchHotTopicAdapter;
import com.cinderellavip.bean.local.SearchItem;
import com.cinderellavip.ui.activity.find.SearchFindResultActivity;
import com.cinderellavip.ui.activity.find.TopicDetailActivity;
import com.cinderellavip.ui.activity.home.SearchResultActivity;
import com.cinderellavip.util.DataUtil;
import com.cinderellavip.util.ScreenUtil;
import com.cinderellavip.weight.FlowLayout;
import com.tozzais.baselibrary.ui.BaseFragment;
import com.tozzais.baselibrary.util.DpUtil;

import java.util.ArrayList;
import java.util.List;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.BindView;


public class SearchTopicFragment extends BaseFragment<String> {

    @BindView(R.id.fl_recent)
    FlowLayout flRecent;
    @BindView(R.id.rv_hot)
    RecyclerView rv_hot;
    @BindView(R.id.iv_clear)
    ImageView iv_clear;


    @Override
    public int setLayout() {
        return R.layout.fragment_search_find_topic;
    }

    @Override
    public void initView(Bundle savedInstanceState) {
        super.initView(savedInstanceState);


    }

    @Override
    public void loadData() {
        List<SearchItem> data = new ArrayList<>();
        data.add(new SearchItem("#我的百搭神仙单品#"));
        addRecent(flRecent, data);

        SearchHotTopicAdapter adapter = new SearchHotTopicAdapter();
        rv_hot.setLayoutManager(new LinearLayoutManager(mActivity));
        rv_hot.setAdapter(adapter);
        adapter.setNewData(DataUtil.getData(6));

    }

    private void addRecent(FlowLayout flowLayout, List<SearchItem> list) {
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
        for (SearchItem s : list) {
            TextView tv = new TextView(mActivity);
            tv.setPadding(DpUtil.dip2px(mActivity, 15), DpUtil.dip2px(mActivity, 3),
                    DpUtil.dip2px(mActivity, 15), DpUtil.dip2px(mActivity, 3));
            tv.setText(s.name);
            tv.setMaxWidth(ScreenUtil.getScreenWidth(mActivity) - DpUtil.dip2px(mActivity, 24));
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
                TopicDetailActivity.launch(mActivity);
            });
            flowLayout.addView(tv, layoutParams);
        }
    }





}
