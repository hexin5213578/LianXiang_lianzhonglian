package com.LianXiangKeJi.SupplyChain.rememberpwd.bean;

/**
 * @ProjectName: LianXiang_lianzhonglian
 * @Package: com.LianXiangKeJi.SupplyChain.rememberpwd.bean
 * @ClassName: ForgetPwdBean
 * @Description: (java类作用描述)
 * @Author: 何梦洋
 * @CreateDate: 2020/7/31 11:10
 */
 public class ForgetPwdBean {
     private String phone;
     private String code;
     private String password;


    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
