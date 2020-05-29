package com.cinderellavip.adapter.viewpager;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.cinderellavip.R;
import com.cinderellavip.global.ImageUtil;
import com.cinderellavip.ui.BigImageActivity;

import java.util.List;

import androidx.viewpager.widget.PagerAdapter;


/**
 * Created by Administrator on 2017/2/6.
 *
 */

public class BannerViewPagerAdapter extends PagerAdapter {

    private List<String> mList;
    private Context mContext;


    public BannerViewPagerAdapter(List<String> mList, Context mContext) {
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
    public Object instantiateItem(ViewGroup container, int position) {
        String bean = mList.get(position);
        View view;

            view = View.inflate(mContext, R.layout.item_detail_pic,null);
            ImageView iv_image = view.findViewById(R.id.iv_image);
            ImageUtil.loadNet(mContext,iv_image,bean);
        iv_image.setOnClickListener(v -> {
            String[] s = new String[]{bean};
            BigImageActivity.launch(mContext,s,0);
        });
        container.addView(view);
        return view;
    }



}



