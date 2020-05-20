package com.cinderellavip.util.alilogin;

import android.app.Activity;
import android.text.TextUtils;

import com.alipay.sdk.app.AuthTask;
import com.alipay.sdk.app.EnvUtils;
import com.alipay.sdk.app.PayTask;
import com.cinderellavip.util.pay.PayResult;
import com.tozzais.baselibrary.util.log.LogUtil;
import com.umeng.commonsdk.debug.D;
import com.umeng.commonsdk.debug.E;

import java.util.Map;

public class AliUtil {

    public interface Back {
         void success(String result);

         void failed();
    }

    public static void login(final Activity activity, final String authInfo, final Back back) {

        new Thread(() -> {
            AuthTask authTask = new AuthTask(activity);
            Map<String, String> result = authTask.authV2(authInfo, true);
//            for(Map.Entry<String, String> entry : result.entrySet()) {
//                LogUtil.e("Key = " + entry.getKey() + ",value=" + entry.getValue());
//            }
            AuthResult authResult = new AuthResult(result, true);
            String resultStatus = authResult.getResultStatus();

            if (TextUtils.equals(resultStatus, "9000")
                    && TextUtils.equals(authResult.getResultCode(), "200")) {
                back.success(authResult.getResult());
            } else {
                back.failed();
            }
        }).start();
    }

}
