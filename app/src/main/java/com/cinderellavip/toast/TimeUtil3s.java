package com.cinderellavip.toast;

import android.app.Dialog;
import android.content.Context;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.cinderellavip.R;
import com.cinderellavip.weight.wheel.OnWheelChangedListener;
import com.cinderellavip.weight.wheel.WheelView;
import com.cinderellavip.weight.wheel.adapters.NumericWheelAdapter;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;


public class TimeUtil3s implements OnWheelChangedListener {


	private static TimeUtil3s cityUtils;

	public static TimeUtil3s getInstance() {
		if (cityUtils == null) {
			synchronized (TimeUtil3s.class) {
				if (cityUtils == null) {
					cityUtils = new TimeUtil3s();
				}
			}
		}
		return cityUtils;
	}

	private WheelView wv_year;
	private WheelView wv_month;
	private WheelView wv_day;
	private Context context;
	private Dialog cityDialog;

	private void clean(){
		wv_year = null;
		wv_month = null;
		wv_day = null;
		context = null;
		cityDialog = null;
	}



	public void showSelectDialog(Context context, onSelectTimeListener listener) {
		this.context = context;
		Calendar c = Calendar.getInstance();
		int year = c.get(Calendar.YEAR);
		int month = c.get(Calendar.MONTH)+1;
		int day = c.get(Calendar.DAY_OF_MONTH);
		int count = getDaysOfMonth(year, month);

		View view = View.inflate(context, R.layout.pop_bottom_service_time, null);
		cityDialog = DialogUtils.getBottomDialog(context, view);
		wv_year =  view.findViewById(R.id.wv_year);
		wv_month =  view.findViewById(R.id.wv_month);
		wv_day =  view.findViewById(R.id.wv_day);
		TextView tv_sure =  view.findViewById(R.id.tv_sure);
		ImageView iv_close =  view.findViewById(R.id.iv_close);
		tv_sure.setOnClickListener(v->{

			cityDialog.dismiss();
			int month1 ;
			if (wv_year.getCurrentItem() == 0){
				month1 = month+wv_month.getCurrentItem();
			}else {
				month1 = wv_month.getCurrentItem()+1;
			}

			int day1 ;
			if (wv_year.getCurrentItem() == 0 && wv_month.getCurrentItem() == 0){
				day1 = day+wv_day.getCurrentItem();
			}else {
				day1 = wv_day.getCurrentItem()+1;
			}

				listener.onFinish(""+(year+wv_year.getCurrentItem())
					,month1+""
					,day1+"");
			clean();
		});
		iv_close.setOnClickListener(v->{
			cityDialog.dismiss();
			clean();
		});

		wv_year.setViewAdapter(getAdapter(context, year, year+1, null));
		wv_year.setCurrentItem(0);


		updateMonth();

		updateDay();



		setUpListener();

		wv_year.setVisibleItems(7);
		wv_month.setVisibleItems(7);
		wv_day.setVisibleItems(7);

	}



	private static NumericWheelAdapter getAdapter(Context context, int minValue, int MaxValue, String format) {
		NumericWheelAdapter adapter = new NumericWheelAdapter(context, minValue, MaxValue, format);
		adapter.setTextSize(14);
		return adapter;

	}

	private void setUpListener() {
		wv_year.addChangingListener(this);
		wv_month.addChangingListener(this);
		wv_day.addChangingListener(this);
	}

	@Override
	public void onChanged(WheelView wheel, int oldValue, int newValue) {
		if (wheel == wv_year) {
			updateMonth();
			updateDay();
		}else if (wheel == wv_month) {
			updateDay();
		}
	}

	private void updateMonth(){
		Calendar c = Calendar.getInstance();
		int month = c.get(Calendar.MONTH)+1;
		if (wv_year.getCurrentItem() == 0){
			wv_month.setViewAdapter(getAdapter(context, month, 12, null));
		}else {
			wv_month.setViewAdapter(getAdapter(context, 1, 12, null));
		}
		wv_month.setCurrentItem(0);

	}

	private void updateDay(){
		Calendar c = Calendar.getInstance();
		int minYear = c.get(Calendar.YEAR);
		int month = c.get(Calendar.MONTH)+1;
		int day = c.get(Calendar.DAY_OF_MONTH);
		int count = getDaysOfMonth(minYear, month);
		if (wv_year.getCurrentItem() == 0 && wv_month.getCurrentItem() == 0){
			wv_day.setViewAdapter(getAdapter(context, day, count, null));
		}else {
			if (wv_year.getCurrentItem() == 0){
				month = month+wv_month.getCurrentItem();
			}else {
				month = wv_month.getCurrentItem()+1;
			}
			count = getDaysOfMonth(minYear + wv_year.getCurrentItem(), month);
			wv_day.setViewAdapter(getAdapter(context, 1, count, null));
		}
		wv_day.setCurrentItem(0);
	}

	/**
	 * 计算 这个月一共多少天
	 * @param year
	 * @param month
	 * @return
	 */
	public int getDaysOfMonth(int year, int month) {
		try {
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
			Calendar calendar = Calendar.getInstance();
			calendar.setTime(sdf.parse(year + "-" + month + "-01"));
			return calendar.getActualMaximum(Calendar.DAY_OF_MONTH);
		} catch (Exception e) {
			return 0;
		}

	}





	public interface onSelectTimeListener {
		void onFinish(String year, String month, String day);
	}

}
