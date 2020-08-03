package com.LianXiangKeJi.SupplyChain.movable.bean;

import java.util.List;

public class CouponBean {

    /**
     * flag : true
     * code : 200
     * message : success
     * data : [{"id":"3a0771a4-6918-4ad1-b989-e54296cef55b8896","userId":"6f9c51b0-b246-468c-b5f0-6ff4fa4a4c7d8428","couponId":"70b34b4d-6014-42ed-861b-f8a3b337af584059","number":5,"couponState":1,"name":"双十一优惠券","saleType":null,"state":"...","beginTime":null,"overTime":null,"full":50,"minus":10},{"id":"3a0771a4-6918-4ad1-b989-e54296cef55b8896","userId":"6f9c51b0-b246-468c-b5f0-6ff4fa4a4c7d8428","couponId":"70b34b4d-6014-42ed-861b-f8a3b337af584059","number":5,"couponState":1,"name":"双十一优惠券","saleType":null,"state":"...","beginTime":null,"overTime":null,"full":50,"minus":10},{"id":"3a0771a4-6918-4ad1-b989-e54296cef55b8896","userId":"6f9c51b0-b246-468c-b5f0-6ff4fa4a4c7d8428","couponId":"70b34b4d-6014-42ed-861b-f8a3b337af584059","number":5,"couponState":1,"name":"双十一优惠券","saleType":null,"state":"...","beginTime":null,"overTime":null,"full":50,"minus":10},{"id":"3a0771a4-6918-4ad1-b989-e54296cef55b8896","userId":"6f9c51b0-b246-468c-b5f0-6ff4fa4a4c7d8428","couponId":"70b34b4d-6014-42ed-861b-f8a3b337af584059","number":5,"couponState":1,"name":"双十一优惠券","saleType":null,"state":"...","beginTime":null,"overTime":null,"full":50,"minus":10},{"id":"3a0771a4-6918-4ad1-b989-e54296cef55b8896","userId":"6f9c51b0-b246-468c-b5f0-6ff4fa4a4c7d8428","couponId":"70b34b4d-6014-42ed-861b-f8a3b337af584059","number":5,"couponState":1,"name":"双十一优惠券","saleType":null,"state":"...","beginTime":null,"overTime":null,"full":50,"minus":10}]
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
         * id : 3a0771a4-6918-4ad1-b989-e54296cef55b8896
         * userId : 6f9c51b0-b246-468c-b5f0-6ff4fa4a4c7d8428
         * couponId : 70b34b4d-6014-42ed-861b-f8a3b337af584059
         * number : 5
         * couponState : 1
         * name : 双十一优惠券
         * saleType : null
         * state : ...
         * beginTime : null
         * overTime : null
         * full : 50
         * minus : 10
         */

        private String id;
        private String userId;
        private String couponId;
        private int number;
        private int couponState;
        private String name;
        private Object saleType;
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

        public int getNumber() {
            return number;
        }

        public void setNumber(int number) {
            this.number = number;
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

        public Object getSaleType() {
            return saleType;
        }

        public void setSaleType(Object saleType) {
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
