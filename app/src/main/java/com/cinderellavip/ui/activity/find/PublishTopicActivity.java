package com.cinderellavip.ui.activity.find;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;

import com.cinderellavip.R;
import com.cinderellavip.adapter.recycleview.HorizontalAdapter;
import com.cinderellavip.bean.UploadImageResult;
import com.cinderellavip.bean.eventbus.UpdateFind;
import com.cinderellavip.bean.local.PublishImageBean;
import com.cinderellavip.global.Constant;
import com.cinderellavip.http.ApiManager;
import com.cinderellavip.http.BaseResult;
import com.cinderellavip.http.Response;
import com.cinderellavip.imagepick.CustomImgPickerPresenter;
import com.cinderellavip.listener.OnPublishImageListener;
import com.cinderellavip.util.PhotoUtils;
import com.cinderellavip.weight.GirdSpaceRight;
import com.tozzais.baselibrary.http.RxHttp;
import com.tozzais.baselibrary.ui.CheckPermissionActivity;
import com.tozzais.baselibrary.util.DpUtil;
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
 * 发布话题
 */
public class PublishTopicActivity extends CheckPermissionActivity  implements OnPublishImageListener {

    @BindView(R.id.et_title)
    EditText etTitle;
    @BindView(R.id.et_content)
    EditText etContent;
    @BindView(R.id.rv_image)
    RecyclerView rvImage;

    private HorizontalAdapter publishImageAdapter;
    private List<PublishImageBean> imageBeanList = new ArrayList<>();
    public int imageTotal = 9;
    private ArrayList<ImageItem> picList = new ArrayList<>();





    public static void launch(Context activity) {
        Intent intent = new Intent(activity, PublishTopicActivity.class);
        activity.startActivity(intent);
    }


    @Override
    public void initView(Bundle savedInstanceState) {
        setLineVisibility();
        setBackTitle("发布话题");




        rvImage.addItemDecoration(new GirdSpaceRight(DpUtil.dip2px(mActivity, 8)));
        publishImageAdapter = new HorizontalAdapter(mActivity,imageBeanList,this);
        LinearLayoutManager managerHorizontal = new LinearLayoutManager(this);
        managerHorizontal.setOrientation(LinearLayoutManager.HORIZONTAL);
        rvImage.setLayoutManager(managerHorizontal);
        rvImage.setHasFixedSize(true);
        rvImage.setAdapter(publishImageAdapter);



    }

    @Override
    public void loadData() {

    }


    @Override
    public int getLayoutId() {
        return R.layout.activity_publish_topic;
    }


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
                        LogUtil.e(imageItem.path);
                        imageBeanList.add(new PublishImageBean(imageItem.path));
                    }
                    publishImageAdapter.notifyDataSetChanged();
                });
    }


    @OnClick({R.id.iv_add_image, R.id.tv_sava})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.iv_add_image:
                checkPermissions(PhotoUtils.needPermissions);
                break;

            case R.id.tv_sava:
                commit();
                break;

        }
    }

    public void commit(){
        String title = etTitle.getText().toString().trim();
        if (TextUtils.isEmpty(title)){
            tsg("请输入标题");
            return;
        }
        String content = etContent.getText().toString().trim();
        if (TextUtils.isEmpty(content)){
            tsg("请输入描述");
            return;
        }
        if (imageBeanList.size() == 0){
            tsg("请选择图片");
            return;
        }
//        LogUtil.e(imageBeanList.size()+"");
        //防止压缩后上传不成功 无法计算压缩数量导致无妨上传的bug
        fileList.clear();
        List<String> list = new ArrayList<>();
        for (PublishImageBean publishImageBean:imageBeanList){
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
                        if (fileList.size()==imageBeanList.size()){
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
                        commit(pics);
                    }
                });
    }

    private void commit(String images){
        String title = etTitle.getText().toString().trim();
        String content = etContent.getText().toString().trim();
        TreeMap<String, String> hashMap = new TreeMap<>();
        hashMap.put("title", title);
        hashMap.put("content", content);
        hashMap.put("images", images);
        new RxHttp<BaseResult>().send(ApiManager.getService().topic_release(hashMap),
                new Response<BaseResult>(mActivity) {
                    @Override
                    public void onSuccess(BaseResult result) {
                        tsg("发布成功");
                        EventBus.getDefault().post(new UpdateFind());
                        finish();
                    }
                });

    }


    @Override
    public void onImageClick(int position) {

    }

    @Override
    public void onImageRemove(int position) {
        imageBeanList.remove(position);
        publishImageAdapter.notifyDataSetChanged();


    }



}
