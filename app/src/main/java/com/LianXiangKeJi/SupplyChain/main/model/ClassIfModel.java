package com.LianXiangKeJi.SupplyChain.main.model;

import com.LianXiangKeJi.SupplyChain.main.bean.ClassIfBean;
import com.LianXiangKeJi.SupplyChain.main.contract.ClassIfContract;
import com.LianXiangKeJi.SupplyChain.utils.NetUtils;

import io.reactivex.Observer;
import io.reactivex.Scheduler;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

/**
 * @ClassName:ClassIfModel
 * @Author:hmy
 * @Description:java类作用描述
 */
public class ClassIfModel implements ClassIfContract.IModel {
    @Override
    public void doGetClassIf(dogetClassIfCallBack callBack) {
        NetUtils.getInstance().getApis().getClassIf("http://192.168.0.143:8080/category/findList")
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<ClassIfBean>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(ClassIfBean bean) {
                        callBack.onGetClassIfSuccess(bean);
                    }

                    @Override
                    public void onError(Throwable e) {
                        callBack.onGetClassError(e.getMessage());

                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }
}
