package com.cinderellavip.ui.fragment.find;

import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.cinderellavip.R;
import com.cinderellavip.adapter.recycleview.CardSaleGoodsAdapter;
import com.cinderellavip.adapter.recycleview.FindAdapter;
import com.cinderellavip.adapter.recycleview.HomeGoodsAdapter;
import com.cinderellavip.adapter.recycleview.ImagePostAdapter;
import com.cinderellavip.adapter.recycleview.PostCommentAdapter;
import com.cinderellavip.toast.DialogUtil;
import com.cinderellavip.ui.activity.find.ReportActivity;
import com.cinderellavip.util.DataUtil;
import com.cinderellavip.weight.GirdSpaceStag;
import com.cinderellavip.weight.MyRecycleView;
import com.cinderellavip.weight.TopSpace;
import com.tozzais.baselibrary.ui.BaseListFragment;
import com.tozzais.baselibrary.util.DpUtil;

import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;
import butterknife.BindView;
import butterknife.OnClick;


public class TopicDetailFragment extends BaseListFragment<String> {


    @BindView(R.id.rv_image)
    RecyclerView rvImage;
    @BindView(R.id.tv_attention)
    TextView tvAttention;

    @Override
    public int setLayout() {
        return R.layout.fragment_find_topic_detail;
    }

    @Override
    public void initView(Bundle savedInstanceState) {
        super.initView(savedInstanceState);

        //设置商品
        GirdSpaceStag girdSpace = new GirdSpaceStag(DpUtil.dip2px(mActivity, 10),2);
        mRecyclerView.setLayoutManager(new StaggeredGridLayoutManager(2,StaggeredGridLayoutManager.VERTICAL));
        mRecyclerView.addItemDecoration(girdSpace);
        mAdapter = new FindAdapter();
        mRecyclerView.setAdapter(mAdapter);



    }

    @Override
    public void loadData() {
        //这里只有通过Handler 已经到底啦 才会出来
        new Handler().postDelayed(() -> {
            setData(DataUtil.getData(8));
        }, 100);

        rvImage.setLayoutManager(new GridLayoutManager(getContext(), 3));
        TopSpace girdSpace = new TopSpace(DpUtil.dip2px(mActivity, 10));
        rvImage.addItemDecoration(girdSpace);
        ImagePostAdapter adapter = new ImagePostAdapter();
        rvImage.setAdapter(adapter);
        adapter.setNewData(DataUtil.getData(5));


    }

    @Override
    public void initListener() {
        super.initListener();
        swipeLayout.setEnabled(false);
        mAdapter.getLoadMoreModule().setEnableLoadMore(false);

    }


    @OnClick({R.id.tv_attention, R.id.iv_more})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.tv_attention:
                if (isAttention){
                    tvAttention.setText("关注");
                    tvAttention.setBackgroundResource(R.drawable.shape_red50);
                }else {
                    tvAttention.setText("已关注");
                    tvAttention.setBackgroundResource(R.drawable.shape_gray50_cccccc);
                }
                isAttention = !isAttention;
                break;
            case R.id.iv_more:
                DialogUtil.showReportDialog(mActivity,payString -> {
                    if ("0".equals(payString)){
                        ReportActivity.launch(mActivity);
                    }else {
                        tsg("已拉黑");
                    }
                });
                break;
        }
    }
    boolean isAttention;
}
