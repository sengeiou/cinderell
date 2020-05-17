package com.cinderellavip.adapter.viewpager;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.cinderellavip.R;
import com.cinderellavip.bean.local.GoodsDetialBanner;
import com.cinderellavip.global.ImageUtil;
import com.cinderellavip.ui.BigImageActivity;
import com.dueeeke.videocontroller.StandardVideoController;
import com.dueeeke.videocontroller.component.CompleteView;
import com.dueeeke.videocontroller.component.ErrorView;
import com.dueeeke.videocontroller.component.GestureView;
import com.dueeeke.videocontroller.component.PrepareView;
import com.dueeeke.videocontroller.component.VodControlView;
import com.dueeeke.videoplayer.player.VideoView;

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


    public DetailBannerAdapter(List<GoodsDetialBanner> mList, Context mContext) {
        this.mList = mList;
        this.mContext = mContext;
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
    public View instantiateItem(ViewGroup container, int position) {
        GoodsDetialBanner bean = mList.get(position);
        View view;
        if (bean.isVideo){
             view = View.inflate(mContext, R.layout.item_detail_video,null);
            StandardVideoController controller = new StandardVideoController(mContext);
            //根据屏幕方向自动进入/退出全屏
            controller.setEnableOrientation(true);

            VideoView player = view.findViewById(R.id.player);

            PrepareView prepareView = new PrepareView(mContext);//准备播放界面

            prepareView.getmStartPlay().setImageResource(R.mipmap.paly_red);
            prepareView.getmStartPlay().setOnClickListener(view1 -> {
                player.start();
            });

            ImageView thumb = prepareView.findViewById(R.id.thumb);//封面图
            ImageUtil.loadNet(mContext,thumb,bean.pic);

            controller.addControlComponent(prepareView);

            CompleteView completeView = new CompleteView(mContext);
            ImageUtil.loadNet(mContext,completeView.mThumb,bean.pic);

            controller.addControlComponent(completeView);//自动完成播放界面

            controller.addControlComponent(new ErrorView(mContext));//错误界面

            VodControlView vodControlView = new VodControlView(mContext);//点播控制条
            //是否显示底部进度条。默认显示
            controller.addControlComponent(vodControlView);

            GestureView gestureControlView = new GestureView(mContext);//滑动控制视图
            controller.addControlComponent(gestureControlView);
            //根据是否为直播决定是否需要滑动调节进度
            controller.setCanChangePosition(true);

//            /如果你不想要UI，不要设置控制器即可
            player.setVideoController(controller);

            player.setUrl(bean.video);

            thumb.setOnClickListener(view1 -> clickPosition(position));
            completeView.mThumb.setOnClickListener(view1 -> clickPosition(position));
//

        }else {
            view = View.inflate(mContext, R.layout.item_detail_pic,null);
            ImageView iv_image = view.findViewById(R.id.iv_image);
            ImageUtil.loadNet(mContext,iv_image,bean.pic);
            iv_image.setOnClickListener(v -> {
                clickPosition(position);
            });
        }
        container.addView(view);
        return view;
    }

    private void clickPosition(int position){
        List<String> list = new ArrayList<>();
        for (GoodsDetialBanner item:mList){
                list.add(item.pic);
        }
        String[] array=list.toArray(new String[list.size()]);
        BigImageActivity.launch(mContext,array,position);
    }




}



