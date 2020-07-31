package com.LianXiangKeJi.SupplyChain.main.fragment;

import android.content.Intent;
import android.text.TextUtils;
import android.view.View;
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
import goodsdetails.activity.GoodsDetailsActivity;
import com.LianXiangKeJi.SupplyChain.login.activity.LoginActivity;
import com.LianXiangKeJi.SupplyChain.order.activity.OrderActivity;
import com.LianXiangKeJi.SupplyChain.setup.activity.SetUpActivity;
import com.LianXiangKeJi.SupplyChain.utils.SPUtil;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.CircleCrop;
import com.bumptech.glide.request.RequestOptions;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.io.File;

import butterknife.BindView;

/**
 * @ClassName:FragmentHome
 * @Author:hmy
 * @Description:java类作用描述
 */
public class FragmentMine extends BaseFragment implements View.OnClickListener {

    @BindView(R.id.ll_seeMore)
    LinearLayout llSeeMore;
    @BindView(R.id.daifukuan)
    LinearLayout daifukuan;
    @BindView(R.id.daifahuo)
    LinearLayout daifahuo;
    @BindView(R.id.daishouhuo)
    LinearLayout daishouhuo;
    @BindView(R.id.yiwancheng)
    LinearLayout yiwancheng;
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
    @BindView(R.id.rl_bindphone)
    RelativeLayout rlBindphone;
    @BindView(R.id.tv_username)
    TextView tvUsername;
    private Intent intent_order;
    private String token;

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
        if(!EventBus.getDefault().isRegistered(this)){
            EventBus.getDefault().register(this);
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if(EventBus.getDefault().isRegistered(this)){
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
        rlBindphone.setOnClickListener(this);

        String username = SPUtil.getInstance().getData(getContext(), SPUtil.FILE_NAME, SPUtil.USER_NAME);
        String hearurl = SPUtil.getInstance().getData(getContext(), SPUtil.FILE_NAME, SPUtil.HEAD_URL);
        // TODO: 2020/7/18 设置用户信息
        if (!TextUtils.isEmpty(token)) {

            Glide.with(getContext()).load(hearurl).apply(RequestOptions.bitmapTransform(new CircleCrop())).into(ivYuan);

            tvLogin.setVisibility(View.GONE);
            tvUsername.setVisibility(View.VISIBLE);
            tvUsername.setText(username);
        }

        // TODO: 2020/7/18 创建跳转至订单页的Intent
        intent_order = new Intent(getContext(), OrderActivity.class);
    }
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void getHeadImage(File file){

        Glide.with(getContext()).load(file).apply(RequestOptions.bitmapTransform(new CircleCrop())).into(ivYuan);

    }
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void getUserName(String name){
        tvUsername.setText(name);
    }
    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            // TODO: 2020/7/15 设置页面 
            case R.id.setup:
                if (TextUtils.isEmpty(token)) {
                    Toast.makeText(getContext(), "请先登录", Toast.LENGTH_SHORT).show();
                } else {
                    startActivity(new Intent(getContext(), SetUpActivity.class));
                }
                break;
            // TODO: 2020/7/15 登录注册 
            case R.id.tv_login:
                if (!TextUtils.isEmpty(token)) {
                    return;
                } else {
                    startActivity(new Intent(getContext(), LoginActivity.class));
                }
                break;
            // TODO: 2020/7/15 查看全部订单 
            case R.id.ll_seeMore:
                if (TextUtils.isEmpty(token)) {
                    Toast.makeText(getContext(), "请先登录", Toast.LENGTH_SHORT).show();
                } else {
                    intent_order.putExtra("flag", 0);
                    startActivity(intent_order);
                }
                break;
            // TODO: 2020/7/15 查看待付款订单 
            case R.id.daifukuan:
                if (TextUtils.isEmpty(token)) {
                    Toast.makeText(getContext(), "请先登录", Toast.LENGTH_SHORT).show();
                } else {
                    intent_order.putExtra("flag", 1);
                    startActivity(intent_order);
                }

                break;
            // TODO: 2020/7/15 查看待发货订单 
            case R.id.daifahuo:
                if (TextUtils.isEmpty(token)) {
                    Toast.makeText(getContext(), "请先登录", Toast.LENGTH_SHORT).show();
                } else {
                    intent_order.putExtra("flag", 2);
                    startActivity(intent_order);
                }
                break;
            // TODO: 2020/7/15 查看待收货订单 
            case R.id.daishouhuo:
                if (TextUtils.isEmpty(token)) {
                    Toast.makeText(getContext(), "请先登录", Toast.LENGTH_SHORT).show();
                } else {
                    intent_order.putExtra("flag", 3);
                    startActivity(intent_order);
                }
                break;
            // TODO: 2020/7/15 查看已完成订单
            case R.id.yiwancheng:
                if (TextUtils.isEmpty(token)) {
                    Toast.makeText(getContext(), "请先登录", Toast.LENGTH_SHORT).show();
                } else {
                    intent_order.putExtra("flag", 4);
                    startActivity(intent_order);
                }
                break;
            // TODO: 2020/7/15 常买商品页
            case R.id.rl_changmai:
                if (TextUtils.isEmpty(token)) {
                    Toast.makeText(getContext(), "请先登录", Toast.LENGTH_SHORT).show();
                }else{
                    startActivity(new Intent(getContext(), AlwaysBuyActivity.class));
                }
                break;
            // TODO: 2020/7/15 我的地址页
            case R.id.rl_myaddress:
                if (TextUtils.isEmpty(token)) {
                    Toast.makeText(getContext(), "请先登录", Toast.LENGTH_SHORT).show();
                } else {
                    Intent intent = new Intent(getContext(), MyAddressActivity.class);
                    startActivity(intent);
                }
                break;
            // TODO: 2020/7/15 绑定手机页
            case R.id.rl_bindphone:
                startActivity(new Intent(getContext(), GoodsDetailsActivity.class));
                break;
            default:
                break;
        }
    }
}
