package com.LianXiangKeJi.SupplyChain.order.fragment;

import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.LianXiangKeJi.SupplyChain.R;
import com.LianXiangKeJi.SupplyChain.base.BaseFragment;
import com.LianXiangKeJi.SupplyChain.base.BasePresenter;
import com.LianXiangKeJi.SupplyChain.order.adapter.Near_HotSellAdapter;
import com.LianXiangKeJi.SupplyChain.order.adapter.PaymentAdapter;
import com.LianXiangKeJi.SupplyChain.order.adapter.ShipAdapter;
import com.LianXiangKeJi.SupplyChain.order.bean.UserOrderBean;
import com.LianXiangKeJi.SupplyChain.recommend.bean.HotSellBean;
import com.LianXiangKeJi.SupplyChain.utils.NetUtils;
import com.liaoinstan.springview.container.DefaultFooter;
import com.liaoinstan.springview.container.DefaultHeader;
import com.liaoinstan.springview.widget.SpringView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

/**
 * @ClassName:FragmentShip
 * @Author:hmy
 * @Description:java类作用描述 待发货订单
 */
public class FragmentShip extends BaseFragment {
    @BindView(R.id.rc_order)
    RecyclerView rcOrder;
    @BindView(R.id.rl_noorder)
    RelativeLayout rlNoorder;
    @BindView(R.id.rc_hotSell)
    RecyclerView rcHotSell;
    @BindView(R.id.sv)
    SpringView sv;


    @Override
    protected void getid(View view) {

    }

    @Override
    protected int getResId() {
        return R.layout.fragment_ship;
    }

    @Override
    protected BasePresenter initPresenter() {
        return null;
    }

    @Override
    protected void getData() {
        sv.setHeader(new DefaultHeader(getContext()));
        getDataBean();
        // TODO: 2020/7/21 测试热销展示的四条商品
        //获取热销商品
        NetUtils.getInstance().getApis()
                .doGetHotSell()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<HotSellBean>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(HotSellBean hotSellBean) {
                        List<HotSellBean.DataBean> data = hotSellBean.getData();
                        if(data.size()>0&&data!=null){
                            GridLayoutManager gridLayoutManager = new GridLayoutManager(getContext(), 2);
                            rcHotSell.setLayoutManager(gridLayoutManager);

                            Near_HotSellAdapter adapter = new Near_HotSellAdapter(getActivity(), data);
                            rcHotSell.setAdapter(adapter);
                        }
                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onComplete() {

                    }
                });
        sv.setListener(new SpringView.OnFreshListener() {
            @Override
            public void onRefresh() {

                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        getDataBean();
                        sv.onFinishFreshAndLoad();
                    }
                }, 1000);
            }

            @Override
            public void onLoadmore() {
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        sv.onFinishFreshAndLoad();
                    }
                }, 1000);
            }
        });

    }
    public void getDataBean(){
        NetUtils.getInstance().getApis()
                .getStateAllUserOrder(1)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<UserOrderBean>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(UserOrderBean userOrderBean) {
                        List<UserOrderBean.DataBean> orderlist = userOrderBean.getData();

                        if (orderlist != null && orderlist.size() > 0) {
                            Log.d("hmy", "待发货" + orderlist.size());

                            rlNoorder.setVisibility(View.GONE);
                            rcOrder.setVisibility(View.VISIBLE);
                            //传入列表数据
                            LinearLayoutManager manager = new LinearLayoutManager(getContext(), RecyclerView.VERTICAL, false);
                            rcOrder.setLayoutManager(manager);
                            ShipAdapter allOrderAdapter = new ShipAdapter(getContext(), orderlist);
                            rcOrder.setAdapter(allOrderAdapter);
                        } else {
                            rlNoorder.setVisibility(View.VISIBLE);
                            rcOrder.setVisibility(View.GONE);
                        }
                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }
}
