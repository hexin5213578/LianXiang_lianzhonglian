package com.LianXiangKeJi.SupplyChain.welcome;

import androidx.appcompat.app.AppCompatActivity;

import android.animation.ValueAnimator;
import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Intent;
import android.graphics.drawable.BitmapDrawable;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.LianXiangKeJi.SupplyChain.R;
import com.LianXiangKeJi.SupplyChain.base.BaseAvtivity;
import com.LianXiangKeJi.SupplyChain.base.BasePresenter;
import com.LianXiangKeJi.SupplyChain.login.activity.LoginActivity;
import com.LianXiangKeJi.SupplyChain.main.activity.MainActivity;
import com.LianXiangKeJi.SupplyChain.protocol.PrivacypolicyActivity;
import com.LianXiangKeJi.SupplyChain.protocol.UserAgreementActivity;

import static com.LianXiangKeJi.SupplyChain.base.App.getContext;

public class WelcomeActivity extends BaseAvtivity{

    private PopupWindow mPopupWindow1;

    @Override
    protected int getResId() {
        return R.layout.activity_welcome;
    }

    @Override
    protected void getData() {
        // 设置标题栏颜色
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP){
                Window window = WelcomeActivity.this.getWindow();
                window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
                window.setStatusBarColor(WelcomeActivity.this.getResources().getColor(R.color.welcome_title));
            }

        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                showselect();
            }
        }, 2000);//2秒后执行Runnable中的run方法

    }

    @Override
    protected BasePresenter initPresenter() {
        return null;
    }
    private void show1(View v) {
        if (mPopupWindow1 != null && !mPopupWindow1.isShowing()) {
            mPopupWindow1.showAtLocation(v, Gravity.CENTER_HORIZONTAL, 0, 0);
        }
        setWindowAlpa(true);
    }
    public void dismiss1() {
        if (mPopupWindow1 != null && mPopupWindow1.isShowing()) {
            mPopupWindow1.dismiss();
        }
    }

    //设置透明度
    public void setWindowAlpa(boolean isopen) {
        if (android.os.Build.VERSION.SDK_INT < 11) {
            return;
        }
        final Window window =WelcomeActivity.this.getWindow();
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
    public void showselect(){
        //要执行的操作
        mPopupWindow1 = new PopupWindow();
        mPopupWindow1.setHeight(ViewGroup.LayoutParams.WRAP_CONTENT);
        mPopupWindow1.setWidth(ViewGroup.LayoutParams.MATCH_PARENT);
        View view1 = LayoutInflater.from(WelcomeActivity.this).inflate(R.layout.dialog_home_selector, null);
        TextView tv_yinsi =  view1.findViewById(R.id.tv_yinsi);
        TextView tv_xieyi =   view1.findViewById(R.id.tv_xieyi);
        TextView tv_notagree = view1.findViewById(R.id.tv_no);
        TextView tv_agree =  view1.findViewById(R.id.tv_agree);

        tv_yinsi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(WelcomeActivity.this, PrivacypolicyActivity.class));

            }
        });
        tv_xieyi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(WelcomeActivity.this, UserAgreementActivity.class));
            }
        });
        tv_notagree.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        tv_agree.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dismiss1();
                startActivity(new Intent(WelcomeActivity.this,MainActivity.class));
                finish();
            }
        });
        //popwindow设置属性
        mPopupWindow1.setContentView(view1);
        mPopupWindow1.setBackgroundDrawable(new BitmapDrawable());
        mPopupWindow1.setFocusable(false);
        mPopupWindow1.setOutsideTouchable(false);
        mPopupWindow1.setOnDismissListener(new PopupWindow.OnDismissListener() {
            @Override
            public void onDismiss() {
                setWindowAlpa(false);
            }
        });
        show1(view1);
    }
}