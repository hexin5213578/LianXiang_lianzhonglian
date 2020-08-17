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
import com.LianXiangKeJi.SupplyChain.order.adapter.FinishAdapter;
import com.LianXiangKeJi.SupplyChain.order.adapter.Near_HotSellAdapter;
import com.LianXiangKeJi.SupplyChain.order.adapter.ShipAdapter;
import com.LianXiangKeJi.SupplyChain.order.bean.UserOrderBean;
import com.LianXiangKeJi.SupplyChain.recommend.bean.HotSellBean;
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
 * @ClassName:FragmentFinish
 * @Author:hmy
 * @Description:java类作用描述 已完成订单
 */
public class FragmentFinish extends BaseFragment {
    @BindView(R.id.rc_order)
    RecyclerView rcOrder;
    @BindView(R.id.rl_noorder)
    RelativeLayout rlNoorder;
    @BindView(R.id.sv)
    SpringView sv;
    private List<UserOrderBean.DataBean> data = new ArrayList<>();

    int page = 1;
    int state=4;
    @Override
    protected void getid(View view) {

    }

    @Override
    protected int getResId() {
        return R.layout.fragment_finish;
    }

    @Override
    protected BasePresenter initPresenter() {
        return null;
    }

    @Override
    protected void getData() {
        sv.setHeader(new DefaultHeader(getContext()));
        sv.setFooter(new DefaultFooter(getContext()));
        data.clear();
        getDataBean(page,state);

        sv.setListener(new SpringView.OnFreshListener() {
            @Override
            public void onRefresh() {

                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        data.clear();
                        page=1;
                        getDataBean(page,state);
                        sv.onFinishFreshAndLoad();
                    }
                }, 1000);
            }

            @Override
            public void onLoadmore() {
                page++;
                getDataBean(page,state);
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
            page=1;
            getDataBean(page,state);
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
    public void getDataBean(int page,int orderstate){
       NetUtils.getInstance().getApis().getUserOrderLimete(page,orderstate)
               .subscribeOn(Schedulers.io())
               .observeOn(AndroidSchedulers.mainThread())
               .subscribe(new Observer<UserOrderBean>() {
                   @Override
                   public void onSubscribe(Disposable d) {

                   }
                   @Override
                   public void onNext(UserOrderBean userOrderBean) {
                       List<UserOrderBean.DataBean> orderlist = userOrderBean.getData();
                       data.addAll(orderlist);
                       if (data != null && data.size() > 0) {
                           Log.d("hmy", "已完成订单" + data.size());
                           rlNoorder.setVisibility(View.GONE);
                           sv.setVisibility(View.VISIBLE);
                           //传入列表数据
                           LinearLayoutManager manager = new LinearLayoutManager(getContext(), RecyclerView.VERTICAL, false);
                           rcOrder.setLayoutManager(manager);
                           FinishAdapter adapter = new FinishAdapter(getContext(), data);
                           rcOrder.setAdapter(adapter);
                       } else {
                           rlNoorder.setVisibility(View.VISIBLE);
                           sv.setVisibility(View.GONE);
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
