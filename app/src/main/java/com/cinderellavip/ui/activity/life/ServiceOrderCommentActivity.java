package com.cinderellavip.ui.activity.life;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.cinderellavip.R;
import com.cinderellavip.adapter.recycleview.MineServiceCommentImageAdapter;
import com.cinderellavip.bean.OrderCommentImageItemBean;
import com.cinderellavip.bean.UploadImageResult;
import com.cinderellavip.bean.eventbus.UpdateLongServiceOrder;
import com.cinderellavip.bean.eventbus.UpdateShortServiceOrder;
import com.cinderellavip.bean.net.life.LifeOrderCommentLabel;
import com.cinderellavip.global.Constant;
import com.cinderellavip.http.ApiManager;
import com.cinderellavip.http.BaseListResult;
import com.cinderellavip.http.BaseResult;
import com.cinderellavip.http.Response;
import com.cinderellavip.imagepick.CustomImgPickerPresenter;
import com.cinderellavip.listener.OnPublishImageListener;
import com.cinderellavip.util.PhotoUtils;
import com.cinderellavip.weight.RatingBarView;
import com.nex3z.flowlayout.FlowLayout;
import com.tozzais.baselibrary.http.RxHttp;
import com.tozzais.baselibrary.ui.CheckPermissionActivity;
import com.tozzais.baselibrary.util.log.LogUtil;
import com.tozzais.baselibrary.util.progress.LoadingUtils;
import com.ypx.imagepicker.ImagePicker;
import com.ypx.imagepicker.bean.ImageItem;
import com.ypx.imagepicker.bean.MimeType;
import com.ypx.imagepicker.bean.SelectMode;

import org.greenrobot.eventbus.EventBus;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.TreeMap;

import androidx.recyclerview.widget.GridLayoutManager;
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
 * Created by Administrator on 2016/9/8.
 */
public class ServiceOrderCommentActivity  extends CheckPermissionActivity  implements OnPublishImageListener {


    @BindView(R.id.et_comment)
    EditText et_comment;
    @BindView(R.id.rv_image)
    RecyclerView rvImage;
    @BindView(R.id.fl_select_flag)
    FlowLayout flSelectFlag;
    @BindView(R.id.fl_flag)
    FlowLayout flFlag;
    @BindView(R.id.rb_logistics_score)
    RatingBarView rbLogisticsScore;


    List<String> selectFlag = new ArrayList<>();

    private String order;
    public static void launch(Context from,String order) {
        Intent intent = new Intent(from, ServiceOrderCommentActivity.class);
        intent.putExtra("order",order);
        from.startActivity(intent);
    }


    MineServiceCommentImageAdapter mAdapter;
    List<OrderCommentImageItemBean> images = new ArrayList<>();
    private ArrayList<ImageItem> picList = new ArrayList<>();
    @Override
    public void initView(Bundle savedInstanceState) {

        setBackTitle("订单评价");

        order = getIntent().getStringExtra("order");
        rvImage.setLayoutManager(new GridLayoutManager(mActivity, 4));
        mAdapter = new MineServiceCommentImageAdapter(this);
        rvImage.setAdapter(mAdapter);
        images.add(new OrderCommentImageItemBean());
        mAdapter.setNewData(images);

    }


    @Override
    public void loadData() {

        new RxHttp<BaseListResult<LifeOrderCommentLabel>>().send(ApiManager.getService().getCommentLabel(),
                new Response<BaseListResult<LifeOrderCommentLabel>>(isLoad, mActivity) {
                    @Override
                    public void onSuccess(BaseListResult<LifeOrderCommentLabel> result) {
                        addData(result.data);
                    }

                    @Override
                    public void onErrorShow(String s) {
                        showError(s);
                    }
                });

    }

    private void addData(List<LifeOrderCommentLabel> list){
        for (LifeOrderCommentLabel label : list) {
            String text = label.content;
            View view = View.inflate(mActivity,R.layout.item_service_order_comment_flag,null);
            TextView tv = view.findViewById(R.id.tv_title);
            tv.setOnClickListener(v -> {
                boolean isHave = false;
                for (String s:selectFlag){
                    if (s.equals(text)){
                        isHave = true;
                        break;
                    }
                }
                if (!isHave){
                    selectFlag.add(text);
                    addCommentData(text);
                }
            });
            tv.setText(text);
            flFlag.addView(view);
        }
    }


    private void addCommentData(String text){

            View view = View.inflate(mActivity,R.layout.item_service_order_comment_flag_select,null);
            TextView tv = view.findViewById(R.id.tv_title);
            ImageView iv_delete = view.findViewById(R.id.iv_delete);
            iv_delete.setOnClickListener(v -> {
                selectFlag.remove(text);
                flSelectFlag.removeView(view);
            });
            tv.setText(text);
            flSelectFlag.addView(view);
    }





    @Override
    public int getLayoutId() {
        return R.layout.activity_order_comment_single;
    }


    @OnClick(R.id.tv_buy)
    public void onClick() {
        String comment = et_comment.getText().toString().trim();
        if (TextUtils.isEmpty(comment)){
            tsg("请输入评价感受");
            return;
        }
        if (picList.size() == 0){
            tsg("请选择图片");
            return;
        }



        LogUtil.e("score = "+rbLogisticsScore.getStarCount());

//        tsg("评价成功");
//        finish();

        fileList.clear();
        List<String> list = new ArrayList<>();
        for (ImageItem publishImageBean:picList){
            list.add(publishImageBean.path);
        }
        compress(list);
    }

    //上传的文件
    private List<File> fileList = new ArrayList<>();
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
                        if (fileList.size()==picList.size()){
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
        LoadingUtils.show(mContext,"图片上传中...");
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
                            pics += s+";";
                        }
                        if (pics.endsWith(";")){
                            pics = pics.substring(0,pics.length()-1);
                        }
                        commit(pics);
                    }
                });
    }

    private void commit(String image){
        LoadingUtils.show(mContext,"加载中...");
        String comment = et_comment.getText().toString().trim();
        TreeMap<String, String> hashMap = new TreeMap<>();
        hashMap.put("order", order);
        hashMap.put("content", comment);
        hashMap.put("image", ""+image);
        String label = "";
        for (int i=0;i<selectFlag.size();i++){
            String s = selectFlag.get(i);
            if (i == selectFlag.size()-1){
                label += s;
            }else {
                label += s+";";
            }
        }
        hashMap.put("label", "" + label);
        hashMap.put("score", "" + rbLogisticsScore.getStarCount());
        new RxHttp<BaseResult>().send(ApiManager.getService().getComment(hashMap),
                new Response<BaseResult>( mActivity) {
                    @Override
                    public void onSuccess(BaseResult result) {
                        tsg("评价成功");
                        EventBus.getDefault().post(new UpdateShortServiceOrder());
                        EventBus.getDefault().post(new UpdateLongServiceOrder());
                        finish();
                    }
                });

    }
    @Override
    public void permissionGranted() {
        ImagePicker.withMulti( new CustomImgPickerPresenter())//指定presenter
                .setMaxCount(4)//设置选择的最大数
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
                    images.clear();
                    for (ImageItem imageItem:picList){
                        images.add(new OrderCommentImageItemBean(imageItem.path));
                    }
                    if (images.size()<4){
                        images.add(new OrderCommentImageItemBean(""));
                    }
                    mAdapter.notifyDataSetChanged();
                });

    }

    @Override
    public void onImageClick(int position) {
        if (TextUtils.isEmpty(images.get(position).path)){
            checkPermissions(PhotoUtils.needPermissions);
        }
    }

    @Override
    public void onImageRemove(int position) {
        images.remove(position);
        picList.remove(position);
        if (!TextUtils.isEmpty( images.get(images.size()-1).path)){
            images.add(images.size(),new OrderCommentImageItemBean());
        }
        mAdapter.notifyDataSetChanged();

    }
}
