package com.LianXiangKeJi.SupplyChain.main.contract;

import com.LianXiangKeJi.SupplyChain.base.BaseView;
import com.LianXiangKeJi.SupplyChain.main.bean.ClassIfBean;

/**
 * @ClassName:ClassIfContract
 * @Author:hmy
 * @Description:java类作用描述
 */
public interface ClassIfContract {
    interface  IView extends BaseView{
        void onGetClassIfSuccess(ClassIfBean bean);
        void onGetClassError(String msg);
    }
    interface IPresenter{
        void doGetClassIf();
    }
    interface IModel{
        void doGetClassIf(dogetClassIfCallBack callBack);
        interface dogetClassIfCallBack{
            void onGetClassIfSuccess(ClassIfBean bean);
            void onGetClassError(String msg);
        }
    }
}
