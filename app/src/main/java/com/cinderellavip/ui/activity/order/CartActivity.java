package com.cinderellavip.ui.activity.order;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Rect;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.EditText;

import com.cinderellavip.R;
import com.cinderellavip.global.GlobalParam;
import com.cinderellavip.ui.fragment.CartFragment;
import com.cinderellavip.util.Utils;
import com.tozzais.baselibrary.ui.BaseActivity;


public class CartActivity extends BaseActivity {


    public static void launch(Activity activity) {
//        if (!GlobalParam.getUserLogin()){
//            SelectLoginWayActivity.launch(activity,true);
//            return;
//        }
        if (!Utils.isFastClick()){
            return;
        }
        Intent intent = new Intent(activity, CartActivity.class);
        activity.startActivity(intent);
    }


    @Override
    public int getLayoutId() {
        return R.layout.layout_content;
    }

    @Override
    public void initView(Bundle savedInstanceState) {
        setBackTitle("购物车");

        getSupportFragmentManager()
                .beginTransaction()
                .add(R.id.content_container, CartFragment.newInstance(CartFragment.NOT_TITLE))
                .commit();
    }

    @Override
    public void loadData() {

    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        if (ev.getAction() == MotionEvent.ACTION_DOWN) {
            // 获取当前焦点所在的控件；
            View view = getCurrentFocus();
            if (view != null && view instanceof EditText) {
                Rect r = new Rect();
                view.getGlobalVisibleRect(r);
                int rawX = (int) ev.getRawX();
                int rawY = (int) ev.getRawY();
                // 判断点击的点是否落在当前焦点所在的 view 上；
                if (!r.contains(rawX, rawY)) {
                    view.clearFocus();
                }
            }
        }
        return super.dispatchTouchEvent(ev);
    }
}
