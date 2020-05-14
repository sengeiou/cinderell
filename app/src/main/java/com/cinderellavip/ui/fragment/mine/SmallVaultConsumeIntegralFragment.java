package com.cinderellavip.ui.fragment.mine;

import android.os.Bundle;
import android.os.Handler;

import com.cinderellavip.R;
import com.cinderellavip.adapter.recycleview.SmallVaultConsumeIntegralAdapter;
import com.cinderellavip.adapter.recycleview.XiaohuiRecommentAdapter;
import com.cinderellavip.bean.net.mine.IntegralItem;
import com.cinderellavip.bean.net.mine.IntegralNumber;
import com.cinderellavip.bean.net.mine.IntegralResult;
import com.cinderellavip.http.ApiManager;
import com.cinderellavip.http.BaseResult;
import com.cinderellavip.http.Response;
import com.cinderellavip.toast.SecondDialogUtil;
import com.cinderellavip.util.DataUtil;
import com.google.android.material.appbar.AppBarLayout;
import com.tozzais.baselibrary.http.RxHttp;
import com.tozzais.baselibrary.ui.BaseListFragment;

import java.util.TreeMap;

import androidx.recyclerview.widget.LinearLayoutManager;
import butterknife.BindView;
import butterknife.OnClick;


/**
 * 小金推荐-消费积分
 */
public class SmallVaultConsumeIntegralFragment extends BaseListFragment<IntegralItem> {

    private int type;

    public static SmallVaultConsumeIntegralFragment newInstance(int type){
        SmallVaultConsumeIntegralFragment cartFragment = new SmallVaultConsumeIntegralFragment();
        Bundle bundle = new Bundle();
        bundle.putInt("type",type);
        cartFragment.setArguments(bundle);
        return cartFragment;

    }



    @Override
    public void loadData() {
        super.loadData();
        TreeMap<String, String> hashMap = new TreeMap<>();
        hashMap.put("page", page + "");
        hashMap.put("type", type + "");
        hashMap.put("limit", PageSize + "");
        new RxHttp<BaseResult<IntegralResult>>().send(ApiManager.getService().mine_integral(hashMap),
                new Response<BaseResult<IntegralResult>>(isLoad, mActivity) {
                    @Override
                    public void onSuccess(BaseResult<IntegralResult> result) {
                        setData(result.data.list);
                    }
                });

    }

    @Override
    public void initListener() {
        super.initListener();
        swipeLayout.setEnabled(true);
        mAdapter.getLoadMoreModule().setEnableLoadMore(false);



    }


    @Override
    public void initView(Bundle savedInstanceState) {
        super.initView(savedInstanceState);
        type = getArguments().getInt("type");
        //设置商品
        mRecyclerView.setLayoutManager(new LinearLayoutManager(mActivity));
        mAdapter = new SmallVaultConsumeIntegralAdapter(type);
        mRecyclerView.setAdapter(mAdapter);

        setEmptyView("暂无数据");
    }


}
