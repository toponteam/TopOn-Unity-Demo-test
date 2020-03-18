//
//  ATBaseUnityWrapper.m
//  UnityContainer
//
//  Created by Martin Lau on 08/08/2018.
//  Copyright © 2018 Martin Lau. All rights reserved.
//

#import "ATBaseUnityWrapper.h"
#import "ATUnityUtilities.h"
@interface ATBaseUnityWrapper()
@property(nonatomic, readonly) NSMutableDictionary<NSString*, NSValue*> *callbacks;
@property(nonatomic, readonly) dispatch_queue_t callbackAccessQueue;
@end
@implementation ATBaseUnityWrapper
+(instancetype) sharedInstance {
    return nil;
}

-(instancetype) init {
    self = [super init];
    if (self != nil) {
        _callbacks = [NSMutableDictionary<NSString*, NSValue*> dictionary];
        _callbackAccessQueue = dispatch_queue_create("com.anythink.UnityPackage", DISPATCH_QUEUE_CONCURRENT);
    }
    return self;
}

-(void) setCallBack:(void (*)(const char *, const char *))callback forKey:(NSString *)key {
    __weak ATBaseUnityWrapper* weakSelf = self;
    if (callback != NULL && [key length] > 0)
        dispatch_barrier_async(_callbackAccessQueue, ^{
            weakSelf.callbacks[key] = [NSValue valueWithPointer:(void*)callback];
        });
}

-(void) removeCallbackForKey:(NSString *)key {
    __weak ATBaseUnityWrapper* weakSelf = self;
    if ([key length] > 0)
        dispatch_barrier_async(_callbackAccessQueue, ^{
            [weakSelf.callbacks removeObjectForKey:key];
        });
}

-(void(*)(const char*, const char *)) callbackForKey:(NSString*)key {
    __block void(*callback)(const char*, const char *) = NULL;
    if ([key length] > 0) {
        __weak ATBaseUnityWrapper* weakSelf = self;
        dispatch_barrier_sync(_callbackAccessQueue, ^{
            callback = (void(*)(const char*, const char *))[weakSelf.callbacks[key] pointerValue];
        });
    }
    return callback;
}

-(NSString*)scriptWrapperClass {
    return @"";
}

-(void) invokeCallback:(NSString*)callback placementID:(NSString*)placementID error:(NSError*)error extra:(NSDictionary*)extra {
    if ([self callbackForKey:placementID] != NULL) {
        if ([callback isKindOfClass:[NSString class]] && [callback length] > 0) {
            NSMutableDictionary *paraDict = [NSMutableDictionary dictionaryWithObject:callback forKey:@"callback"];
            NSMutableDictionary *msgDict = [NSMutableDictionary dictionary];
            if (extra != nil) {
                if (extra[@"extra"] != nil) {
                    msgDict[@"extra"] = extra[@"extra"];
                    msgDict[@"rewarded"] = extra[@"rewarded"];
                } else {
                    msgDict[@"extra"] = extra;
                }
            }
            paraDict[@"msg"] = msgDict;
            if ([placementID isKindOfClass:[NSString class]] && [placementID length] > 0) msgDict[@"placement_id"] = placementID;
            if ([error isKindOfClass:[NSError class]]) {
                NSMutableDictionary *errorDict = [NSMutableDictionary dictionaryWithObject:[NSString stringWithFormat:@"%ld", error.code] forKey:@"code"];
                if ([error.userInfo[NSLocalizedDescriptionKey] length] > 0) {
                    errorDict[@"desc"] = error.userInfo[NSLocalizedDescriptionKey];
                } else {
                    errorDict[@"desc"] = @"";
                }
                if ([error.userInfo[NSLocalizedFailureReasonErrorKey] length] > 0) {
                    errorDict[@"reason"] = error.userInfo[NSLocalizedFailureReasonErrorKey];
                } else {
                    errorDict[@"reason"] = @"";
                }
                msgDict[@"error"] = errorDict;
            }
            [self callbackForKey:placementID]([self scriptWrapperClass].UTF8String, paraDict.jsonString.UTF8String);
        }
    }
}
@end
