package com.LianXiangKeJi.SupplyChain.main.bean;

import java.util.List;

/**
 * @ClassName:ClassIfBean
 * @Author:hmy
 * @Description:java类作用描述
 */
public class ClassIfBean {

    /**
     * flag : true
     * code : 200
     * message : success
     * data : [{"id":"122ad363-8e63-4c99-8340-a974007366b75314","name":"白酒","type":"1","children":[{"id":"1a1b0793-4148-4fe4-87d9-27e6075b5b2f1543","name":"崂山","type":"2","children":null,"goodsList":null,"shopGoodsList":null,"cid":"122ad363-8e63-4c99-8340-a974007366b75314"},{"id":"79b16c7b-f3f8-49e4-b4cf-2419da54c51f2931","name":"闷倒驴","type":"2","children":null,"goodsList":null,"shopGoodsList":null,"cid":"122ad363-8e63-4c99-8340-a974007366b75314"},{"id":"8cec4b94-2cdd-475d-b5ce-ad42e1b392846866","name":"江小白","type":"2","children":null,"goodsList":null,"shopGoodsList":null,"cid":"122ad363-8e63-4c99-8340-a974007366b75314"},{"id":"e8d053c0-5d4c-4453-b6d4-03adfbaa84362248","name":"杜康白酒","type":"2","children":null,"goodsList":null,"shopGoodsList":null,"cid":"122ad363-8e63-4c99-8340-a974007366b75314"}],"goodsList":null,"shopGoodsList":null,"cid":""},{"id":"4498cd58-9979-40f8-a0de-d62ef5d775545030","name":"啤酒","type":"1","children":[{"id":"aff31e28-c69b-42dc-8be4-0d170e06f9608178","name":"尼尼爱","type":"2","children":null,"goodsList":null,"shopGoodsList":null,"cid":"4498cd58-9979-40f8-a0de-d62ef5d775545030"}],"goodsList":null,"shopGoodsList":null,"cid":""},{"id":"7191692c-18f2-408e-a449-8223c1d686ba6882","name":"乳饮酒水","type":"1","children":[{"id":"3d63f060-b91b-4034-87b4-37f50efd8a9d7989","name":"饮用水","type":"2","children":null,"goodsList":null,"shopGoodsList":null,"cid":"7191692c-18f2-408e-a449-8223c1d686ba6882"},{"id":"6b22999f-6b85-494e-b20b-00786357f42f6754","name":"酸奶","type":"2","children":null,"goodsList":null,"shopGoodsList":null,"cid":"7191692c-18f2-408e-a449-8223c1d686ba6882"},{"id":"8fa16614-f2ba-4268-b11d-45bcd02f332e2204","name":"牛奶","type":"2","children":null,"goodsList":null,"shopGoodsList":null,"cid":"7191692c-18f2-408e-a449-8223c1d686ba6882"},{"id":"a8fc0f1b-c5a0-4b11-a29c-eef796c687f78050","name":"饮料","type":"2","children":null,"goodsList":null,"shopGoodsList":null,"cid":"7191692c-18f2-408e-a449-8223c1d686ba6882"}],"goodsList":null,"shopGoodsList":null,"cid":""}]
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
         * name : 白酒
         * type : 1
         * children : [{"id":"1a1b0793-4148-4fe4-87d9-27e6075b5b2f1543","name":"崂山","type":"2","children":null,"goodsList":null,"shopGoodsList":null,"cid":"122ad363-8e63-4c99-8340-a974007366b75314"},{"id":"79b16c7b-f3f8-49e4-b4cf-2419da54c51f2931","name":"闷倒驴","type":"2","children":null,"goodsList":null,"shopGoodsList":null,"cid":"122ad363-8e63-4c99-8340-a974007366b75314"},{"id":"8cec4b94-2cdd-475d-b5ce-ad42e1b392846866","name":"江小白","type":"2","children":null,"goodsList":null,"shopGoodsList":null,"cid":"122ad363-8e63-4c99-8340-a974007366b75314"},{"id":"e8d053c0-5d4c-4453-b6d4-03adfbaa84362248","name":"杜康白酒","type":"2","children":null,"goodsList":null,"shopGoodsList":null,"cid":"122ad363-8e63-4c99-8340-a974007366b75314"}]
         * goodsList : null
         * shopGoodsList : null
         * cid :
         */

        private String id;
        private String name;
        private String type;
        private Object goodsList;
        private Object shopGoodsList;
        private String cid;
        private List<ChildrenBean> children;

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

        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
        }

        public Object getGoodsList() {
            return goodsList;
        }

        public void setGoodsList(Object goodsList) {
            this.goodsList = goodsList;
        }

        public Object getShopGoodsList() {
            return shopGoodsList;
        }

        public void setShopGoodsList(Object shopGoodsList) {
            this.shopGoodsList = shopGoodsList;
        }

        public String getCid() {
            return cid;
        }

        public void setCid(String cid) {
            this.cid = cid;
        }

        public List<ChildrenBean> getChildren() {
            return children;
        }

        public void setChildren(List<ChildrenBean> children) {
            this.children = children;
        }

        public static class ChildrenBean {
            /**
             * id : 1a1b0793-4148-4fe4-87d9-27e6075b5b2f1543
             * name : 崂山
             * type : 2
             * children : null
             * goodsList : null
             * shopGoodsList : null
             * cid : 122ad363-8e63-4c99-8340-a974007366b75314
             */

            private String id;
            private String name;
            private String type;
            private Object children;
            private Object goodsList;
            private Object shopGoodsList;
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

            public String getType() {
                return type;
            }

            public void setType(String type) {
                this.type = type;
            }

            public Object getChildren() {
                return children;
            }

            public void setChildren(Object children) {
                this.children = children;
            }

            public Object getGoodsList() {
                return goodsList;
            }

            public void setGoodsList(Object goodsList) {
                this.goodsList = goodsList;
            }

            public Object getShopGoodsList() {
                return shopGoodsList;
            }

            public void setShopGoodsList(Object shopGoodsList) {
                this.shopGoodsList = shopGoodsList;
            }

            public String getCid() {
                return cid;
            }

            public void setCid(String cid) {
                this.cid = cid;
            }
        }
    }
}
