#import "ReactNativeMiniprogram.h"
#import <WXApi.h>
#define INVOKE_FAILED (@"WeChat API invoke returns false.")

@implementation ReactNativeMiniprogram

RCT_EXPORT_MODULE("ReactNativeMiniprogram")

RCT_EXPORT_METHOD(launchWXMiniProgram:(NSString*)appId:(NSString *)username:(NSString*)path:(NSString*)type:(RCTResponseSenderBlock)callback){
  
    NSUInteger miniProgramType = [type integerValue];
    WXLaunchMiniProgramReq *launchMiniProgramReq = [WXLaunchMiniProgramReq object];
    launchMiniProgramReq.userName = appId;
    launchMiniProgramReq.miniProgramType = miniProgramType;
    if(path != nil){
        launchMiniProgramReq.path = path;
    }
    BOOL success =  [WXApi sendReq:launchMiniProgramReq];
    callback(@[success ? [NSNull null] : INVOKE_FAILED]);
  
}

@end
