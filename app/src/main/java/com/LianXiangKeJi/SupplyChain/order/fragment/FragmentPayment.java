package com.LianXiangKeJi.SupplyChain.order.fragment;

import android.annotation.SuppressLint;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.Toast;

import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.LianXiangKeJi.SupplyChain.R;
import com.LianXiangKeJi.SupplyChain.base.BaseFragment;
import com.LianXiangKeJi.SupplyChain.base.BasePresenter;
import com.LianXiangKeJi.SupplyChain.order.adapter.AllOrderAdapter;
import com.LianXiangKeJi.SupplyChain.order.adapter.Near_HotSellAdapter;
import com.LianXiangKeJi.SupplyChain.order.adapter.PaymentAdapter;
import com.LianXiangKeJi.SupplyChain.order.bean.PayResult;
import com.LianXiangKeJi.SupplyChain.order.bean.UserOrderBean;
import com.LianXiangKeJi.SupplyChain.order.bean.ZfbBean;
import com.LianXiangKeJi.SupplyChain.order.bean.ZfbBean1;
import com.LianXiangKeJi.SupplyChain.recommend.bean.HotSellBean;
import com.LianXiangKeJi.SupplyChain.utils.NetUtils;
import com.alipay.sdk.app.PayTask;
import com.liaoinstan.springview.container.DefaultFooter;
import com.liaoinstan.springview.container.DefaultHeader;
import com.liaoinstan.springview.widget.SpringView;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

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
    private List<UserOrderBean.DataBean> list;
    private static final int SDK_PAY_FLAG = 1;

    @Override
    protected void getid(View view) {

    }
    @Override
    protected int getResId() {
        return R.layout.fragment_payment;
    }

    @Override
    protected BasePresenter initPresenter() {
        return null;
    }
    private Handler mHandler = new Handler() {
        @SuppressLint("HandlerLeak")
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case SDK_PAY_FLAG: {
                    //这里接收支付宝的回调信息
                    //需要注意的是，支付结果一定要调用自己的服务端来确定，不能通过支付宝的回调结果来判断
                    PayResult payResult = new PayResult((Map<String, String>) msg.obj);
                    /**
                     对于支付结果，请商户依赖服务端的异步通知结果。同步通知结果，仅作为支付结束的通知。
                     */
                    String resultInfo = payResult.getResult();// 同步返回需要验证的信息
                    String resultStatus = payResult.getResultStatus();
                    // 判断resultStatus 为9000则代表支付成功
                    if (TextUtils.equals(resultStatus, "9000")) {
                        // 该笔订单是否真实支付成功，需要依赖服务端的异步通知。
                        Toast.makeText(getContext(), "支付成功", Toast.LENGTH_SHORT).show();
                    } else {
                        // 该笔订单真实的支付结果，需要依赖服务端的异步通知。
                        Toast.makeText(getContext(), "支付失败", Toast.LENGTH_SHORT).show();
                    }
                    break;
                }
                default:
                    break;
            }
        }
    };
    @Override
    protected void getData() {
        sv.setHeader(new DefaultHeader(getContext()));

        getDataBean();

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
                        if(data.size()>0 && data!=null){
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
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void getZfbData(ZfbBean1 bean){
        //调用支付宝支付
        String info = bean.getData().getBody();
        Runnable payRunnable = new Runnable() {
            @Override
            public void run() {
                PayTask alipay = new PayTask(getActivity());
                Map<String,String> result = alipay.payV2(info,true);

                Message msg = new Message();
                msg.what = SDK_PAY_FLAG;
                msg.obj = result;
                mHandler.sendMessage(msg);
            }
        };
        // 必须异步调用
        Thread payThread = new Thread(payRunnable);
        payThread.start();
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

                        if (orderlist.size()>0 && orderlist!=null){
                            for (int i =0;i<orderlist.size();i++){
                                UserOrderBean.DataBean dataBean = orderlist.get(i);
                                int orderState = orderlist.get(i).getOrderState();
                                if(orderState==0){
                                    list.add(dataBean);
                                }
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
