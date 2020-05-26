package com.cinderellavip.ui.fragment.find;

import android.os.Bundle;

import com.cinderellavip.R;
import com.cinderellavip.adapter.recycleview.FindAdapter;
import com.cinderellavip.bean.eventbus.AccountExit;
import com.cinderellavip.bean.eventbus.LoginSuccess;
import com.cinderellavip.bean.net.find.FindItem;
import com.cinderellavip.bean.net.find.ListDiscussesResult;
import com.cinderellavip.global.GlobalParam;
import com.cinderellavip.http.ApiManager;
import com.cinderellavip.http.BaseResult;
import com.cinderellavip.http.Response;
import com.cinderellavip.ui.activity.account.LoginActivity;
import com.cinderellavip.weight.GirdSpaceStag;
import com.tozzais.baselibrary.http.RxHttp;
import com.tozzais.baselibrary.ui.BaseListFragment;
import com.tozzais.baselibrary.util.DpUtil;

import java.util.TreeMap;

import androidx.recyclerview.widget.StaggeredGridLayoutManager;


public class FindAttentionFragment extends BaseListFragment<FindItem> {


    public static FindAttentionFragment newInstance() {
        FindAttentionFragment cartFragment = new FindAttentionFragment();
        Bundle bundle = new Bundle();
        cartFragment.setArguments(bundle);
        return cartFragment;
    }



    @Override
    public void initView(Bundle savedInstanceState) {
        super.initView(savedInstanceState);

        GirdSpaceStag girdSpace = new GirdSpaceStag(DpUtil.dip2px(mActivity, 10),2,0,true);
        mRecyclerView.setLayoutManager(new StaggeredGridLayoutManager(2,StaggeredGridLayoutManager.VERTICAL));
        mRecyclerView.addItemDecoration(girdSpace);
        mAdapter = new FindAdapter();
        mRecyclerView.setAdapter(mAdapter);






    }



    @Override
    public void loadData() {
        super.loadData();
        if (GlobalParam.getUserLogin()){
            setEmptyView("暂无关注信息");
            getData();
        }else {
            setEmptyView(R.mipmap.empty_view,"登录账号，查看关注的精彩内容","登录",v -> {
                LoginActivity.launch(mActivity,true);
            });
            setData(true,null);
        }



    }
    private void getData(){
        new RxHttp<BaseResult<ListDiscussesResult>>().send(ApiManager.getService().getDiscussCollects(),
                new Response<BaseResult<ListDiscussesResult>>(isLoad,getContext()) {
                    @Override
                    public void onSuccess(BaseResult<ListDiscussesResult> result) {
                        setData(result.data.discusses);
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
        if (swipeLayout != null)
            swipeLayout.setOnRefreshListener(this::onRefresh);
    }

    @Override
    public void onEvent(Object o) {
        super.onEvent(o);
        if (o instanceof LoginSuccess ||o instanceof AccountExit){
            onRefresh();
        }
    }
}
