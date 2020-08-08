package com.LianXiangKeJi.SupplyChain.order.activity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;

import com.LianXiangKeJi.SupplyChain.R;
import com.LianXiangKeJi.SupplyChain.base.BaseAvtivity;
import com.LianXiangKeJi.SupplyChain.base.BaseFragment;
import com.LianXiangKeJi.SupplyChain.base.BasePresenter;
import com.LianXiangKeJi.SupplyChain.order.bean.UserOrderBean;
import com.LianXiangKeJi.SupplyChain.order.contract.UserOrderContract;
import com.LianXiangKeJi.SupplyChain.order.fragment.FragmentAll;
import com.LianXiangKeJi.SupplyChain.order.fragment.FragmentFinish;
import com.LianXiangKeJi.SupplyChain.order.fragment.FragmentPayment;
import com.LianXiangKeJi.SupplyChain.order.fragment.FragmentReceipt;
import com.LianXiangKeJi.SupplyChain.order.fragment.FragmentShip;
import com.LianXiangKeJi.SupplyChain.order.presenter.UserOrderPresenter;
import com.LianXiangKeJi.SupplyChain.utils.NetUtils;
import com.google.android.material.tabs.TabLayout;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

/**
 * @ClassName:OrderActivity
 * @Author:hmy
 * @Description:java类作用描述 我的订单页
 */
public class OrderActivity extends BaseAvtivity implements View.OnClickListener{
    @BindView(R.id.back)
    ImageView back;
    @BindView(R.id.tab)
    TabLayout tab;
    @BindView(R.id.vp)
    ViewPager vp;
    @BindView(R.id.title)
    TextView title;
    @BindView(R.id.tv_right)
    TextView tvRight;

    private List<String> list = new ArrayList<>();
    private List<BaseFragment> fragments = new ArrayList<>();
    private List<String> textlist = new ArrayList<>();
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
        setTitleColor(OrderActivity.this);
        back.setOnClickListener(this);
        title.setText("我的订单");
        tvRight.setVisibility(View.GONE);
        for (int i = 0; i < 10; i++) {
            textlist.add("第" + i);
        }
/*        GridLayoutManager gridLayoutManager = new GridLayoutManager(OrderActivity.this, 2);
        rcHotSell.setLayoutManager(gridLayoutManager);

        Near_HotSellAdapter adapter = new Near_HotSellAdapter(OrderActivity.this, textlist);
        rcHotSell.setAdapter(adapter);*/


        list.add("全部");
        list.add("待付款");
        list.add("待发货");
        list.add("待收货");
        list.add("已完成");


        tab.addTab(tab.newTab().setText(list.get(0)));
        tab.addTab(tab.newTab().setText(list.get(1)));
        tab.addTab(tab.newTab().setText(list.get(2)));
        tab.addTab(tab.newTab().setText(list.get(3)));
        tab.addTab(tab.newTab().setText(list.get(4)));
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

        //发起全部订单的请求


        //将订单添加进集合
        fragments.add(fragmentAll);
        fragments.add(fragmentPayment);
        fragments.add(fragmentShip);
        fragments.add(fragmentReceipt);
        fragments.add(fragmentFinish);

        MyAdapter myAdapter = new MyAdapter(getSupportFragmentManager());
        vp.setAdapter(myAdapter);
        tab.setupWithViewPager(vp);

        Intent intent = getIntent();
        int flag = intent.getIntExtra("flag", 0);
        Log.d("hmy", "当前跳转到的页面为" + flag);
        //通过标记判断需要展示的页面
        if (flag == 0) {
            vp.setCurrentItem(0);
        } else if (flag == 1) {
            vp.setCurrentItem(1);
        } else if (flag == 2) {
            vp.setCurrentItem(2);
        } else if (flag == 3) {
            vp.setCurrentItem(3);
        } else {
            vp.setCurrentItem(4);
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


    //创建viewpager适配器
    public class MyAdapter extends FragmentPagerAdapter {


        public MyAdapter(@NonNull FragmentManager fm) {
            super(fm);
        }

        @NonNull
        @Override
        public Fragment getItem(int position) {
            return fragments.get(position);
        }

        @Override
        public int getCount() {
            return fragments.size();
        }

        @Nullable
        @Override
        public CharSequence getPageTitle(int position) {
            return list.get(position);
        }
    }
}
