package com.LianXiangKeJi.SupplyChain.order.presenter;

import com.LianXiangKeJi.SupplyChain.base.BasePresenter;
import com.LianXiangKeJi.SupplyChain.base.BaseView;
import com.LianXiangKeJi.SupplyChain.main.contract.ClassIfContract;
import com.LianXiangKeJi.SupplyChain.order.bean.UserOrderBean;
import com.LianXiangKeJi.SupplyChain.order.contract.UserOrderContract;
import com.LianXiangKeJi.SupplyChain.order.model.UserOrderModel;

public class UserOrderPresenter extends BasePresenter implements UserOrderContract.IPresenter{
    private UserOrderModel mModel;
    public UserOrderPresenter(BaseView baseView) {
        super(baseView);
    }

    @Override
    protected void initModel() {
        mModel = new UserOrderModel();
    }

    @Override
    public void dogetUserOrder() {
        mModel.doGetClassIf(new UserOrderContract.IModel.dogetUserOrderCallBack() {
            @Override
            public void dogetUserOrderSuccess(UserOrderBean bean) {
                BaseView baseView =getView();
                if(baseView instanceof UserOrderContract.IView){
                    ((UserOrderContract.IView) baseView).ondogetUserOrderSuccess(bean);
                }
            }

            @Override
            public void dogetUserOrderError(String msg) {
                BaseView baseView =getView();
                if(baseView instanceof UserOrderContract.IView){
                    ((UserOrderContract.IView) baseView).ondogetUserOrderError(msg);
                }
            }
        });
    }
}
