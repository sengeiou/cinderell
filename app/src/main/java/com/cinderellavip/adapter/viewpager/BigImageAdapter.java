package com.cinderellavip.adapter.viewpager;

import android.app.Activity;
import android.content.Context;
import android.view.View;
import android.view.ViewGroup;

import com.cinderellavip.R;
import com.cinderellavip.global.ImageUtil;
import com.cinderellavip.weight.scaleimage.PhotoView;

import androidx.viewpager.widget.PagerAdapter;


/**
 * Created by Administrator on 2017/2/6.
 */

public class BigImageAdapter extends PagerAdapter {

    private String[] mList;
    private Context mContext;

    public BigImageAdapter(String[] mList, Context mContext) {
        this.mList = mList;
        this.mContext = mContext;
    }

    @Override
    public int getCount() {
        return mList == null?0:mList.length;
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
        View view = View.inflate(mContext, R.layout.item_big_image,null);
        PhotoView photoView = view.findViewById(R.id.photoView);
        ImageUtil.loadNet(mContext,photoView,mList[position]);
        photoView.setClickListener(()->{
            ((Activity)mContext).finish();
        });
        container.addView(view);
        return view;
    }

}



