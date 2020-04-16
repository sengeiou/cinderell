package com.cinderellavip.ui.activity.find;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import com.cinderellavip.R;
import com.cinderellavip.adapter.recycleview.HorizontalAdapter;
import com.cinderellavip.adapter.recycleview.PublishImageAdapter;
import com.cinderellavip.bean.local.PublishImageBean;
import com.cinderellavip.imagepick.CustomImgPickerPresenter;
import com.cinderellavip.listener.OnPublishImageListener;
import com.cinderellavip.util.PhotoUtils;
import com.cinderellavip.weight.GirdSpaceRight;
import com.tozzais.baselibrary.ui.CheckPermissionActivity;
import com.tozzais.baselibrary.util.DpUtil;
import com.tozzais.baselibrary.util.log.LogUtil;
import com.ypx.imagepicker.ImagePicker;
import com.ypx.imagepicker.bean.ImageItem;
import com.ypx.imagepicker.bean.MimeType;
import com.ypx.imagepicker.bean.SelectMode;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.BindView;
import butterknife.OnClick;

/**
 * 发布话题
 */
public class PublishPostActivity extends CheckPermissionActivity implements OnPublishImageListener {


    @BindView(R.id.et_title)
    EditText etTitle;
    @BindView(R.id.et_content)
    EditText etContent;
    @BindView(R.id.rv_image)
    RecyclerView rvImage;
    @BindView(R.id.rv_goods)
    RecyclerView rvGoods;

    private HorizontalAdapter publishImageAdapter;
    private List<PublishImageBean> imageBeanList = new ArrayList<>();
    public int imageTotal = 9;
    private ArrayList<ImageItem> picList = new ArrayList<>();


    public static void launch(Context activity) {
        Intent intent = new Intent(activity, PublishPostActivity.class);
        activity.startActivity(intent);
    }


    @Override
    public void initView(Bundle savedInstanceState) {
        setLineVisibility();
        setBackTitle("发布帖子");




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
        return R.layout.activity_publish_post;
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


    @OnClick({R.id.iv_add_image, R.id.iv_add_goods, R.id.tv_sava})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.iv_add_image:
                checkPermissions(PhotoUtils.needPermissions);
                break;
            case R.id.iv_add_goods:
                break;
            case R.id.tv_sava:
                break;
        }
    }

    @Override
    public void onImageClick(int position) {

    }

    @Override
    public void onImageRemove(int position) {

    }
}
