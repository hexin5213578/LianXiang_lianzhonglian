package com.LianXiangKeJi.SupplyChain.order.activity;

import android.animation.ValueAnimator;
import android.annotation.TargetApi;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.drawable.BitmapDrawable;
import android.os.Build;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.RadioButton;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.LianXiangKeJi.SupplyChain.R;
import com.LianXiangKeJi.SupplyChain.base.BaseAvtivity;
import com.LianXiangKeJi.SupplyChain.base.BasePresenter;
import com.LianXiangKeJi.SupplyChain.common.custom.CustomDialog;
import com.LianXiangKeJi.SupplyChain.utils.SPUtil;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * @ClassName:ConfirmOrderActivity
 * @Author:hmy
 * @Description:java类作用描述  确认订单
 */
public class ConfirmOrderActivity extends BaseAvtivity implements View.OnClickListener {
    @BindView(R.id.back)
    ImageView back;
    @BindView(R.id.title)
    TextView title;
    @BindView(R.id.tv_right)
    TextView tvRight;
    @BindView(R.id.tv_name)
    TextView tvName;
    @BindView(R.id.tv_phone)
    TextView tvPhone;
    @BindView(R.id.tv_address)
    TextView tvAddress;
    @BindView(R.id.rl_select_address)
    RelativeLayout rlSelectAddress;
    @BindView(R.id.rc_order)
    RecyclerView rcOrder;
    @BindView(R.id.rb1)
    RadioButton rb1;
    @BindView(R.id.rb2)
    RadioButton rb2;
    @BindView(R.id.tv_yunfei)
    TextView tvYunfei;
    @BindView(R.id.et_remarks)
    EditText etRemarks;
    @BindView(R.id.tv_count)
    TextView tvCount;
    @BindView(R.id.tv_price)
    TextView tvPrice;
    @BindView(R.id.bt_confirm)
    Button btConfirm;
    @BindView(R.id.iv_havenoaddress)
    ImageView ivHavenoaddress;
    @BindView(R.id.tv_havenoaddress)
    TextView tvHavenoaddress;
    @BindView(R.id.iv_haveaddress)
    ImageView ivHaveaddress;
    private PopupWindow mPopupWindow;

    @Override
    protected int getResId() {
        return R.layout.activity_confirm_order;
    }

    @Override
    protected void getData() {
        setTitleColor(ConfirmOrderActivity.this);

        title.setText("确认订单");
        tvRight.setVisibility(View.GONE);
        back.setOnClickListener(this);
        rlSelectAddress.setOnClickListener(this);
        btConfirm.setOnClickListener(this);


        // TODO: 2020/7/21 展示默认地址
        tvName.setText(SPUtil.getInstance().getData(ConfirmOrderActivity.this, SPUtil.FILE_NAME, SPUtil.USER_NAME));
        tvAddress.setText(SPUtil.getInstance().getData(ConfirmOrderActivity.this, SPUtil.FILE_NAME, SPUtil.KEY_ADDRESS));
        tvPhone.setText(SPUtil.getInstance().getData(ConfirmOrderActivity.this, SPUtil.FILE_NAME, SPUtil.KEY_PHONE));
    }

    @Override
    protected BasePresenter initPresenter() {
        return null;
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.back:
                finish();
                break;
                //选择地址
            case R.id.rl_select_address:

                break;
            // TODO: 2020/7/21 提交订单
            case R.id.bt_confirm:
                String name = tvName.getText().toString();
                if(!TextUtils.isEmpty(name)){
                    showSelect();
                }else{
                    CustomDialog.Builder builder = new CustomDialog.Builder(ConfirmOrderActivity.this);
                    builder.setPositiveButton("去填写", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                            // TODO: 2020/7/21 设置你的操作
                        }
                    });
                    builder.setNegativeButton("取消",
                            new android.content.DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int which) {
                                    dialog.dismiss();
                                }
                            });

                    builder.create().show();
                }
                break;
            default:
                break;
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
      /*  if (!EventBus.getDefault().isRegistered(this)) {
            EventBus.getDefault().register(this);
        }*/
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
       /* if (EventBus.getDefault().isRegistered(this)) {
            EventBus.getDefault().unregister(this);
        }*/
    }

    public void showSelect() {
        //创建popwiondow弹出框
        mPopupWindow = new PopupWindow();
        mPopupWindow.setHeight(ViewGroup.LayoutParams.WRAP_CONTENT);
        mPopupWindow.setWidth(ViewGroup.LayoutParams.MATCH_PARENT);
        View view = LayoutInflater.from(this).inflate(R.layout.dialog_select_pay, null);
        ImageView ivclose = view.findViewById(R.id.iv_close);
        RelativeLayout rl2 = view.findViewById(R.id.rl2);
        RelativeLayout rl3 = view.findViewById(R.id.rl3);
        RadioButton rb1 = view.findViewById(R.id.rb1);
        RadioButton rb2 = view.findViewById(R.id.rb2);
        ImageView check1 = view.findViewById(R.id.pay_check1);
        ImageView check2 = view.findViewById(R.id.pay_check2);
        TextView tvprice = view.findViewById(R.id.tv_price);
        Button startpay = view.findViewById(R.id.startpay);
        ivclose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dismiss();
            }
        });
        rl2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                check1.setVisibility(View.VISIBLE);
                check2.setVisibility(View.GONE);
                rb1.setChecked(true);
                rb2.setChecked(false);
            }
        });
        rl3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                check2.setVisibility(View.VISIBLE);
                check1.setVisibility(View.GONE);
                rb2.setChecked(true);
                rb1.setChecked(false);
            }
        });
        // TODO: 2020/7/21 tvprice赋值

        Intent intent = new Intent(ConfirmOrderActivity.this, ConfirmPaymentActivity.class);
        // TODO: 2020/7/21 立即支付 判断微信支付宝选中状态决定调起哪种支付方式
        startpay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // TODO: 2020/7/21 微信支付
                if (rb1.isChecked()) {
                    Log.d("hmy", "微信支付");
                    intent.putExtra("theway","微信支付");
                    startActivity(intent);
                }
                // TODO: 2020/7/21 支付宝支付
                else {
                    Log.d("hmy", "支付宝支付");
                    intent.putExtra("theway","支付宝支付");
                    startActivity(intent);
                }
            }
        });
        //popwindow设置属性
        mPopupWindow.setAnimationStyle(R.style.popwindow_anim_style);
        mPopupWindow.setContentView(view);
        mPopupWindow.setBackgroundDrawable(new BitmapDrawable());
        mPopupWindow.setFocusable(true);
        mPopupWindow.setOutsideTouchable(true);
        mPopupWindow.setOnDismissListener(new PopupWindow.OnDismissListener() {
            @Override
            public void onDismiss() {
                setWindowAlpa(false);
            }
        });
        show(view);
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        dismiss();
    }

    // TODO: 2020/7/20 设置透明度
    public void setWindowAlpa(boolean isopen) {
        if (Build.VERSION.SDK_INT < 11) {
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


    /**
     * 消失PopupWindow
     */
    public void dismiss() {
        if (mPopupWindow != null && mPopupWindow.isShowing()) {
            mPopupWindow.dismiss();
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.bind(this);
    }
}
