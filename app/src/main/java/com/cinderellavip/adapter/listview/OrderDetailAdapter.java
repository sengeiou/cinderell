package com.cinderellavip.adapter.listview;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.cinderellavip.R;
import com.cinderellavip.base.BaseAdapter;
import com.cinderellavip.ui.activity.order.ApplyReturnActivity;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class OrderDetailAdapter extends BaseAdapter<String> {




    public OrderDetailAdapter(List<String> mList, Context context) {
        super(mList, context);
    }

    //是否需要退款
    private boolean isShowReturn;

    public OrderDetailAdapter(List<String> mList, Context context, boolean isShowReturn) {
        super(mList, context);
        this.isShowReturn = isShowReturn;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        final ViewHolder hodler;

        if (convertView == null) {
            convertView = View.inflate(context, R.layout.item_order_goods, null);
            hodler = new ViewHolder(convertView);
            convertView.setTag(hodler);
        } else {
            hodler = (ViewHolder) convertView.getTag();

        }
        if (isShowReturn){
            hodler.tvBtn1.setVisibility(View.VISIBLE);
        }else {
            hodler.tvBtn1.setVisibility(View.GONE);

        }
        hodler.tvBtn1.setOnClickListener(v -> {
            ApplyReturnActivity.launch(context,1);
        });


        return convertView;
    }

    static class ViewHolder {
        @BindView(R.id.tv_btn1)
        TextView tvBtn1;

        ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }
}
