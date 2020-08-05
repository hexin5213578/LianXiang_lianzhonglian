package com.LianXiangKeJi.SupplyChain.wxapi;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.LianXiangKeJi.SupplyChain.paysuccess.activity.PaySuccessActivity;
import com.tencent.mm.opensdk.constants.ConstantsAPI;
import com.tencent.mm.opensdk.modelbase.BaseReq;
import com.tencent.mm.opensdk.modelbase.BaseResp;
import com.tencent.mm.opensdk.openapi.IWXAPI;
import com.tencent.mm.opensdk.openapi.IWXAPIEventHandler;
import com.tencent.mm.opensdk.openapi.WXAPIFactory;

/**
 * @ClassName:WXPayEntryActivity
 * @Author:hmy
 * @Description:java类作用描述
 */
public class WXPayEntryActivity extends Activity implements IWXAPIEventHandler {

    private static final String TAG = "MicroMsg.SDKSample.WXPayEntryActivity";

    private IWXAPI api;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        api = WXAPIFactory.createWXAPI(this, "你的appid");
        api.handleIntent(getIntent(), this);
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        setIntent(intent);
        api.handleIntent(intent, this);
    }

    @Override
    public void onReq(BaseReq req) {
    }

    @Override
    public void onResp(BaseResp resp) {
        Log.d(TAG, "onPayFinish, errCode = " + resp.errCode);

        if(resp.getType() == ConstantsAPI.COMMAND_PAY_BY_WX){
            if (resp.errCode ==0){
                Toast.makeText(this, "支付成功", Toast.LENGTH_SHORT).show();
                // TODO: 2020/7/29 跳转支付成功页
                startActivity(new Intent(WXPayEntryActivity.this, PaySuccessActivity.class));
            }else{
                Toast.makeText(this, "支付失败，请重试", Toast.LENGTH_SHORT).show();
            }
            finish();
        }
    }

}
