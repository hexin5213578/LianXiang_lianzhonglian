package com.LianXiangKeJi.SupplyChain.paysuccess.activity;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.LianXiangKeJi.SupplyChain.R;
import com.LianXiangKeJi.SupplyChain.base.App;
import com.LianXiangKeJi.SupplyChain.base.BaseAvtivity;
import com.LianXiangKeJi.SupplyChain.base.BasePresenter;
import com.LianXiangKeJi.SupplyChain.common.bean.OrderBean;
import com.LianXiangKeJi.SupplyChain.order.adapter.AllOrderAdapter;
import com.LianXiangKeJi.SupplyChain.order.adapter.OrderInfoAdapter;
import com.LianXiangKeJi.SupplyChain.order.adapter.PaymentAdapter;
import com.LianXiangKeJi.SupplyChain.order.bean.DeleteOrCancleOrderBean;
import com.LianXiangKeJi.SupplyChain.order.bean.PayResult;
import com.LianXiangKeJi.SupplyChain.order.bean.SaveGetPayDataBean;
import com.LianXiangKeJi.SupplyChain.order.bean.SaveOrderListBean;
import com.LianXiangKeJi.SupplyChain.order.bean.SaveOrdersidBean;
import com.LianXiangKeJi.SupplyChain.order.bean.WxBean;
import com.LianXiangKeJi.SupplyChain.order.bean.ZfbBean;
import com.LianXiangKeJi.SupplyChain.order.bean.ZfbBean1;
import com.LianXiangKeJi.SupplyChain.utils.NetUtils;
import com.LianXiangKeJi.SupplyChain.utils.SPUtil;
import com.alipay.sdk.app.PayTask;
import com.google.gson.Gson;
import com.tencent.mm.opensdk.modelpay.PayReq;

import org.greenrobot.eventbus.EventBus;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import okhttp3.MediaType;
import okhttp3.RequestBody;

import static com.LianXiangKeJi.SupplyChain.base.App.getContext;

/**
 * @ClassName:OrderWaitPayActivity
 * @Author:hmy
 * @Description:java类作用描述 等待支付订单详情
 */
public class OrderWaitPayActivity extends BaseAvtivity implements View.OnClickListener {
    @BindView(R.id.back)
    ImageView back;
    @BindView(R.id.title)
    TextView title;
    @BindView(R.id.tv_right)
    TextView tvRight;
    @BindView(R.id.rl1)
    RelativeLayout rl1;
    @BindView(R.id.iv_haveaddress)
    ImageView ivHaveaddress;
    @BindView(R.id.tv_name)
    TextView tvName;
    @BindView(R.id.tv_phone)
    TextView tvPhone;
    @BindView(R.id.tv_address)
    TextView tvAddress;
    @BindView(R.id.rl_select_address)
    RelativeLayout rlSelectAddress;
    @BindView(R.id.rc_order_list)
    RecyclerView rcOrderList;
    @BindView(R.id.tv_order_number)
    TextView tvOrderNumber;
    @BindView(R.id.tv_theway)
    TextView tvTheway;
    @BindView(R.id.tv_time1)
    TextView tvTime1;
    @BindView(R.id.to_pay)
    Button toPay;
    @BindView(R.id.bt_cancle)
    Button btCancle;
    private String theway;
    private String orderid;
    private static final int SDK_PAY_FLAG = 1;

    @Override
    protected int getResId() {
        return R.layout.activity_order_wait_pay;
    }
    private Handler mHandler = new Handler() {
        @SuppressLint("HandlerLeak")
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case SDK_PAY_FLAG: {
                    //这里接收支付宝的回调信息
                    //需要注意的是，支付结果一定要调用自己的服务端来确定，不能通过支付宝的回调结果来判断
                    PayResult payResult = new PayResult((Map<String, String>) msg.obj);
                    /**
                     对于支付结果，请商户依赖服务端的异步通知结果。同步通知结果，仅作为支付结束的通知。
                     */
                    String resultInfo = payResult.getResult();// 同步返回需要验证的信息
                    String resultStatus = payResult.getResultStatus();
                    // 判断resultStatus 为9000则代表支付成功
                    if (TextUtils.equals(resultStatus, "9000")) {
                        // 该笔订单是否真实支付成功，需要依赖服务端的异步通知。
                        Toast.makeText(getContext(), "支付成功", Toast.LENGTH_SHORT).show();
                    } else {
                        // 该笔订单真实的支付结果，需要依赖服务端的异步通知。
                        Toast.makeText(getContext(), "支付失败", Toast.LENGTH_SHORT).show();
                    }
                    break;
                }
                default:
                    break;
            }
        }
    };
    @Override
    protected void getData() {
        back.setOnClickListener(this);
        toPay.setOnClickListener(this);
        setTitleColor(OrderWaitPayActivity.this);
        title.setText("订单详情");
        tvRight.setVisibility(View.GONE);

        //展示默认地址
        tvName.setText(SPUtil.getInstance().getData(OrderWaitPayActivity.this, SPUtil.FILE_NAME, SPUtil.USER_NAME));
        tvAddress.setText(SPUtil.getInstance().getData(OrderWaitPayActivity.this, SPUtil.FILE_NAME, SPUtil.KEY_ADDRESS));
        tvPhone.setText(SPUtil.getInstance().getData(OrderWaitPayActivity.this, SPUtil.FILE_NAME, SPUtil.KEY_PHONE));

        Intent intent = getIntent();

        //获取订单信息
        theway = intent.getStringExtra("theway");
        orderid = intent.getStringExtra("orderid");
        long time = intent.getLongExtra("time", 0);

        Bundle bundle = intent.getExtras();
        //获取订单中的商品
        List<OrderBean> orderlist = (List<OrderBean>) bundle.getSerializable("orderlist");

        String date = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(
                new Date(time));
        tvTime1.setText(date);
        tvTheway.setText(theway);
        tvOrderNumber.setText(orderid);

        LinearLayoutManager manager = new LinearLayoutManager(OrderWaitPayActivity.this, RecyclerView.VERTICAL, false);
        rcOrderList.setLayoutManager(manager);
        OrderInfoAdapter orderInfoAdapter = new OrderInfoAdapter(OrderWaitPayActivity.this, orderlist);
        rcOrderList.setAdapter(orderInfoAdapter);

        SaveOrdersidBean saveOrdersidBean = new SaveOrdersidBean();
        saveOrdersidBean.setId(orderid);

        Gson gson = new Gson();
        String json = gson.toJson(saveOrdersidBean);
        RequestBody requestBody = RequestBody.create(MediaType.parse("application/json; charset=utf-8"), json);
        //取消订单
        btCancle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder = new AlertDialog.Builder(OrderWaitPayActivity.this)
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
                                                Toast.makeText(OrderWaitPayActivity.this, "" + deleteOrCancleOrderBean.getData(), Toast.LENGTH_SHORT).show();
                                                finish();
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
                                //ToDo: 你想做的事情
                                dialogInterface.dismiss();
                            }
                        });
                builder.create().show();
            }
        });

    }

    @Override
    protected BasePresenter initPresenter() {
        return null;
    }


    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.back:
                finish();
                break;
            case R.id.to_pay:
                if(theway.equals("微信支付")){
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
                                    SPUtil.getInstance().saveData(OrderWaitPayActivity.this,SPUtil.FILE_NAME,"orderinfo",json);


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
                                    ZfbBean.DataBean data = zfbBean.getData();
                                    String info = data.getBody();
                                    //调用支付宝支付
                                    Runnable payRunnable = new Runnable() {
                                        @Override
                                        public void run() {
                                            PayTask alipay = new PayTask(OrderWaitPayActivity.this);
                                            Map<String,String> result = alipay.payV2(info,true);

                                            Message msg = new Message();
                                            msg.what = SDK_PAY_FLAG;
                                            msg.obj = result;
                                            mHandler.sendMessage(msg);
                                        }
                                    };
                                    // 必须异步调用
                                    Thread payThread = new Thread(payRunnable);
                                    payThread.start();

                                }

                                @Override
                                public void onError(Throwable e) {
                                    Toast.makeText(OrderWaitPayActivity.this, "请求失败", Toast.LENGTH_SHORT).show();
                                }

                                @Override
                                public void onComplete() {

                                }
                            });
                }

                break;
        }
    }

}
