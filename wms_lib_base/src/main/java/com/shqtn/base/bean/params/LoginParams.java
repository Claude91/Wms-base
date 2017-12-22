package com.shqtn.base.bean.params;

/**
 * Created by android on 2017/7/13.
 */

public class LoginParams {
    /**
     * 用户账号
     */
    private String loginName;
    private String loginPass;//需要加密后

    public String getLoginName() {
        return loginName;
    }

    public void setLoginName(String loginName) {
        this.loginName = loginName;
    }

    public String getLoginPass() {
        return loginPass;
    }

    public void setLoginPass(String loginPass) {
        this.loginPass = loginPass;
    }

}
