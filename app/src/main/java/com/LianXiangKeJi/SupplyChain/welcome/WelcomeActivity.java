package com.LianXiangKeJi.SupplyChain.welcome;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.view.Window;
import android.view.WindowManager;

import com.LianXiangKeJi.SupplyChain.R;
import com.LianXiangKeJi.SupplyChain.base.BaseAvtivity;
import com.LianXiangKeJi.SupplyChain.base.BasePresenter;
import com.LianXiangKeJi.SupplyChain.main.activity.MainActivity;

public class WelcomeActivity extends BaseAvtivity{

    @Override
    protected int getResId() {
        return R.layout.activity_welcome;
    }

    @Override
    protected void getData() {
        // TODO: 2020/7/17 设置标题栏颜色
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP){
                Window window = WelcomeActivity.this.getWindow();
                window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
                window.setStatusBarColor(WelcomeActivity.this.getResources().getColor(R.color.welcome_title));
            }

        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                //要执行的操作
                Intent intent = new Intent(WelcomeActivity.this, MainActivity.class);
                startActivity(intent);
                finish();
            }
        }, 2000);//2秒后执行Runnable中的run方法

    }

    @Override
    protected BasePresenter initPresenter() {
        return null;
    }
}