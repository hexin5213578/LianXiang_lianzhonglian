package com.LianXiangKeJi.SupplyChain.search.bean;

import java.util.List;

public class SearchGoodsBean {


    /**
     * flag : true
     * code : 200
     * message : success
     * data : [{"id":"20d0fbaa-8cc4-4107-b187-b04678c4559f2591","name":"阿萨姆奶茶","price":"4.00","cost":"3.00","supplierId":"46d89724-0cbf-4b38-bab6-f0b9e2aecb8f1122","specs":"500ml","allSell":0,"monthSell":0,"stock":"888","status":1,"saleName":null,"littlePrintUrl":"http://47.114.1.170/lianxiangguanwang/pics/90639f04-846e-4e14-ac7a-bca035e0e30e993.png","bigPrintUrl":"http://47.114.1.170/lianxiangguanwang/pics/90639f04-846e-4e14-ac7a-bca035e0e30e993.png","salePrice":null,"cid":"a8fc0f1b-c5a0-4b11-a29c-eef796c687f78050"},{"id":"dfdfbdf7-86b2-4dba-b841-82b5dc84d0387252","name":"康师傅矿泉水","price":"1","cost":"0.6","supplierId":"46d89724-0cbf-4b38-bab6-f0b9e2aecb8f1122","specs":"500ml","allSell":0,"monthSell":0,"stock":"100","status":0,"saleName":null,"littlePrintUrl":"http://47.114.1.170/lianxiangguanwang/pics/38fb0ede-cde8-43fd-bb6f-ea18462932167351.png","bigPrintUrl":"http://47.114.1.170/lianxiangguanwang/pics/38fb0ede-cde8-43fd-bb6f-ea18462932167351.png","salePrice":null,"cid":"3d63f060-b91b-4034-87b4-37f50efd8a9d7989"}]
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
         * id : 20d0fbaa-8cc4-4107-b187-b04678c4559f2591
         * name : 阿萨姆奶茶
         * price : 4.00
         * cost : 3.00
         * supplierId : 46d89724-0cbf-4b38-bab6-f0b9e2aecb8f1122
         * specs : 500ml
         * allSell : 0
         * monthSell : 0
         * stock : 888
         * status : 1
         * saleName : null
         * littlePrintUrl : http://47.114.1.170/lianxiangguanwang/pics/90639f04-846e-4e14-ac7a-bca035e0e30e993.png
         * bigPrintUrl : http://47.114.1.170/lianxiangguanwang/pics/90639f04-846e-4e14-ac7a-bca035e0e30e993.png
         * salePrice : null
         * cid : a8fc0f1b-c5a0-4b11-a29c-eef796c687f78050
         */

        private String id;
        private String name;
        private String price;
        private String cost;
        private String supplierId;
        private String specs;
        private int allSell;
        private int monthSell;
        private String stock;
        private int status;
        private Object saleName;
        private String littlePrintUrl;
        private String bigPrintUrl;
        private Object salePrice;
        private String cid;
        private Integer postion=0;

        public Integer getPostion() {
            return postion;
        }

        public void setPostion(Integer postion) {
            this.postion = postion;
        }

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getPrice() {
            return price;
        }

        public void setPrice(String price) {
            this.price = price;
        }

        public String getCost() {
            return cost;
        }

        public void setCost(String cost) {
            this.cost = cost;
        }

        public String getSupplierId() {
            return supplierId;
        }

        public void setSupplierId(String supplierId) {
            this.supplierId = supplierId;
        }

        public String getSpecs() {
            return specs;
        }

        public void setSpecs(String specs) {
            this.specs = specs;
        }

        public int getAllSell() {
            return allSell;
        }

        public void setAllSell(int allSell) {
            this.allSell = allSell;
        }

        public int getMonthSell() {
            return monthSell;
        }

        public void setMonthSell(int monthSell) {
            this.monthSell = monthSell;
        }

        public String getStock() {
            return stock;
        }

        public void setStock(String stock) {
            this.stock = stock;
        }

        public int getStatus() {
            return status;
        }

        public void setStatus(int status) {
            this.status = status;
        }

        public Object getSaleName() {
            return saleName;
        }

        public void setSaleName(Object saleName) {
            this.saleName = saleName;
        }

        public String getLittlePrintUrl() {
            return littlePrintUrl;
        }

        public void setLittlePrintUrl(String littlePrintUrl) {
            this.littlePrintUrl = littlePrintUrl;
        }

        public String getBigPrintUrl() {
            return bigPrintUrl;
        }

        public void setBigPrintUrl(String bigPrintUrl) {
            this.bigPrintUrl = bigPrintUrl;
        }

        public Object getSalePrice() {
            return salePrice;
        }

        public void setSalePrice(Object salePrice) {
            this.salePrice = salePrice;
        }

        public String getCid() {
            return cid;
        }

        public void setCid(String cid) {
            this.cid = cid;
        }
    }
}
