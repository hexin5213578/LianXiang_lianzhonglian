package com.LianXiangKeJi.SupplyChain.search.bean;

import java.util.List;

public class SearchHotBean {

    /**
     * flag : true
     * code : 200
     * message : success
     * data : [{"id":"335835952320889491","name":"dd","number":14},{"id":"294181602190244181","name":"矿泉水","number":10},{"id":"291544846948363380","name":"茶","number":6},{"id":"291704549386782411","name":"阿萨姆","number":5},{"id":"292127466376248521","name":"100","number":4}]
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
         * id : 335835952320889491
         * name : dd
         * number : 14
         */

        private String id;
        private String name;
        private int number;

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

        public int getNumber() {
            return number;
        }

        public void setNumber(int number) {
            this.number = number;
        }
    }
}
