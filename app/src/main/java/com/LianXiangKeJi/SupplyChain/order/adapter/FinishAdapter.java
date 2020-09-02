package com.LianXiangKeJi.SupplyChain.order.adapter;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.LianXiangKeJi.SupplyChain.R;
import com.LianXiangKeJi.SupplyChain.common.bean.OrderBean;
import com.LianXiangKeJi.SupplyChain.order.bean.DeleteOrCancleOrderBean;
import com.LianXiangKeJi.SupplyChain.order.bean.SaveOrdersidBean;
import com.LianXiangKeJi.SupplyChain.order.bean.UserOrderBean;
import com.LianXiangKeJi.SupplyChain.paysuccess.activity.OrderFinishActivity;
import com.LianXiangKeJi.SupplyChain.utils.NetUtils;
import com.LianXiangKeJi.SupplyChain.utils.StringUtil;
import com.bumptech.glide.Glide;
import com.google.gson.Gson;

import org.greenrobot.eventbus.EventBus;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import okhttp3.MediaType;
import okhttp3.RequestBody;

public class FinishAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private final Context context;
    private final List<UserOrderBean.DataBean> list;

    private double price = 0.0;
    private Dialog mLoadingDialog;

    public FinishAdapter(Context context, List<UserOrderBean.DataBean> list) {

        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = View.inflate(context, R.layout.item_finish, null);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        List<UserOrderBean.DataBean.OrdersDetailListBean> ordersDetailList = list.get(position).getOrdersDetailList();
        LinearLayoutManager manager = new LinearLayoutManager(context, RecyclerView.VERTICAL, false);
        ((ViewHolder) holder).rcOrdergoods.setLayoutManager(manager);

        OrderGoodsListAdapterTwo adapter = new OrderGoodsListAdapterTwo(context, ordersDetailList);

        ((ViewHolder) holder).rcOrdergoods.setAdapter(adapter);

        double money = list.get(position).getMoney();
        String round = StringUtil.round(String.valueOf(money));
        ((ViewHolder) holder).tvPrice.setText("总价：￥" + round);

        ((ViewHolder) holder).rlItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, OrderFinishActivity.class);
                Bundle bundle = new Bundle();
                List<UserOrderBean.DataBean.OrdersDetailListBean> ordersDetailList1 = list.get(position).getOrdersDetailList();
                List<OrderBean> orderlist = new ArrayList<>();


                for (int i = 0; i < ordersDetailList1.size(); i++) {
                    OrderBean orderBean = new OrderBean();

                    UserOrderBean.DataBean.OrdersDetailListBean ordersDetailListBean = ordersDetailList1.get(i);
                    //将数据存入集合
                    orderBean.setName(ordersDetailListBean.getName());
                    orderBean.setPrice(ordersDetailListBean.getPrice() + "");
                    orderBean.setSpecs(ordersDetailListBean.getSpecs());
                    orderBean.setCount(ordersDetailListBean.getNumber());
                    orderBean.setImageurl(ordersDetailListBean.getLittlePrintUrl());
                    orderBean.setGoodsid(ordersDetailListBean.getId());

                    orderlist.add(orderBean);
                }

                bundle.putSerializable("orderlist", (Serializable) orderlist);

                intent.putExtras(bundle);
                String theway = "";
                if (list.get(position).getPayWay() == 0) {
                    theway = "支付宝支付";
                } else {
                    theway = "微信支付";
                }
                long times = list.get(position).getGmtCreate();
                String orderid = list.get(position).getId();

                intent.putExtra("theway", theway);
                intent.putExtra("time", times);
                intent.putExtra("orderid", orderid);
                context.startActivity(intent);
            }
        });


        //取消订单
        ((ViewHolder) holder).btDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SaveOrdersidBean saveOrdersidBean = new SaveOrdersidBean();
                saveOrdersidBean.setId(list.get(position).getId());

                Gson gson = new Gson();
                String json = gson.toJson(saveOrdersidBean);

                RequestBody requestBody = RequestBody.create(MediaType.parse("application/json; charset=utf-8"), json);


                AlertDialog.Builder builder = new AlertDialog.Builder(context)
                        .setMessage("是否确认删除").setPositiveButton("确定", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                showDialog();
                                NetUtils.getInstance().getApis().cancleOrder(requestBody)
                                        .subscribeOn(Schedulers.io())
                                        .observeOn(AndroidSchedulers.mainThread())
                                        .subscribe(new Observer<DeleteOrCancleOrderBean>() {
                                            @Override
                                            public void onSubscribe(Disposable d) {

                                            }

                                            @Override
                                            public void onNext(DeleteOrCancleOrderBean deleteOrCancleOrderBean) {
                                                hideDialog();
                                                Toast.makeText(context, "" + deleteOrCancleOrderBean.getData(), Toast.LENGTH_SHORT).show();
                                                EventBus.getDefault().post("刷新界面");
                                            }

                                            @Override
                                            public void onError(Throwable e) {

                                            }

                                            @Override
                                            public void onComplete() {

                                            }
                                        });
                            }
                        }).setNegativeButton("取消", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                //你想做的事情
                                dialogInterface.dismiss();
                            }
                        });
                builder.create().show();
            }
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.rc_ordergoods)
        RecyclerView rcOrdergoods;
        @BindView(R.id.tv_price)
        TextView tvPrice;
        @BindView(R.id.rl_item)
        RelativeLayout rlItem;
        @BindView(R.id.bt_delete)
        Button btDelete;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
    // 展示loading圈
    public void showDialog() {
        if (mLoadingDialog == null) {
            mLoadingDialog = new Dialog(context);
            mLoadingDialog.setCancelable(false);
            View v = View.inflate(context, R.layout.dialog_loading, null);
            ImageView iv = v.findViewById(R.id.iv_loading);
            Glide.with(context).asGif().load(R.mipmap.loading).into(iv);

            mLoadingDialog.addContentView(v,
                    new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT,
                            ViewGroup.LayoutParams.WRAP_CONTENT));
        }

        mLoadingDialog.show();
    }
    //  隐藏loading圈
    public void hideDialog() {
        if (mLoadingDialog != null && mLoadingDialog.isShowing()) {
            mLoadingDialog.dismiss();
        }
    }
}
