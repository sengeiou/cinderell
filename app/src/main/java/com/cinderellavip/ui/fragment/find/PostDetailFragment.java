package com.cinderellavip.ui.fragment.find;

import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.cinderellavip.R;
import com.cinderellavip.adapter.recycleview.CardSaleGoodsAdapter;
import com.cinderellavip.adapter.recycleview.ImagePostAdapter;
import com.cinderellavip.adapter.recycleview.PostCommentAdapter;
import com.cinderellavip.toast.DialogUtil;
import com.cinderellavip.ui.activity.find.ReportActivity;
import com.cinderellavip.util.DataUtil;
import com.cinderellavip.weight.MyRecycleView;
import com.cinderellavip.weight.TopSpace;
import com.tozzais.baselibrary.ui.BaseListFragment;
import com.tozzais.baselibrary.util.DpUtil;

import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.BindView;
import butterknife.OnClick;


public class PostDetailFragment extends BaseListFragment<String> {


    @BindView(R.id.rv_image)
    MyRecycleView rvImage;
    @BindView(R.id.rv_baby)
    RecyclerView rvBaby;
    @BindView(R.id.tv_attention)
    TextView tvAttention;
    @BindView(R.id.et_content)
    EditText etContent;
    @BindView(R.id.iv_collect)
    ImageView ivCollect;

    @Override
    public int setLayout() {
        return R.layout.fragment_find_post_detail;
    }

    @Override
    public void initView(Bundle savedInstanceState) {
        super.initView(savedInstanceState);

        //设置商品
        mRecyclerView.setLayoutManager(new LinearLayoutManager(mActivity));
        mAdapter = new PostCommentAdapter();
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

        CardSaleGoodsAdapter adapter1 = new CardSaleGoodsAdapter();
        rvBaby.setLayoutManager(new LinearLayoutManager(getContext(), RecyclerView.HORIZONTAL, false));
        rvBaby.setAdapter(adapter1);
        adapter1.setNewData(DataUtil.getData(6));
    }

    @Override
    public void initListener() {
        super.initListener();
        swipeLayout.setEnabled(false);
        mAdapter.getLoadMoreModule().setEnableLoadMore(false);

    }


    @OnClick({R.id.tv_attention, R.id.iv_more, R.id.iv_collect})
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
            case R.id.iv_collect:
                ivCollect.setImageResource(isCollect?
                        R.mipmap.icon_post_collect_default:
                        R.mipmap.icon_post_collect_select);
                isCollect = !isCollect;
                break;
        }
    }
    boolean isCollect;
    boolean isAttention;
}
