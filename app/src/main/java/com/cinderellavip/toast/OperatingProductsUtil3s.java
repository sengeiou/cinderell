package com.cinderellavip.toast;

import android.app.Dialog;
import android.content.Context;
import android.view.View;
import android.widget.TextView;

import com.cinderellavip.R;
import com.cinderellavip.bean.local.OperateProductBean;
import com.cinderellavip.weight.wheel.OnWheelChangedListener;
import com.cinderellavip.weight.wheel.WheelView;
import com.cinderellavip.weight.wheel.adapters.AbstractWheelTextAdapter;

import java.util.ArrayList;
import java.util.List;


public class OperatingProductsUtil3s implements OnWheelChangedListener {


	private static OperatingProductsUtil3s cityUtils;

	public static OperatingProductsUtil3s getInstance() {
		if (cityUtils == null) {
			synchronized (OperatingProductsUtil3s.class) {
				if (cityUtils == null) {
					cityUtils = new OperatingProductsUtil3s();
				}
			}
		}
		return cityUtils;
	}

	private WheelView mViewProvince;
	private WheelView mViewCity;
	private WheelView mViewCounty;
	private TextView mBtnConfirm;
	private Context context;
	private List<OperateProductBean> localCities;
	private ProvinceWheelAdapter provinceAdapter;
	private CityWheelAdapter cityAdapter;
	private Dialog cityDialog;


	public void showSelectDialog(Context context,List<OperateProductBean> list, final onSelectCityFinishListener listener) {
		localCities = new ArrayList<>();
		this.context = context;
		this.localCities = list;
		View view = View.inflate(context, R.layout.base_city_choose_dialog, null);
		cityDialog = DialogUtils.getBottomDialog(context, view);
		mViewProvince = view.findViewById(R.id.wv_country);
		mViewCity = view.findViewById(R.id.wv_city);
		mViewCounty =  view.findViewById(R.id.wv_county);
		mViewCounty.setVisibility(View.GONE);


		mBtnConfirm =  view.findViewById(R.id.tv_sure);
		TextView tv_cancel =view.findViewById(R.id.tv_cancel);
		mBtnConfirm.setOnClickListener(v ->{
			cityDialog.dismiss();
			OperateProductBean province = localCities.get(mViewProvince.getCurrentItem());
			String cityListBean = province.children.get(mViewCity.getCurrentItem());
			if (listener != null) {
				listener.onFinish(province,
						cityListBean);
			}
		});
		tv_cancel.setOnClickListener(v ->{
			cityDialog.dismiss();
		});

		setUpListener();
		setUpData();
	}

	private void setUpData() {
		List<OperateProductBean> province = localCities;
		provinceAdapter = new ProvinceWheelAdapter(context, province);
		mViewProvince.setViewAdapter(provinceAdapter);
		
		mViewProvince.setVisibleItems(7);
		mViewCity.setVisibleItems(7);
		mViewCounty.setVisibleItems(7);
		updateCities();
	}

	private void setUpListener() {
		mViewProvince.addChangingListener(this);
		mViewCity.addChangingListener(this);
		mViewCounty.addChangingListener(this);
	}

	@Override
	public void onChanged(WheelView wheel, int oldValue, int newValue) {
		if (wheel == mViewProvince) {
			updateCities();
		}
	}

	private void updateCities() {

		List<String> city = localCities.get(mViewProvince.getCurrentItem()).children;
		cityAdapter = new CityWheelAdapter(context, city);
		mViewCity.setViewAdapter(cityAdapter);
		mViewCity.setCurrentItem(0);
	}


	class ProvinceWheelAdapter extends AbstractWheelTextAdapter {
		List<OperateProductBean> list;

		public ProvinceWheelAdapter(Context context, List<OperateProductBean> list) {
			super(context);
			this.list = list;
		}

		@Override
		public int getItemsCount() {
			return list == null ? 0 : list.size();
		}



		@Override
		protected CharSequence getItemText(int index) {
			return list.get(index).name;
		}

	}

	class CityWheelAdapter extends AbstractWheelTextAdapter {
		List<String> list;

		public CityWheelAdapter(Context context, List<String> list) {
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


	public interface onSelectCityFinishListener {
		 void onFinish(OperateProductBean province, String city);
	}

}
