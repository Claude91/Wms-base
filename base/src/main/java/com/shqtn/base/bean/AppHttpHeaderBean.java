package com.shqtn.base.bean;

/**
 * Created by Administrator on 2016-11-17.
 */
public class AppHttpHeaderBean {
    private String appScope;
    private String appKey;
    private String acccessToken;
    private String appName;
    private String appVersion;
    private String appTs;
    private String sysVersion;

    public String getSysVersion() {
        return sysVersion;
    }

    public void setSysVersion(String sysVersion) {
        this.sysVersion = sysVersion;
    }

    public final static String appScopeStr = "appScope";
    public final static String appKeyStr = "appKey";
    public final static String acccessTokenStr = "acccessToken";
    public final static String appNameStr = "appName";
    public final static String appVersionStr = "appVersion";
    public final static String appTsStr = "appTs";
    public final static String sysVersionStr ="sysVersion";

    @Override
    public String toString() {
        return "AppHttpHeaderBean{" +
                "sysVersion='" + sysVersion + '\'' +
                ", appScope='" + appScope + '\'' +
                ", appKey='" + appKey + '\'' +
                ", acccessToken='" + acccessToken + '\'' +
                ", appName='" + appName + '\'' +
                ", appVersion='" + appVersion + '\'' +
                ", appTs='" + appTs + '\'' +
                '}';
    }

    public String getAppScope() {
        return appScope;
    }

    public void setAppScope(String appScope) {
        this.appScope = appScope;
    }

    public String getAppKey() {
        return appKey;
    }

    public void setAppKey(String appKey) {
        this.appKey = appKey;
    }

    public String getAcccessToken() {
        return acccessToken;
    }

    public void setAcccessToken(String acccessToken) {
        this.acccessToken = acccessToken;
    }

    public String getAppName() {
        return appName;
    }

    public void setAppName(String appName) {
        this.appName = appName;
    }

    public String getAppVersion() {
        return appVersion;
    }

    public void setAppVersion(String appVersion) {
        this.appVersion = appVersion;
    }

    public String getAppTs() {
        return appTs;
    }

    public void setAppTs(String appTs) {
        this.appTs = appTs;
    }

    public void clear() {
        appKey = null;
        appName = null;
        appScope = null;
        appTs = null;
        appVersion = null;
        acccessToken = null;
        sysVersion = null;
    }
}
