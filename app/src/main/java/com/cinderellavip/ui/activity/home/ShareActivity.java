package com.cinderellavip.ui.activity.home;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.cinderellavip.R;
import com.cinderellavip.adapter.recycleview.ImageShareAdapter;
import com.cinderellavip.bean.local.ShareImageItem;
import com.cinderellavip.bean.net.goods.GoodsInfo;
import com.cinderellavip.bean.net.goods.GoodsResult;
import com.cinderellavip.http.ApiManager;
import com.cinderellavip.http.BaseResult;
import com.cinderellavip.http.Response;
import com.cinderellavip.toast.CenterDialogUtil;
import com.cinderellavip.toast.SecondDialogUtil;
import com.tozzais.baselibrary.http.RxHttp;
import com.tozzais.baselibrary.ui.CheckPermissionActivity;
import com.umeng.socialize.ShareAction;
import com.umeng.socialize.UMShareListener;
import com.umeng.socialize.bean.SHARE_MEDIA;
import com.umeng.socialize.media.UMImage;

import java.util.ArrayList;
import java.util.List;

import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.BindView;
import butterknife.OnClick;

public class ShareActivity extends CheckPermissionActivity {


    @BindView(R.id.rv_image)
    RecyclerView rvImage;
    @BindView(R.id.tv_goods_title)
    TextView tv_goods_title;
    @BindView(R.id.tv_price)
    TextView tvPrice;

    public static void launch(Activity activity, String id) {
        Intent intent = new Intent(activity, ShareActivity.class);
        intent.putExtra("id", id);
        activity.startActivity(intent);
    }

    private String id;


    @Override
    public int getLayoutId() {
        return R.layout.activity_share;
    }

    @Override
    public void initView(Bundle savedInstanceState) {
        setBackTitle("商品分享");
        id = getIntent().getStringExtra("id");

        rvImage.setLayoutManager(new GridLayoutManager(mActivity, 4));
        imageShareAdapter = new ImageShareAdapter();
        rvImage.setAdapter(imageShareAdapter);
    }

    private ImageShareAdapter imageShareAdapter;

    GoodsResult goodsResult;

    @Override
    public void loadData() {
        new RxHttp<BaseResult<GoodsResult>>().send(ApiManager.getService().getGoodsDetail(id),
                new Response<BaseResult<GoodsResult>>(isLoad, mActivity) {
                    @Override
                    public void onSuccess(BaseResult<GoodsResult> result) {
                        goodsResult = result.data;
                        GoodsInfo productInfo = goodsResult.product_info;
                        tv_goods_title.setText(productInfo.name);
                        tvPrice.setText("原件：￥"+productInfo.getOld_price()+"    灰姑娘会员价：￥"+productInfo.getPrice());
                        List<ShareImageItem> list = new ArrayList<>();
                        for (String s:productInfo.images){
                            list.add(new ShareImageItem(true,s));
                        }
                        imageShareAdapter.setNewData(list);

                    }
                });
    }


    @Override
    public void permissionGranted() {

    }

    @OnClick({R.id.rl_share1, R.id.rl_share2, R.id.rl_share3, R.id.rl_share4})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.rl_share1:
                share();
                break;
            case R.id.rl_share2:
                Intent textIntent = new Intent(Intent.ACTION_SEND);
                textIntent.setType("text/plain");
                textIntent.putExtra(Intent.EXTRA_TEXT, "这是一段分享的文字");
                startActivity(Intent.createChooser(textIntent, "分享"));
                break;
            case R.id.rl_share3:
                if (goodsResult != null)
                CenterDialogUtil.showShare(mActivity, () -> {

                });
                break;
            case R.id.rl_share4:

                break;
        }
    }

    private void share() {
        SecondDialogUtil.showPosterDialog(mContext,goodsResult, (payString1, bitmap) -> {
            switch (payString1) {
                case "1":
                    shareImage(SHARE_MEDIA.WEIXIN,bitmap);
                    break;
                case "2":
                    shareImage(SHARE_MEDIA.WEIXIN_CIRCLE,bitmap);
                    break;
                case "down":
                    tsg("保存成功");
                    break;
            }
        });


    }

    public void shareImage(SHARE_MEDIA share_media, Bitmap bitmap) {
        UMImage imagelocal = new UMImage(mContext, bitmap);
        imagelocal.setThumb(new UMImage(mContext, bitmap));
        new ShareAction((Activity) mContext).withMedia(imagelocal )
                .setPlatform(share_media)
                .setCallback(shareListener).share();
    }
    private UMShareListener shareListener = new UMShareListener() {
        @Override
        public void onStart(SHARE_MEDIA platform) {
        }

        @Override
        public void onResult(SHARE_MEDIA platform) {
        }

        @Override
        public void onError(SHARE_MEDIA platform, Throwable t) {
        }

        @Override
        public void onCancel(SHARE_MEDIA platform) {
        }
    };
}
