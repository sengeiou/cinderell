package com.cinderellavip.ui.fragment.mine;

import android.os.Bundle;

import com.cinderellavip.adapter.recycleview.CollectBrandAdapter;
import com.cinderellavip.bean.net.find.FindItem;
import com.cinderellavip.http.ApiManager;
import com.cinderellavip.http.BaseResult;
import com.cinderellavip.http.ListResult;
import com.cinderellavip.http.Response;
import com.tozzais.baselibrary.http.RxHttp;
import com.tozzais.baselibrary.ui.BaseListFragment;

import java.util.TreeMap;

import androidx.recyclerview.widget.LinearLayoutManager;


/**
 */
public class CollectBrandFragment extends BaseListFragment<FindItem> {




    @Override
    public void loadData() {
        super.loadData();
        TreeMap<String, String> hashMap = new TreeMap<>();
        hashMap.put("type", "2");
        hashMap.put("limit", ""+PageSize);
        hashMap.put("page", ""+page);
        new RxHttp<BaseResult<ListResult<FindItem>>>().send(ApiManager.getService().mineCollect(hashMap),
                new Response<BaseResult<ListResult<FindItem>>>(isLoad,getContext()) {
                    @Override
                    public void onSuccess(BaseResult<ListResult<FindItem>> result) {
                        setData(result.data.list);
                    }
                    @Override
                    public void onErrorShow(String s) {
                        showError(s);
                    }
                });
    }



    @Override
    public void initView(Bundle savedInstanceState) {
        super.initView(savedInstanceState);
        //设置商品
        mRecyclerView.setLayoutManager(new LinearLayoutManager(mActivity));
        mAdapter = new CollectBrandAdapter();
        mRecyclerView.setAdapter(mAdapter);

        setEmptyView("暂无收藏的品牌");
    }


}
