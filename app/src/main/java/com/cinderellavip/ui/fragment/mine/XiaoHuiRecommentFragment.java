package com.cinderellavip.ui.fragment.mine;

import android.os.Bundle;

import com.cinderellavip.adapter.recycleview.XiaohuiRecommentAdapter;
import com.cinderellavip.bean.net.mine.MineInviteItem;
import com.cinderellavip.bean.net.mine.MineInviterResult;
import com.cinderellavip.http.ApiManager;
import com.cinderellavip.http.BaseResult;
import com.cinderellavip.http.Response;
import com.tozzais.baselibrary.http.RxHttp;
import com.tozzais.baselibrary.ui.BaseListFragment;

import java.util.TreeMap;

import androidx.recyclerview.widget.LinearLayoutManager;


/**
 * 小金推荐
 */
public class XiaoHuiRecommentFragment extends BaseListFragment<MineInviteItem> {


    @Override
    public void loadData() {
        super.loadData();
        PageSize = 20;
        TreeMap<String, String> hashMap = new TreeMap<>();
        hashMap.put("page", page+"");
        hashMap.put("limit", PageSize+"");
        new RxHttp<BaseResult<MineInviterResult>>().send(ApiManager.getService().mine_inviter(hashMap),
                new Response<BaseResult<MineInviterResult>>(isLoad,mActivity) {
                    @Override
                    public void onSuccess(BaseResult<MineInviterResult> result) {
                        MineInviterResult data = result.data;
                        setData(data.list);
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
    public void initView(Bundle savedInstanceState) {
        super.initView(savedInstanceState);
        //设置商品
        mRecyclerView.setLayoutManager(new LinearLayoutManager(mActivity));
        mAdapter = new XiaohuiRecommentAdapter();
        mRecyclerView.setAdapter(mAdapter);

        setEmptyView("暂无直接好友~");
    }


}
