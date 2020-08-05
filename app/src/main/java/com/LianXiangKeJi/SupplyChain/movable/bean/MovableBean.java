package com.LianXiangKeJi.SupplyChain.movable.bean;

import java.util.List;

public class MovableBean {

    /**
     * flag : true
     * code : 200
     * message : success
     * data : [{"id":"23d754b3-8624-4eff-a312-a5988cacfdce796","name":"123","saleType":"满减券","number":100,"already":0,"residue":100,"employ":0,"state":"123","norm":5,"type":"全场通用","beginTime":1596193775000,"overTime":1597230575000,"fixedTime":null,"full":100,"minus":1},{"id":"70b34b4d-6014-42ed-861b-f8a3b337af584059","name":"双十一优惠券","saleType":"满减券","number":500,"already":5,"residue":495,"employ":0,"state":"...","norm":5,"type":"全场通用","beginTime":1596107849000,"overTime":1596712649000,"fixedTime":null,"full":50,"minus":10},{"id":"9b58ea8d-9965-4dd8-b2c2-91eafec10fa62370","name":"618满减","saleType":"满减券","number":500,"already":5,"residue":495,"employ":0,"state":"...","norm":5,"type":"全场通用","beginTime":null,"overTime":null,"fixedTime":3,"full":50,"minus":10}]
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
         * id : 23d754b3-8624-4eff-a312-a5988cacfdce796
         * name : 123
         * saleType : 满减券
         * number : 100
         * already : 0
         * residue : 100
         * employ : 0
         * state : 123
         * norm : 5
         * type : 全场通用
         * beginTime : 1596193775000
         * overTime : 1597230575000
         * fixedTime : null
         * full : 100
         * minus : 1
         */

        private String id;
        private String name;
        private String saleType;
        private int number;
        private int already;
        private int residue;
        private int employ;
        private String state;
        private int norm;
        private String type;
        private long beginTime;
        private long overTime;
        private Object fixedTime;
        private int full;
        private int minus;

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

        public String getSaleType() {
            return saleType;
        }

        public void setSaleType(String saleType) {
            this.saleType = saleType;
        }

        public int getNumber() {
            return number;
        }

        public void setNumber(int number) {
            this.number = number;
        }

        public int getAlready() {
            return already;
        }

        public void setAlready(int already) {
            this.already = already;
        }

        public int getResidue() {
            return residue;
        }

        public void setResidue(int residue) {
            this.residue = residue;
        }

        public int getEmploy() {
            return employ;
        }

        public void setEmploy(int employ) {
            this.employ = employ;
        }

        public String getState() {
            return state;
        }

        public void setState(String state) {
            this.state = state;
        }

        public int getNorm() {
            return norm;
        }

        public void setNorm(int norm) {
            this.norm = norm;
        }

        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
        }

        public long getBeginTime() {
            return beginTime;
        }

        public void setBeginTime(long beginTime) {
            this.beginTime = beginTime;
        }

        public long getOverTime() {
            return overTime;
        }

        public void setOverTime(long overTime) {
            this.overTime = overTime;
        }

        public Object getFixedTime() {
            return fixedTime;
        }

        public void setFixedTime(Object fixedTime) {
            this.fixedTime = fixedTime;
        }

        public int getFull() {
            return full;
        }

        public void setFull(int full) {
            this.full = full;
        }

        public int getMinus() {
            return minus;
        }

        public void setMinus(int minus) {
            this.minus = minus;
        }
    }
}
