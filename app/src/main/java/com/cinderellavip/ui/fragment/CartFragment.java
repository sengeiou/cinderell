package com.cinderellavip.ui.fragment;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.cinderellavip.MainActivity;
import com.cinderellavip.R;
import com.cinderellavip.adapter.recycleview.CartAdapter;
import com.cinderellavip.adapter.recycleview.CartEmptyAdapter;
import com.cinderellavip.bean.local.CartItem;
import com.cinderellavip.bean.local.HomeGoods;
import com.cinderellavip.global.GlobalParam;
import com.cinderellavip.http.ApiManager;
import com.cinderellavip.listener.CartClickListener;
import com.cinderellavip.weight.MarginDecorationextendsHeader;
import com.tozzais.baselibrary.ui.BaseListFragment;
import com.tozzais.baselibrary.util.DpUtil;
import com.tozzais.baselibrary.util.sign.SignUtil;

import java.util.ArrayList;
import java.util.List;
import java.util.TreeMap;

import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.BindView;
import butterknife.OnClick;

public class CartFragment extends BaseListFragment<HomeGoods> implements CartClickListener {

    public static final int NOT_TITLE = 0, HAVA_TITLE = 1;
    private int type;

    @BindView(R.id.iv_selete_all)
    ImageView ivSeleteAll;
    @BindView(R.id.tv_total_price)
    TextView tvTotalPrice;

    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.tv_settlement)
    TextView tvSettlement;


    @BindView(R.id.ll_bottom)
    LinearLayout ll_bottom;


    private RecyclerView rv_cart;
    private LinearLayout ll_empty;
    private TextView tv_walk_around;
    private BaseQuickAdapter cartAdapter;




    @BindView(R.id.ll_title)
    LinearLayout llTitle;

    @Override
    public int setLayout() {
        return R.layout.fragment_cart;
    }


    public static CartFragment newInstance(int type) {
        CartFragment cartFragment = new CartFragment();
        Bundle bundle = new Bundle();
        bundle.putInt("type", type);
        cartFragment.setArguments(bundle);
        return cartFragment;

    }

    @Override
    public void initView(Bundle savedInstanceState) {
        type = getArguments().getInt("type");
        if (type == NOT_TITLE) {
            llTitle.setVisibility(View.GONE);
        }
        mRecyclerView.setLayoutManager(new GridLayoutManager(mActivity, 2));
        MarginDecorationextendsHeader space = new MarginDecorationextendsHeader(DpUtil.dip2px(mActivity, 8));
        mRecyclerView.addItemDecoration(space);
        mAdapter = new CartEmptyAdapter();
        mRecyclerView.setAdapter(mAdapter);

        View emptyHeader = View.inflate(mActivity, R.layout.header_empty_view_for_cart, null);
        rv_cart = emptyHeader.findViewById(R.id.rv_cart);
        ll_empty = emptyHeader.findViewById(R.id.ll_empty);
        tv_walk_around = emptyHeader.findViewById(R.id.tv_walk_around);
        tv_walk_around.setOnClickListener(this::onClick);
        mAdapter.addHeaderView(emptyHeader);


        rv_cart.setLayoutManager(new LinearLayoutManager(mActivity));
        cartAdapter = new CartAdapter(this);
        cartAdapter.bindToRecyclerView(rv_cart);


    }

    @Override
    public void loadData() {

        getData();
    }

    private void getData() {
        if (!isLoad) {
            showProress();
        }
        TreeMap<String, String> hashMap = new TreeMap<>();
        hashMap.put("user_id", GlobalParam.getUserId());
        hashMap.put("count", PageSize + "");
        hashMap.put("page", page + "");
        hashMap.put("sign", SignUtil.getMd5(hashMap));
        new RxHttp<CartResult>().send(ApiManager.getService().getCartList(hashMap),
                new Response<CartResult>(isLoad,mActivity) {
                    @Override
                    public void onSuccess(CartResult result) {
                        showContent();
                        if (result.data != null && result.data.size() > 0) {
                            onHavaData(true);
                        } else {
                            onHavaData(false);
                        }
                        //购物车默认全选
                        for (CartItem cartItem:result.data){
                            cartItem.isCheck = true;
                        }
                        cartAdapter.setNewData(result.data);
                        setData( result.data1);
                        //解决删除购物车后不重置的bug。放在setData后面，解决刷新不重置总价格的bug
                        onChildSelete(true);
                    }

                    @Override
                    public void onError(Throwable e) {
                        onErrorResult(e);
                    }
                });
    }


    private boolean isSeleteAll = false;

    @OnClick({R.id.iv_selete_all, R.id.tv_settlement})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.iv_selete_all://选择全部
                isSeleteAll = !isSeleteAll;
                ivSeleteAll.setImageResource(isSeleteAll ? R.mipmap.goods_selete : R.mipmap.goods_selete_no);
                List<CartItem> mdata = cartAdapter.getData();
                for (CartItem cartItem : mdata) {
                    cartItem.isCheck = isSeleteAll;
                }
                cartAdapter.notifyDataSetChanged();
                calculateMoney();
                break;
            case R.id.tv_settlement:
                //去结算
                verify();
//                showDialog();
                break;
            case R.id.tv_walk_around:
                if (mActivity instanceof MainActivity){
                    ((MainActivity)mActivity).selectFragment(MainActivity.HOME);
                }else {
                    MainActivity.launch(mActivity);
                }
                break;

        }
    }

    /**
     *
     * @param data
     * @param error 提示 还是食品安全提示
     */
    private void showDialog(List<String> data,boolean error) {
        CenterDialogUtil.showBuyGoods(mActivity,error,  data, () -> {
            if (!error){
                settle();
            }
        });
    }

    private void getCartData(CommitOrderValue commitOrderValue) {
        TreeMap<String, String> hashMap = new TreeMap<>();
        hashMap.put("user_id", GlobalParam.getUserId());
        hashMap.put("carts", commitOrderValue.carts + "");
        hashMap.put("lat", commitOrderValue.lat);
        hashMap.put("lng", commitOrderValue.lng);
        hashMap.put("sign", SignUtil.getMd5(hashMap));
        new RxHttp<BaseResult<OrderSettleResult>>().send(ApiManager.getService().getBuyCart(hashMap),
                new Response<BaseResult<OrderSettleResult>>(mActivity) {
                    @Override
                    public void onSuccess(BaseResult<OrderSettleResult> result) {
                        OrderSettlementActivity.launch(mActivity, OrderSettlementActivity.BUY_CART, commitOrderValue);
                    }
                });

    }


    /**
     *
     */
    private void settle(){
        CommitOrderValue commitOrderValue = new CommitOrderValue();
        commitOrderValue.isCar = "0";
        commitOrderValue.carts = calculateCarts();
        commitOrderValue.sku_id = "";
        commitOrderValue.other_id = "";
        commitOrderValue.num = "";
        commitOrderValue.lat = BaseApplication.lat+"";
        commitOrderValue.lng = BaseApplication.lng+"";
        //全是普通商品 或者全部是特使商品
        commitOrderValue.order_type = mixStatus();
        getCartData(commitOrderValue);


    }



    private void verify() {

        TreeMap<String, String> hashMap = new TreeMap<>();
        hashMap.put("user_id", GlobalParam.getUserId());
        hashMap.put("carts", calculateCarts());
        hashMap.put("full_products", "");
        hashMap.put("sign", SignUtil.getMd5(hashMap));
        new RxHttp<BaseListResult<String>>().send(ApiManager.getService().getConflictCart(hashMap),
                new Response<BaseListResult<String>>(mActivity) {
                    @Override
                    public void onNext(BaseListResult<String> str) {
                        if ("0".equals(str.code) || 0 == str.code){
                            settle();
                        }else if ("10000".equals(str.code) || 10000 == str.code){
                            showDialog(str.data,false);
                        }else {
                            showDialog(str.data,true);
                        }
                    }

                });

    }


    @Override
    public void onChildSelete(boolean isSeleteAll) {
        this.isSeleteAll = isSeleteAll;
        ivSeleteAll.setImageResource(isSeleteAll ? R.mipmap.goods_selete : R.mipmap.goods_selete_no);
        calculateMoney();


    }

    @Override
    public void onHavaData(boolean isHavaData) {
        rv_cart.setVisibility(isHavaData ? View.VISIBLE : View.GONE);
        ll_bottom.setVisibility(isHavaData ? View.VISIBLE : View.GONE);
        ll_empty.setVisibility(!isHavaData ? View.VISIBLE : View.GONE);
        calculateMoney();

    }

    private void calculateMoney(){
        List<CartItem> data = cartAdapter.getData();
        double money = 0;
        for (CartItem cartItem : data) {
            if (cartItem.isCheck) {
                money += cartItem.getPrice() * cartItem.getNum();
            }
        }
        tvTotalPrice.setText("" + PriceFormat.getPeice(money));
    }

    private String calculateCarts(){
        List<CartItem> data = cartAdapter.getData();
        //得到条件
        StringBuffer stringBuffer = new StringBuffer();
        List<CartItem> selectList = new ArrayList<>();
        for (CartItem item:data){
            if (item.isCheck){
                selectList.add(item);
            }
        }
        for (int i=0;i<selectList.size();i++){
            CartItem item = selectList.get(i);
            if (i==selectList.size()-1){
                stringBuffer.append(item.getCart_id());
            }else {
                stringBuffer.append(item.getCart_id()+",");
            }
        }
        return stringBuffer.toString();

    }


    /**
     * 是否有普通商品 和特色商品混合
     * @return 0 全部普通商品  1 全部特色商品 -1混合的
     */
    private String mixStatus(){
        List<CartItem> data = cartAdapter.getData();
        //得到条件
        boolean haveSpecial = false;
//        StringBuffer stringBuffer = new StringBuffer();
        List<CartItem> selectList = new ArrayList<>();
        for (CartItem item:data){
            if (item.isCheck){
                selectList.add(item);
            }
        }
        for (int i=0;i<selectList.size();i++){
            CartItem item = selectList.get(i);
            if (item.isSpecialGoods()){
                haveSpecial = true;
                break;
            }
        }
        return haveSpecial?"1":"0";



    }

    @Override
    public void onEvent(Object o) {
        if (o instanceof AddCartSuccess){
            onRefresh();
        }

    }
}
