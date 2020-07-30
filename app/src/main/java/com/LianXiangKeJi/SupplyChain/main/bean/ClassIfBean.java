package com.LianXiangKeJi.SupplyChain.main.bean;

import java.util.List;

/**
 * @ClassName:ClassIfBean
 * @Author:hmy
 * @Description:java类作用描述
 */
public class ClassIfBean {


    private boolean flag;
    private int code;
    private String message;
    private DataBean data;
    private Object url;

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

    public DataBean getData() {
        return data;
    }

    public void setData(DataBean data) {
        this.data = data;
    }

    public Object getUrl() {
        return url;
    }

    public void setUrl(Object url) {
        this.url = url;
    }

    public static class DataBean {
        private List<CategoryChildBean> categoryChild;

        public List<CategoryChildBean> getCategoryChild() {
            return categoryChild;
        }

        public void setCategoryChild(List<CategoryChildBean> categoryChild) {
            this.categoryChild = categoryChild;
        }

        public static class CategoryChildBean {

            private String id;
            private String name;
            private String type;
            private Object goodsList;
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
                 * id : 6ee1360a-9c63-4fdd-ae1c-5bcfe1414482
                 * name : 国产啤酒
                 * type : 2
                 * children : null
                 * goodsList : [{"id":"1c994994-b711-48fd-8611-b8122b8803f8772","name":"哈尔滨啤酒1","url":"https://www.imooc.com/wenda/detail/418844","introduce":null,"type":"3","allSell":null,"monthSell":null,"ccId":null,"cid":"6ee1360a-9c63-4fdd-ae1c-5bcfe1414482"},{"id":"36bca9c7-66ce-4936-a83a-633a0cef87052402","name":"哈尔滨啤酒","url":"https://www.imooc.com/wenda/detail/418844","introduce":null,"type":"3","allSell":null,"monthSell":null,"ccId":null,"cid":"6ee1360a-9c63-4fdd-ae1c-5bcfe1414482"},{"id":"4a0103a4-1e8d-48a5-925c-d7c75fccb819944","name":"洛阳宫啤酒","url":"123","introduce":"123","type":"3","allSell":null,"monthSell":null,"ccId":null,"cid":"6ee1360a-9c63-4fdd-ae1c-5bcfe1414482"},{"id":"51e9962e-00ff-4068-b180-071dc024652c2380","name":"哈尔滨啤酒","url":"blob:http://192.168.0.180:7777/97b16720-3e69-4065-aad7-3f0bc93b6e67","introduce":null,"type":"3","allSell":null,"monthSell":null,"ccId":null,"cid":"6ee1360a-9c63-4fdd-ae1c-5bcfe1414482"},{"id":"c6567139-e1cb-4e85-8dce-0785e1632cc91453","name":"11","url":"123","introduce":"","type":"3","allSell":null,"monthSell":null,"ccId":null,"cid":"6ee1360a-9c63-4fdd-ae1c-5bcfe1414482"}]
                 * cid : 3fe4c1db-33d4-429f-abe0-74b2183791db
                 */

                private String id;
                private String name;
                private String type;
                private Object children;
                private String cid;
                private List<GoodsListBean> goodsList;

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

                public String getCid() {
                    return cid;
                }

                public void setCid(String cid) {
                    this.cid = cid;
                }

                public List<GoodsListBean> getGoodsList() {
                    return goodsList;
                }

                public void setGoodsList(List<GoodsListBean> goodsList) {
                    this.goodsList = goodsList;
                }

                public static class GoodsListBean {
                    /**
                     * id : 1c994994-b711-48fd-8611-b8122b8803f8772
                     * name : 哈尔滨啤酒1
                     * url : https://www.imooc.com/wenda/detail/418844
                     * introduce : null
                     * type : 3
                     * allSell : null
                     * monthSell : null
                     * ccId : null
                     * cid : 6ee1360a-9c63-4fdd-ae1c-5bcfe1414482
                     */

                    private String id;
                    private String name;
                    private String url;
                    private Object introduce;
                    private String type;
                    private Object allSell;
                    private Object monthSell;
                    private Object ccId;
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

                    public String getUrl() {
                        return url;
                    }

                    public void setUrl(String url) {
                        this.url = url;
                    }

                    public Object getIntroduce() {
                        return introduce;
                    }

                    public void setIntroduce(Object introduce) {
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

                    public String getCid() {
                        return cid;
                    }

                    public void setCid(String cid) {
                        this.cid = cid;
                    }
                }
            }
        }
    }
}
