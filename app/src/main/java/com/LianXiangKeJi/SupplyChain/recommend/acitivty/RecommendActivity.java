package com.LianXiangKeJi.SupplyChain.recommend.acitivty;

import android.os.Bundle;
import android.os.Handler;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.LianXiangKeJi.SupplyChain.R;
import com.LianXiangKeJi.SupplyChain.base.BaseAvtivity;
import com.LianXiangKeJi.SupplyChain.base.BasePresenter;
import com.LianXiangKeJi.SupplyChain.base.Common;
import com.LianXiangKeJi.SupplyChain.recommend.adapter.HotsellAdapter;
import com.LianXiangKeJi.SupplyChain.recommend.adapter.HotsellNoLoginAdapter;
import com.LianXiangKeJi.SupplyChain.recommend.bean.HotSellBean;
import com.LianXiangKeJi.SupplyChain.recommend.bean.HotSellNoLoginBean;
import com.LianXiangKeJi.SupplyChain.utils.NetUtils;
import com.liaoinstan.springview.container.DefaultFooter;
import com.liaoinstan.springview.container.DefaultHeader;
import com.liaoinstan.springview.widget.SpringView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

import static com.LianXiangKeJi.SupplyChain.base.App.getContext;

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
        String token = Common.getToken();

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
        getData(token);
        sv.setListener(new SpringView.OnFreshListener() {
            @Override
            public void onRefresh() {
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        sv.onFinishFreshAndLoad();
                        getData(token);
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
    }

    @Override
    protected BasePresenter initPresenter() {
        return null;
    }

    /**
     * 获取热销商品
     * @param token
     */
    public void getData(String token){
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
                                LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext(),RecyclerView.VERTICAL,false);
                                rcRecommend.setLayoutManager(linearLayoutManager);
                                HotsellAdapter home_hotSellAdapter = new HotsellAdapter(getContext(), data);
                                rcRecommend.setAdapter(home_hotSellAdapter);
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
                                LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext(),RecyclerView.VERTICAL,false);
                                rcRecommend.setLayoutManager(linearLayoutManager);
                                HotsellNoLoginAdapter hotSellNoLoginBean = new HotsellNoLoginAdapter(getContext(), data);
                                rcRecommend.setAdapter(hotSellNoLoginBean);
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
}