package com.LianXiangKeJi.SupplyChain.order.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.LianXiangKeJi.SupplyChain.R;
import com.LianXiangKeJi.SupplyChain.common.bean.OrderBean;
import com.bumptech.glide.Glide;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class OrderInfoAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private final Context context;
    private final List<OrderBean> list;


    public OrderInfoAdapter(Context context, List<OrderBean> list) {

        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = View.inflate(context, R.layout.item_order_info, null);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        OrderBean orderBean = list.get(position);
        Glide.with(context).load(orderBean.getImageurl()).into(((ViewHolder)holder).ivImage);
        ((ViewHolder)holder).tvName.setText(orderBean.getName());
        ((ViewHolder)holder).tvCount.setText("x"+orderBean.getCount()+"");
        ((ViewHolder)holder).tvSpec.setText(orderBean.getSpecs());
        ((ViewHolder)holder).tvPrice.setText("￥"+orderBean.getPrice());
        int count = orderBean.getCount();
        String price = orderBean.getPrice();
        Float aFloat = Float.valueOf(price);

        ((ViewHolder)holder).tvAllprice.setText("小计：￥"+count*aFloat);
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.iv_image)
        ImageView ivImage;
        @BindView(R.id.tv_name)
        TextView tvName;
        @BindView(R.id.tv_spec)
        TextView tvSpec;
        @BindView(R.id.tv_price)
        TextView tvPrice;
        @BindView(R.id.tv_count)
        TextView tvCount;
        @BindView(R.id.tv_allprice)
        TextView tvAllprice;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this,itemView);
        }
    }
}
