package com.cinderellavip.util.dialog;

import android.app.Dialog;
import android.content.Context;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;

import com.cinderellavip.R;
import com.cinderellavip.adapter.recycleview.FilterAdapter;
import com.cinderellavip.listener.OnGetStringListener;
import com.cinderellavip.util.DataUtil;

import java.util.ArrayList;
import java.util.List;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;


public class RightDialogUtil {




	private static Dialog dialog;

	public static void showDialog(Context context, OnGetStringListener listener) {
		View view = View.inflate(context.getApplicationContext(), R.layout.pop_right_condition, null);

		RecyclerView rv_condition = view.findViewById(R.id.rv_condition);
		rv_condition.setLayoutManager(new LinearLayoutManager(context));
		FilterAdapter filterAdapter = new FilterAdapter(v -> {
			dialog.dismiss();
			dialog = null;
		});
		rv_condition.setAdapter(filterAdapter);
		filterAdapter.setNewData(DataUtil.getData(2));


		dialog = new Dialog(context,R.style.transparentFrameWindowStyle);
		dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
		dialog.setContentView(view);
		Window window = dialog.getWindow();
		window.setWindowAnimations(R.style.PopupAnimation1);



		WindowManager.LayoutParams wl = window.getAttributes();
		wl.width = ViewGroup.LayoutParams.WRAP_CONTENT;
		wl.height = ViewGroup.LayoutParams.MATCH_PARENT;
		wl.gravity = Gravity.RIGHT;
		window.setAttributes(wl);
//		cityDialog.onWindowAttributesChanged(wl);
		dialog.setCanceledOnTouchOutside(true);
		window.clearFlags(WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE | WindowManager.LayoutParams.FLAG_ALT_FOCUSABLE_IM);
		window.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE |
				WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);

		window.setFlags(
				WindowManager.LayoutParams.FLAG_FULLSCREEN,
				WindowManager.LayoutParams.FLAG_FULLSCREEN);

		dialog.show();

	}



}
