package com.cinderellavip.ui.activity.home;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.RelativeLayout;

import com.cinderellavip.R;
import com.cinderellavip.adapter.recycleview.ImageShareAdapter;
import com.cinderellavip.bean.local.ShareImageItem;
import com.cinderellavip.toast.CenterDialogUtil;
import com.cinderellavip.toast.SecondDialogUtil;
import com.tozzais.baselibrary.ui.CheckPermissionActivity;

import java.util.ArrayList;
import java.util.List;

import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.BindView;
import butterknife.OnClick;

public class ShareActivity extends CheckPermissionActivity {


    @BindView(R.id.rv_image)
    RecyclerView rvImage;

    public static void launch(Activity activity, int id) {
        Intent intent = new Intent(activity, ShareActivity.class);
        intent.putExtra("id", id);
        activity.startActivity(intent);
    }

    private int id;


    @Override
    public int getLayoutId() {
        return R.layout.activity_share;
    }

    @Override
    public void initView(Bundle savedInstanceState) {
        setBackTitle("商品分享");
        rvImage.setLayoutManager(new GridLayoutManager(mActivity, 4));
        imageShareAdapter = new ImageShareAdapter();
        rvImage.setAdapter(imageShareAdapter);
    }

    private ImageShareAdapter imageShareAdapter;


    @Override
    public void loadData() {
        List<ShareImageItem> list = new ArrayList<>();
        list.add(new ShareImageItem(true));
        list.add(new ShareImageItem(true));
        list.add(new ShareImageItem(true));
        list.add(new ShareImageItem(true));
        list.add(new ShareImageItem(true));
        list.add(new ShareImageItem(true));
        imageShareAdapter.setNewData(list);
    }


    @Override
    public void permissionGranted() {

    }

    @OnClick({R.id.rl_share1, R.id.rl_share2, R.id.rl_share3, R.id.rl_share4})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.rl_share1:
                share();
                break;
            case R.id.rl_share2:
                Intent textIntent = new Intent(Intent.ACTION_SEND);
                textIntent.setType("text/plain");
                textIntent.putExtra(Intent.EXTRA_TEXT, "这是一段分享的文字");
                startActivity(Intent.createChooser(textIntent, "分享"));
                break;
            case R.id.rl_share3:
                CenterDialogUtil.showShare(mActivity,()->{

                });
                break;
            case R.id.rl_share4:
                break;
        }
    }
    private void share(){
        SecondDialogUtil.showPosterDialog(mContext, (payString1, bitmap) -> {
            switch (payString1){
                case "1":
                    tsg("分享微信");
                    break;
                case "2":
                    tsg("分享朋友圈");
                    break;
                case "down":
                    tsg("保存成功");
                    break;
            }
        });







    }
}
