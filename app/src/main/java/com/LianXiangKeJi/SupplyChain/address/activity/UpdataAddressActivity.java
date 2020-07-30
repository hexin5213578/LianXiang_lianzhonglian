package com.LianXiangKeJi.SupplyChain.address.activity;

import android.content.DialogInterface;
import android.content.Intent;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Switch;
import android.widget.TextView;

import com.LianXiangKeJi.SupplyChain.R;
import com.LianXiangKeJi.SupplyChain.base.BaseAvtivity;
import com.LianXiangKeJi.SupplyChain.base.BasePresenter;
import com.LianXiangKeJi.SupplyChain.common.custom.CustomDialog;
import com.LianXiangKeJi.SupplyChain.map.MapActivity;
import com.amap.api.services.core.PoiItem;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;


import butterknife.BindView;

/**
 * @ClassName:UpdataAddressActivity
 * @Author:hmy
 * @Description:java类作用描述 编辑地址
 */
public class UpdataAddressActivity extends BaseAvtivity implements View.OnClickListener {
    @BindView(R.id.back)
    ImageView back;
    @BindView(R.id.title)
    TextView title;
    @BindView(R.id.tv_right)
    TextView tvRight;
    @BindView(R.id.et_name)
    EditText etName;
    @BindView(R.id.et_phone)
    EditText etPhone;
    @BindView(R.id.et_jiedao)
    EditText etJiedao;
    @BindView(R.id.rl_location)
    RelativeLayout rlLocation;
    @BindView(R.id.et_address)
    EditText etAddress;
    @BindView(R.id.Swich)
    Switch Swich;
    @BindView(R.id.tv_delete)
    TextView tvDelete;

    @Override
    protected int getResId() {
        return R.layout.aicitivy_updataaddress;
    }

    @Override
    protected void onResume() {
        super.onResume();
        if(!EventBus.getDefault().isRegistered(this)){
            EventBus.getDefault().register(this);
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if(EventBus.getDefault().isRegistered(this)){
            EventBus.getDefault().unregister(this);
        }
    }

    @Override
    protected void getData() {

        setTitleColor(UpdataAddressActivity.this);
        title.setText("编辑收货地址");
        tvRight.setText("保存");
        tvRight.setOnClickListener(this);
        rlLocation.setOnClickListener(this);
        tvDelete.setOnClickListener(this);
        back.setOnClickListener(this);

    }

    // TODO: 2020/7/23 接受定位到的信息
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void getJiedao(PoiItem poiItem){
        etJiedao.setText(poiItem.getSnippet());
        etAddress.setText(poiItem.getTitle());
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
            case R.id.tv_right:
                // TODO: 2020/7/18 调用修改我的地址接口


                break;
            case R.id.rl_location:
                startActivity(new Intent(UpdataAddressActivity.this, MapActivity.class));
                break;
            case R.id.tv_delete:
                CustomDialog.Builder builder = new CustomDialog.Builder(UpdataAddressActivity.this);
                builder.setPositiveButton("删除", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                        //设置你的操作事项
                        // TODO: 2020/7/18 调用删除接口 删除地址


                    }
                });
                builder.setNegativeButton("取消",
                        new android.content.DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();

                            }
                        });

                builder.create().show();
                break;
        }
    }
}
