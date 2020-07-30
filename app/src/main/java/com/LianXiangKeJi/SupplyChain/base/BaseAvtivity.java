package com.LianXiangKeJi.SupplyChain.base;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Rect;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.LianXiangKeJi.SupplyChain.R;
import com.LianXiangKeJi.SupplyChain.common.service.LocationService;
import com.LianXiangKeJi.SupplyChain.utils.SPUtil;
import com.bumptech.glide.Glide;
import com.smarx.notchlib.INotchScreen;
import com.smarx.notchlib.NotchScreenManager;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import butterknife.ButterKnife;
import butterknife.Unbinder;

import static org.greenrobot.eventbus.EventBus.TAG;

/**
 * @ClassName: BaseAvtivity
 * @Description: (java类作用描述)
 * @Author: hmy
 */
public abstract class BaseAvtivity<P extends BasePresenter> extends AppCompatActivity implements BaseView  {
    private P presenter;
    private Unbinder bind;
    Dialog mLoadingDialog;
    private LatitudeandlongitudeBean latitudeandlongitudeBean;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(getResId());

        presenter = initPresenter();
        bind = ButterKnife.bind(this);
        getData();
    }

    // TODO: 2020/7/25 展示loading圈
    public void showDialog() {
        if (mLoadingDialog == null) {
            mLoadingDialog = new Dialog(this);
            mLoadingDialog.setCancelable(false);
            View v = View.inflate(this, R.layout.dialog_loading, null);
            ImageView iv = v.findViewById(R.id.iv_loading);
            Glide.with(this).asGif().load(R.mipmap.loading).into(iv);

            mLoadingDialog.addContentView(v,
                    new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT,
                            ViewGroup.LayoutParams.WRAP_CONTENT));
        }

        mLoadingDialog.show();
    }

    // TODO: 2020/7/25 隐藏loading圈
    public void hideDialog() {
        if (mLoadingDialog != null && mLoadingDialog.isShowing()) {
            mLoadingDialog.dismiss();
        }
    }
    public P getPresenter() {
        return presenter;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if(presenter!=null){
            presenter.detachView();
            presenter=null;
        }
        bind.unbind();
    }

    // TODO: 2020/7/17 设置标题栏颜色
    public void setTitleColor(Activity activity){
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP){
            Window window = activity.getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(activity.getResources().getColor(R.color.title));
        }
    }
    //判断网络状态
    public boolean NetWork(Context context){
        ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = cm.getActiveNetworkInfo();
        if(activeNetworkInfo!=null){
            return true;
        }
        return false;
    }
    protected abstract int getResId();
    protected abstract void getData();
    protected abstract P initPresenter();
    //获取经纬度
    public LatitudeandlongitudeBean getLocation(Context context){
        float dataOffloat = SPUtil.getInstance().getDataOffloat(this, SPUtil.FILE_NAME, SPUtil.LATITUDE);
        float dataOffloat1 = SPUtil.getInstance().getDataOffloat(this, SPUtil.FILE_NAME, SPUtil.LONGITUDE);

        if(dataOffloat!=0.0 && dataOffloat1!=0.0){
            // TODO: 2020/7/23 获取经纬度

            stopService(new Intent(this, LocationService.class));
            latitudeandlongitudeBean = new LatitudeandlongitudeBean();
            latitudeandlongitudeBean.setLatitude(dataOffloat);
            latitudeandlongitudeBean.setLongitude(dataOffloat1);
        }
        return latitudeandlongitudeBean;
    }
    // TODO: 2020/7/15 关闭软键盘
    public void closekeyboard(){
        InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE); //得到InputMethodManager的实例
        if (imm.isActive()) {//如果开启
            imm.toggleSoftInput(InputMethodManager.SHOW_IMPLICIT, InputMethodManager.HIDE_NOT_ALWAYS);//关闭软键盘，开启方法相同，这个方法是切换开启与关闭状态的
        }
    }

    /**
     *
     * @param activity 上下文
     */
    public void setNotchBar(Activity activity){
        NotchScreenManager.getInstance().setDisplayInNotch(activity);

        NotchScreenManager.getInstance().getNotchInfo(activity, new INotchScreen.NotchScreenCallback() {
            @Override
            public void onResult(INotchScreen.NotchScreenInfo notchScreenInfo) {
                Log.i(TAG, "Is this screen notch? " + notchScreenInfo.hasNotch);
                if (notchScreenInfo.hasNotch) {
                    for (Rect rect : notchScreenInfo.notchRects) {
                        Log.i(TAG, "notch screen Rect =  " + rect.toShortString());
                    }
                }
            }
        });
    }
}
