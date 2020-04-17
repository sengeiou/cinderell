package com.cinderellavip.toast;

import android.app.Dialog;
import android.content.Context;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.cinderellavip.R;
import com.tozzais.baselibrary.util.toast.ToastCommom;


public class CenterDialogUtil {


    private static Dialog cityDialog;


    public static void showBulletin(Context context) {
        View messageView = View.inflate(context, R.layout.pop_bottom_realname_auth, null);
        cityDialog = DialogUtils.getCenterDialog(context, messageView, false);
        ImageView iv_close = messageView.findViewById(R.id.iv_close);
        EditText et_name = messageView.findViewById(R.id.et_name);
        TextView tv_commit = messageView.findViewById(R.id.tv_commit);
        iv_close.setOnClickListener(v -> {
            cityDialog.dismiss();
            cityDialog = null;
        });
        tv_commit.setOnClickListener(v -> {
            String code = et_name.getText().toString().trim();
            if (TextUtils.isEmpty(code)){
                ToastCommom.createToastConfig().ToastShow(context,"请输入邀请码");
                return;
            }else {
                ToastCommom.createToastConfig().ToastShow(context,"申请成功");
            }
            cityDialog.dismiss();
            cityDialog = null;
        });

    }




    public interface onSelectListener {
        void onFinish(String payString);
    }

}
