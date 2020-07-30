package com.LianXiangKeJi.SupplyChain.utils;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.SharedPreferences;

/**
 * @ClassName: SPUtil
 * @Description: (java类作用描述)
 * @Author: hmy
 */
public class SPUtil {
    /**
     * id : 85b002a2-da0b-4a07-9005-6b4438b95c422913
     * username : 群福超市
     * password : hexin521
     * phone : 15652578310
     * token : eyJhbGciOiJSUzI1NiIsInR5cCI6IkpXVCJ9.eyJpZCI6Ijg1YjAwMmEyLWRhMGItNGEwNy05MDA1LTZiNDQzOGI5NWM0MjI5MTMiLCJwYXNzd29yZCI6ImhleGluNTIxIiwicGhvbmUiOiIxNTY1MjU3ODMxMCIsInVzZXJuYW1lIjoi576k56aP6LaF5biCIn0.kH0SpmazC1_doewQmSaMH2c5z3HQq4FLd8PC_bU-IHkVTsQMXTQMvTt6HKXRPz4o6lQdt3FfbC_K_bcdvUk-1h3l4ZqNUMUcu2H-glKhkSlo88aofrPmgDZSOfpBcQPrQ9Ob7BThhzSekaBCXYI4nvSlNXbPYqtT_dNUTxhJm9FtJh8AhaiuGkLO7NUuYGK1HGs0jM7BJJqKnN_9XeKC_VjT3kn8eHyyeL0oZ_26cIZI91x8qm0RPbr7jElnLBtTGWC97YsBaWC5vQ4wV9i1-sn1Cwj36KPhD1yGMTQ1Ru6sSjBAfsG3Y_Qs7wSWiQqoKENVcMF3pF60ufvm-D_lFg
     * headUrl : 123
     * doorUrl : http://47.114.1.170/lianxiangguanwang/pics/c304ecb7-3894-4043-a3b8-67822ddb46a16095.jpg
     * licenseUrl : http://47.114.1.170/lianxiangguanwang/pics/6860b6ba-0ccc-408e-b3d4-2375bbe93fd98068.jpg
     * setTime : 2020-07-29T03:31:03.000+0000
     * checkStatus : 1
     * code : 2849
     */



    public static final String FILE_NAME = "userInfo";
    public static final String KEY_TOKEN = "token";
    public static final String LATITUDE = "latitude";
    public static final String LONGITUDE = "longitude";
    public static final String USER_NAME = "username";
    public static final String HEAD_URL = "hearurl";
    public static final String CHECK_STATUS = "checkstatus";
    public static final String KEY_ID = "id";
    public static final String KEY_ADDRESS = "address";
    public static final String KEY_SCOPE = "scope";
    public static final String KEY_PHONE = "phone";

    private SPUtil() {
    }


    private static class SingleInstance{
        private static SPUtil sSPUtil = new SPUtil();
    }
    public static SPUtil getInstance(){
        return SingleInstance.sSPUtil;
    }
    //存储信息
    @SuppressLint("CommitPrefEdits")
    public void saveData(Context context, String fileName, String key, String value){
        SharedPreferences sharedPreferences = context.getSharedPreferences(fileName, Context.MODE_PRIVATE);
        SharedPreferences.Editor edit = sharedPreferences.edit();
        edit.putString(key, value);
        edit.commit();
    }
    //存储信息
    @SuppressLint("CommitPrefEdits")
    public void saveDataOffloat(Context context, String fileName, String key, float value){
        SharedPreferences sharedPreferences = context.getSharedPreferences(fileName, Context.MODE_PRIVATE);
        SharedPreferences.Editor edit = sharedPreferences.edit();
        edit.putFloat(key, value);
        edit.commit();
    }
    //读取信息
    public String getData(Context context, String fileName, String key){
        SharedPreferences sharedPreferences = context.getSharedPreferences(fileName, Context.MODE_PRIVATE);
        String string = sharedPreferences.getString(key, null);
        return string;
    }
    //读取信息
    public float getDataOffloat(Context context, String fileName, String key){
        SharedPreferences sharedPreferences = context.getSharedPreferences(fileName, Context.MODE_PRIVATE);
        float string = sharedPreferences.getFloat(key, 0);
        return string;
    }
    //清除
    public static void unReg(Context context, String name){
        SharedPreferences sharedPreferences = context.getSharedPreferences(name, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.clear();
        editor.commit();
    }
}