package com.cinderellavip.ui.fragment.mine;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.cinderellavip.R;
import com.cinderellavip.adapter.recycleview.XiaohuiRecommentAdapter;
import com.cinderellavip.bean.net.mine.MineInviteItem;
import com.cinderellavip.bean.net.mine.MineInviterResult;
import com.cinderellavip.global.GlobalParam;
import com.cinderellavip.http.ApiManager;
import com.cinderellavip.http.BaseResult;
import com.cinderellavip.http.Response;
import com.cinderellavip.toast.SecondDialogUtil;
import com.cinderellavip.util.QRCodeUtil;
import com.google.android.material.appbar.AppBarLayout;
import com.tozzais.baselibrary.http.RxHttp;
import com.tozzais.baselibrary.ui.BaseListFragment;
import com.tozzais.baselibrary.util.DpUtil;
import com.umeng.socialize.ShareAction;
import com.umeng.socialize.bean.SHARE_MEDIA;
import com.umeng.socialize.media.UMImage;
import com.umeng.socialize.media.UMWeb;

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
    @BindView(R.id.iv_code)
    ImageView iv_code;
    @BindView(R.id.tv_mine_code)
    TextView tv_mine_code;

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

        String recommendCode = GlobalParam.getRecommendCode();
        Bitmap qrCode = QRCodeUtil.createQRCode(recommendCode, DpUtil.dip2px(mActivity,150));
        iv_code.setImageBitmap(qrCode);
        tv_mine_code.setText(recommendCode);
    }


    @OnClick(R.id.tv_invite)
    public void onClick() {
        SecondDialogUtil.showRecommendDialog(mActivity, (payString1, bitmap) -> {
            switch (payString1){
                case "1":
                    shareTextAndImage(SHARE_MEDIA.WEIXIN);
                    break;
                case "2":
                    shareTextAndImage(SHARE_MEDIA.WEIXIN_CIRCLE);
                    break;
                case "down":
                    tsg("保存成功");
                    break;
            }

        });
    }
    public void shareTextAndImage(SHARE_MEDIA share_media) {
        String url = "http://api.huiguniangvip.com/h5/#/register/" + GlobalParam.getRecommendCode();
        UMWeb web = new UMWeb(url);
        web.setTitle("免费加入灰姑娘");
        String content = GlobalParam.getUserBean().username+"，邀请您加入灰姑娘，省钱、赚钱～";
        UMImage thumb =  new UMImage(mActivity, R.drawable.logo1);
        web.setThumb(thumb);
        web.setDescription(content);
        new ShareAction(mActivity).withMedia(web)
                .setPlatform(share_media)
                .setCallback(null).share();
    }
}
