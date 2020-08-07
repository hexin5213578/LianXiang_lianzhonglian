package com.LianXiangKeJi.SupplyChain.order.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.LianXiangKeJi.SupplyChain.R;
import com.LianXiangKeJi.SupplyChain.order.bean.UserOrderBean;
import com.bumptech.glide.Glide;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class OrderGoodsListAdapterTwo extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private final Context context;
    private final List<UserOrderBean.DataBean.OrdersDetailListBean> list;



    public OrderGoodsListAdapterTwo(Context context, List<UserOrderBean.DataBean.OrdersDetailListBean> list) {

        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = View.inflate(context, R.layout.item_goods_list_two, null);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {

        ((ViewHolder) holder).tvGoodsName.setText(list.get(position).getName());
        ((ViewHolder)holder).tvGoodsSpec.setText(list.get(position).getSpecs());
        ((ViewHolder)holder).tvPrice.setText("￥"+list.get(position).getPrice());
        ((ViewHolder)holder).tvGoodsCount.setText("x"+list.get(position).getNumber());
        Glide.with(context).load(list.get(position).getLittlePrintUrl()).into(((ViewHolder)holder).ivGoodsImage);
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
        @BindView(R.id.tv_goods_spec)
        TextView tvGoodsSpec;
        @BindView(R.id.tv_price)
        TextView tvPrice;
        @BindView(R.id.tv_goods_count)
        TextView tvGoodsCount;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
