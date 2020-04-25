package com.cinderellavip.adapter.viewpager;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.cinderellavip.R;

import java.util.List;

import androidx.viewpager.widget.PagerAdapter;


/**
 * Created by Administrator on 2017/2/6.
 *
 */

public class BannerViewPagerAdapter extends PagerAdapter {

    private List<Integer> mList;
    private Context mContext;


    public BannerViewPagerAdapter(List<Integer> mList, Context mContext) {
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
        Integer bean = mList.get(position);
        View view;

            view = View.inflate(mContext, R.layout.item_detail_pic,null);
            ImageView iv_image = view.findViewById(R.id.iv_image);
        iv_image.setImageResource(bean);
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
        container.addView(view);
        return view;
    }



}



