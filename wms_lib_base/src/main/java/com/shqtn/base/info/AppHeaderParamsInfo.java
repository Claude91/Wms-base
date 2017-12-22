package com.shqtn.base.info;

import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Build;

import com.shqtn.base.C;
import com.shqtn.base.bean.AppHttpHeaderBean;
import com.shqtn.base.utils.Base64Utils;
import com.shqtn.base.utils.HeaderParamsUtils;
import com.shqtn.base.utils.JsonUtils;
import com.shqtn.base.utils.StringUtils;

import java.util.IdentityHashMap;
import java.util.Map;



/**
 * 在MyApp 中对必要的请求头进行初始话，保存在内存中
 * 每次网络请求，httpService.post.build  将必要的参数添加到请求头中，
 * 因为每次请求都是必要的参数，所以将数据持久保存到内存中。
 * 如果保存到 shared文件，会导致每次都需要读取数据，导致频繁的连接，内存开销大。
 * 所以采取当前做法
 * Created by Administrator on 2016-11-17.
 */
public class AppHeaderParamsInfo {
    private static final String APP_NAME = "android";
    private static final String APP_SCOPE = "RF";
    private static AppHeaderParamsInfo mHeaderParamsInfo;

    public static AppHeaderParamsInfo getInstance() {
        if (mHeaderParamsInfo == null) {
            synchronized (AppHeaderParamsInfo.class) {
                if (mHeaderParamsInfo == null) {
                    mHeaderParamsInfo = new AppHeaderParamsInfo();
                }
            }
        }
        return mHeaderParamsInfo;
    }
    private Context mContext;
    private AppHttpHeaderBean mAppHttpHeaderBean;
    private String mTime;

    public static class InitParams{
        /**
         * 此初始化只需要在登录时进行初始化
         * @param applicationContext 是applicationContext;
         * @param time 当前时间
         */
        public static void init(Context applicationContext, long time){
            getInstance().initParams(applicationContext,time);
        }

        public static void init(Context applicationContext, long time, String token){
            getInstance().initParams(applicationContext,time);
            getInstance().setToken(token);

        }

        public static void setToken(String token){
            getInstance().setToken(token);
        }
    }


    /**
     * 此初始化只需要在登录时进行初始化
     * @param applicationContext 是applicationContext;
     * @param time 当前时间
     */
    private void initParams(Context applicationContext, long time) {
        mContext = applicationContext;

        mTime = String.valueOf(time);
        initHeaderBean();
    }

    private AppHeaderParamsInfo() {
    }


    private void initHeaderBean() {
        if (mAppHttpHeaderBean == null) {
            mAppHttpHeaderBean = new AppHttpHeaderBean();
        }else {
            mAppHttpHeaderBean.clear();
        }
        mAppHttpHeaderBean.setSysVersion(Build.VERSION.RELEASE);

        mAppHttpHeaderBean.setAppVersion(getAppVersion());

        mAppHttpHeaderBean.setAppName(APP_NAME);

        String appScope = Base64Utils.endBase64(APP_SCOPE, mTime);
        mAppHttpHeaderBean.setAppScope(appScope);

        String keyBase64 = Base64Utils.endBase64(C.APP_NORMAL_KEY, mTime);
        mAppHttpHeaderBean.setAppKey(keyBase64);

        String appTsBase64 = Base64Utils.singleBase64(mTime);
        mAppHttpHeaderBean.setAppTs(appTsBase64);

        String token = Base64Utils.endBase64(C.APP_NORMAL_TOKEN, mTime);
        mAppHttpHeaderBean.setAcccessToken(token);
    }


    /**
     * 获得app的版本号
     *
     * @return 当前的版本号
     * @throws PackageManager.NameNotFoundException
     */
    private String getAppVersion() {
        try {
            PackageManager packageManager =  mContext.getPackageManager();
            String packageName = mContext.getPackageName();

            PackageInfo pInfo = packageManager.getPackageInfo(packageName,PackageManager.COMPONENT_ENABLED_STATE_DEFAULT);

            String versionName = pInfo.versionName;
            return versionName;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        return null;
    }


    /**
     * 进行对请求回来的token 进行解析，并设置请求头中
     *
     * @param token
     * @return
     */
    private void setToken(String token) {
        if (StringUtils.isEmpty(token)){
            return;
        }
        token = Base64Utils.getFromBase64(token);
        //登录成功
        String[] str = token.split("&");
        String tokenBase64 = str[str.length - 1];
        token = Base64Utils.getFromBase64(tokenBase64);
        addAccessToken(token);
    }

    /**
     * 用户登录后 需要设置该token记录当前用户已经登录成功
     * @param appToken
     */
    private void addAccessToken(String appToken) {
        try {
            mAppHttpHeaderBean.setAcccessToken(Base64Utils.endBase64(appToken,mTime));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 获得系统名称
     *
     * @return app名称
     * @throws PackageManager.NameNotFoundException
     */
    private String getAppName() {
        try {
            String packageName = mContext.getPackageName();
            PackageManager packageManager = mContext.getPackageManager();
            ApplicationInfo applicationInfo = packageManager.getApplicationInfo(packageName, PackageManager.COMPONENT_ENABLED_STATE_DEFAULT);

            String appName = (String) packageManager.getApplicationLabel(applicationInfo);
            return appName;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        return null;
    }

    public void saveHeaderParams(Context context) {
        Map<String, String> headerMap = getHeaderParams();
        HeaderParamsUtils.save(context,headerMap);
    }


    public AppHttpHeaderBean getHttpHeader(Context context) {
        if (mAppHttpHeaderBean == null) {
            Map<String, String> map = HeaderParamsUtils.getHeaderParams(context);
            if (map != null) {
                String jsonMap = JsonUtils.toJson(map);
                mAppHttpHeaderBean = JsonUtils.getObject(jsonMap, AppHttpHeaderBean.class);
            }
        }
        return mAppHttpHeaderBean;
    }

    public Map<String, String> getHeaderParams() {
        Map<String, String> map = new IdentityHashMap<>();
        map.put(AppHttpHeaderBean.appScopeStr, mAppHttpHeaderBean.getAppScope());
        map.put(AppHttpHeaderBean.appKeyStr, mAppHttpHeaderBean.getAppKey());
        map.put(AppHttpHeaderBean.acccessTokenStr, mAppHttpHeaderBean.getAcccessToken());
        map.put(AppHttpHeaderBean.appNameStr, mAppHttpHeaderBean.getAppName());
        map.put(AppHttpHeaderBean.appVersionStr, mAppHttpHeaderBean.getAppVersion());
        map.put(AppHttpHeaderBean.sysVersionStr, mAppHttpHeaderBean.getSysVersion());
        map.put(AppHttpHeaderBean.appTsStr, mAppHttpHeaderBean.getAppTs());
        return map;
    }
}
