package com.LianXiangKeJi.SupplyChain.main.adapter;

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
import com.LianXiangKeJi.SupplyChain.alwaysbuy.adapter.AlwaysBuyAdapterOne;
import com.LianXiangKeJi.SupplyChain.base.Common;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * @ClassName:Home_HotSellAdapter
 * @Author:hmy
 * @Description:java类作用描述
 */
public class Home_HotSellAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private final Context context;
    private final List<Integer> list;
    private Integer integer;

    public Home_HotSellAdapter(Context context, List<Integer> list) {

        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = View.inflate(context, R.layout.item_hotsell, null);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {

        String token = Common.getToken();
        if(TextUtils.isEmpty(token)){
            ((ViewHolder)holder).tvGoodsPrice.setText("￥？");
        }
        ((ViewHolder)holder).jia.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                integer = list.get(position);
                integer++;
                list.set(position,integer);
                if(TextUtils.isEmpty(token)){
                    Toast.makeText(context, "请先登录", Toast.LENGTH_SHORT).show();
                }else{

                    ((ViewHolder)holder).tvGoodsCount.setText(integer +"");
                    ((ViewHolder)holder).tvGoodsCount.setVisibility(View.VISIBLE);
                    ((ViewHolder)holder).jian.setVisibility(View.VISIBLE);
                    // TODO: 2020/7/21 拿到商品信息 以count为数量加入购物车


                    Toast.makeText(context, "添加进货单成功", Toast.LENGTH_SHORT).show();
                }

            }
        });
        ((ViewHolder)holder).jian.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                integer = list.get(position);
                integer--;
                list.set(position,integer);
                ((ViewHolder)holder).tvGoodsCount.setText(integer+"");
                if(integer==0){
                    ((ViewHolder)holder).tvGoodsCount.setVisibility(View.GONE);
                    ((ViewHolder)holder).jian.setVisibility(View.GONE);
                }
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
        @BindView(R.id.jia)
        ImageView jia;
        @BindView(R.id.tv_goods_count)
        TextView tvGoodsCount;
        @BindView(R.id.jian)
        ImageView jian;
        @BindView(R.id.rl_hotsellgoods)
        RelativeLayout rlHotsellgoods;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this,itemView);
        }
    }
}
