package com.cinderellavip.toast;

import android.app.Dialog;
import android.content.Context;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.cinderellavip.R;
import com.cinderellavip.bean.local.OperateProductBean;
import com.cinderellavip.weight.wheel.OnWheelChangedListener;
import com.cinderellavip.weight.wheel.WheelView;
import com.cinderellavip.weight.wheel.adapters.AbstractWheelTextAdapter;

import java.util.ArrayList;
import java.util.List;


public class ServiceTypeDialogUtil implements OnWheelChangedListener {


	private static ServiceTypeDialogUtil cityUtils;

	public static ServiceTypeDialogUtil getInstance() {
		if (cityUtils == null) {
			synchronized (ServiceTypeDialogUtil.class) {
				if (cityUtils == null) {
					cityUtils = new ServiceTypeDialogUtil();
				}
			}
		}
		return cityUtils;
	}

	private WheelView mViewProvince;
	private WheelView mViewCity;
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
		View view = View.inflate(context, R.layout.pop_bottom_service_type, null);
		cityDialog = DialogUtils.getBottomDialog(context, view);
		mViewProvince = view.findViewById(R.id.wv_country);
		mViewCity = view.findViewById(R.id.wv_city);


		mBtnConfirm =  view.findViewById(R.id.tv_sure);
		ImageView iv_close =view.findViewById(R.id.iv_close);
		mBtnConfirm.setOnClickListener(v ->{

			OperateProductBean province = localCities.get(mViewProvince.getCurrentItem());
			String cityListBean = province.children.get(mViewCity.getCurrentItem());
			if (listener != null) {
				listener.onFinish(province,
						cityListBean);
			}
			cityDialog.dismiss();
			cityDialog = null;
		});
		iv_close.setOnClickListener(v ->{
			cityDialog.dismiss();
			cityDialog = null;
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
		updateCities();
	}

	private void setUpListener() {
		mViewProvince.addChangingListener(this);
		mViewCity.addChangingListener(this);
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
