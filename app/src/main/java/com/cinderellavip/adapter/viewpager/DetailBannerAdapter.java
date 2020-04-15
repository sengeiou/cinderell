package com.cinderellavip.adapter.viewpager;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.VideoView;


import com.cinderellavip.R;
import com.cinderellavip.bean.local.GoodsDetialBanner;
import com.cinderellavip.global.ImageUtil;
import com.cinderellavip.http.HttpUrl;
import com.cinderellavip.ui.BigImageActivity;

import java.util.ArrayList;
import java.util.List;

import androidx.viewpager.widget.PagerAdapter;


/**
 * Created by Administrator on 2017/2/6.
 *
 */

public class DetailBannerAdapter extends PagerAdapter {

    private List<GoodsDetialBanner> mList;
    private Context mContext;

    //视频是否是全地址
    private boolean videoIsFullPath;

    public DetailBannerAdapter(List<GoodsDetialBanner> mList, Context mContext) {
        this.mList = mList;
        this.mContext = mContext;
    }

    public DetailBannerAdapter(List<GoodsDetialBanner> mList, Context mContext, boolean videoIsFullPath) {
        this.mList = mList;
        this.mContext = mContext;
        this.videoIsFullPath = videoIsFullPath;
    }

    @Override
    public int getCount() {
        return mList == null?0:mList.size();
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((View) object);
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        GoodsDetialBanner bean = mList.get(position);
        View view;
        if (bean.isVideo){
             view = View.inflate(mContext, R.layout.item_detail_video,null);
             VideoView videoView = view.findViewById(R.id.video);
//            StandardVideoController mController = new StandardVideoController(mContext);
//            ImageView thumb = mController.getThumb();
//            mController.setEnableOrientation(true);
//            mController.setTitle("");
//            if (videoIsFullPath){
//                videoView.setUrl(bean.logo);
//            }else {
//                videoView.setUrl(HttpUrl.image_url+bean.logo);
//            }
//            videoView.setPlayerFactory(ExoMediaPlayerFactory.create());
//            ImageUtil.loadNet(mContext,thumb,bean.pic);
////
//            videoView.setVideoController(mController);
        }else {
            view = View.inflate(mContext, R.layout.item_detail_pic,null);
//            ImageView iv_image = view.findViewById(R.id.iv_image);
//            ImageUtil.loadNet(mContext,iv_image,bean.getXBannerUrl());
//            List<String> list = new ArrayList<>();
//            for (GoodsDetialBanner item:mList){
//                if (!item.isVideo){
//                    list.add(item.logo);
//                }
//            }
//            String[] array=list.toArray(new String[list.size()]);
//            iv_image.setOnClickListener(v -> {
//                BigImageActivity.launch(mContext,array,mList.get(0).isVideo?position-1:position,BigImageActivity.NET);
//            });
//            BigImageActivity.launch();
        }
        container.addView(view);
        return view;
    }



}



