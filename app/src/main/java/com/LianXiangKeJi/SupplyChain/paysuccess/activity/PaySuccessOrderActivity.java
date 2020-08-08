package com.LianXiangKeJi.SupplyChain.paysuccess.activity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.LianXiangKeJi.SupplyChain.R;
import com.LianXiangKeJi.SupplyChain.base.BaseAvtivity;
import com.LianXiangKeJi.SupplyChain.base.BasePresenter;
import com.LianXiangKeJi.SupplyChain.common.bean.OrderBean;
import com.LianXiangKeJi.SupplyChain.order.activity.ConfirmOrderActivity;
import com.LianXiangKeJi.SupplyChain.order.adapter.OrderInfoAdapter;
import com.LianXiangKeJi.SupplyChain.utils.SPUtil;

import java.text.SimpleDateFormat;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class PaySuccessOrderActivity extends BaseAvtivity implements View.OnClickListener {
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
    @BindView(R.id.rc_order_list)
    RecyclerView rcOrderList;
    @BindView(R.id.tv_order_number)
    TextView tvOrderNumber;
    @BindView(R.id.tv_theway)
    TextView tvTheway;
    @BindView(R.id.tv_time1)
    TextView tvTime1;
    @BindView(R.id.ll_call)
    LinearLayout llCall;

    @Override
    protected int getResId() {
        return R.layout.activity_pay_success_order;
    }

    @Override
    protected void getData() {
        setTitleColor(PaySuccessOrderActivity.this);
        back.setOnClickListener(this);
        title.setText("订单详情");
        tvRight.setVisibility(View.GONE);
 /*       llCall.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                callPhone("13253353293");
            }
        });*/
        //展示默认地址
        tvName.setText(SPUtil.getInstance().getData(PaySuccessOrderActivity.this, SPUtil.FILE_NAME, SPUtil.USER_NAME));
        tvAddress.setText(SPUtil.getInstance().getData(PaySuccessOrderActivity.this, SPUtil.FILE_NAME, SPUtil.KEY_ADDRESS));
        tvPhone.setText(SPUtil.getInstance().getData(PaySuccessOrderActivity.this, SPUtil.FILE_NAME, SPUtil.KEY_PHONE));
        Intent intent = getIntent();


        //获取订单信息
        String theway = intent.getStringExtra("theway");
        String orderid = intent.getStringExtra("orderid");
        long time = intent.getLongExtra("time", 0);
        Bundle bundle = intent.getExtras();
        //获取订单中的商品
        List<OrderBean> orderlist = (List<OrderBean>) bundle.getSerializable("orderlist");

        String date = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(
                new java.util.Date(time));
        tvTime1.setText(date);
        tvTheway.setText(theway);
        tvOrderNumber.setText(orderid);

        LinearLayoutManager manager = new LinearLayoutManager(PaySuccessOrderActivity.this, RecyclerView.VERTICAL, false);
        rcOrderList.setLayoutManager(manager);
        OrderInfoAdapter orderInfoAdapter = new OrderInfoAdapter(PaySuccessOrderActivity.this, orderlist);
        rcOrderList.setAdapter(orderInfoAdapter);

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
        }
    }


}
