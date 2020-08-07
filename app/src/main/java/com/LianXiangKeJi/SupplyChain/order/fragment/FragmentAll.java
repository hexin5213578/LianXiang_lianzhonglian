package com.LianXiangKeJi.SupplyChain.order.fragment;

import android.os.Handler;
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
 * @ClassName:FragmentAll
 * @Author:hmy
 * @Description:java类作用描述 全部订单
 */
public class FragmentAll extends BaseFragment {
    @BindView(R.id.rc_order)
    RecyclerView rcOrder;
    @BindView(R.id.rl_noorder)
    RelativeLayout rlNoorder;
    @BindView(R.id.rc_hotSell)
    RecyclerView rcHotSell;
    @BindView(R.id.sv)
    SpringView sv;
    private List<UserOrderBean.DataBean> list;
    private List<String> textlist = new ArrayList<>();
    private LinearLayoutManager manager;

    @Override
    protected void getid(View view) {
        rcOrder = view.findViewById(R.id.rc_order);
        rcHotSell = view.findViewById(R.id.rc_hotSell);
        sv = view.findViewById(R.id.sv);
        rlNoorder = view.findViewById(R.id.rl_noorder);
    }

    @Override
    protected int getResId() {
        return R.layout.fragment_all;
    }

    @Override
    protected BasePresenter initPresenter() {
        return null;
    }

    @Override
    protected void getData() {

        for (int i = 0; i < 10; i++) {
            textlist.add("第" + i);
        }
        rcHotSell.getParent().requestDisallowInterceptTouchEvent(true);

        // TODO: 2020/7/21 测试热销展示的四条商品

        GridLayoutManager gridLayoutManager = new GridLayoutManager(getContext(), 2);
        rcHotSell.setLayoutManager(gridLayoutManager);

        Near_HotSellAdapter adapter = new Near_HotSellAdapter(getContext(), textlist);
        rcHotSell.setAdapter(adapter);

        getDataBean();

        sv.setHeader(new DefaultHeader(getContext()));
        sv.setFooter(new DefaultFooter(getContext()));

        sv.setListener(new SpringView.OnFreshListener() {
            @Override
            public void onRefresh() {
                getDataBean();


                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        sv.onFinishFreshAndLoad();
                    }
                }, 1000);
            }

            /**
             * @param
             */
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
        showDialog();
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
                        hideDialog();
                        List<UserOrderBean.DataBean> list = userOrderBean.getData();
                        if (list != null && list.size() > 0) {
                            Log.d("hmy", "全部订单" + list.size());

                            rlNoorder.setVisibility(View.GONE);
                            rcOrder.setVisibility(View.VISIBLE);
                            //传入列表数据
                            manager = new LinearLayoutManager(getContext(), RecyclerView.VERTICAL, false);
                            rcOrder.setLayoutManager(manager);
                            AllOrderAdapter allOrderAdapter = new AllOrderAdapter(getContext(), list);
                            rcOrder.setAdapter(allOrderAdapter);

                        } else {
                            rlNoorder.setVisibility(View.VISIBLE);
                            rcOrder.setVisibility(View.GONE);
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        hideDialog();
                        Toast.makeText(getContext(), "加载失败", Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }
}
