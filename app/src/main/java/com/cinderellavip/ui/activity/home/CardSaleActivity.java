package com.cinderellavip.ui.activity.home;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import com.cinderellavip.R;
import com.cinderellavip.adapter.recycleview.CardSaleAdapter;
import com.cinderellavip.adapter.recycleview.HomeGoodsAdapter;
import com.cinderellavip.ui.fragment.home.CardSaleFragment;
import com.cinderellavip.util.DataUtil;
import com.cinderellavip.util.Utils;
import com.cinderellavip.weight.GirdSpace;
import com.tozzais.baselibrary.ui.BaseActivity;
import com.tozzais.baselibrary.ui.BaseListActivity;
import com.tozzais.baselibrary.util.DpUtil;

import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;
import butterknife.BindView;


/**
 * Created by Administrator on 2016/9/8.
 * 大牌特卖
 */
public class CardSaleActivity extends BaseActivity {



    public static void launch(Context from) {
        if (!Utils.isFastClick()){
            return;
        }
        Intent intent = new Intent(from, CardSaleActivity.class);
        from.startActivity(intent);
    }


    @Override
    public void initView(Bundle savedInstanceState) {

        setBackTitle("大牌特卖");
        setRightIcon(R.mipmap.icon_search_black);



    }


    @Override
    public void loadData() {
        CardSaleFragment fragment = new CardSaleFragment();
        getSupportFragmentManager().beginTransaction().add(R.id.content_container, fragment).commit();
    }

    @Override
    public int getLayoutId() {
        return R.layout.layout_content;
    }

    @Override
    public void initListener() {
        super.initListener();
        iv_right_icon.setOnClickListener(v -> {
            SearchActivity.launch(mActivity);
        });
    }
}
