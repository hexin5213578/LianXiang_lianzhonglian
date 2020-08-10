package com.LianXiangKeJi.SupplyChain.main.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
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
import com.LianXiangKeJi.SupplyChain.main.bean.DeleteShopCarBean;
import com.LianXiangKeJi.SupplyChain.main.bean.SaveShopCarBean;
import com.LianXiangKeJi.SupplyChain.main.bean.ShopCarBean;
import com.LianXiangKeJi.SupplyChain.recommend.bean.HotSellBean;
import com.LianXiangKeJi.SupplyChain.recommend.bean.HotSellNoLoginBean;
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
 * @ClassName:HotsellAdapter
 * @Author:hmy
 * @Description:java类作用描述
 */
public class HotsellNoLoginAdapter_home extends RecyclerView.Adapter<RecyclerView.ViewHolder> {


    private final Context context;
    private final List<HotSellNoLoginBean.DataBean> list;

    public HotsellNoLoginAdapter_home(Context context, List<HotSellNoLoginBean.DataBean> list) {

        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = View.inflate(context, R.layout.item_hotsell, null);
        return new ViewHolder(view);
    }

    @SuppressLint("NewApi")
    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
            ((ViewHolder)holder).tvGoodsPrice.setText("￥？");

        Glide.with(context).load(list.get(position).getUrl()).into(((ViewHolder)holder).ivGoodsImage);
        ((ViewHolder)holder).tvGoodsName.setText(list.get(position).getName());
        
        ((ViewHolder)holder).jia.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(context, "请先登录", Toast.LENGTH_SHORT).show();
            }
        });
        //条目点击去商品详情
        ((ViewHolder)holder).rlItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                GoodsDeatailsBean goodsDeatailsBean = new GoodsDeatailsBean();
                goodsDeatailsBean.setPrice("?");
                goodsDeatailsBean.setImage(list.get(position).getUrl());
                goodsDeatailsBean.setName(list.get(position).getName()+"");
                goodsDeatailsBean.setMonthsell(list.get(position).getMonthSell()+"");
                goodsDeatailsBean.setId(list.get(position).getId()+"");
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
        @BindView(R.id.tv_goods_price)
        TextView tvGoodsPrice;
        @BindView(R.id.jia)
        Button jia;
        @BindView(R.id.rl_item)
        RelativeLayout rlItem;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this,itemView);
        }
    }
}
