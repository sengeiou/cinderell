package com.cinderellavip.adapter.recycleview;

import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.module.LoadMoreModule;
import com.chad.library.adapter.base.viewholder.BaseViewHolder;
import com.cinderellavip.R;
import com.cinderellavip.bean.local.PublishImageBean;
import com.cinderellavip.global.ImageUtil;
import com.cinderellavip.listener.OnPublishImageListener;
import com.cinderellavip.weight.RoundImageView;


public class PublishImageAdapter extends BaseQuickAdapter<PublishImageBean, BaseViewHolder> implements LoadMoreModule {

    private OnPublishImageListener onPublishImageListener;
    public PublishImageAdapter(OnPublishImageListener onPublishImageListener) {
        super(R.layout.item_publish_photo, null);
        this.onPublishImageListener = onPublishImageListener;
    }


    @Override
    protected void convert(final BaseViewHolder helper, final PublishImageBean item) {
        int position = helper.getAdapterPosition();

        RoundImageView iv_image = helper.getView(R.id.iv_image);
        ImageView iv_close = helper.getView(R.id.iv_close);

        if (!TextUtils.isEmpty(item.path))
        ImageUtil.loadLocal(getContext(),iv_image,item.path);

        iv_close.setOnClickListener(v -> {
            onPublishImageListener.onImageRemove(position);
        });


    }


}
