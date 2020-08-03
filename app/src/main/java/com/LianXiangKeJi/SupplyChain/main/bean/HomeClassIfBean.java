package com.LianXiangKeJi.SupplyChain.main.bean;

import java.util.List;

public class HomeClassIfBean {
    /**
     * flag : true
     * code : 200
     * message : success
     * data : [{"id":"122ad363-8e63-4c99-8340-a974007366b75314","name":"米面粮油","picture":"http://47.114.1.170/lianxiangguanwang/pics/b6f5e887-c950-41a1-b6c3-4038781d9ade9449.png"},{"id":"4498cd58-9979-40f8-a0de-d62ef5d775545030","name":"方便快食","picture":"http://47.114.1.170/lianxiangguanwang/pics/efdfae95-67da-4e0f-b38d-be1077d4cc509179.png"},{"id":"7191692c-18f2-408e-a449-8223c1d686ba6882","name":"啤酒饮料","picture":"http://47.114.1.170/lianxiangguanwang/pics/6e3e0559-4b85-42d1-aa28-3833256e5a542524.png"},{"id":"a96fecb6-cc21-482f-905e-ff76c4f6abbd6439","name":"进口食品","picture":"http://47.114.1.170/lianxiangguanwang/pics/e5e60265-fdd7-46e7-9468-937f38906b6e6782.png"}]
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
         * id : 122ad363-8e63-4c99-8340-a974007366b75314
         * name : 米面粮油
         * picture : http://47.114.1.170/lianxiangguanwang/pics/b6f5e887-c950-41a1-b6c3-4038781d9ade9449.png
         */

        private String id;
        private String name;
        private String picture;

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

        public String getPicture() {
            return picture;
        }

        public void setPicture(String picture) {
            this.picture = picture;
        }
    }

}
