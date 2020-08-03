package com.LianXiangKeJi.SupplyChain.search.bean;

import java.util.List;

public class SearchGoodsNoLoginBean {

    /**
     * flag : true
     * code : 200
     * message : success
     * data : [{"id":"416178a0-f785-453d-8a07-7649bda626f66635","name":"康师傅矿泉水","url":"http://47.114.1.170/lianxiangguanwang/pics/38fb0ede-cde8-43fd-bb6f-ea18462932167351.png","introduce":"","type":"3","allSell":0,"monthSell":0,"ccId":"7191692c-18f2-408e-a449-8223c1d686ba6882","cid":"3d63f060-b91b-4034-87b4-37f50efd8a9d7989","cname":"康师傅矿泉水"},{"id":"d3b28ed8-eefa-42ab-aacd-556c394acda11571","name":"1233","url":"123","introduce":"","type":"3","allSell":0,"monthSell":0,"ccId":"4498cd58-9979-40f8-a0de-d62ef5d775545030","cid":"aff31e28-c69b-42dc-8be4-0d170e06f9608178","cname":"1233"}]
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
         * id : 416178a0-f785-453d-8a07-7649bda626f66635
         * name : 康师傅矿泉水
         * url : http://47.114.1.170/lianxiangguanwang/pics/38fb0ede-cde8-43fd-bb6f-ea18462932167351.png
         * introduce :
         * type : 3
         * allSell : 0
         * monthSell : 0
         * ccId : 7191692c-18f2-408e-a449-8223c1d686ba6882
         * cid : 3d63f060-b91b-4034-87b4-37f50efd8a9d7989
         * cname : 康师傅矿泉水
         */

        private String id;
        private String name;
        private String url;
        private String introduce;
        private String type;
        private int allSell;
        private int monthSell;
        private String ccId;
        private String cid;
        private String cname;

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

        public String getCcId() {
            return ccId;
        }

        public void setCcId(String ccId) {
            this.ccId = ccId;
        }

        public String getCid() {
            return cid;
        }

        public void setCid(String cid) {
            this.cid = cid;
        }

        public String getCname() {
            return cname;
        }

        public void setCname(String cname) {
            this.cname = cname;
        }
    }
}
