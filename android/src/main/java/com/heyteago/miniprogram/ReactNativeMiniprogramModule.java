package com.heyteago.miniprogram;

import com.facebook.react.bridge.Arguments;
import com.facebook.react.bridge.Promise;
import com.facebook.react.bridge.ReactApplicationContext;
import com.facebook.react.bridge.ReactContextBaseJavaModule;
import com.facebook.react.bridge.ReactMethod;
import com.facebook.react.bridge.WritableMap;
import com.tencent.mm.opensdk.modelbase.BaseReq;
import com.tencent.mm.opensdk.modelbase.BaseResp;
import com.tencent.mm.opensdk.modelbiz.WXInvoiceAuthInsert;
import com.tencent.mm.opensdk.modelbiz.WXLaunchMiniProgram;
import com.tencent.mm.opensdk.openapi.IWXAPI;
import com.tencent.mm.opensdk.openapi.IWXAPIEventHandler;
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
     *
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

    /**
     * 打开授权页
     *
     * @param appId   应用AppId
     * @param url     获取授权页链接返回的auth_url
     * @param promise
     */
    @ReactMethod
    public void openAuthPage(String appId, String url, final Promise promise) {
        try {
            WXInvoiceAuthInsert.Req req = new WXInvoiceAuthInsert.Req();
            req.url = url;
            IWXAPI api = WXAPIFactory.createWXAPI(reactContext, appId);
            api.handleIntent(getCurrentActivity().getIntent(), new IWXAPIEventHandler() {
                @Override
                public void onReq(BaseReq baseReq) {

                }

                @Override
                public void onResp(BaseResp baseResp) {
                    if (baseResp.getClass().equals(WXInvoiceAuthInsert.Resp.class)) {
                        WXInvoiceAuthInsert.Resp oResp = (WXInvoiceAuthInsert.Resp) baseResp;
                        String sLog = "errcode:" + oResp.errCode + " wxorderid:" + oResp.wxOrderId;
                        System.out.print(sLog);
                        WritableMap writableMap = Arguments.createMap();
                        writableMap.putInt("errCode", oResp.errCode);
                        writableMap.putString("wxOrderId", oResp.wxOrderId);
                        promise.resolve(writableMap);
                    }
                }
            });
            api.sendReq(req);
        } catch (Exception e) {
            promise.reject(new Throwable(e.getMessage()));
        }
    }
}
