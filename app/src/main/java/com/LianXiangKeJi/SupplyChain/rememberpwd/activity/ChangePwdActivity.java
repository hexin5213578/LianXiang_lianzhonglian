package com.LianXiangKeJi.SupplyChain.rememberpwd.activity;

import android.os.Bundle;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.LianXiangKeJi.SupplyChain.R;
import com.LianXiangKeJi.SupplyChain.base.BaseAvtivity;
import com.LianXiangKeJi.SupplyChain.base.BasePresenter;
import com.LianXiangKeJi.SupplyChain.base.Common;
import com.LianXiangKeJi.SupplyChain.recommend.acitivty.RecommendActivity;
import com.LianXiangKeJi.SupplyChain.rememberpwd.bean.ChangePwdBean;
import com.LianXiangKeJi.SupplyChain.setup.bean.UpdateImageBean;
import com.LianXiangKeJi.SupplyChain.utils.NetUtils;
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

/**
 * @ClassName:RememberPwdActivity
 * @Author:hmy
 * @Description:java类作用描述
 */
public class ChangePwdActivity extends BaseAvtivity implements View.OnClickListener {
    @BindView(R.id.back)
    ImageView back;
    @BindView(R.id.title)
    TextView title;
    @BindView(R.id.tv_right)
    TextView tvRight;

    @BindView(R.id.bt_queding)
    Button btQueding;
    @BindView(R.id.et_old_pwd)
    EditText etOldPwd;
    @BindView(R.id.et_new_pwd1)
    EditText etNewPwd1;
    @BindView(R.id.et_new_pwd2)
    EditText etNewPwd2;
    private String token;

    @Override
    protected int getResId() {
        return R.layout.activity_change_pwd;
    }

    @Override
    protected void getData() {
        setTitleColor(ChangePwdActivity.this);

        back.setOnClickListener(this);
        tvRight.setVisibility(View.GONE);
        title.setText("修改密码");
        btQueding.setOnClickListener(this);

        StringUtil.changePwdToCiphertext(etOldPwd);
        StringUtil.changePwdToCiphertext(etNewPwd1);
        StringUtil.changePwdToCiphertext(etNewPwd2);

        token = Common.getToken();
    }

    @Override
    protected BasePresenter initPresenter() {
        return null;
    }


    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            // TODO: 2020/7/18 确定 拿到输入框的内容 发起更改手机号请求
            case R.id.bt_queding:
                String oldpwd = etOldPwd.getText().toString();
                String newpwd1 = etNewPwd1.getText().toString();
                String newpwd2 = etNewPwd2.getText().toString();
                if(StringUtil.checkPassword(newpwd1) && StringUtil.checkPassword(newpwd2)){
                    if(StringUtil.checkPassword(oldpwd)){
                        if(newpwd1.equals(newpwd2)){
                            ChangePwdBean changePwdBean = new ChangePwdBean();
                            changePwdBean.setPassword(newpwd1);
                            Gson gson = new Gson();

                            String json = gson.toJson(changePwdBean);
                            RequestBody requestBody = RequestBody.create(MediaType.parse("application/json; charset=utf-8"), json);
                            showDialog();
                            //发起修改密码的请求
                            NetUtils.getInstance().getApis().doUpdatePassword(requestBody)
                                    .subscribeOn(Schedulers.io())
                                    .observeOn(AndroidSchedulers.mainThread())
                                    .subscribe(new Observer<UpdateImageBean>() {
                                        @Override
                                        public void onSubscribe(Disposable d) {

                                        }

                                        @Override
                                        public void onNext(UpdateImageBean updateImageBean) {
                                            Toast.makeText(ChangePwdActivity.this, ""+updateImageBean.getData(), Toast.LENGTH_SHORT).show();
                                           if(updateImageBean.getData().equals("修改成功")){
                                               finish();
                                           }
                                            hideDialog();
                                        }

                                        @Override
                                        public void onError(Throwable e) {

                                        }

                                        @Override
                                        public void onComplete() {

                                        }
                                    });

                        }else{
                            Toast.makeText(this, "两次密码输入不一致", Toast.LENGTH_SHORT).show();
                        }
                    }
                }
                break;
            case R.id.back:
                finish();
                break;
        }
    }

}
