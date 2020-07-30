package com.LianXiangKeJi.SupplyChain.main.fragment;

import android.content.Intent;
import android.os.Handler;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.LianXiangKeJi.SupplyChain.R;
import com.LianXiangKeJi.SupplyChain.base.BaseFragment;
import com.LianXiangKeJi.SupplyChain.base.BasePresenter;
import com.LianXiangKeJi.SupplyChain.main.adapter.Home_HotSellAdapter;
import com.LianXiangKeJi.SupplyChain.main.adapter.Home_labelAdapter;
import com.LianXiangKeJi.SupplyChain.recommend.acitivty.RecommendActivity;
import com.LianXiangKeJi.SupplyChain.search.activity.SearchActivity;
import com.bumptech.glide.Glide;
import com.liaoinstan.springview.container.DefaultFooter;
import com.liaoinstan.springview.container.DefaultHeader;
import com.liaoinstan.springview.widget.SpringView;
import com.stx.xhb.xbanner.XBanner;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

/**
 * @ClassName:FragmentHome
 * @Author:hmy
 * @Description:java类作用描述
 */
public class FragmentHome extends BaseFragment implements View.OnClickListener {

    @BindView(R.id.et_search)
    EditText etSearch;
    @BindView(R.id.xbn)
    XBanner xbn;
    @BindView(R.id.rc_fenLei)
    RecyclerView rcFenLei;
    @BindView(R.id.ll_more)
    LinearLayout llMore;
    @BindView(R.id.rc_recommend_goods)
    RecyclerView rcRecommendGoods;
    List<Integer> images = new ArrayList<>();
    @BindView(R.id.sv)
    SpringView sv;
    private List<Integer> textlist = new ArrayList<>();
    private Home_HotSellAdapter home_hotSellAdapter;

    @Override
    protected void getid(View view) {

    }

    @Override
    protected int getResId() {
        return R.layout.fragment_home;
    }

    @Override
    protected BasePresenter initPresenter() {
        return null;
    }

    @Override
    protected void getData() {
        sv.setHeader(new DefaultHeader(getContext()));
        sv.setFooter(new DefaultFooter(getContext()));

        etSearch.setOnClickListener(this);
        llMore.setOnClickListener(this);
        images.add(R.mipmap.lunbo1);
        images.add(R.mipmap.lunbo2);
        images.add(R.mipmap.lunbo3);
        images.add(R.mipmap.lunbo4);
        //轮播图设置数据
        xbn.setData(images, null);
        xbn.setmAdapter(new XBanner.XBannerAdapter() {
            @Override
            public void loadBanner(XBanner banner, Object model, View view, int position) {
                Glide.with(getContext()).load(images.get(position)).into((ImageView) view);
            }
        });
        xbn.setPageChangeDuration(1000);
        //轮播图的点击事件
        xbn.setOnItemClickListener(new XBanner.OnItemClickListener() {
            @Override
            public void onItemClick(XBanner banner, Object model, View view, int position) {
                Toast.makeText(getContext(), "点击了第" + (position + 1) + "张图片", Toast.LENGTH_SHORT).show();
            }
        });
        // TODO: 2020/7/21 测试分类标签栏

       for (int i=0;i<10;i++){
           textlist.add(0);
       }

        GridLayoutManager manager = new GridLayoutManager(getContext(), 4);
        rcFenLei.setLayoutManager(manager);
        Home_labelAdapter home_labelAdapter = new Home_labelAdapter(getContext(), textlist);
        rcFenLei.setAdapter(home_labelAdapter);


        // TODO: 2020/7/21 测试热销展示的四条商品
        GridLayoutManager gridLayoutManager = new GridLayoutManager(getContext(), 3);
        rcRecommendGoods.setLayoutManager(gridLayoutManager);
        home_hotSellAdapter = new Home_HotSellAdapter(getContext(), textlist);
        rcRecommendGoods.setAdapter(home_hotSellAdapter);

        sv.setListener(new SpringView.OnFreshListener() {
            @Override
            public void onRefresh() {

                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        sv.onFinishFreshAndLoad();
                    }
                }, 1000);
                // TODO: 2020/7/23 刷新适配器
                home_hotSellAdapter.notifyDataSetChanged();
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
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.et_search:
                Intent intent = new Intent(getContext(), SearchActivity.class);
                startActivity(intent);
                break;
            case R.id.ll_more:
                Intent intent1 = new Intent(getContext(), RecommendActivity.class);
                startActivity(intent1);
                break;
        }
    }
}
