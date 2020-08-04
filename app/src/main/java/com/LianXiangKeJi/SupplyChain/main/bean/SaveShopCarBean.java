package com.LianXiangKeJi.SupplyChain.main.bean;

import java.util.List;

public class SaveShopCarBean {
    private List<ResultBean> shoppingCartList;
    private boolean state;

    public List<ResultBean> getShoppingCartList() {
        return shoppingCartList;
    }

    public void setShoppingCartList(List<ResultBean> shoppingCartList) {
        this.shoppingCartList = shoppingCartList;
    }

    public boolean isState() {
        return state;
    }

    public void setState(boolean state) {
        this.state = state;
    }

    public static class ResultBean{
        private String shopGoodsId;

        public String getShopGoodsId() {
            return shopGoodsId;
        }

        public void setShopGoodsId(String shopGoodsId) {
            this.shopGoodsId = shopGoodsId;
        }
    }
}
