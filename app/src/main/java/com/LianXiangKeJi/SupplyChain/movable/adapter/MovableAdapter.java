package com.LianXiangKeJi.SupplyChain.movable.adapter;

import android.content.Context;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.LianXiangKeJi.SupplyChain.R;
import com.LianXiangKeJi.SupplyChain.base.Common;
import com.LianXiangKeJi.SupplyChain.movable.bean.GetCouponBean;
import com.LianXiangKeJi.SupplyChain.movable.bean.MovableBean;
import com.LianXiangKeJi.SupplyChain.movable.bean.SaveCouponIdBean;
import com.LianXiangKeJi.SupplyChain.utils.NetUtils;
import com.bumptech.glide.Glide;
import com.google.gson.Gson;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import okhttp3.MediaType;
import okhttp3.RequestBody;

public class MovableAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private final Context context;
    private final List<MovableBean.DataBean> list;

    public MovableAdapter(Context context, List<MovableBean.DataBean> list) {

        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = View.inflate(context, R.layout.item_movable_getcoupon, null);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        MovableBean.DataBean dataBean = list.get(position);
        ((ViewHolder) holder).tvCouponMan.setText("全场满" + dataBean.getFull() + "元可用");
        ((ViewHolder) holder).tvCouponJian.setText(dataBean.getMinus() + "");

        if (dataBean.getBeginTime() != 0 && dataBean.getOverTime() != 0) {
            String date = new SimpleDateFormat("yyyy.MM.dd HH:mm").format(
                    new Date(dataBean.getOverTime()));
            ((ViewHolder) holder).tvCouponTime.setText("有效期至 " + date);
        } else {
            ((ViewHolder) holder).tvCouponTime.setText("领取后三天有效");
        }

        ((ViewHolder)holder).getCoupon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String token = Common.getToken();
                if(TextUtils.isEmpty(token)){
                    Toast.makeText(context, "请先登录", Toast.LENGTH_SHORT).show();
                }else{

                    SaveCouponIdBean saveCouponIdBean = new SaveCouponIdBean();
                    saveCouponIdBean.setCouponId(dataBean.getId());

                    Gson gson = new Gson();
                    String json = gson.toJson(saveCouponIdBean);

                    RequestBody requestBody = RequestBody.create(MediaType.parse("application/json; charset=utf-8"), json);

                    NetUtils.getInstance().getApis().doGetCoupon(requestBody)
                            .subscribeOn(Schedulers.io())
                            .observeOn(AndroidSchedulers.mainThread())
                            .subscribe(new Observer<GetCouponBean>() {
                                @Override
                                public void onSubscribe(Disposable d) {

                                }

                                @Override
                                public void onNext(GetCouponBean getCouponBean) {
                                    Toast.makeText(context, ""+getCouponBean.getData(), Toast.LENGTH_SHORT).show();
                                    if (getCouponBean.getData().equals("领取成功")){
                                        Glide.with(context).load(R.mipmap.activity_getover).into(((ViewHolder)holder).getCoupon);
                                    }
                                }

                                @Override
                                public void onError(Throwable e) {
                                    if(e.getMessage().equals("timeout")){
                                        Toast.makeText(context, "连接超时", Toast.LENGTH_SHORT).show();
                                    }
                                }

                                @Override
                                public void onComplete() {

                                }
                            });
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
        @BindView(R.id.get_coupon)
        ImageView getCoupon;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
