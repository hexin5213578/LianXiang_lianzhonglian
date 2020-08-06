package com.LianXiangKeJi.SupplyChain.order.bean;

import java.util.List;

public class SavePutOrderBean {
    private List<ResultBean> goodsList;
    private String userCouponId;
    private String remark;

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public static class ResultBean{
        private String shopGoodsId;
        private String number;

        public String getShopGoodsId() {
            return shopGoodsId;
        }

        public void setShopGoodsId(String shopGoodsId) {
            this.shopGoodsId = shopGoodsId;
        }

        public String getNumber() {
            return number;
        }

        public void setNumber(String number) {
            this.number = number;
        }
    }

    public List<ResultBean> getGoodsList() {
        return goodsList;
    }

    public void setGoodsList(List<ResultBean> goodsList) {
        this.goodsList = goodsList;
    }

    public String getUserCouponId() {
        return userCouponId;
    }

    public void setUserCouponId(String userCouponId) {
        this.userCouponId = userCouponId;
    }
}
