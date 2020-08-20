package com.cinderellavip.ui.fragment.home;

import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.recyclerview.widget.LinearLayoutManager;

import com.cinderellavip.R;
import com.cinderellavip.adapter.recycleview.OrderAdapter;
import com.cinderellavip.adapter.recycleview.SearchShopAdapter;
import com.cinderellavip.bean.ListOrders;
import com.cinderellavip.bean.SearchListResult;
import com.cinderellavip.bean.SearchStore;
import com.cinderellavip.bean.eventbus.OrderComment;
import com.cinderellavip.bean.eventbus.OrderPaySuccess;
import com.cinderellavip.bean.eventbus.ReceiveOrder;
import com.cinderellavip.bean.local.HomeGoods;
import com.cinderellavip.bean.local.OrderBean;
import com.cinderellavip.http.ApiManager;
import com.cinderellavip.http.BaseResult;
import com.cinderellavip.http.Response;
import com.cinderellavip.util.DataUtil;
import com.cinderellavip.weight.LinearSpace1;
import com.tozzais.baselibrary.http.RxHttp;
import com.tozzais.baselibrary.ui.BaseListFragment;
import com.tozzais.baselibrary.util.DpUtil;

import java.util.List;
import java.util.TreeMap;

import butterknife.BindView;


public class SearchShopFragment extends BaseListFragment<SearchStore> {


    @BindView(R.id.tv_current_page)
    TextView tvCurrentPage;
    @BindView(R.id.tv_total_page)
    TextView tvTotalPage;
    @BindView(R.id.iv_top)
    LinearLayout iv_top;

    @Override
    public int setLayout() {
        return R.layout.fragment_recycleview_search_result;
    }


    private String keyword;
    public static SearchShopFragment newInstance(String keyword) {
        SearchShopFragment fragment = new SearchShopFragment();
        Bundle bundle = new Bundle();
        bundle.putString("keyword", keyword);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public void initView(Bundle savedInstanceState) {
        super.initView(savedInstanceState);
        keyword = getArguments().getString("keyword");

        mRecyclerView.setLayoutManager(new LinearLayoutManager(mActivity));

        LinearSpace1 girdSpace = new LinearSpace1(DpUtil.dip2px(mActivity, 12));
        mRecyclerView.addItemDecoration(girdSpace);

        mAdapter = new SearchShopAdapter();
        mRecyclerView.setAdapter(mAdapter);

        setEmptyView("没有相关店铺哦~");



    }

    @Override
    public void loadData() {
        super.loadData();
        getData();


    }

    private void  getData(){
        //解决搜索面膜数据总数为70 一共显示5页的bug  PageSize必须设置为20
        PageSize = 20;
        TreeMap<String, String> hashMap = new TreeMap<>();
        hashMap.put("keyword", "" + keyword);
        hashMap.put("limit", ""+PageSize);
        hashMap.put("page", ""+page);
        new RxHttp<BaseResult<SearchListResult<SearchStore>>>().send(ApiManager.getService().getSearchStore(hashMap),
                new Response<BaseResult<SearchListResult<SearchStore>>>(isLoad,getContext()) {
                    @Override
                    public void onSuccess(BaseResult<SearchListResult<SearchStore>> result) {
                        SearchListResult<SearchStore> data = result.data;
                        int total = data.total;
                        List<SearchStore> list = data.list;
                        if (total>0){
                            iv_top.setVisibility(View.VISIBLE);
                            tvTotalPage.setText(""+(total/PageSize+(total%PageSize == 0?0:1) ));
                            if (list != null && list.size()>0)
                                tvCurrentPage.setText(""+page);
                        }else {
                            iv_top.setVisibility(View.GONE);
                        }
                        setData(list);
                    }
                    @Override
                    public void onError(Throwable e) {
                        onErrorResult(e);
                    }
                    @Override
                    public void onErrorShow(String s) {
                        showError(s);
                    }
                });
    }


    public void setKeyword(String keyword) {
        this.keyword = keyword;
        swipeLayout.setRefreshing(true);
        onRefresh();

    }

}
