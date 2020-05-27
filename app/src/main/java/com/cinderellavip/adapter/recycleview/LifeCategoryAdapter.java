package com.cinderellavip.adapter.recycleview;


import android.app.Activity;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.viewholder.BaseViewHolder;
import com.cinderellavip.R;
import com.cinderellavip.bean.net.HomeCategoryItem;
import com.cinderellavip.bean.net.life.LiftHomeCategory;
import com.cinderellavip.global.ImageUtil;
import com.cinderellavip.ui.activity.life.DirectAppointmentActivity;
import com.cinderellavip.ui.activity.life.ServiceCheckListActivity;
import com.cinderellavip.ui.activity.life.ServiceListActivity;
import com.cinderellavip.util.ScreenUtil;


public class LifeCategoryAdapter extends BaseQuickAdapter<LiftHomeCategory, BaseViewHolder> {

    public LifeCategoryAdapter() {
        super(R.layout.item_home_category, null);
    }


    @Override
    protected void convert( BaseViewHolder helper,  LiftHomeCategory item) {
        int position = helper.getAdapterPosition();
//        helper.setText(R.id.tv_number,item);
        ImageView iv_image = helper.getView(R.id.iv_image);
        TextView tv_number = helper.getView(R.id.tv_number);

        ImageUtil.load(getContext(),iv_image,item.icon);
        tv_number.setText(item.name);

        LinearLayout rl_root = helper.getView(R.id.rl_root);
        ViewGroup.LayoutParams linearParams = rl_root.getLayoutParams(); //取控件textView当前的布局参数 linearParams.height = 20;// 控件的高强制设成20
        int screenWidth = ScreenUtil.getScreenWidth((Activity) getContext());
        linearParams.width = screenWidth/5;// 控件的宽强制设成30
        linearParams.height = screenWidth/5;// 控件的宽强制设成30
        rl_root.setLayoutParams(linearParams); //使设置好的布局参数应用到控件


        helper.getView(R.id.rl_root).setOnClickListener(v -> {
            //0:分类  1：服务清单 2：直约
            if (2 == (item.type)){
                DirectAppointmentActivity.launch(getContext());
            }else  if (1 == (item.type)){
                ServiceCheckListActivity.launch((Activity) getContext());
            }else {
                ServiceListActivity.launch(getContext(),item);
            }

        });



    }



}
