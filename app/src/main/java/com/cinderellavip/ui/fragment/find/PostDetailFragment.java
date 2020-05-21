package com.cinderellavip.ui.fragment.find;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.cinderellavip.R;
import com.cinderellavip.adapter.recycleview.CardSaleGoodsAdapter;
import com.cinderellavip.adapter.recycleview.ImagePostAdapter;
import com.cinderellavip.adapter.recycleview.PostCommentAdapter;
import com.cinderellavip.bean.net.find.DiscussComment;
import com.cinderellavip.bean.net.find.DiscussInfo;
import com.cinderellavip.bean.net.find.DiscussInfoResult;
import com.cinderellavip.bean.net.find.DiscussUser;
import com.cinderellavip.global.ImageUtil;
import com.cinderellavip.http.ApiManager;
import com.cinderellavip.http.BaseResult;
import com.cinderellavip.http.Response;
import com.cinderellavip.listener.OnCommentReplyClickListener;
import com.cinderellavip.listener.SoftKeyBoardListener;
import com.cinderellavip.toast.DialogUtil;
import com.cinderellavip.ui.activity.find.PostDetailActivity;
import com.cinderellavip.ui.activity.find.ReportActivity;
import com.cinderellavip.util.KeyboardUtils;
import com.cinderellavip.weight.CircleImageView;
import com.cinderellavip.weight.MyRecycleView;
import com.cinderellavip.weight.TopSpace;
import com.tozzais.baselibrary.http.RxHttp;
import com.tozzais.baselibrary.ui.BaseListFragment;
import com.tozzais.baselibrary.util.DpUtil;
import com.tozzais.baselibrary.util.toast.ToastCommom;

import java.util.TreeMap;

import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.BindView;
import butterknife.OnClick;


public class PostDetailFragment extends BaseListFragment<DiscussComment> implements OnCommentReplyClickListener {


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
    @BindView(R.id.tv_send)
    TextView tv_send;
    @BindView(R.id.iv_avatar)
    CircleImageView ivAvatar;
    @BindView(R.id.tv_name)
    TextView tvName;
    @BindView(R.id.tv_time)
    TextView tvTime;
    @BindView(R.id.tv_post_name)
    TextView tvPostName;
    @BindView(R.id.tv_post_content)
    TextView tvPostContent;
    @BindView(R.id.tv_comment_number)
    TextView tvCommentNumber;
    @BindView(R.id.ll_goods)
    LinearLayout ll_goods;

    @BindView(R.id.ll_comment)//评价的布局
    LinearLayout ll_comment;
    @BindView(R.id.ll_bottom)//评价的布局
    LinearLayout ll_bottom;

    private String id;

    public static PostDetailFragment newInstance(String id) {
        PostDetailFragment cartFragment = new PostDetailFragment();
        Bundle bundle = new Bundle();
        bundle.putString("id", id);
        cartFragment.setArguments(bundle);
        return cartFragment;

    }

    @Override
    public int setLayout() {
        return R.layout.fragment_find_post_detail;
    }

    @Override
    public void initView(Bundle savedInstanceState) {
        super.initView(savedInstanceState);

        id = getArguments().getString("id");
        //评价
        mRecyclerView.setLayoutManager(new LinearLayoutManager(mActivity));
        mAdapter = new PostCommentAdapter(this);
        mRecyclerView.setAdapter(mAdapter);
        //图片
        rvImage.setLayoutManager(new GridLayoutManager(getContext(), 3));
        TopSpace girdSpace = new TopSpace(DpUtil.dip2px(mActivity, 10));
        rvImage.addItemDecoration(girdSpace);
        imagePostAdapter = new ImagePostAdapter();
        rvImage.setAdapter(imagePostAdapter);
        //商品
        cardSaleGoodsAdapter = new CardSaleGoodsAdapter();
        rvBaby.setLayoutManager(new LinearLayoutManager(getContext(), RecyclerView.HORIZONTAL, false));
        rvBaby.setAdapter(cardSaleGoodsAdapter);

    }

    //图片的适配器
    private ImagePostAdapter imagePostAdapter;
    private CardSaleGoodsAdapter cardSaleGoodsAdapter;

    @Override
    public void loadData() {
        super.loadData();
        TreeMap<String, String> hashMap = new TreeMap<>();
        hashMap.put("limit", "" + PageSize);
        hashMap.put("page", "" + page);
        hashMap.put("id", "" + id);
        new RxHttp<BaseResult<DiscussInfoResult>>().send(ApiManager.getService().getDiscussInfo(hashMap),
                new Response<BaseResult<DiscussInfoResult>>(isLoad, getContext()) {
                    @Override
                    public void onSuccess(BaseResult<DiscussInfoResult> result) {
                        DiscussInfoResult discussesResult = result.data;
                        if (page == DEFAULT_PAGE) {
                            setData(discussesResult);
                        }
                        setData(discussesResult.comments);
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


    private DiscussInfo data;
    private void setData(DiscussInfoResult discussesResult) {
        this.data = discussesResult.info;
        if (mActivity instanceof PostDetailActivity){
            ((PostDetailActivity)mActivity).setBackTitle(data.title);
        }
        imagePostAdapter.setNewData(data.images);
        DiscussUser user = data.user;
        ImageUtil.loadNet(mActivity,ivAvatar,user.avatar);
        tvName.setText(user.user_name);
        tvTime.setText(user.create_at);
        setAttention(user.collect);
        setCollect(data.collect);
        tvPostName.setText(data.topic);
        tvPostContent.setText(data.content);
        if (data.products == null || data.products.size() ==0){
            ll_goods.setVisibility(View.GONE);
        }else {
            ll_goods.setVisibility(View.VISIBLE);
            cardSaleGoodsAdapter.setNewData(data.products);
        }

        tvCommentNumber.setText("评价 ("+discussesResult.total+")");
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
    private void setCollect(boolean isCollect){
        ivCollect.setImageResource(!isCollect ?
                R.mipmap.icon_post_collect_default :
                R.mipmap.icon_post_collect_select);
    }


    @Override
    public void initListener() {
        super.initListener();
        swipeLayout.setEnabled(false);
        mAdapter.getLoadMoreModule().setEnableLoadMore(false);

        SoftKeyBoardListener.setListener(mActivity, new SoftKeyBoardListener.OnSoftKeyBoardChangeListener() {
            @Override
            public void keyBoardShow(int height) {
                ll_comment.setVisibility(View.VISIBLE);
                ll_bottom.setVisibility(View.GONE);
            }

            @Override
            public void keyBoardHide(int height) {
                ll_comment.setVisibility(View.GONE);
                ll_bottom.setVisibility(View.VISIBLE);
            }
        });

    }


    @OnClick({R.id.tv_attention, R.id.iv_more, R.id.iv_collect, R.id.tv_send, R.id.tv_comment})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.tv_attention:
                if (data !=null)
                    collect(data.user.id+"","4");
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
                break;
            case R.id.iv_collect:
                if (data !=null)
                collect(id+"","3");
                break;
            case R.id.tv_send:
                String content = etContent.getText().toString().trim();
                if (!TextUtils.isEmpty(content)){
                    etContent.setText("");
                    KeyboardUtils.hideKeyboard(etContent);
                    if (!TextUtils.isEmpty(comment_id)){
                        //回复评论
                        reply(content);
                    }else {
                        //直接评价
                        comment(content);
                    }
                }
                break;
            case R.id.tv_comment:
                onSure("","","说点什么吧");
                break;
        }
    }

    //收藏
    private void collect(String id,String type){
        TreeMap<String, String> hashMap = new TreeMap<>();
        hashMap.put("id", id + "");
        hashMap.put("type",   type);
        new RxHttp<BaseResult>().send(ApiManager.getService().getCollect(hashMap),
                new Response<BaseResult>(getContext()) {
                    @Override
                    public void onSuccess(BaseResult result) {
                        if (type.equals("4")){
                            data.user.collect = !data.user.collect;
                            if (data.user.collect){
                                tsg("关注成功");
                            }else {
                                tsg("取消关注");
                            }
                            setAttention(data.user.collect);
                        }else if (type.equals("3")){
                            data.collect = !data.collect;
                            if (data.collect){
                                tsg("收藏成功");
                            }else {
                                tsg("取消收藏");
                            }
                            setCollect(data.collect);
                        }

                    }
                });
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

    @Override
    public void onSure(String id, String reply_id, String hint) {
        this.comment_id = id;
        this.reply_id = reply_id;
        etContent.setHint(hint);
        KeyboardUtils.showKeyboard(etContent);
    }
    private String comment_id;
    private String reply_id;


    //回复评论
    private void reply(String content) {

        TreeMap<String, String> hashMap = new TreeMap<>();
        hashMap.put("id", comment_id + "");
        hashMap.put("reply_id", reply_id + "");
        hashMap.put("content", content + "");
        new RxHttp<BaseResult>().send(ApiManager.getService().discussReply(hashMap),
                new Response<BaseResult>(getContext()) {
                    @Override
                    public void onSuccess(BaseResult result) {

                        tsg("评价成功");
                        onRefresh();
                    }


                });
    }

    //评价
    private void comment(String content) {


        TreeMap<String, String> hashMap = new TreeMap<>();
        hashMap.put("id", id + "");
        hashMap.put("content", content + "");
        new RxHttp<BaseResult>().send(ApiManager.getService().commentReply(hashMap),
                new Response<BaseResult>(getContext()) {

                    @Override
                    public void onSuccess(BaseResult result) {

                        tsg("评价成功");
                        onRefresh();
                    }

                });
    }

}
