//
//  ATBaseUnityWrapper.h
//  UnityContainer
//
//  Created by Martin Lau on 08/08/2018.
//  Copyright © 2018 Martin Lau. All rights reserved.
//

#import <Foundation/Foundation.h>
#import "ATUnityWrapper.h"
@interface ATBaseUnityWrapper : NSObject<ATUnityWrapper>
-(NSString*)scriptWrapperClass;
-(id)selWrapperClassWithDict:(NSDictionary *)dict callback:(void(*)(const char*, const char*))callback;
-(void) invokeCallback:(NSString*)callback placementID:(NSString*)placementID error:(NSError*)error extra:(NSDictionary*)extra;
- (NSArray *)jsonStrToArray:(NSString *)jsonString;
@end
