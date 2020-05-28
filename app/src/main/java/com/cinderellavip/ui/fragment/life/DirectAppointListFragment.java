package com.cinderellavip.ui.fragment.life;

import android.os.Bundle;

import com.cinderellavip.adapter.recycleview.DirectListAdapter;
import com.cinderellavip.bean.ListData;
import com.cinderellavip.bean.direct.DirectPerson;
import com.cinderellavip.global.CinderellApplication;
import com.cinderellavip.http.ApiManager;
import com.cinderellavip.http.BaseResult;
import com.cinderellavip.http.Response;
import com.tozzais.baselibrary.http.RxHttp;
import com.tozzais.baselibrary.ui.BaseListFragment;

import java.util.TreeMap;

import androidx.recyclerview.widget.LinearLayoutManager;

public class DirectAppointListFragment extends BaseListFragment<DirectPerson> {


    private int service;

    public static DirectAppointListFragment newInstance(int service){
        DirectAppointListFragment cartFragment = new DirectAppointListFragment();
        Bundle bundle = new Bundle();
        bundle.putInt("service",service);
        cartFragment.setArguments(bundle);
        return cartFragment;

    }





    @Override
    public void initView(Bundle savedInstanceState) {
        super.initView(savedInstanceState);
        service = getArguments().getInt("service");
        mRecyclerView.setLayoutManager(new LinearLayoutManager(mActivity));
        mAdapter = new DirectListAdapter();
        mRecyclerView.setAdapter(mAdapter);

        setEmptyView("暂无服务人员信息");

    }

    @Override
    public void loadData() {
        super.loadData();
        getData();



    }

    private void  getData(){
        TreeMap<String, String> hashMap = new TreeMap<>();
        hashMap.put("service", ""+service);
        hashMap.put("city", ""+ CinderellApplication.name);
        hashMap.put("longitude", ""+ CinderellApplication.longitude);
        hashMap.put("latitude", ""+ CinderellApplication.latitude);
        hashMap.put("page", ""+page);
        new RxHttp<BaseResult<ListData<DirectPerson>>>().send(ApiManager.getService().getDirectPerson(hashMap),
                new Response<BaseResult<ListData<DirectPerson>>>(isLoad,getContext()) {
                    @Override
                    public void onSuccess(BaseResult<ListData<DirectPerson>> result) {
                        setData(result.data.data);
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

    @Override
    public void initListener() {
        super.initListener();
        mAdapter.setOnItemClickListener(((baseQuickAdapter, view, position) -> {


        }));
    }


    public void setId(int id) {
        if (isLoad){
            this.service = id;
            onRefresh();
        }

    }
}
