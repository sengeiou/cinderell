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
import com.cinderellavip.util.ScreenUtil;
import com.cinderellavip.weight.RoundImageView;
import com.tozzais.baselibrary.util.DpUtil;


public class MineOrderCommentImageAdpter extends BaseQuickAdapter<OrderCommentImageItemBean, BaseViewHolder> {



    private OnDoublePositionClickListener listener;
    private int parentPosition;
    int number;
    public MineOrderCommentImageAdpter(OnDoublePositionClickListener listener, int parentPosition, int number) {
        super(R.layout.item_order_comment_image, null);
        this.listener = listener;
        this.parentPosition = parentPosition;
        this.number = number;

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
                iv_image.setImageResource(R.mipmap.add_upload_image);
        }else {
            iv_image_close.setVisibility(View.VISIBLE);
            ImageUtil.loadLocal(getContext(),iv_image,item.path);
        }
        iv_image_close.setOnClickListener(v -> {
            //图片
            getData().remove(position);
            if (!TextUtils.isEmpty( getData().get( getData().size()-1).path)){
                getData().add( getData().size(),new OrderCommentImageItemBean());
            }
            notifyDataSetChanged();
        });

        iv_image.setOnClickListener(v -> {
            listener.onClick(parentPosition,position);
        });

        ViewGroup.LayoutParams linearParams = ll_root.getLayoutParams();
        linearParams.width = ((screenWidth - DpUtil.dip2px(getContext(),12*(number-1)))/number);// 控件的宽强制设成30
        linearParams.height = linearParams.width;// 控件的宽强制设成30
        ll_root.setLayoutParams(linearParams); //使设置好的布局参数应用到控件

//        LogUtil.e(""+linearParams.width);

    }


}
