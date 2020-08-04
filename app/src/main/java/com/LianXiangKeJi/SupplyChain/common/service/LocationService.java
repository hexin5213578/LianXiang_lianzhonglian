package com.LianXiangKeJi.SupplyChain.common.service;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.util.Log;

import androidx.annotation.Nullable;

import com.LianXiangKeJi.SupplyChain.common.bean.SaveSuccessBean;
import com.LianXiangKeJi.SupplyChain.utils.SPUtil;
import com.amap.api.location.AMapLocation;
import com.amap.api.location.AMapLocationClient;
import com.amap.api.location.AMapLocationClientOption;
import com.amap.api.location.AMapLocationListener;

import org.greenrobot.eventbus.EventBus;

/**
 * @ClassName:LocationService
 * @Author:hmy
 * @Description:java类作用描述
 */
public class LocationService extends Service implements AMapLocationListener {
    //声明mlocationClient对象
    public AMapLocationClient mlocationClient;
    //声明mLocationOption对象
    public AMapLocationClientOption mLocationOption = null;
    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;

    }
    @Override
    public void onCreate() {
        super.onCreate();

        mlocationClient = new AMapLocationClient(this);
//初始化定位参数
        mLocationOption = new AMapLocationClientOption();
//设置定位监听
        mlocationClient.setLocationListener(this);
//设置定位模式为高精度模式，Battery_Saving为低功耗模式，Device_Sensors是仅设备模式
        mLocationOption.setLocationMode(AMapLocationClientOption.AMapLocationMode.Hight_Accuracy);
//设置定位间隔,单位毫秒,默认为2000ms
        mLocationOption.setInterval(2000);
//设置定位参数
        mlocationClient.setLocationOption(mLocationOption);
// 此方法为每隔固定时间会发起一次定位请求，为了减少电量消耗或网络流量消耗，
// 注意设置合适的定位时间的间隔（最小间隔支持为1000ms），并且在合适时间调用stopLocation()方法来取消定位请求
// 在定位结束后，在合适的生命周期调用onDestroy()方法
// 在单次定位情况下，定位无论成功与否，都无需调用stopLocation()方法移除请求，定位sdk内部会移除
//启动定位
        mlocationClient.startLocation();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mlocationClient.stopLocation();

    }

    @Override
    public void onLocationChanged(AMapLocation aMapLocation) {
        if (aMapLocation != null) {
            if (aMapLocation.getErrorCode() == 0) {
                //定位成功回调信息，设置相关消息
                aMapLocation.getLocationType();//获取当前定位结果来源，如网络定位结果，详见定位类型表
                double latitude = aMapLocation.getLatitude();//获取纬度
                double longitude = aMapLocation.getLongitude();//获取经度
                aMapLocation.getAccuracy();//获取精度信息

                SPUtil.getInstance().saveDataOffloat(this,SPUtil.FILE_NAME,SPUtil.LONGITUDE, (float) longitude);
                SPUtil.getInstance().saveDataOffloat(this,SPUtil.FILE_NAME,SPUtil.LATITUDE, (float) latitude);

                // TODO: 2020/7/24 判断是否存入成功
                float dataOffloat = SPUtil.getInstance().getDataOffloat(this, SPUtil.FILE_NAME, SPUtil.LATITUDE);
                float dataOffloat1 = SPUtil.getInstance().getDataOffloat(this, SPUtil.FILE_NAME, SPUtil.LONGITUDE);


                if(dataOffloat!=0.0 && dataOffloat1!=0.0){
                    // TODO: 2020/7/24 发送标识
                    SaveSuccessBean saveSuccessBean = new SaveSuccessBean();
                    EventBus.getDefault().post(saveSuccessBean);
                }
                /*float longitude1 = SPUtil.getInstance().getDataOffloat(this, SPUtil.FILE_NAME, SPUtil.LONGITUDE);
                float latitude1 = SPUtil.getInstance().getDataOffloat(this, SPUtil.FILE_NAME, SPUtil.LATITUDE);


                Log.d("xxx","经度为"+longitude1+"纬度为"+latitude1);*/
            } else {
                //显示错误信息ErrCode是错误码，errInfo是错误信息，详见错误码表。
                Log.e("AmapError","location Error, ErrCode:"
                        + aMapLocation.getErrorCode() + ", errInfo:"
                        + aMapLocation.getErrorInfo());
            }
        }
    }
}
