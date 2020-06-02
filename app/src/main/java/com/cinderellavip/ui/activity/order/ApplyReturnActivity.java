package com.cinderellavip.ui.activity.order;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;


import com.cinderellavip.R;
import com.cinderellavip.adapter.listview.OrderCommentAdapter;
import com.cinderellavip.adapter.recycleview.HorizontalAdapter;
import com.cinderellavip.adapter.recycleview.MineOrderCommentImageAdpter;
import com.cinderellavip.adapter.recycleview.MineOrderRefundImageAdpter;
import com.cinderellavip.bean.OrderCommentImageItemBean;
import com.cinderellavip.bean.ReturnReasonItem;
import com.cinderellavip.bean.UploadImageResult;
import com.cinderellavip.bean.eventbus.OrderRefund;
import com.cinderellavip.bean.local.PublishImageBean;
import com.cinderellavip.bean.net.PhoneResult;
import com.cinderellavip.bean.net.mine.MessageItem;
import com.cinderellavip.bean.net.order.OrderGoodsInfo;
import com.cinderellavip.global.Constant;
import com.cinderellavip.global.GlobalParam;
import com.cinderellavip.http.ApiManager;
import com.cinderellavip.http.BaseResult;
import com.cinderellavip.http.ListResult;
import com.cinderellavip.http.Response;
import com.cinderellavip.imagepick.CustomImgPickerPresenter;
import com.cinderellavip.listener.OnDoublePositionClickListener;
import com.cinderellavip.listener.OnPublishImageListener;
import com.cinderellavip.toast.CenterDialogUtil;
import com.cinderellavip.toast.ReturnUtil;
import com.cinderellavip.ui.BigImageActivity;
import com.cinderellavip.util.PartMapUtils;
import com.cinderellavip.util.PhotoUtils;
import com.cinderellavip.util.Utils;
import com.cinderellavip.weight.GirdSpaceRight;
import com.tozzais.baselibrary.http.RxHttp;
import com.tozzais.baselibrary.ui.BaseActivity;
import com.tozzais.baselibrary.ui.CheckPermissionActivity;
import com.tozzais.baselibrary.util.CommonUtils;
import com.tozzais.baselibrary.util.DpUtil;
import com.tozzais.baselibrary.util.log.LogUtil;
import com.tozzais.baselibrary.util.progress.LoadingUtils;
import com.tozzais.baselibrary.util.toast.ToastCommom;
import com.ypx.imagepicker.ImagePicker;
import com.ypx.imagepicker.bean.ImageItem;
import com.ypx.imagepicker.bean.MimeType;
import com.ypx.imagepicker.bean.SelectMode;

import org.greenrobot.eventbus.EventBus;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.BindView;
import butterknife.OnClick;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import top.zibin.luban.CompressionPredicate;
import top.zibin.luban.Luban;
import top.zibin.luban.OnCompressListener;

/**
 *
 */
public class ApplyReturnActivity extends CheckPermissionActivity  implements OnPublishImageListener {


    @BindView(R.id.tv_reason)
    TextView tvReason;
    @BindView(R.id.tv_phone)
    TextView tv_phone;
    @BindView(R.id.tv_return_explain)
    TextView tv_return_explain;
    @BindView(R.id.tv_number)
    TextView tv_number;
    @BindView(R.id.et_reason)
    EditText etReason;
    @BindView(R.id.ll_reason)
    LinearLayout ll_reason;
    @BindView(R.id.rv_image)
    RecyclerView rvImage;

    private OrderGoodsInfo orderGoodsInfo;



    public static void launch(Context activity, OrderGoodsInfo orderGoodsInfo) {
        if (!Utils.isFastClick()){
            return;
        }
        Intent intent = new Intent(activity, ApplyReturnActivity.class);
        intent.putExtra("orderGoodsInfo", orderGoodsInfo);
        activity.startActivity(intent);
    }


    @Override
    public int getLayoutId() {
        return R.layout.activity_order_apply_return;
    }


    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putParcelableArrayList("imageBeanList",imageBeanList);

    }

    @Override
    public void initView(Bundle savedInstanceState) {
        PhoneResult phoneBean = GlobalParam.getPhoneBean();
        if(phoneBean != null)
            tv_phone.setText(phoneBean.products_tel_phone);

        orderGoodsInfo = getIntent().getParcelableExtra("orderGoodsInfo");
        LogUtil.e("orderGoodsInfo+"+orderGoodsInfo.order_id);
        setBackTitle("申请退款");

        if (savedInstanceState != null){
            imageBeanList = savedInstanceState.getParcelableArrayList("imageBeanList");
            publishImageAdapter = new MineOrderRefundImageAdpter(this, 0,4);
            rvImage.setLayoutManager(new GridLayoutManager(mActivity, 4));
            rvImage.setAdapter(publishImageAdapter);
            publishImageAdapter.setNewData(imageBeanList);
        }

    }

    @Override
    public void loadData() {
        if (imageBeanList.size()==0){
            publishImageAdapter = new MineOrderRefundImageAdpter(this, 0,4);
            rvImage.setLayoutManager(new GridLayoutManager(mActivity, 4));
            rvImage.setAdapter(publishImageAdapter);
            imageBeanList.add(new OrderCommentImageItemBean());
            publishImageAdapter.setNewData(imageBeanList);
        }

    }

    //图片和视频源数据
    private MineOrderRefundImageAdpter publishImageAdapter;
    private ArrayList<OrderCommentImageItemBean> imageBeanList = new ArrayList<>();
    public int imageTotal = 4;
    private ArrayList<ImageItem> picList = new ArrayList<>();
    @Override
    public void permissionGranted() {
        ImagePicker.withMulti( new CustomImgPickerPresenter())//指定presenter
                .setMaxCount(imageTotal)//设置选择的最大数
                .setColumnCount(4)//设置列数
                .setOriginal(true)
                .mimeTypes(MimeType.ofImage())//设置要加载的文件类型，可指定单一类型
                .filterMimeTypes(MimeType.GIF)//设置需要过滤掉加载的文件类型
                .setSelectMode(SelectMode.MODE_MULTI)
                .setPreviewVideo(false)
                .showCamera(true)//显示拍照
                .setPreview(false)//是否开启预览
                .setVideoSinglePick(true)//设置视频单选
                .setSinglePickWithAutoComplete(true)
                .setSinglePickImageOrVideoType(true)//设置图片和视频单一类型选择
                .setMaxVideoDuration(120000L)//设置视频可选取的最大时长
                .setMinVideoDuration(5000L)
                .setLastImageList(picList)//设置上一次操作的图片列表，下次选择时默认恢复上一次选择的状态
                .setShieldList(null)//设置需要屏蔽掉的图片列表，下次选择时已屏蔽的文件不可选择
                .pick(this, items -> {
                    picList.clear();
                    picList.addAll(items);
                    imageBeanList.clear();
                    for (ImageItem imageItem:picList){
                        imageBeanList.add(new OrderCommentImageItemBean(imageItem.path));
                    }
                    if (imageBeanList.size()<imageTotal){
                        imageBeanList.add(new OrderCommentImageItemBean());
                    }
                    publishImageAdapter.notifyDataSetChanged();
                });
    }



    @OnClick({R.id.ll_reason, R.id.tv_call, R.id.tv_login, R.id.iv_reduce
            , R.id.iv_add})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.ll_reason:
                new RxHttp<BaseResult<ListResult<String>>>().send(ApiManager.getService().refundReason(),
                        new Response<BaseResult<ListResult<String>>>(mActivity) {
                            @Override
                            public void onSuccess(BaseResult<ListResult<String>> result) {
                                ReturnUtil.showSelectDialog(mContext, result.data.list, reason -> {
                                    tvReason.setText(reason);
                                    });
                            }
                        });
                break;
            case R.id.tv_call:
                CommonUtils.callKeFu(mContext,tv_phone.getText().toString());
                break;
            case R.id.tv_login:
                refund();

                break;
            case R.id.iv_reduce:
                modify(false);
                break;
            case R.id.iv_add:
                modify(true);
                break;
        }
    }
    private void modify(boolean isAdd){
        if (orderGoodsInfo == null){
            return;
        }
        String number = tv_number.getText().toString();
        int i = Integer.parseInt(number);
        if (isAdd && i<orderGoodsInfo.refund_num){
            tv_number.setText((i+1)+"");
        }else if (i >1){
            tv_number.setText((i-1)+"");
        }
    }

    private void refundCommit(String images){
        if (orderGoodsInfo == null){
            tsg("订单不存在");
            return;
        }
        TreeMap<String, String> hashMap = new TreeMap<>();
        hashMap.put("order_id", orderGoodsInfo.order_id + "");
        hashMap.put("product_norm_id", orderGoodsInfo.product_norm_id + "");
        hashMap.put("number", tv_number.getText().toString().trim() + "");
        hashMap.put("reason", tvReason.getText().toString().trim() + "");
        hashMap.put("comment", tv_return_explain.getText().toString().trim() + "");
        hashMap.put("images", images + "");
        new RxHttp<BaseResult>().send(ApiManager.getService().refundCommit(hashMap),
                new Response<BaseResult>(mActivity) {
                    @Override
                    public void onSuccess(BaseResult result) {
                        tsg("提交成功");
                        EventBus.getDefault().post(new OrderRefund());
                        finish();
                    }
                });

    }

    private void refund() {
        String reason = tvReason.getText().toString().trim();
        if (TextUtils.isEmpty(reason)){
            tsg("请选择退款原因");
            return;
        }
        String comment = tv_return_explain.getText().toString().trim();
        if (TextUtils.isEmpty(comment)){
            tsg("请输入退款原因");
            return;
        }
        List<String> list = new ArrayList<>();
        for (OrderCommentImageItemBean publishImageBean:imageBeanList){
            if (TextUtils.isEmpty(publishImageBean.path)){
                break;
            }
            list.add(publishImageBean.path);
        }
        fileSize = list.size();
        if (fileSize>0){
            compress(list);
        }else {
            refundCommit("");
        }
    }

    /**
     * 压缩图片
     */
    //上传的文件
    private List<File> fileList = new ArrayList<>();
    private int fileSize;
    private void compress(List<String> list){
        LoadingUtils.show(mContext,"压缩图片中...");
        Luban.with(this)
                .load(list)
                .ignoreBy(100)
                .setTargetDir(Constant.ROOT_PATH)
                .filter(new CompressionPredicate() {
                    @Override
                    public boolean apply(String path) {
                        return !(TextUtils.isEmpty(path) || path.toLowerCase().endsWith(".gif"));
                    }
                })
                .setCompressListener(new OnCompressListener() {
                    @Override
                    public void onStart() {
                    }
                    @Override
                    public void onSuccess(File file) {
                        fileList.add(file);
                        if (fileList.size()==fileSize){
                            upload();
                        }
                    }
                    @Override
                    public void onError(Throwable e) {
                        LoadingUtils.dismiss();
                    }
                }).launch();

    }

    private void upload(){
        List<MultipartBody.Part> parts = new ArrayList<>();
        for (int i=0;i<fileList.size();i++){
            File file = fileList.get(i);
            String name = "file["+i+"]";
            RequestBody requestFile = RequestBody.create(MediaType.parse("multipart/form-data"), file);
            MultipartBody.Part part = MultipartBody.Part.createFormData(name, file.getName(), requestFile);
            parts.add(part);
        }
        new RxHttp<BaseResult<UploadImageResult>>().send(ApiManager.getService().getUploadImgs(parts),
                new Response<BaseResult<UploadImageResult>>(false,mActivity) {
                    @Override
                    public void onSuccess(BaseResult<UploadImageResult> result) {
                        String pics = "";
                        for (String s:result.data.list){
                                pics += s+",";
                        }
                        if (pics.endsWith(",")){
                            pics = pics.substring(0,pics.length()-1);
                        }
                        refundCommit(pics);
                    }
                });
    }



    @Override
    public void onImageClick(int position) {
        OrderCommentImageItemBean item = imageBeanList.get(position);
        if (TextUtils.isEmpty(item.path)) {
            checkPermissions(PhotoUtils.needPermissions);
        } else {
            //查看图片
            List<String> list = new ArrayList<>();
            for (int i = 0; i < imageBeanList.size(); i++) {
                String path = imageBeanList.get(i).path;
                if (!TextUtils.isEmpty(path)) {
                    list.add(path);
                }
            }
            String[] array = list.toArray(new String[list.size()]);
            BigImageActivity.launch(mContext, array, position);
        }
    }

    @Override
    public void onImageRemove(int position) {
        //图片
        imageBeanList.remove(position);
        picList.remove(position);
        if (!TextUtils.isEmpty( imageBeanList.get( imageBeanList.size()-1).path)){
            imageBeanList.add( imageBeanList.size(),new OrderCommentImageItemBean());
        }
        publishImageAdapter.notifyDataSetChanged();

    }
}