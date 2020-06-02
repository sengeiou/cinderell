package com.cinderellavip.ui.activity.mine;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import com.cinderellavip.R;
import com.cinderellavip.util.Utils;
import com.tozzais.baselibrary.ui.BaseActivity;


/**
 * Created by Administrator on 2016/9/8.
 */
public class ApplyProductSupplierResultActivity extends BaseActivity {



    public static void launch(Context from) {
        if (!Utils.isFastClick()){
            return;
        }
        Intent intent = new Intent(from, ApplyProductSupplierResultActivity.class);
        from.startActivity(intent);
    }


    @Override
    public void initView(Bundle savedInstanceState) {

        setBackTitle("申请产品供应商");



    }


    @Override
    public void loadData() {
//        new RxHttp<BaseResult<ApplyResult>>().send(ApiManager.getService().applyResult(),
//                new Response<BaseResult<ApplyResult>>( mActivity) {
//                    @Override
//                    public void onSuccess(BaseResult<ApplyResult> result) {
//                        CenterDialogUtil.showApplySuccess(mContext,()->{
//                            ApplyProductSupplierResultActivity.launch(mActivity);
//                            mActivity.finish();
//                        });
//                    }
//                });
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_apply_product_result;
    }


}
