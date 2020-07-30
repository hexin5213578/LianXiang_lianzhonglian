package com.LianXiangKeJi.SupplyChain.order.fragment;

import android.os.Handler;
import android.view.View;
import android.widget.RelativeLayout;

import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.LianXiangKeJi.SupplyChain.R;
import com.LianXiangKeJi.SupplyChain.base.BaseFragment;
import com.LianXiangKeJi.SupplyChain.base.BasePresenter;
import com.LianXiangKeJi.SupplyChain.order.adapter.Near_HotSellAdapter;
import com.liaoinstan.springview.container.DefaultFooter;
import com.liaoinstan.springview.container.DefaultHeader;
import com.liaoinstan.springview.widget.SpringView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

/**
 * @ClassName:FragmentFinish
 * @Author:hmy
 * @Description:java类作用描述 已完成订单
 */
public class FragmentFinish extends BaseFragment {
    @BindView(R.id.rc_order)
    RecyclerView rcOrder;
    @BindView(R.id.rc_hotSell)
    RecyclerView rcHotSell;
    @BindView(R.id.sv)
    SpringView sv;
    @BindView(R.id.rl_noorder)
    RelativeLayout rlNoorder;
    private List<String> list;
    private List<String> textlist = new ArrayList<>();

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

        for (int i = 0; i <= 10; i++) {
            textlist.add("第" + i);
        }

        // TODO: 2020/7/21 测试热销展示的四条商品
        GridLayoutManager gridLayoutManager = new GridLayoutManager(getContext(), 2);
        rcHotSell.setLayoutManager(gridLayoutManager);

        Near_HotSellAdapter adapter = new Near_HotSellAdapter(getContext(), textlist);
        rcHotSell.setAdapter(adapter);
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
        // TODO: 2020/7/23 判断传过来的数据是否为空 为空展示无订单 不为空展示列表
        if( list!=null &&list.size()>0){
            rlNoorder.setVisibility(View.GONE);
            rcOrder.setVisibility(View.VISIBLE);
            // TODO: 2020/7/23 传入列表数据


        }else{
            rlNoorder.setVisibility(View.VISIBLE);
            rcOrder.setVisibility(View.GONE);
        }
    }

    public void setData(List<String> data) {

        list = new ArrayList<>();
        list.addAll(data);
        if (getUserVisibleHint()) {
            getData();
        }
    }
}
