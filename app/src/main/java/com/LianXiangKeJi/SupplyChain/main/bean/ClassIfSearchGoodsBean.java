package com.LianXiangKeJi.SupplyChain.main.bean;

import android.content.Intent;

import java.util.List;

public class ClassIfSearchGoodsBean {


    /**
     * flag : true
     * code : 200
     * message : success
     * data : [[{"id":"527bb89b-835c-48b3-ba66-01a481bca9733343","name":"阿萨姆奶茶500mlx15","price":"222.00","cost":"111.00","supplierId":"7f9959b4-5719-4bc8-acd3-2ecca11d7d74","specs":"500ml*15瓶","allSell":0,"monthSell":0,"stock":"123123","status":1,"saleName":null,"littlePrintUrl":"http://47.114.1.170/lianxiangguanwang/pics/893a9f34-69e9-4a94-b825-7222deab30cf9295.png","bigPrintUrl":"http://47.114.1.170/lianxiangguanwang/pics/893a9f34-69e9-4a94-b825-7222deab30cf9295.png","salePrice":null,"cid":"80ebe7f7-f296-40b4-9854-656de828930c489"}]]
     * url : null
     */

    private boolean flag;
    private int code;
    private String message;
    private Object url;
    private List<List<DataBean>> data;

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

    public List<List<DataBean>> getData() {
        return data;
    }

    public void setData(List<List<DataBean>> data) {
        this.data = data;
    }

    public static class DataBean {
        /**
         * id : 527bb89b-835c-48b3-ba66-01a481bca9733343
         * name : 阿萨姆奶茶500mlx15
         * price : 222.00
         * cost : 111.00
         * supplierId : 7f9959b4-5719-4bc8-acd3-2ecca11d7d74
         * specs : 500ml*15瓶
         * allSell : 0
         * monthSell : 0
         * stock : 123123
         * status : 1
         * saleName : null
         * littlePrintUrl : http://47.114.1.170/lianxiangguanwang/pics/893a9f34-69e9-4a94-b825-7222deab30cf9295.png
         * bigPrintUrl : http://47.114.1.170/lianxiangguanwang/pics/893a9f34-69e9-4a94-b825-7222deab30cf9295.png
         * salePrice : null
         * cid : 80ebe7f7-f296-40b4-9854-656de828930c489
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
