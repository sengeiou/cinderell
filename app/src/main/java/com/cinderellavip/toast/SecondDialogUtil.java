package com.cinderellavip.toast;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Bitmap;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.cinderellavip.R;
import com.cinderellavip.bean.net.goods.GoodsInfo;
import com.cinderellavip.bean.net.goods.GoodsResult;
import com.cinderellavip.bean.net.goods.GroupInfo;
import com.cinderellavip.bean.net.goods.SpikeInfo;
import com.cinderellavip.bean.net.life.ServiceProjectDetail;
import com.cinderellavip.bean.net.mine.MineInfo;
import com.cinderellavip.bean.net.order.OrderGoodsInfo;
import com.cinderellavip.global.GlobalParam;
import com.cinderellavip.global.ImageUtil;
import com.cinderellavip.global.ShareConstant;
import com.cinderellavip.listener.ShareClickListener;
import com.cinderellavip.util.ClipBoardUtil;
import com.cinderellavip.util.QRCodeUtil;
import com.cinderellavip.util.image.ImgUtils;
import com.tozzais.baselibrary.util.DpUtil;
import com.tozzais.baselibrary.util.toast.ToastCommom;


public class SecondDialogUtil {
    private static Dialog dialog;


    public static void showPosterDialog(Context context,
                                        GoodsResult goodsResult, Bitmap appletsCode, onSelectListener listener) {
        View view = View.inflate(context, R.layout.pop_bottom_poster, null);
        GoodsInfo product_info = goodsResult.product_info;
        dialog = DialogUtils.getCenterDialog(context, view);
        ImageView iv_avatar = view.findViewById(R.id.iv_avatar);
        ImageView iv_code = view.findViewById(R.id.iv_code);
        MineInfo userBean = GlobalParam.getUserBean();

        TextView tv_name = view.findViewById(R.id.tv_name);
        TextView tv_product_name = view.findViewById(R.id.tv_product_name);
        TextView tv_price = view.findViewById(R.id.tv_price);
        ImageView iv_image = view.findViewById(R.id.iv_image);

        ImageUtil.loadNet(context,iv_avatar,userBean.user_avatar);
        tv_name.setText(userBean.username);
        ImageUtil.loadNet(context, iv_image, product_info.images.get(0));
        iv_code.setImageBitmap(appletsCode);
        tv_product_name.setText(product_info.name);
        if (product_info.hasGroup ){
            //团购
            GroupInfo group_info = goodsResult.group_info;
            tv_price.setText("￥"+group_info.getGroup_price());
        }else if (product_info.hasSpike){
            //秒杀
            SpikeInfo group_info = goodsResult.spike_info;
            tv_price.setText("￥"+group_info.getSpikePrice());
        }else {
            tv_price.setText("￥"+product_info.getPrice());
        }


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

    public static void showPosterGroupDialog(Context context,
                                             OrderGoodsInfo goodsResult, String appletsCode, onSelectListener listener) {
        View view = View.inflate(context, R.layout.pop_bottom_poster, null);
        dialog = DialogUtils.getCenterDialog(context, view);
        ImageView iv_avatar = view.findViewById(R.id.iv_avatar);
        ImageView iv_code = view.findViewById(R.id.iv_code);
        MineInfo userBean = GlobalParam.getUserBean();

        TextView tv_name = view.findViewById(R.id.tv_name);
        TextView tv_product_name = view.findViewById(R.id.tv_product_name);
        TextView tv_price = view.findViewById(R.id.tv_price);
        ImageView iv_image = view.findViewById(R.id.iv_image);

        ImageUtil.loadNet(context,iv_avatar,userBean.user_avatar);
        tv_name.setText(userBean.username);
        ImageUtil.loadNet(context, iv_image, goodsResult.product_thumb);
        ImageUtil.loadNet(context, iv_code, appletsCode);
        tv_product_name.setText(goodsResult.product_name);
        tv_price.setText("￥"+goodsResult.product_price);

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

    public static void shareDialog(Context context, ServiceProjectDetail serviceProjectDetail, ShareClickListener listener) {
        View view = View.inflate(context, R.layout.pop_bottom_share, null);
        dialog = DialogUtils.getCenterDialog(context, view);
        ImageView iv_avatar = view.findViewById(R.id.iv_avatar);
        TextView tv_name = view.findViewById(R.id.tv_name);
        TextView tv_product_name = view.findViewById(R.id.tv_product_name);
        TextView tv_price = view.findViewById(R.id.tv_price);
        ImageView iv_image = view.findViewById(R.id.iv_image);
        ImageView iv_code = view.findViewById(R.id.iv_code);

        MineInfo userBean = GlobalParam.getUserBean();
        ImageUtil.loadNet(context,iv_avatar,userBean.user_avatar);
        tv_name.setText(userBean.username);

        ImageUtil.loadNet(context,iv_image,serviceProjectDetail.thumb_nail);
        tv_price.setText(serviceProjectDetail.title);

        String recommendCode = GlobalParam.getRecommendCode();
        String share = ShareConstant.REGISTER + recommendCode;
        Bitmap qrCode = QRCodeUtil.createQRCode(share, DpUtil.dip2px(context, 60));
        iv_code.setImageBitmap(qrCode);


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
        ImageView iv_avatar = view.findViewById(R.id.iv_avatar);
        TextView tv_name = view.findViewById(R.id.tv_name);
        TextView tv_code = view.findViewById(R.id.tv_code);
        TextView tv_copy_code = view.findViewById(R.id.tv_copy_code);
        ImageView iv_image = view.findViewById(R.id.iv_image);

        String recommendCode = GlobalParam.getRecommendCode();
        String share = ShareConstant.REGISTER + recommendCode;
        Bitmap qrCode = QRCodeUtil.createQRCode(share, DpUtil.dip2px(context,150));
        iv_image.setImageBitmap(qrCode);
        tv_code.setText(recommendCode);

        MineInfo userBean = GlobalParam.getUserBean();
        ImageUtil.loadNet(context,iv_avatar,userBean.user_avatar);
        tv_name.setText(userBean.username);

        LinearLayout ll_poster = view.findViewById(R.id.ll_poster);
        ll_poster.setDrawingCacheEnabled(true);
        ll_poster.buildDrawingCache();

        view.findViewById(R.id.ll_root).setOnClickListener(v -> {
            dialog.dismiss();
            dialog = null;

        });
        tv_copy_code.setOnClickListener(v -> {
            ClipBoardUtil.copy(context,recommendCode);

        });
        view.findViewById(R.id.iv_down).setOnClickListener(v -> {
            tv_copy_code.setVisibility(View.GONE);
            Bitmap bitmap = ll_poster.getDrawingCache(); // 获取图片
            boolean isSuccess = ImgUtils.saveImageToGallery(context, bitmap);
            if (isSuccess) {
                listener.onFinish("down",null);
                dialog.dismiss();
                dialog = null;
            }
            tv_copy_code.setVisibility(View.VISIBLE);
        });
        view.findViewById(R.id.iv_weChat).setOnClickListener(v -> {
            tv_copy_code.setVisibility(View.GONE);
            Bitmap bitmap = ll_poster.getDrawingCache(); // 获取图片
            listener.onFinish("1",bitmap);
            tv_copy_code.setVisibility(View.VISIBLE);
        });
        view.findViewById(R.id.iv_weChat_circle).setOnClickListener(v -> {
            tv_copy_code.setVisibility(View.GONE);
            Bitmap bitmap = ll_poster.getDrawingCache(); // 获取图片
            listener.onFinish("2",bitmap);
            tv_copy_code.setVisibility(View.VISIBLE);
        });

    }


    public interface onSelectListener {
        void onFinish(String payString, Bitmap bitmap);
    }

}
