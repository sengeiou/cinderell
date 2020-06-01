package com.cinderellavip.ui.fragment.find;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.cinderellavip.R;
import com.cinderellavip.adapter.recycleview.FindAdapter;
import com.cinderellavip.adapter.recycleview.ImagePostAdapter;
import com.cinderellavip.bean.net.find.DiscussInfo;
import com.cinderellavip.bean.net.find.DiscussUser;
import com.cinderellavip.bean.net.find.FindItem;
import com.cinderellavip.bean.net.find.TopicInfoResult;
import com.cinderellavip.global.ImageUtil;
import com.cinderellavip.http.ApiManager;
import com.cinderellavip.http.BaseResult;
import com.cinderellavip.http.Response;
import com.cinderellavip.toast.DialogUtil;
import com.cinderellavip.ui.activity.find.ReportActivity;
import com.cinderellavip.ui.activity.find.TopicDetailActivity;
import com.cinderellavip.weight.CircleImageView;
import com.cinderellavip.weight.GirdSpaceStag;
import com.cinderellavip.weight.TopSpace;
import com.google.android.material.appbar.AppBarLayout;
import com.tozzais.baselibrary.http.RxHttp;
import com.tozzais.baselibrary.ui.BaseListFragment;
import com.tozzais.baselibrary.util.DpUtil;

import java.util.TreeMap;

import androidx.core.widget.NestedScrollView;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;
import butterknife.BindView;
import butterknife.OnClick;


public class TopicDetailFragment extends BaseListFragment<FindItem> {


//    @BindView(R.id.appbar)
//    AppBarLayout appbar;

    @BindView(R.id.scrollView)
    NestedScrollView scrollView;

    @BindView(R.id.rv_image)
    RecyclerView rvImage;
    @BindView(R.id.tv_attention)
    TextView tvAttention;
    @BindView(R.id.iv_avatar)
    CircleImageView ivAvatar;
    @BindView(R.id.tv_name)
    TextView tvName;
    @BindView(R.id.tv_time)
    TextView tvTime;
    @BindView(R.id.tv_topic_title)
    TextView tvTopicTitle;
    @BindView(R.id.tv_content)
    TextView tvContent;

    private String id;

    public static TopicDetailFragment newInstance(String id) {
        TopicDetailFragment cartFragment = new TopicDetailFragment();
        Bundle bundle = new Bundle();
        bundle.putString("id", id);
        cartFragment.setArguments(bundle);
        return cartFragment;

    }

    @Override
    public int setLayout() {
        return R.layout.fragment_find_topic_detail;
    }

    @Override
    public void initView(Bundle savedInstanceState) {
        super.initView(savedInstanceState);

        id = getArguments().getString("id");
        //设置商品
        GirdSpaceStag girdSpace = new GirdSpaceStag(DpUtil.dip2px(mActivity, 10), 2);
        mRecyclerView.setLayoutManager(new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL));
        mRecyclerView.addItemDecoration(girdSpace);
        mAdapter = new FindAdapter();
        mRecyclerView.setAdapter(mAdapter);
        //
        rvImage.setLayoutManager(new GridLayoutManager(getContext(), 3));
        TopSpace girdSpace1 = new TopSpace(DpUtil.dip2px(mActivity, 10));
        rvImage.addItemDecoration(girdSpace1);
        imagePostAdapter = new ImagePostAdapter();
        rvImage.setAdapter(imagePostAdapter);


    }

    @Override
    public void loadData() {
        super.loadData();
        TreeMap<String, String> hashMap = new TreeMap<>();
        hashMap.put("id", "" + id);
        new RxHttp<BaseResult<TopicInfoResult>>().send(ApiManager.getService().getTopicInfo(hashMap),
                new Response<BaseResult<TopicInfoResult>>(isLoad, getContext()) {
                    @Override
                    public void onSuccess(BaseResult<TopicInfoResult> result) {
                        TopicInfoResult discussesResult = result.data;
                        if (page == DEFAULT_PAGE) {
                            setData(discussesResult);
                        }
                        setData(discussesResult.discusses);

                    }

                    @Override
                    public void onErrorShow(String s) {
                        showError(s);
                    }
                });


    }


    private DiscussInfo data;
    //图片的适配器
    private ImagePostAdapter imagePostAdapter;

    private void setData(TopicInfoResult discussesResult) {
        this.data = discussesResult.info;
        if (mActivity instanceof TopicDetailActivity) {
            ((TopicDetailActivity) mActivity).setBackTitle(data.title);
        }
        imagePostAdapter.setNewData(data.images);
        DiscussUser user = data.user;
        ImageUtil.loadNet(mActivity, ivAvatar, user.avatar);
        tvName.setText(user.user_name);
        tvTime.setText(user.create_at);
        setAttention(user.collect);
        tvTopicTitle.setText(data.title);
        tvContent.setText(data.content);
    }

    private void setAttention(boolean isCollect){
        if (!isCollect) {
            tvAttention.setText("关注");
            tvAttention.setBackgroundResource(R.drawable.shape_red50);
        } else {
            tvAttention.setText("已关注");
            tvAttention.setBackgroundResource(R.drawable.shape_gray50_cccccc);
        }
    }


    @Override
    public void initListener() {
        if (swipeLayout != null)
            swipeLayout.setOnRefreshListener(this::onRefresh);

//        appbar.addOnOffsetChangedListener((AppBarLayout.BaseOnOffsetChangedListener) (appBarLayout, i) -> {
//            if (i >= 0) {
//                swipeLayout.setEnabled(true); //当滑动到顶部的时候开启
//            } else {
//                swipeLayout.setEnabled(false); //否则关闭
//            }
//        });
        scrollView.setOnScrollChangeListener((View.OnScrollChangeListener) (v, scrollX, scrollY, oldScrollX, oldScrollY) -> {
            swipeLayout.setEnabled(scrollY<=0); //当滑动到顶部的时候开启
        });

    }


    @OnClick({R.id.tv_attention, R.id.iv_more})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.tv_attention:
                if (data !=null)
                    collect(data.user.id+"");
                break;
            case R.id.iv_more:
                if (data != null)
                    DialogUtil.showReportDialog(mActivity,data.user.shield, payString -> {
                        if ("0".equals(payString)) {
                            ReportActivity.launch(mActivity,data.user.id+"","2");
                        } else {
                            shield(data.user.id+"");
                        }
                    });
//
                break;
        }
    }

    //拉黑
    private void shield(String user_id){
        TreeMap<String, String> hashMap = new TreeMap<>();
        hashMap.put("user_id", user_id + "");
        new RxHttp<BaseResult>().send(ApiManager.getService().getShield(hashMap),
                new Response<BaseResult>( getContext()) {
                    @Override
                    public void onSuccess(BaseResult result) {
                        data.user.shield = !data.user.shield;
                        tsg(result.message);
                    }
                });
    }
    //收藏
    private void collect(String id){
        TreeMap<String, String> hashMap = new TreeMap<>();
        hashMap.put("id", id + "");
        hashMap.put("type","4");
        new RxHttp<BaseResult>().send(ApiManager.getService().getCollect(hashMap),
                new Response<BaseResult>(getContext()) {
                    @Override
                    public void onSuccess(BaseResult result) {
                        data.user.collect = !data.user.collect;
                        if (data.user.collect){
                            tsg("关注成功");
                        }else {
                            tsg("取消关注");
                        }
                        setAttention(data.user.collect);
                    }
                });
    }

}
