package com.cinderellavip.adapter.recycleview;

import android.app.Activity;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.module.LoadMoreModule;
import com.chad.library.adapter.base.viewholder.BaseViewHolder;
import com.cinderellavip.R;
import com.cinderellavip.bean.local.ShareImageItem;
import com.cinderellavip.global.ImageUtil;
import com.cinderellavip.util.ScreenUtil;
import com.cinderellavip.weight.RoundImageView;
import com.tozzais.baselibrary.util.DpUtil;


public class ImageShareAdapter extends BaseQuickAdapter<ShareImageItem, BaseViewHolder> implements LoadMoreModule {



    public ImageShareAdapter() {
        super(R.layout.item_image_share, null);

    }

    @Override
    protected void convert( BaseViewHolder helper, ShareImageItem item) {
        int screenWidth = ScreenUtil.getScreenWidth((Activity) getContext()) - DpUtil.dip2px(getContext(),24);
        int position = helper.getAdapterPosition();
        RoundImageView iv_image = helper.getView(R.id.iv_image);
        ImageView iv_image_close = helper.getView(R.id.iv_image_close);
        RelativeLayout ll_root = helper.getView(R.id.ll_root);



        ImageUtil.loadNet(getContext(),iv_image,item.path);
        if (!item.isCheck){
            iv_image_close.setImageResource(R.mipmap.share_image_default);
        }else {
            iv_image_close.setImageResource(R.mipmap.share_image_select);
        }
        iv_image.setOnClickListener(v -> {


            item.isCheck = !item.isCheck ;
            notifyDataSetChanged();


        });

        ViewGroup.LayoutParams linearParams = ll_root.getLayoutParams();
        linearParams.width = ((screenWidth - DpUtil.dip2px(getContext(),12*(4-1)))/4);// 控件的宽强制设成30
        linearParams.height = linearParams.width;// 控件的宽强制设成30
        ll_root.setLayoutParams(linearParams); //使设置好的布局参数应用到控件

//        LogUtil.e(""+linearParams.width);

    }


}
