package com.cinderellavip.ui.fragment.home;

import android.os.Bundle;
import android.view.View;


import com.cinderellavip.adapter.recycleview.MerchantGoodsCategoryAdapter;
import com.cinderellavip.bean.net.HomeCategoryItem;
import com.cinderellavip.global.GlobalParam;
import com.cinderellavip.http.ApiManager;
import com.cinderellavip.http.BaseResult;
import com.cinderellavip.http.ListResult;
import com.cinderellavip.http.Response;
import com.tozzais.baselibrary.http.RxHttp;
import com.tozzais.baselibrary.ui.BaseListFragment;

import java.util.TreeMap;

import androidx.recyclerview.widget.LinearLayoutManager;


public class MerchantGoodsCategoryFragment extends BaseListFragment<HomeCategoryItem> {



    int merchant_id;


    public static MerchantGoodsCategoryFragment newInstance(int merchant_id) {
        MerchantGoodsCategoryFragment cartFragment = new MerchantGoodsCategoryFragment();
        Bundle bundle = new Bundle();
        bundle.putInt("merchant_id", merchant_id);
        cartFragment.setArguments(bundle);
        return cartFragment;

    }


    @Override
    public void initView(Bundle savedInstanceState) {
        super.initView(savedInstanceState);
        merchant_id= getArguments().getInt("merchant_id");

        mRecyclerView.setLayoutManager(new LinearLayoutManager(mActivity));
        mAdapter = new MerchantGoodsCategoryAdapter(merchant_id);
        mRecyclerView.setAdapter(mAdapter);


    }

    @Override
    public void loadData() {
        super.loadData();
        TreeMap<String, String> hashMap = new TreeMap<>();
        hashMap.put("store_id", "" + merchant_id);
        new RxHttp<BaseResult<ListResult<HomeCategoryItem>>>().send(ApiManager.getService().storeCategory(hashMap),
                new Response<BaseResult<ListResult<HomeCategoryItem>>>(isLoad, mActivity) {
                    @Override
                    public void onSuccess(BaseResult<ListResult<HomeCategoryItem>> result) {
                        showContent();
                        setData(result.data.list);
                    }
                    @Override
                    public void onErrorShow(String s) {
                        showError(s);
                    }
                });

    }

}
