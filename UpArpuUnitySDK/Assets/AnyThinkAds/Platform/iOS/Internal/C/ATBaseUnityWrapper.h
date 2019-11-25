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
-(void) invokeCallback:(NSString*)callback placementID:(NSString*)placementID error:(NSError*)error extra:(NSDictionary*)extra;
@end
