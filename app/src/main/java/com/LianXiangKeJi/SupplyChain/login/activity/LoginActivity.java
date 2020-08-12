package com.LianXiangKeJi.SupplyChain.login.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.LianXiangKeJi.SupplyChain.R;
import com.LianXiangKeJi.SupplyChain.base.BaseAvtivity;
import com.LianXiangKeJi.SupplyChain.base.BasePresenter;
import com.LianXiangKeJi.SupplyChain.common.bean.GetPhoneCodeBean;
import com.LianXiangKeJi.SupplyChain.login.bean.LoginBean;
import com.LianXiangKeJi.SupplyChain.login.bean.LoginPhoneBean;
import com.LianXiangKeJi.SupplyChain.login.bean.LoginSuccessBean;
import com.LianXiangKeJi.SupplyChain.main.activity.MainActivity;
import com.LianXiangKeJi.SupplyChain.protocol.PrivacypolicyActivity;
import com.LianXiangKeJi.SupplyChain.protocol.UserAgreementActivity;
import com.LianXiangKeJi.SupplyChain.regist.activity.RegistActivity;
import com.LianXiangKeJi.SupplyChain.rememberpwd.activity.RememberPwdActivity;
import com.LianXiangKeJi.SupplyChain.utils.NetUtils;
import com.LianXiangKeJi.SupplyChain.utils.SPUtil;
import com.LianXiangKeJi.SupplyChain.utils.StringUtil;
import com.google.gson.Gson;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import okhttp3.MediaType;
import okhttp3.RequestBody;

public class LoginActivity extends BaseAvtivity implements View.OnClickListener {

    @BindView(R.id.bg_pwd)
    ImageView bgPwd;
    @BindView(R.id.bg_code)
    ImageView bgCode;
    @BindView(R.id.regist)
    TextView regist;
    @BindView(R.id.login_pwd)
    RadioButton loginPwd;
    @BindView(R.id.login_code)
    RadioButton loginCode;
    @BindView(R.id.et_username)
    EditText etUsername;
    @BindView(R.id.et_pwd)
    EditText etPwd;
    @BindView(R.id.tv_rememberpwd1)
    TextView tvRememberpwd1;
    @BindView(R.id.bt_login_pwd)
    Button btLoginPwd;
    @BindView(R.id.rl_pwd)
    RelativeLayout rlPwd;
    @BindView(R.id.et_username_code)
    EditText etUsernameCode;
    @BindView(R.id.tv_getcode)
    TextView tvGetcode;
    @BindView(R.id.et_code)
    EditText etCode;
    @BindView(R.id.tv_rememberpwd2)
    TextView tvRememberpwd2;
    @BindView(R.id.bt_login_code)
    Button btLoginCode;
    @BindView(R.id.rl_code)
    RelativeLayout rlCode;
    @BindView(R.id.tv_yonghuxieyi)
    TextView tvYonghuxieyi;
    @BindView(R.id.tv_yinsizhengce)
    TextView tvYinsizhengce;
    @BindView(R.id.l1)
    LinearLayout l1;
    @BindView(R.id.tv_yonghuxieyi2)
    TextView tvYonghuxieyi2;
    @BindView(R.id.tv_yinsizhengce2)
    TextView tvYinsizhengce2;
    @BindView(R.id.l2)
    LinearLayout l2;
    private CountDownTimer mTimer;

    @Override
    protected int getResId() {
        return R.layout.activity_login;
    }

    @Override
    protected void getData() {
        loginPwd.setOnClickListener(this);
        loginCode.setOnClickListener(this);
        regist.setOnClickListener(this);

        //登錄按鈕

        btLoginPwd.setOnClickListener(this);
        btLoginCode.setOnClickListener(this);

        //忘記密碼
        tvRememberpwd1.setOnClickListener(this);
        tvRememberpwd2.setOnClickListener(this);

        //獲取驗證碼
        tvGetcode.setOnClickListener(this);
        setTitleColor(this);

        //用户协议 保密协议
        tvYonghuxieyi.setOnClickListener(this);
        tvYonghuxieyi2.setOnClickListener(this);
        tvYinsizhengce.setOnClickListener(this);
        tvYinsizhengce2.setOnClickListener(this);
        StringUtil.changePwdToCiphertext(etPwd);
    }

    @Override
    protected BasePresenter initPresenter() {
        return null;
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.login_pwd:
                bgPwd.setVisibility(View.VISIBLE);
                bgCode.setVisibility(View.GONE);
                rlPwd.setVisibility(View.VISIBLE);
                rlCode.setVisibility(View.GONE);
                l1.setVisibility(View.VISIBLE);
                l2.setVisibility(View.GONE);
                break;
            case R.id.login_code:
                bgPwd.setVisibility(View.GONE);
                bgCode.setVisibility(View.VISIBLE);
                rlPwd.setVisibility(View.GONE);
                rlCode.setVisibility(View.VISIBLE);
                l1.setVisibility(View.GONE);
                l2.setVisibility(View.VISIBLE);
                break;
            case R.id.regist:
                startActivity(new Intent(LoginActivity.this, RegistActivity.class));

                break;
            // 用戶名密碼登陸
            case R.id.bt_login_pwd:
                //  獲取用戶名及密碼
                String username = etUsername.getText().toString();
                String pwd = etPwd.getText().toString();

                LoginBean loginBean = new LoginBean();
                loginBean.setPhone(username);
                loginBean.setPassword(pwd);

                Gson gson = new Gson();
                String json = gson.toJson(loginBean);
                RequestBody requestBody = RequestBody.create(MediaType.parse("application/json; charset=utf-8"), json);

                if (StringUtil.checkPhoneNumber(username)) {
                    if (StringUtil.checkPassword(pwd)) {
                        showDialog();
                        NetUtils.getInstance().getApis().doPwdLogin(requestBody)
                                .subscribeOn(Schedulers.io())
                                .observeOn(AndroidSchedulers.mainThread())
                                .subscribe(new Observer<LoginSuccessBean>() {
                                    @Override
                                    public void onSubscribe(Disposable d) {

                                    }

                                    @Override
                                    public void onNext(LoginSuccessBean bean) {
                                        hideDialog();

                                        if (bean.getMessage().equals("success")) {
                                            startActivity(new Intent(LoginActivity.this, MainActivity.class));
                                            finish();
                                            Toast.makeText(LoginActivity.this, "登陆成功", Toast.LENGTH_SHORT).show();

                                            LoginSuccessBean.DataBean data = bean.getData();
                                            //将登录后的用户信息存储
                                            SPUtil.getInstance().saveData(LoginActivity.this, SPUtil.FILE_NAME, SPUtil.KEY_TOKEN, data.getToken());
                                            SPUtil.getInstance().saveData(LoginActivity.this, SPUtil.FILE_NAME, SPUtil.HEAD_URL, data.getHeadUrl());
                                            SPUtil.getInstance().saveData(LoginActivity.this, SPUtil.FILE_NAME, SPUtil.USER_NAME, data.getUsername());
                                            SPUtil.getInstance().saveData(LoginActivity.this, SPUtil.FILE_NAME, SPUtil.CHECK_STATUS, data.getCheckStatus() + "");
                                            SPUtil.getInstance().saveData(LoginActivity.this, SPUtil.FILE_NAME, SPUtil.KEY_ID, data.getId());
                                            SPUtil.getInstance().saveData(LoginActivity.this, SPUtil.FILE_NAME, SPUtil.KEY_ADDRESS, data.getAddress());
                                            SPUtil.getInstance().saveData(LoginActivity.this, SPUtil.FILE_NAME, SPUtil.KEY_SCOPE, data.getScope());
                                            SPUtil.getInstance().saveData(LoginActivity.this, SPUtil.FILE_NAME, SPUtil.KEY_PHONE, data.getPhone());
                                        }
                                    }

                                    @Override
                                    public void onError(Throwable e) {
                                        hideDialog();
                                        Toast.makeText(LoginActivity.this, "没有此用户,可能账号或密码错误", Toast.LENGTH_SHORT).show();
                                    }

                                    @Override
                                    public void onComplete() {

                                    }
                                });

                    }
                }

                break;
            // 驗證碼登陸
            case R.id.bt_login_code:
                // 獲取手機號及驗證碼
                String codeusername = etUsernameCode.getText().toString();
                String code = etCode.getText().toString();
                if (StringUtil.checkPhoneNumber(codeusername)) {
                    if (!TextUtils.isEmpty(code)) {
                        LoginPhoneBean loginPhoneBean = new LoginPhoneBean();
                        loginPhoneBean.setPhone(codeusername);
                        loginPhoneBean.setCode(code);
                        Gson gson1 = new Gson();
                        String json1 = gson1.toJson(loginPhoneBean);
                        RequestBody requestBody1 = RequestBody.create(MediaType.parse("application/json; charset=utf-8"), json1);
                        showDialog();
                        NetUtils.getInstance().getApis().doCodeLogin(requestBody1)
                                .subscribeOn(Schedulers.io())
                                .observeOn(AndroidSchedulers.mainThread())
                                .subscribe(new Observer<LoginSuccessBean>() {
                                    @Override
                                    public void onSubscribe(Disposable d) {

                                    }

                                    @Override
                                    public void onNext(LoginSuccessBean bean) {
                                        hideDialog();
                                        if (bean.getMessage().equals("success")) {
                                            startActivity(new Intent(LoginActivity.this, MainActivity.class));
                                            finish();
                                            Toast.makeText(LoginActivity.this, "登陆成功", Toast.LENGTH_SHORT).show();
                                            LoginSuccessBean.DataBean data = bean.getData();
                                            //将登录后的用户信息存储
                                            SPUtil.getInstance().saveData(LoginActivity.this, SPUtil.FILE_NAME, SPUtil.KEY_TOKEN, data.getToken());
                                            SPUtil.getInstance().saveData(LoginActivity.this, SPUtil.FILE_NAME, SPUtil.HEAD_URL, data.getHeadUrl());
                                            SPUtil.getInstance().saveData(LoginActivity.this, SPUtil.FILE_NAME, SPUtil.USER_NAME, data.getUsername());
                                            SPUtil.getInstance().saveData(LoginActivity.this, SPUtil.FILE_NAME, SPUtil.CHECK_STATUS, data.getCheckStatus() + "");
                                            SPUtil.getInstance().saveData(LoginActivity.this, SPUtil.FILE_NAME, SPUtil.KEY_ID, data.getId());
                                            SPUtil.getInstance().saveData(LoginActivity.this, SPUtil.FILE_NAME, SPUtil.KEY_ADDRESS, data.getAddress());
                                            SPUtil.getInstance().saveData(LoginActivity.this, SPUtil.FILE_NAME, SPUtil.KEY_SCOPE, data.getScope());
                                            SPUtil.getInstance().saveData(LoginActivity.this, SPUtil.FILE_NAME, SPUtil.KEY_PHONE, data.getPhone());
                                        }
                                    }

                                    @Override
                                    public void onError(Throwable e) {
                                        hideDialog();
                                        Toast.makeText(LoginActivity.this, "没有此验证码可能已经过期", Toast.LENGTH_SHORT).show();
                                    }

                                    @Override
                                    public void onComplete() {

                                    }
                                });

                    } else {
                        Toast.makeText(this, "验证码不能为空", Toast.LENGTH_SHORT).show();
                    }
                }
                break;
            // 忘記密碼
            case R.id.tv_rememberpwd1:
            case R.id.tv_rememberpwd2:
                startActivity(new Intent(LoginActivity.this, RememberPwdActivity.class));
                break;
            // 發起獲取二維碼請求
            case R.id.tv_getcode:
                String phone = etUsernameCode.getText().toString();
                if (StringUtil.checkPhoneNumber(phone)) {
                    //调用倒计时
                    countDownTime();
                    // 发起获取验证码的网络请求
                    showDialog();
                    NetUtils.getInstance().getApis().getPhoneCode("http://192.168.0.143:8081/user/code", phone)
                            .subscribeOn(Schedulers.io())
                            .observeOn(AndroidSchedulers.mainThread())
                            .subscribe(new Observer<GetPhoneCodeBean>() {
                                @Override
                                public void onSubscribe(Disposable d) {

                                }

                                @Override
                                public void onNext(GetPhoneCodeBean getPhoneCodeBean) {
                                    hideDialog();
                                    Toast.makeText(LoginActivity.this, "" + getPhoneCodeBean.getData(), Toast.LENGTH_SHORT).show();
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
            case R.id.tv_yonghuxieyi:
            case R.id.tv_yonghuxieyi2:
                startActivity(new Intent(LoginActivity.this, UserAgreementActivity.class));
                break;
            case R.id.tv_yinsizhengce:
            case R.id.tv_yinsizhengce2:
                startActivity(new Intent(LoginActivity.this, PrivacypolicyActivity.class));
                break;
        }
    }

    private void countDownTime() {
        //用安卓自带的CountDownTimer实现
        mTimer = new CountDownTimer(60 * 1000, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                tvGetcode.setText(millisUntilFinished / 1000 + "秒后重发");
            }

            @Override
            public void onFinish() {
                tvGetcode.setEnabled(true);
                tvGetcode.setText("发送验证码");
                cancel();
            }
        };
        mTimer.start();
        tvGetcode.setEnabled(false);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        //界面关闭销毁倒计时
        if (mTimer != null) {
            mTimer.cancel();

        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.bind(this);
    }
}