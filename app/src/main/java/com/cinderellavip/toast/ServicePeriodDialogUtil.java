package com.cinderellavip.toast;

import android.app.Dialog;
import android.content.Context;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.cinderellavip.R;
import com.cinderellavip.weight.wheel.WheelView;
import com.cinderellavip.weight.wheel.adapters.AbstractWheelTextAdapter;

import java.util.List;


public class ServicePeriodDialogUtil {





	private static Dialog cityDialog;

	public static  void showSelectDialog(Context context, List<String> data,  onSelectListener listener) {

		View view = View.inflate(context, R.layout.pop_bottom_service_period, null);
		cityDialog = DialogUtils.getBottomDialog(context,view);
		WheelView mViewProvince = view.findViewById(R.id.wv_country);
		ImageView iv_close = view.findViewById(R.id.iv_close);
		iv_close.setOnClickListener(v -> {
			cityDialog.dismiss();
		});
		TextView mBtnConfirm = view.findViewById(R.id.tv_sure);
		mBtnConfirm.setOnClickListener(v->{
			cityDialog.dismiss();
			listener.onFinish(data.get(mViewProvince.getCurrentItem()),mViewProvince.getCurrentItem()+1+"");
		});



		WheelAdapter provinceAdapter = new WheelAdapter(context, data);
		mViewProvince.setViewAdapter(provinceAdapter);
		mViewProvince.setVisibleItems(7);


	}

	static  class WheelAdapter extends AbstractWheelTextAdapter {
		List<String> list;

		public WheelAdapter(Context context, List<String> list) {
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
		void onFinish(String reason,String position);
	}

}
