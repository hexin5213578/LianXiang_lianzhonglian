package com.LianXiangKeJi.SupplyChain.paysuccess.activity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.LianXiangKeJi.SupplyChain.R;
import com.LianXiangKeJi.SupplyChain.base.BaseAvtivity;
import com.LianXiangKeJi.SupplyChain.base.BasePresenter;
import com.LianXiangKeJi.SupplyChain.common.bean.OrderBean;
import com.LianXiangKeJi.SupplyChain.order.adapter.AllOrderAdapter;
import com.LianXiangKeJi.SupplyChain.order.adapter.OrderInfoAdapter;
import com.LianXiangKeJi.SupplyChain.order.bean.ConfirmGetGoodsBean;
import com.LianXiangKeJi.SupplyChain.order.bean.SaveOrdersidBean;
import com.LianXiangKeJi.SupplyChain.utils.NetUtils;
import com.LianXiangKeJi.SupplyChain.utils.SPUtil;
import com.google.gson.Gson;

import org.greenrobot.eventbus.EventBus;

import java.text.SimpleDateFormat;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import okhttp3.MediaType;
import okhttp3.RequestBody;

/**
 * @ClassName:OrderShippedActivity
 * @Author:hmy
 * @Description:java类作用描述 待收货订单详情
 */
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
    private String orderid;

    @Override
    protected int getResId() {
        return R.layout.activity_order_shipped;
    }

    @Override
    protected void getData() {
        back.setOnClickListener(this);
        btConfirm.setOnClickListener(this);
        setTitleColor(OrderShippedActivity.this);
        title.setText("订单详情");
        tvRight.setVisibility(View.GONE);

        //展示默认地址

        tvName.setText(SPUtil.getInstance().getData(OrderShippedActivity.this, SPUtil.FILE_NAME, SPUtil.USER_NAME));
        tvAddress.setText(SPUtil.getInstance().getData(OrderShippedActivity.this, SPUtil.FILE_NAME, SPUtil.KEY_ADDRESS));
        tvPhone.setText(SPUtil.getInstance().getData(OrderShippedActivity.this, SPUtil.FILE_NAME, SPUtil.KEY_PHONE));
        Intent intent = getIntent();


        //获取订单信息
        String theway = intent.getStringExtra("theway");
        orderid = intent.getStringExtra("orderid");
        long time = intent.getLongExtra("time", 0);

        Bundle bundle = intent.getExtras();
        //获取订单中的商品
        List<OrderBean> orderlist = (List<OrderBean>) bundle.getSerializable("orderlist");

        String date = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(
                new java.util.Date(time));
        tvTime1.setText(date);
        tvTime2.setText(date);
        tvTheway.setText(theway);
        tvOrderNumber.setText(orderid);

        LinearLayoutManager manager = new LinearLayoutManager(OrderShippedActivity.this, RecyclerView.VERTICAL, false);
        rcOrderList.setLayoutManager(manager);
        OrderInfoAdapter orderInfoAdapter = new OrderInfoAdapter(OrderShippedActivity.this, orderlist);
        rcOrderList.setAdapter(orderInfoAdapter);

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
                SaveOrdersidBean saveOrdersidBean = new SaveOrdersidBean();
                saveOrdersidBean.setId(orderid);
                Gson gson = new Gson();
                String json = gson.toJson(saveOrdersidBean);

                RequestBody requestBody = RequestBody.create(MediaType.parse("application/json; charset=utf-8"), json);

                AlertDialog.Builder builder = new AlertDialog.Builder(OrderShippedActivity.this)
                        .setMessage("是否确认收货").setPositiveButton("确定", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                showDialog();
                                NetUtils.getInstance().getApis().doConfirmGetGoods(requestBody)
                                        .subscribeOn(Schedulers.io())
                                        .observeOn(AndroidSchedulers.mainThread())
                                        .subscribe(new Observer<ConfirmGetGoodsBean>() {
                                            @Override
                                            public void onSubscribe(Disposable d) {

                                            }

                                            @Override
                                            public void onNext(ConfirmGetGoodsBean confirmGetGoodsBean) {
                                                hideDialog();
                                                Toast.makeText(OrderShippedActivity.this, "" + confirmGetGoodsBean.getData(), Toast.LENGTH_SHORT).show();
                                                EventBus.getDefault().post("刷新界面");
                                                finish();
                                            }

                                            @Override
                                            public void onError(Throwable e) {

                                            }

                                            @Override
                                            public void onComplete() {

                                            }
                                        });
                            }
                        }).setNegativeButton("取消", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                //你想做的事情
                                dialogInterface.dismiss();
                            }
                        });
                builder.create().show();
                break;
        }
    }
}
