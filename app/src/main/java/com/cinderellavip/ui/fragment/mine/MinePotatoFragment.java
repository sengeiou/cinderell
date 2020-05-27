package com.cinderellavip.ui.fragment.mine;

import android.os.Bundle;
import android.widget.TextView;

import com.cinderellavip.R;
import com.cinderellavip.adapter.recycleview.MinePotatoAdapter;
import com.cinderellavip.bean.potato.MinePotatoItem;
import com.cinderellavip.bean.potato.MinePotatoResult;
import com.cinderellavip.http.ApiManager;
import com.cinderellavip.http.BaseResult;
import com.cinderellavip.http.Response;
import com.google.android.material.appbar.AppBarLayout;
import com.tozzais.baselibrary.http.RxHttp;
import com.tozzais.baselibrary.ui.BaseListFragment;
import com.tozzais.baselibrary.util.log.LogUtil;

import java.util.TreeMap;

import androidx.recyclerview.widget.LinearLayoutManager;
import butterknife.BindView;


public class MinePotatoFragment extends BaseListFragment<MinePotatoItem> {


    @BindView(R.id.appbar)
    AppBarLayout appbar;
    @BindView(R.id.tv_num)
    TextView tvNum;


    @Override
    public void initView(Bundle savedInstanceState) {
        super.initView(savedInstanceState);

        //设置商品
        mRecyclerView.setLayoutManager(new LinearLayoutManager(mActivity));
        mAdapter = new MinePotatoAdapter();
        mRecyclerView.setAdapter(mAdapter);

    }


    @Override
    public int setLayout() {
        return R.layout.fragment_mine_potato;
    }


    @Override
    public void loadData() {
        super.loadData();
        TreeMap<String, String> hashMap = new TreeMap<>();
        hashMap.put("limit", "" + PageSize);
        hashMap.put("page", "" + page);
        new RxHttp<BaseResult<MinePotatoResult>>().send(ApiManager.getService().minePotato(hashMap),
                new Response<BaseResult<MinePotatoResult>>(isLoad, getContext()) {
                    @Override
                    public void onSuccess(BaseResult<MinePotatoResult> result) {
                        if (page == DEFAULT_PAGE){
                            tvNum.setText(result.data.num+"");
                        }
                        setData(result.data.list);
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

        //SwipeRefreshLayout和CoordinatorLayout滑动冲突
        appbar.addOnOffsetChangedListener((AppBarLayout.BaseOnOffsetChangedListener) (appBarLayout, i) -> {
            LogUtil.e("totalDy" + i);
            if (i >= 0) {
                swipeLayout.setEnabled(true); //当滑动到顶部的时候开启
            } else {
                swipeLayout.setEnabled(false); //否则关闭
            }
        });


    }


}
