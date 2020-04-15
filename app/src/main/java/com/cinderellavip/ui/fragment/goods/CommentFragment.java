package com.cinderellavip.ui.fragment.goods;

import android.os.Bundle;
import android.os.Handler;

import com.cinderellavip.R;
import com.cinderellavip.adapter.recycleview.CommentAdapter;
import com.cinderellavip.bean.local.CommnetTabItem;
import com.cinderellavip.util.DataUtil;
import com.cinderellavip.weight.tab.CommentTabLayout;
import com.tozzais.baselibrary.ui.BaseListFragment;

import java.util.ArrayList;
import java.util.List;

import androidx.recyclerview.widget.LinearLayoutManager;
import butterknife.BindView;


public class CommentFragment extends BaseListFragment<String> {

    @BindView(R.id.tab_label)
    CommentTabLayout tabLabel;
    private int id;

    @Override
    public int setLayout() {
        return R.layout.fragment_comment;
    }

    @Override
    public void initView(Bundle savedInstanceState) {
        super.initView(savedInstanceState);
        id = getArguments().getInt("id");
        mRecyclerView.setLayoutManager(new LinearLayoutManager(mActivity));
//        mRecyclerView.addItemDecoration(new LineItemDecoration(2, R.color.line));
        mAdapter = new CommentAdapter(true);
        mRecyclerView.setAdapter(mAdapter);
        setEmptyView("暂无评价");
    }

    @Override
    public void loadData() {

        new Handler().postDelayed(() -> {
            List<CommnetTabItem> tabList = new ArrayList<>();
            tabList.add(new CommnetTabItem("全部评价","169"));
            tabList.add(new CommnetTabItem("好评","100"));
            tabList.add(new CommnetTabItem("中评","20"));
            tabList.add(new CommnetTabItem("差评","9"));
            tabList.add(new CommnetTabItem("有图","40"));
            tabLabel.setTitle(tabList);

            setData(true, DataUtil.getData(4));
        }, 100);


    }


}
