package com.LianXiangKeJi.SupplyChain.movable.adapter;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.LianXiangKeJi.SupplyChain.R;
import com.LianXiangKeJi.SupplyChain.movable.bean.CouponBean;
import com.LianXiangKeJi.SupplyChain.movable.bean.SaveCouponIdBean;

import org.greenrobot.eventbus.EventBus;

import java.text.SimpleDateFormat;
import java.util.Date;
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
        ((ViewHolder) holder).tvCouponMan.setText("全场满" + dataBean.getFull() + "元可用");
        ((ViewHolder) holder).tvCouponJian.setText(dataBean.getMinus() + "");

        long overTime = dataBean.getOverTime();
        long beginTime = list.get(position).getBeginTime();

        String date = new SimpleDateFormat("yyyy.MM.dd HH:mm").format(
                new Date(overTime));
        ((ViewHolder) holder).tvCouponTime.setText("有效期至 " + date);

        String date1 = new SimpleDateFormat("yyyy.MM.dd HH:mm").format(
                new Date(beginTime));
        //获取系统时间
        long time = System.currentTimeMillis();
        //结束时间跟当前时间对比 小于24小时提示即将到期
        if (overTime - time < 86400000 && overTime-time>0) {
            ((ViewHolder) holder).Expiring.setVisibility(View.VISIBLE);
        }

        ((ViewHolder)holder).rlSelect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int couponState = list.get(position).getCouponState();
                SaveCouponIdBean saveCouponIdBean = new SaveCouponIdBean();

                saveCouponIdBean.setState(couponState);

                if(couponState==0){
                    Toast.makeText(context, "开始使用时间为"+date1, Toast.LENGTH_SHORT).show();
                }else if(couponState==1){
                    saveCouponIdBean.setClose("关闭界面");
                    saveCouponIdBean.setCouponId(list.get(position).getId());
                    saveCouponIdBean.setFull(list.get(position).getFull()+"");
                    saveCouponIdBean.setJian(list.get(position).getMinus()+"");
                    EventBus.getDefault().post(saveCouponIdBean);
                }else if(couponState==2){
                    Toast.makeText(context, "此优惠券已被占用", Toast.LENGTH_SHORT).show();
                }else if(couponState==3){
                    Toast.makeText(context, "此优惠券已过期", Toast.LENGTH_SHORT).show();

                }
            }
        });

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
        @BindView(R.id.Expiring)
        ImageView Expiring;
        @BindView(R.id.rl_select)
        RelativeLayout rlSelect;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
