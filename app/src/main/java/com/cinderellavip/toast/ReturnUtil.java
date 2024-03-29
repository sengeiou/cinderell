package com.cinderellavip.toast;

import android.app.Dialog;
import android.content.Context;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.cinderellavip.R;
import com.cinderellavip.weight.wheel.WheelView;
import com.cinderellavip.weight.wheel.adapters.AbstractWheelTextAdapter;

import java.util.List;


public class ReturnUtil {




	private static Dialog cityDialog;

	public static  void showSelectDialog(Context context,String title, List<String> data, final onSelectListener listener) {

		View view = View.inflate(context, R.layout.pop_bottom_return_dialog, null);
		cityDialog = DialogUtils.getBottomDialog(context,view);
		WheelView mViewProvince = view.findViewById(R.id.wv_country);
		ImageView iv_close = view.findViewById(R.id.iv_close);
		TextView tv_title = view.findViewById(R.id.tv_title);
		if (!TextUtils.isEmpty(title)){
			tv_title.setText(title);
		}
		iv_close.setOnClickListener(v -> {
			cityDialog.dismiss();
		});
		TextView mBtnConfirm = view.findViewById(R.id.tv_sure);
		mBtnConfirm.setOnClickListener(v->{
			cityDialog.dismiss();
			listener.onFinish(data.get(mViewProvince.getCurrentItem()));
		});


		ProviceWheelAdapter provinceAdapter = new ProviceWheelAdapter(context, data);
		mViewProvince.setViewAdapter(provinceAdapter);
		mViewProvince.setVisibleItems(7);
		cityDialog.setOnDismissListener(dialog -> {
			cityDialog = null;
		});


	}

	public static  void showSelectDialog(Context context, List<String> data, final onSelectListener listener) {

		showSelectDialog(context,"",data,listener);


	}

	static  class ProviceWheelAdapter extends AbstractWheelTextAdapter {
		List<String> list;

		public ProviceWheelAdapter(Context context, List<String> list) {
			super(context);
			this.list = list;
		}

		@Override
		public int getItemsCount() {
			return list == null ? 0 : list.size();
		}


		@Override
		protected CharSequence getItemText(int index) {
			return list.get(index);
		}

	}

	public interface onSelectListener {
		void onFinish(String reason);
	}

}
