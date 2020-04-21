package com.cinderellavip.ui.fragment.mine;

import android.os.Bundle;
import android.os.Handler;

import com.cinderellavip.R;
import com.cinderellavip.adapter.recycleview.XiaohuiRecommentAdapter;
import com.cinderellavip.toast.SecondDialogUtil;
import com.cinderellavip.util.DataUtil;
import com.google.android.material.appbar.AppBarLayout;
import com.tozzais.baselibrary.ui.BaseListFragment;

import androidx.recyclerview.widget.LinearLayoutManager;
import butterknife.BindView;
import butterknife.OnClick;


/**
 * 小金推荐
 */
public class XiaoHuiRecommentFragment extends BaseListFragment<String> {


    @BindView(R.id.appbar)
    AppBarLayout appbar;

    @Override
    public int setLayout() {
        return R.layout.fragment_small_vault;
    }

    @Override
    public void loadData() {

        //这里只有通过Handler 已经到底啦 才会出来
        new Handler().postDelayed(() -> {
            setData(DataUtil.getData(18));
        }, 100);
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
