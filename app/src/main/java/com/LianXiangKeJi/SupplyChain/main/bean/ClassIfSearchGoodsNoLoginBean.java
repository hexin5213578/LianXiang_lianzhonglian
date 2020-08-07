package com.LianXiangKeJi.SupplyChain.main.bean;

import java.util.List;

public class ClassIfSearchGoodsNoLoginBean {

    /**
     * flag : true
     * code : 200
     * message : success
     * data : [[{"id":"85e0bc1f-4050-4c98-8d49-f66ac4e82ecc4429","name":"百事可乐碳酸饮料330mlx24","url":"http://47.114.1.170/lianxiangguanwang/pics/911bedbc-47e2-485e-8092-d05bdbf860c83455.png","introduce":"","type":"3","allSell":null,"monthSell":null,"ccId":null,"cid":null,"cname":null}]]
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
         * id : 85e0bc1f-4050-4c98-8d49-f66ac4e82ecc4429
         * name : 百事可乐碳酸饮料330mlx24
         * url : http://47.114.1.170/lianxiangguanwang/pics/911bedbc-47e2-485e-8092-d05bdbf860c83455.png
         * introduce :
         * type : 3
         * allSell : null
         * monthSell : null
         * ccId : null
         * cid : null
         * cname : null
         */

        private String id;
        private String name;
        private String url;
        private String introduce;
        private String type;
        private Object allSell;
        private Object monthSell;
        private Object ccId;
        private Object cid;
        private Object cname;

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

        public String getUrl() {
            return url;
        }

        public void setUrl(String url) {
            this.url = url;
        }

        public String getIntroduce() {
            return introduce;
        }

        public void setIntroduce(String introduce) {
            this.introduce = introduce;
        }

        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
        }

        public Object getAllSell() {
            return allSell;
        }

        public void setAllSell(Object allSell) {
            this.allSell = allSell;
        }

        public Object getMonthSell() {
            return monthSell;
        }

        public void setMonthSell(Object monthSell) {
            this.monthSell = monthSell;
        }

        public Object getCcId() {
            return ccId;
        }

        public void setCcId(Object ccId) {
            this.ccId = ccId;
        }

        public Object getCid() {
            return cid;
        }

        public void setCid(Object cid) {
            this.cid = cid;
        }

        public Object getCname() {
            return cname;
        }

        public void setCname(Object cname) {
            this.cname = cname;
        }
    }
}
