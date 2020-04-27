package com.cinderellavip.ui.fragment.life;

import android.os.Bundle;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.cinderellavip.R;
import com.cinderellavip.adapter.recycleview.CategoryAdapter;
import com.cinderellavip.adapter.recycleview.CategoryListAdapter;
import com.cinderellavip.bean.net.CategoryItem;
import com.cinderellavip.listener.CategoryClickListener;
import com.tozzais.baselibrary.ui.BaseListFragment;
import com.tozzais.baselibrary.weight.ProgressLayout;

import java.util.ArrayList;
import java.util.List;

import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.BindView;


public class ServiceCheckListFragment extends BaseListFragment<String> implements CategoryClickListener {


    @BindView(R.id.rv_category)
    RecyclerView rvCategory;


    protected BaseQuickAdapter mCategoryAdapter;
    @BindView(R.id.progress_bar)
    ProgressLayout progressBar;
    @BindView(R.id.progress_bar1)
    ProgressLayout progressBar1;




    @Override
    public int setLayout() {
        return R.layout.fragment_service_check_list;
    }


    @Override
    public void initView(Bundle savedInstanceState) {
        super.initView(savedInstanceState);
        //左边的分类
        rvCategory.setLayoutManager(new LinearLayoutManager(mActivity));
        mCategoryAdapter = new CategoryAdapter(this);
        rvCategory.setAdapter(mCategoryAdapter);

        //右边的商品
        mRecyclerView.setLayoutManager(new LinearLayoutManager(mActivity));
        mAdapter = new CategoryListAdapter();
        mRecyclerView.setAdapter(mAdapter);

    }







    @Override
    public void loadData() {
        List<CategoryItem> data = new ArrayList<>();
        data.add(new CategoryItem(true,"热门"));
        data.add(new CategoryItem(false,"商务兼职"));
        data.add(new CategoryItem(false,"家政保洁"));
        data.add(new CategoryItem(false,"美体按摩"));
        data.add(new CategoryItem(false,"家政服务"));
        data.add(new CategoryItem(false,"美容美妆"));
        mCategoryAdapter.setNewData(data);


        List<String> data1 = new ArrayList<>();
        data1.add("日常保洁");
        data1.add("保洁套餐");
        data1.add("开荒");
        mAdapter.setNewData(data1);


    }

    @Override
    public void initListener() {
        swipeLayout.setOnRefreshListener(() -> {
            swipeLayout.setRefreshing(false);
        });
        swipeLayout.setEnabled(false);


    }


    @Override
    public void onCategorySelect(int position) {


    }




}
