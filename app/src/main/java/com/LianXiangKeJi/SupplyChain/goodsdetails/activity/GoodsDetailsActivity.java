package com.LianXiangKeJi.SupplyChain.goodsdetails.activity;

import android.animation.ValueAnimator;
import android.annotation.TargetApi;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.drawable.BitmapDrawable;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Parcelable;
import android.text.TextUtils;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import com.LianXiangKeJi.SupplyChain.R;
import com.LianXiangKeJi.SupplyChain.base.BaseAvtivity;
import com.LianXiangKeJi.SupplyChain.base.BasePresenter;
import com.LianXiangKeJi.SupplyChain.base.Common;
import com.LianXiangKeJi.SupplyChain.common.bean.OrderBean;
import com.LianXiangKeJi.SupplyChain.goodsdetails.bean.GoodsDeatailsBean;
import com.LianXiangKeJi.SupplyChain.main.bean.SaveShopCarBean;
import com.LianXiangKeJi.SupplyChain.main.bean.ShopCarBean;
import com.LianXiangKeJi.SupplyChain.order.activity.ConfirmOrderActivity;
import com.LianXiangKeJi.SupplyChain.paysuccess.bean.IntentBean;
import com.LianXiangKeJi.SupplyChain.recommend.adapter.HotsellAdapter;
import com.LianXiangKeJi.SupplyChain.utils.NetUtils;
import com.LianXiangKeJi.SupplyChain.utils.SPUtil;
import com.LianXiangKeJi.SupplyChain.utils.StringUtil;
import com.bumptech.glide.Glide;
import com.google.gson.Gson;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.io.Serializable;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import okhttp3.MediaType;
import okhttp3.RequestBody;

/**
 * @ClassName:GoodsDetailsActivity
 * @Author:hmy
 * @Description:java类作用描述 商品详情页
 */
public class GoodsDetailsActivity extends BaseAvtivity implements View.OnClickListener {
    @BindView(R.id.iv_good_image)
    ImageView ivGoodImage;
    @BindView(R.id.back)
    ImageView back;
    @BindView(R.id.tv_goods_title)
    TextView tvGoodsTitle;
    @BindView(R.id.tv_goods_money)
    TextView tvGoodsMoney;
    @BindView(R.id.tv_goods_yuanjia)
    TextView tvGoodsYuanjia;
    @BindView(R.id.tv_goods_yunfei)
    TextView tvGoodsYunfei;
    @BindView(R.id.tv_goods_xiaoliang)
    TextView tvGoodsXiaoliang;
    @BindView(R.id.tv_goods_kucun)
    TextView tvGoodsKucun;
    @BindView(R.id.bt_join)
    Button btJoin;
    @BindView(R.id.bt_buy)
    Button btBuy;
    private PopupWindow mPopupWindow;
    private PopupWindow mPopupWindow1;

    int count = 1;
    String str = "";
    private String token;
    int Titlecolor = 0;
    private View view1;
    private View view2;
    private String image;
    private GoodsDeatailsBean bean;
    private double allprice;

    @Override
    protected int getResId() {
        return R.layout.activity_goods_details;
    }
    @Override
    protected void onResume() {
        super.onResume();
        if (!EventBus.getDefault().isRegistered(this)) {
            EventBus.getDefault().register(this);
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (EventBus.getDefault().isRegistered(this)) {
            EventBus.getDefault().unregister(this);
        }
    }
    @Override
    protected void getData() {
        token = Common.getToken();
        Intent intent = getIntent();
        bean = (GoodsDeatailsBean) intent.getSerializableExtra("goods");
        if(bean !=null){
            image = bean.getImage();
            tvGoodsTitle.setText(bean.getName());
            if(bean.getPrice().equals("？")){
                tvGoodsMoney.setText("¥ "+ bean.getPrice());
            }else{
                tvGoodsMoney.setText("¥ "+ StringUtil.round(bean.getPrice()));
            }
            if(bean.getStock()!=null && !bean.getStock().equals("null")){
                tvGoodsKucun.setText("库存"+ bean.getStock()+"件");
            }else{
                tvGoodsKucun.setVisibility(View.INVISIBLE);
                tvGoodsYunfei.setVisibility(View.INVISIBLE);
            }
            if(bean.getMonthsell()!=null && !bean.getMonthsell().equals("null") && Integer.valueOf(bean.getMonthsell())!=0){
                tvGoodsXiaoliang.setText("月销:"+ bean.getMonthsell()+"件");
            }else{
                tvGoodsXiaoliang.setVisibility(View.INVISIBLE);
            }
            Glide.with(GoodsDetailsActivity.this).load(image).into(ivGoodImage);

            if(bean.getOld_price()!=null){
                tvGoodsYuanjia.getPaint().setFlags(Paint.STRIKE_THRU_TEXT_FLAG); //中划线
                tvGoodsYuanjia.setText(bean.getOld_price());
            }else{
                tvGoodsYuanjia.setVisibility(View.INVISIBLE);
            }
        }

        //加载网络图片
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Bitmap myBitmap = Glide.with(GoodsDetailsActivity.this)
                            .asBitmap()
                            .load(image)//必须
                            .into(500, 500)
                            .get();
                    ArrayList<Integer> picturePixel = Common.getPicturePixel(myBitmap);
                    //计数相同颜色数量并保存
                    HashMap<Integer,Integer> color2=new HashMap<>();
                    for (Integer color:picturePixel){
                        if (color2.containsKey(color)){
                            Integer integer = color2.get(color);
                            integer++;
                            color2.remove(color);
                            color2.put(color,integer);
                        }else{
                            color2.put(color,1);
                        }
                    }
                    //挑选数量最多的颜色
                    Iterator iter = color2.entrySet().iterator();
                    int count=0;
                    int color=0;
                    while (iter.hasNext()) {
                        Map.Entry entry = (Map.Entry) iter.next();
                        int value = (int) entry.getValue();
                        if (count<value){
                            count=value;
                            color= (int) entry.getKey();
                        }
                    }
                    Titlecolor = color;
                    if (color==0){
                        return;
                    }
                    GoodsDetailsActivity.this.runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            //设置顶部状态栏背景

                            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP){
                                Window window = GoodsDetailsActivity.this.getWindow();
                                window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
                                window.setStatusBarColor(Titlecolor);
                            }
                        }
                    });
                } catch (Exception e) {
                    e.printStackTrace();
                    Log.d("hmy",e.getMessage());
                }
            }
        }).start();




        back.setOnClickListener(this);
        btJoin.setOnClickListener(this);
        btBuy.setOnClickListener(this);

    }

    @Override
    protected BasePresenter initPresenter() {
        return null;
    }
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void getIntent(IntentBean bean) {
        if (bean.getStr().equals("关闭")) {
            finish();
        }
    }
    @Override
    public void onClick(View view) {
        switch (view.getId()){
            //结束当前页
            case R.id.back:
                finish();
                break;
            //加入购物车
            case R.id.bt_join:
                //立即支付
            case R.id.bt_buy:
                if(!TextUtils.isEmpty(token)){
                    showSelect();
                }else{
                    Toast.makeText(this, "请先登录", Toast.LENGTH_SHORT).show();
                }


                break;
        }
    }

    //重新进入时隐藏弹出框
    @Override
    protected void onRestart() {
        super.onRestart();
        dismiss();
    }

    //弹出选择规格
    public void showSelect() {
        //创建popwiondow弹出框
        mPopupWindow = new PopupWindow();
        mPopupWindow.setHeight(ViewGroup.LayoutParams.WRAP_CONTENT);
        mPopupWindow.setWidth(ViewGroup.LayoutParams.MATCH_PARENT);
        view1 = LayoutInflater.from(this).inflate(R.layout.dailog_select_spec,null);

        //找控件处理操作
        ImageView iv_touxiang = view1.findViewById(R.id.iv_touxiang);
        TextView tv_name  = view1.findViewById(R.id.tv_name);
        ImageView iv_close = view1.findViewById(R.id.iv_close);
        TextView tv_guige = view1.findViewById(R.id.tv_guige);
        TextView tv_price = view1.findViewById(R.id.tv_price);
        LinearLayout iv_jia = view1.findViewById(R.id.iv_jia);
        LinearLayout iv_jian = view1.findViewById(R.id.iv_jian);
        RadioButton rb1 = view1.findViewById(R.id.rb1);
        RadioButton rb2 = view1.findViewById(R.id.rb2);
        TextView tv_count = view1.findViewById(R.id.tv_count);
        TextView tv_count_change = view1.findViewById(R.id.tv_count_change);
        TextView tv_allprice = view1.findViewById(R.id.tv_allprice);
        Button bt_join = view1.findViewById(R.id.bt_join);
        Button bt_buy = view1.findViewById(R.id.bt_buy);


        if(TextUtils.isEmpty(token)){


        }else{
            tv_name.setText(bean.getName());
            tv_guige.setText(bean.getSpec());
            tv_price.setText("￥"+StringUtil.round(bean.getPrice()));
            Glide.with(GoodsDetailsActivity.this).load(bean.getImage()).into(iv_touxiang);

            tv_count.setText(count+"");
            tv_count_change.setText(count+"");
            //计算总价
            String s = tv_price.getText().toString();
            String substring = s.substring(1);
            float price = Float.parseFloat(substring);

            allprice = count*price;
            DecimalFormat df = new DecimalFormat("#.00");
            df.format((float) allprice);
            tv_allprice.setText("¥"+df.format((float) allprice));

        }
     
        iv_close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dismiss();
            }
        });
        iv_jia.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                count++;
                tv_count.setText(count+"");
                tv_count_change.setText(count+"");
                //计算总价
                String s = tv_price.getText().toString();
                String substring = s.substring(1);
                float price = Float.parseFloat(substring);

                 allprice = count*price;
                DecimalFormat df = new DecimalFormat("#.00");
                df.format((float) allprice);
                tv_allprice.setText("¥"+df.format((float) allprice));
            }
        });
        iv_jian.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(count==1){
                    Toast.makeText(GoodsDetailsActivity.this, "数量不能为0", Toast.LENGTH_SHORT).show();
                }else{
                    count--;
                    tv_count.setText(count+"");
                    tv_count_change.setText(count+"");
                    //计算总价
                    String s = tv_price.getText().toString();
                    String substring = s.substring(1);
                    float price = Float.parseFloat(substring);

                     allprice = count*price;
                    DecimalFormat df = new DecimalFormat("#.00");
                    df.format((float) allprice);
                    tv_allprice.setText("¥"+df.format((float) allprice));
                }
            }
        });
        //加入进货单
        bt_join.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //处理选择配送方式
                boolean checked = rb1.isChecked();
                boolean checked1 = rb2.isChecked();
                if(checked){
                    str=rb1.getText().toString();
                }
                if(checked1){
                    str=rb2.getText().toString();
                }

                //调用加入购物车数据
                if (TextUtils.isEmpty(token)){
                    Toast.makeText(GoodsDetailsActivity.this, "请先登录", Toast.LENGTH_SHORT).show();
                }else{
                    //拿到商品信息 以count为数量加入购物车
                    String id = bean.getId();
                    LinkedHashMap<String, String> map = SPUtil.getMap(GoodsDetailsActivity.this, "goodsid");

                    for (String key : map.keySet()) {
                        if(id.equals(key)){
                            String value = map.get(key);
                            tv_count_change.setText(value);
                        }
                    }

                    map.put(id, String.valueOf(count));

                    List<SaveShopCarBean.ResultBean> shoplist = new ArrayList<>();

                    //遍历集合的键
                    if(map.size()>0) {
                        SaveShopCarBean saveShopCarBean = new SaveShopCarBean();
                        saveShopCarBean.setState(false);
                        for (Map.Entry<String, String> entry : map.entrySet()) {
                            String key = entry.getKey();
                            SaveShopCarBean.ResultBean resultBean = new SaveShopCarBean.ResultBean();
                            resultBean.setShopGoodsId(key);
                            shoplist.add(resultBean);
                        }
                        saveShopCarBean.setShoppingCartList(shoplist);

                        Gson gson = new Gson();
                        String json = gson.toJson(saveShopCarBean);

                        RequestBody requestBody = RequestBody.create(MediaType.parse("application/json; charset=utf-8"), json);
                        NetUtils.getInstance().getApis().doShopCar(requestBody)
                                .subscribeOn(Schedulers.io())
                                .observeOn(AndroidSchedulers.mainThread())
                                .subscribe(new Observer<ShopCarBean>() {
                                    @Override
                                    public void onSubscribe(Disposable d) {

                                    }

                                    @Override
                                    public void onNext(ShopCarBean shopCarBean) {
                                        //添加成功后处理
                                        mPopupWindow1 = new PopupWindow();
                                        mPopupWindow1.setHeight(ViewGroup.LayoutParams.WRAP_CONTENT);
                                        mPopupWindow1.setWidth(ViewGroup.LayoutParams.MATCH_PARENT);
                                        view2 = LayoutInflater.from(GoodsDetailsActivity.this).inflate(R.layout.dialog_addshopcar_success,null);
                                        //popwindow设置属性
                                        mPopupWindow1.setContentView(view2);
                                        mPopupWindow1.setBackgroundDrawable(new BitmapDrawable());
                                        mPopupWindow1.setFocusable(true);
                                        mPopupWindow1.setOutsideTouchable(true);
                                        mPopupWindow1.setOnDismissListener(new PopupWindow.OnDismissListener() {
                                            @Override
                                            public void onDismiss() {
                                                setWindowAlpa(false);
                                            }
                                        });

                                        ((ViewGroup)view1).removeAllViews();

                                        show1(view2);
                                        Handler handler = new Handler();
                                        handler.postDelayed(new Runnable() {
                                            @Override
                                            public void run() {
                                                //要执行的操作
                                                dismiss1();
                                            }
                                        }, 2000);//2秒后执行弹出框消失
                                    }

                                    @Override
                                    public void onError(Throwable e) {

                                    }

                                    @Override
                                    public void onComplete() {

                                    }
                                });
                    }

                    SPUtil.setMap(GoodsDetailsActivity.this,"goodsid",map);

                    //隐藏规格选择框
                    dismiss();


                }
            }
        });
        //立即订购
        bt_buy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (allprice<200){
                    Toast.makeText(GoodsDetailsActivity.this, "结算最低金额为200元", Toast.LENGTH_SHORT).show();
                }else{
                    //处理选择配送方式
                    boolean checked = rb1.isChecked();
                    boolean checked1 = rb2.isChecked();
                    if(checked){
                        str=rb1.getText().toString();
                    }
                    if(checked1){
                        str=rb2.getText().toString();
                    }
                    //跳转至确认订单页  将所选择信息通过集合传递
                    if (TextUtils.isEmpty(token)){
                        Toast.makeText(GoodsDetailsActivity.this, "请先登录", Toast.LENGTH_SHORT).show();
                    }else{
                        Intent intent = new Intent(GoodsDetailsActivity.this, ConfirmOrderActivity.class);
                        OrderBean orderBean = new OrderBean();
                        orderBean.setImageurl(bean.getImage());
                        orderBean.setName(bean.getName());
                        orderBean.setPrice(bean.getPrice());
                        orderBean.setSpecs(bean.getSpec());
                        orderBean.setCount(count);
                        orderBean.setGoodsid(bean.getId());
                        List<OrderBean> list = new ArrayList<>();

                        list.add(orderBean);
                        Bundle bundle = new Bundle();
                        bundle.putSerializable("orderlist", (Serializable) list);
                        intent.putExtra("flag","true");
                        intent.putExtras(bundle);
                        startActivity(intent);
                        dismiss();
                    }
                }
            }
        });
        //popwindow设置属性
        mPopupWindow.setAnimationStyle(R.style.popwindow_anim_style);
        mPopupWindow.setContentView(view1);
        mPopupWindow.setBackgroundDrawable(new BitmapDrawable());
        mPopupWindow.setFocusable(true);
        mPopupWindow.setOutsideTouchable(true);
        mPopupWindow.setOnDismissListener(new PopupWindow.OnDismissListener() {
            @Override
            public void onDismiss() {
                setWindowAlpa(false);
            }
        });
        show(view1);


    }

    //设置透明度
    public void setWindowAlpa(boolean isopen) {
        if (android.os.Build.VERSION.SDK_INT < 11) {
            return;
        }
        final Window window = this.getWindow();
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

    /**
     * 显示PopupWindow
     */
    private void show(View v) {
        if (mPopupWindow != null && !mPopupWindow.isShowing()) {
            mPopupWindow.showAtLocation(v, Gravity.BOTTOM, 0, 0);
        }
        setWindowAlpa(true);

    }
    private void show1(View v) {
        if (mPopupWindow1 != null && !mPopupWindow1.isShowing()) {
            mPopupWindow1.showAtLocation(v, Gravity.CENTER_HORIZONTAL, 0, 0);
        }
        setWindowAlpa(true);
    }

    /**
     * 消失PopupWindow
     */
    public void dismiss() {
        if (mPopupWindow != null && mPopupWindow.isShowing()) {
            mPopupWindow.dismiss();
        }
    }
    public void dismiss1() {
        if (mPopupWindow1 != null && mPopupWindow1.isShowing()) {
            mPopupWindow1.dismiss();
        }
    }
}
