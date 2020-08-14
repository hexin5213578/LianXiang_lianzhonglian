package com.LianXiangKeJi.SupplyChain.order.adapter;

import android.animation.ValueAnimator;
import android.annotation.TargetApi;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.drawable.BitmapDrawable;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.LianXiangKeJi.SupplyChain.R;
import com.LianXiangKeJi.SupplyChain.base.App;
import com.LianXiangKeJi.SupplyChain.common.bean.OrderBean;
import com.LianXiangKeJi.SupplyChain.order.bean.ConfirmGetGoodsBean;
import com.LianXiangKeJi.SupplyChain.order.bean.DeleteOrCancleOrderBean;
import com.LianXiangKeJi.SupplyChain.order.bean.SaveGetPayDataBean;
import com.LianXiangKeJi.SupplyChain.order.bean.SaveOrderListBean;
import com.LianXiangKeJi.SupplyChain.order.bean.SaveOrdersidBean;
import com.LianXiangKeJi.SupplyChain.order.bean.UserOrderBean;
import com.LianXiangKeJi.SupplyChain.order.bean.WxBean;
import com.LianXiangKeJi.SupplyChain.order.bean.ZfbBean;
import com.LianXiangKeJi.SupplyChain.paysuccess.activity.OrderCancleActivity;
import com.LianXiangKeJi.SupplyChain.paysuccess.activity.OrderFinishActivity;
import com.LianXiangKeJi.SupplyChain.paysuccess.activity.OrderShippedActivity;
import com.LianXiangKeJi.SupplyChain.paysuccess.activity.OrderWaitPayActivity;
import com.LianXiangKeJi.SupplyChain.paysuccess.activity.PaySuccessOrderActivity;
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

public class AllOrderAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private final Activity context;
    private final List<UserOrderBean.DataBean> list;

    private PopupWindow mPopupWindow1;

    public AllOrderAdapter(Activity context, List<UserOrderBean.DataBean> list) {

        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if (viewType == 0) {
            View view = View.inflate(context, R.layout.item_payment, null);
            return new ViewHolder(view);
        } else if (viewType == 1) {
            View view = View.inflate(context, R.layout.item_ship, null);
            return new ViewHolder(view);
        } else if (viewType == 3) {
            View view = View.inflate(context, R.layout.item_receipt, null);
            return new ViewHolder(view);
        } else if (viewType == 4) {
            View view = View.inflate(context, R.layout.item_finish, null);
            return new ViewHolder(view);
        } else if (viewType == 5) {
            View view = View.inflate(context, R.layout.item_cancel, null);
            return new ViewHolder(view);
        } else if (viewType == 6) {
            View view = View.inflate(context, R.layout.item_payment_huodao, null);
            return new ViewHolder(view);
        }
        return null;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        int orderState = list.get(position).getOrderState();
        int payWay = list.get(position).getPayWay();
        if (orderState == 0) {
            List<UserOrderBean.DataBean.OrdersDetailListBean> ordersDetailList = list.get(position).getOrdersDetailList();
            LinearLayoutManager manager = new LinearLayoutManager(context, RecyclerView.VERTICAL, false);
            ((ViewHolder) holder).rcOrdergoods.setLayoutManager(manager);

            OrderGoodsListAdapterTwo adapter = new OrderGoodsListAdapterTwo(context, ordersDetailList);

            ((ViewHolder) holder).rcOrdergoods.setAdapter(adapter);
            //总价为
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

            ((ViewHolder) holder).btPay.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (list.get(position).getPayWay() == 1) {
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
                                        SPUtil.getInstance().saveData(context, SPUtil.FILE_NAME, "orderinfo", json);


                                        PayReq request = new PayReq();
                                        WxBean.DataBean.ReturnmapBean returnmap = data.getReturnmap();
                                        request.appId = returnmap.getAppid();
                                        request.partnerId = returnmap.getPartnerId();
                                        request.prepayId = returnmap.getPrepayId();
                                        request.nonceStr = returnmap.getNonceStr();
                                        request.timeStamp = returnmap.getTimeStamp();
                                        request.packageValue = returnmap.getPackageX();
                                        request.sign = returnmap.getSign();
                                        request.extData = "app data"; // optional
                                        App.getWXApi().sendReq(request);

                                    }

                                    @Override
                                    public void onError(Throwable e) {

                                    }

                                    @Override
                                    public void onComplete() {

                                    }
                                });

                    } else {
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
                                        if (zfbBean != null) {
                                            EventBus.getDefault().post(zfbBean);
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

        } else if (orderState == 1) {
            List<UserOrderBean.DataBean.OrdersDetailListBean> ordersDetailList = list.get(position).getOrdersDetailList();
            LinearLayoutManager manager = new LinearLayoutManager(context, RecyclerView.VERTICAL, false);
            ((ViewHolder) holder).rcOrdergoods.setLayoutManager(manager);
            OrderGoodsListAdapter adapter = new OrderGoodsListAdapter(context, ordersDetailList);
            ((ViewHolder) holder).rcOrdergoods.setAdapter(adapter);

            ((ViewHolder) holder).tvPrice.setText("总价：￥" + list.get(position).getMoney());

            ((ViewHolder) holder).rlItem.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(context, PaySuccessOrderActivity.class);
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

        } else if (orderState == 3) {
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
            if (list.get(position).getPayWay() == 3) {
                ((ViewHolder) holder).btConfirm.setText("已收货(付款)");
                ((ViewHolder)holder).tvPayment.setText("确认收货后付款");
            }

            RequestBody requestBody = RequestBody.create(MediaType.parse("application/json; charset=utf-8"), json);
            ((ViewHolder) holder).btConfirm.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    String s = ((ViewHolder) holder).btConfirm.getText().toString();
                    if (s.equals("确认收货")) {
                        AlertDialog.Builder builder = new AlertDialog.Builder(context)
                                .setMessage("是否确认收货").setPositiveButton("确定", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialogInterface, int i) {
                                        NetUtils.getInstance().getApis().doConfirmGetGoods(requestBody)
                                                .subscribeOn(Schedulers.io())
                                                .observeOn(AndroidSchedulers.mainThread())
                                                .subscribe(new Observer<ConfirmGetGoodsBean>() {
                                                    @Override
                                                    public void onSubscribe(Disposable d) {

                                                    }

                                                    @Override
                                                    public void onNext(ConfirmGetGoodsBean confirmGetGoodsBean) {
                                                        Toast.makeText(context, "" + confirmGetGoodsBean.getData(), Toast.LENGTH_SHORT).show();
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
                    } else {
                        //选择支付方式
                        mPopupWindow1 = new PopupWindow();
                        mPopupWindow1.setHeight(ViewGroup.LayoutParams.WRAP_CONTENT);
                        mPopupWindow1.setWidth(ViewGroup.LayoutParams.MATCH_PARENT);
                        View view1 = LayoutInflater.from(context).inflate(R.layout.dialog_go_pay, null);
                        //popwindow设置属性
                        mPopupWindow1.setContentView(view1);
                        mPopupWindow1.setBackgroundDrawable(new BitmapDrawable());
                        mPopupWindow1.setFocusable(true);
                        mPopupWindow1.setOutsideTouchable(true);
                        mPopupWindow1.setOnDismissListener(new PopupWindow.OnDismissListener() {
                            @Override
                            public void onDismiss() {
                                setWindowAlpa(false);
                            }
                        });
                        show1(view1);
                        Handler handler = new Handler();
                        handler.postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                //要执行的操作
                                dismiss1();
                            }
                        }, 2000);//2秒后执行弹出框消失
                    }
                }
            });

            ((ViewHolder) holder).rlItem.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    if (list.get(position).getPayWay() == 3) {
                        //选择支付方式
                        mPopupWindow1 = new PopupWindow();
                        mPopupWindow1.setHeight(ViewGroup.LayoutParams.WRAP_CONTENT);
                        mPopupWindow1.setWidth(ViewGroup.LayoutParams.MATCH_PARENT);
                        View view1 = LayoutInflater.from(context).inflate(R.layout.dialog_tishi, null);
                        //popwindow设置属性
                        mPopupWindow1.setContentView(view1);
                        mPopupWindow1.setBackgroundDrawable(new BitmapDrawable());
                        mPopupWindow1.setFocusable(true);
                        mPopupWindow1.setOutsideTouchable(true);
                        mPopupWindow1.setOnDismissListener(new PopupWindow.OnDismissListener() {
                            @Override
                            public void onDismiss() {
                                setWindowAlpa(false);
                            }
                        });
                        show1(view1);
                        Handler handler = new Handler();
                        handler.postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                //要执行的操作
                                dismiss1();
                            }
                        }, 2000);//2秒后执行弹出框消失
                    }else{
                        Intent intent = new Intent(context, OrderShippedActivity.class);
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
                        } else if (list.get(position).getPayWay() == 1) {
                            theway = "微信支付";
                        } else {
                            theway = "货到付款";
                        }
                        long times = list.get(position).getGmtCreate();
                        String orderid = list.get(position).getId();

                        intent.putExtra("theway", theway);
                        intent.putExtra("time", times);
                        intent.putExtra("orderid", orderid);
                        context.startActivity(intent);
                    }
                }
            });
        } else if (orderState == 4) {
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
            ((ViewHolder) holder).btDelete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    AlertDialog.Builder builder = new AlertDialog.Builder(context)
                            .setMessage("是否确认删除").setPositiveButton("确定", new DialogInterface.OnClickListener() {
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
                                    // 你想做的事情
                                    dialogInterface.dismiss();
                                }
                            });
                    builder.create().show();
                }
            });
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
        } else if (orderState == 5) {
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
            ((ViewHolder) holder).btDelete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    AlertDialog.Builder builder = new AlertDialog.Builder(context)
                            .setMessage("是否确认删除").setPositiveButton("确定", new DialogInterface.OnClickListener() {
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
                    Intent intent = new Intent(context, OrderCancleActivity.class);
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
        } else if (orderState == 6) {
            List<UserOrderBean.DataBean.OrdersDetailListBean> ordersDetailList = list.get(position).getOrdersDetailList();
            LinearLayoutManager manager = new LinearLayoutManager(context, RecyclerView.VERTICAL, false);
            ((ViewHolder) holder).rcOrdergoods.setLayoutManager(manager);
            OrderGoodsListAdapterTwo adapter = new OrderGoodsListAdapterTwo(context, ordersDetailList);

            ((ViewHolder) holder).rcOrdergoods.setAdapter(adapter);
            ((ViewHolder)holder).tvPayment.setText("等待卖家发货");
            ((ViewHolder) holder).tvPrice.setText("总价：￥" + list.get(position).getMoney());
            ((ViewHolder) holder).btPay.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    mPopupWindow1 = new PopupWindow();
                    mPopupWindow1.setHeight(ViewGroup.LayoutParams.WRAP_CONTENT);
                    mPopupWindow1.setWidth(ViewGroup.LayoutParams.MATCH_PARENT);
                    View view1 = LayoutInflater.from(context).inflate(R.layout.dialog_no_pay, null);
                    //popwindow设置属性
                    mPopupWindow1.setContentView(view1);
                    mPopupWindow1.setBackgroundDrawable(new BitmapDrawable());
                    mPopupWindow1.setFocusable(true);
                    mPopupWindow1.setOutsideTouchable(true);
                    mPopupWindow1.setOnDismissListener(new PopupWindow.OnDismissListener() {
                        @Override
                        public void onDismiss() {
                            setWindowAlpa(false);
                        }
                    });
                    show1(view1);
                    Handler handler = new Handler();
                    handler.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            //要执行的操作
                            dismiss1();
                        }
                    }, 2000);//2秒后执行弹出框消失


                }
            });

            ((ViewHolder) holder).rlItem.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    mPopupWindow1 = new PopupWindow();
                    mPopupWindow1.setHeight(ViewGroup.LayoutParams.WRAP_CONTENT);
                    mPopupWindow1.setWidth(ViewGroup.LayoutParams.MATCH_PARENT);
                    View view1 = LayoutInflater.from(context).inflate(R.layout.dialog_no_pay, null);
                    //popwindow设置属性
                    mPopupWindow1.setContentView(view1);
                    mPopupWindow1.setBackgroundDrawable(new BitmapDrawable());
                    mPopupWindow1.setFocusable(true);
                    mPopupWindow1.setOutsideTouchable(true);
                    mPopupWindow1.setOnDismissListener(new PopupWindow.OnDismissListener() {
                        @Override
                        public void onDismiss() {
                            setWindowAlpa(false);
                        }
                    });
                    show1(view1);
                    Handler handler = new Handler();
                    handler.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            //要执行的操作
                            dismiss1();
                        }
                    }, 2000);//2秒后执行弹出框消失

                }
            });
        }
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    @Override
    public int getItemViewType(int position) {
        UserOrderBean.DataBean dataBean = list.get(position);
        int orderState = dataBean.getOrderState();

        return orderState;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.rc_ordergoods)
        RecyclerView rcOrdergoods;
        @BindView(R.id.tv_price)
        TextView tvPrice;
        @BindView(R.id.bt_cancle)
        Button btCancle;
        @BindView(R.id.bt_confirm)
        Button btConfirm;
        @BindView(R.id.bt_delete)
        Button btDelete;
        @BindView(R.id.bt_pay)
        Button btPay;
        @BindView(R.id.rl_item)
        RelativeLayout rlItem;
        @BindView(R.id.tv_payment)
        TextView tvPayment;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

    private void show1(View v) {
        if (mPopupWindow1 != null && !mPopupWindow1.isShowing()) {
            mPopupWindow1.showAtLocation(v, Gravity.CENTER_HORIZONTAL, 0, 0);
        }
        setWindowAlpa(true);
    }

    public void dismiss1() {
        if (mPopupWindow1 != null && mPopupWindow1.isShowing()) {
            mPopupWindow1.dismiss();
        }
    }

    //设置透明度
    public void setWindowAlpa(boolean isopen) {
        if (Build.VERSION.SDK_INT < 11) {
            return;
        }
        final Window window = context.getWindow();
        final WindowManager.LayoutParams lp = window.getAttributes();
        window.setFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND, WindowManager.LayoutParams.FLAG_DIM_BEHIND);
        ValueAnimator animator;
        if (isopen) {
            animator = ValueAnimator.ofFloat(1.0f, 0.5f);
        } else {
            animator = ValueAnimator.ofFloat(0.5f, 1.0f);
        }
        animator.setDuration(400);
        animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {

            @TargetApi(Build.VERSION_CODES.HONEYCOMB)
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                float alpha = (float) animation.getAnimatedValue();
                lp.alpha = alpha;
                window.setAttributes(lp);
            }
        });
        animator.start();
    }
}
