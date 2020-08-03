package com.LianXiangKeJi.SupplyChain.movable.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.LianXiangKeJi.SupplyChain.R;
import com.LianXiangKeJi.SupplyChain.movable.bean.CouponBean;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class CouPonAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private final Context context;
    private final List<CouponBean.DataBean> list;


    public CouPonAdapter(Context context, List<CouponBean.DataBean> list) {

        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = View.inflate(context, R.layout.item_coupon, null);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        CouponBean.DataBean dataBean = list.get(position);
        ((ViewHolder)holder).tvCouponMan.setText("全场满"+dataBean.getFull()+"元可用");
        ((ViewHolder)holder).tvCouponJian.setText(dataBean.getMinus()+"");

        long beginTime = dataBean.getBeginTime();
        long overTime = dataBean.getOverTime();


    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.tv)
        TextView tv;
        @BindView(R.id.tv_coupon_jian)
        TextView tvCouponJian;
        @BindView(R.id.tv_coupon_man)
        TextView tvCouponMan;
        @BindView(R.id.tv_coupon_time)
        TextView tvCouponTime;
        @BindView(R.id.iv_use)
        ImageView ivUse;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this,itemView);
        }
    }
}
