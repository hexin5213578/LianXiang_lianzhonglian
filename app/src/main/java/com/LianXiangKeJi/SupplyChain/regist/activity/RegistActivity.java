package com.LianXiangKeJi.SupplyChain.regist.activity;

import android.Manifest;
import android.app.AlertDialog;
import android.content.ComponentName;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.LianXiangKeJi.SupplyChain.BuildConfig;
import com.LianXiangKeJi.SupplyChain.R;
import com.LianXiangKeJi.SupplyChain.base.BaseAvtivity;
import com.LianXiangKeJi.SupplyChain.base.BasePresenter;
import com.LianXiangKeJi.SupplyChain.common.bean.GetPhoneCodeBean;
import com.LianXiangKeJi.SupplyChain.login.activity.LoginActivity;
import com.LianXiangKeJi.SupplyChain.map.activity.MapActivity;
import com.LianXiangKeJi.SupplyChain.regist.bean.RegistLogcationBean;
import com.LianXiangKeJi.SupplyChain.utils.NetUtils;
import com.LianXiangKeJi.SupplyChain.utils.StringUtil;
import com.amap.api.services.core.PoiItem;
import com.bumptech.glide.Glide;
import com.wildma.pictureselector.PictureBean;
import com.wildma.pictureselector.PictureSelector;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import okhttp3.RequestBody;

public class RegistActivity extends BaseAvtivity implements View.OnClickListener {

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
    @BindView(R.id.et_pwd1)
    EditText etPwd1;
    @BindView(R.id.et_pwd2)
    EditText etPwd2;
    @BindView(R.id.rl_mentou)
    RelativeLayout rlMentou;
    @BindView(R.id.rl_yingye)
    RelativeLayout rlYingye;
    @BindView(R.id.bt_regist)
    Button btRegist;
    @BindView(R.id.iv_mentou)
    ImageView ivMentou;
    @BindView(R.id.iv_camera1)
    ImageView ivCamera1;
    @BindView(R.id.tv_mentou)
    TextView tvMentou;
    @BindView(R.id.iv_yingye)
    ImageView ivYingye;
    @BindView(R.id.iv_camera2)
    ImageView ivCamera2;
    @BindView(R.id.tv_yingye)
    TextView tvYingye;
    @BindView(R.id.et_username)
    EditText etUsername;
    @BindView(R.id.et_address)
    EditText etAddress;
    @BindView(R.id.rl_location)
    RelativeLayout rlLocation;
    private String stringExtra;
    private String stringExtra1;
    private CountDownTimer mTimer;
    private File file;
    private File file1;
    private String location;
    @Override
    protected int getResId() {
        return R.layout.activity_regist;
    }

    @Override
    protected void getData() {
        setTitleColor(this);

        back.setVisibility(View.GONE);
        tvRight.setVisibility(View.GONE);
        title.setText("注册");
        btGetcode.setOnClickListener(this);
        rlMentou.setOnClickListener(this);
        rlYingye.setOnClickListener(this);
        btRegist.setOnClickListener(this);
        rlLocation.setOnClickListener(this);

        StringUtil.changePwdToCiphertext(etPwd1);
        StringUtil.changePwdToCiphertext(etPwd2);


    }

    @Override
    protected BasePresenter initPresenter() {
        return null;
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (!EventBus.getDefault().isRegistered(this)) {
            EventBus.getDefault().register(this);
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            //获取验证码
            case R.id.bt_getcode:
                //发送延时事件
                String phon = etPhone.getText().toString();
                if (StringUtil.checkPhoneNumber(phon)) {
                    //调用倒计时
                    countDownTime();
                    //发起获取验证码的网络请求
                    showDialog();
                    NetUtils.getInstance().getApis().getPhoneCode("http://192.168.0.143:8081/user/code", phon)
                            .subscribeOn(Schedulers.io())
                            .observeOn(AndroidSchedulers.mainThread())
                            .subscribe(new Observer<GetPhoneCodeBean>() {
                                @Override
                                public void onSubscribe(Disposable d) {

                                }

                                @Override
                                public void onNext(GetPhoneCodeBean getPhoneCodeBean) {
                                    hideDialog();
                                    Toast.makeText(RegistActivity.this, "" + getPhoneCodeBean.getData(), Toast.LENGTH_SHORT).show();
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
            // 门头照
            case R.id.rl_mentou:
                onTakePhoto();
                PictureSelector.create(RegistActivity.this, 100).selectPicture(true);
                break;
            //  店铺营业执照
            case R.id.rl_yingye:
                onTakePhoto();
                PictureSelector.create(RegistActivity.this, 101).selectPicture(false, 500, 300, 200, 200);
                break;
            // 提交
            case R.id.bt_regist:


                String phone = etPhone.getText().toString();
                String code = etCode.getText().toString();
                String pwd1 = etPwd1.getText().toString();
                String pwd2 = etPwd2.getText().toString();
                String address = etAddress.getText().toString();
                String username = etUsername.getText().toString();
                if (StringUtil.checkPhoneNumber(etPhone.getText().toString())) {
                    if (StringUtil.checkPassword(pwd1)) {
                        if (StringUtil.checkPassword(pwd2)) {
                            if (!TextUtils.isEmpty(code)) {
                                if (!TextUtils.isEmpty(stringExtra)) {
                                    if (!TextUtils.isEmpty(stringExtra1)) {
                                        if (pwd1.equals(pwd2)) {
                                            if (!TextUtils.isEmpty(username)) {
                                                if (!TextUtils.isEmpty(address)) {

                                                    AlertDialog.Builder builder = new AlertDialog.Builder(this)
                                                            .setMessage("收货地址填写后不可修改,是否继续提交").setPositiveButton("确定提交", new DialogInterface.OnClickListener() {
                                                                @Override
                                                                public void onClick(DialogInterface dialogInterface, int i) {
                                                                    //发起解析地址的请求
                                                                    showDialog();
                                                                    NetUtils.getInstance().getApis().doRegistLocation("https://restapi.amap.com/v3/geocode/geo", "6002f521fac7009a462a76d33debdd4a", address, "JSON")
                                                                            .subscribeOn(Schedulers.io())
                                                                            .observeOn(AndroidSchedulers.mainThread())
                                                                            .subscribe(new Observer<RegistLogcationBean>() {


                                                                                @Override
                                                                                public void onSubscribe(Disposable d) {

                                                                                }

                                                                                @Override
                                                                                public void onNext(RegistLogcationBean registLogcationBean) {
                                                                                    List<RegistLogcationBean.GeocodesBean> geocodes = registLogcationBean.getGeocodes();
                                                                                    RegistLogcationBean.GeocodesBean geocodesBean = geocodes.get(0);
                                                                                    location = geocodesBean.getLocation();

                                                                                    ArrayList<File> list = new ArrayList<>();
                                                                                    HashMap<String, String> map = new HashMap<>();
                                                                                    map.put("username", username);
                                                                                    map.put("address", address);
                                                                                    map.put("scope", location);
                                                                                    map.put("password", pwd1);
                                                                                    map.put("phone", phone);
                                                                                    map.put("code", code);

                                                                                    list.add(file);
                                                                                    list.add(file1);

                                                                                    RequestBody requsetBody = NetUtils.getInstance().getRequsetBody(list, map);
                                                                                    NetUtils.getInstance().getApis().doRegist(requsetBody)
                                                                                            .subscribeOn(Schedulers.io())
                                                                                            .observeOn(AndroidSchedulers.mainThread())
                                                                                            .subscribe(new Observer<GetPhoneCodeBean>() {
                                                                                                @Override
                                                                                                public void onSubscribe(Disposable d) {

                                                                                                }

                                                                                                @Override
                                                                                                public void onNext(GetPhoneCodeBean getPhoneCodeBean) {
                                                                                                    hideDialog();
                                                                                                    Toast.makeText(RegistActivity.this, getPhoneCodeBean.getData(), Toast.LENGTH_SHORT).show();

                                                                                                    if (getPhoneCodeBean.getData().equals("注册信息已提交")) {
                                                                                                        startActivity(new Intent(RegistActivity.this,LoginActivity.class));
                                                                                                        finish();
                                                                                                    }
                                                                                                }

                                                                                                @Override
                                                                                                public void onError(Throwable e) {
                                                                                                    hideDialog();
                                                                                                    Toast.makeText(RegistActivity.this, "注册失败", Toast.LENGTH_SHORT).show();
                                                                                                }

                                                                                                @Override
                                                                                                public void onComplete() {

                                                                                                }
                                                                                            });
                                                                                }

                                                                                @Override
                                                                                public void onError(Throwable e) {

                                                                                }

                                                                                @Override
                                                                                public void onComplete() {

                                                                                }
                                                                            });

                                                                }
                                                            }).setNegativeButton("前往修改", new DialogInterface.OnClickListener() {
                                                                @Override
                                                                public void onClick(DialogInterface dialogInterface, int i) {
                                                                    //你想做的事情
                                                                    dialogInterface.dismiss();
                                                                }
                                                            });
                                                    builder.create().show();


                                                } else {
                                                    Toast.makeText(this, "店铺地址不能为空", Toast.LENGTH_SHORT).show();
                                                }
                                            } else {
                                                Toast.makeText(this, "请输入店铺名称", Toast.LENGTH_SHORT).show();
                                            }
                                        } else {
                                            Toast.makeText(this, "两次密码输入不一致", Toast.LENGTH_SHORT).show();
                                        }
                                    } else {
                                        Toast.makeText(this, "请上传店铺营业执照", Toast.LENGTH_SHORT).show();
                                    }
                                } else {
                                    Toast.makeText(this, "请上传店铺门头照", Toast.LENGTH_SHORT).show();
                                }
                            } else {
                                Toast.makeText(this, "验证码不能为空", Toast.LENGTH_SHORT).show();
                            }
                        }
                    }
                }

                break;
            case R.id.rl_location:
               /* String sdk = Build.VERSION.SDK; // SDK号
                String model = Build.MODEL; // 手机型号
                String release = Build.VERSION.RELEASE; // android系统版本号
                String brand = Build.BRAND;//手机厂商
                if (Build.VERSION.SDK_INT >= 23) {
                    int request = ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION);
                    if (request != PackageManager.PERMISSION_GRANTED)//缺少权限，进行权限申请
                    {

                        AlertDialog.Builder builder = new AlertDialog.Builder(this)
                                .setMessage("检测到当前未开启定位权限，是否跳转到设置页？").setPositiveButton("确定", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialogInterface, int i) {
                                        if (TextUtils.equals(brand.toLowerCase(), "redmi") || TextUtils.equals(brand.toLowerCase(), "xiaomi")) {
                                            gotoMiuiPermission();//小米
                                        } else if (TextUtils.equals(brand.toLowerCase(), "meizu")) {
                                            gotoMeizuPermission();
                                        } else if (TextUtils.equals(brand.toLowerCase(), "huawei") || TextUtils.equals(brand.toLowerCase(), "honor")) {
                                            gotoHuaweiPermission();
                                        } else {
                                            startActivity(getAppDetailSettingIntent());
                                        }
                                    }
                                }).setNegativeButton("取消", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialogInterface, int i) {
                                        // 你想做的事情
                                        dialogInterface.dismiss();
                                    }
                                });
                        builder.create().show();
                        return;
                    }
                }*/
                Request();


                break;
        }
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void getAddress(PoiItem poiItem) {
        etAddress.setText(poiItem.getCityName() + poiItem.getAdName() + poiItem.getSnippet() + poiItem.getTitle());
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == RESULT_OK && requestCode == 100) {
            if (data != null) {
                PictureBean bean = data.getParcelableExtra(PictureSelector.PICTURE_RESULT);
                stringExtra = bean.getPath();
                file = new File(stringExtra);
                Glide.with(RegistActivity.this).load(stringExtra).into(ivMentou);
                ivCamera1.setVisibility(View.GONE);
                tvMentou.setVisibility(View.GONE);
            }
        }

        if (resultCode == RESULT_OK && requestCode == 101) {
            if (data != null) {
                PictureBean bean = data.getParcelableExtra(PictureSelector.PICTURE_RESULT);
                stringExtra1 = bean.getPath();
                file1 = new File(stringExtra1);

                Glide.with(RegistActivity.this).load(stringExtra1).into(ivYingye);
                ivCamera2.setVisibility(View.GONE);
                tvYingye.setVisibility(View.GONE);
            }
        }
    }
    //安卓10.0定位权限
    public void Request() {
        if (Build.VERSION.SDK_INT >= 23) {
            int request = ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION);
            if (request != PackageManager.PERMISSION_GRANTED)//缺少权限，进行权限申请
            {
                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 100);
                return;//
            } else {
                Intent intent = new Intent(RegistActivity.this, MapActivity.class);
                startActivity(intent);
            }
        } else {

        }
    }
    //开启相机相册动态权限
    public void onTakePhoto() {
        if (Build.VERSION.SDK_INT >= 23) {
            int request = ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA);
            if (request != PackageManager.PERMISSION_GRANTED)//缺少权限，进行权限申请
            {
                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.CAMERA}, 123);
                return;//
            } else {
                //权限同意，不需要处理,去掉用拍照的方法               Toast.makeText(this,"权限同意",Toast.LENGTH_SHORT).show();
            }
        } else {
            //低于23 不需要特殊处理，去掉用拍照的方法
        }
    }

    //参数 requestCode是我们在申请权限的时候使用的唯一的申请码
    //String[] permission则是权限列表，一般用不到
    //int[] grantResults 是用户的操作响应，包含这权限是够请求成功
    //由于在权限申请的时候，我们就申请了一个权限，所以此处的数组的长度都是1
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == 123) {            //当然权限多了，建议使用Switch，不必纠结于此
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                Toast.makeText(this, "权限申请成功", Toast.LENGTH_SHORT).show();
            } else if (grantResults[0] == PackageManager.PERMISSION_DENIED) {
                Toast.makeText(this, "权限申请失败，用户拒绝权限", Toast.LENGTH_SHORT).show();
            }
        }else if(requestCode==100){
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                Toast.makeText(this, "权限申请成功", Toast.LENGTH_SHORT).show();

                Intent intent = new Intent(RegistActivity.this, MapActivity.class);
                startActivity(intent);
            } else if (grantResults[0] == PackageManager.PERMISSION_DENIED) {
                Toast.makeText(this, "权限申请失败，用户拒绝权限", Toast.LENGTH_SHORT).show();
            }
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        //界面关闭销毁倒计时
        if (mTimer != null) {
            mTimer.cancel();
        }
        if (EventBus.getDefault().isRegistered(this)) {
            EventBus.getDefault().unregister(this);
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

    /**
     * 跳转到miui的权限管理页面
     *//*
    private void gotoMiuiPermission() {
        try { // MIUI 8
            Intent localIntent = new Intent("miui.intent.action.APP_PERM_EDITOR");
            localIntent.setClassName("com.miui.securitycenter", "com.miui.permcenter.permissions.PermissionsEditorActivity");
            localIntent.putExtra("extra_pkgname", RegistActivity.this.getPackageName());
            RegistActivity.this.startActivity(localIntent);
        } catch (Exception e) {
            try { // MIUI 5/6/7
                Intent localIntent = new Intent("miui.intent.action.APP_PERM_EDITOR");
                localIntent.setClassName("com.miui.securitycenter", "com.miui.permcenter.permissions.AppPermissionsEditorActivity");
                localIntent.putExtra("extra_pkgname", RegistActivity.this.getPackageName());
                RegistActivity.this.startActivity(localIntent);
            } catch (Exception e1) { // 否则跳转到应用详情
                startActivity(getAppDetailSettingIntent());
            }
        }
    }

    *//**
     * 跳转到魅族的权限管理系统
     *//*
    private void gotoMeizuPermission() {
        try {
            Intent intent = new Intent("com.meizu.safe.security.SHOW_APPSEC");
            intent.addCategory(Intent.CATEGORY_DEFAULT);
            intent.putExtra("packageName", BuildConfig.APPLICATION_ID);
            startActivity(intent);
        } catch (Exception e) {
            e.printStackTrace();
            startActivity(getAppDetailSettingIntent());
        }
    }

    *//**
     * 华为的权限管理页面
     *//*
    private void gotoHuaweiPermission() {
        try {
            Intent intent = new Intent();
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            ComponentName comp = new ComponentName("com.huawei.systemmanager", "com.huawei.permissionmanager.ui.MainActivity");//华为权限管理
            intent.setComponent(comp);
            startActivity(intent);
        } catch (Exception e) {
            e.printStackTrace();
            startActivity(getAppDetailSettingIntent());
        }

    }

    *//**
     * 获取应用详情页面intent（如果找不到要跳转的界面，也可以先把用户引导到系统设置页面）
     *
     * @return
     *//*
    private Intent getAppDetailSettingIntent() {
        Intent localIntent = new Intent();
        localIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        if (Build.VERSION.SDK_INT >= 9) {
            localIntent.setAction("android.settings.APPLICATION_DETAILS_SETTINGS");
            localIntent.setData(Uri.fromParts("package", getPackageName(), null));
        } else if (Build.VERSION.SDK_INT <= 8) {
            localIntent.setAction(Intent.ACTION_VIEW);
            localIntent.setClassName("com.android.settings", "com.android.settings.InstalledAppDetails");
            localIntent.putExtra("com.android.settings.ApplicationPkgName", getPackageName());
        }
        return localIntent;
    }*/
}