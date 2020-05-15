package com.cinderellavip.ui.activity.order;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;

import com.cinderellavip.R;
import com.cinderellavip.adapter.listview.OrderCommentAdapter;
import com.cinderellavip.bean.OrderCommentImageItemBean;
import com.cinderellavip.bean.UploadImageResult;
import com.cinderellavip.bean.eventbus.OrderComment;
import com.cinderellavip.bean.net.order.OrderGoodsInfo;
import com.cinderellavip.bean.net.order.OrderInfo;
import com.cinderellavip.bean.net.order.OrderInfoResult;
import com.cinderellavip.bean.request.CommentOrder;
import com.cinderellavip.global.Constant;
import com.cinderellavip.http.ApiManager;
import com.cinderellavip.http.BaseResult;
import com.cinderellavip.http.Response;
import com.cinderellavip.listener.OnDoublePositionClickListener;
import com.cinderellavip.ui.BigImageActivity;
import com.cinderellavip.util.PhotoUtils;
import com.cinderellavip.weight.MyListView;
import com.cinderellavip.weight.RatingBarView;
import com.google.gson.Gson;
import com.tozzais.baselibrary.http.RxHttp;
import com.tozzais.baselibrary.ui.CheckPermissionActivity;
import com.tozzais.baselibrary.util.log.LogUtil;
import com.tozzais.baselibrary.util.progress.LoadingUtils;

import org.greenrobot.eventbus.EventBus;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.TreeMap;

import butterknife.BindView;
import butterknife.OnClick;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import top.zibin.luban.Luban;
import top.zibin.luban.OnCompressListener;

/**
 *
 */
public class OrderCommentActivity extends CheckPermissionActivity implements OnDoublePositionClickListener {


    @BindView(R.id.lv_comment)
    MyListView lvComment;


    private OrderCommentAdapter adapter;
    private ArrayList<OrderGoodsInfo> data;
    private int IMAGE_MAX = 4;

    private int position = 3;
    private int childPosition = 3;

    public int order_id;
    public static void launch(Context activity, int order_id) {
        Intent intent = new Intent(activity, OrderCommentActivity.class);
        intent.putExtra("order_id", order_id);
        activity.startActivity(intent);
    }


    @Override
    public int getLayoutId() {
        return R.layout.activity_order_comment;
    }

    @Override
    public void initView(Bundle savedInstanceState) {
        LogUtil.e("initView" + (savedInstanceState==null));
        order_id = getIntent().getIntExtra("order_id", -1);
        setBackTitle("发表评价");
        if (savedInstanceState != null){
            data = savedInstanceState.getParcelableArrayList("list");
            position = savedInstanceState.getInt("position");
            childPosition = savedInstanceState.getInt("childPosition");
            adapter = new OrderCommentAdapter(OrderCommentActivity.this.data, mContext, OrderCommentActivity.this);
            lvComment.setAdapter(adapter);
        }
    }

    @Override
    public void loadData() {
//        LogUtil.e("loadData");
        if (data != null){
            return;
        }
        if (!isLoad)showProress();
        new RxHttp<BaseResult<OrderInfoResult<OrderInfo>>>().send(ApiManager.getService().getOrderDetail(order_id + ""),
                new Response<BaseResult<OrderInfoResult<OrderInfo>>>(isLoad, mActivity) {
                    @Override
                    public void onSuccess(BaseResult<OrderInfoResult<OrderInfo>> result) {
                        showContent();
                        if (data == null){
                            data = new ArrayList<>();
                        }
                        List<OrderGoodsInfo> datas = result.data.order.goods;
                        for (OrderGoodsInfo itemBean:datas){
                            data.add(itemBean);
                        }
                        for (OrderGoodsInfo orderCommentItemBean : OrderCommentActivity.this.data) {
                            orderCommentItemBean.images.add(new OrderCommentImageItemBean());
                        }
                        adapter = new OrderCommentAdapter(OrderCommentActivity.this.data, mContext, OrderCommentActivity.this);
                        lvComment.setAdapter(adapter);
                    }

                    @Override
                    public void onErrorShow(String s) {
                        showError(s);
                    }
                });

    }



    @OnClick(R.id.tv_btn_bottom3)
    public void onClick() {
        List<CommentOrder> list = new ArrayList<>();
        for (OrderGoodsInfo bean : data) {
            if (TextUtils.isEmpty(bean.product_star)) {
                tsg("请选择评分");
                return;
            }
            if (TextUtils.isEmpty(bean.content)) {
                tsg("请输入评价内容");
                return;
            }
            list.add(new CommentOrder(bean.product_id + "", bean.product_star, bean.content, bean.getPics()));
        }
        comment(new Gson().toJson(list));
    }


    @BindView(R.id.rb_logistics_score)
    RatingBarView rb_logistics_score;
    @BindView(R.id.rb_logistics_service)
    RatingBarView rb_logistics_service;
    @BindView(R.id.rb_attitude_score)
    RatingBarView rb_attitude_score;

    private void comment(String str) {
        int desc_score = rb_logistics_score.getStarCount();
        int send_score = rb_logistics_service.getStarCount();
        int service_score = rb_attitude_score.getStarCount();

        TreeMap<String, String> map = new TreeMap<>();
        map.put("order_id", order_id + "");
        map.put("assess_star", desc_score+"");
        map.put("logistics_star", send_score+"");
        map.put("server_star", service_score+"");
        map.put("goods_commit", str);
        new RxHttp<BaseResult>().send(ApiManager.getService().orderComment(map),
                new Response<BaseResult>(isLoad, mActivity) {
                    @Override
                    public void onSuccess(BaseResult result) {
                        tsg("评价成功");
                        EventBus.getDefault().post(new OrderComment());
                        finish();
                    }
                });
    }



    @Override
    public void onClick(int position, int childPosition) {
        this.position = position;
        this.childPosition = childPosition;
        List<OrderCommentImageItemBean> mDatas = data.get(position).images;
        OrderCommentImageItemBean item = mDatas.get(childPosition);
        if (TextUtils.isEmpty(item.path)) {
            checkPermissions(PhotoUtils.needPermissions);
        } else {
            //查看图片
            List<String> list = new ArrayList<>();
            for (int i = 0; i < mDatas.size(); i++) {
                String path = mDatas.get(i).path;
                if (!TextUtils.isEmpty(path)) {
                    list.add(path);
                }
            }
            String[] array = list.toArray(new String[list.size()]);
            BigImageActivity.launch(mContext, array, childPosition);
        }

    }

    //图片选择有关
    @Override
    public void permissionGranted() {
        PhotoUtils.getInstance().selectPic(mActivity);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if ((requestCode == PhotoUtils.PHOTOTAKE || requestCode == PhotoUtils.PHOTOZOOM) && resultCode == RESULT_OK) {
            String path = PhotoUtils.getInstance().getPath(mActivity, requestCode, data);
            compress(path);
        }
    }
    private void compress(String path){
        Luban.with(this)
                .load(path)
                .ignoreBy(100)
                .setTargetDir(Constant.ROOT_PATH)
                .filter(path1 -> !(TextUtils.isEmpty(path1) || path1.toLowerCase().endsWith(".gif")))
                .setCompressListener(new OnCompressListener() {
                    @Override
                    public void onStart() {
                        LoadingUtils.show(mContext,"压缩图片中...");
                    }

                    @Override
                    public void onSuccess(File file) {
                        LoadingUtils.dismiss();
                        upload(file.getAbsolutePath());
                    }

                    @Override
                    public void onError(Throwable e) {
                        tsg("图片压缩失败");
                        LoadingUtils.dismiss();
                    }
                }).launch();

    }

    private void upload(String path) {
        List<MultipartBody.Part> parts = new ArrayList<>();
        File file = new File(path);
        RequestBody requestFile = RequestBody.create(MediaType.parse("multipart/form-data"), file);
        MultipartBody.Part part = MultipartBody.Part.createFormData("file", file.getName(), requestFile);
        parts.add(part);
        new RxHttp<BaseResult<UploadImageResult>>().send(ApiManager.getService().getUploadImg(parts),
                new Response<BaseResult<UploadImageResult>>(mActivity) {
                    @Override
                    public void onSuccess(BaseResult<UploadImageResult> result) {
                        setAdapter(path,result.data.url);
                    }
                });
    }
    private void setAdapter(String path,String netPath) {
        List<OrderCommentImageItemBean> mDatas = data.get(position).images;
        OrderCommentImageItemBean item = mDatas.get(childPosition);
        item.path = path;
        item.netPath = netPath;
        //只有前两个 并且总数量小于 4 说明图片还可以添加
        if (position < IMAGE_MAX-1 && mDatas.size() < IMAGE_MAX
                && !TextUtils.isEmpty(mDatas.get(mDatas.size() - 1).path)) {
            mDatas.add(mDatas.size(), new OrderCommentImageItemBean());
        }
        adapter.notifyDataSetChanged();
    }


    @Override
    protected void onSaveInstanceState(Bundle outState) {
        LogUtil.e("onSaveInstanceState");
        super.onSaveInstanceState(outState);
        outState.putParcelableArrayList("list",data);
        outState.putInt("position",position);
        outState.putInt("childPosition",childPosition);
    }


}