package com.shqtn.base.bean;

/**
 * Created by android on 2017/7/11.
 */

public class UserClientBean {

    /**
     * userCode : QL2
     * userName : 帥哥
     * empCode : 10001
     * language : CHN
     * orgn : SYSTEM
     * deparment : 0301
     * token : TVRRNU9UYzFPVFEyTnpJeE1nPT0mWWpJMllqaGlPVEF0TmpZd1pDMHhNV1UzTFdKaU9HUXRNVEE1T0RNMllUTXlOemM1
     * ts : 1499759467212
     */

    private String userCode;//账号
    private String userName;//用户名
    private String empCode;//职位编码
    private String language;//语言
    private String orgn;
    private String deparment;
    private String token;
    private long ts;

    public String getUserCode() {
        return userCode;
    }

    public void setUserCode(String userCode) {
        this.userCode = userCode;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getEmpCode() {
        return empCode;
    }

    public void setEmpCode(String empCode) {
        this.empCode = empCode;
    }

    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }

    public String getOrgn() {
        return orgn;
    }

    public void setOrgn(String orgn) {
        this.orgn = orgn;
    }

    public String getDeparment() {
        return deparment;
    }

    public void setDeparment(String deparment) {
        this.deparment = deparment;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public long getTs() {
        return ts;
    }

    public void setTs(long ts) {
        this.ts = ts;
    }
}
