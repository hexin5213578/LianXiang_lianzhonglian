package com.LianXiangKeJi.SupplyChain.recommend.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.LianXiangKeJi.SupplyChain.R;
import com.LianXiangKeJi.SupplyChain.goodsdetails.activity.GoodsDetailsActivity;
import com.LianXiangKeJi.SupplyChain.goodsdetails.bean.GoodsDeatailsBean;
import com.LianXiangKeJi.SupplyChain.recommend.bean.HotSellNoLoginBean;
import com.bumptech.glide.Glide;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * @ClassName:HotsellAdapter
 * @Author:hmy
 * @Description:java类作用描述
 */
public class HotsellNoLoginAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private final Context context;
    private final List<HotSellNoLoginBean.DataBean> list;


    public HotsellNoLoginAdapter(Context context, List<HotSellNoLoginBean.DataBean> list) {

        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = View.inflate(context, R.layout.item_hotsell_details, null);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        ((ViewHolder) holder).tvGoodsPrice.setText("￥？");
        Glide.with(context).load(list.get(position).getUrl()).into(((ViewHolder) holder).ivGoodsImage);
        ((ViewHolder) holder).tvGoodsName.setText(list.get(position).getName());
        // TODO: 2020/7/21 条目点击去商品详情
        ((ViewHolder) holder).rlItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                GoodsDeatailsBean goodsDeatailsBean = new GoodsDeatailsBean();
                goodsDeatailsBean.setPrice("？");
                goodsDeatailsBean.setImage(list.get(position).getUrl());
                goodsDeatailsBean.setName(list.get(position).getName() + "");
                goodsDeatailsBean.setMonthsell(list.get(position).getMonthSell() + "");
                goodsDeatailsBean.setId(list.get(position).getId() + "");

                Intent intent = new Intent(context, GoodsDetailsActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                intent.putExtra("goods", goodsDeatailsBean);
                context.startActivity(intent);
            }
        });
        ((ViewHolder) holder).jia.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(context, "请先登录", Toast.LENGTH_SHORT).show();
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
}
