package com.LianXiangKeJi.SupplyChain.recommend.bean;

import java.util.List;

public class HotSellNoLoginBean {

    /**
     * flag : true
     * code : 200
     * message : success
     * data : [{"id":"0b754b92-9e30-4cf9-90b8-021d0eb474476260","name":"阿萨姆奶茶500mlx15","url":"http://47.114.1.170/lianxiangguanwang/pics/893a9f34-69e9-4a94-b825-7222deab30cf9295.png","introduce":"","type":"3","allSell":0,"monthSell":0,"ccId":"91b5acc8-95d2-4697-b39c-05a061136a152353","cid":"80ebe7f7-f296-40b4-9854-656de828930c489","cname":"阿萨姆奶茶500mlx15"},{"id":"581eb1ef-38e8-46f8-84e3-2b9bf3a430479098","name":"安慕希205mlx*12","url":"http://47.114.1.170/lianxiangguanwang/pics/843b839d-4e7b-4aba-a9fb-4d285af1717d5627.png","introduce":"","type":"3","allSell":0,"monthSell":0,"ccId":"91b5acc8-95d2-4697-b39c-05a061136a152353","cid":"80ebe7f7-f296-40b4-9854-656de828930c489","cname":"安慕希205mlx*12"},{"id":"599b1df7-ed7a-40b4-920b-0f978ce51e239468","name":"汇源100分百橙味果汁1LX12","url":"http://47.114.1.170/lianxiangguanwang/pics/f10d5306-0b36-4a9a-9176-9507147fdb2d6102.png","introduce":"","type":"3","allSell":0,"monthSell":0,"ccId":"91b5acc8-95d2-4697-b39c-05a061136a152353","cid":"4cbdb49a-f6d1-456c-bcc5-9e0d7b94b874797","cname":"汇源100分百橙味果汁1LX12"},{"id":"6e435d4e-2573-4a64-919c-55a5e359bb054960","name":"可乐","url":"http://47.114.1.170/lianxiangguanwang/pics/4bdb2e17-0a5d-4be8-87ed-f79977f14a3f8094.png","introduce":"","type":"3","allSell":0,"monthSell":0,"ccId":"91b5acc8-95d2-4697-b39c-05a061136a152353","cid":"7f3959d6-cffa-465b-bb33-a9d8772d7c889021","cname":"可乐"},{"id":"85e0bc1f-4050-4c98-8d49-f66ac4e82ecc4429","name":"百事可乐碳酸饮料330mlx24","url":"http://47.114.1.170/lianxiangguanwang/pics/911bedbc-47e2-485e-8092-d05bdbf860c83455.png","introduce":"","type":"3","allSell":0,"monthSell":0,"ccId":"91b5acc8-95d2-4697-b39c-05a061136a152353","cid":"7f3959d6-cffa-465b-bb33-a9d8772d7c889021","cname":"百事可乐碳酸饮料330mlx24"},{"id":"8cdda77e-123c-4f7d-b23e-795d11b5d2536761","name":"农夫山泉矿泉水","url":"http://47.114.1.170/lianxiangguanwang/pics/ab65d828-ace3-4a3e-9c49-55d8ce4b6fee4166.png","introduce":"","type":"3","allSell":0,"monthSell":0,"ccId":"91b5acc8-95d2-4697-b39c-05a061136a152353","cid":"8ddf8387-e089-40d2-ac7d-9e9e871611046088","cname":"农夫山泉矿泉水"},{"id":"f2452522-68e7-499d-ba2e-ca78c11960fe6572","name":"百岁山矿泉水570mlx24","url":"http://47.114.1.170/lianxiangguanwang/pics/8bc194c9-2df6-4552-95f3-26fbef587cc61054.png","introduce":"","type":"3","allSell":0,"monthSell":0,"ccId":"91b5acc8-95d2-4697-b39c-05a061136a152353","cid":"8ddf8387-e089-40d2-ac7d-9e9e871611046088","cname":"百岁山矿泉水570mlx24"}]
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
         * id : 0b754b92-9e30-4cf9-90b8-021d0eb474476260
         * name : 阿萨姆奶茶500mlx15
         * url : http://47.114.1.170/lianxiangguanwang/pics/893a9f34-69e9-4a94-b825-7222deab30cf9295.png
         * introduce :
         * type : 3
         * allSell : 0
         * monthSell : 0
         * ccId : 91b5acc8-95d2-4697-b39c-05a061136a152353
         * cid : 80ebe7f7-f296-40b4-9854-656de828930c489
         * cname : 阿萨姆奶茶500mlx15
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
