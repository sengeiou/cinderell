package com.cinderellavip.adapter.listview;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;

import com.cinderellavip.R;
import com.cinderellavip.base.BaseAdapter;

import java.util.List;

import butterknife.ButterKnife;

public class EnsureOrderGoodsAdapter extends BaseAdapter<String> {

    public EnsureOrderGoodsAdapter(List<String> mList, Context context) {
        super(mList, context);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        final ViewHolder hodler;

        if (convertView == null) {
            convertView = View.inflate(context, R.layout.item_ensure_order_goods, null);
            hodler = new ViewHolder(convertView);
            convertView.setTag(hodler);
        } else {
            hodler = (ViewHolder) convertView.getTag();
        }

        return convertView;
    }

    static class ViewHolder {




        ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }
}
