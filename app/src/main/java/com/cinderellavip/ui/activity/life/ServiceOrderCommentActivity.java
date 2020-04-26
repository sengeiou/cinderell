package com.cinderellavip.ui.activity.life;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.cinderellavip.R;
import com.cinderellavip.weight.RatingBarView;
import com.nex3z.flowlayout.FlowLayout;
import com.tozzais.baselibrary.ui.BaseActivity;

import java.util.ArrayList;
import java.util.List;

import androidx.recyclerview.widget.RecyclerView;
import butterknife.BindView;
import butterknife.OnClick;


/**
 * Created by Administrator on 2016/9/8.
 */
public class ServiceOrderCommentActivity extends BaseActivity {


    @BindView(R.id.rv_image)
    RecyclerView rvImage;
    @BindView(R.id.fl_select_flag)
    FlowLayout flSelectFlag;
    @BindView(R.id.fl_flag)
    FlowLayout flFlag;
    @BindView(R.id.rb_logistics_score)
    RatingBarView rbLogisticsScore;


    List<String> selectFlag = new ArrayList<>();

    public static void launch(Context from) {
        Intent intent = new Intent(from, ServiceOrderCommentActivity.class);
        from.startActivity(intent);
    }


    @Override
    public void initView(Bundle savedInstanceState) {

        setBackTitle("订单评价");


    }


    @Override
    public void loadData() {
       addData();

    }

    private void addData(){
        List<String> list=new ArrayList<>();
        list.add("服务态度好");
        list.add("做饭好吃");
        list.add("相处愉快");
        list.add("说话轻柔");
        list.add("不打鼾");
        list.add("形象气质好");
        list.add("个人卫生好");
        for (String text : list) {
            View view = View.inflate(mActivity,R.layout.item_service_order_comment_flag,null);
            TextView tv = view.findViewById(R.id.tv_title);
            tv.setOnClickListener(v -> {
                boolean isHave = false;
                for (String s:selectFlag){
                    if (s.equals(text)){
                        isHave = true;
                        break;
                    }
                }
                if (!isHave){
                    selectFlag.add(text);
                    addCommentData(text);
                }
            });
            tv.setText(text);
            flFlag.addView(view);
        }
    }


    private void addCommentData(String text){

            View view = View.inflate(mActivity,R.layout.item_service_order_comment_flag_select,null);
            TextView tv = view.findViewById(R.id.tv_title);
            ImageView iv_delete = view.findViewById(R.id.iv_delete);
            iv_delete.setOnClickListener(v -> {
                flSelectFlag.removeView(view);
            });
            tv.setText(text);
            flSelectFlag.addView(view);
    }





    @Override
    public int getLayoutId() {
        return R.layout.activity_order_comment_single;
    }


    @OnClick(R.id.tv_buy)
    public void onClick() {
    }
}
