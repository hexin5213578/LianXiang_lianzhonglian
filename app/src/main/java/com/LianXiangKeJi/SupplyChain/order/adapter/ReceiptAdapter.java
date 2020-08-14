package com.LianXiangKeJi.SupplyChain.order.adapter;

import android.animation.ValueAnimator;
import android.annotation.TargetApi;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.drawable.BitmapDrawable;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.RadioButton;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.LianXiangKeJi.SupplyChain.R;
import com.LianXiangKeJi.SupplyChain.base.App;
import com.LianXiangKeJi.SupplyChain.common.bean.OrderBean;
import com.LianXiangKeJi.SupplyChain.order.activity.ConfirmOrderActivity;
import com.LianXiangKeJi.SupplyChain.order.activity.ConfirmPaymentActivity;
import com.LianXiangKeJi.SupplyChain.order.bean.CashondeliveryBean;
import com.LianXiangKeJi.SupplyChain.order.bean.ConfirmGetGoodsBean;
import com.LianXiangKeJi.SupplyChain.order.bean.SaveGetPayDataBean;
import com.LianXiangKeJi.SupplyChain.order.bean.SaveOrderListBean;
import com.LianXiangKeJi.SupplyChain.order.bean.SaveOrdersidBean;
import com.LianXiangKeJi.SupplyChain.order.bean.SavePutOrderBean;
import com.LianXiangKeJi.SupplyChain.order.bean.UserOrderBean;
import com.LianXiangKeJi.SupplyChain.order.bean.WxBean;
import com.LianXiangKeJi.SupplyChain.order.bean.ZfbBean;
import com.LianXiangKeJi.SupplyChain.order.bean.ZfbBean1;
import com.LianXiangKeJi.SupplyChain.order.bean.ZfbBean2;
import com.LianXiangKeJi.SupplyChain.paysuccess.activity.OrderHuodaoPayActivity;
import com.LianXiangKeJi.SupplyChain.paysuccess.activity.OrderShippedActivity;
import com.LianXiangKeJi.SupplyChain.paysuccess.activity.OrderWaitPayActivity;
import com.LianXiangKeJi.SupplyChain.paysuccess.bean.IntentBean;
import com.LianXiangKeJi.SupplyChain.utils.NetUtils;
import com.LianXiangKeJi.SupplyChain.utils.SPUtil;
import com.LianXiangKeJi.SupplyChain.utils.StringUtil;
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

public class ReceiptAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private final Activity context;
    private final List<UserOrderBean.DataBean> list;
    private PopupWindow mPopupWindow;
    private double price = 0.0;
    private String id;

    public ReceiptAdapter(Activity context, List<UserOrderBean.DataBean> list) {

        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if(viewType==0 || viewType==1){
            View view = View.inflate(context, R.layout.item_receipt, null);
            return new ViewHolder(view);
        }else if(viewType==3){
            View view =View.inflate(context,R.layout.item_payment_huodao,null);
            return new ViewHolder(view);
        }
        return null;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        if(list.get(position).getPayWay()==0 || list.get(position).getPayWay()==1){
            List<UserOrderBean.DataBean.OrdersDetailListBean> ordersDetailList = list.get(position).getOrdersDetailList();
            LinearLayoutManager manager = new LinearLayoutManager(context, RecyclerView.VERTICAL, false);
            ((ViewHolder) holder).rcOrdergoods.setLayoutManager(manager);

            OrderGoodsListAdapterTwo adapter = new OrderGoodsListAdapterTwo(context, ordersDetailList);

            ((ViewHolder) holder).rcOrdergoods.setAdapter(adapter);

            ((ViewHolder) holder).tvPrice.setText("总价：￥" + list.get(position).getMoney());

            ((ViewHolder) holder).rlItem.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
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

            SaveOrdersidBean saveOrdersidBean = new SaveOrdersidBean();
            saveOrdersidBean.setId(list.get(position).getId());

            Gson gson = new Gson();
            String json = gson.toJson(saveOrdersidBean);

            RequestBody requestBody = RequestBody.create(MediaType.parse("application/json; charset=utf-8"), json);
            ((ViewHolder) holder).btConfirm.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
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
                                    // 你想做的事情
                                    dialogInterface.dismiss();
                                }
                            });
                    builder.create().show();
                }
            });
        }else if(list.get(position).getPayWay()==3){
            id = list.get(position).getId();
            List<UserOrderBean.DataBean.OrdersDetailListBean> ordersDetailList = list.get(position).getOrdersDetailList();
            LinearLayoutManager manager = new LinearLayoutManager(context, RecyclerView.VERTICAL, false);
            ((ViewHolder) holder).rcOrdergoods.setLayoutManager(manager);
            OrderGoodsListAdapterTwo adapter = new OrderGoodsListAdapterTwo(context, ordersDetailList);

            ((ViewHolder) holder).rcOrdergoods.setAdapter(adapter);
            price = list.get(position).getMoney();

            ((ViewHolder) holder).tvPrice.setText("总价：￥" + list.get(position).getMoney());
            ((ViewHolder)holder).rlItem.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(context, OrderHuodaoPayActivity.class);
                    Bundle bundle = new Bundle();
                    List<UserOrderBean.DataBean.OrdersDetailListBean> ordersDetailList1 = list.get(position).getOrdersDetailList();
                    List<OrderBean> orderlist = new ArrayList<>();


                    for (int i=0;i<ordersDetailList1.size();i++){
                        OrderBean orderBean = new OrderBean();
                        UserOrderBean.DataBean.OrdersDetailListBean ordersDetailListBean = ordersDetailList1.get(i);
                        //将数据存入集合
                        orderBean.setName(ordersDetailListBean.getName());
                        orderBean.setPrice(ordersDetailListBean.getPrice()+"");
                        orderBean.setSpecs(ordersDetailListBean.getSpecs());
                        orderBean.setCount(ordersDetailListBean.getNumber());
                        orderBean.setImageurl(ordersDetailListBean.getLittlePrintUrl());
                        orderBean.setGoodsid(ordersDetailListBean.getId());
                        orderlist.add(orderBean);
                    }

                    bundle.putSerializable("orderlist", (Serializable) orderlist);

                    intent.putExtras(bundle);
                    String theway = "";
                    if(list.get(position).getPayWay()==0){
                        theway="支付宝支付";
                    }else if(list.get(position).getPayWay()==1){
                        theway="微信支付";
                    }else{
                        theway="货到付款";
                    }
                    long times = list.get(position).getGmtCreate();
                    String orderid = list.get(position).getId();


                    intent.putExtra("theway",theway);
                    intent.putExtra("time",times);
                    intent.putExtra("orderid",orderid);
                    context.startActivity(intent);
                }
            });
            ((ViewHolder)holder).btPay.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    showSelect();
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
        int payWay = list.get(position).getPayWay();
        return payWay;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.rc_ordergoods)
        RecyclerView rcOrdergoods;
        @BindView(R.id.tv_price)
        TextView tvPrice;
        @BindView(R.id.rl_item)
        RelativeLayout rlItem;
        @BindView(R.id.bt_confirm)
        Button btConfirm;
        @BindView(R.id.bt_pay)
        Button btPay;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
    public void showSelect() {
        //创建popwiondow弹出框
        mPopupWindow = new PopupWindow();
        mPopupWindow.setHeight(ViewGroup.LayoutParams.WRAP_CONTENT);
        mPopupWindow.setWidth(ViewGroup.LayoutParams.MATCH_PARENT);
        View view = LayoutInflater.from(context).inflate(R.layout.dialog_select_pay2, null);
        ImageView ivclose = view.findViewById(R.id.iv_close);
        RelativeLayout rl2 = view.findViewById(R.id.rl2);
        RelativeLayout rl3 = view.findViewById(R.id.rl3);

        RadioButton rb1 = view.findViewById(R.id.rb1);
        RadioButton rb2 = view.findViewById(R.id.rb2);

        ImageView check1 = view.findViewById(R.id.pay_check1);
        ImageView check2 = view.findViewById(R.id.pay_check2);

        TextView tvprice = view.findViewById(R.id.tv_price);
        Button startpay = view.findViewById(R.id.startpay);

        tvprice.setText(" ￥" + StringUtil.round(String.valueOf(price)));

        ivclose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dismiss();
            }
        });
        rl2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                check1.setVisibility(View.VISIBLE);
                check2.setVisibility(View.GONE);
                rb1.setChecked(true);
                rb2.setChecked(false);
            }
        });
        rl3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                check2.setVisibility(View.VISIBLE);
                check1.setVisibility(View.GONE);
                rb2.setChecked(true);
                rb1.setChecked(false);
            }
        });
        //立即支付 判断微信支付宝选中状态决定调起哪种支付方式
        startpay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // 微信支付
                if (rb1.isChecked()) {
                    //获取微信支付的请求体
                    SaveGetPayDataBean saveGetPayDataBean = new SaveGetPayDataBean();
                    saveGetPayDataBean.setOrdersId(id);
                    saveGetPayDataBean.setPayWay("1");

                    Gson gson = new Gson();
                    String json = gson.toJson(saveGetPayDataBean);

                    RequestBody requestBody = RequestBody.create(MediaType.parse("application/json; charset=utf-8"), json);

                    NetUtils.getInstance().getApis()
                            .doGetWxData(requestBody)
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
                                    dismiss();
                                }

                                @Override
                                public void onError(Throwable e) {

                                }

                                @Override
                                public void onComplete() {

                                }
                            });
                }
                //支付宝支付
                else if(rb2.isChecked()) {
                    //获取支付宝支付的请求体
                    SaveGetPayDataBean saveGetPayDataBean = new SaveGetPayDataBean();
                    saveGetPayDataBean.setOrdersId(id);
                    saveGetPayDataBean.setPayWay("0");

                    Gson gson = new Gson();
                    String json = gson.toJson(saveGetPayDataBean);

                    RequestBody requestBody = RequestBody.create(MediaType.parse("application/json; charset=utf-8"), json);
                    NetUtils.getInstance()
                            .getApis()
                            .doGetZfbData(requestBody)
                            .subscribeOn(Schedulers.io())
                            .observeOn(AndroidSchedulers.mainThread())
                            .subscribe(new Observer<ZfbBean>() {
                                @Override
                                public void onSubscribe(Disposable d) {

                                }

                                @Override
                                public void onNext(ZfbBean bean) {
                                    if(bean!=null){
                                        ZfbBean2 zfbBean2 = new ZfbBean2();

                                        ZfbBean2.DataBean dataBean = new ZfbBean2.DataBean();
                                        String body = bean.getData().getBody();
                                        dataBean.setBody(body);
                                        zfbBean2.setData(dataBean);

                                        EventBus.getDefault().post(zfbBean2);
                                        dismiss();
                                    }
                                }

                                @Override
                                public void onError(Throwable e) {

                                }

                                @Override
                                public void onComplete() {

                                }
                            });
                }
            }
        });
        //popwindow设置属性
        mPopupWindow.setAnimationStyle(R.style.popwindow_anim_style);
        mPopupWindow.setContentView(view);
        mPopupWindow.setBackgroundDrawable(new BitmapDrawable());
        mPopupWindow.setFocusable(true);
        mPopupWindow.setOutsideTouchable(true);
        mPopupWindow.setOnDismissListener(new PopupWindow.OnDismissListener() {
            @Override
            public void onDismiss() {
                setWindowAlpa(false);
            }
        });
        show(view);
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

    /**
     * 显示PopupWindow
     */
    private void show(View v) {
        if (mPopupWindow != null && !mPopupWindow.isShowing()) {
            mPopupWindow.showAtLocation(v, Gravity.BOTTOM, 0, 0);
        }
        setWindowAlpa(true);
    }


    /**
     * 消失PopupWindow
     */
    public void dismiss() {
        if (mPopupWindow != null && mPopupWindow.isShowing()) {
            mPopupWindow.dismiss();
        }
    }
}
