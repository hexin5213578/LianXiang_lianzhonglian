package com.LianXiangKeJi.SupplyChain.main.fragment;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.LianXiangKeJi.SupplyChain.R;
import com.LianXiangKeJi.SupplyChain.base.BaseFragment;
import com.LianXiangKeJi.SupplyChain.base.BasePresenter;
import com.LianXiangKeJi.SupplyChain.base.Common;
import com.LianXiangKeJi.SupplyChain.common.bean.OrderBean;
import com.LianXiangKeJi.SupplyChain.main.adapter.ShopcarAdapter;
import com.LianXiangKeJi.SupplyChain.main.bean.DeleteShopCarBean;
import com.LianXiangKeJi.SupplyChain.main.bean.SaveShopCarBean;
import com.LianXiangKeJi.SupplyChain.main.bean.ShopCarBean;
import com.LianXiangKeJi.SupplyChain.order.activity.ConfirmOrderActivity;
import com.LianXiangKeJi.SupplyChain.recommend.adapter.HotsellAdapter;
import com.LianXiangKeJi.SupplyChain.search.adapter.SearchGoodsAdapter;
import com.LianXiangKeJi.SupplyChain.utils.NetUtils;
import com.LianXiangKeJi.SupplyChain.utils.SPUtil;
import com.google.gson.Gson;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import me.trojx.dancingnumber.DancingNumberView;
import okhttp3.MediaType;
import okhttp3.RequestBody;

/**
 * @ClassName:FragmentHome
 * @Author:hmy
 * @Description:java类作用描述   购物车页
 */
public class FragmentOrder extends BaseFragment implements View.OnClickListener {

    @BindView(R.id.tv_manager)
    TextView tvManager;
    @BindView(R.id.rc_order)
    RecyclerView rcOrder;
    @BindView(R.id.rb_checkAll)
    CheckBox rbCheckAll;
    @BindView(R.id.heji)
    TextView heji;
    @BindView(R.id.bt_jiesuan)
    Button btJiesuan;
    @BindView(R.id.bt_delete)
    Button btDelete;
    @BindView(R.id.money)
    TextView money;
    @BindView(R.id.tv)
    TextView tv;
    @BindView(R.id.ll_jieSuan)
    RelativeLayout llJieSuan;
    @BindView(R.id.rl_noshopcar)
    RelativeLayout noshopcar;
    @BindView(R.id.ll_order)
    LinearLayout llOrder;
    private List<String> listId = new ArrayList<>();
    private ShopcarAdapter shopcarAdapter;
    private List<ShopCarBean.DataBean> data;
    private double totalPrice;

    @Override
    protected void getid(View view) {

    }

    @Override
    protected int getResId() {
        return R.layout.fragment_order;
    }

    @Override
    protected BasePresenter initPresenter() {
        return null;
    }

    @Override
    protected void getData() {
        String token = Common.getToken();
        if (!TextUtils.isEmpty(token)) {
            tvManager.setOnClickListener(this);
            btJiesuan.setOnClickListener(this);
            btDelete.setOnClickListener(this);

            SaveShopCarBean saveShopCarBean = new SaveShopCarBean();
            saveShopCarBean.setState(true);
            Gson gson = new Gson();
            String json = gson.toJson(saveShopCarBean);

            RequestBody requestBody = RequestBody.create(MediaType.parse("application/json; charset=utf-8"), json);
            //查询购物车
            showDialog();
            doShopCar(requestBody);
            hideDialog();
            money.setText("¥"+"0.00");
        } else {
            Toast.makeText(getContext(), "请先登录", Toast.LENGTH_SHORT).show();
        }

    }
    @SuppressLint("WrongConstant")
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void calculationCountAndPrice(String i) {
        //是否所有的条目都被选中
        boolean isAllChecked = true;
        totalPrice = 0;
        for (ShopCarBean.DataBean bean : data) {
            if (!bean.isPersonChecked()) {
                isAllChecked = false;
            } else {
                totalPrice += Double.valueOf(bean.getPrice()) * bean.getCount();
            }
        }
        money.setText("¥" + String.format("%.2f", totalPrice));

        rbCheckAll.setChecked(isAllChecked);

        //将改变的数量存入本地文件
        LinkedHashMap<String, String> goodsid = SPUtil.getMap(getContext(), "goodsid");
        for (String key : goodsid.keySet()) {
            for (ShopCarBean.DataBean bean : data) {
                if (key.equals(bean.getId())){
                    goodsid.put(key, String.valueOf(bean.getCount()));
                }
            }
        }
        SPUtil.setMap(getContext(), "goodsid", goodsid);
    }

    @Override
    public void onStart() {
        super.onStart();

        listId.clear();

        tvManager.setVisibility(View.VISIBLE);
        heji.setVisibility(View.VISIBLE);
        money.setVisibility(View.VISIBLE);
        btJiesuan.setVisibility(View.VISIBLE);
        btDelete.setVisibility(View.GONE);

        rbCheckAll.setChecked(false);
        SaveShopCarBean saveShopCarBean = new SaveShopCarBean();
        saveShopCarBean.setState(true);
        Gson gson = new Gson();
        String json = gson.toJson(saveShopCarBean);

        RequestBody requestBody = RequestBody.create(MediaType.parse("application/json; charset=utf-8"), json);
        //查询购物车

        doShopCar(requestBody);
        Log.d("xxx","onstart");
    }

    @Override
    public void onHiddenChanged(boolean hidden) {
        listId.clear();
        Log.d("xxx","onHiddenChanged");

        tvManager.setVisibility(View.VISIBLE);
        heji.setVisibility(View.VISIBLE);
        money.setVisibility(View.VISIBLE);
        btJiesuan.setVisibility(View.VISIBLE);
        btDelete.setVisibility(View.GONE);
        money.setText("¥"+"0.00");
        rbCheckAll.setChecked(false);
        super.onHiddenChanged(hidden);
        SaveShopCarBean saveShopCarBean = new SaveShopCarBean();
        saveShopCarBean.setState(true);
        Gson gson = new Gson();
        String json = gson.toJson(saveShopCarBean);

        RequestBody requestBody = RequestBody.create(MediaType.parse("application/json; charset=utf-8"), json);
        //查询购物车

        doShopCar(requestBody);

    }
    private boolean checked = true;
    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.tv_manager:
                if(checked){
                    tvManager.setText("完成");//设置显示内容
                    heji.setVisibility(View.GONE);
                    money.setVisibility(View.GONE);
                    btJiesuan.setVisibility(View.GONE);
                    btDelete.setVisibility(View.VISIBLE);
                    Animation shake = AnimationUtils.loadAnimation(getContext(), R.anim.shake);
                    tvManager.startAnimation(shake);
                    checked=false;
                }else{
                    tvManager.setText("管理");//设置显示内容
                    heji.setVisibility(View.VISIBLE);
                    money.setVisibility(View.VISIBLE);
                    btJiesuan.setVisibility(View.VISIBLE);
                    btDelete.setVisibility(View.GONE);
                    Animation shake = AnimationUtils.loadAnimation(getContext(), R.anim.shake);
                    tvManager.startAnimation(shake);
                    checked=true;
                }
                break;
            case R.id.bt_jiesuan:
                //  带数据去订单页发起结算
                List<OrderBean> list_order  = new ArrayList<>();

                for (int i=0;i<data.size();i++){
                    ShopCarBean.DataBean dataBean = data.get(i);
                    if(dataBean.isPersonChecked()==true){
                        OrderBean orderBean = new OrderBean();

                        orderBean.setImageurl(dataBean.getLittlePrintUrl());
                        orderBean.setGoodsid(dataBean.getId());
                        orderBean.setCount(dataBean.getCount());
                        orderBean.setSpecs(dataBean.getSpecs());
                        orderBean.setName(dataBean.getName());
                        orderBean.setPrice(dataBean.getPrice());
                        list_order.add(orderBean);
                    }
                }
                if(list_order!=null &&list_order.size()>0){
                    if(totalPrice>200){
                        Intent intent = new Intent(getContext(), ConfirmOrderActivity.class);
                        Bundle bundle = new Bundle();
                        bundle.putSerializable("orderlist", (Serializable) list_order);
                        intent.putExtras(bundle);
                        intent.putExtra("flag","flase");
                        startActivity(intent);
                    }else{
                        Toast.makeText(getContext(), "结算最低金额为200元", Toast.LENGTH_SHORT).show();
                    }

                }else{
                    Toast.makeText(getContext(), "请先选购商品", Toast.LENGTH_SHORT).show();
                }

                break;
            case R.id.bt_delete:
                List<SaveShopCarBean.ResultBean> shoplist = new ArrayList<>();

                for (ShopCarBean.DataBean bean : data) {
                    if(bean.isPersonChecked()==false){
                        listId.add(bean.getId());
                    }
                }
                Log.d("hmy","当前集合长度:"+data.size());
                Log.d("hmy","不删除的数量"+listId.size());
                if(listId.size()<1){
                    SaveShopCarBean saveShopCarBean1 = new SaveShopCarBean();
                    saveShopCarBean1.setState(false);
                    for (int i=0;i<listId.size();i++){
                        SaveShopCarBean.ResultBean resultBean = new SaveShopCarBean.ResultBean();
                        resultBean.setShopGoodsId(listId.get(i));
                        shoplist.add(resultBean);
                    }
                    //添加进集合
                    Log.d("hmy","全删"+listId.size());
                    saveShopCarBean1.setShoppingCartList(shoplist);

                    Gson gson1 = new Gson();
                    String json1 = gson1.toJson(saveShopCarBean1);
                    RequestBody requestBody1 = RequestBody.create(MediaType.parse("application/json; charset=utf-8"), json1);
                    //删除购物车
                    NetUtils.getInstance().getApis().doDeleteShopCar(requestBody1)
                            .subscribeOn(Schedulers.io())
                            .observeOn(AndroidSchedulers.mainThread())
                            .subscribe(new Observer<DeleteShopCarBean>() {
                                @Override
                                public void onSubscribe(Disposable d) {

                                }

                                @Override
                                public void onNext(DeleteShopCarBean bean) {
                                    Toast.makeText(getContext(), ""+bean.getData(), Toast.LENGTH_SHORT).show();

                                    SaveShopCarBean saveShopCarBean = new SaveShopCarBean();
                                    saveShopCarBean.setState(true);
                                    Gson gson = new Gson();
                                    String json = gson.toJson(saveShopCarBean);

                                    RequestBody requestBody = RequestBody.create(MediaType.parse("application/json; charset=utf-8"), json);
                                    //查询购物车
                                    doShopCar(requestBody);
                                    calculationCountAndPrice("0");

                                    LinkedHashMap<String, String> goodsid = SPUtil.getMap(getContext(), "goodsid");
                                    goodsid.clear();
                                    SPUtil.setMap(getContext(),"goodsid",goodsid);
                                }

                                @Override
                                public void onError(Throwable e) {
                                    hideDialog();
                                }

                                @Override
                                public void onComplete() {

                                }
                            });
                }else{
                    SaveShopCarBean saveShopCarBean2 = new SaveShopCarBean();
                    saveShopCarBean2.setState(false);
                    LinkedHashMap<String, String> goodsid = SPUtil.getMap(getContext(), "goodsid");
                    LinkedHashMap<String, String> map = new LinkedHashMap<>();

                    for (int i=0;i<listId.size();i++){
                        SaveShopCarBean.ResultBean resultBean = new SaveShopCarBean.ResultBean();
                        resultBean.setShopGoodsId(listId.get(i));
                        shoplist.add(resultBean);
                        Iterator<String> iterator = goodsid.keySet().iterator();
                        while (iterator.hasNext()){
                            String next = iterator.next();
                            if(next.equals(listId.get(i))){
                                map.put(next,goodsid.get(next));
                            }
                        }
                    }

                    SPUtil.setMap(getContext(),"goodsid",map);

                    Log.d("hmy","选删"+listId.size());

                    //添加进集合
                    saveShopCarBean2.setShoppingCartList(shoplist);

                    Gson gson2 = new Gson();
                    String json2 = gson2.toJson(saveShopCarBean2);
                    RequestBody requestBody2 = RequestBody.create(MediaType.parse("application/json; charset=utf-8"), json2);
                    //删除购物车
                    doShopCar(requestBody2);
                    data.clear();
                    listId.clear();
                }

                }
        }

    @Override
    public void onResume() {
        super.onResume();
        if (!EventBus.getDefault().isRegistered(this)) {
            EventBus.getDefault().register(this);
        }
    }

    @Override
    public void onPause() {
        super.onPause();
        if (EventBus.getDefault().isRegistered(this)) {
            EventBus.getDefault().unregister(this);
        }
        Log.d("xxx","onResume");
    }
    /**
     *  处理购物车
     * @param body
     */
    public void doShopCar(RequestBody body){
        showDialog();
        NetUtils.getInstance().getApis().doShopCar(body)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<ShopCarBean>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(ShopCarBean shopCarBean) {
                        hideDialog();
                        money.setText("¥"+"0.00");

                        data = shopCarBean.getData();
                        LinkedHashMap<String, String> goodsid = SPUtil.getMap(getContext(), "goodsid");
                        LinkedHashMap<String,String> orderlist = new LinkedHashMap<>();

                        //判断进货单数据是否为空 如果为空隐藏所有功能展示进货单为空图片
                        if(data.size()>0&& data !=null){
                            noshopcar.setVisibility(View.GONE);
                            llOrder.setVisibility(View.VISIBLE);
                            for (String key : goodsid.keySet()) {
                                String value = goodsid.get(key);
                                for (int i =0;i<data.size();i++){
                                    if(data.get(i).getId().equals(key)){
                                        data.get(i).setCount(Integer.valueOf(value));
                                    }
                                }
                            }
                            //第一次查询购物车 以服务器为准
                            for (int i =0;i<data.size();i++){
                                orderlist.put(data.get(i).getId(),String.valueOf(data.get(i).getCount()));
                            }
                            //覆盖
                            SPUtil.setMap(getContext(),"goodsid",orderlist);

                            LinearLayoutManager manager = new LinearLayoutManager(getContext());

                            rcOrder.setLayoutManager(manager);

                            shopcarAdapter = new ShopcarAdapter(getContext());
                            shopcarAdapter.setData(data);
                            rcOrder.setAdapter(shopcarAdapter);

                            rbCheckAll.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    // 先获取是否被选中,在onClick中拿到的是已经改变过的值
                                    for (ShopCarBean.DataBean bean : data) {
                                        bean.setPersonChecked(rbCheckAll.isChecked());
                                    }
                                    //刷新列表
                                    shopcarAdapter.notifyDataSetChanged();
                                    //重新计算价格
                                    calculationCountAndPrice("0");
                                }
                            });
                        }else{
                            noshopcar.setVisibility(View.VISIBLE);
                            llOrder.setVisibility(View.GONE);
                            tvManager.setVisibility(View.GONE);

                            LinkedHashMap<String,String> orderlist1 = new LinkedHashMap<>();
                            SPUtil.setMap(getContext(),"goodsid",orderlist1);
                        }

                    }

                    @Override
                    public void onError(Throwable e) {
                        hideDialog();
                        if(e.getMessage().equals("timeout")){
                            Toast.makeText(getContext(), "连接超时", Toast.LENGTH_SHORT).show();
                            rbCheckAll.setChecked(false);
                        }else{
                            noshopcar.setVisibility(View.VISIBLE);
                            llOrder.setVisibility(View.GONE);
                            tvManager.setVisibility(View.GONE);
                        }
                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }
}
