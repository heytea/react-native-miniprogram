#import "ReactNativeMiniprogram.h"
#import <WXApi.h>
#define INVOKE_FAILED (@"WeChat API invoke returns false.")

@implementation ReactNativeMiniprogram

RCT_EXPORT_MODULE(ReactNativeMiniprogram)

RCT_EXPORT_METHOD(launchWXMiniProgram
                  :(NSString*)appId
                  :(NSString *)username
                  :(NSString*)path
                  :(NSString*)type
                  :(RCTResponseSenderBlock)callback){
  
    NSUInteger miniProgramType = [type integerValue];
    WXLaunchMiniProgramReq *launchMiniProgramReq = [WXLaunchMiniProgramReq object];
    launchMiniProgramReq.userName = appId;
    launchMiniProgramReq.miniProgramType = miniProgramType;
    if(path != nil){
        launchMiniProgramReq.path = path;
    }
    
    [WXApi sendReq:launchMiniProgramReq completion:^(BOOL success) {
        callback(@[success ? [NSNull null] : INVOKE_FAILED]);
    }];
   
  
}

RCT_EXPORT_METHOD(openAuthPage:(NSString *)appId
                  :(NSString *)url
                  :(RCTResponseSenderBlock)callback
                  ){
    
    WXInvoiceAuthInsertReq *req = [[WXInvoiceAuthInsertReq alloc] init];
    req.urlString = url;
    
    [WXApi sendReq:req completion:^(BOOL success) {
        callback(@[success ? [NSNull null] : INVOKE_FAILED]);
    }];
    
}

- (void) onResp:(BaseResp *)resp
{
    if ([resp isKindOfClass:[WXInvoiceAuthInsertResp class]]) {
        WXInvoiceAuthInsertResp *wxResp = (WXInvoiceAuthInsertResp *) resp;
        NSString *strTitle = @"微信回跳";
        NSString *strMsg = [NSString stringWithFormat:@"errcode: %d orderid:%@", wxResp.errCode, wxResp.wxOrderId];
        UIAlertView *alert = [[UIAlertView alloc] initWithTitle:strTitle message:strMsg delegate:self cancelButtonTitle:@"取消" otherButtonTitles:@"确定", nil];
        [alert show];
    }
}

@end

