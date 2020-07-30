package com.LianXiangKeJi.SupplyChain.login.bean;

/**
 * @ClassName:LoginSuccessBean
 * @Author:hmy
 * @Description:java类作用描述
 */
public class LoginSuccessBean {


    /**
     * flag : true
     * code : 200
     * message : success
     * data : {"id":"6f9c51b0-b246-468c-b5f0-6ff4fa4a4c7d8428","username":"群福超市","password":"hexin521","phone":"15652578310","token":"eyJhbGciOiJSUzI1NiIsInR5cCI6IkpXVCJ9.eyJpZCI6IjZmOWM1MWIwLWIyNDYtNDY4Yy1iNWYwLTZmZjRmYTRhNGM3ZDg0MjgiLCJwYXNzd29yZCI6ImhleGluNTIxIiwicGhvbmUiOiIxNTY1MjU3ODMxMCIsInVzZXJuYW1lIjoi5L2w5ZiJ5a6c6LaF5biCIn0.FreOrz_LiIn3Wjpq80pA4PdJS6wzcIfYqEAKRDMZXp8XmMFexkwh8N8uc4Ouhi_QHfoQ6CFyqQ0-prVPl9SJn20UVPVZfuU7sEiStUIihUFHsEB81oZxayc9j7KhmdyW0RRAsEtN22rJ31_tvR8jbE6kaAlsIrA3HB3sSSqdAj9by_HQRtp06c500KuAbx8bqhZjKuiAwghWDxNEL18MeVDxt2UYlNN6gShsrRhW1KTt0AIubUPoaB4TlvXnlWJAl0_wleq37DTyBlnCFsWSQ5Zhv-ztXHqmPbpnRJQzO3Z6EYwuK9jQOMGSiFtmlBeq5Lcju8QOzHjzb4RBIYyTjg","headUrl":"http://47.114.1.170/lianxiangguanwang/pics/169af678-154e-4269-af4b-1e08363d5f149293.jpg","doorUrl":"http://47.114.1.170/lianxiangguanwang/pics/04cc5449-3b24-4cfa-81ad-fdcd737b64d09903.jpg","licenseUrl":"http://47.114.1.170/lianxiangguanwang/pics/39a9d4b9-3758-42b7-b87f-19bc349b54e01476.jpg","setTime":"2020-07-30T01:34:40.000+0000","checkStatus":1,"address":"洛阳市涧西区九都西路太原路口浅井头村3号楼佰嘉宜超市","scope":"112.394552,34.644045","code":"4566"}
     * url : null
     */

    private boolean flag;
    private int code;
    private String message;
    private DataBean data;
    private Object url;

    public boolean isFlag() {
        return flag;
    }

    public void setFlag(boolean flag) {
        this.flag = flag;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public DataBean getData() {
        return data;
    }

    public void setData(DataBean data) {
        this.data = data;
    }

    public Object getUrl() {
        return url;
    }

    public void setUrl(Object url) {
        this.url = url;
    }

    public static class DataBean {
        /**
         * id : 6f9c51b0-b246-468c-b5f0-6ff4fa4a4c7d8428
         * username : 群福超市
         * password : hexin521
         * phone : 15652578310
         * token : eyJhbGciOiJSUzI1NiIsInR5cCI6IkpXVCJ9.eyJpZCI6IjZmOWM1MWIwLWIyNDYtNDY4Yy1iNWYwLTZmZjRmYTRhNGM3ZDg0MjgiLCJwYXNzd29yZCI6ImhleGluNTIxIiwicGhvbmUiOiIxNTY1MjU3ODMxMCIsInVzZXJuYW1lIjoi5L2w5ZiJ5a6c6LaF5biCIn0.FreOrz_LiIn3Wjpq80pA4PdJS6wzcIfYqEAKRDMZXp8XmMFexkwh8N8uc4Ouhi_QHfoQ6CFyqQ0-prVPl9SJn20UVPVZfuU7sEiStUIihUFHsEB81oZxayc9j7KhmdyW0RRAsEtN22rJ31_tvR8jbE6kaAlsIrA3HB3sSSqdAj9by_HQRtp06c500KuAbx8bqhZjKuiAwghWDxNEL18MeVDxt2UYlNN6gShsrRhW1KTt0AIubUPoaB4TlvXnlWJAl0_wleq37DTyBlnCFsWSQ5Zhv-ztXHqmPbpnRJQzO3Z6EYwuK9jQOMGSiFtmlBeq5Lcju8QOzHjzb4RBIYyTjg
         * headUrl : http://47.114.1.170/lianxiangguanwang/pics/169af678-154e-4269-af4b-1e08363d5f149293.jpg
         * doorUrl : http://47.114.1.170/lianxiangguanwang/pics/04cc5449-3b24-4cfa-81ad-fdcd737b64d09903.jpg
         * licenseUrl : http://47.114.1.170/lianxiangguanwang/pics/39a9d4b9-3758-42b7-b87f-19bc349b54e01476.jpg
         * setTime : 2020-07-30T01:34:40.000+0000
         * checkStatus : 1
         * address : 洛阳市涧西区九都西路太原路口浅井头村3号楼佰嘉宜超市
         * scope : 112.394552,34.644045
         * code : 4566
         */

        private String id;
        private String username;
        private String password;
        private String phone;
        private String token;
        private String headUrl;
        private String doorUrl;
        private String licenseUrl;
        private String setTime;
        private int checkStatus;
        private String address;
        private String scope;
        private String code;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getUsername() {
            return username;
        }

        public void setUsername(String username) {
            this.username = username;
        }

        public String getPassword() {
            return password;
        }

        public void setPassword(String password) {
            this.password = password;
        }

        public String getPhone() {
            return phone;
        }

        public void setPhone(String phone) {
            this.phone = phone;
        }

        public String getToken() {
            return token;
        }

        public void setToken(String token) {
            this.token = token;
        }

        public String getHeadUrl() {
            return headUrl;
        }

        public void setHeadUrl(String headUrl) {
            this.headUrl = headUrl;
        }

        public String getDoorUrl() {
            return doorUrl;
        }

        public void setDoorUrl(String doorUrl) {
            this.doorUrl = doorUrl;
        }

        public String getLicenseUrl() {
            return licenseUrl;
        }

        public void setLicenseUrl(String licenseUrl) {
            this.licenseUrl = licenseUrl;
        }

        public String getSetTime() {
            return setTime;
        }

        public void setSetTime(String setTime) {
            this.setTime = setTime;
        }

        public int getCheckStatus() {
            return checkStatus;
        }

        public void setCheckStatus(int checkStatus) {
            this.checkStatus = checkStatus;
        }

        public String getAddress() {
            return address;
        }

        public void setAddress(String address) {
            this.address = address;
        }

        public String getScope() {
            return scope;
        }

        public void setScope(String scope) {
            this.scope = scope;
        }

        public String getCode() {
            return code;
        }

        public void setCode(String code) {
            this.code = code;
        }
    }
}
