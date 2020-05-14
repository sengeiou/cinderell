package com.cinderellavip.global;

import android.content.Context;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;
import com.bumptech.glide.request.RequestOptions;
import com.cinderellavip.R;

import jp.wasabeef.glide.transformations.BlurTransformation;


/**
 * 目前为测试，当路径为空时才向控件imageView中加载图片
 * Created by 32672 on 2019/1/7.
 */

public class ImageUtil {


    public static  void loadNetRound(Context mContext, ImageView imageView, String path){
        //设置图片圆角角度
        RoundedCorners roundedCorners = new RoundedCorners(10);
        //通过RequestOptions扩展功能
        RequestOptions options = RequestOptions.bitmapTransform(roundedCorners)
                .error(R.drawable.shape_gray5);
        Glide.with(mContext).load(path).apply(options).into(imageView);


    }
    public static  void loadNet(Context mContext, ImageView imageView, String path){
        RequestOptions options = new RequestOptions()
                .placeholder(R.drawable.shape_gray5)//图片加载出来前，显示的图片
                .fallback(R.drawable.shape_gray5) //url为空的时候,显示的图片
                .error(R.drawable.shape_gray5);//图片加载失败后，显示的图片
        Glide.with(mContext).load(path).apply(options).into(imageView);
    }

    public static  void loadNet2(Context mContext, ImageView imageView, String path){
        RequestOptions options = new RequestOptions()
                .placeholder(R.drawable.shape_white5)//图片加载出来前，显示的图片
                .fallback(R.drawable.shape_white5) //url为空的时候,显示的图片
                .error(R.drawable.shape_white5);//图片加载失败后，显示的图片
        Glide.with(mContext).load(path).apply(options).into(imageView);
    }

    public static  void loadNet1(Context mContext, ImageView imageView, String path){
        RequestOptions options = new RequestOptions()
                .placeholder(R.drawable.shape_oval_gray)//图片加载出来前，显示的图片
                .fallback(R.drawable.shape_oval_gray) //url为空的时候,显示的图片
                .error(R.drawable.shape_oval_gray);//图片加载失败后，显示的图片
        Glide.with(mContext).load(path).apply(options).into(imageView);
    }


    public static  void loadGauss(Context mContext, ImageView imageView, String path){

        RequestOptions requestOptions = RequestOptions.bitmapTransform(new BlurTransformation(mContext, 12, 1));
        Glide.with(mContext).load(path).apply(requestOptions).into(imageView);


    }

    public static  void loadProduct(Context mContext, ImageView imageView, String path){
        //设置图片圆角角度
        RoundedCorners roundedCorners = new RoundedCorners(10);
        //通过RequestOptions扩展功能
        RequestOptions options = RequestOptions.bitmapTransform(roundedCorners)
                .error(R.drawable.shape_white5);
        Glide.with(mContext).load(path).apply(options).into(imageView);
    }

    public static  void load(Context mContext, ImageView imageView, String path){
        RequestOptions options = new RequestOptions()
                .error(R.drawable.shape_gray5);//图片加载失败后，显示的图片
        Glide.with(mContext).load(path).apply(options).into(imageView);

    }
    public static  void loadNetTest(Context mContext, ImageView imageView, String path){
        RoundedCorners roundedCorners = new RoundedCorners(30);
        //通过RequestOptions扩展功能
        RequestOptions options = new RequestOptions().override(300, 300)
                .error(R.drawable.shape_gray5);
        Glide.with(mContext).load("http://192.168.1.212:9000/FileUpLoad//Image/20186/c96bf354-3243-4f4c-b4e9-dd89a98f2ce6.png").apply(options).into(imageView);
    }
    public static  void loadNetCircle(Context mContext, ImageView imageView, String path){
        //设置图片圆角角度
        RequestOptions options = RequestOptions.circleCropTransform().override(300, 300)
                //圆形
                .circleCrop()
                .error(R.drawable.shape_gray5)
                .circleCrop();
        Glide.with(mContext).load(path).apply(options).into(imageView);



    }
    public static  void loadAvatar(Context mContext, ImageView imageView, String path){
        RequestOptions options = new RequestOptions()
                .error(R.mipmap.avatar_default)    //加载错误之后的错误图
                .override(400,400)  //指定图片的尺寸
                //指定图片的缩放类型为fitCenter （等比例缩放图片，宽或者是高等于ImageView的宽或者是高。）
                .fitCenter()
                //指定图片的缩放类型为centerCrop （等比例缩放图片，直到图片的狂高都大于等于ImageView的宽度，然后截取中间的显示。）
                .centerCrop()
                .circleCrop()//指定图片的缩放类型为centerCrop （圆形）
                .skipMemoryCache(true)  //跳过内存缓存
                .diskCacheStrategy(DiskCacheStrategy.ALL)   //缓存所有版本的图像
                .diskCacheStrategy(DiskCacheStrategy.NONE)  //跳过磁盘缓存
                .diskCacheStrategy(DiskCacheStrategy.DATA)  //只缓存原来分辨率的图片
                .diskCacheStrategy(DiskCacheStrategy.RESOURCE)  //只缓存最终的图片
                ;
        Glide.with(mContext).load(path).apply(options).into(imageView);
    }
    public static  void loadNetAllAddress(Context mContext, ImageView imageView, String path){
        Glide.with(mContext).load(path).into(imageView);
    }

    public static  void loadLocal(Context mContext, ImageView imageView, String path){
        Glide.with(mContext).load(path).into(imageView);
    }
}
