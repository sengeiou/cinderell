package com.cinderellavip;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.graphics.Rect;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.cinderellavip.bean.SignResult;
import com.cinderellavip.bean.VersionBean;
import com.cinderellavip.bean.eventbus.UpdateHomeMainData;
import com.cinderellavip.bean.eventbus.UpdateShopPage;
import com.cinderellavip.bean.net.PhoneResult;
import com.cinderellavip.global.Constant;
import com.cinderellavip.global.GlobalParam;
import com.cinderellavip.global.RequestCode;
import com.cinderellavip.http.ApiManager;
import com.cinderellavip.http.BaseResult;
import com.cinderellavip.http.Response;
import com.cinderellavip.service.SobotNotificationClickReceiver;
import com.cinderellavip.service.SobotUnReadMsgReceiver;
import com.cinderellavip.toast.CenterDialogUtil;
import com.cinderellavip.toast.DialogUtil;
import com.cinderellavip.ui.activity.account.LoginActivity;
import com.cinderellavip.ui.fragment.CartFragment;
import com.cinderellavip.ui.fragment.FindFragment;
import com.cinderellavip.ui.fragment.LifeFragment;
import com.cinderellavip.ui.fragment.MineFragment;
import com.cinderellavip.ui.fragment.ShopFragment;
import com.cinderellavip.util.Utils;
import com.cinderellavip.util.VersionUtil;
import com.flyco.roundview.RoundTextView;
import com.sobot.chat.ZCSobotApi;
import com.sobot.chat.utils.ZhiChiConstant;
import com.tozzais.baselibrary.http.RxHttp;
import com.tozzais.baselibrary.ui.CheckPermissionActivity;
import com.tozzais.baselibrary.util.StatusBarUtil;
import com.tozzais.baselibrary.util.log.LogUtil;
import com.xuexiang.xupdate.XUpdate;
import com.xuexiang.xupdate._XUpdate;
import com.xuexiang.xupdate.service.OnFileDownloadListener;
import com.xuexiang.xutil.app.PathUtils;
import com.xuexiang.xutil.display.HProgressDialogUtils;

import org.greenrobot.eventbus.EventBus;

import java.io.File;
import java.util.Calendar;
import java.util.TreeMap;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import butterknife.BindView;
import butterknife.OnClick;

public class MainActivity extends CheckPermissionActivity {

    public static String[] needPermissions = {
            Manifest.permission.ACCESS_FINE_LOCATION,
            Manifest.permission.WRITE_EXTERNAL_STORAGE,
            Manifest.permission.READ_EXTERNAL_STORAGE
    };

    @BindView(R.id.iv_shop)
    ImageView ivShop;
    @BindView(R.id.tv_shop)
    TextView tvShop;
    @BindView(R.id.iv_life_service)
    ImageView ivLifeService;
    @BindView(R.id.tv_life_service)
    TextView tvLifeService;
    @BindView(R.id.iv_find)
    ImageView ivFind;
    @BindView(R.id.tv_find)
    TextView tvFind;
    @BindView(R.id.iv_cart)
    ImageView ivCart;
    @BindView(R.id.tv_cart_number)
    RoundTextView tvCartNumber;
    @BindView(R.id.tv_cart)
    TextView tvCart;
    @BindView(R.id.iv_mine)
    ImageView ivMine;
    @BindView(R.id.tv_mine)
    TextView tvMine;
    @BindView(R.id.ll_main)
    LinearLayout llMain;

    public static void launch(Activity context) {
        Intent intent = new Intent(context, MainActivity.class);
        context.startActivity(intent);
        if (!context.isFinishing())
        context.finish();
    }

    public static void launch(Activity context,int mPosition) {
        Intent intent = new Intent(context, MainActivity.class);
        intent.putExtra("position",mPosition);
        context.startActivity(intent);
        if (!context.isFinishing())
        context.finish();

    }

    @Override
    public int getLayoutId() {
        return -1;
    }

    @Override
    protected int getBaseLayout() {
        return R.layout.activity_main;
    }

    @Override
    public void initView(Bundle savedInstanceState) {
        ZCSobotApi.checkIMConnected(getApplicationContext(), GlobalParam.getUserId());

        if (fragmentManager == null)
            fragmentManager = getSupportFragmentManager();
        if (savedInstanceState ==null){
            selectFragment(SHOP);
        }else {
            isLoad = savedInstanceState.getBoolean("isLoad");
        }

    }

    @Override
    public void loadData() {
        if (!isLoad){
            checkPermissions(needPermissions);
            startTime();
        }

    }

    @Override
    public void initListener() {


       new RxHttp<BaseResult<PhoneResult>>().send(ApiManager.getService().getPhone(),
                new Response<BaseResult<PhoneResult>>(isLoad, mActivity) {
                    @Override
                    public void onSuccess(BaseResult<PhoneResult> result) {
                        GlobalParam.setPhoneBean(result.data);
                    }
                });


        //https://github.com/xuexiangjys/XUpdate/wiki/%E6%BC%94%E7%A4%BADemo%E4%BB%8B%E7%BB%8D
        TreeMap<String, String> hashMap = new TreeMap<>();
        hashMap.put("type", 1 + "");
        new RxHttp<BaseResult<VersionBean>>().send(ApiManager.getService().getVersion(hashMap),
                new Response<BaseResult<VersionBean>>(mActivity,Response.BOTH) {
                    @Override
                    public void onSuccess(BaseResult<VersionBean> result) {
//                        LogUtil.e(result.data.toString());
                        String versionName=null;
                        try {
                            PackageManager pm = mContext.getPackageManager();
                            PackageInfo pi = pm.getPackageInfo(mContext.getPackageName(), 0);
                            versionName = pi.versionName;
                        } catch (Exception e) {
                        }
                        if ( VersionUtil.isModify(versionName,result.data.version)) {
                            showDialog(result.data);
                        }else {
                            sign();
                        }

                    }
                });

        regReceiver();
    }








    private SobotNotificationClickReceiver nClickReceiver;//点击通知以后发出的广播接收者
    private SobotUnReadMsgReceiver unReadMsgReceiver;//获取未读消息数的广播接收者
    private void regReceiver(){
        IntentFilter filter = new IntentFilter();
        if (nClickReceiver == null){
            nClickReceiver = new SobotNotificationClickReceiver();
        }
        filter.addAction(ZhiChiConstant.SOBOT_NOTIFICATION_CLICK);
        registerReceiver(nClickReceiver, filter);

        if (unReadMsgReceiver == null){
            unReadMsgReceiver = new SobotUnReadMsgReceiver();
        }
        filter.addAction(ZhiChiConstant.sobot_unreadCountBrocast);
        registerReceiver(unReadMsgReceiver, filter);
    }

    private void  showDialog(VersionBean versionBean){
        AlertDialog.Builder builder = new AlertDialog.Builder(mActivity);
        builder.setTitle("版本更新提示");
        builder.setMessage(versionBean.commit);
        builder.setPositiveButton("立即更新", (dialogInterface, i) -> {
                downFile(versionBean);
        });
        builder.setNegativeButton("暂不更新", null);
        builder.create().show(); //构建AlertDialog并显示

    }

    private void downFile(VersionBean versionBean){
        XUpdate.newBuild(mActivity)
                .apkCacheDir(PathUtils.getExtDownloadsPath())
                .build()
                .download(versionBean.url, new OnFileDownloadListener() {
                    @Override
                    public void onStart() {
                        HProgressDialogUtils.showHorizontalProgressDialog(mActivity, "下载进度", false);
                    }

                    @Override
                    public void onProgress(float progress, long total) {
                        HProgressDialogUtils.setProgress(Math.round(progress * 100));
                    }

                    @Override
                    public boolean onCompleted(File file) {
                        HProgressDialogUtils.cancel();
                        _XUpdate.startInstallApk(mActivity, file);
                        return false;
                    }

                    @Override
                    public void onError(Throwable throwable) {
                        HProgressDialogUtils.cancel();
                    }
                });
    }


    private void sign(){
        if (!GlobalParam.getUserLogin()){
            return;
        }
        new RxHttp<BaseResult<SignResult>>().send(ApiManager.getService().sign(),
                new Response<BaseResult<SignResult>>(mActivity,Response.BOTH) {
                    @Override
                    public void onSuccess(BaseResult<SignResult> result) {
                        String s = "灰豆+"+result.data.num;
                        CenterDialogUtil.showSignSuccess(mContext,s,()->{
                        });
                    }
                });
    }


    public static final int SHOP = 0;
    public static final int LIFE = 1;
    public static final int FIND = 2;
    public static final int CART = 3;
    public static final int MINE = 4;
    private static final String TAG_SHOP = "tag_shop";
    private static final String TAG_LIFE = "tag_life";
    private static final String TAG_FIND = "tag_find";
    private static final String TAG_CART = "tag_cart";
    private static final String TAG_MINE = "tag_mine";


    private int mPosition;//当前选中的底部菜单
    private FragmentManager fragmentManager;
    private ShopFragment shopFragment;
    private LifeFragment lifeFragment;
    private FindFragment findFragment;
    private CartFragment cartFragment;
    private MineFragment mineFragment;


    @OnClick({R.id.ll_shop, R.id.ll_life_service, R.id.ll_find, R.id.ll_cart, R.id.ll_mine})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.ll_shop:
                if (mPosition == SHOP){
                    EventBus.getDefault().post(new UpdateShopPage("-1"));
                }else {
                    selectFragment(SHOP);
                }
                break;
            case R.id.ll_life_service:
                selectFragment(LIFE);
                break;
            case R.id.ll_find:
                if (mPosition == FIND){
                    if (!Utils.isFastClick()){
                        return;
                    }
                    DialogUtil.showPublishDialog(mContext);
                }else {
                    selectFragment(FIND);
                }
                break;
            case R.id.ll_cart:
                if (GlobalParam.getUserLogin()){
                    selectFragment(CART);
                }else {
                    LoginActivity.launch(mActivity);
                }
                break;
            case R.id.ll_mine:
                selectFragment(MINE);
//                DirectAppointmentTechnicianDetailActivity.launch(mActivity,0);
                break;
        }
    }

    public void selectFragment(int position) {
        switch (position) {
            case SHOP:
                mPosition = SHOP;
                StatusBarUtil.setDarkMode(this);
                setTabChecked(mPosition);
                setTabSelection(mPosition);
                break;
            case LIFE:
                StatusBarUtil.setLightMode(this);
                mPosition = LIFE;
                setTabChecked(mPosition);
                setTabSelection(mPosition);
                break;
            case FIND:
                StatusBarUtil.setLightMode(this);
                mPosition = FIND;
                setTabChecked(mPosition);
                setTabSelection(mPosition);
                break;
            case CART:
                StatusBarUtil.setLightMode(this);
                mPosition = CART;
                setTabChecked(mPosition);
                setTabSelection(mPosition);
                break;
            case MINE:
                StatusBarUtil.setLightMode(this);
                mPosition = MINE;
                setTabChecked(mPosition);
                setTabSelection(mPosition);
                break;
        }


    }

    /**
     * 设置底部菜单被选中后字体、图片的颜色
     *
     * @param pos
     */
    private void setTabChecked(int pos) {

        ivShop.setImageResource(pos == SHOP ? R.mipmap.shop_select : R.mipmap.shop);
        tvShop.setTextColor(pos == SHOP ? getResources().getColor(R.color.baseColor) : getResources().getColor(R.color.main_tab_gray_text_color));
        ivLifeService.setImageResource(pos == LIFE ? R.mipmap.life_service_select : R.mipmap.life_service);
        tvLifeService.setTextColor(pos == LIFE ? getResources().getColor(R.color.baseColor) : getResources().getColor(R.color.main_tab_gray_text_color));
        ivFind.setImageResource(pos == FIND ? R.mipmap.find_select : R.mipmap.find);
        tvFind.setTextColor(pos == FIND ? getResources().getColor(R.color.baseColor) : getResources().getColor(R.color.main_tab_gray_text_color));
        tvFind.setText(pos == FIND ? "发布" : "发现");
        ivCart.setImageResource(pos == CART ? R.mipmap.cart_select : R.mipmap.cart);
        tvCart.setTextColor(pos == CART ? getResources().getColor(R.color.baseColor) : getResources().getColor(R.color.main_tab_gray_text_color));
        ivMine.setImageResource(pos == MINE ? R.mipmap.mine_select : R.mipmap.mine);
        tvMine.setTextColor(pos == MINE ? getResources().getColor(R.color.baseColor) : getResources().getColor(R.color.main_tab_gray_text_color));

    }

    private void setTabSelection(int position) {
        FragmentTransaction transaction = fragmentManager.beginTransaction();//开启Fragment事务
        hideFragments(transaction);//隐藏所有页面（）
        switch (position) {
            case SHOP:
                if (shopFragment == null) {
                    shopFragment = new ShopFragment();
                    transaction.add(R.id.fl_container, shopFragment, TAG_SHOP);
                } else {
                    shopFragment.getCategory();
                    transaction.show(shopFragment);
                }
                break;

            case LIFE:
                if (lifeFragment == null) {
                    lifeFragment = new LifeFragment();
                    transaction.add(R.id.fl_container, lifeFragment, TAG_LIFE);
                } else {
                    transaction.show(lifeFragment);
                }
                break;
            case FIND:
                if (findFragment == null) {
                    findFragment = new FindFragment();
                    transaction.add(R.id.fl_container, findFragment, TAG_FIND);
                } else {
                    transaction.show(findFragment);
                }
                break;

            case CART:
                if (cartFragment == null) {
                    cartFragment = CartFragment.newInstance(CartFragment.HAVA_TITLE);
                    transaction.add(R.id.fl_container, cartFragment, TAG_CART);
                } else {
                    transaction.show(cartFragment);
                }
                break;
            case MINE:
//                tsg("hahaha");
                if (mineFragment == null) {
                    mineFragment = new MineFragment();
                    transaction.add(R.id.fl_container, mineFragment, TAG_MINE);
                } else {
                    transaction.show(mineFragment);
                }
                break;
        }
        // 提交
        transaction.commit();
    }

    /**
     * 隐藏所有的页面
     */
    private void hideFragments(FragmentTransaction transaction) {
        if (shopFragment != null) {
            transaction.hide(shopFragment);
        }
        if (lifeFragment != null) {
            transaction.hide(lifeFragment);
        }
        if (findFragment != null) {
            transaction.hide(findFragment);
        }
        if (cartFragment != null) {
            transaction.hide(cartFragment);
        }
        if (mineFragment != null) {
            transaction.hide(mineFragment);
        }
    }


    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        if (savedInstanceState != null) {
            fragmentManager = getSupportFragmentManager();
            shopFragment = (ShopFragment) fragmentManager.findFragmentByTag(TAG_SHOP);
            lifeFragment = (LifeFragment) fragmentManager.findFragmentByTag(TAG_LIFE);
            findFragment = (FindFragment) fragmentManager.findFragmentByTag(TAG_FIND);
            cartFragment = (CartFragment) fragmentManager.findFragmentByTag(TAG_CART);
            mineFragment = (MineFragment) fragmentManager.findFragmentByTag(TAG_MINE);
        }
        mPosition = savedInstanceState.getInt("mPosition");
        selectFragment(mPosition);
    }


    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt("mPosition", mPosition);
        outState.putBoolean("isLoad", isLoad);
    }

    @Override
    protected int getToolbarLayout() {
        return -1;
    }

    @Override
    protected void setStatusBar() {
        StatusBarUtil.setTransparentForImageViewInFragment(MainActivity.this, null);
        StatusBarUtil.setLightMode(this);
    }





    private long mExitTime;
    public static final String FILE_PATH = "static.db";

    @Override
    public void permissionGranted() {

        File RootPath = new File(Constant.ROOT_PATH);
        if (!RootPath.exists()) {
            boolean b = RootPath.mkdir();
            LogUtil.e("创建"+b);
        }



    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            if ((System.currentTimeMillis() - mExitTime) > 2000) {
                tsg("再按一次退出");
                mExitTime = System.currentTimeMillis();
            } else {
                //小于2000ms则认为是用户确实希望退出程序-调用System.exit()方法进行退出
                System.exit(0);
            }
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);
        super.onCreate(savedInstanceState);
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
    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == RequestCode.request_service_city && resultCode == Activity.RESULT_OK){
            lifeFragment.setAddress(data.getStringExtra("name"));
        }
    }

    private void startTime(){
        Calendar calendar = Calendar.getInstance();
        //分钟
        int minute = calendar.get(Calendar.MINUTE);
//        LogUtil.e(minute+"");
        if (!mHandler.hasMessages(2))
        mHandler.sendEmptyMessageDelayed(2, 1000*60*(60-minute+2));

    }

    private Handler mHandler = new Handler(new Handler.Callback() {
        @Override
        public boolean handleMessage(Message msg) {
            if (msg.what == 2){
                LogUtil.e("刷新了");
                EventBus.getDefault().post(new UpdateHomeMainData());
                mHandler.sendEmptyMessageDelayed(2, 1000*60*60);
            }
            return false;
        }
    });
    @Override
    protected void onDestroy() {

        try {
            if (nClickReceiver != null){
                unregisterReceiver(nClickReceiver);
            }

            if (unReadMsgReceiver != null){
                unregisterReceiver(unReadMsgReceiver);
            }
        } catch (Exception e){
            e.printStackTrace();
        }

        if (mHandler != null) {
            mHandler.removeMessages(2);
        }
        mHandler = null;

        super.onDestroy();
    }


}
