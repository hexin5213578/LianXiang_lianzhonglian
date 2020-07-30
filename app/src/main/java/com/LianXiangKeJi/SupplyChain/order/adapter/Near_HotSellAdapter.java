package com.LianXiangKeJi.SupplyChain.order.adapter;

import android.content.Context;
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
import com.LianXiangKeJi.SupplyChain.base.Common;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * @ClassName:Home_HotSellAdapter
 * @Author:hmy
 * @Description:java类作用描述
 */
public class Near_HotSellAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private final Context context;
    private final List<String> list;

    int count = 0;
    public Near_HotSellAdapter(Context context, List<String> list) {

        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = View.inflate(context, R.layout.item_near_hotsell, null);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {

        String token = Common.getToken();
        if(TextUtils.isEmpty(token)){
            ((ViewHolder)holder).tvGoodsPrice.setText("￥？");
        }

        ((ViewHolder)holder).rlHotsellgoods.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // TODO: 2020/7/22 跳转至商品详情页

            }
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.tv_goods_image)
        ImageView tvGoodsImage;
        @BindView(R.id.tv_goods_name)
        TextView tvGoodsName;
        @BindView(R.id.tv_goods_buycount)
        TextView tvGoodsBuycount;
        @BindView(R.id.tv_goods_price)
        TextView tvGoodsPrice;
        @BindView(R.id.rl_hotsellgoods)
        RelativeLayout rlHotsellgoods;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this,itemView);
        }
    }
}
