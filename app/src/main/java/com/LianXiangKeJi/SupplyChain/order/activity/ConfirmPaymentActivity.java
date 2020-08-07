package com.LianXiangKeJi.SupplyChain.order.activity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.LianXiangKeJi.SupplyChain.R;
import com.LianXiangKeJi.SupplyChain.base.App;
import com.LianXiangKeJi.SupplyChain.base.BaseAvtivity;
import com.LianXiangKeJi.SupplyChain.base.BasePresenter;
import com.LianXiangKeJi.SupplyChain.common.bean.OrderBean;
import com.LianXiangKeJi.SupplyChain.main.bean.SaveShopCarBean;
import com.LianXiangKeJi.SupplyChain.main.bean.ShopCarBean;
import com.LianXiangKeJi.SupplyChain.order.bean.GenerOrdersBean;
import com.LianXiangKeJi.SupplyChain.order.bean.PayResult;
import com.LianXiangKeJi.SupplyChain.order.bean.SavePutOrderBean;
import com.LianXiangKeJi.SupplyChain.paysuccess.activity.PaySuccessActivity;
import com.LianXiangKeJi.SupplyChain.paysuccess.bean.IntentBean;
import com.LianXiangKeJi.SupplyChain.utils.NetUtils;
import com.LianXiangKeJi.SupplyChain.utils.SPUtil;
import com.alipay.sdk.app.PayTask;
import com.google.gson.Gson;
import com.tencent.mm.opensdk.constants.ConstantsAPI;
import com.tencent.mm.opensdk.modelbase.BaseReq;
import com.tencent.mm.opensdk.modelbase.BaseResp;
import com.tencent.mm.opensdk.modelpay.PayReq;
import com.tencent.mm.opensdk.openapi.IWXAPIEventHandler;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import okhttp3.MediaType;
import okhttp3.RequestBody;

/**
 * @ClassName:ConfirmPaymentActivity
 * @Author:hmy
 * @Description:java类作用描述
 */
public class ConfirmPaymentActivity extends BaseAvtivity implements View.OnClickListener, IWXAPIEventHandler {
    @BindView(R.id.back)
    ImageView back;
    @BindView(R.id.title)
    TextView title;
    @BindView(R.id.tv_right)
    TextView tvRight;
    @BindView(R.id.tv_order_number)
    TextView tvOrderNumber;
    @BindView(R.id.tv_order_price)
    TextView tvOrderPrice;
    @BindView(R.id.tv_order_pay_theway)
    TextView tvOrderPayTheway;
    @BindView(R.id.tv_order_time)
    TextView tvOrderTime;
    @BindView(R.id.tv_order_payto)
    TextView tvOrderPayto;
    @BindView(R.id.bt_confirm)
    Button btConfirm;
    private String info;
    private List<OrderBean> orderlist;
    private String theway;
    private GenerOrdersBean.DataBean data;
    private String remark;
    private String couponid;

    @Override
    protected int getResId() {
        return R.layout.activity_confirm_payment;
    }

    @Override
    protected void getData() {
        setTitleColor(ConfirmPaymentActivity.this);

        title.setText("支付");
        tvRight.setVisibility(View.GONE);
        
        Intent intent = getIntent();
        theway = intent.getStringExtra("theway");
        remark = intent.getStringExtra("remark");
        couponid = intent.getStringExtra("couponid");

        Bundle bundle = intent.getExtras();
        orderlist = (List<OrderBean>) bundle.getSerializable("orderlist");

        if(theway.equals("支付宝支付")){
            SavePutOrderBean savePutOrderBean = new SavePutOrderBean();
            List<SavePutOrderBean.ResultBean> list = new ArrayList<>();
            for (int i = 0; i< orderlist.size(); i++){
                String goodsid = orderlist.get(i).getGoodsid();
                int count = orderlist.get(i).getCount();
                SavePutOrderBean.ResultBean resultBean = new SavePutOrderBean.ResultBean();
                resultBean.setNumber(count+"");
                resultBean.setShopGoodsId(goodsid);
                list.add(resultBean);
            }
            savePutOrderBean.setGoodsList(list);
            if(couponid!=null){
                savePutOrderBean.setUserCouponId(couponid);
            }else{
                savePutOrderBean.setUserCouponId("");
            }
            if(remark!=null){
                savePutOrderBean.setRemark(remark);
            }else{
                savePutOrderBean.setRemark("");
            }
            Gson gson = new Gson();
            String json = gson.toJson(savePutOrderBean);

            Log.d("hmy",json);
            RequestBody requestBody = RequestBody.create(MediaType.parse("application/json; charset=utf-8"), json);


            NetUtils.getInstance().getApis().doGenerOrder(requestBody)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(new Observer<GenerOrdersBean>() {


                        @Override
                        public void onSubscribe(Disposable d) {

                        }

                        @Override
                        public void onNext(GenerOrdersBean generOrdersBean) {
                            data = generOrdersBean.getData();
                            tvOrderNumber.setText(data.getOrdersId());
                            info = data.getBody();
                            tvOrderPrice.setText("￥"+ data.getMoney()+"");
                            tvOrderPayTheway.setText(data.getPayment());
                            String date = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(
                                    new java.util.Date(data.getGmtCreate()));
                            tvOrderTime.setText(date);


                            LinkedHashMap<String, String> map = SPUtil.getMap(ConfirmPaymentActivity.this, "goodsid");

                            for (int i=0;i<orderlist.size();i++){
                                String goodsid = orderlist.get(i).getGoodsid();
                                for (int j =0;j<map.size();j++){
                                    map.remove(goodsid);
                                }
                            }
                            //创建订单后从购物车删除
                            List<SaveShopCarBean.ResultBean> shoplist = new ArrayList<>();
                            SaveShopCarBean saveShopCarBean = new SaveShopCarBean();
                            saveShopCarBean.setState(false);
                            SaveShopCarBean.ResultBean resultBean = new SaveShopCarBean.ResultBean();
                            //添加进集合
                            //遍历map集合的键
                            for (String key : map.keySet()) {
                                resultBean.setShopGoodsId(key);
                                shoplist.add(resultBean);

                            }
                            saveShopCarBean.setShoppingCartList(shoplist);

                            Gson gson = new Gson();
                            String json = gson.toJson(saveShopCarBean);
                            RequestBody requestBody = RequestBody.create(MediaType.parse("application/json; charset=utf-8"), json);

                            NetUtils.getInstance().getApis().doShopCar(requestBody)
                                    .subscribeOn(Schedulers.io())
                                    .observeOn(AndroidSchedulers.mainThread())
                                    .subscribe(new Observer<ShopCarBean>() {
                                        @Override
                                        public void onSubscribe(Disposable d) {

                                        }

                                        @Override
                                        public void onNext(ShopCarBean shopCarBean) {

                                        }

                                        @Override
                                        public void onError(Throwable e) {

                                        }

                                        @Override
                                        public void onComplete() {

                                        }
                                    });
                            SPUtil.setMap(ConfirmPaymentActivity.this,"goodsid",map);
                        }

                        @Override
                        public void onError(Throwable e) {

                        }

                        @Override
                        public void onComplete() {

                        }
                    });
        }else{
            // TODO: 2020/8/5 生成微信订单
            Toast.makeText(this, "微信支付", Toast.LENGTH_SHORT).show();


        }


        tvOrderPayTheway.setText(theway);
        // TODO: 2020/7/24 赋值


        back.setOnClickListener(this);
        btConfirm.setOnClickListener(this);
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void getIntent(IntentBean bean){
        if(bean.getStr().equals("关闭")){
            finish();
        }
    }
    private static final int SDK_PAY_FLAG = 1;

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
                        Toast.makeText(ConfirmPaymentActivity.this, "支付成功", Toast.LENGTH_SHORT).show();



                       Intent intent = new Intent(ConfirmPaymentActivity.this, PaySuccessActivity.class);

                       Bundle bundle = new Bundle();
                       bundle.putSerializable("orderlist", (Serializable) orderlist);

                       intent.putExtras(bundle);
                       intent.putExtra("theway",theway);
                       intent.putExtra("time",data.getGmtCreate());
                       intent.putExtra("orderid",data.getOrdersId());
                       startActivity(intent);

                    } else {
                        // 该笔订单真实的支付结果，需要依赖服务端的异步通知。
                        Toast.makeText(ConfirmPaymentActivity.this, "支付失败", Toast.LENGTH_SHORT).show();
                    }
                    break;
                }
                default:
                    break;
            }
        }
    };
    @Override
    protected BasePresenter initPresenter() {
        return null;
    }
    @Override
    protected void onResume() {
        super.onResume();
        if (!EventBus.getDefault().isRegistered(this)) {
            EventBus.getDefault().register(this);
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (EventBus.getDefault().isRegistered(this)) {
            EventBus.getDefault().unregister(this);
        }
    }
    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.back:
                finish();
                break;
            case R.id.bt_confirm:
                String pay_theway = tvOrderPayTheway.getText().toString();
                if(pay_theway.equals("微信支付")){
                    // TODO: 2020/7/31 调起微信支付
                    PayReq request = new PayReq();
                    request.appId="wxacf956e14f407890";
                    App.getWXApi().sendReq(request);

                }else{
                    // TODO: 2020/7/31 调起支付宝支付
                    Runnable payRunnable = new Runnable() {
                        @Override
                        public void run() {
                            PayTask alipay = new PayTask(ConfirmPaymentActivity.this);
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
                break;
        }
    }

    @Override
    public void onReq(BaseReq req) {

    }
    //处理微信回调
    @Override
    public void onResp(BaseResp resp) {
        Log.d("hmy", "onPayFinish, errCode = " + resp.errCode);

        if (resp.getType() == ConstantsAPI.COMMAND_PAY_BY_WX) {
            if(resp.errCode == 0) {
                Toast.makeText(this, "支付成功", Toast.LENGTH_SHORT).show();

                Intent intent = new Intent(ConfirmPaymentActivity.this, PaySuccessActivity.class);

                Bundle bundle = new Bundle();
                bundle.putSerializable("orderlist", (Serializable) orderlist);

                intent.putExtras(bundle);
                intent.putExtra("theway",theway);
                intent.putExtra("time",data.getGmtCreate());
                intent.putExtra("orderid",data.getOrdersId());
                startActivity(intent);

            }else{
                Toast.makeText(this, "支付失败，请重试", Toast.LENGTH_SHORT).show();

            }
        }
    }
}
