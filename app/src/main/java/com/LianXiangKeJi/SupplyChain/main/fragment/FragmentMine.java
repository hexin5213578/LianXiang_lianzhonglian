package com.LianXiangKeJi.SupplyChain.main.fragment;

import android.content.Intent;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.LianXiangKeJi.SupplyChain.R;
import com.LianXiangKeJi.SupplyChain.address.activity.MyAddressActivity;
import com.LianXiangKeJi.SupplyChain.alwaysbuy.activity.AlwaysBuyActivity;
import com.LianXiangKeJi.SupplyChain.base.BaseFragment;
import com.LianXiangKeJi.SupplyChain.base.BasePresenter;
import com.LianXiangKeJi.SupplyChain.base.Common;
import com.LianXiangKeJi.SupplyChain.login.activity.LoginActivity;
import com.LianXiangKeJi.SupplyChain.movable.activity.CouponActivity;
import com.LianXiangKeJi.SupplyChain.order.activity.OrderActivity;
import com.LianXiangKeJi.SupplyChain.order.bean.UserOrderBean;
import com.LianXiangKeJi.SupplyChain.setup.activity.SetUpActivity;
import com.LianXiangKeJi.SupplyChain.utils.NetUtils;
import com.LianXiangKeJi.SupplyChain.utils.SPUtil;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.CircleCrop;
import com.bumptech.glide.request.RequestOptions;
import com.tencent.bugly.crashreport.CrashReport;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.io.File;
import java.util.List;

import butterknife.BindView;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

/**
 * @ClassName:FragmentHome
 * @Author:hmy
 * @Description:java类作用描述
 */
public class FragmentMine extends BaseFragment implements View.OnClickListener {

    @BindView(R.id.ll_seeMore)
    LinearLayout llSeeMore;
    @BindView(R.id.daifukuan)
    RelativeLayout daifukuan;
    @BindView(R.id.daifahuo)
    RelativeLayout daifahuo;
    @BindView(R.id.daishouhuo)
    RelativeLayout daishouhuo;
    @BindView(R.id.yiwancheng)
    RelativeLayout yiwancheng;
    @BindView(R.id.setup)
    ImageView setup;
    @BindView(R.id.iv_yuan)
    ImageView ivYuan;
    @BindView(R.id.tv_login)
    TextView tvLogin;
    @BindView(R.id.rl_changmai)
    RelativeLayout rlChangmai;
    @BindView(R.id.rl_myaddress)
    RelativeLayout rlMyaddress;
    @BindView(R.id.tv_username)
    TextView tvUsername;
    @BindView(R.id.rl_youhui)
    RelativeLayout rlYouhui;
    @BindView(R.id.tv_daifukuan)
    TextView tvDaifukuan;
    @BindView(R.id.tv_daifahuo)
    TextView tvDaifahuo;
    @BindView(R.id.tv_daishouhuo)
    TextView tvDaishouhuo;
    @BindView(R.id.tv_yiwancheng)
    TextView tvYiwancheng;
    private Intent intent_order;
    private String token;
    int a = 0;
    int b = 0;
    int c = 0;
    int d = 0;

    @Override
    protected void getid(View view) {

    }

    @Override
    protected int getResId() {
        return R.layout.fragment_mine;
    }

    @Override
    protected BasePresenter initPresenter() {
        return null;
    }

    @Override
    public void onResume() {
        super.onResume();
        if (!EventBus.getDefault().isRegistered(this)) {
            EventBus.getDefault().register(this);
        }
        getDataBean();
        Log.d("hmy","onResume");
    }

    @Override
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);
        getDataBean();
        Log.d("hmy","onHiddenChanged");
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (EventBus.getDefault().isRegistered(this)) {
            EventBus.getDefault().unregister(this);
        }
    }

    @Override
    protected void getData() {
        token = Common.getToken();
        setup.setOnClickListener(this);
        tvLogin.setOnClickListener(this);
        llSeeMore.setOnClickListener(this);
        daifukuan.setOnClickListener(this);
        daifahuo.setOnClickListener(this);
        daishouhuo.setOnClickListener(this);
        yiwancheng.setOnClickListener(this);
        rlChangmai.setOnClickListener(this);
        rlMyaddress.setOnClickListener(this);
        rlYouhui.setOnClickListener(this);
        ivYuan.setOnClickListener(this);
        String username = SPUtil.getInstance().getData(getContext(), SPUtil.FILE_NAME, SPUtil.USER_NAME);
        String hearurl = SPUtil.getInstance().getData(getContext(), SPUtil.FILE_NAME, SPUtil.HEAD_URL);
        // 设置用户信息
        if (!TextUtils.isEmpty(token)) {

            Glide.with(getContext()).load(hearurl).apply(RequestOptions.bitmapTransform(new CircleCrop())).into(ivYuan);

            tvLogin.setVisibility(View.GONE);
            tvUsername.setVisibility(View.VISIBLE);
            tvUsername.setText(username);
        }

        // 创建跳转至订单页的Intent
        intent_order = new Intent(getContext(), OrderActivity.class);

        getDataBean();
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void getHeadImage(File file) {

        Glide.with(getContext()).load(file).apply(RequestOptions.bitmapTransform(new CircleCrop())).into(ivYuan);

    }



    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            //  设置页面
            case R.id.setup:
                if (TextUtils.isEmpty(token)) {
                    Toast.makeText(getContext(), "请先登录", Toast.LENGTH_SHORT).show();
                } else {
                    startActivity(new Intent(getContext(), SetUpActivity.class));
                }
                break;
            // 登录注册
            case R.id.tv_login:
            case R.id.iv_yuan:
                if (!TextUtils.isEmpty(token)) {
                    return;
                } else {
                    startActivity(new Intent(getContext(), LoginActivity.class));
                }
                break;
            //查看全部订单
            case R.id.ll_seeMore:
                if (TextUtils.isEmpty(token)) {
                    Toast.makeText(getContext(), "请先登录", Toast.LENGTH_SHORT).show();
                } else {
                    intent_order.putExtra("flag", 0);
                    startActivity(intent_order);
                }
                break;
            //查看待付款订单
            case R.id.daifukuan:
                if (TextUtils.isEmpty(token)) {
                    Toast.makeText(getContext(), "请先登录", Toast.LENGTH_SHORT).show();
                } else {
                    intent_order.putExtra("flag", 1);
                    startActivity(intent_order);
                }

                break;
            //查看待发货订单
            case R.id.daifahuo:
                if (TextUtils.isEmpty(token)) {
                    Toast.makeText(getContext(), "请先登录", Toast.LENGTH_SHORT).show();
                } else {
                    intent_order.putExtra("flag", 2);
                    startActivity(intent_order);
                }
                break;
            //查看待收货订单
            case R.id.daishouhuo:
                if (TextUtils.isEmpty(token)) {
                    Toast.makeText(getContext(), "请先登录", Toast.LENGTH_SHORT).show();
                } else {
                    intent_order.putExtra("flag", 3);
                    startActivity(intent_order);
                }
                break;
            //查看已完成订单
            case R.id.yiwancheng:
                if (TextUtils.isEmpty(token)) {
                    Toast.makeText(getContext(), "请先登录", Toast.LENGTH_SHORT).show();
                } else {
                    intent_order.putExtra("flag", 4);
                    startActivity(intent_order);
                }
                break;
            //  常买商品页
            case R.id.rl_changmai:
                if (TextUtils.isEmpty(token)) {
                    Toast.makeText(getContext(), "请先登录", Toast.LENGTH_SHORT).show();
                } else {
                    startActivity(new Intent(getContext(), AlwaysBuyActivity.class));
                }
                break;
            //  我的地址页
            case R.id.rl_myaddress:
                if (TextUtils.isEmpty(token)) {
                    Toast.makeText(getContext(), "请先登录", Toast.LENGTH_SHORT).show();
                } else {
                    Intent intent = new Intent(getContext(), MyAddressActivity.class);
                    startActivity(intent);
                }
                break;
            //  优惠券
            case R.id.rl_youhui:
                if (TextUtils.isEmpty(token)) {
                    Toast.makeText(getContext(), "请先登录", Toast.LENGTH_SHORT).show();
                } else {
                    startActivity(new Intent(getContext(), CouponActivity.class));
                }
                break;
            default:
                break;
        }
    }

    public void getDataBean() {
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
                        List<UserOrderBean.DataBean> data = userOrderBean.getData();
                        for (int i = 0; i < data.size(); i++) {
                            UserOrderBean.DataBean dataBean = data.get(i);
                            int orderState = dataBean.getOrderState();
                            if (orderState == 0) {
                                a++;
                            } else if (orderState == 1) {
                                b++;
                            } else if (orderState == 3) {
                                c++;
                            } else if (orderState == 4) {
                                d++;
                            }
                        }
                        if (a == 0) {
                            tvDaifukuan.setVisibility(View.GONE);
                        } else {
                            tvDaifukuan.setVisibility(View.VISIBLE);
                        }
                        if (b == 0) {
                            tvDaifahuo.setVisibility(View.GONE);
                        } else {
                            tvDaifahuo.setVisibility(View.VISIBLE);

                        }
                        if (c == 0) {
                            tvDaishouhuo.setVisibility(View.GONE);
                        } else {
                            tvDaishouhuo.setVisibility(View.VISIBLE);

                        }
                        if (d == 0) {
                            tvYiwancheng.setVisibility(View.GONE);
                        } else {
                            tvYiwancheng.setVisibility(View.VISIBLE);

                        }

                        tvDaifukuan.setText(a + "");
                        tvDaifahuo.setText(b + "");
                        tvDaishouhuo.setText(c + "");
                        tvYiwancheng.setText(d + "");
                        a = 0;
                        b = 0;
                        c = 0;
                        d = 0;

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
