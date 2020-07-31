package com.LianXiangKeJi.SupplyChain.order.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.widget.NestedScrollView;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;

import com.LianXiangKeJi.SupplyChain.R;
import com.LianXiangKeJi.SupplyChain.base.BaseAvtivity;
import com.LianXiangKeJi.SupplyChain.base.BaseFragment;
import com.LianXiangKeJi.SupplyChain.base.BasePresenter;
import com.LianXiangKeJi.SupplyChain.order.adapter.Near_HotSellAdapter;
import com.LianXiangKeJi.SupplyChain.order.fragment.FragmentAll;
import com.LianXiangKeJi.SupplyChain.order.fragment.FragmentFinish;
import com.LianXiangKeJi.SupplyChain.order.fragment.FragmentPayment;
import com.LianXiangKeJi.SupplyChain.order.fragment.FragmentReceipt;
import com.LianXiangKeJi.SupplyChain.order.fragment.FragmentShip;
import com.google.android.material.tabs.TabLayout;
import com.liaoinstan.springview.container.DefaultFooter;
import com.liaoinstan.springview.container.DefaultHeader;
import com.liaoinstan.springview.widget.SpringView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.LianXiangKeJi.SupplyChain.base.App.getContext;

/**
 * @ClassName:OrderActivity
 * @Author:hmy
 * @Description:java类作用描述
 */
public class OrderActivity extends BaseAvtivity implements View.OnClickListener {
    @BindView(R.id.back)
    ImageView back;
    @BindView(R.id.et_search)
    EditText etSearch;
    @BindView(R.id.tv_search)
    TextView tvSearch;
    @BindView(R.id.tab)
    TabLayout tab;
    @BindView(R.id.vp)
    ViewPager vp;
    @BindView(R.id.rc_search_order)
    RecyclerView rcSearchOrder;
    @BindView(R.id.rc_hotSell)
    RecyclerView rcHotSell;
    @BindView(R.id.sc_search)
    NestedScrollView scSearch;
    @BindView(R.id.rl_noorder)
    RelativeLayout rlNoorder;
    @BindView(R.id.sv)
    SpringView sv;
    private List<String> list = new ArrayList<>();
    private List<BaseFragment> fragments = new ArrayList<>();
    private List<String> textlist = new ArrayList<>();

    @Override
    protected int getResId() {
        return R.layout.activity_order;
    }

    @Override
    protected void getData() {
        setTitleColor(OrderActivity.this);
        back.setOnClickListener(this);
        tvSearch.setOnClickListener(this);

        for (int i = 0; i < 10; i++) {
            textlist.add("第" + i);
        }
        GridLayoutManager gridLayoutManager = new GridLayoutManager(OrderActivity.this, 2);
        rcHotSell.setLayoutManager(gridLayoutManager);

        Near_HotSellAdapter adapter = new Near_HotSellAdapter(OrderActivity.this, textlist);
        rcHotSell.setAdapter(adapter);

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
        FragmentAll fragmentAll = new FragmentAll();
        //待付款订单
        FragmentPayment fragmentPayment = new FragmentPayment();
        //待发货订单
        FragmentShip fragmentShip = new FragmentShip();
        //待收货订单
        FragmentReceipt fragmentReceipt = new FragmentReceipt();
        //已完成订单
        FragmentFinish fragmentFinish = new FragmentFinish();

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
        // TODO: 2020/7/18 通过标记判断需要展示的页面
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

        sv.setHeader(new DefaultHeader(getContext()));
        sv.setFooter(new DefaultFooter(getContext()));


        // TODO: 2020/7/27 刷新搜索订单
        sv.setListener(new SpringView.OnFreshListener() {
            @Override
            public void onRefresh() {

                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        sv.onFinishFreshAndLoad();
                    }
                }, 1000);
                adapter.notifyDataSetChanged();
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

    // TODO: 2020/7/21 拿到订单数量 为空展示无订单
    @Override
    protected BasePresenter initPresenter() {
        return null;
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.back:
                int visibility = scSearch.getVisibility();
                if (visibility == 0) {
                    sv.setVisibility(View.GONE);
                    tab.setVisibility(View.VISIBLE);
                    vp.setVisibility(View.VISIBLE);
                }
                if (visibility != 0) {
                    finish();
                }
                break;
            case R.id.tv_search:
                String search = etSearch.getText().toString();
                if (!TextUtils.isEmpty(search)) {
                    sv.setVisibility(View.VISIBLE);
                    tab.setVisibility(View.GONE);
                    vp.setVisibility(View.GONE);
                    rcSearchOrder.setVisibility(View.GONE);
                    rlNoorder.setVisibility(View.VISIBLE);
                    //关闭软键盘
                    closekeyboard();
                    etSearch.setText("");
                } else {
                    Toast.makeText(this, "请输入搜索内容", Toast.LENGTH_SHORT).show();
                }
                break;
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.bind(this);
    }

    // TODO: 2020/7/18 创建viewpager适配器
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
