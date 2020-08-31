package com.LianXiangKeJi.SupplyChain.main.fragment;

import android.animation.ValueAnimator;
import android.annotation.TargetApi;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.BitmapDrawable;
import android.os.Build;
import android.os.Handler;
import android.text.TextUtils;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;

import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.LianXiangKeJi.SupplyChain.R;
import com.LianXiangKeJi.SupplyChain.base.BaseFragment;
import com.LianXiangKeJi.SupplyChain.base.BasePresenter;
import com.LianXiangKeJi.SupplyChain.base.Common;
import com.LianXiangKeJi.SupplyChain.main.adapter.Home_labelAdapter;
import com.LianXiangKeJi.SupplyChain.main.adapter.HotsellAdapter_home;
import com.LianXiangKeJi.SupplyChain.main.adapter.HotsellNoLoginAdapter_home;
import com.LianXiangKeJi.SupplyChain.main.bean.HomeClassIfBean;
import com.LianXiangKeJi.SupplyChain.main.bean.HomeLunboBean;
import com.LianXiangKeJi.SupplyChain.movable.activity.MovableActivity;
import com.LianXiangKeJi.SupplyChain.recommend.acitivty.RecommendActivity;
import com.LianXiangKeJi.SupplyChain.recommend.adapter.HotsellAdapter;
import com.LianXiangKeJi.SupplyChain.recommend.adapter.HotsellNoLoginAdapter;
import com.LianXiangKeJi.SupplyChain.recommend.bean.HotSellBean;
import com.LianXiangKeJi.SupplyChain.recommend.bean.HotSellNoLoginBean;
import com.LianXiangKeJi.SupplyChain.search.activity.SearchActivity;
import com.LianXiangKeJi.SupplyChain.utils.NetUtils;
import com.bumptech.glide.Glide;
import com.liaoinstan.springview.container.DefaultFooter;
import com.liaoinstan.springview.container.DefaultHeader;
import com.liaoinstan.springview.widget.SpringView;
import com.stx.xhb.xbanner.XBanner;
import com.stx.xhb.xbanner.transformers.Transformer;

import java.util.ArrayList;
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
    List<String> images = new ArrayList<>();
    @BindView(R.id.sv)
    SpringView sv;
    @BindView(R.id.activity_coupon)
    ImageView activityCoupon;
    private PopupWindow mPopupWindow1;

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
        String token = Common.getToken();
        sv.setHeader(new DefaultHeader(getContext()));

        etSearch.setOnClickListener(this);
        llMore.setOnClickListener(this);
        activityCoupon.setOnClickListener(this);

        //获取轮播图
        getLunboImage();
        //获取热销商品
        getHotSell(token);
        //获取分类
        getClassif();
        sv.setListener(new SpringView.OnFreshListener() {
            @Override
            public void onRefresh() {
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        sv.onFinishFreshAndLoad();
                        getLunboImage();
                        getHotSell(token);
                        getClassif();
                    }
                }, 1000);
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
        xbn.setOnItemClickListener(new XBanner.OnItemClickListener() {
            @Override
            public void onItemClick(XBanner banner, Object model, View view, int position) {


            }
        });

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.et_search:
                //跳转搜索页
                startActivity(new Intent(getContext(), SearchActivity.class));
                break;
            case R.id.ll_more:
                //跳转查看更多热销商品
                startActivity(new Intent(getContext(), RecommendActivity.class));
                break;
            case R.id.activity_coupon:
                //跳转到
                startActivity(new Intent(getContext(), MovableActivity.class));
                break;
        }
    }
    //获取轮播图
    public void  getLunboImage(){
        //获取轮播图图片
        NetUtils.getInstance().getApis()
                .doGetLunboImage()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<HomeLunboBean>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(HomeLunboBean homeLunboBean) {

                        List<HomeLunboBean.DataBean> data = homeLunboBean.getData();
                        for (int i =0;i<data.size();i++){
                            images.add(data.get(i).getSlideshowUrl());
                        }
                        //轮播图设置数据
                        xbn.setData(images, null);
                        xbn.setmAdapter(new XBanner.XBannerAdapter() {
                            @Override
                            public void loadBanner(XBanner banner, Object model, View view, int position) {
                                Glide.with(getContext()).load(images.get(position)).into((ImageView) view);
                            }
                        });
                        xbn.setPageChangeDuration(1000);

                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onComplete() {

                    }
                });
        images.clear();
    }
    public void getHotSell(String token){
        if(!TextUtils.isEmpty(token)){
            NetUtils.getInstance().getApis().doGetHotSell()
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(new Observer<HotSellBean>() {
                        @Override
                        public void onSubscribe(Disposable d) {

                        }

                        @Override
                        public void onNext(HotSellBean hotSellBean) {
                            List<HotSellBean.DataBean> data = hotSellBean.getData();
                            if(data.size()>0&&data!=null){
                                //热销展示的四条商品
                                GridLayoutManager gridLayoutManager = new GridLayoutManager(getContext(), 3);
                                rcRecommendGoods.setLayoutManager(gridLayoutManager);
                                HotsellAdapter_home home_hotSellAdapter = new HotsellAdapter_home(getActivity(), data);
                                rcRecommendGoods.setAdapter(home_hotSellAdapter);
                            }
                        }

                        @Override
                        public void onError(Throwable e) {

                        }

                        @Override
                        public void onComplete() {

                        }
                    });
        }else{
            NetUtils.getInstance().getApis().doGetHotNoLoginSell()
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(new Observer<HotSellNoLoginBean>() {
                        @Override
                        public void onSubscribe(Disposable d) {

                        }

                        @Override
                        public void onNext(HotSellNoLoginBean hotSellBean) {
                            List<HotSellNoLoginBean.DataBean> data = hotSellBean.getData();
                            if(data.size()>0&&data!=null){
                                //热销展示的四条商品
                                GridLayoutManager gridLayoutManager = new GridLayoutManager(getContext(), 3);
                                rcRecommendGoods.setLayoutManager(gridLayoutManager);
                                HotsellNoLoginAdapter_home hotSellNoLoginBean = new HotsellNoLoginAdapter_home(getContext(), data);
                                rcRecommendGoods.setAdapter(hotSellNoLoginBean);
                            }
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
    public void getClassif(){
        //分类标签栏
        NetUtils.getInstance().getApis().doGetHomeClassIf()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<HomeClassIfBean>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(HomeClassIfBean homeClassIfBean) {
                        List<HomeClassIfBean.DataBean> data = homeClassIfBean.getData();
                        GridLayoutManager manager = new GridLayoutManager(getContext(), 4);
                        rcFenLei.setLayoutManager(manager);
                        Home_labelAdapter home_labelAdapter = new Home_labelAdapter(getContext(), data);
                        rcFenLei.setAdapter(home_labelAdapter);
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
