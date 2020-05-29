package com.cinderellavip.adapter.recycleview;


import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.viewholder.BaseViewHolder;
import com.cinderellavip.R;
import com.cinderellavip.bean.net.life.ShortTime;

public class SelectServiceTimeAdapter extends BaseQuickAdapter<ShortTime, BaseViewHolder>  {


    public SelectServiceTimeAdapter() {
        super(R.layout.item_select_service_time, null);
    }


    @Override
    protected void convert(final BaseViewHolder helper, final ShortTime item) {
        int position = helper.getAdapterPosition();
        RelativeLayout ll_root = helper.getView(R.id.ll_root);
        TextView tv_title = helper.getView(R.id.tv_title);
        ImageView iv_select = helper.getView(R.id.iv_select);

        tv_title.setText(item.start);
        if (item.no_time == 1){
            //不可点击
            ll_root.setEnabled(false);
            tv_title.setTextColor(getContext().getColor(R.color.grayText));
            ll_root.setBackgroundResource(R.drawable.shape_line_gray_graysolid);
            iv_select.setVisibility(View.GONE);

        }else if (item.no_time == 0 && !item.isCheck){
            //正常
            ll_root.setEnabled(true);
            tv_title.setTextColor(getContext().getColor(R.color.black_title_color));
            ll_root.setBackgroundResource(R.drawable.shape_line_gray_whitesolid);
            iv_select.setVisibility(View.GONE);
        }else if (item.no_time == 0 && item.isCheck){
            //选中的
            ll_root.setEnabled(true);
            tv_title.setTextColor(getContext().getColor(R.color.yellow_deep));
            ll_root.setBackgroundResource(R.drawable.shape_line_yellow);
            iv_select.setVisibility(View.VISIBLE);
        }
        ll_root.setOnClickListener(v -> {
            for (int i = 0;i<getData().size();i++){
                ShortTime shortTime = getData().get(i);
                shortTime.isCheck = i == position;
            }
            notifyDataSetChanged();
        });


    }


}
