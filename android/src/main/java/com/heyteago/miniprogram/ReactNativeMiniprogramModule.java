package com.heyteago.miniprogram;

import com.facebook.react.bridge.Promise;
import com.facebook.react.bridge.ReactApplicationContext;
import com.facebook.react.bridge.ReactContextBaseJavaModule;
import com.facebook.react.bridge.ReactMethod;
import com.tencent.mm.opensdk.modelbiz.WXLaunchMiniProgram;
import com.tencent.mm.opensdk.openapi.IWXAPI;
import com.tencent.mm.opensdk.openapi.WXAPIFactory;

public class ReactNativeMiniprogramModule extends ReactContextBaseJavaModule {

    private final ReactApplicationContext reactContext;

    public ReactNativeMiniprogramModule(ReactApplicationContext reactContext) {
        super(reactContext);
        this.reactContext = reactContext;
    }

    @Override
    public String getName() {
        return "ReactNativeMiniprogram";
    }

    /**
     * App拉起微信小程序
     * @param appId            应用AppId
     * @param username         拉起的小程序的username
     * @param path             拉起小程序页面的可带参路径，不填默认拉起小程序首页，对于小游戏，可以只传入 query 部分，来实现传参效果，如：传入 "?foo=bar"
     * @param MINIPTOGRAM_TYPE 拉起小程序的类型。0：正式版。1：开发版。2：体验版
     */
    @ReactMethod
    public void launchWXMiniProgram(String appId, String username, String path, int MINIPTOGRAM_TYPE, Promise promise) {
        try {
            IWXAPI api = WXAPIFactory.createWXAPI(reactContext, appId);
            WXLaunchMiniProgram.Req req = new WXLaunchMiniProgram.Req();
            req.userName = username;
            req.path = path;
            req.miniprogramType = WXLaunchMiniProgram.Req.MINIPTOGRAM_TYPE_RELEASE;
            req.miniprogramType = MINIPTOGRAM_TYPE;
            api.sendReq(req);
            promise.resolve(null);
        } catch (Exception e) {
            promise.reject(new Throwable(e.getMessage()));
        }
    }
}
