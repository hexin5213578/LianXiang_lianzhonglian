package com.LianXiangKeJi.SupplyChain.main.bean;

import java.util.List;

public class HomeLunboBean {

    /**
     * flag : true
     * code : 200
     * message : success
     * data : [{"id":"7518552406257201","name":"轮播图1","slideshowUrl":"http://47.114.1.170/lianxiangguanwang/pics/371819c8-4548-4d90-b90a-528ac29c19371089.jpg"}]
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
         * id : 7518552406257201
         * name : 轮播图1
         * slideshowUrl : http://47.114.1.170/lianxiangguanwang/pics/371819c8-4548-4d90-b90a-528ac29c19371089.jpg
         */

        private String id;
        private String name;
        private String slideshowUrl;

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

        public String getSlideshowUrl() {
            return slideshowUrl;
        }

        public void setSlideshowUrl(String slideshowUrl) {
            this.slideshowUrl = slideshowUrl;
        }
    }
}
