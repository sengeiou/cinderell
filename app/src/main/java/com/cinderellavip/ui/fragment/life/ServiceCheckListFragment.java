package com.cinderellavip.ui.fragment.life;

import android.os.Bundle;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.cinderellavip.R;
import com.cinderellavip.adapter.recycleview.CategoryAdapter;
import com.cinderellavip.adapter.recycleview.CategoryListAdapter;
import com.cinderellavip.bean.net.life.LiftCategoryItem;
import com.cinderellavip.global.CinderellaApplication;
import com.cinderellavip.http.ApiManager;
import com.cinderellavip.http.BaseResult;
import com.cinderellavip.http.ListResult;
import com.cinderellavip.http.Response;
import com.cinderellavip.listener.CategoryClickListener;
import com.tozzais.baselibrary.http.RxHttp;
import com.tozzais.baselibrary.ui.BaseListFragment;
import com.tozzais.baselibrary.weight.ProgressLayout;

import java.util.List;
import java.util.TreeMap;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.BindView;


public class ServiceCheckListFragment extends BaseListFragment<LiftCategoryItem> implements CategoryClickListener {


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






    private List<LiftCategoryItem> listResult;
    @Override
    public void loadData() {
        super.loadData();
        TreeMap<String, String> hashMap = new TreeMap<>();
        hashMap.put("city", CinderellaApplication.name);
        new RxHttp<BaseResult<ListResult<LiftCategoryItem>>>().send(ApiManager.getService().life_checklist(hashMap),
                new Response<BaseResult<ListResult<LiftCategoryItem>>>(isLoad,mActivity) {
                    @Override
                    public void onSuccess(BaseResult<ListResult<LiftCategoryItem>> result) {
                        listResult = result.data.list;
                        LiftCategoryItem liftCategoryItem = listResult.get(0);
                        liftCategoryItem.isCheck = true;
                        mCategoryAdapter.setNewData(listResult);
                        setData(true,liftCategoryItem.data);
                    }

                    @Override
                    public void onErrorShow(String s) {
                        showError(s);
                    }
                });
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

        setData(true,listResult.get(position).data);
    }




}
