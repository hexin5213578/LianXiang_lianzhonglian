package com.LianXiangKeJi.SupplyChain.order.model;

import com.LianXiangKeJi.SupplyChain.order.bean.UserOrderBean;
import com.LianXiangKeJi.SupplyChain.order.contract.UserOrderContract;
import com.LianXiangKeJi.SupplyChain.utils.NetUtils;

import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class UserOrderModel implements UserOrderContract.IModel {
    @Override
    public void doGetClassIf(dogetUserOrderCallBack callBack) {
        NetUtils.getInstance().getApis().getUserOrder()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<UserOrderBean>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(UserOrderBean userOrderBean) {
                        callBack.dogetUserOrderSuccess(userOrderBean);
                    }

                    @Override
                    public void onError(Throwable e) {
                        callBack.dogetUserOrderError(e.getMessage());
                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }
}
