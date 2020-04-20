package com.cinderellavip.ui.activity.order;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;

import com.cinderellavip.R;
import com.cinderellavip.adapter.listview.OrderCommentAdapter;
import com.cinderellavip.bean.OrderCommentImageItemBean;
import com.cinderellavip.bean.OrderCommentItemBean;
import com.cinderellavip.bean.request.CommentOrder;
import com.cinderellavip.listener.OnDoublePositionClickListener;
import com.cinderellavip.ui.BigImageActivity;
import com.cinderellavip.util.PhotoUtils;
import com.cinderellavip.weight.MyListView;
import com.google.gson.Gson;
import com.tozzais.baselibrary.ui.CheckPermissionActivity;
import com.tozzais.baselibrary.util.log.LogUtil;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

/**
 *
 */
public class OrderCommentActivity extends CheckPermissionActivity implements OnDoublePositionClickListener {


    @BindView(R.id.lv_comment)
    MyListView lvComment;

    private OrderCommentAdapter adapter;
    private ArrayList<OrderCommentItemBean> data;
    private int IMAGE_MAX = 4;
    private int position = 3;
    private int childPosition = 3;

    public int child_order_id;
    public static void launch(Context activity, int child_order_id) {
        Intent intent = new Intent(activity, OrderCommentActivity.class);
        intent.putExtra("child_order_id", child_order_id);
        activity.startActivity(intent);
    }


    @Override
    public int getLayoutId() {
        return R.layout.activity_order_comment;
    }

    @Override
    public void initView(Bundle savedInstanceState) {
        LogUtil.e("initView" + (savedInstanceState==null));
        child_order_id = getIntent().getIntExtra("child_order_id", -1);
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
        if (data == null){
            data = new ArrayList<>();
        }
        data.add(new OrderCommentItemBean());
        data.add(new OrderCommentItemBean());

        for (OrderCommentItemBean orderCommentItemBean : OrderCommentActivity.this.data) {
            orderCommentItemBean.mDatas.add(new OrderCommentImageItemBean());
        }
        adapter = new OrderCommentAdapter(OrderCommentActivity.this.data, mContext, OrderCommentActivity.this);
        lvComment.setAdapter(adapter);
    }

    @OnClick(R.id.tv_btn_bottom3)
    public void onClick() {
        List<CommentOrder> list = new ArrayList<>();
        for (OrderCommentItemBean bean : data) {
            if (TextUtils.isEmpty(bean.score)) {
                tsg("请选择评分");
                return;
            }
            if (TextUtils.isEmpty(bean.content)) {
                tsg("请输入评价内容");
                return;
            }
            list.add(new CommentOrder(bean.product_id + "", bean.score, bean.content, bean.getPics()));
        }

//        tsg(new Gson().toJson(list));
        tsg("评价成功");
        finish();
    }

//    private void comment(String str) {
//        TreeMap<String, String> map = new TreeMap<>();
//        map.put("child_order_id", child_order_id + "");
//        map.put("str", str);
//        map.put("user_id", GlobalParam.getUserId());
//        map.put("sign", SignUtil.getMd5(map));
//        new RxHttp<BaseResult>().send(ApiManager.getService().getEvaluation(map),
//                new Response<BaseResult>(isLoad, mActivity) {
//                    @Override
//                    public void onSuccess(BaseResult result) {
//                        tsg("评价成功");
//                        EventBus.getDefault().post(new OrderComment());
//                        finish();
//                    }
//                });
//    }



    @Override
    public void onClick(int position, int childPosition) {
        this.position = position;
        this.childPosition = childPosition;
        List<OrderCommentImageItemBean> mDatas = data.get(position).mDatas;
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

    @Override
    public void permissionGranted() {
        PhotoUtils.getInstance().selectPic(mActivity);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if ((requestCode == PhotoUtils.PHOTOTAKE || requestCode == PhotoUtils.PHOTOZOOM) && resultCode == RESULT_OK) {
            String path = PhotoUtils.getInstance().getPath(mActivity, requestCode, data);
            upload(path);
        }
    }

    private void upload(String path) {
        List<OrderCommentImageItemBean> mDatas = data.get(position).mDatas;
        OrderCommentImageItemBean item = mDatas.get(childPosition);
        item.path = path;
        item.netPath = path;
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