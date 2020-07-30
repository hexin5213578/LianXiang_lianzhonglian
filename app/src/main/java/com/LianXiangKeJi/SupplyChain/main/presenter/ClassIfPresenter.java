package com.LianXiangKeJi.SupplyChain.main.presenter;

import com.LianXiangKeJi.SupplyChain.base.BasePresenter;
import com.LianXiangKeJi.SupplyChain.base.BaseView;
import com.LianXiangKeJi.SupplyChain.main.bean.ClassIfBean;
import com.LianXiangKeJi.SupplyChain.main.contract.ClassIfContract;
import com.LianXiangKeJi.SupplyChain.main.model.ClassIfModel;

/**
 * @ClassName:ClassIfPresenter
 * @Author:hmy
 * @Description:java类作用描述
 */
public class ClassIfPresenter extends BasePresenter implements ClassIfContract.IPresenter {
    private ClassIfModel mModel;
    public ClassIfPresenter(BaseView baseView) {
        super(baseView);
    }

    @Override
    protected void initModel() {
        mModel = new ClassIfModel();
    }

    @Override
    public void doGetClassIf() {
        mModel.doGetClassIf(new ClassIfContract.IModel.dogetClassIfCallBack() {
            @Override
            public void onGetClassIfSuccess(ClassIfBean bean) {
                BaseView baseView =getView();
                if(baseView instanceof ClassIfContract.IView){
                    ((ClassIfContract.IView) baseView).onGetClassIfSuccess(bean);
                }
            }

            @Override
            public void onGetClassError(String msg) {
                BaseView baseView =getView();
                if(baseView instanceof ClassIfContract.IView){
                    ((ClassIfContract.IView) baseView).onGetClassError(msg);
                }
            }
        });
    }
}
