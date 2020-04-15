package com.cinderellavip.toast;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Paint;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.cinderellavip.R;
import com.cinderellavip.global.ImageUtil;
import com.cinderellavip.weight.FlowLayout;
import com.cinderellavip.weight.SquareRoundImageView;
import com.tozzais.baselibrary.util.DpUtil;
import com.tozzais.baselibrary.util.toast.ToastCommom;

import java.util.ArrayList;
import java.util.List;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;


public class DialogUtil {
    private static Dialog dialog;




    private static void addData(Context mContext, FlowLayout flowLayout, List<String> list) {
        //往容器内添加TextView数据
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        layoutParams.setMargins(DpUtil.dip2px(mContext, 12), DpUtil.dip2px(mContext, 5),
                0, DpUtil.dip2px(mContext, 5));
        if (flowLayout != null) {
            flowLayout.removeAllViews();
        }
        for (int i = 0; i < list.size(); i++) {
            final TextView tv = new TextView(mContext);
            tv.setPadding(DpUtil.dip2px(mContext, 15), DpUtil.dip2px(mContext, 3),
                    DpUtil.dip2px(mContext, 15), DpUtil.dip2px(mContext, 3));
            tv.setText(list.get(i));
            tv.setMaxEms(10);
            tv.setSingleLine();
            tv.setTextSize(14);
            tv.setTextColor(mContext.getResources().getColor(R.color.white));
            tv.setBackgroundResource(R.drawable.shape_red50);
            tv.setLayoutParams(layoutParams);
            tv.setOnClickListener(v -> {
//				SearchResultActivity.launch(mContext,tv.getText().toString());
            });
            flowLayout.addView(tv, layoutParams);
        }

    }

    public static void showSpeciSpecialDialog(Context context,  onSelectListener listener) {
        View view = View.inflate(context, R.layout.pop_bottom_specification, null);
        dialog = DialogUtils.getBottomDialog(context, view);
        ImageView iv_close = view.findViewById(R.id.iv_close);
        FlowLayout fl_special = view.findViewById(R.id.fl_special);
        View iv_reduce = view.findViewById(R.id.iv_reduce);
        TextView tv_number = view.findViewById(R.id.tv_number);
        TextView tv_sure = view.findViewById(R.id.tv_sure);
        View iv_add = view.findViewById(R.id.iv_add);

        SquareRoundImageView iv_image = view.findViewById(R.id.iv_image);
        TextView tv_price = view.findViewById(R.id.tv_price);
        TextView tv_former_price = view.findViewById(R.id.tv_former_price);
        TextView tv_specification = view.findViewById(R.id.tv_specification);
//        tv_price.setText("会员$" + details.getVip_price());
//        tv_former_price.setText("原价$" + details.getNo_vip_price());
        tv_former_price.getPaint().setFlags(Paint.STRIKE_THRU_TEXT_FLAG);
//        tv_specification.setText("已选“" + details.norm + "”");
//        ImageUtil.loadNet(context, iv_image, details.logo);

        iv_reduce.setOnClickListener(v -> {
            String trim = tv_number.getText().toString().trim();
            int number = Integer.parseInt(trim);
            if (number > 1) {
                tv_number.setText(number - 1 + "");
            }
        });
        iv_add.setOnClickListener(v -> {
            String trim = tv_number.getText().toString().trim();
            int number = Integer.parseInt(trim);
            tv_number.setText(number + 1 + "");
        });
        tv_sure.setOnClickListener(v -> {
            listener.onFinish(tv_number.getText().toString().trim());
            dialog.dismiss();
            dialog = null;
        });
        List<String> list = new ArrayList<>();
        list.add("500g");
        list.add("1kg");
        addData(context, fl_special, list);
        iv_close.setOnClickListener(v -> {
            dialog.dismiss();
            dialog = null;
        });
    }



    public interface onSelectListener {
        void onFinish(String payString);
    }

}
