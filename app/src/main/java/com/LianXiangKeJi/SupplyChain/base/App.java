package com.LianXiangKeJi.SupplyChain.base;

import android.app.Application;
import android.content.Context;
import android.graphics.Color;
import android.graphics.Rect;
import android.os.Build;
import android.util.Log;
import android.view.Window;
import android.view.WindowManager;

import androidx.multidex.MultiDex;

import com.alipay.sdk.app.EnvUtils;
import com.bumptech.glide.Glide;
import com.facebook.drawee.backends.pipeline.Fresco;
import com.smarx.notchlib.INotchScreen;
import com.smarx.notchlib.NotchScreenManager;

import static org.greenrobot.eventbus.EventBus.TAG;

/**
 * @ClassName: App
 * @Description: (java类作用描述)
 * @Author: hmy
 */
public class App extends Application {
    private static Context context;

    @Override
    public void onCreate() {
        super.onCreate();
        context = this;
        MultiDex.install(this);
        Fresco.initialize(context);
        //沙箱测试
        EnvUtils.setEnv(EnvUtils.EnvEnum.SANDBOX);


    }
    public static Context getContext() {
        return context;
    }
}
