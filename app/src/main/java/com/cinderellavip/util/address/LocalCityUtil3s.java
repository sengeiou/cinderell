package com.cinderellavip.util.address;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;


import com.cinderellavip.R;
import com.cinderellavip.weight.wheel.OnWheelChangedListener;
import com.cinderellavip.weight.wheel.WheelView;
import com.cinderellavip.weight.wheel.adapters.AbstractWheelTextAdapter;

import java.util.ArrayList;
import java.util.List;


public class LocalCityUtil3s implements OnWheelChangedListener {

	public static final int LEVEL_ONE = 1 ,LEVEL_TWO = 2 , DEFAULT = 3;

	private static LocalCityUtil3s cityUtils;

	public static LocalCityUtil3s getInstance() {
		if (cityUtils == null) {
			synchronized (LocalCityUtil3s.class) {
				if (cityUtils == null) {
					cityUtils = new LocalCityUtil3s();
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
	private ArrayList<LocalCity> localCities;
	private ProvinceWheelAdapter provinceAdapter;
	private CityWheelAdapter cityAdapter;
	private Dialog cityDialog;
	public void showSelectDialog(final Context context ,final onSelectCityFinishListener listener){
		showSelectDialog(context,3,listener);
	}

	public void showSelectDialog(Context context, int number , final onSelectCityFinishListener listener) {
//		cityDao = new CityDao(context);
		localCities = CityUtil.convertStream(context);
		this.context = context;
		View view = View.inflate(context, R.layout.base_city_choose_dialog, null);
		mViewProvince = view.findViewById(R.id.wv_country);
		mViewCity = view.findViewById(R.id.wv_city);
		mViewCounty =  view.findViewById(R.id.wv_county);
		if(LEVEL_ONE == number){
			mViewCity.setVisibility(View.GONE);
			mViewCounty.setVisibility(View.GONE);
		}else if (LEVEL_TWO == number){
			mViewCounty.setVisibility(View.GONE);
		}

		mBtnConfirm =  view.findViewById(R.id.tv_sure);
		TextView tv_cancel =view.findViewById(R.id.tv_cancel);
		mBtnConfirm.setOnClickListener(v ->{
			cityDialog.dismiss();
			LocalCity province = localCities.get(mViewProvince.getCurrentItem());
			LocalCity.CityListBean cityListBean = province.cityList.get(mViewCity.getCurrentItem());
			if (listener != null) {
				listener.onFinish(province,
						cityListBean,
						cityListBean.areaList.get(mViewCounty.getCurrentItem()));
			}
		});
		tv_cancel.setOnClickListener(v ->{
			cityDialog.dismiss();
		});

		setUpListener();
		setUpData();

		cityDialog = new Dialog(context,R.style.transparentFrameWindowStyle);
		cityDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
		cityDialog.setContentView(view);
		Window window = cityDialog.getWindow();
		window.setWindowAnimations(R.style.PopupAnimation);

		WindowManager.LayoutParams wl = window.getAttributes();
		wl.x = 0;
		wl.y = ((Activity) context).getWindowManager().getDefaultDisplay().getHeight();
		wl.width = ViewGroup.LayoutParams.MATCH_PARENT;
		wl.height = ViewGroup.LayoutParams.WRAP_CONTENT;

		cityDialog.getWindow().setAttributes(wl);
//		cityDialog.onWindowAttributesChanged(wl);

		cityDialog.setCanceledOnTouchOutside(true);
		cityDialog.show();
	}

	private void setUpData() {
		List<LocalCity> province = localCities;
		provinceAdapter = new ProvinceWheelAdapter(context, province);
		mViewProvince.setViewAdapter(provinceAdapter);
		
		mViewProvince.setVisibleItems(7);
		mViewCity.setVisibleItems(7);
		mViewCounty.setVisibleItems(7);
		updateCities();
		updateCounty();
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
			updateCounty();
		}else if(wheel == mViewCity){
			updateCounty();
		}
	}

	private void updateCities() {

		List<LocalCity.CityListBean> city = localCities.get(mViewProvince.getCurrentItem()).cityList;
		cityAdapter = new CityWheelAdapter(context, city);
		mViewCity.setViewAdapter(cityAdapter);
		mViewCity.setCurrentItem(0);
	}
	private void updateCounty(){
		List<LocalCity.CityListBean.AreaListBean> areaList =
				localCities.get(mViewProvince.getCurrentItem()).cityList.get(mViewCity.getCurrentItem()).areaList;
		mViewCounty.setViewAdapter(new AreaWheelAdapter(context,areaList));
		mViewCounty.setCurrentItem(0);
	}

	//省
	class ProvinceWheelAdapter extends AbstractWheelTextAdapter {
		List<LocalCity> list;

		public ProvinceWheelAdapter(Context context, List<LocalCity> list) {
			super(context);
			this.list = list;
		}

		@Override
		public int getItemsCount() {
			return list == null ? 0 : list.size();
		}

		public String getCityId(int position) {
			return list.get(position).id;
		}

		@Override
		protected CharSequence getItemText(int index) {
			return list.get(index).name;
		}

	}

	class CityWheelAdapter extends AbstractWheelTextAdapter {
		List<LocalCity.CityListBean> list;

		public CityWheelAdapter(Context context, List<LocalCity.CityListBean> list) {
			super(context);
			this.list = list;
		}

		@Override
		public int getItemsCount() {
			return list == null ? 0 : list.size();
		}

		public String getCityId(int position) {
			return list.get(position).id;
		}

		@Override
		protected CharSequence getItemText(int index) {
			return list.get(index).name;
		}

	}
	class AreaWheelAdapter extends AbstractWheelTextAdapter {
		List<LocalCity.CityListBean.AreaListBean> list;

		public AreaWheelAdapter(Context context, List<LocalCity.CityListBean.AreaListBean> list) {
			super(context);
			this.list = list;
		}

		@Override
		public int getItemsCount() {
			return list == null ? 0 : list.size();
		}

		public String getCityId(int position) {
			return list.get(position).id;
		}

		@Override
		protected CharSequence getItemText(int index) {
			return list.get(index).name;
		}

	}


	public interface onSelectCityFinishListener {
		 void onFinish(CityBean province, CityBean city, CityBean county);
	}

}
