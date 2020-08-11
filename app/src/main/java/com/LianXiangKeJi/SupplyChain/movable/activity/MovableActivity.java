package com.LianXiangKeJi.SupplyChain.movable.activity;

import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.LianXiangKeJi.SupplyChain.R;
import com.LianXiangKeJi.SupplyChain.base.BaseAvtivity;
import com.LianXiangKeJi.SupplyChain.base.BasePresenter;
import com.LianXiangKeJi.SupplyChain.movable.adapter.MovableAdapter;
import com.LianXiangKeJi.SupplyChain.movable.bean.MovableBean;
import com.LianXiangKeJi.SupplyChain.utils.NetUtils;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class MovableActivity extends BaseAvtivity {
    @BindView(R.id.rc_coupon_get)
    RecyclerView rcCouponGet;
    @BindView(R.id.l1)
    LinearLayout l1;
    @BindView(R.id.back)
    ImageView back;
    @BindView(R.id.title)
    TextView title;

    @Override
    protected int getResId() {
        return R.layout.activity_movable;
    }

    @Override
    protected void getData() {
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        title.setText("领取优惠券");
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Window window = MovableActivity.this.getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(MovableActivity.this.getResources().getColor(R.color.activity_title));
        }
        //查询所有优惠券
        showDialog();
        NetUtils.getInstance().getApis().doGetAllCoupon()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<MovableBean>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }
                    @Override
                    public void onNext(MovableBean couponBean) {
                        hideDialog();
                        List<MovableBean.DataBean> data = couponBean.getData();
                        if (data != null && data.size() > 0) {
                            LinearLayoutManager manager = new LinearLayoutManager(MovableActivity.this, RecyclerView.VERTICAL, false);
                            rcCouponGet.setLayoutManager(manager);
                            MovableAdapter movableAdapter = new MovableAdapter(MovableActivity.this, data);
                            rcCouponGet.setAdapter(movableAdapter);

                        }

                    }

                    @Override
                    public void onError(Throwable e) {
                        hideDialog();
                        Toast.makeText(MovableActivity.this, "加载失败", Toast.LENGTH_SHORT).show();
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

}
