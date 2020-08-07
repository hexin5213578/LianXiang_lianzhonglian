package com.LianXiangKeJi.SupplyChain.order.bean;

import java.util.List;

public class UserOrderBean {

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
         * id : 11573712628841319
         * userId : 6f9c51b0-b246-468c-b5f0-6ff4fa4a4c7d8428
         * username : 群福超市
         * phone : 15652578310
         * money : 0.01
         * gmtCreate : 1596762951000
         * gmtPayment : null
         * gmtClose : 1596764751000
         * orderState : 0
         * address : 洛阳市涧西区九都西路太原路口浅井头村3号楼佰嘉宜超市
         * remark : null
         * payWay : 0
         * discount : null
         * userCouponId : null
         * ordersDetailList : [{"id":"06e4c885-711b-47c5-83b3-73c3f6d880846989","shopGoodsId":"d7b9324f-c519-4603-a2cf-0c6e62eba9cd8313","number":1,"price":133,"ordersId":"11573712628841319","name":"汇源100分百橙味果汁1LX12","specs":"汇源100分百橙味果汁1LX12","littlePrintUrl":"http://47.114.1.170/lianxiangguanwang/pics/f10d5306-0b36-4a9a-9176-9507147fdb2d6102.png"}]
         */

        private String id;
        private String userId;
        private String username;
        private String phone;
        private double money;
        private long gmtCreate;
        private Object gmtPayment;
        private long gmtClose;
        private int orderState;
        private String address;
        private Object remark;
        private int payWay;
        private Object discount;
        private Object userCouponId;
        private List<OrdersDetailListBean> ordersDetailList;

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

        public String getUsername() {
            return username;
        }

        public void setUsername(String username) {
            this.username = username;
        }

        public String getPhone() {
            return phone;
        }

        public void setPhone(String phone) {
            this.phone = phone;
        }

        public double getMoney() {
            return money;
        }

        public void setMoney(double money) {
            this.money = money;
        }

        public long getGmtCreate() {
            return gmtCreate;
        }

        public void setGmtCreate(long gmtCreate) {
            this.gmtCreate = gmtCreate;
        }

        public Object getGmtPayment() {
            return gmtPayment;
        }

        public void setGmtPayment(Object gmtPayment) {
            this.gmtPayment = gmtPayment;
        }

        public long getGmtClose() {
            return gmtClose;
        }

        public void setGmtClose(long gmtClose) {
            this.gmtClose = gmtClose;
        }

        public int getOrderState() {
            return orderState;
        }

        public void setOrderState(int orderState) {
            this.orderState = orderState;
        }

        public String getAddress() {
            return address;
        }

        public void setAddress(String address) {
            this.address = address;
        }

        public Object getRemark() {
            return remark;
        }

        public void setRemark(Object remark) {
            this.remark = remark;
        }

        public int getPayWay() {
            return payWay;
        }

        public void setPayWay(int payWay) {
            this.payWay = payWay;
        }

        public Object getDiscount() {
            return discount;
        }

        public void setDiscount(Object discount) {
            this.discount = discount;
        }

        public Object getUserCouponId() {
            return userCouponId;
        }

        public void setUserCouponId(Object userCouponId) {
            this.userCouponId = userCouponId;
        }

        public List<OrdersDetailListBean> getOrdersDetailList() {
            return ordersDetailList;
        }

        public void setOrdersDetailList(List<OrdersDetailListBean> ordersDetailList) {
            this.ordersDetailList = ordersDetailList;
        }

        public static class OrdersDetailListBean {
            /**
             * id : 06e4c885-711b-47c5-83b3-73c3f6d880846989
             * shopGoodsId : d7b9324f-c519-4603-a2cf-0c6e62eba9cd8313
             * number : 1
             * price : 133.0
             * ordersId : 11573712628841319
             * name : 汇源100分百橙味果汁1LX12
             * specs : 汇源100分百橙味果汁1LX12
             * littlePrintUrl : http://47.114.1.170/lianxiangguanwang/pics/f10d5306-0b36-4a9a-9176-9507147fdb2d6102.png
             */

            private String id;
            private String shopGoodsId;
            private int number;
            private double price;
            private String ordersId;
            private String name;
            private String specs;
            private String littlePrintUrl;

            public String getId() {
                return id;
            }

            public void setId(String id) {
                this.id = id;
            }

            public String getShopGoodsId() {
                return shopGoodsId;
            }

            public void setShopGoodsId(String shopGoodsId) {
                this.shopGoodsId = shopGoodsId;
            }

            public int getNumber() {
                return number;
            }

            public void setNumber(int number) {
                this.number = number;
            }

            public double getPrice() {
                return price;
            }

            public void setPrice(double price) {
                this.price = price;
            }

            public String getOrdersId() {
                return ordersId;
            }

            public void setOrdersId(String ordersId) {
                this.ordersId = ordersId;
            }

            public String getName() {
                return name;
            }

            public void setName(String name) {
                this.name = name;
            }

            public String getSpecs() {
                return specs;
            }

            public void setSpecs(String specs) {
                this.specs = specs;
            }

            public String getLittlePrintUrl() {
                return littlePrintUrl;
            }

            public void setLittlePrintUrl(String littlePrintUrl) {
                this.littlePrintUrl = littlePrintUrl;
            }
        }
    }
}
