package com.LianXiangKeJi.SupplyChain.order.bean;

import com.google.gson.annotations.SerializedName;

public class WechatOrderBean {


    /**
     * flag : true
     * code : 200
     * message : success
     * data : {"ordersId":"1200000742910473998","money":0.01,"payment":"微信APP支付","body":null,"returnmap":{"appid":"wxacf956e14f407890","nonceStr":"GkuLY85x0bS9IaIpGQn5dFdWfv63pLMr","package":"Sign=WXPay","partnerId":"1595918921","prepayId":"wx08135515886960ecb87647ef438d240000","sign":"3CEC11D1645234C664F0A63BF7AF03B1","timeStamp":"1596866083"},"gmtCreate":1596866083493}
     * url : null
     */

    private boolean flag;
    private int code;
    private String message;
    private DataBean data;
    private Object url;

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

    public DataBean getData() {
        return data;
    }

    public void setData(DataBean data) {
        this.data = data;
    }

    public Object getUrl() {
        return url;
    }

    public void setUrl(Object url) {
        this.url = url;
    }

    public static class DataBean {
        /**
         * ordersId : 1200000742910473998
         * money : 0.01
         * payment : 微信APP支付
         * body : null
         * returnmap : {"appid":"wxacf956e14f407890","nonceStr":"GkuLY85x0bS9IaIpGQn5dFdWfv63pLMr","package":"Sign=WXPay","partnerId":"1595918921","prepayId":"wx08135515886960ecb87647ef438d240000","sign":"3CEC11D1645234C664F0A63BF7AF03B1","timeStamp":"1596866083"}
         * gmtCreate : 1596866083493
         */

        private String ordersId;
        private double money;
        private String payment;
        private Object body;
        private ReturnmapBean returnmap;
        private long gmtCreate;

        public String getOrdersId() {
            return ordersId;
        }

        public void setOrdersId(String ordersId) {
            this.ordersId = ordersId;
        }

        public double getMoney() {
            return money;
        }

        public void setMoney(double money) {
            this.money = money;
        }

        public String getPayment() {
            return payment;
        }

        public void setPayment(String payment) {
            this.payment = payment;
        }

        public Object getBody() {
            return body;
        }

        public void setBody(Object body) {
            this.body = body;
        }

        public ReturnmapBean getReturnmap() {
            return returnmap;
        }

        public void setReturnmap(ReturnmapBean returnmap) {
            this.returnmap = returnmap;
        }

        public long getGmtCreate() {
            return gmtCreate;
        }

        public void setGmtCreate(long gmtCreate) {
            this.gmtCreate = gmtCreate;
        }

        public static class ReturnmapBean {
            /**
             * appid : wxacf956e14f407890
             * nonceStr : GkuLY85x0bS9IaIpGQn5dFdWfv63pLMr
             * package : Sign=WXPay
             * partnerId : 1595918921
             * prepayId : wx08135515886960ecb87647ef438d240000
             * sign : 3CEC11D1645234C664F0A63BF7AF03B1
             * timeStamp : 1596866083
             */

            private String appid;
            private String nonceStr;
            @SerializedName("package")
            private String packageX;
            private String partnerId;
            private String prepayId;
            private String sign;
            private String timeStamp;

            public String getAppid() {
                return appid;
            }

            public void setAppid(String appid) {
                this.appid = appid;
            }

            public String getNonceStr() {
                return nonceStr;
            }

            public void setNonceStr(String nonceStr) {
                this.nonceStr = nonceStr;
            }

            public String getPackageX() {
                return packageX;
            }

            public void setPackageX(String packageX) {
                this.packageX = packageX;
            }

            public String getPartnerId() {
                return partnerId;
            }

            public void setPartnerId(String partnerId) {
                this.partnerId = partnerId;
            }

            public String getPrepayId() {
                return prepayId;
            }

            public void setPrepayId(String prepayId) {
                this.prepayId = prepayId;
            }

            public String getSign() {
                return sign;
            }

            public void setSign(String sign) {
                this.sign = sign;
            }

            public String getTimeStamp() {
                return timeStamp;
            }

            public void setTimeStamp(String timeStamp) {
                this.timeStamp = timeStamp;
            }
        }
    }
}
