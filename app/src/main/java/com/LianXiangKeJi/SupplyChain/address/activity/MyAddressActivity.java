package com.LianXiangKeJi.SupplyChain.address.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.LianXiangKeJi.SupplyChain.R;
import com.LianXiangKeJi.SupplyChain.base.BaseAvtivity;
import com.LianXiangKeJi.SupplyChain.base.BasePresenter;
import com.LianXiangKeJi.SupplyChain.base.LatitudeandlongitudeBean;
import com.LianXiangKeJi.SupplyChain.order.activity.ConfirmOrderActivity;
import com.LianXiangKeJi.SupplyChain.utils.SPUtil;

import org.greenrobot.eventbus.EventBus;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MyAddressActivity extends BaseAvtivity implements View.OnClickListener {

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
    private LatitudeandlongitudeBean location;

    @Override
    protected int getResId() {
        return R.layout.activity_my_address;
    }

    @Override
    protected void onResume() {
        super.onResume();
   /*     if (!EventBus.getDefault().isRegistered(this)) {
            EventBus.getDefault().register(this);
        }*/
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    /*    if (EventBus.getDefault().isRegistered(this)) {
            EventBus.getDefault().unregister(this);
        }*/
    }

    @Override
    protected void getData() {
        setTitleColor(this);

        title.setText("我的收货地址");
        tvRight.setVisibility(View.GONE);
        back.setOnClickListener(this);

        tvName.setText(SPUtil.getInstance().getData(MyAddressActivity.this, SPUtil.FILE_NAME, SPUtil.USER_NAME));
        tvAddress.setText(SPUtil.getInstance().getData(MyAddressActivity.this, SPUtil.FILE_NAME, SPUtil.KEY_ADDRESS));
        tvPhone.setText(SPUtil.getInstance().getData(MyAddressActivity.this, SPUtil.FILE_NAME, SPUtil.KEY_PHONE));
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
        }
    }
}