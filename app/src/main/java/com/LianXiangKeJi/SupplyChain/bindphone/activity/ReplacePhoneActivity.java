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
import com.LianXiangKeJi.SupplyChain.bindphone.bean.ReplactPhoneBean;
import com.LianXiangKeJi.SupplyChain.common.bean.GetPhoneCodeBean;
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
 * @ClassName:ReplacePhoneActivity
 * @Author:hmy
 * @Description:java类作用描述
 */
public class ReplacePhoneActivity extends BaseAvtivity implements View.OnClickListener {
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
    private String id;

    @Override
    protected int getResId() {
        return R.layout.activity_bind_phone;
    }

    @Override
    protected void getData() {
        setTitleColor(this);
        tvRight.setVisibility(View.GONE);
        back.setOnClickListener(this);
        title.setText("更改手机号");
        btQueding.setOnClickListener(this);
        btGetcode.setOnClickListener(this);

        id = SPUtil.getInstance().getData(ReplacePhoneActivity.this, SPUtil.FILE_NAME, SPUtil.KEY_ID);


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
                //發起綁定手機號請求
                ReplactPhoneBean replactPhoneBean = new ReplactPhoneBean();
                replactPhoneBean.setId(id);
                replactPhoneBean.setPhone(phone);
                replactPhoneBean.setCode(code);

                Gson gson = new Gson();

                String json = gson.toJson(replactPhoneBean);
                RequestBody requestBody = RequestBody.create(MediaType.parse("application/json; charset=utf-8"), json);
                showDialog();
                NetUtils.getInstance().getApis().doUpdatePhone(requestBody)
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(new Observer<UpdateImageBean>() {
                            @Override
                            public void onSubscribe(Disposable d) {

                            }

                            @Override
                            public void onNext(UpdateImageBean updateImageBean) {
                                hideDialog();
                                Toast.makeText(ReplacePhoneActivity.this, ""+updateImageBean.getData(), Toast.LENGTH_SHORT).show();
                                if(updateImageBean.getData().equals("修改成功")){
                                    finish();
                                }
                            }

                            @Override
                            public void onError(Throwable e) {
                                hideDialog();
                                Toast.makeText(ReplacePhoneActivity.this, "更换手机号失败", Toast.LENGTH_SHORT).show();
                            }

                            @Override
                            public void onComplete() {

                            }
                        });
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
                    NetUtils.getInstance().getApis().getPhoneCode("http://192.168.0.143:8081/user/code",phone)
                            .subscribeOn(Schedulers.io())
                            .observeOn(AndroidSchedulers.mainThread())
                            .subscribe(new Observer<GetPhoneCodeBean>() {
                                @Override
                                public void onSubscribe(Disposable d) {

                                }

                                @Override
                                public void onNext(GetPhoneCodeBean getPhoneCodeBean) {
                                    hideDialog();
                                    Toast.makeText(ReplacePhoneActivity.this, ""+getPhoneCodeBean.getData(), Toast.LENGTH_SHORT).show();
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
