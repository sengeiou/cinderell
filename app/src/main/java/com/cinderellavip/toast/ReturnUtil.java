package com.cinderellavip.toast;

import android.app.Dialog;
import android.content.Context;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;


import com.cinderellavip.R;
import com.cinderellavip.bean.ReturnReasonItem;
import com.cinderellavip.weight.wheel.WheelView;
import com.cinderellavip.weight.wheel.adapters.AbstractWheelTextAdapter;

import java.util.ArrayList;
import java.util.List;


public class ReturnUtil {





	private static Dialog cityDialog;

	public static  void showSelectDialog(Context context, List<ReturnReasonItem> data, final onSelectListener listener) {

		View view = View.inflate(context, R.layout.pop_bottom_return_dialog, null);
		cityDialog = DialogUtils.getBottomDialog(context,view);
		WheelView mViewProvince = view.findViewById(R.id.wv_country);
		ImageView iv_close = view.findViewById(R.id.iv_close);
		iv_close.setOnClickListener(v -> {
			cityDialog.dismiss();
		});
		TextView mBtnConfirm = view.findViewById(R.id.tv_sure);
		mBtnConfirm.setOnClickListener(v->{
			cityDialog.dismiss();
			listener.onFinish(data.get(mViewProvince.getCurrentItem()));
		});


		ArrayList<String> strings = new ArrayList<>();
		for (ReturnReasonItem returnReasonItem:data){
			strings.add(returnReasonItem.name);
		}

		ProviceWheelAdapter provinceAdapter = new ProviceWheelAdapter(context, strings);
		mViewProvince.setViewAdapter(provinceAdapter);
		mViewProvince.setVisibleItems(7);


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
		void onFinish(ReturnReasonItem reason);
	}

}
