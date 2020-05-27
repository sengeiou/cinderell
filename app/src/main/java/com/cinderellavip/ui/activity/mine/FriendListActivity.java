package com.cinderellavip.ui.activity.mine;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.cinderellavip.R;
import com.cinderellavip.bean.net.mine.MineInviterNumber;
import com.cinderellavip.global.GlobalParam;
import com.cinderellavip.global.ShareConstant;
import com.cinderellavip.http.ApiManager;
import com.cinderellavip.http.BaseResult;
import com.cinderellavip.http.Response;
import com.cinderellavip.ui.fragment.mine.XiaoHuiRecommentFragment;
import com.cinderellavip.util.QRCodeUtil;
import com.tozzais.baselibrary.http.RxHttp;
import com.tozzais.baselibrary.ui.BaseActivity;
import com.tozzais.baselibrary.util.DpUtil;

import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;
import butterknife.BindView;
import butterknife.OnClick;


/**
 * Created by Administrator on 2016/9/8.
 */
public class FriendListActivity extends BaseActivity {



    public static void launch(Context from) {
        Intent intent = new Intent(from, FriendListActivity.class);
        from.startActivity(intent);
    }



    @Override
    public void initView(Bundle savedInstanceState) {
        setLineVisibility();
        setBackTitle("直接好友列表");

    }


    @Override
    public void loadData() {
        XiaoHuiRecommentFragment fragment = new XiaoHuiRecommentFragment();
        getSupportFragmentManager().beginTransaction().add(R.id.content_container, fragment).commit();
    }

    @Override
    public int getLayoutId() {
        return R.layout.layout_content;
    }
}
