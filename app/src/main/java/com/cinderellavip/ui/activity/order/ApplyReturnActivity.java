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
import com.cinderellavip.adapter.recycleview.HorizontalAdapter;
import com.cinderellavip.bean.ReturnReasonItem;
import com.cinderellavip.bean.local.PublishImageBean;
import com.cinderellavip.imagepick.CustomImgPickerPresenter;
import com.cinderellavip.listener.OnPublishImageListener;
import com.cinderellavip.toast.CenterDialogUtil;
import com.cinderellavip.toast.ReturnUtil;
import com.cinderellavip.util.PhotoUtils;
import com.cinderellavip.weight.GirdSpaceRight;
import com.tozzais.baselibrary.ui.BaseActivity;
import com.tozzais.baselibrary.ui.CheckPermissionActivity;
import com.tozzais.baselibrary.util.CommonUtils;
import com.tozzais.baselibrary.util.DpUtil;
import com.tozzais.baselibrary.util.log.LogUtil;
import com.ypx.imagepicker.ImagePicker;
import com.ypx.imagepicker.bean.ImageItem;
import com.ypx.imagepicker.bean.MimeType;
import com.ypx.imagepicker.bean.SelectMode;

import java.util.ArrayList;
import java.util.List;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.BindView;
import butterknife.OnClick;

/**
 *
 */
public class ApplyReturnActivity extends CheckPermissionActivity  implements OnPublishImageListener {


    @BindView(R.id.tv_reason)
    TextView tvReason;
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

    private int child_order_id;

    public static void launch(Context activity, int child_order_id) {
        Intent intent = new Intent(activity, ApplyReturnActivity.class);
        intent.putExtra("child_order_id", child_order_id);
        activity.startActivity(intent);
    }


    @Override
    public int getLayoutId() {
        return R.layout.activity_order_apply_return;
    }

    @Override
    public void initView(Bundle savedInstanceState) {
        child_order_id = getIntent().getIntExtra("child_order_id",-1);
        setBackTitle("申请退款");

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

    private HorizontalAdapter publishImageAdapter;
    private List<PublishImageBean> imageBeanList = new ArrayList<>();
    public int imageTotal = 9;
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
                        LogUtil.e(imageItem.path);
                        imageBeanList.add(new PublishImageBean(imageItem.path));
                    }
                    publishImageAdapter.notifyDataSetChanged();
                });
    }
    @OnClick({R.id.ll_reason, R.id.tv_call, R.id.tv_login, R.id.iv_reduce
            , R.id.iv_add, R.id.iv_add_image})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.ll_reason:
                List<ReturnReasonItem> data = new ArrayList<>();
                data.add(new ReturnReasonItem("商品瑕疵，无法正常使用"));
                data.add(new ReturnReasonItem("商品缺货，无法发货"));
                data.add(new ReturnReasonItem("时间不足，无法领取"));
                data.add(new ReturnReasonItem("商品存在严重质量问题"));
                data.add(new ReturnReasonItem("商品实物与商家描述存在较大误差"));
                    ReturnUtil.showSelectDialog(mContext, data, reason -> {
                        tvReason.setText(reason.name);
                    });
                break;
            case R.id.tv_call:
                CommonUtils.callKeFu(mContext);
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
            case R.id.iv_add_image:
                checkPermissions(PhotoUtils.needPermissions);
                break;
        }
    }
    private void modify(boolean isAdd){
        String number = tv_number.getText().toString();
        int i = Integer.parseInt(number);
        if (isAdd){
            tv_number.setText((i+1)+"");
        }else if (i >1){
            tv_number.setText((i-1)+"");
        }
    }

    private void refund() {
        String reason = tvReason.getText().toString().trim();
        if (TextUtils.isEmpty(reason)){
            tsg("请选择退款原因");
            return;
        }
        CenterDialogUtil.showCommitSuccess(mContext,()->{
            finish();
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