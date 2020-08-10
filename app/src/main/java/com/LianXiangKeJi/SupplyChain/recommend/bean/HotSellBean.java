package com.LianXiangKeJi.SupplyChain.recommend.bean;

import java.util.List;

public class HotSellBean {

    /**
     * flag : true
     * code : 200
     * message : success
     * data : [{"id":"f6a0d0a5-3849-40e3-ba7e-c99a3922f7c47385","name":"百岁山矿泉水570mlx24","price":"150","cost":"120","supplierId":"7f9959b4-5719-4bc8-acd3-2ecca11d7d74","specs":"570mlx24","allSell":110,"monthSell":110,"stock":1112,"status":1,"saleName":null,"littlePrintUrl":"http://47.114.1.170/lianxiangguanwang/pics/8bc194c9-2df6-4552-95f3-26fbef587cc61054.png","bigPrintUrl":"http://47.114.1.170/lianxiangguanwang/pics/8bc194c9-2df6-4552-95f3-26fbef587cc61054.png","salePrice":null,"cid":"8ddf8387-e089-40d2-ac7d-9e9e871611046088"},{"id":"ee926294-0584-49d8-8665-298f39a666822281","name":"农夫山泉矿泉水","price":"120","cost":"100","supplierId":"7f9959b4-5719-4bc8-acd3-2ecca11d7d74","specs":"380ml","allSell":90,"monthSell":90,"stock":1112,"status":1,"saleName":null,"littlePrintUrl":"http://47.114.1.170/lianxiangguanwang/pics/ab65d828-ace3-4a3e-9c49-55d8ce4b6fee4166.png","bigPrintUrl":"http://47.114.1.170/lianxiangguanwang/pics/ab65d828-ace3-4a3e-9c49-55d8ce4b6fee4166.png","salePrice":null,"cid":"8ddf8387-e089-40d2-ac7d-9e9e871611046088"},{"id":"d7b9324f-c519-4603-a2cf-0c6e62eba9cd8313","name":"汇源100分百橙味果汁1LX12","price":"133","cost":"122","supplierId":"7f9959b4-5719-4bc8-acd3-2ecca11d7d74","specs":"汇源100分百橙味果汁1LX12","allSell":70,"monthSell":70,"stock":1502,"status":1,"saleName":null,"littlePrintUrl":"http://47.114.1.170/lianxiangguanwang/pics/f10d5306-0b36-4a9a-9176-9507147fdb2d6102.png","bigPrintUrl":"http://47.114.1.170/lianxiangguanwang/pics/f10d5306-0b36-4a9a-9176-9507147fdb2d6102.png","salePrice":null,"cid":"4cbdb49a-f6d1-456c-bcc5-9e0d7b94b874797"},{"id":"527bb89b-835c-48b3-ba66-01a481bca9733343","name":"阿萨姆奶茶500mlx15","price":"222.00","cost":"111.00","supplierId":"7f9959b4-5719-4bc8-acd3-2ecca11d7d74","specs":"500ml*15瓶","allSell":50,"monthSell":50,"stock":121,"status":1,"saleName":null,"littlePrintUrl":"http://47.114.1.170/lianxiangguanwang/pics/893a9f34-69e9-4a94-b825-7222deab30cf9295.png","bigPrintUrl":"http://47.114.1.170/lianxiangguanwang/pics/893a9f34-69e9-4a94-b825-7222deab30cf9295.png","salePrice":null,"cid":"80ebe7f7-f296-40b4-9854-656de828930c489"},{"id":"5ce69644-4318-4f35-a4fe-c03ac65d1e2f1597","name":"百事可乐碳酸饮料330mlx24","price":"200","cost":"100","supplierId":"7f9959b4-5719-4bc8-acd3-2ecca11d7d74","specs":"330mlx24","allSell":30,"monthSell":30,"stock":121,"status":1,"saleName":null,"littlePrintUrl":"http://47.114.1.170/lianxiangguanwang/pics/911bedbc-47e2-485e-8092-d05bdbf860c83455.png","bigPrintUrl":"http://47.114.1.170/lianxiangguanwang/pics/911bedbc-47e2-485e-8092-d05bdbf860c83455.png","salePrice":null,"cid":"7f3959d6-cffa-465b-bb33-a9d8772d7c889021"}]
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
         * id : f6a0d0a5-3849-40e3-ba7e-c99a3922f7c47385
         * name : 百岁山矿泉水570mlx24
         * price : 150
         * cost : 120
         * supplierId : 7f9959b4-5719-4bc8-acd3-2ecca11d7d74
         * specs : 570mlx24
         * allSell : 110
         * monthSell : 110
         * stock : 1112
         * status : 1
         * saleName : null
         * littlePrintUrl : http://47.114.1.170/lianxiangguanwang/pics/8bc194c9-2df6-4552-95f3-26fbef587cc61054.png
         * bigPrintUrl : http://47.114.1.170/lianxiangguanwang/pics/8bc194c9-2df6-4552-95f3-26fbef587cc61054.png
         * salePrice : null
         * cid : 8ddf8387-e089-40d2-ac7d-9e9e871611046088
         */

        private String id;
        private String name;
        private String price;
        private String cost;
        private String supplierId;
        private String specs;
        private int allSell;
        private int monthSell;
        private int stock;
        private int status;
        private Object saleName;
        private String littlePrintUrl;
        private String bigPrintUrl;
        private Object salePrice;
        private String cid;

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

        public int getStock() {
            return stock;
        }

        public void setStock(int stock) {
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
