package com.LianXiangKeJi.SupplyChain.alwaysbuy.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.LianXiangKeJi.SupplyChain.R;
import com.LianXiangKeJi.SupplyChain.alwaysbuy.adapter.AlwaysBuyAdapterOne;
import com.LianXiangKeJi.SupplyChain.alwaysbuy.adapter.AlwaysBuyAdapterTwo;
import com.LianXiangKeJi.SupplyChain.base.BaseAvtivity;
import com.LianXiangKeJi.SupplyChain.base.BasePresenter;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * @ClassName:AlwaysBuyActivity
 * @Author:hmy
 * @Description:java类作用描述
 */
public class AlwaysBuyActivity extends BaseAvtivity implements View.OnClickListener {
    @BindView(R.id.back)
    ImageView back;
    @BindView(R.id.title)
    TextView title;
    @BindView(R.id.rc_always_buy)
    RecyclerView rcAlwaysBuy;
    @BindView(R.id.tv_manager)
    TextView tvManager;
    @BindView(R.id.tv_finish)
    TextView tvFinish;
    @BindView(R.id.rc_always_buy_manager)
    RecyclerView rcAlwaysBuyManager;
    @BindView(R.id.rb_checkAll)
    CheckBox rbCheckAll;
    @BindView(R.id.bt_delete)
    Button btDelete;
    @BindView(R.id.rl_delete)
    RelativeLayout rlDelete;
    @BindView(R.id.v1)
    View v1;
    List<Integer> textList = new ArrayList<>();

    @Override
    protected int getResId() {
        return R.layout.activity_alwaysbuy;
    }

    @Override
    protected void getData() {
        setTitleColor(AlwaysBuyActivity.this);

        title.setText("常买商品");
        tvManager.setText("管理");
        tvFinish.setText("完成");
        back.setOnClickListener(this);
        tvFinish.setOnClickListener(this);
        tvManager.setOnClickListener(this);

        for (int i=0;i<=5;i++){
            textList.add(0);
        }

        // TODO: 2020/7/21 常买商品列表
        LinearLayoutManager manager = new LinearLayoutManager(AlwaysBuyActivity.this,RecyclerView.VERTICAL,false);
        rcAlwaysBuy.setLayoutManager(manager);
        AlwaysBuyAdapterOne alwaysBuyAdapterOne = new AlwaysBuyAdapterOne(AlwaysBuyActivity.this,textList);
        rcAlwaysBuy.setAdapter(alwaysBuyAdapterOne);

        // TODO: 2020/7/21 管理常买商品列表
        LinearLayoutManager manager1 = new LinearLayoutManager(AlwaysBuyActivity.this,RecyclerView.VERTICAL,false);
        rcAlwaysBuyManager.setLayoutManager(manager1);
        AlwaysBuyAdapterTwo alwaysBuyAdapterTwo = new AlwaysBuyAdapterTwo(AlwaysBuyActivity.this,textList);
        rcAlwaysBuyManager.setAdapter(alwaysBuyAdapterTwo);

    }

    @Override
    protected BasePresenter initPresenter() {
        return null;
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.tv_manager:
                tvManager.setVisibility(View.GONE);
                tvFinish.setVisibility(View.VISIBLE);
                rcAlwaysBuy.setVisibility(View.GONE);
                rcAlwaysBuyManager.setVisibility(View.VISIBLE);
                rlDelete.setVisibility(View.VISIBLE);
                v1.setVisibility(View.VISIBLE);
                break;
            case R.id.tv_finish:
                tvManager.setVisibility(View.VISIBLE);
                tvFinish.setVisibility(View.GONE);
                rcAlwaysBuy.setVisibility(View.VISIBLE);
                rcAlwaysBuyManager.setVisibility(View.GONE);
                rlDelete.setVisibility(View.GONE);
                v1.setVisibility(View.GONE);
                break;
            case R.id.back:
                finish();
                break;
        }
    }

}
