package com.LianXiangKeJi.SupplyChain.main.adapter;

import android.annotation.SuppressLint;
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
import com.LianXiangKeJi.SupplyChain.base.Common;
import com.LianXiangKeJi.SupplyChain.goodsdetails.activity.GoodsDetailsActivity;
import com.LianXiangKeJi.SupplyChain.goodsdetails.bean.GoodsDeatailsBean;
import com.LianXiangKeJi.SupplyChain.main.bean.ClassIfSearchGoodsBean;
import com.LianXiangKeJi.SupplyChain.recommend.adapter.HotsellAdapter;
import com.LianXiangKeJi.SupplyChain.utils.StringUtil;
import com.bumptech.glide.Glide;

import org.greenrobot.eventbus.EventBus;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ClassifSearchGoodsAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private final Context context;
    private final List<ClassIfSearchGoodsBean.DataBean> list;
    private Integer integer=0;

    public ClassifSearchGoodsAdapter(Context context, List<ClassIfSearchGoodsBean.DataBean> list) {

        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = View.inflate(context, R.layout.item_classif_search_goods, null);

        return new ViewHolder(view);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        String token = Common.getToken();
        if(TextUtils.isEmpty(token)){
            ((ViewHolder)holder).tvGoodsPrice.setText("￥？");


        }
        else{
            ((ViewHolder)holder).tvGoodsName.setText(list.get(position).getName()+"  "+list.get(position).getSpecs());
            ((ViewHolder)holder).tvGoodsPrice.setText("￥"+list.get(position).getPrice());

            double price =Double.parseDouble(list.get(position).getPrice());

            Integer count = Integer.valueOf(list.get(position).getAllSell());

            ((ViewHolder)holder).tvGoodsYichengjiao.setText("成交"+count*price+"元");

            Glide.with(context).load(list.get(position).getLittlePrintUrl()).into(((ViewHolder)holder).ivGoodsImage);

            ((ViewHolder)holder).jia.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                        integer = list.get(position).getPostion();
                        integer++;
                        list.get(position).setPostion(integer);

                        ((ViewHolder)holder).tvGoodsCount.setText(integer +"");
                        ((ViewHolder)holder).tvGoodsCount.setVisibility(View.VISIBLE);
                        ((ViewHolder)holder).jian.setVisibility(View.VISIBLE);
                        // TODO: 2020/7/21 拿到商品信息 以count为数量加入购物车

                        Toast.makeText(context, "添加进货单成功", Toast.LENGTH_SHORT).show();
                }
            });
            ((ViewHolder)holder).jian.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    integer = list.get(position).getPostion();
                    integer--;
                    list.get(position).setPostion(integer);

                    ((ViewHolder)holder).tvGoodsCount.setText(integer +"");
                    if(ClassifSearchGoodsAdapter.this.integer ==0){
                        ((ViewHolder)holder).tvGoodsCount.setVisibility(View.GONE);
                        ((ViewHolder)holder).jian.setVisibility(View.GONE);
                    }
                }
            });
            // TODO: 2020/7/21 条目点击去商品详情
            ((ViewHolder)holder).rlItem.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    GoodsDeatailsBean goodsDeatailsBean = new GoodsDeatailsBean();
                    goodsDeatailsBean.setImage(list.get(position).getLittlePrintUrl());
                    goodsDeatailsBean.setName(list.get(position).getName()+list.get(position).getSpecs());
                    goodsDeatailsBean.setPrice(list.get(position).getPrice());
                    goodsDeatailsBean.setStock(list.get(position).getStock());
                    goodsDeatailsBean.setMonthsell(list.get(position).getMonthSell()+"");
                    goodsDeatailsBean.setSpec(list.get(position).getSpecs());

                    Intent intent = new Intent(context, GoodsDetailsActivity.class);
                    intent.putExtra("goods",goodsDeatailsBean);
                    context.startActivity(intent);
                }
            });
        }

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
        @BindView(R.id.tv_goods_discount)
        TextView tvGoodsDiscount;
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
