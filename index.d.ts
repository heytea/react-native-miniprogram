
export enum MINIPTOGRAM_TYPE {
    MINIPTOGRAM_TYPE_RELEASE = 0,
    MINIPROGRAM_TYPE_TEST = 1,
    MINIPROGRAM_TYPE_PREVIEW = 2
}

/**
 * App跳转微信小程序
 * @param appId 应用AppId
 * @param username  拉起的小程序的username
 * @param path  拉起小程序页面的可带参路径，不填默认拉起小程序首页，对于小游戏，可以只传入 query 部分，来实现传参效果，如：传入 "?foo=bar"
 * @param type 拉起小程序的类型。0：正式版。1：开发版。2：体验版
 */
export function launchWXMiniProgram(appId: string, username: string, path: string, type: MINIPTOGRAM_TYPE): Promise<null>;