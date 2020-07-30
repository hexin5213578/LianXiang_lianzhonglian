package com.LianXiangKeJi.SupplyChain.utils;

import com.LianXiangKeJi.SupplyChain.common.bean.GetPhoneCodeBean;
import com.LianXiangKeJi.SupplyChain.login.bean.LoginSuccessBean;
import com.LianXiangKeJi.SupplyChain.main.bean.ClassIfBean;
import com.LianXiangKeJi.SupplyChain.regist.bean.RegistLogcationBean;
import com.LianXiangKeJi.SupplyChain.setup.bean.UpdateImageBean;

import io.reactivex.Observable;
import okhttp3.RequestBody;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;
import retrofit2.http.Url;

public interface Apis {

    //获取分类商品
    @GET()
    Observable<ClassIfBean> getClassIf(@Url String url);

    //获取验证码
    @GET()
    Observable<GetPhoneCodeBean> getPhoneCode(@Url String url, @Query("phone") String phone);

    //用户注册
    @POST()
    Observable<GetPhoneCodeBean> doRegist (@Url String url,@Body RequestBody body);

    //密码登录
    @POST("user/login")
    Observable<LoginSuccessBean> doPwdLogin(@Body RequestBody body);

    //验证码登录
    @POST("user/phoneLogin")
    Observable<LoginSuccessBean> doCodeLogin(@Body RequestBody body);

    //更换头像
    @POST("user/updateHeadPrint")
    Observable<UpdateImageBean> doUpdateHeadImage(@Body RequestBody body);

    //地址转码经纬度
    @GET()
    Observable<RegistLogcationBean> doRegistLocation(@Url String url,@Query("key")String key,@Query("address")String address,@Query("output")String type);

    //修改用户名
    @POST("user/updateUsername")
    Observable<UpdateImageBean>doUpdateUserName(@Body RequestBody body);
}
