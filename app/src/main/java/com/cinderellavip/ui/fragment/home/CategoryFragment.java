package com.cinderellavip.ui.fragment.home;

import android.os.Bundle;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.cinderellavip.R;
import com.cinderellavip.adapter.recycleview.CategoryHomeAdapter;
import com.cinderellavip.adapter.recycleview.CategoryHomeListAdapter;
import com.cinderellavip.bean.net.CategoryItem;
import com.cinderellavip.bean.net.HomeCategoryItem;
import com.cinderellavip.bean.net.HomeCategoryResult;
import com.cinderellavip.bean.net.home.CateMoreList;
import com.cinderellavip.http.ApiManager;
import com.cinderellavip.http.BaseResult;
import com.cinderellavip.http.ListResult;
import com.cinderellavip.http.Response;
import com.cinderellavip.listener.CategoryClickListener;
import com.cinderellavip.util.dialog.RightDialogUtil;
import com.tozzais.baselibrary.http.RxHttp;
import com.tozzais.baselibrary.ui.BaseListFragment;
import com.tozzais.baselibrary.weight.ProgressLayout;

import java.util.ArrayList;
import java.util.List;
import java.util.TreeMap;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.BindView;


public class CategoryFragment extends BaseListFragment<CateMoreList> implements CategoryClickListener {


    @BindView(R.id.rv_category)
    RecyclerView rvCategory;


    protected CategoryHomeAdapter mCategoryAdapter;
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
        getCategory();
    }

    @Override
    public void initListener() {

        swipeLayout.setOnRefreshListener(() -> {
            swipeLayout.setRefreshing(false);
        });
        swipeLayout.setEnabled(false);
    }



    private int onePosition = 0;//一级分类选择的位置。防止点击相同的item 还请求网络的bug
    @Override
    public void onCategorySelect(int position) {
        if (onePosition == position){
            return;
        }
        this.onePosition = position;
        HomeCategoryItem homeCategoryItem = mCategoryAdapter.getData().get(position);
        getTwoCategory(homeCategoryItem.id+"");


    }

    /**
     * 获取一级分类
     */
    private void getCategory(){
        new RxHttp<BaseResult<HomeCategoryResult>>().send(ApiManager.getService().getHomeCategory(),
                new Response<BaseResult<HomeCategoryResult>>(isLoad,mActivity) {
                    @Override
                    public void onSuccess(BaseResult<HomeCategoryResult> result) {
                        showContent();
                        isLoad = true;
                        List<HomeCategoryItem> list = result.data.list;
                        HomeCategoryItem homeCategoryItem = list.get(0);
                        homeCategoryItem.isCheck = true;
                        mCategoryAdapter.setNewData(list);
                        getTwoCategory(homeCategoryItem.id+"");
                    }
                    @Override
                    public void onErrorShow(String s) {
                        tsg(s);
                    }
                });

    }

    private void getTwoCategory(String first_category_id) {
        TreeMap<String, String> hashMap = new TreeMap<>();
        hashMap.put("first_category_id", ""+first_category_id);
        new RxHttp<BaseResult<ListResult<CateMoreList>>>().send(ApiManager.getService().getHomeMoreCate(hashMap),
                new Response<BaseResult<ListResult<CateMoreList>>>(isLoad,getContext()) {
                    @Override
                    public void onSuccess(BaseResult<ListResult<CateMoreList>> result) {
                        List<CateMoreList> listListResult = new ArrayList<>();
                        List<CateMoreList> list = result.data.list;
                        for (CateMoreList cateMoreList:list){
                            if (cateMoreList != null && cateMoreList.next != null && cateMoreList.next.size()>0){
                                listListResult.add(cateMoreList);
                            }
                        }
                        setData(true,listListResult);
                    }
                });
    }


}
