package com.LianXiangKeJi.SupplyChain.order.activity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.fragment.app.FragmentTransaction;
import androidx.viewpager.widget.ViewPager;

import com.LianXiangKeJi.SupplyChain.R;
import com.LianXiangKeJi.SupplyChain.base.BaseAvtivity;
import com.LianXiangKeJi.SupplyChain.base.BaseFragment;
import com.LianXiangKeJi.SupplyChain.base.BasePresenter;
import com.LianXiangKeJi.SupplyChain.order.fragment.FragmentAll;
import com.LianXiangKeJi.SupplyChain.order.fragment.FragmentFinish;
import com.LianXiangKeJi.SupplyChain.order.fragment.FragmentPayment;
import com.LianXiangKeJi.SupplyChain.order.fragment.FragmentReceipt;
import com.LianXiangKeJi.SupplyChain.order.fragment.FragmentShip;
import com.LianXiangKeJi.SupplyChain.order.presenter.UserOrderPresenter;
import com.google.android.material.tabs.TabLayout;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * @ClassName:OrderActivity
 * @Author:hmy
 * @Description:java类作用描述 我的订单页
 */
public class OrderActivity extends BaseAvtivity implements View.OnClickListener, RadioGroup.OnCheckedChangeListener {
    @BindView(R.id.back)
    ImageView back;
    @BindView(R.id.flContent)
    FrameLayout flContent;
    @BindView(R.id.title)
    TextView title;
    @BindView(R.id.tv_right)
    TextView tvRight;
    @BindView(R.id.rb_all)
    RadioButton rbAll;
    @BindView(R.id.rb_daifukuan)
    RadioButton rbDaifukuan;
    @BindView(R.id.rb_daishouhuo)
    RadioButton rbDaishouhuo;
    @BindView(R.id.rb_daifahuo)
    RadioButton rbDaifahuo;
    @BindView(R.id.rb_finish)
    RadioButton rbFinish;
    @BindView(R.id.rgMenu)
    RadioGroup rgMenu;
    /**
     * 当前展示的Fragment
     */
    private FragmentManager fmManager;
    private Fragment currentFragment;
    /**
     * 上次点击返回按钮的时间戳
     */
    private long firstPressedTime;

    /**
     * 创建Fragment实例
     */
    private FragmentAll fragmentAll;
    private FragmentPayment fragmentPayment;
    private FragmentShip fragmentShip;
    private FragmentReceipt fragmentReceipt;
    private FragmentFinish fragmentFinish;
    @Override
    protected int getResId() {
        return R.layout.activity_order;
    }

    @Override
    protected void getData() {


        //全部订单
        fragmentAll = new FragmentAll();
        //待付款订单
        fragmentPayment = new FragmentPayment();
        //待发货订单
        fragmentShip = new FragmentShip();
        //待收货订单
        fragmentReceipt = new FragmentReceipt();
        //已完成订单
        fragmentFinish = new FragmentFinish();

        setTitleColor(OrderActivity.this);
        back.setOnClickListener(this);
        title.setText("我的订单");
        tvRight.setVisibility(View.GONE);
        rgMenu.setOnCheckedChangeListener(this);


        fmManager=getSupportFragmentManager();

        Intent intent = getIntent();
        int flag = intent.getIntExtra("flag", 0);
        Log.d("hmy", "当前跳转到的页面为" + flag);
        //通过标记判断需要展示的页面
        if (flag == 0) {
            rbAll.setChecked(true);
        } else if (flag == 1) {
            rbDaifukuan.setChecked(true);
        } else if (flag == 2) {
            rbDaifahuo.setChecked(true);
        } else if (flag == 3) {
            rbDaishouhuo.setChecked(true);
        } else {
            rbFinish.setChecked(true);
        }

    }

    @Override
    protected BasePresenter initPresenter() {
        return new UserOrderPresenter(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.back:
                finish();
                break;
        }
    }


    @Override
    public void onCheckedChanged(RadioGroup radioGroup, int i) {
        switch (i) {
            case R.id.rb_all:
                replace(fragmentAll);
                break;
            case R.id.rb_daifukuan:
                replace(fragmentPayment);
                break;
            case R.id.rb_daifahuo:
                replace(fragmentShip);
                break;
            case R.id.rb_daishouhuo:
                replace(fragmentReceipt);
                break;
            case R.id.rb_finish:
                replace(fragmentFinish);
                break;
            default:
                break;
        }
    }
    /**
     * 切换页面显示fragment
     *
     * @param to 跳转到的fragment
     */
    private void replace(Fragment to) {
        if (to == null || to == currentFragment) {
            // 如果跳转的fragment为空或者跳转的fragment为当前fragment则不做操作
            return;
        }
        if (currentFragment == null) {
            // 如果当前fragment为空,即为第一次添加fragment
            fmManager.beginTransaction()
                    .add(R.id.flContent, to)
                    .commitAllowingStateLoss();
            currentFragment = to;
            return;
        }
        // 切换fragment
        FragmentTransaction transaction = fmManager.beginTransaction().hide(currentFragment);
        if (!to.isAdded()) {
            transaction.add(R.id.flContent, to);
        } else {
            transaction.show(to);
        }
        transaction.commitAllowingStateLoss();
        currentFragment = to;
    }
}
