//
//  ATUnityWrapper.h
//  ATSDK
//
//  Created by Martin Lau on 08/08/2018.
//  Copyright © 2018 Martin Lau. All rights reserved.
//

#ifndef ATUnityWrapper_h
#define ATUnityWrapper_h
@protocol ATUnityWrapper<NSObject>
+(instancetype) sharedInstance;
@optional
-(void) setCallBack:(void(*)(const char*, const char *))callback forKey:(NSString*)key;
-(void) removeCallbackForKey:(NSString*)key;
-(void(*)(const char*, const char *)) callbackForKey:(NSString*)key;
@end

#endif /* ATUnityWrapper_h */
