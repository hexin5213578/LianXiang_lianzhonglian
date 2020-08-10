package com.LianXiangKeJi.SupplyChain.order.adapter;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.LianXiangKeJi.SupplyChain.R;
import com.LianXiangKeJi.SupplyChain.base.App;
import com.LianXiangKeJi.SupplyChain.common.bean.OrderBean;
import com.LianXiangKeJi.SupplyChain.order.bean.DeleteOrCancleOrderBean;
import com.LianXiangKeJi.SupplyChain.order.bean.SaveGetPayDataBean;
import com.LianXiangKeJi.SupplyChain.order.bean.SaveOrderListBean;
import com.LianXiangKeJi.SupplyChain.order.bean.SaveOrdersidBean;
import com.LianXiangKeJi.SupplyChain.order.bean.UserOrderBean;
import com.LianXiangKeJi.SupplyChain.order.bean.WxBean;
import com.LianXiangKeJi.SupplyChain.order.bean.ZfbBean;
import com.LianXiangKeJi.SupplyChain.order.bean.ZfbBean1;
import com.LianXiangKeJi.SupplyChain.paysuccess.activity.OrderWaitPayActivity;
import com.LianXiangKeJi.SupplyChain.utils.NetUtils;
import com.LianXiangKeJi.SupplyChain.utils.SPUtil;
import com.google.gson.Gson;
import com.tencent.mm.opensdk.modelpay.PayReq;

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

public class PaymentAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private final Context context;
    private final List<UserOrderBean.DataBean> list;

    private double price = 0.0;

    public PaymentAdapter(Context context, List<UserOrderBean.DataBean> list) {

        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = View.inflate(context, R.layout.item_payment, null);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        List<UserOrderBean.DataBean.OrdersDetailListBean> ordersDetailList = list.get(position).getOrdersDetailList();
        LinearLayoutManager manager = new LinearLayoutManager(context, RecyclerView.VERTICAL, false);
        ((ViewHolder) holder).rcOrdergoods.setLayoutManager(manager);

        OrderGoodsListAdapterTwo adapter = new OrderGoodsListAdapterTwo(context, ordersDetailList);

        ((ViewHolder) holder).rcOrdergoods.setAdapter(adapter);

        ((ViewHolder) holder).tvPrice.setText("总价：￥" + list.get(position).getMoney());

        SaveOrdersidBean saveOrdersidBean = new SaveOrdersidBean();
        saveOrdersidBean.setId(list.get(position).getId());

        Gson gson = new Gson();
        String json = gson.toJson(saveOrdersidBean);

        RequestBody requestBody = RequestBody.create(MediaType.parse("application/json; charset=utf-8"), json);
        //取消订单
        ((ViewHolder) holder).btCancle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder = new AlertDialog.Builder(context)
                        .setMessage("是否确认取消订单").setPositiveButton("确定", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                NetUtils.getInstance().getApis().cancleOrder(requestBody)
                                        .subscribeOn(Schedulers.io())
                                        .observeOn(AndroidSchedulers.mainThread())
                                        .subscribe(new Observer<DeleteOrCancleOrderBean>() {
                                            @Override
                                            public void onSubscribe(Disposable d) {

                                            }

                                            @Override
                                            public void onNext(DeleteOrCancleOrderBean deleteOrCancleOrderBean) {
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
        ((ViewHolder) holder).rlItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, OrderWaitPayActivity.class);
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

        String orderid = list.get(position).getId();

        ((ViewHolder)holder).btPay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(list.get(position).getPayWay()==1){
                    //调用微信支付
                    SaveGetPayDataBean saveGetPayDataBean = new SaveGetPayDataBean();
                    saveGetPayDataBean.setOrdersId(orderid);
                    saveGetPayDataBean.setPayWay("1");

                    Gson gson = new Gson();
                    String json = gson.toJson(saveGetPayDataBean);

                    RequestBody requestBody = RequestBody.create(MediaType.parse("application/json; charset=utf-8"), json);

                    NetUtils.getInstance().getApis().doGetWxData(requestBody)
                            .subscribeOn(Schedulers.io())
                            .observeOn(AndroidSchedulers.mainThread())
                            .subscribe(new Observer<WxBean>() {
                                @Override
                                public void onSubscribe(Disposable d) {

                                }

                                @Override
                                public void onNext(WxBean wxBean) {
                                    WxBean.DataBean data = wxBean.getData();
                                    SaveOrderListBean saveOrderListBean = new SaveOrderListBean();

                                    //设置标记跳转支付成功页
                                    saveOrderListBean.setFlag(1);
                                    Gson gson = new Gson();
                                    String json = gson.toJson(saveOrderListBean);
                                    SPUtil.getInstance().saveData(context,SPUtil.FILE_NAME,"orderinfo",json);


                                    PayReq request = new PayReq();
                                    WxBean.DataBean.ReturnmapBean returnmap = data.getReturnmap();
                                    request.appId=returnmap.getAppid();
                                    request.partnerId=returnmap.getPartnerId();
                                    request.prepayId=returnmap.getPrepayId();
                                    request.nonceStr =returnmap.getNonceStr();
                                    request.timeStamp=returnmap.getTimeStamp();
                                    request.packageValue=returnmap.getPackageX();
                                    request.sign= returnmap.getSign();
                                    request.extData			= "app data"; // optional
                                    App.getWXApi().sendReq(request);

                                }

                                @Override
                                public void onError(Throwable e) {

                                }

                                @Override
                                public void onComplete() {

                                }
                            });

                }else{
                    //调用支付宝支付
                    SaveGetPayDataBean saveGetPayDataBean = new SaveGetPayDataBean();
                    saveGetPayDataBean.setOrdersId(orderid);
                    saveGetPayDataBean.setPayWay("0");

                    Gson gson = new Gson();
                    String json = gson.toJson(saveGetPayDataBean);

                    RequestBody requestBody = RequestBody.create(MediaType.parse("application/json; charset=utf-8"), json);
                    NetUtils.getInstance().getApis().doGetZfbData(requestBody)
                            .subscribeOn(Schedulers.io())
                            .observeOn(AndroidSchedulers.mainThread())
                            .subscribe(new Observer<ZfbBean>() {
                                @Override
                                public void onSubscribe(Disposable d) {

                                }

                                @Override
                                public void onNext(ZfbBean zfbBean) {
                                    if(zfbBean!=null){
                                        ZfbBean1 zfbBean1 = new ZfbBean1();
                                        ZfbBean1.DataBean dataBean = new ZfbBean1.DataBean();
                                        String body = zfbBean.getData().getBody();
                                        dataBean.setBody(body);
                                        zfbBean1.setData(dataBean);

                                        EventBus.getDefault().post(zfbBean1);
                                    }
                                }

                                @Override
                                public void onError(Throwable e) {
                                    Toast.makeText(context, "请求失败", Toast.LENGTH_SHORT).show();
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
        @BindView(R.id.rc_ordergoods)
        RecyclerView rcOrdergoods;
        @BindView(R.id.tv_price)
        TextView tvPrice;
        @BindView(R.id.bt_cancle)
        Button btCancle;
        @BindView(R.id.rl_item)
        RelativeLayout rlItem;
        @BindView(R.id.bt_pay)
        Button btPay;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
