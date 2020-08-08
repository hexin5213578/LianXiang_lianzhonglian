package com.LianXiangKeJi.SupplyChain.search.adapter;

import android.content.Context;
import android.content.Intent;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.LianXiangKeJi.SupplyChain.R;
import com.LianXiangKeJi.SupplyChain.alwaysbuy.adapter.AlwaysBuyAdapterOne;
import com.LianXiangKeJi.SupplyChain.base.Common;
import com.LianXiangKeJi.SupplyChain.goodsdetails.activity.GoodsDetailsActivity;
import com.LianXiangKeJi.SupplyChain.goodsdetails.bean.GoodsDeatailsBean;
import com.LianXiangKeJi.SupplyChain.main.adapter.ClassifSearchGoodsAdapter;
import com.LianXiangKeJi.SupplyChain.main.bean.DeleteShopCarBean;
import com.LianXiangKeJi.SupplyChain.main.bean.SaveShopCarBean;
import com.LianXiangKeJi.SupplyChain.main.bean.ShopCarBean;
import com.LianXiangKeJi.SupplyChain.search.bean.SearchGoodsBean;
import com.LianXiangKeJi.SupplyChain.utils.NetUtils;
import com.LianXiangKeJi.SupplyChain.utils.SPUtil;
import com.bumptech.glide.Glide;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import okhttp3.MediaType;
import okhttp3.RequestBody;

/**
 * @ClassName:SearchGoodsAdapter
 * @Author:hmy
 * @Description:java类作用描述
 */
public class SearchGoodsAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private final Context context;
    private final List<SearchGoodsBean.DataBean> list;
    private Integer integer;

    public SearchGoodsAdapter(Context context, List<SearchGoodsBean.DataBean> list) {

        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = View.inflate(context, R.layout.item_searchgoods, null);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        String token = Common.getToken();
        if(TextUtils.isEmpty(token)){
            ((ViewHolder)holder).tvGoodsPrice.setText("￥？");
        }
        Glide.with(context).load(list.get(position).getLittlePrintUrl()).into(((ViewHolder)holder).ivGoodsImage);
        ((ViewHolder)holder).tvGoodsName.setText(list.get(position).getName());

        ((ViewHolder)holder).tvGoodsName.setText(list.get(position).getName());
        ((ViewHolder)holder).tvGoodsPrice.setText("￥"+list.get(position).getPrice());

        Integer count = Integer.valueOf(list.get(position).getAllSell());
        ((ViewHolder)holder).tvGoodsYichengjiao.setText("成交"+count+"笔");

        LinkedHashMap<String, String> map = SPUtil.getMap(context, "goodsid");

        for (String key : map.keySet()) {
            if(list.get(position).getId().equals(key)){
                integer=1;
                list.get(position).setPostion(integer);
                list.get(position).setPostion(integer);
                ((ViewHolder)holder).tvGoodsCount.setText(integer +"");
                ((ViewHolder)holder).tvGoodsCount.setVisibility(View.VISIBLE);
                ((ViewHolder)holder).jian.setVisibility(View.VISIBLE);
            }
        }

        ((ViewHolder)holder).jia.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                    integer = list.get(position).getPostion();
                    if(integer==0){
                        integer++;
                    }
                    if(integer==1){
                        list.get(position).setPostion(integer);
                        ((ViewHolder)holder).tvGoodsCount.setText(integer +"");
                        ((ViewHolder)holder).tvGoodsCount.setVisibility(View.VISIBLE);
                        ((ViewHolder)holder).jian.setVisibility(View.VISIBLE);
                        //拿到商品信息 以count为数量加入购物车
                        String id = list.get(position).getId();
                        String name = list.get(position).getName();
                        List<SaveShopCarBean.ResultBean> shoplist = new ArrayList<>();

                        LinkedHashMap<String, String> map = SPUtil.getMap(context, "goodsid");

                        map.put(id,name);

                        //遍历集合的键
                        if(map.size()>0) {
                            SaveShopCarBean saveShopCarBean = new SaveShopCarBean();
                            saveShopCarBean.setState(false);
                            for (Map.Entry<String, String> entry : map.entrySet()) {
                                String key = entry.getKey();
                                SaveShopCarBean.ResultBean resultBean = new SaveShopCarBean.ResultBean();
                                resultBean.setShopGoodsId(key);
                                shoplist.add(resultBean);
                            }
                            saveShopCarBean.setShoppingCartList(shoplist);

                            Gson gson = new Gson();
                            String json = gson.toJson(saveShopCarBean);

                            RequestBody requestBody = RequestBody.create(MediaType.parse("application/json; charset=utf-8"), json);
                            NetUtils.getInstance().getApis().doShopCar(requestBody)
                                    .subscribeOn(Schedulers.io())
                                    .observeOn(AndroidSchedulers.mainThread())
                                    .subscribe(new Observer<ShopCarBean>() {
                                        @Override
                                        public void onSubscribe(Disposable d) {

                                        }

                                        @Override
                                        public void onNext(ShopCarBean shopCarBean) {
                                            Toast.makeText(context, "添加进货单成功", Toast.LENGTH_SHORT).show();

                                        }

                                        @Override
                                        public void onError(Throwable e) {

                                        }

                                        @Override
                                        public void onComplete() {

                                        }
                                    });
                        }

                        SPUtil.setMap(context,"goodsid",map);

                    }
            }
        });
        ((ViewHolder)holder).jian.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                List<SaveShopCarBean.ResultBean> shoplist = new ArrayList<>();

                integer = list.get(position).getPostion();
                integer--;
                list.get(position).setPostion(integer);
                ((ViewHolder)holder).tvGoodsCount.setText(integer +"");

                String id = list.get(position).getId();
                LinkedHashMap<String, String> goodsid = SPUtil.getMap(context, "goodsid");

                goodsid.remove(id);
                //遍历集合的键
                if(goodsid.size()>0){
                    SaveShopCarBean saveShopCarBean = new SaveShopCarBean();
                    saveShopCarBean.setState(false);

                    for (Map.Entry<String, String> entry : goodsid.entrySet()) {
                        String key = entry.getKey();
                        SaveShopCarBean.ResultBean resultBean = new SaveShopCarBean.ResultBean();
                        resultBean.setShopGoodsId(key);
                        shoplist.add(resultBean);
                    }
                    //添加进集合

                    saveShopCarBean.setShoppingCartList(shoplist);

                    Gson gson = new Gson();
                    String json = gson.toJson(saveShopCarBean);
                    RequestBody requestBody2 = RequestBody.create(MediaType.parse("application/json; charset=utf-8"), json);
                    NetUtils.getInstance().getApis().doShopCar(requestBody2)
                            .subscribeOn(Schedulers.io())
                            .observeOn(AndroidSchedulers.mainThread())
                            .subscribe(new Observer<ShopCarBean>() {
                                @Override
                                public void onSubscribe(Disposable d) {

                                }

                                @Override
                                public void onNext(ShopCarBean shopCarBean) {

                                }

                                @Override
                                public void onError(Throwable e) {

                                }

                                @Override
                                public void onComplete() {

                                }
                            });

                }else{
                    SaveShopCarBean saveShopCarBean = new SaveShopCarBean();
                    saveShopCarBean.setState(false);
                    Gson gson = new Gson();
                    String json = gson.toJson(saveShopCarBean);
                    RequestBody requestBody = RequestBody.create(MediaType.parse("application/json; charset=utf-8"), json);

                    NetUtils.getInstance().getApis().doDeleteShopCar(requestBody)
                            .subscribeOn(Schedulers.io())
                            .observeOn(AndroidSchedulers.mainThread())
                            .subscribe(new Observer<DeleteShopCarBean>() {
                                @Override
                                public void onSubscribe(Disposable d) {

                                }

                                @Override
                                public void onNext(DeleteShopCarBean bean) {

                                }

                                @Override
                                public void onError(Throwable e) {

                                }

                                @Override
                                public void onComplete() {

                                }
                            });
                }
                SPUtil.setMap(context,"goodsid",goodsid);

                if(integer ==0){
                    ((ViewHolder)holder).tvGoodsCount.setVisibility(View.GONE);
                    ((ViewHolder)holder).jian.setVisibility(View.GONE);
                }
            }
        });
        //条目点击去商品详情
        ((ViewHolder)holder).rlItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                GoodsDeatailsBean goodsDeatailsBean = new GoodsDeatailsBean();
                goodsDeatailsBean.setImage(list.get(position).getLittlePrintUrl());
                goodsDeatailsBean.setName(list.get(position).getName());
                goodsDeatailsBean.setPrice(list.get(position).getPrice());
                goodsDeatailsBean.setStock(list.get(position).getStock());
                goodsDeatailsBean.setMonthsell(list.get(position).getMonthSell()+"");
                goodsDeatailsBean.setSpec(list.get(position).getSpecs());
                goodsDeatailsBean.setId(list.get(position).getId());

                Intent intent = new Intent(context, GoodsDetailsActivity.class);
                intent.putExtra("goods",goodsDeatailsBean);
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.iv_goods_image)
        ImageView ivGoodsImage;
        @BindView(R.id.tv_goods_name)
        TextView tvGoodsName;
        @BindView(R.id.tv_goods_yichengjiao)
        TextView tvGoodsYichengjiao;
        @BindView(R.id.tv_goods_price)
        TextView tvGoodsPrice;
        @BindView(R.id.jia)
        ImageView jia;
        @BindView(R.id.tv_goods_count)
        TextView tvGoodsCount;
        @BindView(R.id.jian)
        ImageView jian;
        @BindView(R.id.rl_item)
        RelativeLayout rlItem;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this,itemView);
        }
    }
}
