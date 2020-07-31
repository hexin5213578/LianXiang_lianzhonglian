package com.LianXiangKeJi.SupplyChain.order.activity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.LianXiangKeJi.SupplyChain.R;
import com.LianXiangKeJi.SupplyChain.base.BaseAvtivity;
import com.LianXiangKeJi.SupplyChain.base.BasePresenter;
import com.LianXiangKeJi.SupplyChain.main.activity.MainActivity;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * @ClassName:ConfirmPaymentActivity
 * @Author:hmy
 * @Description:java类作用描述
 */
public class ConfirmPaymentActivity extends BaseAvtivity implements View.OnClickListener {
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
        String theway = intent.getStringExtra("theway");

        // TODO: 2020/7/24 赋值
        tvOrderPayTheway.setText(theway);
        
        back.setOnClickListener(this);
        btConfirm.setOnClickListener(this);
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
            case R.id.bt_confirm:
                String pay_theway = tvOrderPayTheway.getText().toString();
                if(pay_theway.equals("微信支付")){
                    // TODO: 2020/7/31 调起微信支付
                }else{
                    // TODO: 2020/7/31 调起支付宝支付
                }
                break;
        }
    }
}
