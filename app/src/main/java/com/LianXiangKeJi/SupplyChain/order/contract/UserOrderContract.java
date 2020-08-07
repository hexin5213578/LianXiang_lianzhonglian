package com.LianXiangKeJi.SupplyChain.order.contract;

import com.LianXiangKeJi.SupplyChain.base.BaseView;
import com.LianXiangKeJi.SupplyChain.main.bean.ClassIfBean;
import com.LianXiangKeJi.SupplyChain.main.contract.ClassIfContract;
import com.LianXiangKeJi.SupplyChain.order.bean.UserOrderBean;

public interface UserOrderContract {
    interface  IView extends BaseView {
        void ondogetUserOrderSuccess(UserOrderBean bean);
        void ondogetUserOrderError(String msg);
    }
    interface IPresenter{
        void dogetUserOrder();
    }
    interface IModel{
        void doGetClassIf(dogetUserOrderCallBack callBack);
        interface dogetUserOrderCallBack{
            void dogetUserOrderSuccess(UserOrderBean bean);
            void dogetUserOrderError(String msg);
        }
    }
}
