package com.LianXiangKeJi.SupplyChain.bindphone.activity;

import android.os.CountDownTimer;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.LianXiangKeJi.SupplyChain.R;
import com.LianXiangKeJi.SupplyChain.base.BaseAvtivity;
import com.LianXiangKeJi.SupplyChain.base.BasePresenter;
import com.LianXiangKeJi.SupplyChain.common.bean.GetPhoneCodeBean;
import com.LianXiangKeJi.SupplyChain.utils.NetUtils;
import com.LianXiangKeJi.SupplyChain.utils.StringUtil;

import butterknife.BindView;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

/**
 * @ClassName:ReplacePhoneActivity
 * @Author:hmy
 * @Description:java类作用描述
 */
public class BindPhoneActivity extends BaseAvtivity implements View.OnClickListener {
    @BindView(R.id.back)
    ImageView back;
    @BindView(R.id.title)
    TextView title;
    @BindView(R.id.tv_right)
    TextView tvRight;
    @BindView(R.id.et_phone)
    EditText etPhone;
    @BindView(R.id.et_code)
    EditText etCode;
    @BindView(R.id.bt_queding)
    Button btQueding;
    @BindView(R.id.bt_getcode)
    Button btGetcode;
    private String phone;
    private CountDownTimer mTimer;

    @Override
    protected int getResId() {
        return R.layout.activity_phone;
    }

    @Override
    protected void getData() {
        setTitleColor(this);
        tvRight.setVisibility(View.GONE);
        back.setOnClickListener(this);
        title.setText("绑定手机号");
        btQueding.setOnClickListener(this);
        btGetcode.setOnClickListener(this);

    }

    @Override
    protected BasePresenter initPresenter() {
        return null;
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.bt_queding:
                phone = etPhone.getText().toString();
                String code = etCode.getText().toString();
                // 發起綁定手機號請求
                break;
            case R.id.back:
                finish();

                break;
            case R.id.bt_getcode:
                phone = etPhone.getText().toString();
                if(StringUtil.checkPhoneNumber(phone)){
                    //调用倒计时
                    countDownTime();
                    //发起获取验证码的网络请求
                    showDialog();
                    NetUtils.getInstance().getApis().getPhoneCode(phone)
                            .subscribeOn(Schedulers.io())
                            .observeOn(AndroidSchedulers.mainThread())
                            .subscribe(new Observer<GetPhoneCodeBean>() {
                                @Override
                                public void onSubscribe(Disposable d) {

                                }

                                @Override
                                public void onNext(GetPhoneCodeBean getPhoneCodeBean) {
                                    hideDialog();
                                    Toast.makeText(BindPhoneActivity.this, ""+getPhoneCodeBean.getData(), Toast.LENGTH_SHORT).show();
                                }

                                @Override
                                public void onError(Throwable e) {
                                    hideDialog();
                                    Toast.makeText(BindPhoneActivity.this, "获取验证码失败", Toast.LENGTH_SHORT).show();
                                }

                                @Override
                                public void onComplete() {

                                }
                            });
                }
                break;
        }
    }
    private void countDownTime() {
        //用安卓自带的CountDownTimer实现
        mTimer = new CountDownTimer(60 * 1000, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                btGetcode.setText(millisUntilFinished / 1000 + "秒后重发");
            }

            @Override
            public void onFinish() {
                btGetcode.setEnabled(true);
                btGetcode.setText("发送验证码");
                cancel();
            }
        };
        mTimer.start();
        btGetcode.setEnabled(false);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if(mTimer!=null){
            mTimer.cancel();

        }
    }
}
