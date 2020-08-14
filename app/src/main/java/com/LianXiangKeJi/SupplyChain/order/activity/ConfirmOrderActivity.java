package com.LianXiangKeJi.SupplyChain.order.activity;

import android.animation.ValueAnimator;
import android.annotation.TargetApi;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.drawable.BitmapDrawable;
import android.os.Build;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.RadioButton;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.LianXiangKeJi.SupplyChain.R;
import com.LianXiangKeJi.SupplyChain.base.BaseAvtivity;
import com.LianXiangKeJi.SupplyChain.base.BasePresenter;
import com.LianXiangKeJi.SupplyChain.common.bean.OrderBean;
import com.LianXiangKeJi.SupplyChain.common.custom.CustomDialog;
import com.LianXiangKeJi.SupplyChain.movable.activity.CouponActivity;
import com.LianXiangKeJi.SupplyChain.movable.bean.SaveCouponIdBean;
import com.LianXiangKeJi.SupplyChain.order.adapter.OrderInfoAdapter;
import com.LianXiangKeJi.SupplyChain.order.bean.CashondeliveryBean;
import com.LianXiangKeJi.SupplyChain.order.bean.SavePutOrderBean;
import com.LianXiangKeJi.SupplyChain.paysuccess.bean.IntentBean;
import com.LianXiangKeJi.SupplyChain.utils.NetUtils;
import com.LianXiangKeJi.SupplyChain.utils.SPUtil;
import com.LianXiangKeJi.SupplyChain.utils.StringUtil;
import com.google.gson.Gson;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.io.Serializable;
import java.text.DecimalFormat;
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

/**
 * @ClassName:ConfirmOrderActivity
 * @Author:hmy
 * @Description:java类作用描述 确认订单
 */
public class ConfirmOrderActivity extends BaseAvtivity implements View.OnClickListener {
    @BindView(R.id.back)
    ImageView back;
    @BindView(R.id.title)
    TextView title;
    @BindView(R.id.tv_right)
    TextView tvRight;
    @BindView(R.id.tv_name)
    TextView tvName;
    @BindView(R.id.tv_phone)
    TextView tvPhone;
    @BindView(R.id.tv_address)
    TextView tvAddress;
    @BindView(R.id.rl_select_address)
    RelativeLayout rlSelectAddress;
    @BindView(R.id.rc_order)
    RecyclerView rcOrder;
    @BindView(R.id.tv_yunfei)
    TextView tvYunfei;
    @BindView(R.id.et_remarks)
    EditText etRemarks;
    @BindView(R.id.tv_count)
    TextView tvCount;
    @BindView(R.id.tv_price)
    TextView tvPrice;
    @BindView(R.id.bt_confirm)
    Button btConfirm;
    @BindView(R.id.iv_havenoaddress)
    ImageView ivHavenoaddress;
    @BindView(R.id.tv_havenoaddress)
    TextView tvHavenoaddress;
    @BindView(R.id.iv_haveaddress)
    ImageView ivHaveaddress;
    @BindView(R.id.tv_coupon)
    TextView tvCoupon;
    @BindView(R.id.rl_select_coupon)
    RelativeLayout rlSelectCoupon;
    private PopupWindow mPopupWindow;
    private int count = 0;
    private double price = 0.00;
    private List<OrderBean> orderlist;
    String counponId  ="";
    int orderstate;
    @Override
    protected int getResId() {
        return R.layout.activity_confirm_order;
    }

    @Override
    protected void getData() {
        setTitleColor(ConfirmOrderActivity.this);

        title.setText("确认订单");
        tvRight.setVisibility(View.GONE);
        back.setOnClickListener(this);
        rlSelectAddress.setOnClickListener(this);
        btConfirm.setOnClickListener(this);
        rlSelectCoupon.setOnClickListener(this);

        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        orderlist = (List<OrderBean>) bundle.getSerializable("orderlist");
        //设置适配器
        LinearLayoutManager manager = new LinearLayoutManager(ConfirmOrderActivity.this, RecyclerView.VERTICAL, false);
        rcOrder.setLayoutManager(manager);
        OrderInfoAdapter orderInfoAdapter = new OrderInfoAdapter(ConfirmOrderActivity.this, orderlist);
        rcOrder.setAdapter(orderInfoAdapter);

        for (int i = 0; i < orderlist.size(); i++) {
            int count = orderlist.get(i).getCount();
            String price = orderlist.get(i).getPrice();
            Float aFloat = Float.valueOf(price);

            this.count += count;
            double itemprice = count * aFloat;

            Log.d("hmy", "条目价格为" + itemprice + "");
            this.price += itemprice;

        }
        tvCount.setText(orderlist.size() + "种共" + count + "件，");
        Log.d("hmy", "总价为" + price + "");

        tvPrice.setText(StringUtil.round(String.valueOf(price)));
        // 展示默认地址
        tvName.setText(SPUtil.getInstance().getData(ConfirmOrderActivity.this, SPUtil.FILE_NAME, SPUtil.USER_NAME));
        tvAddress.setText(SPUtil.getInstance().getData(ConfirmOrderActivity.this, SPUtil.FILE_NAME, SPUtil.KEY_ADDRESS));
        tvPhone.setText(SPUtil.getInstance().getData(ConfirmOrderActivity.this, SPUtil.FILE_NAME, SPUtil.KEY_PHONE));
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
            //选择地址
            case R.id.rl_select_address:

                break;
            //提交订单
            case R.id.bt_confirm:
                String name = tvName.getText().toString();
                if (!TextUtils.isEmpty(name)) {
                    showSelect();
                } else {
                    CustomDialog.Builder builder = new CustomDialog.Builder(ConfirmOrderActivity.this);
                    builder.setPositiveButton("去填写", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                            //设置你的操作
                        }
                    });
                    builder.setNegativeButton("取消",
                            new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int which) {
                                    dialog.dismiss();
                                }
                            });

                    builder.create().show();
                }
                break;
            case R.id.rl_select_coupon:
                startActivity(new Intent(ConfirmOrderActivity.this, CouponActivity.class));

                break;
            default:
                break;
        }
    }
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void getCouponBean(SaveCouponIdBean bean){
        String couponId = bean.getCouponId();
        int state = bean.getState();
        orderstate = state;
        counponId=couponId;
        int full = Integer.valueOf(bean.getFull());
        int jian = Integer.valueOf(bean.getJian());
        if(price>=full){
            price = this.price - jian;
            tvPrice.setText(price + "");
            tvCoupon.setText("满"+full+"元减"+jian+"元");
        }
        if(full==0){
            tvCoupon.setText("请选择优惠券");
        }

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

    public void showSelect() {
        //创建popwiondow弹出框
        mPopupWindow = new PopupWindow();
        mPopupWindow.setHeight(ViewGroup.LayoutParams.WRAP_CONTENT);
        mPopupWindow.setWidth(ViewGroup.LayoutParams.MATCH_PARENT);
        View view = LayoutInflater.from(this).inflate(R.layout.dialog_select_pay, null);
        ImageView ivclose = view.findViewById(R.id.iv_close);
        RelativeLayout rl2 = view.findViewById(R.id.rl2);
        RelativeLayout rl3 = view.findViewById(R.id.rl3);
        RelativeLayout rl4 = view.findViewById(R.id.rl4);

        RadioButton rb1 = view.findViewById(R.id.rb1);
        RadioButton rb2 = view.findViewById(R.id.rb2);
        RadioButton rb3 = view.findViewById(R.id.rb3);

        ImageView check1 = view.findViewById(R.id.pay_check1);
        ImageView check2 = view.findViewById(R.id.pay_check2);
        ImageView check3 = view.findViewById(R.id.pay_check3);

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
                check3.setVisibility(View.GONE);
                rb1.setChecked(true);
                rb2.setChecked(false);
                rb3.setChecked(false);
            }
        });
        rl3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                check2.setVisibility(View.VISIBLE);
                check1.setVisibility(View.GONE);
                check3.setVisibility(View.GONE);
                rb2.setChecked(true);
                rb1.setChecked(false);
                rb3.setChecked(false);
            }
        });
        rl4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                check3.setVisibility(View.VISIBLE);
                check2.setVisibility(View.GONE);
                check1.setVisibility(View.GONE);
                rb3.setChecked(true);
                rb2.setChecked(false);
                rb1.setChecked(false);
            }
        });
        //立即支付 判断微信支付宝选中状态决定调起哪种支付方式
        startpay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // 微信支付
                if (rb1.isChecked()) {
                    Intent intent = new Intent(ConfirmOrderActivity.this, ConfirmPaymentActivity.class);
                    Log.d("hmy", "微信支付");
                    intent.putExtra("theway", "微信支付");
                    intent.putExtra("remark", etRemarks.getText().toString());
                    intent.putExtra("couponid",counponId);
                    Bundle bundle = new Bundle();
                    bundle.putSerializable("orderlist", (Serializable) orderlist);
                    intent.putExtras(bundle);
                    if(orderstate==2){
                        Toast.makeText(ConfirmOrderActivity.this, "优惠券已经被占用", Toast.LENGTH_SHORT).show();
                    }else{
                        startActivity(intent);
                    }
                }
                //支付宝支付
                else if(rb2.isChecked()){
                    Log.d("hmy", "支付宝支付");

                    Intent intent = new Intent(ConfirmOrderActivity.this, ConfirmPaymentActivity.class);
                    intent.putExtra("theway", "支付宝支付");

                    intent.putExtra("remark", etRemarks.getText().toString());
                    intent.putExtra("couponid",counponId);
                    Bundle bundle = new Bundle();
                    bundle.putSerializable("orderlist", (Serializable) orderlist);
                    intent.putExtras(bundle);
                    if(orderstate==2){
                        Toast.makeText(ConfirmOrderActivity.this, "优惠券已经被占用", Toast.LENGTH_SHORT).show();
                    }else{
                        startActivity(intent);
                    }
                    //货到付款
                }else if(rb3.isChecked()){

                    //生成货到付款订单
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
                    if(counponId!=null){
                        savePutOrderBean.setUserCouponId(counponId);
                    }else{
                        savePutOrderBean.setUserCouponId("");
                    }
                    String s = etRemarks.getText().toString();

                    if(s!=null){
                        savePutOrderBean.setRemark(s);
                    }else{
                        savePutOrderBean.setRemark("");
                    }
                    savePutOrderBean.setPayWay(3);
                    Gson gson = new Gson();
                    String json = gson.toJson(savePutOrderBean);

                    Log.d("hmy",json);
                    RequestBody requestBody = RequestBody.create(MediaType.parse("application/json; charset=utf-8"), json);
                    NetUtils.getInstance().getApis().doCashonOrder(requestBody)
                            .subscribeOn(Schedulers.io())
                            .observeOn(AndroidSchedulers.mainThread())
                            .subscribe(new Observer<CashondeliveryBean>() {
                                @Override
                                public void onSubscribe(Disposable d) {

                                }

                                @Override
                                public void onNext(CashondeliveryBean cashondeliveryBean) {
                                    Toast.makeText(ConfirmOrderActivity.this, ""+cashondeliveryBean.getData(), Toast.LENGTH_SHORT).show();
                                    if(cashondeliveryBean.getData().equals("货到付款订单已提交")){
                                        IntentBean intentBean = new IntentBean();
                                        intentBean.setStr("关闭");
                                        EventBus.getDefault().post(intentBean);
                                        finish();
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

    @Override
    protected void onRestart() {
        super.onRestart();
        dismiss();
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void getIntent(IntentBean bean) {
        if (bean.getStr().equals("关闭")) {
            finish();
        }
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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //add setContentView(...) invocation
        ButterKnife.bind(this);
    }
}
