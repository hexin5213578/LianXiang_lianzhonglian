package com.LianXiangKeJi.SupplyChain.search.adapter;

import android.animation.ValueAnimator;
import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Intent;
import android.graphics.drawable.BitmapDrawable;
import android.os.Build;
import android.os.Handler;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.recyclerview.widget.RecyclerView;

import com.LianXiangKeJi.SupplyChain.R;
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
import com.LianXiangKeJi.SupplyChain.utils.StringUtil;
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
    private final Activity context;
    private final List<SearchGoodsBean.DataBean> list;

    private PopupWindow mPopupWindow1;

    public SearchGoodsAdapter(Activity context, List<SearchGoodsBean.DataBean> list) {

        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = View.inflate(context, R.layout.item_searchgoods, null);
        return new ViewHolder(view);
    }

    @SuppressLint("NewApi")
    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        Glide.with(context).load(list.get(position).getLittlePrintUrl()).into(((ViewHolder) holder).ivGoodsImage);
        ((ViewHolder) holder).tvGoodsName.setText(list.get(position).getName());

        ((ViewHolder) holder).tvGoodsName.setText(list.get(position).getName());
        ((ViewHolder) holder).tvGoodsPrice.setText("￥" + StringUtil.round(list.get(position).getPrice()));

        Integer count = Integer.valueOf(list.get(position).getAllSell());
        ((ViewHolder) holder).tvGoodsYichengjiao.setText("成交" + count + "笔");

        LinkedHashMap<String, String> map = SPUtil.getMap(context, "goodsid");

        for (String key : map.keySet()) {
            if (list.get(position).getId().equals(key)) {
                String s = map.get(key);
                ((ViewHolder)holder).jian.setVisibility(View.VISIBLE);
                ((ViewHolder)holder).count.setVisibility(View.VISIBLE);
                ((ViewHolder)holder).count.setText(s);
                if(Integer.valueOf(s)==0){
                    ((ViewHolder)holder).jian.setVisibility(View.INVISIBLE);
                    ((ViewHolder)holder).count.setVisibility(View.INVISIBLE);
                }
            }
        }
        ((ViewHolder) holder).jia.setOnClickListener(new View.OnClickListener() {
            @SuppressLint({"ResourceAsColor", "NewApi"})
            @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
            @Override
            public void onClick(View view) {
                String a = "0";
                LinkedHashMap<String, String> map = SPUtil.getMap(context, "goodsid");
                for (String key:map.keySet()
                ) {
                    if(key.equals(list.get(position).getId())){
                        a = map.get(key);
                    }
                }
                Integer integer = Integer.valueOf(a);
                integer++;
                ((ViewHolder)holder).count.setText(integer+"");

                map.put(list.get(position).getId(), String.valueOf(integer));

                ((ViewHolder)holder).jian.setVisibility(View.VISIBLE);
                ((ViewHolder)holder).count.setVisibility(View.VISIBLE);

                String id = list.get(position).getId();

                map.put(id, String.valueOf(integer));

                //拿到商品信息 以count为数量加入购物车
                if (Integer.valueOf(a)==0){
                    List<SaveShopCarBean.ResultBean> shoplist = new ArrayList<>();
                    //遍历集合的键
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
                                    mPopupWindow1 = new PopupWindow();
                                    mPopupWindow1.setHeight(ViewGroup.LayoutParams.WRAP_CONTENT);
                                    mPopupWindow1.setWidth(ViewGroup.LayoutParams.MATCH_PARENT);
                                    View view1 = LayoutInflater.from(context).inflate(R.layout.dialog_addshopcar_success, null);
                                    //popwindow设置属性
                                    mPopupWindow1.setContentView(view1);
                                    mPopupWindow1.setBackgroundDrawable(new BitmapDrawable());
                                    mPopupWindow1.setFocusable(true);
                                    mPopupWindow1.setOutsideTouchable(true);
                                    mPopupWindow1.setOnDismissListener(new PopupWindow.OnDismissListener() {
                                        @Override
                                        public void onDismiss() {
                                            setWindowAlpa(false);
                                        }
                                    });


                                    show1(view1);
                                    Handler handler = new Handler();
                                    handler.postDelayed(new Runnable() {
                                        @Override
                                        public void run() {
                                            //要执行的操作
                                            dismiss1();
                                        }
                                    }, 2000);//2秒后执行弹出框消失


                                }

                                @Override
                                public void onError(Throwable e) {

                                }

                                @Override
                                public void onComplete() {

                                }
                            });
                }
                SPUtil.setMap(context, "goodsid", map);
            }
        });
        ((ViewHolder)holder).jian.setOnClickListener(new View.OnClickListener() {

            private String s;

            @Override
            public void onClick(View view) {
                List<SaveShopCarBean.ResultBean> shoplist = new ArrayList<>();
                LinkedHashMap<String, String> map = SPUtil.getMap(context, "goodsid");
                for (String key:map.keySet()
                ) {
                    if(key.equals(list.get(position).getId())){
                        s = map.get(key);
                    }
                }
                Integer integer = Integer.valueOf(s);
                integer--;
                ((ViewHolder)holder).count.setText(integer+"");


                map.put(list.get(position).getId(), String.valueOf(integer));

                if(integer==0){
                    ((ViewHolder)holder).jian.setVisibility(View.INVISIBLE);
                    ((ViewHolder)holder).count.setVisibility(View.INVISIBLE);

                    map.remove(list.get(position).getId());

                    //遍历集合的键
                    if(map.size()>0){
                        SaveShopCarBean saveShopCarBean = new SaveShopCarBean();
                        saveShopCarBean.setState(false);

                        for (Map.Entry<String, String> entry : map.entrySet()) {
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
                }
                SPUtil.setMap(context, "goodsid", map);
            }
        });
        //条目点击去商品详情
        ((ViewHolder) holder).rlItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                GoodsDeatailsBean goodsDeatailsBean = new GoodsDeatailsBean();
                goodsDeatailsBean.setImage(list.get(position).getLittlePrintUrl());
                goodsDeatailsBean.setName(list.get(position).getName());
                goodsDeatailsBean.setPrice(list.get(position).getPrice());
                goodsDeatailsBean.setStock(list.get(position).getStock());
                goodsDeatailsBean.setMonthsell(list.get(position).getMonthSell() + "");
                goodsDeatailsBean.setSpec(list.get(position).getSpecs());
                goodsDeatailsBean.setId(list.get(position).getId());

                Intent intent = new Intent(context, GoodsDetailsActivity.class);
                intent.putExtra("goods", goodsDeatailsBean);
                context.startActivity(intent);
                //Toast.makeText(context, "当前点击为第"+position+"个条目", Toast.LENGTH_SHORT).show();
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
        @BindView(R.id.jian)
        ImageView jian;
        @BindView(R.id.count)
        TextView count;
        @BindView(R.id.jia)
        ImageView jia;
        @BindView(R.id.rl_item)
        RelativeLayout rlItem;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

    private void show1(View v) {
        if (mPopupWindow1 != null && !mPopupWindow1.isShowing()) {
            mPopupWindow1.showAtLocation(v, Gravity.CENTER_HORIZONTAL, 0, 0);
        }
        setWindowAlpa(true);
    }

    public void dismiss1() {
        if (mPopupWindow1 != null && mPopupWindow1.isShowing()) {
            mPopupWindow1.dismiss();
        }
    }

    //设置透明度
    public void setWindowAlpa(boolean isopen) {
        if (Build.VERSION.SDK_INT < 11) {
            return;
        }
        final Window window = context.getWindow();
        final WindowManager.LayoutParams lp = window.getAttributes();
        window.setFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND, WindowManager.LayoutParams.FLAG_DIM_BEHIND);
        ValueAnimator animator;
        if (isopen) {
            animator = ValueAnimator.ofFloat(1.0f, 0.5f);
        } else {
            animator = ValueAnimator.ofFloat(0.5f, 1.0f);
        }
        animator.setDuration(400);
        animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {

            @TargetApi(Build.VERSION_CODES.HONEYCOMB)
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                float alpha = (float) animation.getAnimatedValue();
                lp.alpha = alpha;
                window.setAttributes(lp);
            }
        });
        animator.start();
    }
}
