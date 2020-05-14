package com.cinderellavip.toast;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Paint;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.cinderellavip.R;
import com.cinderellavip.adapter.recycleview.CouponReceiveDialogAdapter;
import com.cinderellavip.adapter.recycleview.CouponReceiveDialogForServiceAdapter;
import com.cinderellavip.bean.local.CouponsBean;
import com.cinderellavip.bean.net.SpecialItem;
import com.cinderellavip.bean.net.goods.GoodsResult;
import com.cinderellavip.global.ImageUtil;
import com.cinderellavip.ui.activity.find.PublishPostActivity;
import com.cinderellavip.ui.activity.find.PublishTopicActivity;
import com.cinderellavip.weight.CartNumberView;
import com.cinderellavip.weight.SquareRoundImageView;
import com.nex3z.flowlayout.FlowLayout;
import com.tozzais.baselibrary.util.CommonUtils;
import com.tozzais.baselibrary.util.log.LogUtil;

import java.util.ArrayList;
import java.util.List;

import androidx.core.widget.NestedScrollView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;


public class DialogUtil {
    private static Dialog dialog;






    public static void showSpeciSpecialDialog(Context context, GoodsResult goodsResult, onNormSelectListener listener) {

        View view = View.inflate(context, R.layout.pop_bottom_specification, null);
        dialog = DialogUtils.getBottomDialog(context, view);
        ImageView iv_close = view.findViewById(R.id.iv_close);


        TextView tv_sure = view.findViewById(R.id.tv_sure);

        SquareRoundImageView iv_image = view.findViewById(R.id.iv_image);


        CartNumberView cart_view = view.findViewById(R.id.cart_view);
        cart_view.setNumber(1);
        TextView tv_price = view.findViewById(R.id.tv_price);
        TextView tv_former_price = view.findViewById(R.id.tv_former_price);
        TextView tv_specification = view.findViewById(R.id.tv_specification);
        tv_former_price.getPaint().setFlags(Paint.STRIKE_THRU_TEXT_FLAG);
        //商品规格
        FlowLayout fl_special = view.findViewById(R.id.fl_flag);
        NestedScrollView scrollview = view.findViewById(R.id.scrollview);

        List<SpecialItem> list = goodsResult.product_norm;
        //设置默认规格
        final SpecialItem specialItem = list.get(0);
        ImageUtil.loadNet(context,iv_image,specialItem.thumb);
        tv_price.setText("￥"+specialItem.getPrice());
        tv_former_price.setText(specialItem.getOld_price());
        tv_specification.setText("已选：“"+specialItem.name+"”");

        specialItem.isCheck = true;
        for (int i=0;i<list.size();i++) {
            SpecialItem text = list.get(i);
            View normView = View.inflate(context,R.layout.item_service_order_comment_flag,null);
            TextView tv = normView.findViewById(R.id.tv_title);
            if (text.isCheck){
                tv.setTextColor(context.getResources().getColor(R.color.white));
                tv.setBackgroundResource(R.drawable.shape_basecolor50);
            }else {
                tv.setBackgroundResource(R.drawable.shape_line_gray50);
                tv.setTextColor(context.getResources().getColor(R.color.black_title_color));
            }
            tv.setOnClickListener(v -> {
                for (int j=0;j<list.size();j++){
                    View childAt = fl_special.getChildAt(j);
                    TextView textView = childAt.findViewById(R.id.tv_title);
                    SpecialItem specialItem1 = list.get(j);
                    //text 是点击的  specialItem1是遍历的
                    if (specialItem1 == text){
                        //只是为了做传递给后台用。只能去id。其他参数不能取
                        specialItem.id = text.id;
                        specialItem1.isCheck = true;
                    }else {
                        specialItem1.isCheck = false;
                    }
                    if (specialItem1.isCheck){
                        ImageUtil.loadNet(context,iv_image,text.thumb);
                        tv_price.setText("￥"+text.getPrice());
                        tv_former_price.setText(text.getOld_price());
                        tv_specification.setText("已选：“"+text.name+"”");

                        textView.setTextColor(context.getResources().getColor(R.color.white));
                        textView.setBackgroundResource(R.drawable.shape_basecolor50);
                    }else {
                        textView.setBackgroundResource(R.drawable.shape_line_gray50);
                        textView.setTextColor(context.getResources().getColor(R.color.black_title_color));
                    }
                }

            });
            tv.setText(text.name);
            fl_special.addView(normView);
        }
        int measuredHeight = fl_special.getMeasuredHeight();
        LogUtil.e("measuredHeight = "+measuredHeight);
        LogUtil.e("measuredHeight = "+fl_special.getHeight());

        tv_sure.setOnClickListener(v -> {
            listener.onFinish(specialItem.id+"",cart_view.getTv_number().getText().toString().trim());
            dialog.dismiss();
            dialog = null;
        });
        iv_close.setOnClickListener(v -> {
            dialog.dismiss();
            dialog = null;
        });
    }


    public  static void showReceiveCouponDialog(Context context, List<CouponsBean> data) {
        View view = View.inflate(context, R.layout.pop_bottom_selete_coupon, null);
        dialog = DialogUtils.getBottomDialog(context,view);
        ImageView iv_close = view.findViewById(R.id.iv_close);
        RecyclerView rv_coupon = view.findViewById(R.id.rv_coupon);
        rv_coupon.setLayoutManager(new LinearLayoutManager(context));
        CouponReceiveDialogAdapter adpter = new CouponReceiveDialogAdapter();
        rv_coupon.setAdapter(adpter);
        adpter.setNewData(data);
        iv_close.setOnClickListener(v -> {
            dialog.dismiss();
            dialog = null;
        });
    }

    public  static void showServiceCouponDialog(Context context, List<CouponsBean> data) {
        View view = View.inflate(context, R.layout.pop_bottom_selete_coupon, null);
        dialog = DialogUtils.getBottomDialog(context,view);
        ImageView iv_close = view.findViewById(R.id.iv_close);
        RecyclerView rv_coupon = view.findViewById(R.id.rv_coupon);
        rv_coupon.setLayoutManager(new LinearLayoutManager(context));
        CouponReceiveDialogForServiceAdapter adapter = new CouponReceiveDialogForServiceAdapter();
        rv_coupon.setAdapter(adapter);
        adapter.setNewData(data);
        iv_close.setOnClickListener(v -> {
            dialog.dismiss();
            dialog = null;
        });
    }

    public static void showReportDialog(Context context, onSelectListener listener) {
        View view = View.inflate(context, R.layout.pop_bottom_report, null);
        dialog = DialogUtils.getBottomDialog(context, view);
        LinearLayout ll_pullback = view.findViewById(R.id.ll_pullback);
        LinearLayout ll_report = view.findViewById(R.id.ll_report);
        ImageView iv_close = view.findViewById(R.id.iv_close);
        iv_close.setOnClickListener(v -> {
            dialog.dismiss();
            dialog = null;
        });
        ll_pullback.setOnClickListener(v -> {
            listener.onFinish("1");
            dialog.dismiss();
            dialog = null;
        });
        ll_report.setOnClickListener(v -> {
            listener.onFinish("0");
            dialog.dismiss();
            dialog = null;
        });

    }

    public static void showPublishDialog(Context context) {
        View view = View.inflate(context, R.layout.pop_bottom_publish, null);
        dialog = DialogUtils.getBottomDialog(context, view);
        LinearLayout ll_pullback = view.findViewById(R.id.ll_pullback);
        LinearLayout ll_report = view.findViewById(R.id.ll_report);
        ImageView iv_close = view.findViewById(R.id.iv_close);
        iv_close.setOnClickListener(v -> {
            dialog.dismiss();
            dialog = null;
        });
        ll_pullback.setOnClickListener(v -> {
            PublishTopicActivity.launch(context);
            dialog.dismiss();
            dialog = null;
        });
        ll_report.setOnClickListener(v -> {
            PublishPostActivity.launch(context);

            dialog.dismiss();
            dialog = null;
        });

    }


    public static void showSexDialog(Context context, onSelectListener listener) {
        View view = View.inflate(context, R.layout.pop_bottom_sex, null);
        dialog = DialogUtils.getBottomDialog(context, view);
        ImageView iv_woman = view.findViewById(R.id.iv_woman);
        ImageView iv_man = view.findViewById(R.id.iv_man);
        iv_woman.setOnClickListener(v -> {
            listener.onFinish("2");
            dialog.dismiss();
            dialog = null;
        });
        iv_man.setOnClickListener(v -> {
            listener.onFinish("1");
            dialog.dismiss();
            dialog = null;
        });

    }

    public static void showCallPhoneDialog(Context context) {
        View view = View.inflate(context, R.layout.pop_bottom_callphone, null);
        dialog = DialogUtils.getBottomDialog(context, view);
        TextView tv_phone = view.findViewById(R.id.tv_phone);
        RelativeLayout ll_phone = view.findViewById(R.id.ll_phone);
        RelativeLayout tv_cancel = view.findViewById(R.id.tv_cancel);
        ll_phone.setOnClickListener(v -> {
            CommonUtils.callPhone(context,"69765809");
            dialog.dismiss();
            dialog = null;
        });
        tv_cancel.setOnClickListener(v -> {
            dialog.dismiss();
            dialog = null;
        });

    }
    public interface onSelectListener {
        void onFinish(String payString);
    }

    public interface onNormSelectListener {
        void onFinish(String norm_id,String number);
    }
}
