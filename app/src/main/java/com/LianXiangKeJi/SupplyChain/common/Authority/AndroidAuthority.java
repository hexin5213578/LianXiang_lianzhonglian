package com.LianXiangKeJi.SupplyChain.common.Authority;


/**
 * @ClassName:AndroidAuthority
 * @Author:hmy
 * @Description:java类作用描述
 */
public class AndroidAuthority {
    private AndroidAuthority() {

    }
    private static class SingleInstance{
        private static AndroidAuthority utils = new AndroidAuthority();
    }
    public static AndroidAuthority getInstance(){
        return SingleInstance.utils;
    }

}
