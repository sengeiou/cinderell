package com.cinderellavip.ui.activity.home;

import android.app.Activity;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.view.View;
import android.widget.TextView;

import com.cinderellavip.R;
import com.cinderellavip.adapter.recycleview.ImageShareAdapter;
import com.cinderellavip.bean.AppletsCode;
import com.cinderellavip.bean.local.ShareImageItem;
import com.cinderellavip.bean.net.goods.GoodsInfo;
import com.cinderellavip.bean.net.goods.GoodsResult;
import com.cinderellavip.bean.net.goods.GroupInfo;
import com.cinderellavip.bean.net.goods.SpikeInfo;
import com.cinderellavip.global.Constant;
import com.cinderellavip.global.GlobalParam;
import com.cinderellavip.http.ApiManager;
import com.cinderellavip.http.BaseResult;
import com.cinderellavip.http.Response;
import com.cinderellavip.toast.CenterDialogUtil;
import com.cinderellavip.toast.SecondDialogUtil;
import com.cinderellavip.util.ClipBoardUtil;
import com.cinderellavip.util.Utils;
import com.tozzais.baselibrary.http.RxHttp;
import com.tozzais.baselibrary.ui.BaseActivity;
import com.tozzais.baselibrary.util.log.LogUtil;
import com.tozzais.baselibrary.util.progress.LoadingUtils;
import com.umeng.socialize.ShareAction;
import com.umeng.socialize.UMShareListener;
import com.umeng.socialize.bean.SHARE_MEDIA;
import com.umeng.socialize.media.UMImage;
import com.umeng.socialize.media.UMMin;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.TreeMap;
import java.util.concurrent.Executors;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.BindView;
import butterknife.OnClick;

public class ShareActivity extends BaseActivity {


    @BindView(R.id.rv_image)
    RecyclerView rvImage;
    @BindView(R.id.tv_goods_title)
    TextView tv_goods_title;
    @BindView(R.id.tv_price)
    TextView tvPrice;

    private String id;
    private ImageShareAdapter imageShareAdapter;
    private List<ShareImageItem> list = new ArrayList<>();
    GoodsResult goodsResult;


    //点击的类型 1 分享素材 2 是分享到朋友圈 3 保存图片
    private int clickType = 0;

    public static void launch(Activity activity, String id) {
        if (!Utils.isFastClick()){
            return;
        }
        Intent intent = new Intent(activity, ShareActivity.class);
        intent.putExtra("id", id);
        activity.startActivity(intent);
    }

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

        executorPool = new ThreadPoolExecutor(CORE_POOL_SIZE, MAX_POOL_SIZE, KEEP_ALIVE_TIME,
                TimeUnit.SECONDS, new LinkedBlockingQueue<Runnable>(),
                Executors.defaultThreadFactory(), new ThreadPoolExecutor.AbortPolicy());
    }


    @Override
    public void loadData() {
        new RxHttp<BaseResult<GoodsResult>>().send(ApiManager.getService().getGoodsDetail(id),
                new Response<BaseResult<GoodsResult>>(isLoad, mActivity) {
                    @Override
                    public void onSuccess(BaseResult<GoodsResult> result) {
                        goodsResult = result.data;
                        GoodsInfo productInfo = goodsResult.product_info;
                        tv_goods_title.setText(productInfo.name);
                        if (productInfo.hasGroup ){
                            GroupInfo group_info = goodsResult.group_info;
                            //团购
                            tvPrice.setText("原价：￥" + group_info.getProduct_price() + "    灰姑娘拼团价：￥" + group_info.getGroup_price());
                        }else if (productInfo.hasSpike ){
                            //秒杀
                            SpikeInfo group_info = goodsResult.spike_info;
                            tvPrice.setText("原价：￥" + group_info.getProductPrice() + "    灰姑娘秒杀价：￥" + group_info.getSpikePrice());
                        }else {
                            tvPrice.setText("原价：￥" + productInfo.getOld_price() + "    灰姑娘会员价：￥" + productInfo.getPrice());
                        }

                        for (String s : productInfo.images) {
                            list.add(new ShareImageItem(true, s));
                        }
                        imageShareAdapter.setNewData(list);
                    }
                });

        getCode();
    }

    private void getCode() {
        TreeMap<String, String> hashMap = new TreeMap<>();
        hashMap.put("scene", GlobalParam.getRecommendCode() + ";1;" + id);
        new RxHttp<BaseResult<AppletsCode>>().send(ApiManager.getService().getAppletsCode(hashMap),
                new Response<BaseResult<AppletsCode>>(isLoad, mActivity) {
                    @Override
                    public void onSuccess(BaseResult<AppletsCode> result) {
                        String path = result.data.url;
                        new Thread(() -> {
                            try {
                                if (!TextUtils.isEmpty(path)) { //网络图片
                                    // 对资源链接
                                    URL url = new URL(path);
                                    //打开输入流
                                    InputStream inputStream = url.openStream();
                                    //对网上资源进行下载转换位图图片
                                    Bitmap bitmap = BitmapFactory.decodeStream(inputStream);
                                    codeBitmap = scaleBitmap(bitmap, 0.4f);
                                    inputStream.close();
                                }
                            } catch (IOException e) {
                                e.printStackTrace();
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        }).start();
                    }
                });
    }

    private Bitmap codeBitmap;

    private Bitmap scaleBitmap(Bitmap origin, float ratio) {
        if (origin == null) {
            return null;
        }
        int width = origin.getWidth();
        int height = origin.getHeight();
        Matrix matrix = new Matrix();
        matrix.preScale(ratio, ratio);
        Bitmap newBM = Bitmap.createBitmap(origin, 0, 0, width, height, matrix, false);
        if (newBM.equals(origin)) {
            return newBM;
        }
        origin.recycle();
        return newBM;
    }


    @OnClick({R.id.rl_share1, R.id.rl_share2, R.id.rl_share3, R.id.rl_share4})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.rl_share1:
                if (goodsResult != null && codeBitmap != null)
                    share();
                break;
            case R.id.rl_share2:
                clickType = 1;
                saveImage();
                break;
            case R.id.rl_share3:
                clickType = 2;
                saveImage();
                break;
            case R.id.rl_share4:
                clickType = 3;
                saveImage();
                break;
        }
    }

    private void saveImage() {
        if (goodsResult != null && codeBitmap != null) {
            List<String> paths = new ArrayList<>();
            for (ShareImageItem shareImageItem : list) {
                if (shareImageItem.isCheck) {
                    paths.add(shareImageItem.path);
                }
            }
            if (paths.size() == 0) {
                tsg("请选择需要下载的图片");
                return;
            }
            number = paths.size();
            loadNumber = 0;
            saveList.clear();
            LoadingUtils.show(mActivity, "图片下载中");
            for (String path : paths) {
                executorPool.execute(new WorkerThread(path));
            }
        } else {
            tsg("操作失败，未获取到小程序码信息");
        }
    }

    //需要上传的数量
    private int number = 0;
    //已经上传的数量
    private int loadNumber = 0;
    //上传成功的文件
    private ArrayList<Uri> saveList = new ArrayList<>();

    public class WorkerThread implements Runnable {
        private String filePath;

        public WorkerThread(String threadName) {
            this.filePath = threadName;
        }

        @Override
        public synchronized void run() {
            loadNumber++;
            try {
                Bitmap mBitmap = null;
                if (!TextUtils.isEmpty(filePath)) { //网络图片
                    // 对资源链接
                    URL url = new URL(filePath);
                    //打开输入流
                    InputStream inputStream = url.openStream();
                    //对网上资源进行下载转换位图图片
                    mBitmap = BitmapFactory.decodeStream(inputStream);
                    inputStream.close();
                }
                if (mBitmap != null) {
                    Bitmap bitmap = mergeBitmap(mBitmap, codeBitmap);
                    saveFile(bitmap);
                }
            } catch (MalformedURLException e) {
                runOnUiThread(() -> {
                    if (loadNumber == number) {
                        LoadingUtils.dismiss();
                        tsg("图片下载失败");
                        number = 0;
                        loadNumber = 0;
                        saveList.clear();
                    }
                });
                LogUtil.e("MalformedURLException"+e.getLocalizedMessage());
                e.printStackTrace();
            } catch (IOException e) {
                runOnUiThread(() -> {
                    if (loadNumber == number) {
                        LoadingUtils.dismiss();
                        tsg("图片下载失败");
                        number = 0;
                        loadNumber = 0;
                        saveList.clear();
                    }
                });
                LogUtil.e("MalformedURLException"+e.getLocalizedMessage());
                e.printStackTrace();
            }

        }
    }

    private Bitmap mergeBitmap(Bitmap firstBitmap, Bitmap secondBitmap) {
        Bitmap bitmap = Bitmap.createBitmap(firstBitmap.getWidth(), firstBitmap.getHeight(), firstBitmap.getConfig());
        Canvas canvas = new Canvas(bitmap);

        canvas.drawBitmap(firstBitmap, new Matrix(), null);

        int width0 = firstBitmap.getWidth();
        int height0 = firstBitmap.getHeight();

        int width1 = secondBitmap.getWidth();
        int height1 = secondBitmap.getHeight();
        canvas.drawBitmap(secondBitmap, width0 - width1, height0 - height1, null);

        return bitmap;
    }


    public void saveFile(Bitmap bm) throws IOException {

        File RootPath = new File(Constant.PATH);
        if (!RootPath.exists()) {
            boolean b = RootPath.mkdir();
            LogUtil.e("创建"+b);
        }
        String fileName = System.currentTimeMillis() + ".jpg";

        File myCaptureFile = new File( Constant.PATH +"/"+ fileName);

        BufferedOutputStream bos = new BufferedOutputStream(new FileOutputStream(myCaptureFile));
        bm.compress(Bitmap.CompressFormat.JPEG, 80, bos);
        bos.flush();
        bos.close();
        //把图片保存后声明这个广播事件通知系统相册有新图片到来
        Intent intent = new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE);
        Uri uri;
        if (Build.VERSION.SDK_INT >= 24) {
//            uri = FileProvider.getUriForFile(this,"com.cinderellavip.provider", myCaptureFile);
            uri = getImageContentUri(this, myCaptureFile);
        } else {
            uri = Uri.fromFile(myCaptureFile);
        }
        intent.setData(uri);
        sendBroadcast(intent);

        saveList.add(uri);
        if (saveList.size() == number) {
            saveShare();
        }

    }
    //解决7.0以上生成uri 无法通过系统分享微信和无法刷新相册的bug
    public  Uri getImageContentUri(Context context, File imageFile) {
        String filePath = imageFile.getAbsolutePath();
        Cursor cursor = context.getContentResolver().query(MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
                new String[] { MediaStore.Images.Media._ID }, MediaStore.Images.Media.DATA + "=? ",
                new String[] { filePath }, null);
        Uri uri = null;

        if (cursor != null) {
            if (cursor.moveToFirst()) {
                int id = cursor.getInt(cursor.getColumnIndex(MediaStore.MediaColumns._ID));
                Uri baseUri = Uri.parse("content://media/external/images/media");
                uri = Uri.withAppendedPath(baseUri, "" + id);
            }
            cursor.close();
        }

        if (uri == null) {
            ContentValues values = new ContentValues();
            values.put(MediaStore.Images.Media.DATA, filePath);
            uri = context.getContentResolver().insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, values);
        }

        return uri;
    }

    private void copyText(String tip){
        if (goodsResult != null){
            GoodsInfo productInfo = goodsResult.product_info;
            String priceText = "";
            if (productInfo.hasGroup ){
                GroupInfo group_info = goodsResult.group_info;
                //团购
                priceText = "\n原价：￥" + group_info.getProduct_price() + "\n灰姑娘拼团价：￥" + group_info.getGroup_price();
            }else if (productInfo.hasSpike ){
                //秒杀
                SpikeInfo group_info = goodsResult.spike_info;
                priceText = "\n原价：￥" + group_info.getProductPrice() + "\n灰姑娘秒杀价：￥" + group_info.getSpikePrice();
            }else {
                priceText = "\n原价：￥" + productInfo.getOld_price() + "\n灰姑娘会员价：￥" + productInfo.getPrice();
            }
            String name = productInfo.name+priceText;
            ClipBoardUtil.copy(mActivity, name,tip);
        }


    }

    private void saveShare(){
        runOnUiThread(() -> {
            LoadingUtils.dismiss();
            if (clickType == 1) {
                copyText("文案信息已复制成功");
                sendMoreImage();
            }else {
                copyText(null);
                CenterDialogUtil.showShare(mActivity, () -> {
                    Intent lan = getPackageManager().getLaunchIntentForPackage("com.tencent.mm");
                    Intent intent1 = new Intent(Intent.ACTION_MAIN);
                    intent1.addCategory(Intent.CATEGORY_LAUNCHER);
                    intent1.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    intent1.setComponent(lan.getComponent());
                    startActivity(intent1);
                });
            }
        });
    }


    private final int CORE_POOL_SIZE = 4;//核心线程数
    private final int MAX_POOL_SIZE = 5;//最大线程数
    private final long KEEP_ALIVE_TIME = 10;//空闲线程超时时间
    private ThreadPoolExecutor executorPool;


    public void sendMoreImage() {
        Intent mulIntent = new Intent(Intent.ACTION_SEND_MULTIPLE);
        mulIntent.putParcelableArrayListExtra(Intent.EXTRA_STREAM, saveList);
        mulIntent.setType("image/jpeg");
        startActivity(Intent.createChooser(mulIntent, "多图文件分享"));
    }

    private void share() {
        copyText(null);
        SecondDialogUtil.showPosterDialog(mContext, goodsResult, codeBitmap, (payString1, bitmap) -> {
            switch (payString1) {
                case "1":
                    shareMINApp(SHARE_MEDIA.WEIXIN, bitmap);
                    break;
                case "2":
                    shareImage(SHARE_MEDIA.WEIXIN_CIRCLE, bitmap);
                    break;
                case "down":
                    tsg("保存成功");
                    break;
            }
        });


    }

    public void shareMINApp(SHARE_MEDIA share_media, Bitmap bitmap) {

        String path = "/pages/index/index?shareType=1&shareUserId=" + GlobalParam.getRecommendCode() + "&shareValue=" + id;
        UMMin umMin = new UMMin("url");
        umMin.setThumb(new UMImage(this, bitmap));
        umMin.setTitle(goodsResult.product_info.name);
        umMin.setDescription(goodsResult.product_info.name);
        umMin.setPath(path);
        umMin.setUserName(Constant.SMALL_APPLICATION_ID);
        new ShareAction(mActivity)
                .withMedia(umMin)
                .setPlatform(SHARE_MEDIA.WEIXIN)
                .setCallback(shareListener).share();
    }

    public void shareImage(SHARE_MEDIA share_media, Bitmap bitmap) {
        UMImage imagelocal = new UMImage(mContext, bitmap);
        imagelocal.setThumb(new UMImage(mContext, bitmap));
        new ShareAction((Activity) mContext).withMedia(imagelocal)
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
