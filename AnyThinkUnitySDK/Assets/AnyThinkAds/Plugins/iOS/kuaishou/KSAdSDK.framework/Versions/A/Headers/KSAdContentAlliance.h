//
//  KSAdContentAlliance.h
//  Aspects
//
//  Created by xuzhijun on 2020/1/9.
//

#import <UIKit/UIKit.h>

NS_ASSUME_NONNULL_BEGIN

@interface KSAdContentAlliance : NSObject

@property (nonatomic, readonly) UIViewController *viewController;

- (instancetype)initWithPosId:(NSString *)posId;

@end

NS_ASSUME_NONNULL_END
