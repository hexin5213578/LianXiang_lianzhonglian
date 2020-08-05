package com.LianXiangKeJi.SupplyChain.order.bean;

public class GenerOrdersBean {

    /**
     * flag : true
     * code : 200
     * message : success
     * data : {"ordersId":"c6526ab4-567b-460e-982f-03a3eff25ba09055","money":0.01,"payment":"支付宝支付","body":"alipay_sdk=alipay-sdk-java-3.1.0&app_id=2021001185635349&biz_content=%7B%22out_trade_no%22%3A%22c6526ab4-567b-460e-982f-03a3eff25ba09055%22%2C%22total_amount%22%3A%222100.0%22%7D&charset=UTF-8&format=json&method=alipay.trade.app.pay&notify_url=https%3A%2F%2Ftest.shuai.com%2FaliPayCallback&sign=TxwjNtDajd4jpxLOUFL%2BcZx991Tyt4ScFEqhu96TSWvqiuf9aZPP8mpAmiajksBv%2BdtZ4Z9R5t1459kP1ZhbQBvjbEdE26PjQu6O%2BzoSmGfAR1ky%2F8YNi6emkp%2FHN6Ka%2BImM1DuVXf0x7e9X6rZeEQDZ2f57GkwuWX7wGaqzTlf8a%2BqnevrT%2BFNjcr4dWLUZbZZ4cgZPY8prOxJwyEBw6yqlpVQHcHESugBWVz9EyXv3XA3kL2FfgWqu9a%2FC9MLKMDhMXCYhSanWe4Ts285%2FFD%2Bwha3pMXu%2B55srHvX3MLxvvBPilROjiF%2FpGt1yDHrDZatdMu%2FtZDC4%2FkWWHmBsMw%3D%3D&sign_type=RSA2&timestamp=2020-08-05+11%3A23%3A42&version=1.0","gmtCreate":1596597821844}
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
         * ordersId : c6526ab4-567b-460e-982f-03a3eff25ba09055
         * money : 0.01
         * payment : 支付宝支付
         * body : alipay_sdk=alipay-sdk-java-3.1.0&app_id=2021001185635349&biz_content=%7B%22out_trade_no%22%3A%22c6526ab4-567b-460e-982f-03a3eff25ba09055%22%2C%22total_amount%22%3A%222100.0%22%7D&charset=UTF-8&format=json&method=alipay.trade.app.pay&notify_url=https%3A%2F%2Ftest.shuai.com%2FaliPayCallback&sign=TxwjNtDajd4jpxLOUFL%2BcZx991Tyt4ScFEqhu96TSWvqiuf9aZPP8mpAmiajksBv%2BdtZ4Z9R5t1459kP1ZhbQBvjbEdE26PjQu6O%2BzoSmGfAR1ky%2F8YNi6emkp%2FHN6Ka%2BImM1DuVXf0x7e9X6rZeEQDZ2f57GkwuWX7wGaqzTlf8a%2BqnevrT%2BFNjcr4dWLUZbZZ4cgZPY8prOxJwyEBw6yqlpVQHcHESugBWVz9EyXv3XA3kL2FfgWqu9a%2FC9MLKMDhMXCYhSanWe4Ts285%2FFD%2Bwha3pMXu%2B55srHvX3MLxvvBPilROjiF%2FpGt1yDHrDZatdMu%2FtZDC4%2FkWWHmBsMw%3D%3D&sign_type=RSA2&timestamp=2020-08-05+11%3A23%3A42&version=1.0
         * gmtCreate : 1596597821844
         */

        private String ordersId;
        private double money;
        private String payment;
        private String body;
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

        public long getGmtCreate() {
            return gmtCreate;
        }

        public void setGmtCreate(long gmtCreate) {
            this.gmtCreate = gmtCreate;
        }
    }
}
