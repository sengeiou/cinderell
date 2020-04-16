package com.cinderellavip.adapter.recycleview;

import android.content.Context;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.cinderellavip.R;
import com.cinderellavip.bean.local.PublishImageBean;
import com.cinderellavip.global.ImageUtil;
import com.cinderellavip.listener.OnPublishImageListener;
import com.cinderellavip.weight.RoundImageView;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class HorizontalAdapter extends RecyclerView.Adapter<HorizontalAdapter.HorizontalViewHolder> {


    private OnPublishImageListener onPublishImageListener;

    private Context mContext;

    private List<PublishImageBean> mList = new ArrayList<>();

    public HorizontalAdapter(Context context,List<PublishImageBean> list,OnPublishImageListener onPublishImageListener) {
        this.mContext = context;
        this.mList = list;
        this.onPublishImageListener = onPublishImageListener;

    }

    @NonNull
    @Override
    public HorizontalViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.item_publish_photo, parent, false);
        return new HorizontalViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull HorizontalViewHolder holder, int position) {
        String path = mList.get(position).path;
        if (!TextUtils.isEmpty(path))
            ImageUtil.loadLocal(mContext,holder.iv_image,path);

        holder.iv_close.setOnClickListener(v -> {
            onPublishImageListener.onImageRemove(position);
        });
    }

    @Override
    public int getItemCount() {
        return mList == null ? 0 : mList.size();
    }

    public class HorizontalViewHolder extends RecyclerView.ViewHolder {

        RoundImageView iv_image;
        ImageView iv_close;

        public HorizontalViewHolder(View itemView) {
            super(itemView);
            iv_image = itemView.findViewById(R.id.iv_image);
            iv_close = itemView.findViewById(R.id.iv_close);
        }
    }
}
