package com.LianXiangKeJi.SupplyChain.paysuccess.activity;

import android.animation.ValueAnimator;
import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.content.Intent;
import android.graphics.drawable.BitmapDrawable;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
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

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.LianXiangKeJi.SupplyChain.R;
import com.LianXiangKeJi.SupplyChain.base.App;
import com.LianXiangKeJi.SupplyChain.base.BaseAvtivity;
import com.LianXiangKeJi.SupplyChain.base.BasePresenter;
import com.LianXiangKeJi.SupplyChain.common.bean.OrderBean;
import com.LianXiangKeJi.SupplyChain.order.adapter.OrderInfoAdapter;
import com.LianXiangKeJi.SupplyChain.order.bean.PayResult;
import com.LianXiangKeJi.SupplyChain.order.bean.SaveGetPayDataBean;
import com.LianXiangKeJi.SupplyChain.order.bean.SaveOrderListBean;
import com.LianXiangKeJi.SupplyChain.order.bean.WxBean;
import com.LianXiangKeJi.SupplyChain.order.bean.ZfbBean;
import com.LianXiangKeJi.SupplyChain.order.bean.ZfbBean2;
import com.LianXiangKeJi.SupplyChain.utils.NetUtils;
import com.LianXiangKeJi.SupplyChain.utils.SPUtil;
import com.LianXiangKeJi.SupplyChain.utils.StringUtil;
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

public class OrderHuodaoPayActivity extends BaseAvtivity implements View.OnClickListener {
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
    @BindView(R.id.tv1)
    TextView tv1;
    @BindView(R.id.tv2)
    TextView tv2;
    @BindView(R.id.tv_order_number)
    TextView tvOrderNumber;
    @BindView(R.id.tv3)
    TextView tv3;
    @BindView(R.id.tv_theway)
    TextView tvTheway;
    @BindView(R.id.tv4)
    TextView tv4;
    @BindView(R.id.tv_time1)
    TextView tvTime1;
    @BindView(R.id.tv6)
    TextView tv6;
    @BindView(R.id.bt_pay)
    Button btPay;
    private PopupWindow mPopupWindow;
    private double OrderPrice = 0.0;
    private String orderprice;
    private String orderid;
    private static final int SDK_PAY_FLAG = 1;

    @Override
    protected int getResId() {
        return R.layout.activity_order_huodao_pay;
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
        btPay.setOnClickListener(this);
        title.setText("订单详情");
        tvRight.setVisibility(View.GONE);
        setTitleColor(OrderHuodaoPayActivity.this);
        //展示默认地址

        tvName.setText(SPUtil.getInstance().getData(OrderHuodaoPayActivity.this, SPUtil.FILE_NAME, SPUtil.USER_NAME));
        tvAddress.setText(SPUtil.getInstance().getData(OrderHuodaoPayActivity.this, SPUtil.FILE_NAME, SPUtil.KEY_ADDRESS));
        tvPhone.setText(SPUtil.getInstance().getData(OrderHuodaoPayActivity.this, SPUtil.FILE_NAME, SPUtil.KEY_PHONE));
        Intent intent = getIntent();


        //获取订单信息
        String theway = intent.getStringExtra("theway");
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

        LinearLayoutManager manager = new LinearLayoutManager(OrderHuodaoPayActivity.this, RecyclerView.VERTICAL, false);
        rcOrderList.setLayoutManager(manager);
        OrderInfoAdapter orderInfoAdapter = new OrderInfoAdapter(OrderHuodaoPayActivity.this, orderlist);
        rcOrderList.setAdapter(orderInfoAdapter);

        for (int i =0;i<orderlist.size();i++){
            String price = orderlist.get(i).getPrice();
            OrderPrice+=Double.valueOf(price);
        }
        orderprice = StringUtil.round(String.valueOf(OrderPrice));

    }

    @Override
    protected BasePresenter initPresenter() {
        return null;
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.back:
                finish();
                break;
            case R.id.bt_pay:
                showSelect();
                break;
        }
    }
    public void showSelect() {
        //创建popwiondow弹出框
        mPopupWindow = new PopupWindow();
        mPopupWindow.setHeight(ViewGroup.LayoutParams.WRAP_CONTENT);
        mPopupWindow.setWidth(ViewGroup.LayoutParams.MATCH_PARENT);
        View view = LayoutInflater.from(OrderHuodaoPayActivity.this).inflate(R.layout.dialog_select_pay2, null);
        ImageView ivclose = view.findViewById(R.id.iv_close);
        RelativeLayout rl2 = view.findViewById(R.id.rl2);
        RelativeLayout rl3 = view.findViewById(R.id.rl3);

        RadioButton rb1 = view.findViewById(R.id.rb1);
        RadioButton rb2 = view.findViewById(R.id.rb2);

        ImageView check1 = view.findViewById(R.id.pay_check1);
        ImageView check2 = view.findViewById(R.id.pay_check2);

        TextView tvprice = view.findViewById(R.id.tv_price);
        Button startpay = view.findViewById(R.id.startpay);

        tvprice.setText(" ￥" + StringUtil.round(String.valueOf(orderprice)));

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
                    saveGetPayDataBean.setOrdersId(orderid);
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
                                    SPUtil.getInstance().saveData(OrderHuodaoPayActivity.this,SPUtil.FILE_NAME,"orderinfo",json);


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
                }
                //支付宝支付
                else if(rb2.isChecked()) {
                    //获取支付宝支付的请求体
                    SaveGetPayDataBean saveGetPayDataBean = new SaveGetPayDataBean();
                    saveGetPayDataBean.setOrdersId(orderid);
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
                                    //调用支付宝支付
                                    ZfbBean.DataBean data = bean.getData();
                                    String info = data.getBody();
                                    //调用支付宝支付
                                    Runnable payRunnable = new Runnable() {
                                        @Override
                                        public void run() {
                                            PayTask alipay = new PayTask(OrderHuodaoPayActivity.this);
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
                                    Toast.makeText(OrderHuodaoPayActivity.this, "请求失败", Toast.LENGTH_SHORT).show();

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
        final Window window = this.getWindow();
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
