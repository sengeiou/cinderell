package com.cinderellavip.adapter.recycleview;

import android.app.Activity;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.viewholder.BaseViewHolder;
import com.cinderellavip.R;
import com.cinderellavip.bean.OrderCommentImageItemBean;
import com.cinderellavip.global.ImageUtil;
import com.cinderellavip.listener.OnDoublePositionClickListener;
import com.cinderellavip.listener.OnPublishImageListener;
import com.cinderellavip.util.ScreenUtil;
import com.cinderellavip.weight.RoundImageView;
import com.tozzais.baselibrary.util.DpUtil;


public class MineServiceCommentImageAdapter extends BaseQuickAdapter<OrderCommentImageItemBean, BaseViewHolder> {




    private OnPublishImageListener listener;
    public MineServiceCommentImageAdapter( OnPublishImageListener listener) {
        super(R.layout.item_order_comment_image, null);
        this.listener = listener;

    }

    @Override
    protected void convert( BaseViewHolder helper, OrderCommentImageItemBean item) {
        int screenWidth = ScreenUtil.getScreenWidth((Activity) getContext()) - DpUtil.dip2px(getContext(),24);
        int position = helper.getAdapterPosition();
        RoundImageView iv_image = helper.getView(R.id.iv_image);
        ImageView iv_image_close = helper.getView(R.id.iv_image_close);
        RelativeLayout ll_root = helper.getView(R.id.ll_root);

        if (TextUtils.isEmpty(item.path)){
            iv_image_close.setVisibility(View.GONE);
                iv_image.setImageResource(R.mipmap.add_pic_service_order_comment);
        }else {
            iv_image_close.setVisibility(View.VISIBLE);
            ImageUtil.loadLocal(getContext(),iv_image,item.path);
        }
        iv_image_close.setOnClickListener(v -> {
            //图片
            listener.onImageRemove(position);

        });

        iv_image.setOnClickListener(v -> {
            listener.onImageClick(position);
        });

        ViewGroup.LayoutParams linearParams = ll_root.getLayoutParams();
        linearParams.width = ((screenWidth - DpUtil.dip2px(getContext(),12*(4-1)))/4);// 控件的宽强制设成30
        linearParams.height = linearParams.width;// 控件的宽强制设成30
        ll_root.setLayoutParams(linearParams); //使设置好的布局参数应用到控件

//        LogUtil.e(""+linearParams.width);

    }


}
