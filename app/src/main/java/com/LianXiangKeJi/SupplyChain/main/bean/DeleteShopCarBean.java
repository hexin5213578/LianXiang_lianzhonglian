package com.LianXiangKeJi.SupplyChain.main.bean;

public class DeleteShopCarBean {

    /**
     * flag : true
     * code : 200
     * message : success
     * data : 删除成功
     * url : null
     */

    private boolean flag;
    private int code;
    private String message;
    private String data;
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

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    public Object getUrl() {
        return url;
    }

    public void setUrl(Object url) {
        this.url = url;
    }
}
