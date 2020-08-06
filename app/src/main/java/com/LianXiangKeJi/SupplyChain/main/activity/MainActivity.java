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
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.Toast;

import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.LianXiangKeJi.SupplyChain.BuildConfig;
import com.LianXiangKeJi.SupplyChain.R;
import com.LianXiangKeJi.SupplyChain.base.BaseAvtivity;
import com.LianXiangKeJi.SupplyChain.base.BasePresenter;
import com.LianXiangKeJi.SupplyChain.base.LatitudeandlongitudeBean;
import com.LianXiangKeJi.SupplyChain.common.bean.SaveSuccessBean;
import com.LianXiangKeJi.SupplyChain.common.service.LocationService;
import com.LianXiangKeJi.SupplyChain.main.fragment.FragmentClassIf;
import com.LianXiangKeJi.SupplyChain.main.fragment.FragmentHome;
import com.LianXiangKeJi.SupplyChain.main.fragment.FragmentMine;
import com.LianXiangKeJi.SupplyChain.main.fragment.FragmentOrder;
import com.LianXiangKeJi.SupplyChain.utils.SPUtil;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.LinkedHashMap;

import butterknife.BindView;

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
        //存储map集合存放商品Id
       /* LinkedHashMap map = new LinkedHashMap<String,String>();
        SPUtil.getInstance().setMap(MainActivity.this,"goodsid",map);*/

        rbs[0] = rbHome;
        rbs[1] = rbClassIf;
        rbs[2] = rbOrder;
        rbs[3] = rbMine;
        //  判断网络状态
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


    /*    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            Request();
        }*/

        //  开启服务
        //startService(new Intent(this,LocationService.class));


    }
    @Override
    protected BasePresenter initPresenter() {
        return null;
    }

    //  拿到存入成功信息 取出经纬度
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void getSavaSuccess(SaveSuccessBean successBean){

        location = getLocation(MainActivity.this);
        float longitude = location.getLongitude();
        float latitude = location.getLatitude();

        //Log.d("hmy","经度为"+longitude+"纬度为"+latitude);

    }
    @Subscribe(threadMode = ThreadMode.MAIN,sticky = true)
    public void getId(Integer id){
        replace(fragmentClassIf);
        rbClassIf.setChecked(true);
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
        if(!EventBus.getDefault().isRegistered(this)){
            EventBus.getDefault().unregister(this);
        }
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
                    // request success
                }
                break;
        }
    }*/
}