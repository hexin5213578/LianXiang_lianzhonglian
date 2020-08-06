package com.LianXiangKeJi.SupplyChain.movable.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.LianXiangKeJi.SupplyChain.R;
import com.LianXiangKeJi.SupplyChain.base.BaseAvtivity;
import com.LianXiangKeJi.SupplyChain.base.BasePresenter;
import com.LianXiangKeJi.SupplyChain.movable.adapter.CouPonAdapter;
import com.LianXiangKeJi.SupplyChain.movable.bean.CouponBean;
import com.LianXiangKeJi.SupplyChain.movable.bean.SaveCouponIdBean;
import com.LianXiangKeJi.SupplyChain.utils.NetUtils;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class CouponActivity extends BaseAvtivity implements View.OnClickListener {
    @BindView(R.id.back)
    ImageView back;
    @BindView(R.id.title)
    TextView title;
    @BindView(R.id.tv_right)
    TextView tvRight;
    @BindView(R.id.rc_coupon)
    RecyclerView rcCoupon;

    @Override
    protected int getResId() {
        return R.layout.acitvity_coupon;
    }

    @Override
    protected void getData() {
        back.setOnClickListener(this);
        title.setText("我的优惠券");
        tvRight.setVisibility(View.GONE);
        setTitleColor(CouponActivity.this);
        showDialog();
        NetUtils.getInstance().getApis().doGetMyCoupon()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<CouponBean>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(CouponBean couponBean) {
                        List<CouponBean.DataBean> data = couponBean.getData();
                        if(data!=null && data.size()>0){
                            hideDialog();
                            LinearLayoutManager manager = new LinearLayoutManager(CouponActivity.this,RecyclerView.VERTICAL,false);
                            rcCoupon.setLayoutManager(manager);
                            CouPonAdapter couPonAdapter = new CouPonAdapter(CouponActivity.this, data);
                            rcCoupon.setAdapter(couPonAdapter);
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

    @Override
    protected BasePresenter initPresenter() {
        return null;
    }

    @Override
    protected void onResume() {
        super.onResume();
        if(!EventBus.getDefault().isRegistered(this)){
            EventBus.getDefault().register(this);
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if(EventBus.getDefault().isRegistered(this)){
            EventBus.getDefault().unregister(this);
        }
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void getbean(SaveCouponIdBean bean){
        if(bean.getClose().equals("关闭界面")){
            finish();
        }
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.back:
                //关闭页面
                finish();
                break;
        }
    }
}
