package com.cinderellavip.ui.fragment.home;

import android.os.Bundle;
import android.os.Handler;
import android.widget.ImageView;
import android.widget.TextView;

import com.cinderellavip.R;
import com.cinderellavip.adapter.recycleview.HomeGoodsAdapter;
import com.cinderellavip.adapter.recycleview.SearchGoodsAdapter;
import com.cinderellavip.bean.local.HomeGoods;
import com.cinderellavip.util.DataUtil;
import com.cinderellavip.weight.GirdSpace;
import com.tozzais.baselibrary.ui.BaseListFragment;
import com.tozzais.baselibrary.util.DpUtil;

import androidx.recyclerview.widget.GridLayoutManager;
import butterknife.BindView;


public class SearchResultFragment extends BaseListFragment<HomeGoods> {


    @Override
    public int setLayout() {
        return R.layout.fragment_recycleview_search_result;
    }

    public String sort = "0",area = "0",type_child_ids = "";
    /**
     * 一级商品lieb
     * @param type  parent_id == 0 就是一级
     * @param type_id 一级分类ID
     * @return
     */
    private int type_id;
    public static SearchResultFragment newInstance(int type_id) {
        SearchResultFragment cartFragment = new SearchResultFragment();
        Bundle bundle = new Bundle();
        bundle.putInt("type_id", type_id);
        cartFragment.setArguments(bundle);
        return cartFragment;
    }



    /**
     *
     * @param keyword 所搜页面进入
     * @return
     */
    private String keyword;
    public static SearchResultFragment newInstance(String keyword) {
        SearchResultFragment cartFragment = new SearchResultFragment();
        Bundle bundle = new Bundle();
        bundle.putString("keyword", keyword);
        cartFragment.setArguments(bundle);
        return cartFragment;
    }



    @Override
    public void initView(Bundle savedInstanceState) {
        super.initView(savedInstanceState);
        type_id = getArguments().getInt("type_id");
        keyword = getArguments().getString("keyword");

        girdSpace = new GirdSpace(DpUtil.dip2px(mActivity, 10),2,0,true);
        setLayoutManager();



        setEmptyView("没有更多商品，换个关键字试试");

    }



    @Override
    public void loadData() {
        super.loadData();
//        TreeMap<String, String> hashMap = new TreeMap<>();
//        hashMap.put("user_id", GlobalParam.getUserId());
//        hashMap.put("count", PageSize+"");
//        hashMap.put("page", page + "");
//        if (type_id != 0)
//        hashMap.put("type_id", type_id + "");
//        hashMap.put("sort", sort + "");
//        hashMap.put("type_child_ids", type_child_ids+"");
//        hashMap.put("area", ""+area);
//        if (!TextUtils.isEmpty(keyword))
//        hashMap.put("keyword", keyword);
//        hashMap.put("sign", SignUtil.getMd5(hashMap));
//
//        new RxHttp<BaseListResult<HomeGoods>>().send(ApiManager.getService().getGoodsListForCategory(hashMap),
//                new Response<BaseListResult<HomeGoods>>(isLoad,mActivity) {
//                    @Override
//                    public void onSuccess(BaseListResult<HomeGoods> result) {
//                        showContent();
//                        for (HomeGoods homeGoods:result.data){
//                            homeGoods.setType(isGrid?HomeGoods.GRID:HomeGoods.VERTICAL);
//                        }
        new Handler().postDelayed(() -> {
            setData(DataUtil.getHomeGoods(8,0));
        }, 100);

//                    }
//                    @Override
//                    public void onError(Throwable e) {
//                        onErrorResult(e);
//                    }
//
//                    @Override
//                    public void onErrorShow(String s) {
//                        showError(s);
//                    }
//
//                    @Override
//                    public void onCompleted() {
//                        super.onCompleted();
//                        if (!TextUtils.isEmpty(keyword))
//                            EventBus.getDefault().post(new UpdateHistorySearch());
//                    }
//                });

    }
    public void setSortAndArea(String sort, String area){
        this.sort = sort;
        this.area = area;
        onRefresh();

    }
    public void setKeyword(String keyword){
        this.keyword = keyword;
        onRefresh();

    }

    public void setTypeIds(String type_child_ids){
        this.type_child_ids = type_child_ids;
        onRefresh();

    }

    private  GirdSpace girdSpace;
    private boolean isGrid = true;
    private void setLayoutManager(){
        mRecyclerView.setLayoutManager(new GridLayoutManager(mActivity, 2));
        mRecyclerView.addItemDecoration(girdSpace);
        mAdapter = new SearchGoodsAdapter();
        mRecyclerView.setAdapter(mAdapter);
    }







}
