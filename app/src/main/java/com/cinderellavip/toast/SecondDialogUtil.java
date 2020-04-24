package com.cinderellavip.toast;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Bitmap;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.cinderellavip.R;
import com.cinderellavip.listener.ShareClickListener;
import com.cinderellavip.util.image.ImgUtils;
import com.tozzais.baselibrary.util.toast.ToastCommom;


public class SecondDialogUtil {
    private static Dialog dialog;


    public static void showPosterDialog(Context context,  onSelectListener listener) {
        View view = View.inflate(context, R.layout.pop_bottom_poster, null);
        dialog = DialogUtils.getCenterDialog(context, view);
        ImageView iv_avatar = view.findViewById(R.id.iv_avatar);
        TextView tv_name = view.findViewById(R.id.tv_name);
        TextView tv_product_name = view.findViewById(R.id.tv_product_name);
        TextView tv_price = view.findViewById(R.id.tv_price);
        TextView tv_advance_price = view.findViewById(R.id.tv_advance_price);
        ImageView iv_image = view.findViewById(R.id.iv_image);
        LinearLayout ll_poster = view.findViewById(R.id.ll_poster);
        ll_poster.setDrawingCacheEnabled(true);
        ll_poster.buildDrawingCache();
        view.findViewById(R.id.ll_root).setOnClickListener(v -> {
            dialog.dismiss();
            dialog = null;
        });
        view.findViewById(R.id.iv_down).setOnClickListener(v -> {
            Bitmap bitmap = ll_poster.getDrawingCache(); // 获取图片
            boolean isSuccess = ImgUtils.saveImageToGallery(context, bitmap);
            if (isSuccess) {
                listener.onFinish("down",null);
                dialog.dismiss();
                dialog = null;
            }
        });
        view.findViewById(R.id.iv_weChat).setOnClickListener(v -> {
            Bitmap bitmap = ll_poster.getDrawingCache(); // 获取图片
            listener.onFinish("1",bitmap);
        });
        view.findViewById(R.id.iv_weChat_circle).setOnClickListener(v -> {
            Bitmap bitmap = ll_poster.getDrawingCache(); // 获取图片
            listener.onFinish("2",bitmap);
        });

    }

    public static void shareDialog(Context context,  ShareClickListener listener) {
        View view = View.inflate(context, R.layout.pop_bottom_share, null);
        dialog = DialogUtils.getCenterDialog(context, view);
        ImageView iv_avatar = view.findViewById(R.id.iv_avatar);
        TextView tv_name = view.findViewById(R.id.tv_name);
        TextView tv_product_name = view.findViewById(R.id.tv_product_name);
        TextView tv_price = view.findViewById(R.id.tv_price);
        ImageView iv_image = view.findViewById(R.id.iv_image);
        LinearLayout ll_poster = view.findViewById(R.id.ll_poster);
        ll_poster.setDrawingCacheEnabled(true);
        ll_poster.buildDrawingCache();
        view.findViewById(R.id.ll_root).setOnClickListener(v -> {
            dialog.dismiss();
            dialog = null;
        });
        view.findViewById(R.id.iv_down).setOnClickListener(v -> {
            Bitmap bitmap = ll_poster.getDrawingCache(); // 获取图片
            boolean isSuccess = ImgUtils.saveImageToGallery(context, bitmap);
            if (isSuccess) {
                ToastCommom.createToastConfig().ToastShow(context,"保存成功");
                dialog.dismiss();
                dialog = null;
            }
        });
        view.findViewById(R.id.iv_weChat).setOnClickListener(v -> {
            Bitmap bitmap = ll_poster.getDrawingCache(); // 获取图片
            listener.onWeChatClick(bitmap);
        });
        view.findViewById(R.id.iv_weChat_circle).setOnClickListener(v -> {
            Bitmap bitmap = ll_poster.getDrawingCache(); // 获取图片
            listener.onWeChatCircleClick(bitmap);
        });

    }

    public static void showRecommendDialog(Context context,  onSelectListener listener) {
        View view = View.inflate(context, R.layout.pop_bottom_recommend, null);
        dialog = DialogUtils.getCenterDialog(context, view);

        LinearLayout ll_poster = view.findViewById(R.id.ll_poster);
        ll_poster.setDrawingCacheEnabled(true);
        ll_poster.buildDrawingCache();

        view.findViewById(R.id.ll_root).setOnClickListener(v -> {
            dialog.dismiss();
            dialog = null;

        });
        view.findViewById(R.id.iv_down).setOnClickListener(v -> {
            Bitmap bitmap = ll_poster.getDrawingCache(); // 获取图片
            boolean isSuccess = ImgUtils.saveImageToGallery(context, bitmap);
            if (isSuccess) {
                listener.onFinish("down",null);
                dialog.dismiss();
                dialog = null;
            }
        });
        view.findViewById(R.id.iv_weChat).setOnClickListener(v -> {
            Bitmap bitmap = ll_poster.getDrawingCache(); // 获取图片
            listener.onFinish("1",bitmap);
        });
        view.findViewById(R.id.iv_weChat_circle).setOnClickListener(v -> {
            Bitmap bitmap = ll_poster.getDrawingCache(); // 获取图片
            listener.onFinish("2",bitmap);
        });

    }


    public interface onSelectListener {
        void onFinish(String payString, Bitmap bitmap);
    }

}
