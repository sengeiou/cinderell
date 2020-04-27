package com.cinderellavip.adapter.recycleview;

import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.viewholder.BaseViewHolder;
import com.cinderellavip.R;
import com.cinderellavip.bean.net.CategoryItem;
import com.cinderellavip.listener.CategoryClickListener;

import java.util.List;


public class CategoryAdapter extends BaseQuickAdapter<CategoryItem, BaseViewHolder> {

    private CategoryClickListener categoryClickListener;
    public CategoryAdapter(CategoryClickListener categoryClickListener) {
        super(R.layout.item_category, null);
        this.categoryClickListener = categoryClickListener;
    }


    @Override
    protected void convert(final BaseViewHolder helper, final CategoryItem item) {
        int position = helper.getAdapterPosition();

        TextView tv_name = helper.getView(R.id.tv_name);
        LinearLayout ll_root = helper.getView(R.id.ll_root);
        ImageView iv_select = helper.getView(R.id.iv_select);
        if (item.isCheck){
            iv_select.setVisibility(View.VISIBLE);
            ll_root.setBackgroundColor(getContext().getResources().getColor(R.color.white));
            tv_name.setTextColor(getContext().getResources().getColor(R.color.yellow_deep));
        }else {
            ll_root.setBackgroundColor(getContext().getResources().getColor(R.color.gray_bg));
            iv_select.setVisibility(View.INVISIBLE);
            tv_name.setTextColor(getContext().getResources().getColor(R.color.black_title_color));
        }

        tv_name.setText(item.type_name);
        ll_root.setOnClickListener(view -> {
            List<CategoryItem> data = getData();
            for (int i = 0; i< data.size(); i++){
                data.get(i).isCheck = (i == position);
            }
            notifyDataSetChanged();
        });



    }


}
