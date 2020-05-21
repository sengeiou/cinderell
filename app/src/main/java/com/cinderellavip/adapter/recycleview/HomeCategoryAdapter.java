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
import com.cinderellavip.bean.net.home.CateMoreList;
import com.cinderellavip.global.ImageUtil;
import com.cinderellavip.http.ApiManager;
import com.cinderellavip.http.BaseResult;
import com.cinderellavip.http.ListResult;
import com.cinderellavip.http.Response;
import com.cinderellavip.ui.activity.home.GoodsListActivity;
import com.cinderellavip.util.ScreenUtil;
import com.cinderellavip.util.dialog.RightDialogUtil;
import com.tozzais.baselibrary.http.RxHttp;

import java.util.TreeMap;


public class HomeCategoryAdapter extends BaseQuickAdapter<HomeCategoryItem, BaseViewHolder> {

    /**
     * 为了 一级分类下  加载更多 使用 。如果为null说明是首页
     */
    private HomeCategoryItem homeCategoryItem;
    public HomeCategoryAdapter(HomeCategoryItem homeCategoryItem) {
        super(R.layout.item_home_category, null);
        this.homeCategoryItem = homeCategoryItem;
    }

    private onCategoryClick onCategoryClick;
    public HomeCategoryAdapter(onCategoryClick onCategoryClick) {
        super(R.layout.item_home_category, null);
        this.onCategoryClick = onCategoryClick;
    }
    public interface onCategoryClick{
        void onCategoryClick(HomeCategoryItem item);
    }


    @Override
    protected void convert( BaseViewHolder helper,  HomeCategoryItem item) {
        int position = helper.getAdapterPosition();
//        helper.setText(R.id.tv_number,item);
        ImageView iv_image = helper.getView(R.id.iv_image);
        TextView tv_number = helper.getView(R.id.tv_number);
        if ("-1".equals(item.type)){
            iv_image.setImageResource(R.mipmap.icon_more);
            tv_number.setText("更多");
        }else {
            ImageUtil.loadNet1(getContext(),iv_image,item.image);
            tv_number.setText(item.name);
        }
        LinearLayout rl_root = helper.getView(R.id.rl_root);
        ViewGroup.LayoutParams linearParams = rl_root.getLayoutParams(); //取控件textView当前的布局参数 linearParams.height = 20;// 控件的高强制设成20
        int screenWidth = ScreenUtil.getScreenWidth((Activity) getContext());
        linearParams.width = screenWidth/5;// 控件的宽强制设成30
        linearParams.height = screenWidth/5;// 控件的宽强制设成30
        rl_root.setLayoutParams(linearParams); //使设置好的布局参数应用到控件


        helper.getView(R.id.rl_root).setOnClickListener(v -> {
            if ("-1".equals(item.type)){
               getGoods();
            }else if (homeCategoryItem != null && "3".equals(item.type)){
                GoodsListActivity.launch(getContext(),item.name,item.id);
            }else if (onCategoryClick != null ){
                onCategoryClick.onCategoryClick(item);
            }
        });





    }

    private void getGoods(){
        TreeMap<String, String> hashMap = new TreeMap<>();
        hashMap.put("first_category_id", ""+homeCategoryItem.id);
        new RxHttp<BaseResult<ListResult<CateMoreList>>>().send(ApiManager.getService().getHomeMoreCate(hashMap),
                new Response<BaseResult<ListResult<CateMoreList>>>(getContext()) {
                    @Override
                    public void onSuccess(BaseResult<ListResult<CateMoreList>> result) {
                        RightDialogUtil.showDialog(getContext(),result.data,homeCategoryItem.name,s -> {});
                    }
                });

    }



}
