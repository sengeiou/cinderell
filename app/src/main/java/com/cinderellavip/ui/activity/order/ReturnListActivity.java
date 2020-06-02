package com.cinderellavip.ui.activity.order;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import com.cinderellavip.R;
import com.cinderellavip.ui.activity.home.SearchActivity;
import com.cinderellavip.ui.fragment.home.CardSaleFragment;
import com.cinderellavip.ui.fragment.order.RefundFragment;
import com.cinderellavip.util.Utils;
import com.tozzais.baselibrary.ui.BaseActivity;


/**
 * Created by Administrator on 2016/9/8.
 */
public class ReturnListActivity extends BaseActivity {



    public static void launch(Context from) {
        if (!Utils.isFastClick()){
            return;
        }
        Intent intent = new Intent(from, ReturnListActivity.class);
        from.startActivity(intent);
    }


    @Override
    public void initView(Bundle savedInstanceState) {

        setBackTitle("退款");
//        setRightIcon(R.mipmap.icon_search_black);



    }


    @Override
    public void loadData() {
        RefundFragment fragment = new RefundFragment();
        getSupportFragmentManager().beginTransaction().add(R.id.content_container, fragment).commit();
    }

    @Override
    public int getLayoutId() {
        return R.layout.layout_content;
    }


}
