package com.cinderellavip.adapter.recycleview;

import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.viewholder.BaseViewHolder;
import com.cinderellavip.R;
import com.cinderellavip.bean.net.CategoryItem;
import com.cinderellavip.bean.net.HomeCategoryItem;
import com.cinderellavip.bean.net.HomeCategoryResult;
import com.cinderellavip.listener.CategoryClickListener;


public class CategoryHomeAdapter extends BaseQuickAdapter<HomeCategoryItem, BaseViewHolder> {

    private CategoryClickListener categoryClickListener;
    public CategoryHomeAdapter(CategoryClickListener categoryClickListener) {
        super(R.layout.item_category_home, null);
        this.categoryClickListener = categoryClickListener;
    }


    @Override
    protected void convert(final BaseViewHolder helper, final HomeCategoryItem item) {
        int position = helper.getAdapterPosition();

        TextView tv_name = helper.getView(R.id.tv_name);
        if (item.isCheck){
            tv_name.setTextColor(getContext().getResources().getColor(R.color.white));
            tv_name.setBackgroundResource(R.drawable.shape_red50);
        }else {
            tv_name.setTextColor(getContext().getResources().getColor(R.color.black_title_color));
            tv_name.setBackgroundResource(R.drawable.shape_white50);
        }

        tv_name.setText(item.name);
        tv_name.setOnClickListener(view -> {
            categoryClickListener.onCategorySelect(position);
            for (int i = 0;i<getData().size();i++){
                getData().get(i).isCheck = (i == position);
            }
            notifyDataSetChanged();


        });



    }


}
