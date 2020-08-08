package com.LianXiangKeJi.SupplyChain.order.bean;

public class ZfbBean {

    /**
     * flag : true
     * code : 200
     * message : success
     * data : {"ordersId":"2054930142569848048","money":0.01,"payment":"支付宝支付","body":"alipay_sdk=alipay-sdk-java-3.1.0&app_id=2021001185635349&biz_content=%7B%22body%22%3A%22%E6%94%AF%E4%BB%98%E5%AE%9Dapp%E6%94%AF%E4%BB%98%22%2C%22out_trade_no%22%3A%222054930142569848048%22%2C%22subject%22%3A%22%E9%93%BE%E4%BC%97%E5%BA%97%E5%95%86%E5%93%81%E4%BB%98%E6%AC%BE%22%2C%22total_amount%22%3A%220.01%22%7D&charset=UTF-8&format=json&method=alipay.trade.app.pay&notify_url=http%3A%2F%2F2d509776.ittun.com%2FaliPayCallback&sign=YU%2BlC7Oy%2F6AXpjNPq9RT1ccfq46A%2FMWw1GlyJbKK%2BxMDgYq%2BYmmGSIPZHDXsKH131o1b0crksiCRD1u9FshPDSgyvXBZn%2FOoHJUb4gVovzH%2BkBRDtTj34dMzIHf%2FTsjjb43pp4VFSfKYp3VfptUdiTcch3ZNfp6RbPQCXEMIxCZ4gPXc0IXSRqFXfqrvNjORhea72%2BwE0FAfxZHpfSZfQ8n8rukTA4APkf8OY%2Bk9IU1uwjRJujB4kkArpVdDcXZdDk9fDpFnlcnRUzh5f0XCYFtge6LYoujzOi%2FXybsiJz7oZsohtqaqLEo72WsM6MK9xpYiJooGi27lgju7acvI2Q%3D%3D&sign_type=RSA2&timestamp=2020-08-08+18%3A00%3A09&version=1.0","returnmap":null,"gmtCreate":1596880781122}
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
         * ordersId : 2054930142569848048
         * money : 0.01
         * payment : 支付宝支付
         * body : alipay_sdk=alipay-sdk-java-3.1.0&app_id=2021001185635349&biz_content=%7B%22body%22%3A%22%E6%94%AF%E4%BB%98%E5%AE%9Dapp%E6%94%AF%E4%BB%98%22%2C%22out_trade_no%22%3A%222054930142569848048%22%2C%22subject%22%3A%22%E9%93%BE%E4%BC%97%E5%BA%97%E5%95%86%E5%93%81%E4%BB%98%E6%AC%BE%22%2C%22total_amount%22%3A%220.01%22%7D&charset=UTF-8&format=json&method=alipay.trade.app.pay&notify_url=http%3A%2F%2F2d509776.ittun.com%2FaliPayCallback&sign=YU%2BlC7Oy%2F6AXpjNPq9RT1ccfq46A%2FMWw1GlyJbKK%2BxMDgYq%2BYmmGSIPZHDXsKH131o1b0crksiCRD1u9FshPDSgyvXBZn%2FOoHJUb4gVovzH%2BkBRDtTj34dMzIHf%2FTsjjb43pp4VFSfKYp3VfptUdiTcch3ZNfp6RbPQCXEMIxCZ4gPXc0IXSRqFXfqrvNjORhea72%2BwE0FAfxZHpfSZfQ8n8rukTA4APkf8OY%2Bk9IU1uwjRJujB4kkArpVdDcXZdDk9fDpFnlcnRUzh5f0XCYFtge6LYoujzOi%2FXybsiJz7oZsohtqaqLEo72WsM6MK9xpYiJooGi27lgju7acvI2Q%3D%3D&sign_type=RSA2&timestamp=2020-08-08+18%3A00%3A09&version=1.0
         * returnmap : null
         * gmtCreate : 1596880781122
         */

        private String ordersId;
        private double money;
        private String payment;
        private String body;
        private Object returnmap;
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

        public String getBody() {
            return body;
        }

        public void setBody(String body) {
            this.body = body;
        }

        public Object getReturnmap() {
            return returnmap;
        }

        public void setReturnmap(Object returnmap) {
            this.returnmap = returnmap;
        }

        public long getGmtCreate() {
            return gmtCreate;
        }

        public void setGmtCreate(long gmtCreate) {
            this.gmtCreate = gmtCreate;
        }
    }
}
