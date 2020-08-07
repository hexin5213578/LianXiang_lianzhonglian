package com.LianXiangKeJi.SupplyChain.order.fragment;

import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.RelativeLayout;

import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.LianXiangKeJi.SupplyChain.R;
import com.LianXiangKeJi.SupplyChain.base.BaseFragment;
import com.LianXiangKeJi.SupplyChain.base.BasePresenter;
import com.LianXiangKeJi.SupplyChain.order.adapter.AllOrderAdapter;
import com.LianXiangKeJi.SupplyChain.order.adapter.Near_HotSellAdapter;
import com.LianXiangKeJi.SupplyChain.order.adapter.PaymentAdapter;
import com.LianXiangKeJi.SupplyChain.order.bean.UserOrderBean;
import com.LianXiangKeJi.SupplyChain.utils.NetUtils;
import com.liaoinstan.springview.container.DefaultFooter;
import com.liaoinstan.springview.container.DefaultHeader;
import com.liaoinstan.springview.widget.SpringView;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

/**
 * @ClassName:FragmentPayment
 * @Author:hmy
 * @Description:java类作用描述 待付款订单
 */
public class FragmentPayment extends BaseFragment {
    @BindView(R.id.rc_order)
    RecyclerView rcOrder;
    @BindView(R.id.rl_noorder)
    RelativeLayout rlNoorder;
    @BindView(R.id.rc_hotSell)
    RecyclerView rcHotSell;
    @BindView(R.id.sv)
    SpringView sv;
    private List<String> textlist = new ArrayList<>();
    private List<UserOrderBean.DataBean> list;

    @Override
    protected void getid(View view) {
        rcOrder = view.findViewById(R.id.rc_order);
        rcHotSell = view.findViewById(R.id.rc_hotSell);
        sv = view.findViewById(R.id.sv);
        rlNoorder = view.findViewById(R.id.rl_noorder);
    }

    @Override
    protected int getResId() {
        return R.layout.fragment_payment;
    }

    @Override
    protected BasePresenter initPresenter() {
        return null;
    }

    @Override
    protected void getData() {
        sv.setHeader(new DefaultHeader(getContext()));
        sv.setFooter(new DefaultFooter(getContext()));

        getDataBean();

        for (int i = 0; i <= 10; i++) {
            textlist.add("第" + i);
        }

        // TODO: 2020/7/21 测试热销展示的四条商品
        GridLayoutManager gridLayoutManager = new GridLayoutManager(getContext(), 2);
        rcHotSell.setLayoutManager(gridLayoutManager);

        Near_HotSellAdapter adapter = new Near_HotSellAdapter(getContext(), textlist);
        rcHotSell.setAdapter(adapter);
        sv.setListener(new SpringView.OnFreshListener() {
            @Override
            public void onRefresh() {

                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        sv.onFinishFreshAndLoad();
                        getDataBean();
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
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void refresh(String str){
        if (str.equals("刷新界面")){
            getDataBean();
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
    public void onDestroy() {
        super.onDestroy();
        if (EventBus.getDefault().isRegistered(this)) {
            EventBus.getDefault().unregister(this);
        }
    }

    public void getDataBean(){
        NetUtils.getInstance().getApis()
                .getUserOrder()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<UserOrderBean>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(UserOrderBean userOrderBean) {
                        List<UserOrderBean.DataBean> orderlist = userOrderBean.getData();
                        list = new ArrayList<>();

                        for (int i =0;i<orderlist.size();i++){
                            UserOrderBean.DataBean dataBean = orderlist.get(i);
                            int orderState = orderlist.get(i).getOrderState();
                            if(orderState==0){
                                list.add(dataBean);
                            }
                        }

                        if (list != null && list.size() > 0) {
                            Log.d("hmy", "代付款订单" + list.size());

                            rlNoorder.setVisibility(View.GONE);
                            rcOrder.setVisibility(View.VISIBLE);
                            //传入列表数据
                            LinearLayoutManager manager = new LinearLayoutManager(getContext(), RecyclerView.VERTICAL, false);
                            rcOrder.setLayoutManager(manager);
                            PaymentAdapter allOrderAdapter = new PaymentAdapter(getContext(), list);
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
