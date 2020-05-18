package com.cinderellavip.ui.fragment.mine;

import android.os.Bundle;

import com.cinderellavip.adapter.recycleview.MineGroupUpAdapter;
import com.cinderellavip.bean.local.OrderBean;
import com.cinderellavip.http.ApiManager;
import com.cinderellavip.http.BaseResult;
import com.cinderellavip.http.ListResult;
import com.cinderellavip.http.Response;
import com.tozzais.baselibrary.http.RxHttp;
import com.tozzais.baselibrary.ui.BaseListFragment;

import java.util.TreeMap;

import androidx.recyclerview.widget.LinearLayoutManager;


public class MineGroupUpFragment extends BaseListFragment<OrderBean> {


    private int type;

    public static MineGroupUpFragment newInstance(int type){
        MineGroupUpFragment cartFragment = new MineGroupUpFragment();
        Bundle bundle = new Bundle();
        bundle.putInt("type",type);
        cartFragment.setArguments(bundle);
        return cartFragment;

    }


    @Override
    public void initView(Bundle savedInstanceState) {
        super.initView(savedInstanceState);
        type = getArguments().getInt("type");
        mRecyclerView.setLayoutManager(new LinearLayoutManager(mActivity));
        mAdapter = new MineGroupUpAdapter();
        mRecyclerView.setAdapter(mAdapter);

        setEmptyView("您还没有相关拼团哦~");

    }

    private String category = "1";
    @Override
    public void loadData() {
        super.loadData();
        getData();


    }

    private void  getData(){
        TreeMap<String, String> hashMap = new TreeMap<>();
        hashMap.put("status", ""+type);
        hashMap.put("limit", ""+PageSize);
        hashMap.put("page", ""+page);
        new RxHttp<BaseResult<ListResult<OrderBean>>>().send(ApiManager.getService().getGroupOrderList(hashMap),
                new Response<BaseResult<ListResult<OrderBean>>>(isLoad,getContext()) {
                    @Override
                    public void onSuccess(BaseResult<ListResult<OrderBean>> result) {
                        setData(result.data.list);
                    }
                    @Override
                    public void onErrorShow(String s) {
                        showError(s);
                    }
                });
    }

}
