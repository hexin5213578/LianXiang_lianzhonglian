package com.LianXiangKeJi.SupplyChain.rememberpwd.activity;

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
import com.LianXiangKeJi.SupplyChain.rememberpwd.bean.ForgetPwdBean;
import com.LianXiangKeJi.SupplyChain.setup.bean.UpdateImageBean;
import com.LianXiangKeJi.SupplyChain.utils.NetUtils;
import com.LianXiangKeJi.SupplyChain.utils.SPUtil;
import com.LianXiangKeJi.SupplyChain.utils.StringUtil;
import com.google.gson.Gson;

import butterknife.BindView;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import okhttp3.MediaType;
import okhttp3.RequestBody;

/**
 * @ClassName:RememberPwdActivity
 * @Author:hmy
 * @Description:java类作用描述
 */
public class RememberPwdActivity extends BaseAvtivity implements View.OnClickListener {
    @BindView(R.id.back)
    ImageView back;
    @BindView(R.id.title)
    TextView title;
    @BindView(R.id.tv_right)
    TextView tvRight;
    @BindView(R.id.et_phone)
    EditText etPhone;
    @BindView(R.id.bt_getcode)
    Button btGetcode;
    @BindView(R.id.et_code)
    EditText etCode;
    @BindView(R.id.et_newpwd)
    EditText etNewpwd;
    @BindView(R.id.bt_queding)
    Button btQueding;
    private CountDownTimer mTimer;
    private String id;

    @Override
    protected int getResId() {
        return R.layout.activity_remember_pwd;
    }

    @Override
    protected void getData() {
        setTitleColor(RememberPwdActivity.this);

        back.setOnClickListener(this);
        tvRight.setVisibility(View.GONE);
        title.setText("忘记密码");
        btGetcode.setOnClickListener(this);
        btQueding.setOnClickListener(this);


        StringUtil.changePwdToCiphertext(etNewpwd);
        id = SPUtil.getInstance().getData(RememberPwdActivity.this, SPUtil.FILE_NAME, SPUtil.KEY_ID);

    }

    @Override
    protected BasePresenter initPresenter() {
        return null;
    }


    @Override
    public void onClick(View view) {
        switch (view.getId()){
            // TODO: 2020/7/18 获取验证码
            case R.id.bt_getcode:
                String phon = etPhone.getText().toString();
                if (StringUtil.checkPhoneNumber(phon)) {
                    //调用倒计时
                    countDownTime();
                    // TODO: 2020/7/28 发起获取验证码的网络请求
                    showDialog();
                    NetUtils.getInstance().getApis().getPhoneCode("http://192.168.0.143:8081/user/code",phon)
                            .subscribeOn(Schedulers.io())
                            .observeOn(AndroidSchedulers.mainThread())
                            .subscribe(new Observer<GetPhoneCodeBean>() {
                                @Override
                                public void onSubscribe(Disposable d) {

                                }

                                @Override
                                public void onNext(GetPhoneCodeBean getPhoneCodeBean) {
                                    hideDialog();
                                    Toast.makeText(RememberPwdActivity.this, ""+getPhoneCodeBean.getData(), Toast.LENGTH_SHORT).show();
                                }

                                @Override
                                public void onError(Throwable e) {

                                }

                                @Override
                                public void onComplete() {

                                }
                            });
                }
                break;
            // TODO: 2020/7/18 确定 拿到输入框的内容 发起更改手机号请求
            case R.id.bt_queding:
                String phone = etPhone.getText().toString();
                String code = etCode.getText().toString();
                String newpwd = etNewpwd.getText().toString();
                if(StringUtil.checkPhoneNumber(phone)){
                    if(StringUtil.checkPassword(newpwd)){
                        if(StringUtil.checkSms(code)){
                            ForgetPwdBean forgetPwdBean = new ForgetPwdBean();
                            forgetPwdBean.setPhone(phone);
                            forgetPwdBean.setCode(code);
                            forgetPwdBean.setPassword(newpwd);

                            Gson gson = new Gson();

                            String json = gson.toJson(forgetPwdBean);
                            RequestBody requestBody = RequestBody.create(MediaType.parse("application/json; charset=utf-8"), json);
                            showDialog();
                            NetUtils.getInstance().getApis().doForgetPassword(requestBody)
                                    .subscribeOn(Schedulers.io())
                                    .observeOn(AndroidSchedulers.mainThread())
                                    .subscribe(new Observer<UpdateImageBean>() {
                                        @Override
                                        public void onSubscribe(Disposable d) {

                                        }

                                        @Override
                                        public void onNext(UpdateImageBean updateImageBean) {
                                            hideDialog();
                                            Toast.makeText(RememberPwdActivity.this, ""+updateImageBean.getData(), Toast.LENGTH_SHORT).show();
                                            if(updateImageBean.getData().equals("修改成功")){
                                                finish();
                                            }
                                        }

                                        @Override
                                        public void onError(Throwable e) {

                                        }

                                        @Override
                                        public void onComplete() {

                                        }
                                    });
                        }
                    }
                }

                break;
            case R.id.back:
                finish();
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
