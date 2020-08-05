package com.LianXiangKeJi.SupplyChain.recommend.acitivty;

import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.LianXiangKeJi.SupplyChain.R;
import com.LianXiangKeJi.SupplyChain.base.BaseAvtivity;
import com.LianXiangKeJi.SupplyChain.base.BasePresenter;
import com.LianXiangKeJi.SupplyChain.recommend.adapter.HotsellAdapter;
import com.liaoinstan.springview.container.DefaultFooter;
import com.liaoinstan.springview.container.DefaultHeader;
import com.liaoinstan.springview.widget.SpringView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class RecommendActivity extends BaseAvtivity {


    @BindView(R.id.back)
    ImageView back;
    @BindView(R.id.title)
    TextView title;
    @BindView(R.id.tv_right)
    TextView tvRight;
    @BindView(R.id.rc_recommend)
    RecyclerView rcRecommend;
    List<Integer> textList = new ArrayList<>();
    @BindView(R.id.sv)
    SpringView sv;

    @Override
    protected int getResId() {
        return R.layout.activity_recommend;
    }

    @Override
    protected void getData() {
        setTitleColor(this);

        sv.setHeader(new DefaultHeader(this));
        sv.setFooter(new DefaultFooter(this));
        title.setText("同城热销");
        tvRight.setVisibility(View.GONE);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        for (int i = 0; i <= 10; i++) {
            textList.add(0);
        }

        // TODO: 2020/7/21 常买商品列表
        LinearLayoutManager manager = new LinearLayoutManager(RecommendActivity.this, RecyclerView.VERTICAL, false);
        rcRecommend.setLayoutManager(manager);
        HotsellAdapter adapter = new HotsellAdapter(RecommendActivity.this, textList);
        rcRecommend.setAdapter(adapter);
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

    @Override
    protected BasePresenter initPresenter() {
        return null;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.bind(this);
    }
}