package com.cinderellavip.adapter.recycleview;


import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.module.LoadMoreModule;
import com.chad.library.adapter.base.viewholder.BaseViewHolder;
import com.cinderellavip.R;
import com.cinderellavip.bean.PrePayLongOrder;
import com.cinderellavip.bean.eventbus.UpdateLongServiceOrder;
import com.cinderellavip.bean.net.life.LongOrderItem;
import com.cinderellavip.global.ImageUtil;
import com.cinderellavip.http.ApiManager;
import com.cinderellavip.http.BaseResult;
import com.cinderellavip.http.Response;
import com.cinderellavip.toast.CenterDialogUtil;
import com.cinderellavip.ui.activity.life.LongServiceOrderDetailActivity;
import com.cinderellavip.ui.activity.life.PayCheckoutCounterActivity;
import com.tozzais.baselibrary.http.RxHttp;
import com.tozzais.baselibrary.util.toast.ToastCommom;

import org.greenrobot.eventbus.EventBus;

import java.util.TreeMap;

public class LongServiceOrderAdapter extends BaseQuickAdapter<LongOrderItem, BaseViewHolder> implements LoadMoreModule {


    /**
     * 是不是长期服务订单
     */
    private boolean isLongServiceOrder;
    public LongServiceOrderAdapter() {
        super(R.layout.item_order_single, null);
    }
    public LongServiceOrderAdapter(boolean isLongServiceOrder) {
        super(R.layout.item_order_single, null);
        this.isLongServiceOrder = isLongServiceOrder;
    }


    @Override
    protected void convert(BaseViewHolder helper, LongOrderItem item) {
        int position = helper.getAdapterPosition();
        TextView tv_status = helper.getView(R.id.tv_status);
        TextView tv_btn1 = helper.getView(R.id.tv_btn1);
        TextView tv_btn2 = helper.getView(R.id.tv_btn2);

        ImageView iv_image = helper.getView(R.id.iv_image);
        ImageUtil.loadNet(getContext(),iv_image,item.service_icon);
        helper.setText(R.id.tv_title,"服务编号："+item.code)
                .setText(R.id.tv_status,item.getStatus())
                .setText(R.id.tv_type,item.service_name)
                .setText(R.id.tv_service_type,"服务类型："+item.service_name+"-"+item.project_title)
                .setText(R.id.tv_service_time,"服务周期："+item.cycle+"个月")
                .setText(R.id.tv_service_address,"服务地址："+item.address.address+item.address.doorplate)
                .setText(R.id.tv_price,""+item.getPrice()+"元");
        switch (item.type) {
            case 1:
                tv_btn1.setText("取消");
                tv_btn2.setText("确认");
                tv_btn1.setVisibility(View.VISIBLE);
                tv_btn2.setVisibility(View.VISIBLE);
                break;
            case 2:
                tv_btn1.setText("取消");
                tv_btn2.setText("支付");
                tv_btn1.setVisibility(View.VISIBLE);
                tv_btn2.setVisibility(View.VISIBLE);
                break;
            case 3:
            case 4:
            case 6:
            case 7:
                tv_btn1.setText("详情");
                tv_btn1.setVisibility(View.VISIBLE);
                tv_btn2.setVisibility(View.GONE);
                break;
            case 5:
                tv_btn2.setText("评价");
                tv_btn1.setVisibility(View.GONE);
                tv_btn2.setVisibility(View.VISIBLE);
                break;

        }
        tv_btn1.setOnClickListener(view -> {
            switch (item.type) {
                case 1:
                case 2:
                    CenterDialogUtil.showServiceOrder(getContext(), "确认提示", "您确定要取消该订单吗？\n取消后不可撤回"
                            , "取消", "确定", s -> {
                                cancel(item.id+"");
                            });

                    break;
                default:
                    LongServiceOrderDetailActivity.launch(getContext(), item.id);
                    break;

            }
        });
        tv_btn2.setOnClickListener(view -> {
            switch (item.type) {
                case 1:
                    CenterDialogUtil.showServiceOrder(getContext(), "确认提示", "请仔细核对订单信息\n确认后不可撤回"
                            , "取消", "确定", s -> {
                                confirm(item.cont_id+"");
                            });
                    break;
                case 2:
                    //去支付
                     PrePayLongOrder prePayLongOrder = new PrePayLongOrder();
                     prePayLongOrder.contracts_id = item.cont_id+"";
                     prePayLongOrder.coupon = "";
                     prePayLongOrder.type = PrePayLongOrder.LONG;
                     PayCheckoutCounterActivity.launch(getContext(),prePayLongOrder);
                    break;
                default:
                    LongServiceOrderDetailActivity.launch(getContext(), item.id);
                    break;

            }
        });
        LinearLayout ll_root = helper.getView(R.id.ll_root);
        ll_root.setOnClickListener(v -> {
            LongServiceOrderDetailActivity.launch(getContext(), item.id);
        });



    }


    /**
     * 确定定价
     * @param contracts_id 合同id
     */
    public void confirm(String contracts_id) {
        TreeMap<String, String> hashMap = new TreeMap<>();
        hashMap.put("contracts_id", ""+contracts_id);
        new RxHttp<BaseResult>().send(ApiManager.getService().longOrderConfirm(hashMap),
                new Response<BaseResult>(getContext()) {
                    @Override
                    public void onSuccess(BaseResult result) {
                        ToastCommom.createToastConfig().ToastShow(getContext(),"操作成功");
                        EventBus.getDefault().post(new UpdateLongServiceOrder());
                    }
                });


    }


    /**
     * @param contracts_id 合同id
     */
    public void cancel(String contracts_id) {
        TreeMap<String, String> hashMap = new TreeMap<>();
        hashMap.put("order", ""+contracts_id);
        new RxHttp<BaseResult>().send(ApiManager.getService().cancelLongOrder(hashMap),
                new Response<BaseResult>(getContext()) {
                    @Override
                    public void onSuccess(BaseResult result) {
                        ToastCommom.createToastConfig().ToastShow(getContext(),"操作成功");
                        EventBus.getDefault().post(new UpdateLongServiceOrder());
                    }
                });


    }


}
