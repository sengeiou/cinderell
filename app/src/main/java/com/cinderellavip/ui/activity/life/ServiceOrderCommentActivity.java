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
import com.cinderellavip.adapter.recycleview.MineOrderCommentImageAdpter;
import com.cinderellavip.adapter.recycleview.MineServiceCommentImageAdapter;
import com.cinderellavip.bean.OrderCommentImageItemBean;
import com.cinderellavip.bean.local.PublishImageBean;
import com.cinderellavip.imagepick.CustomImgPickerPresenter;
import com.cinderellavip.listener.OnPublishImageListener;
import com.cinderellavip.util.PhotoUtils;
import com.cinderellavip.weight.RatingBarView;
import com.nex3z.flowlayout.FlowLayout;
import com.tozzais.baselibrary.ui.BaseActivity;
import com.tozzais.baselibrary.ui.CheckPermissionActivity;
import com.tozzais.baselibrary.util.log.LogUtil;
import com.ypx.imagepicker.ImagePicker;
import com.ypx.imagepicker.bean.ImageItem;
import com.ypx.imagepicker.bean.MimeType;
import com.ypx.imagepicker.bean.SelectMode;

import java.util.ArrayList;
import java.util.List;

import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.BindView;
import butterknife.OnClick;


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

    public static void launch(Context from) {
        Intent intent = new Intent(from, ServiceOrderCommentActivity.class);
        from.startActivity(intent);
    }


    MineServiceCommentImageAdapter mAdapter;
    List<OrderCommentImageItemBean> images = new ArrayList<>();
    private ArrayList<ImageItem> picList = new ArrayList<>();
    @Override
    public void initView(Bundle savedInstanceState) {

        setBackTitle("订单评价");


        rvImage.setLayoutManager(new GridLayoutManager(mActivity, 4));
        mAdapter = new MineServiceCommentImageAdapter(this);
        rvImage.setAdapter(mAdapter);
        images.add(new OrderCommentImageItemBean());
        mAdapter.setNewData(images);

    }


    @Override
    public void loadData() {
       addData();




    }

    private void addData(){
        List<String> list=new ArrayList<>();
        list.add("服务态度好");
        list.add("做饭好吃");
        list.add("相处愉快");
        list.add("说话轻柔");
        list.add("不打鼾");
        list.add("形象气质好");
        list.add("个人卫生好");
        for (String text : list) {
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
        tsg("评价成功");
        finish();
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
