package com.LianXiangKeJi.SupplyChain.main.activity;

import android.Manifest;
import android.app.AlertDialog;
import android.content.ComponentName;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.Toast;

import androidx.annotation.RequiresApi;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.LianXiangKeJi.SupplyChain.BuildConfig;
import com.LianXiangKeJi.SupplyChain.R;
import com.LianXiangKeJi.SupplyChain.base.BaseAvtivity;
import com.LianXiangKeJi.SupplyChain.base.BasePresenter;
import com.LianXiangKeJi.SupplyChain.base.Common;
import com.LianXiangKeJi.SupplyChain.base.LatitudeandlongitudeBean;
import com.LianXiangKeJi.SupplyChain.common.bean.SaveSuccessBean;
import com.LianXiangKeJi.SupplyChain.common.service.LocationService;
import com.LianXiangKeJi.SupplyChain.main.fragment.FragmentClassIf;
import com.LianXiangKeJi.SupplyChain.main.fragment.FragmentHome;
import com.LianXiangKeJi.SupplyChain.main.fragment.FragmentMine;
import com.LianXiangKeJi.SupplyChain.main.fragment.FragmentOrder;
import com.LianXiangKeJi.SupplyChain.map.MapActivity;
import com.LianXiangKeJi.SupplyChain.utils.SPUtil;
import com.amap.api.location.AMapLocation;
import com.amap.api.location.AMapLocationClient;
import com.amap.api.location.AMapLocationClientOption;
import com.amap.api.location.AMapLocationListener;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends BaseAvtivity implements RadioGroup.OnCheckedChangeListener {


    @BindView(R.id.flContent)
    FrameLayout flContent;
    @BindView(R.id.rbHome)
    RadioButton rbHome;
    @BindView(R.id.rbClassIf)
    RadioButton rbClassIf;
    @BindView(R.id.rbOrder)
    RadioButton rbOrder;
    @BindView(R.id.rbMine)
    RadioButton rbMine;
    @BindView(R.id.rgMenu)
    RadioGroup rgMenu;
    RadioButton[] rbs = new RadioButton[4];
    @BindView(R.id.iv_nonet)
    ImageView ivNonet;
    @BindView(R.id.rl_main)
    RelativeLayout rlMain;
    private FragmentManager fmManager;

    /**
     * 当前展示的Fragment
     */
    private Fragment currentFragment;
    /**
     * 上次点击返回按钮的时间戳
     */
    private long firstPressedTime;

    /**
     * 创建Fragment实例
     */
    private FragmentHome fragmentHome;
    private FragmentClassIf fragmentClassIf;
    private FragmentOrder fragmentOrder;
    private FragmentMine fragmentMine;
    private LatitudeandlongitudeBean location;

    @Override
    protected int getResId() {
        return R.layout.activity_main;
    }

    @Override
    protected void getData() {

        rbs[0] = rbHome;
        rbs[1] = rbClassIf;
        rbs[2] = rbOrder;
        rbs[3] = rbMine;
        // TODO: 2020/7/21 判断网络状态
        boolean b = NetWork(MainActivity.this);
        if (b == false) {
            flContent.setVisibility(View.GONE);
            rgMenu.setVisibility(View.GONE);
            ivNonet.setVisibility(View.VISIBLE);

            rlMain.setBackgroundColor(this.getResources().getColor(R.color.white));

        }else{
            for (RadioButton rb : rbs) {
                //给每个RadioButton加入drawable限制边距控制显示大小
                Drawable[] drawables = rb.getCompoundDrawables();
                //获取drawables
                Rect rt = new Rect(0, 0, 70, 70);
                //定义一个Rect边界
                drawables[1].setBounds(rt);

                //添加限制给控件
                rb.setCompoundDrawables(null, drawables[1], null, null);
            }

            fmManager = getSupportFragmentManager();
            rgMenu.setOnCheckedChangeListener(this);
            //创建fragment实例
            fragmentHome = new FragmentHome();
            fragmentClassIf = new FragmentClassIf();
            fragmentOrder = new FragmentOrder();
            fragmentMine = new FragmentMine();
            /**
             * 首次进入加载第一个界面
             */
            rbHome.setChecked(true);
            //设置状态栏颜色
            setTitleColor(this);
        }
        String sdk = Build.VERSION.SDK; // SDK号
        String model = Build.MODEL; // 手机型号
        String release = Build.VERSION.RELEASE; // android系统版本号
        String brand = Build.BRAND;//手机厂商
        if (Build.VERSION.SDK_INT >= 23) {
            int request = ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION);
            if (request != PackageManager.PERMISSION_GRANTED)//缺少权限，进行权限申请
            {

                AlertDialog.Builder  builder = new AlertDialog.Builder(this)
                        .setMessage("检测到当前未开启定位权限，是否开启？").setPositiveButton("确定", new DialogInterface.OnClickListener() {
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
                                //ToDo: 你想做的事情
                                dialogInterface.dismiss();
                            }
                        });
                builder.create().show();
                return;
            }
        }

    /*    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            Request();
        }*/

        // TODO: 2020/7/23 开启服务
        startService(new Intent(this,LocationService.class));


    }
    @Override
    protected BasePresenter initPresenter() {
        return null;
    }

    // TODO: 2020/7/24 拿到存入成功信息 取出经纬度
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void getSavaSuccess(SaveSuccessBean successBean){

        location = getLocation(MainActivity.this);
        float longitude = location.getLongitude();
        float latitude = location.getLatitude();

        Log.d("hmy","经度为"+longitude+"纬度为"+latitude);

        if(EventBus.getDefault().isRegistered(this)){
            EventBus.getDefault().unregister(this);
        }
    }
    @Override
    public void onCheckedChanged(RadioGroup radioGroup, int i) {
        switch (i) {
            case R.id.rbHome:
                replace(fragmentHome);
                break;
            case R.id.rbClassIf:
                replace(fragmentClassIf);
                break;
            case R.id.rbOrder:
                replace(fragmentOrder);
                break;
            case R.id.rbMine:
                replace(fragmentMine);
                break;
            default:
                break;
        }
    }

    /**
     * 切换页面显示fragment
     *
     * @param to 跳转到的fragment
     */
    private void replace(Fragment to) {
        if (to == null || to == currentFragment) {
            // 如果跳转的fragment为空或者跳转的fragment为当前fragment则不做操作
            return;
        }
        if (currentFragment == null) {
            // 如果当前fragment为空,即为第一次添加fragment
            fmManager.beginTransaction()
                    .add(R.id.flContent, to)
                    .commitAllowingStateLoss();
            currentFragment = to;
            return;
        }
        // 切换fragment
        FragmentTransaction transaction = fmManager.beginTransaction().hide(currentFragment);
        if (!to.isAdded()) {
            transaction.add(R.id.flContent, to);
        } else {
            transaction.show(to);
        }
        transaction.commitAllowingStateLoss();
        currentFragment = to;
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        exitWithDoubleClick();
    }

    /**
     * 点击两次返回按钮退出APP
     */
    private void exitWithDoubleClick() {
        if (System.currentTimeMillis() - firstPressedTime < 3_000) {
            super.onBackPressed();
        } else {
            Toast.makeText(this, "再按一次退出", Toast.LENGTH_SHORT).show();
            firstPressedTime = System.currentTimeMillis();
        }
    }


    @Override
    protected void onResume() {
        super.onResume();
        if(!EventBus.getDefault().isRegistered(this)){
            EventBus.getDefault().register(this);
        }

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

    }



    /**
     * 跳转到miui的权限管理页面
     */
    private void gotoMiuiPermission() {
        try { // MIUI 8
            Intent localIntent = new Intent("miui.intent.action.APP_PERM_EDITOR");
            localIntent.setClassName("com.miui.securitycenter", "com.miui.permcenter.permissions.PermissionsEditorActivity");
            localIntent.putExtra("extra_pkgname", MainActivity.this.getPackageName());
            MainActivity.this.startActivity(localIntent);
        } catch (Exception e) {
            try { // MIUI 5/6/7
                Intent localIntent = new Intent("miui.intent.action.APP_PERM_EDITOR");
                localIntent.setClassName("com.miui.securitycenter", "com.miui.permcenter.permissions.AppPermissionsEditorActivity");
                localIntent.putExtra("extra_pkgname",  MainActivity.this.getPackageName());
                MainActivity.this.startActivity(localIntent);
            } catch (Exception e1) { // 否则跳转到应用详情
                startActivity(getAppDetailSettingIntent());
            }
        }
    }

    /**
     * 跳转到魅族的权限管理系统
     */
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

    /**
     * 华为的权限管理页面
     */
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

    /**
     * 获取应用详情页面intent（如果找不到要跳转的界面，也可以先把用户引导到系统设置页面）
     *
     * @return
     */
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
    }

        public void onTakeLocation1() {
        if (Build.VERSION.SDK_INT >= 23) {
            int request = ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION);
            if (request != PackageManager.PERMISSION_GRANTED)//缺少权限，进行权限申请
            {
                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_COARSE_LOCATION}, 123);
                return;//
            } else {
                //权限同意，不需要处理,去掉用拍照的方法Toast.makeText(this,"权限同意",Toast.LENGTH_SHORT).show();
            }
        } else {
            //低于23 不需要特殊处理，去掉用拍照的方法
        }
    }
/*    @RequiresApi(api = Build.VERSION_CODES.M)
    public void Request() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (checkSelfPermission(Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_COARSE_LOCATION}, 1);
            }
        }
    }
    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch(requestCode) {
            case 1:
                if(grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    // TODO request success
                }
                break;
        }
    }*/
}