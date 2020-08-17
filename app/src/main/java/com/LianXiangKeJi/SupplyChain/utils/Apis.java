package com.LianXiangKeJi.SupplyChain.utils;

import com.LianXiangKeJi.SupplyChain.common.bean.GetPhoneCodeBean;
import com.LianXiangKeJi.SupplyChain.login.bean.LoginSuccessBean;
import com.LianXiangKeJi.SupplyChain.main.bean.ClassIfBean;
import com.LianXiangKeJi.SupplyChain.main.bean.ClassIfSearchGoodsBean;
import com.LianXiangKeJi.SupplyChain.main.bean.ClassIfSearchGoodsNoLoginBean;
import com.LianXiangKeJi.SupplyChain.main.bean.DeleteShopCarBean;
import com.LianXiangKeJi.SupplyChain.main.bean.HomeClassIfBean;
import com.LianXiangKeJi.SupplyChain.main.bean.HomeLunboBean;
import com.LianXiangKeJi.SupplyChain.main.bean.ShopCarBean;
import com.LianXiangKeJi.SupplyChain.movable.activity.CouponActivity;
import com.LianXiangKeJi.SupplyChain.movable.bean.CouponBean;
import com.LianXiangKeJi.SupplyChain.movable.bean.GetCouponBean;
import com.LianXiangKeJi.SupplyChain.movable.bean.MovableBean;
import com.LianXiangKeJi.SupplyChain.order.bean.CashondeliveryBean;
import com.LianXiangKeJi.SupplyChain.order.bean.ConfirmGetGoodsBean;
import com.LianXiangKeJi.SupplyChain.order.bean.DeleteOrCancleOrderBean;
import com.LianXiangKeJi.SupplyChain.order.bean.GenerOrdersBean;
import com.LianXiangKeJi.SupplyChain.order.bean.UserOrderBean;
import com.LianXiangKeJi.SupplyChain.order.bean.WechatOrderBean;
import com.LianXiangKeJi.SupplyChain.order.bean.WxBean;
import com.LianXiangKeJi.SupplyChain.order.bean.ZfbBean;
import com.LianXiangKeJi.SupplyChain.recommend.bean.HotSellBean;
import com.LianXiangKeJi.SupplyChain.recommend.bean.HotSellNoLoginBean;
import com.LianXiangKeJi.SupplyChain.regist.bean.RegistLogcationBean;
import com.LianXiangKeJi.SupplyChain.search.bean.SearchGoodsBean;
import com.LianXiangKeJi.SupplyChain.search.bean.SearchGoodsNoLoginBean;
import com.LianXiangKeJi.SupplyChain.search.bean.SearchHotBean;
import com.LianXiangKeJi.SupplyChain.setup.bean.UpdateImageBean;
import com.LianXiangKeJi.SupplyChain.utils.bean.IdentifyYingyeBean;

import io.reactivex.Observable;
import okhttp3.RequestBody;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.Query;
import retrofit2.http.Url;

public interface Apis {

    //获取分类商品
    @GET("category/findCategoryList")
    Observable<ClassIfBean> getClassIf();

    //获取验证码
    @GET()
    Observable<GetPhoneCodeBean> getPhoneCode(@Url String url, @Query("phone") String phone);

    //用户注册
    @POST("user/signIn")
    Observable<GetPhoneCodeBean> doRegist (@Body RequestBody body);

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

    //修改密码
    @POST("user/updatePassword")
    Observable<UpdateImageBean> doUpdatePassword(@Body RequestBody body);

    //忘记密码
    @POST("user/forgetPassword")
    Observable<UpdateImageBean> doForgetPassword(@Body RequestBody body);

    //更换手机号
    @POST("user/updatePhone")
    Observable<UpdateImageBean> doUpdatePhone(@Body RequestBody body);

    //查询三级分类商品
    @POST("category/findShopGoods")
    Observable<ClassIfSearchGoodsBean> doClassifSearchGoods(@Body RequestBody body);

    //查询三级分类商品 未登录
    @POST("category/findShopGoods")
    Observable<ClassIfSearchGoodsNoLoginBean> doClassifSearchGoodsNoLogin(@Body RequestBody body);

    //查找商品
    @POST("category/seekShopGoods")
    Observable<SearchGoodsBean> doSearchGoods(@Body RequestBody body);

    //查找商品 未登录
    @POST("category/seekShopGoods")
    Observable<SearchGoodsNoLoginBean> doSearchGoodsNoLogin(@Body RequestBody body);

    //首页分类
    @GET("category/findHomePage")
    Observable<HomeClassIfBean> doGetHomeClassIf();

    //查询我的优惠券
    @GET("userCoupon/super/findUserCoupon")
    Observable<CouponBean> doGetMyCoupon();

    //购物车添加 查询
    @POST("shoppingCart/findShoppingCart")
    Observable<ShopCarBean> doShopCar(@Body RequestBody body);

    //购物车删除
    @POST("shoppingCart/findShoppingCart")
    Observable<DeleteShopCarBean> doDeleteShopCar(@Body RequestBody body);

    //查询所有优惠券
    @GET("userCoupon/findCoupon")
    Observable<MovableBean> doGetAllCoupon();

    //用户领取优惠券
    @POST("userCoupon/super/addUserCoupon")
    Observable<GetCouponBean> doGetCoupon(@Body RequestBody body);


    //查询热门搜索
    @GET("category/hotSeek")
    Observable<SearchHotBean> GetSearchHot();


    //生成支付宝订单
    @POST("order/foundOrder")
    Observable<GenerOrdersBean> doGenerOrder(@Body RequestBody body);

    //生成微信订单
    @POST("order/foundOrder")
    Observable<WechatOrderBean> doWXGenerOrder(@Body RequestBody body);

    //生成货到付款订单
    @POST("order/foundOrder")
    Observable<CashondeliveryBean> doCashonOrder(@Body RequestBody body);

    //查询所有订单
    @GET("order/super/findOrders")
    Observable<UserOrderBean> getUserOrder();

    //查询所有订单(分页 带状态)
    @GET("order/super/findAll")
    Observable<UserOrderBean> getUserOrderLimete(@Query("pageNum")int num,@Query("orderState")int  orderstate);

    //查询所有订单(分页  无状态)
    @GET("order/super/findAll")
    Observable<UserOrderBean> getAllUserOrderLimete(@Query("pageNum")int num);

    //查询所有订单(无分页  带状态)
    @GET("order/super/findAll")
    Observable<UserOrderBean> getStateAllUserOrder(@Query("orderState")int orderstate);

    //通过订单号获取微信支付数据
    @POST("order/super/payOne")
    Observable<WxBean> doGetWxData(@Body RequestBody body);

    //通过订单号获取支付宝支付数据
    @POST("order/super/payOne")
    Observable<ZfbBean> doGetZfbData(@Body RequestBody body);

    //删除订单
    @POST("order/super/delOrdersOne")
    Observable<DeleteOrCancleOrderBean> cancleOrder(@Body RequestBody body);

    //确认收货
    @POST("order/super/confirmOrders")
    Observable<ConfirmGetGoodsBean> doConfirmGetGoods(@Body RequestBody body);

    //查询热门商品
    @GET("category/hotShopGoods")
    Observable<HotSellBean> doGetHotSell();

    //查询未登录热门商品
    @GET("category/hotShopGoods")
    Observable<HotSellNoLoginBean> doGetHotNoLoginSell();

    //首页轮播图
    @GET("category/findSlideshow")
    Observable<HomeLunboBean> doGetLunboImage();

    //营业执照识别
    @POST()
    @Headers({"Content-Type:application/x-www-form-urlencoded;charset=UTF-8"})
    Observable<IdentifyYingyeBean> IdentifyYingye(@Url String url, @Query("access_token") String token, @Field("image")String image, @Field("detect_direction")String direction);
}
