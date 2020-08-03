package com.cinderellavip.ui.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.text.TextUtils;

import androidx.appcompat.widget.Toolbar;

import com.cinderellavip.R;
import com.dueeeke.videocontroller.StandardVideoController;
import com.dueeeke.videoplayer.player.VideoView;
import com.tozzais.baselibrary.ui.BaseActivity;
import com.tozzais.baselibrary.util.StatusBarUtil;

import butterknife.BindView;


public class VideoActivity extends BaseActivity {


    @BindView(R.id.player)
    VideoView player;
    @BindView(R.id.tb_toolbar)
    Toolbar tb_toolbar;

    /**
     * 登录进入
     *
     * @param context
     */
    public static void launch(Activity context, String path) {
        Intent intent = new Intent(context, VideoActivity.class);
        intent.putExtra("path", path);
        context.startActivity(intent);

    }

    @Override
    public int getLayoutId() {
        return -1;
    }

    @Override
    protected int getBaseLayout() {
        return R.layout.activity_player;
    }

    @Override
    public void initView(Bundle savedInstanceState) {

        if (tb_toolbar != null) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                tb_toolbar.setElevation(0);
            }
            tb_toolbar.setTitle("");
            setSupportActionBar(tb_toolbar);
            mTitle =  findViewById(R.id.tv_title);
            tv_right = findViewById(R.id.tv_right);
            iv_right_icon = findViewById(R.id.iv_right_icon);

                tb_toolbar.setNavigationIcon(R.drawable.icon_back);
                tb_toolbar.setNavigationOnClickListener(view -> finish());
        }


    }

    @Override
    public void loadData() {
        String path = getIntent().getStringExtra("path");
        if (!TextUtils.isEmpty(path)){
            StandardVideoController controller = new StandardVideoController(this);
            //根据屏幕方向自动进入/退出全屏
            controller.setEnableOrientation(true);
            player.setVideoController(controller);

            player.setUrl(path);

            //保存播放进度
//            mVideoView.setProgressManager(new ProgressManagerImpl());
            //播放状态监听
//            mVideoView.addOnVideoViewStateChangeListener(mOnVideoViewStateChangeListener);

            //使用IjkPlayer解码
//            mVideoView.setPlayerFactory(IjkPlayerFactory.create());
            //使用ExoPlayer解码
            //使用MediaPlayer解码
//            mVideoView.setPlayerFactory(AndroidMediaPlayerFactory.create());

            player.start();
        }

    }

    @Override
    protected int getToolbarLayout() {
        return -1;
    }

    @Override
    protected void setStatusBar() {
        StatusBarUtil.setTransparentForImageViewInFragment(VideoActivity.this, null);
        StatusBarUtil.setLightMode(this);
    }

    @Override
    protected void onPause() {
        super.onPause();
        player.pause();
    }

    @Override
    protected void onResume() {
        super.onResume();
        player.resume();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        player.release();
    }


    @Override
    public void onBackPressed() {
        if (!player.onBackPressed()) {
            super.onBackPressed();
        }
    }

}
