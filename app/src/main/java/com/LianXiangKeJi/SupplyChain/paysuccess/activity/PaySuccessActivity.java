package com.LianXiangKeJi.SupplyChain.paysuccess.activity;

import android.content.Intent;
import android.os.Build;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.LianXiangKeJi.SupplyChain.R;
import com.LianXiangKeJi.SupplyChain.base.BaseAvtivity;
import com.LianXiangKeJi.SupplyChain.base.BasePresenter;
import com.LianXiangKeJi.SupplyChain.main.activity.MainActivity;
import com.LianXiangKeJi.SupplyChain.paysuccess.bean.IntentBean;

import org.greenrobot.eventbus.EventBus;

import java.text.SimpleDateFormat;

import butterknife.BindView;

/**
 * @ClassName:PaySuccessActivity
 * @Author:hmy
 * @Description:java类作用描述 支付成功页
 */
public class PaySuccessActivity extends BaseAvtivity implements View.OnClickListener {
    @BindView(R.id.back)
    ImageView back;
    @BindView(R.id.tv_bianhao)
    TextView tvBianhao;
    @BindView(R.id.tv_fangshi)
    TextView tvFangshi;
    @BindView(R.id.tv_time)
    TextView tvTime;
    @BindView(R.id.bt_back)
    Button btBack;
    @BindView(R.id.bt_seeorder)
    Button btSeeorder;

    @Override
    protected int getResId() {
        return R.layout.activity_paysuccess;
    }

    @Override
    protected void getData() {
        btBack.setOnClickListener(this);
        btSeeorder.setOnClickListener(this);
        // TODO: 2020/7/17 设置标题栏颜色
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP){
                Window window = PaySuccessActivity.this.getWindow();
                window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
                window.setStatusBarColor(PaySuccessActivity.this.getResources().getColor(R.color.title_paysuccess));
            }
        Intent intent = getIntent();
        String theway = intent.getStringExtra("theway");
        long times = intent.getLongExtra("time",0);
        String orderid = intent.getStringExtra("orderid");

        tvBianhao.setText(orderid);
        tvFangshi.setText(theway);
        String date = new SimpleDateFormat("yyyy.MM.dd").format(
                new java.util.Date(times));
        tvTime.setText(date);
    }

    @Override
    protected BasePresenter initPresenter() {
        return null;
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            // TODO: 2020/7/22 跳转至主页
            case R.id.bt_back:
                startActivity(new Intent(PaySuccessActivity.this, MainActivity.class));
                EventBus.getDefault().postSticky(new IntentBean());
                break;
            // TODO: 2020/7/22 查看订单
            case R.id.bt_seeorder:

                break;
        }
    }
}
