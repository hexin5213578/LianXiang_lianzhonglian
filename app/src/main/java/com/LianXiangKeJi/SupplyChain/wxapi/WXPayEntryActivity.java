package com.LianXiangKeJi.SupplyChain.wxapi;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.LianXiangKeJi.SupplyChain.R;
import com.LianXiangKeJi.SupplyChain.base.App;
import com.LianXiangKeJi.SupplyChain.common.bean.OrderBean;
import com.LianXiangKeJi.SupplyChain.order.bean.DeleteOrCancleOrderBean;
import com.LianXiangKeJi.SupplyChain.order.bean.SaveOrderListBean;
import com.LianXiangKeJi.SupplyChain.paysuccess.activity.PaySuccessActivity;
import com.LianXiangKeJi.SupplyChain.utils.NetUtils;
import com.LianXiangKeJi.SupplyChain.utils.SPUtil;
import com.google.gson.Gson;
import com.tencent.mm.opensdk.constants.ConstantsAPI;
import com.tencent.mm.opensdk.modelbase.BaseReq;
import com.tencent.mm.opensdk.modelbase.BaseResp;
import com.tencent.mm.opensdk.openapi.IWXAPI;
import com.tencent.mm.opensdk.openapi.IWXAPIEventHandler;
import com.tencent.mm.opensdk.openapi.WXAPIFactory;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

/**
 * @ClassName:WXPayEntryActivity
 * @Author:hmy
 * @Description:java类作用描述
 */
public class WXPayEntryActivity extends Activity implements IWXAPIEventHandler {
    
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        App.getWXApi().handleIntent(getIntent(), this);
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        setIntent(intent);

        App.getWXApi().handleIntent(intent, this);
    }

    @Override
    public void onReq(BaseReq req) {
    }

    @Override
    public void onResp(BaseResp resp) {

        Log.d("hmy", "onPayFinish, errCode = " + resp.errCode);

        if (resp.getType() == ConstantsAPI.COMMAND_PAY_BY_WX) {
            if(resp.errCode == 0) {
                Toast.makeText(this, "支付成功", Toast.LENGTH_SHORT).show();

                String orderinfo = SPUtil.getInstance().getData(WXPayEntryActivity.this, SPUtil.FILE_NAME, "orderinfo");
                Gson gson = new Gson();
                SaveOrderListBean saveOrderListBean = gson.fromJson(orderinfo, SaveOrderListBean.class);
                String orderid = saveOrderListBean.getOrderid();
                List<OrderBean> orderlist = saveOrderListBean.getOrderlist();
                String theway = saveOrderListBean.getTheway();
                long time = saveOrderListBean.getTime();
                int flag = saveOrderListBean.getFlag();
                //标记为0代表从发起订单支付 跳转到支付成功页
                if(flag==0){
                    //传递数据
                    Intent intent = new Intent(WXPayEntryActivity.this, PaySuccessActivity.class);
                    Bundle bundle = new Bundle();
                    bundle.putSerializable("orderlist", (Serializable) orderlist);
                    intent.putExtras(bundle);
                    intent.putExtra("theway",theway);
                    intent.putExtra("time",time);
                    intent.putExtra("orderid",orderid);
                    startActivity(intent);
                    finish();
                    //标记为1代表从订单页支付 直接关闭回调类
                }else if(flag==1){
                    finish();
                }

            }else if(resp.errCode==-1){
                Toast.makeText(this, "支付失败，请重试", Toast.LENGTH_SHORT).show();
                finish();
            }else{
                Toast.makeText(this, "取消支付", Toast.LENGTH_SHORT).show();
                finish();
            }
        }
    }
}
