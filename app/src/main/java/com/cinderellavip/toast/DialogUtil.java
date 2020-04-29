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
import com.cinderellavip.ui.activity.find.PublishPostActivity;
import com.cinderellavip.ui.activity.find.PublishTopicActivity;
import com.cinderellavip.weight.CartNumberView;
import com.cinderellavip.weight.SquareRoundImageView;
import com.nex3z.flowlayout.FlowLayout;
import com.tozzais.baselibrary.util.CommonUtils;

import java.util.ArrayList;
import java.util.List;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;


public class DialogUtil {
    private static Dialog dialog;




    private static void addData(Context mContext, FlowLayout flowLayout) {
        List<SpecialItem> list=new ArrayList<>();
        list.add(new SpecialItem(true,"500g"));
        list.add(new SpecialItem(false,"1KG"));
        for (int i=0;i<list.size();i++) {
            SpecialItem text = list.get(i);
            View view = View.inflate(mContext,R.layout.item_service_order_comment_flag,null);
            TextView tv = view.findViewById(R.id.tv_title);
            if (text.isCheck){
                tv.setTextColor(mContext.getResources().getColor(R.color.white));
                tv.setBackgroundResource(R.drawable.shape_basecolor50);
            }else {
                tv.setBackgroundResource(R.drawable.shape_line_gray50);
                tv.setTextColor(mContext.getResources().getColor(R.color.black_title_color));
            }
            tv.setOnClickListener(v -> {
                text.isCheck = !text.isCheck;
                if (text.isCheck){
                    tv.setTextColor(mContext.getResources().getColor(R.color.white));
                    tv.setBackgroundResource(R.drawable.shape_basecolor50);
                }else {
                    tv.setBackgroundResource(R.drawable.shape_line_gray50);
                    tv.setTextColor(mContext.getResources().getColor(R.color.black_title_color));
                }
            });
            tv.setText(text.name);
            flowLayout.addView(view);
        }

    }

    public static void showSpeciSpecialDialog(Context context,  onSelectListener listener) {
        View view = View.inflate(context, R.layout.pop_bottom_specification, null);
        dialog = DialogUtils.getBottomDialog(context, view);
        ImageView iv_close = view.findViewById(R.id.iv_close);
        FlowLayout fl_special = view.findViewById(R.id.fl_flag);
        TextView tv_sure = view.findViewById(R.id.tv_sure);

        SquareRoundImageView iv_image = view.findViewById(R.id.iv_image);
        CartNumberView cart_view = view.findViewById(R.id.cart_view);
        TextView tv_price = view.findViewById(R.id.tv_price);
        TextView tv_former_price = view.findViewById(R.id.tv_former_price);
        TextView tv_specification = view.findViewById(R.id.tv_specification);
        tv_former_price.getPaint().setFlags(Paint.STRIKE_THRU_TEXT_FLAG);
        tv_sure.setOnClickListener(v -> {
            listener.onFinish(cart_view.getTv_number().getText().toString().trim());
            dialog.dismiss();
            dialog = null;
        });
        addData(context, fl_special);
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
            listener.onFinish("灰姑娘");
            dialog.dismiss();
            dialog = null;
        });
        iv_man.setOnClickListener(v -> {
            listener.onFinish("灰小伙");
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

}
