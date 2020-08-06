package com.LianXiangKeJi.SupplyChain.paysuccess.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.LianXiangKeJi.SupplyChain.R;
import com.LianXiangKeJi.SupplyChain.base.BaseAvtivity;
import com.LianXiangKeJi.SupplyChain.base.BasePresenter;
import com.LianXiangKeJi.SupplyChain.utils.SPUtil;

import butterknife.BindView;
import butterknife.ButterKnife;

public class OrderShippedActivity extends BaseAvtivity implements View.OnClickListener {


    @BindView(R.id.back)
    ImageView back;
    @BindView(R.id.title)
    TextView title;
    @BindView(R.id.tv_right)
    TextView tvRight;
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
    @BindView(R.id.tv_time2)
    TextView tvTime2;
    @BindView(R.id.bt_confirm)
    Button btConfirm;

    @Override
    protected int getResId() {
        return R.layout.activity_order_shipped;
    }

    @Override
    protected void getData() {
        back.setOnClickListener(this);
        btConfirm.setOnClickListener(this);

        title.setText("订单详情");
        tvRight.setVisibility(View.GONE);

        //展示默认地址

        tvName.setText(SPUtil.getInstance().getData(OrderShippedActivity.this, SPUtil.FILE_NAME, SPUtil.USER_NAME));
        tvAddress.setText(SPUtil.getInstance().getData(OrderShippedActivity.this, SPUtil.FILE_NAME, SPUtil.KEY_ADDRESS));
        tvPhone.setText(SPUtil.getInstance().getData(OrderShippedActivity.this, SPUtil.FILE_NAME, SPUtil.KEY_PHONE));

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

                break;
        }
    }
}
