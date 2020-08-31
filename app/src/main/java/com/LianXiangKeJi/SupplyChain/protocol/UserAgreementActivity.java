package com.LianXiangKeJi.SupplyChain.protocol;

import android.os.Bundle;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageView;
import android.widget.TextView;

import com.LianXiangKeJi.SupplyChain.R;
import com.LianXiangKeJi.SupplyChain.base.BaseAvtivity;
import com.LianXiangKeJi.SupplyChain.base.BasePresenter;

import butterknife.BindView;
import butterknife.ButterKnife;

public class UserAgreementActivity extends BaseAvtivity {
    @BindView(R.id.back)
    ImageView back;
    @BindView(R.id.title)
    TextView title;
    @BindView(R.id.tv_right)
    TextView tvRight;
    @BindView(R.id.wab)
    WebView wab;
    String str = "http://www.luoyanglx.com/xieyi/";
    @Override
    protected int getResId() {
        return R.layout.activity_user_agreement;
    }

    @Override
    protected void getData() {
        setTitleColor(UserAgreementActivity.this);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        tvRight.setVisibility(View.GONE);
        title.setText("用户协议");
        /**
         * 通过此WebView 获取到 WebSettings ，通过WebSettings设置WebView
         */
        WebSettings webSettings = wab.getSettings();

        /**
         * 设置支持JavaScript激活，可用等
         */
        webSettings.setJavaScriptEnabled(true);

        /**
         * 设置自身浏览器，注意：可用把WebView理解为浏览器，设置new WebViewClient()后，手机就不会跳转其他的浏览器
         */
        wab.setWebViewClient(new WebViewClient());

        /**
         * addJavascriptInterface是添加(给js调用-->Java方法)
         * JSHook里面的方法 就是给JavaScript调用的;
         * androidCallbackAction是JavaScript/HTML/H5那边定义定义的标识，所以必须和JavaScript/HTML/H5那边定义标识一致
         */

        wab.loadUrl(str);
    }

    @Override
    protected BasePresenter initPresenter() {
        return null;
    }
}
