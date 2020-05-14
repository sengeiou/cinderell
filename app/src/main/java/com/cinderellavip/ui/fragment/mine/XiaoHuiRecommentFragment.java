package com.cinderellavip.ui.fragment.mine;

import android.os.Bundle;
import android.os.Handler;
import android.widget.TextView;

import com.cinderellavip.R;
import com.cinderellavip.adapter.recycleview.XiaohuiRecommentAdapter;
import com.cinderellavip.bean.net.mine.MineInviteItem;
import com.cinderellavip.bean.net.mine.MineInviterResult;
import com.cinderellavip.bean.net.mine.WithDrawHistoryResult;
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
 * 小金推荐
 */
public class XiaoHuiRecommentFragment extends BaseListFragment<MineInviteItem> {


    @BindView(R.id.appbar)
    AppBarLayout appbar;

    @BindView(R.id.tab_label)
    TextView tab_label;

    @Override
    public int setLayout() {
        return R.layout.fragment_xiaohui_recommed;
    }

    @Override
    public void loadData() {
        TreeMap<String, String> hashMap = new TreeMap<>();
        hashMap.put("page", page+"");
        hashMap.put("limit", PageSize+"");
        new RxHttp<BaseResult<MineInviterResult>>().send(ApiManager.getService().mine_inviter(hashMap),
                new Response<BaseResult<MineInviterResult>>(isLoad,mActivity) {
                    @Override
                    public void onSuccess(BaseResult<MineInviterResult> result) {
                        MineInviterResult data = result.data;
                        if (page == DEFAULT_PAGE) {
                            tab_label.setText("我推荐的好友（"+data.total+"）");
                        }
                        setData(data.list);
                    }
                });
    }

    @Override
    public void initListener() {
        super.initListener();
        swipeLayout.setEnabled(true);
        mAdapter.getLoadMoreModule().setEnableLoadMore(false);

        //SwipeRefreshLayout和CoordinatorLayout滑动冲突
        appbar.addOnOffsetChangedListener((AppBarLayout.BaseOnOffsetChangedListener) (appBarLayout, i) -> {
            if (i >= 0) {
                swipeLayout.setEnabled(true); //当滑动到顶部的时候开启
            } else {
                swipeLayout.setEnabled(false); //否则关闭
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
    }


    @OnClick(R.id.tv_invite)
    public void onClick() {
        SecondDialogUtil.showRecommendDialog(mActivity, (payString1, bitmap) -> {
            switch (payString1){
                case "1":
                    tsg("分享微信");
                    break;
                case "2":
                    tsg("分享朋友圈");
                    break;
                case "down":
                    tsg("保存成功");
                    break;
            }

        });
    }
}
