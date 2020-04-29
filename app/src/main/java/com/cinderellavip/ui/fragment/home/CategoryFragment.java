package com.cinderellavip.ui.fragment.home;

import android.os.Bundle;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.cinderellavip.R;
import com.cinderellavip.adapter.recycleview.CategoryHomeAdapter;
import com.cinderellavip.adapter.recycleview.CategoryHomeListAdapter;
import com.cinderellavip.bean.net.CategoryItem;
import com.cinderellavip.listener.CategoryClickListener;
import com.tozzais.baselibrary.ui.BaseListFragment;
import com.tozzais.baselibrary.weight.ProgressLayout;

import java.util.ArrayList;
import java.util.List;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.BindView;


public class CategoryFragment extends BaseListFragment<String> implements CategoryClickListener {


    @BindView(R.id.rv_category)
    RecyclerView rvCategory;


    protected BaseQuickAdapter mCategoryAdapter;
    @BindView(R.id.progress_bar)
    ProgressLayout progressBar;
    @BindView(R.id.progress_bar1)
    ProgressLayout progressBar1;



    @Override
    public int setLayout() {
        return R.layout.fragment_category;
    }


    @Override
    public void initView(Bundle savedInstanceState) {
        super.initView(savedInstanceState);
        //左边的分类
        rvCategory.setLayoutManager(new LinearLayoutManager(mActivity));
        mCategoryAdapter = new CategoryHomeAdapter(this);
        rvCategory.setAdapter(mCategoryAdapter);

        //右边的商品
        mRecyclerView.setLayoutManager(new LinearLayoutManager(mActivity));
        mAdapter = new CategoryHomeListAdapter();
        mRecyclerView.setAdapter(mAdapter);



    }


    @Override
    public void loadData() {

//        getOneCategory();

        List<CategoryItem> data = new ArrayList<>();
        data.add(new CategoryItem(true,"食品生鲜"));
        data.add(new CategoryItem(false,"居家百货"));
        data.add(new CategoryItem(false,"家电电器"));
        data.add(new CategoryItem(false,"数码电子"));
        data.add(new CategoryItem(false,"美妆日化"));
        data.add(new CategoryItem(false,"母婴用品"));
        data.add(new CategoryItem(false,"个护清洗"));
        data.add(new CategoryItem(false,"服装鞋包"));
        data.add(new CategoryItem(false,"童装童鞋"));
        data.add(new CategoryItem(false,"宠物园艺"));
        mCategoryAdapter.setNewData(data);


        List<String> data1 = new ArrayList<>();
        data1.add("常用分类");
        mAdapter.setNewData(data1);
    }

    @Override
    public void initListener() {

        swipeLayout.setOnRefreshListener(() -> {
            swipeLayout.setRefreshing(false);
        });
        swipeLayout.setEnabled(false);
    }



    CategoryItem categoryItem;

    private int onePosition = 0;//一级分类选择的位置。防止点击相同的item 还请求网络的bug
    @Override
    public void onCategorySelect(int position) {


    }

    private void getOneCategory() {


    }

    private void getTwoCategory(int position, int type_id) {


    }


}
