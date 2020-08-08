package com.LianXiangKeJi.SupplyChain.order.bean;

import com.LianXiangKeJi.SupplyChain.common.bean.OrderBean;

import java.util.List;

public class SaveOrderListBean {
    private String theway;
    private long time;
    private String time1;
    private String orderid;
    private int flag;
    private List<OrderBean> orderlist ;

    public String getTime1() {
        return time1;
    }

    public void setTime1(String time1) {
        this.time1 = time1;
    }

    public int getFlag() {
        return flag;
    }

    public void setFlag(int flag) {
        this.flag = flag;
    }

    public String getTheway() {
        return theway;
    }

    public void setTheway(String theway) {
        this.theway = theway;
    }

    public long getTime() {
        return time;
    }

    public void setTime(long time) {
        this.time = time;
    }

    public String getOrderid() {
        return orderid;
    }

    public void setOrderid(String orderid) {
        this.orderid = orderid;
    }

    public List<OrderBean> getOrderlist() {
        return orderlist;
    }

    public void setOrderlist(List<OrderBean> orderlist) {
        this.orderlist = orderlist;
    }
}
