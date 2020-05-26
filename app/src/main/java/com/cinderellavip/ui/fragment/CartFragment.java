package com.cinderellavip.ui.fragment;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.cinderellavip.MainActivity;
import com.cinderellavip.R;
import com.cinderellavip.adapter.recycleview.CartAdapter;
import com.cinderellavip.adapter.recycleview.HomeGoodsAdapter;
import com.cinderellavip.bean.eventbus.AddCart;
import com.cinderellavip.bean.eventbus.UpdateCart;
import com.cinderellavip.bean.local.HomeGoods;
import com.cinderellavip.bean.local.RequestSettlePara;
import com.cinderellavip.bean.net.cart.CartGoodsItem;
import com.cinderellavip.bean.net.cart.CartItem;
import com.cinderellavip.bean.net.cart.CartResult;
import com.cinderellavip.http.ApiManager;
import com.cinderellavip.http.BaseResult;
import com.cinderellavip.http.Response;
import com.cinderellavip.listener.CartGoodsClickListener;
import com.cinderellavip.ui.activity.home.EnsureOrderActivity;
import com.cinderellavip.util.PriceFormat;
import com.cinderellavip.weight.MarginDecorationextendsHeader;
import com.tozzais.baselibrary.http.RxHttp;
import com.tozzais.baselibrary.ui.BaseListFragment;
import com.tozzais.baselibrary.util.DpUtil;
import com.tozzais.baselibrary.util.log.LogUtil;

import java.util.ArrayList;
import java.util.List;

import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.BindView;
import butterknife.OnClick;

public class CartFragment extends BaseListFragment<HomeGoods> implements CartGoodsClickListener {

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
    private CartAdapter cartAdapter;




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
        super.initView(savedInstanceState);
        type = getArguments().getInt("type");
        if (type == NOT_TITLE) {
            llTitle.setVisibility(View.GONE);
        }
        mRecyclerView.setLayoutManager(new GridLayoutManager(mActivity, 2));
        MarginDecorationextendsHeader space = new MarginDecorationextendsHeader(DpUtil.dip2px(mActivity, 8));
        mRecyclerView.addItemDecoration(space);
        mAdapter = new HomeGoodsAdapter();
        mRecyclerView.setAdapter(mAdapter);

        View emptyHeader = View.inflate(mActivity, R.layout.header_empty_view_for_cart, null);
        rv_cart = emptyHeader.findViewById(R.id.rv_cart);
        ll_empty = emptyHeader.findViewById(R.id.ll_empty);
        tv_walk_around = emptyHeader.findViewById(R.id.tv_walk_around);
        tv_walk_around.setOnClickListener(this::onClick);
        mAdapter.addHeaderView(emptyHeader);


        rv_cart.setLayoutManager(new LinearLayoutManager(mActivity));
        cartAdapter = new CartAdapter(this);
        rv_cart.setAdapter(cartAdapter);


    }

    @Override
    public void loadData() {
        super.loadData();
        new RxHttp<BaseResult<CartResult>>().send(ApiManager.getService().getCartData(),
                new Response<BaseResult<CartResult>>(isLoad,mActivity) {
                    @Override
                    public void onSuccess(BaseResult<CartResult> result) {
                        setData(result.data.products);
                        setCartData(result.data.list);
                    }

                    @Override
                    public void onErrorShow(String s) {
                        showError(s);
                    }

                    @Override
                    public void onCompleted() {
                        super.onCompleted();
                        swipeLayout.setRefreshing(false);
                    }
                });
    }

    private void setCartData(List<CartItem> list) {
        cartAdapter.setNewData(list);
        //解决删除购物车后不重置的bug。放在setData后面，解决刷新不重置总价格的bug
        onClick(false);

    }

    private boolean isSeleteAll = false;

    @OnClick({R.id.iv_selete_all, R.id.tv_settlement})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.iv_selete_all://选择全部
                LogUtil.e("onClick11111");
                isSeleteAll = !isSeleteAll;
                ivSeleteAll.setImageResource(isSeleteAll ? R.mipmap.gwcxz : R.mipmap.gwcmx);
                List<CartItem> mdata = cartAdapter.getData();
                for (CartItem cartItem : mdata) {
                    for (CartGoodsItem cartGoodsItem:cartItem.products){
                        cartGoodsItem.isCheck = isSeleteAll;
                    }
                    cartItem.isCheck = isSeleteAll;
                }
                cartAdapter.notifyDataSetChanged();
                calculateMoney();
                break;
            case R.id.tv_settlement:
                //去结算
                calculateCarts();
//                showDialog();
                break;
            case R.id.tv_walk_around:
                if (mActivity instanceof MainActivity){
                    ((MainActivity)mActivity).selectFragment(MainActivity.SHOP);
                }else {
                    MainActivity.launch(mActivity);
                }
                break;

        }
    }

    private void calculateMoney(){
        List<CartItem> data = cartAdapter.getData();
        double money = 0;
        for (CartItem cartItem : data) {
            for (CartGoodsItem cartGoodsItem:cartItem.products)
            if (cartGoodsItem.isCheck) {
                money += cartGoodsItem.product_price * cartGoodsItem.product_num;
            }
        }
        tvTotalPrice.setText("" + PriceFormat.getPeice(money));
    }

    private void calculateCarts(){
        List<CartItem> data = cartAdapter.getData();
        //得到条件
        StringBuffer stringBuffer = new StringBuffer();

        List<CartGoodsItem> selectList = new ArrayList<>();
        for (CartItem item:data){
            for (CartGoodsItem cartGoodsItem:item.products)
                if (cartGoodsItem.isCheck) {
                    selectList.add(cartGoodsItem);
                }

        }
        if (selectList.size() == 0){
            tsg("请选择商品");
            return;
        }
//

        for (int i=0;i<selectList.size();i++){
            CartGoodsItem item = selectList.get(i);
            if (i==selectList.size()-1){
                stringBuffer.append(item.cart_id);
            }else {
                stringBuffer.append(item.cart_id+",");
            }
        }
        RequestSettlePara para = new RequestSettlePara(RequestSettlePara.CART, stringBuffer.toString());
        EnsureOrderActivity.launch(mActivity,para);

    }

    @Override
    public void onClick(boolean b) {
        LogUtil.e("onClick"+b);
        this.isSeleteAll = true;
        for (CartItem cartItem:cartAdapter.getData()){
            boolean allSelect = true;
            for (CartGoodsItem goodsItem:cartItem.products){
                if (!goodsItem.isCheck){
                    isSeleteAll = false;
                    allSelect = false;
                    break;
                }
            }
            if (!allSelect){
                break;
            }
        }
        ivSeleteAll.setImageResource(isSeleteAll ?  R.mipmap.gwcxz : R.mipmap.gwcmx);
        boolean isHavaData = !(cartAdapter.getData().size() == 0);
        rv_cart.setVisibility(isHavaData ? View.VISIBLE : View.GONE);
        ll_bottom.setVisibility(isHavaData ? View.VISIBLE : View.GONE);
        ll_empty.setVisibility(!isHavaData ? View.VISIBLE : View.GONE);
        calculateMoney();
    }

    @Override
    public void initListener() {
        if (swipeLayout != null)
            swipeLayout.setOnRefreshListener(this::onRefresh);
    }

    @Override
    public void onEvent(Object o) {
        super.onEvent(o);
        if (o instanceof AddCart || o instanceof UpdateCart){
            onRefresh();
        }
    }
}
