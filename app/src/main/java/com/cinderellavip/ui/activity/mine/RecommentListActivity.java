package com.cinderellavip.ui.activity.mine;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.cinderellavip.R;
import com.cinderellavip.bean.net.mine.MineInviterNumber;
import com.cinderellavip.global.GlobalParam;
import com.cinderellavip.global.ShareConstant;
import com.cinderellavip.http.ApiManager;
import com.cinderellavip.http.BaseResult;
import com.cinderellavip.http.Response;
import com.cinderellavip.toast.SecondDialogUtil;
import com.cinderellavip.util.QRCodeUtil;
import com.cinderellavip.util.Utils;
import com.tozzais.baselibrary.http.RxHttp;
import com.tozzais.baselibrary.ui.BaseActivity;
import com.tozzais.baselibrary.util.DpUtil;
import com.umeng.socialize.ShareAction;
import com.umeng.socialize.bean.SHARE_MEDIA;
import com.umeng.socialize.media.UMImage;
import com.umeng.socialize.media.UMWeb;

import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;
import butterknife.BindView;
import butterknife.OnClick;


/**
 * Created by Administrator on 2016/9/8.
 */
public class RecommentListActivity extends BaseActivity {


    @BindView(R.id.tv_mine_code)
    TextView tvMineCode;
    @BindView(R.id.iv_code)
    ImageView ivCode;
    @BindView(R.id.tv_invite)
    TextView tvInvite;
    @BindView(R.id.tv_total_number)
    TextView tvTotalNumber;
    @BindView(R.id.tv_direct_number)
    TextView tvDirectNumber;
    @BindView(R.id.ll_direct)
    LinearLayout llDirect;
    @BindView(R.id.tv_indirect_number)
    TextView tvIndirectNumber;
    @BindView(R.id.swipeLayout)
    SwipeRefreshLayout swipeLayout;

    public static void launch(Context from) {
        if (!Utils.isFastClick()){
            return;
        }
        Intent intent = new Intent(from, RecommentListActivity.class);
        from.startActivity(intent);
    }


    @Override
    public void initView(Bundle savedInstanceState) {
        setBackTitle("小灰推荐");
        String recommendCode = GlobalParam.getRecommendCode();
        String share = ShareConstant.REGISTER + recommendCode;
        Bitmap qrCode = QRCodeUtil.createQRCode(share, DpUtil.dip2px(mActivity, 150));
        ivCode.setImageBitmap(qrCode);
        tvMineCode.setText(recommendCode);
    }


    @Override
    public void loadData() {
        if (!isLoad)showProress();
        new RxHttp<BaseResult<MineInviterNumber>>().send(ApiManager.getService().mine_inviter_number(),
                new Response<BaseResult<MineInviterNumber>>(isLoad, mActivity) {
                    @Override
                    public void onSuccess(BaseResult<MineInviterNumber> result) {
                        showContent();
                        MineInviterNumber data = result.data;
                        tvTotalNumber.setText("好友总人数\n" + data.total + "人");
                        tvDirectNumber.setText(data.direct + "人");
                        tvIndirectNumber.setText(data.indirect + "人");
                    }

                    @Override
                    public void onErrorShow(String s) {
                        showError(s);
                    }

                    @Override
                    public void onCompleted() {
                        super.onCompleted();
                        swipeLayout.setRefreshing(false);
                    }
                });

    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_xiaohui_recommed;
    }

    @Override
    public void initListener() {
        super.initListener();
        swipeLayout.setOnRefreshListener(this::loadData);
    }

    @OnClick({R.id.tv_invite, R.id.ll_direct})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.tv_invite:
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
                break;
            case R.id.ll_direct:
                FriendListActivity.launch(mActivity);
                break;
        }
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
