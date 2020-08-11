package com.LianXiangKeJi.SupplyChain.movable.bean;

import java.util.List;

public class CouponBean {


    /**
     * flag : true
     * code : 200
     * message : success
     * data : [{"id":"3c43af03-0d94-463d-8f15-5c6814105c38217","userId":"6f9c51b0-b246-468c-b5f0-6ff4fa4a4c7d8428","couponId":"2c3f6e4e-f974-42d9-87db-c8058e22948b2987","couponState":2,"name":"双11","saleType":"满减券","state":"123123123","beginTime":1596816000000,"overTime":1601481599000,"full":100,"minus":10},{"id":"be538aaa-ca57-4169-a1ed-6fcac4c93e582230","userId":"6f9c51b0-b246-468c-b5f0-6ff4fa4a4c7d8428","couponId":"9f48f3e4-b807-45af-b767-90e31cdbc1f11500","couponState":1,"name":"618","saleType":"满减券","state":"123123","beginTime":1596816000000,"overTime":1601049599000,"full":100,"minus":11}]
     * url : null
     */

    private boolean flag;
    private int code;
    private String message;
    private Object url;
    private List<DataBean> data;

    public boolean isFlag() {
        return flag;
    }

    public void setFlag(boolean flag) {
        this.flag = flag;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Object getUrl() {
        return url;
    }

    public void setUrl(Object url) {
        this.url = url;
    }

    public List<DataBean> getData() {
        return data;
    }

    public void setData(List<DataBean> data) {
        this.data = data;
    }

    public static class DataBean {
        /**
         * id : 3c43af03-0d94-463d-8f15-5c6814105c38217
         * userId : 6f9c51b0-b246-468c-b5f0-6ff4fa4a4c7d8428
         * couponId : 2c3f6e4e-f974-42d9-87db-c8058e22948b2987
         * couponState : 2
         * name : 双11
         * saleType : 满减券
         * state : 123123123
         * beginTime : 1596816000000
         * overTime : 1601481599000
         * full : 100
         * minus : 10
         */

        private String id;
        private String userId;
        private String couponId;
        private int couponState;
        private String name;
        private String saleType;
        private String state;
        private long beginTime;
        private long overTime;
        private int full;
        private int minus;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getUserId() {
            return userId;
        }

        public void setUserId(String userId) {
            this.userId = userId;
        }

        public String getCouponId() {
            return couponId;
        }

        public void setCouponId(String couponId) {
            this.couponId = couponId;
        }

        public int getCouponState() {
            return couponState;
        }

        public void setCouponState(int couponState) {
            this.couponState = couponState;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getSaleType() {
            return saleType;
        }

        public void setSaleType(String saleType) {
            this.saleType = saleType;
        }

        public String getState() {
            return state;
        }

        public void setState(String state) {
            this.state = state;
        }

        public long getBeginTime() {
            return beginTime;
        }

        public void setBeginTime(long beginTime) {
            this.beginTime = beginTime;
        }

        public long getOverTime() {
            return overTime;
        }

        public void setOverTime(long overTime) {
            this.overTime = overTime;
        }

        public int getFull() {
            return full;
        }

        public void setFull(int full) {
            this.full = full;
        }

        public int getMinus() {
            return minus;
        }

        public void setMinus(int minus) {
            this.minus = minus;
        }
    }
}
