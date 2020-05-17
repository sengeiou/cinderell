package com.cinderellavip.adapter.recycleview;


import android.text.SpannableString;
import android.text.Spanned;
import android.text.style.ForegroundColorSpan;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.module.LoadMoreModule;
import com.chad.library.adapter.base.viewholder.BaseViewHolder;
import com.cinderellavip.R;
import com.cinderellavip.bean.net.find.FindItem;
import com.cinderellavip.global.ImageUtil;

import org.jetbrains.annotations.NotNull;


/**
 *
 */
public class FindAdapter extends BaseQuickAdapter<FindItem, BaseViewHolder> implements LoadMoreModule {

    public FindAdapter() {
        super(R.layout.item_find, null);

    }
    private boolean isStag;
    public FindAdapter(boolean isStag) {
        super(R.layout.item_find, null);
        this.isStag = isStag;

    }


    @Override
    protected void convert(@NotNull BaseViewHolder holder, @NotNull  FindItem findItem) {
        int position = holder.getLayoutPosition();

        ImageView iv_image = holder.getView(R.id.iv_image);
        ImageView iv_avatar = holder.getView(R.id.iv_avatar);
        TextView tv_topic = holder.getView(R.id.tv_topic);

        ImageUtil.loadNet(getContext(),iv_image,findItem.thumb);
        ImageUtil.loadNet(getContext(),iv_avatar,findItem.user_avatar);

        holder.setText(R.id.tv_title,findItem.title)
                .setText(R.id.tv_name,findItem.user_name)
                .setText(R.id.tv_scan_number,findItem.see_num+"");

       TextView tv_content =  holder.getView(R.id.tv_content);




       if (findItem.isTopic()){
           tv_content.setText(findItem.content);
           tv_topic.setVisibility(View.VISIBLE);

       }else {
           String s = findItem.topic+""+findItem.content;

           SpannableString spannableString = new SpannableString(s);
           spannableString.setSpan(new ForegroundColorSpan(getContext().getColor(R.color.baseColor)), 0,
                   findItem.topic.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
           tv_content.setText(spannableString);
           tv_topic.setVisibility(View.GONE);

       }




    }

}
