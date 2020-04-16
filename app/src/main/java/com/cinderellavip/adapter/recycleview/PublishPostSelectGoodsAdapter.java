package com.cinderellavip.adapter.recycleview;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.cinderellavip.R;
import com.cinderellavip.listener.OnPublishImageListener;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class PublishPostSelectGoodsAdapter extends RecyclerView.Adapter<PublishPostSelectGoodsAdapter.HorizontalViewHolder> {


    private OnPublishImageListener onPublishImageListener;

    private Context mContext;

    private List<String> mList;

    public PublishPostSelectGoodsAdapter(Context context, List<String> list, OnPublishImageListener onPublishImageListener) {
        this.mContext = context;
        this.mList = list;
        this.onPublishImageListener = onPublishImageListener;

    }

    @NonNull
    @Override
    public HorizontalViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.item_publish_post_select_goods, parent, false);
        return new HorizontalViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull HorizontalViewHolder holder, int position) {
        holder.iv_delete.setOnClickListener(v -> {
            onPublishImageListener.onImageRemove(position);
        });
    }

    @Override
    public int getItemCount() {
        return mList == null ? 0 : mList.size();
    }

    public class HorizontalViewHolder extends RecyclerView.ViewHolder {

        ImageView iv_delete;

        public HorizontalViewHolder(View itemView) {
            super(itemView);
            iv_delete = itemView.findViewById(R.id.iv_delete);
        }
    }
}
