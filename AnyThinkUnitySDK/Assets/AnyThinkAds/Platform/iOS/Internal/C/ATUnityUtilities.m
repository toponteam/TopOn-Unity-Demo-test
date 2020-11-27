//
//  ATUnityUtilities.m
//  UnityContainer
//
//  Created by Martin Lau on 14/08/2018.
//  Copyright © 2018 Martin Lau. All rights reserved.
//

#import "ATUnityUtilities.h"
NSString *const kATUnityUtilitiesInterstitialImpressionNotification = @"com.anythink.kATUnityUtilitiesInterstitialImpressionNotification";
NSString *const kATUnityUtilitiesInterstitialCloseNotification = @"kATUnityUtilitiesInterstitialCloseNotification";
NSString *const kATUnityUtilitiesRewardedVideoImpressionNotification = @"kATUnityUtilitiesRewardedVideoImpressionNotification";
NSString *const kATUnityUtilitiesRewardedVideoCloseNotification = @"kATUnityUtilitiesRewardedVideoCloseNotification";
NSString *const kATUnityUtilitiesAdShowingExtraScenarioKey = @"Scenario";

@implementation ATUnityUtilities
+(BOOL)isEmpty:(id)object {
    return (object == nil || [object isKindOfClass:[NSNull class]] || ([object respondsToSelector:@selector(length)] && [(NSData *)object length] == 0) || ([object respondsToSelector:@selector(count)] && [(NSArray *)object count] == 0));
}
@end
@implementation NSDictionary (KAKit)
-(NSString*) jsonString {
    NSError *error;
    NSData *jsonData = [NSJSONSerialization dataWithJSONObject:self
                                                       options:(NSJSONWritingOptions)NSJSONWritingPrettyPrinted
                                                         error:&error];
    
    if (!jsonData) {
        return @"{}";
    } else {
        return [[NSString alloc] initWithData:jsonData encoding:NSUTF8StringEncoding];
    }
}

-(BOOL)containsObjectForKey:(id)key {
    return [self.allKeys containsObject:key];
}
@end

@implementation NSData(ATKit)
+(instancetype) dataWithUTF8String:(const char*)string {
    return [[NSString stringWithUTF8String:string] dataUsingEncoding:NSUTF8StringEncoding];
}
@end
@implementation UIColor (Hex)
// 透明度固定为1，以0x开头的十六进制转换成的颜色
+ (UIColor*) colorWithHex:(long)hexColor;
{
    return [UIColor colorWithHex:hexColor alpha:1.];
}
// 0x开头的十六进制转换成的颜色,透明度可调整
+ (UIColor *)colorWithHex:(long)hexColor alpha:(float)opacity
{
    float red = ((float)((hexColor & 0xFF0000) >> 16))/255.0;
    float green = ((float)((hexColor & 0xFF00) >> 8))/255.0;
    float blue = ((float)(hexColor & 0xFF))/255.0;
    return [UIColor colorWithRed:red green:green blue:blue alpha:opacity];
}
// 颜色转换三：iOS中十六进制的颜色（以#开头）转换为UIColor
+ (UIColor *) colorWithHexString: (NSString *)color
{
    NSString *cString = [[color stringByTrimmingCharactersInSet:[NSCharacterSet whitespaceAndNewlineCharacterSet]] uppercaseString];
    
    // String should be 6 or 8 characters
    if ([cString length] < 6) {
        return [UIColor clearColor];
    }
    
    // 判断前缀并剪切掉
    if ([cString hasPrefix:@"0X"])
        cString = [cString substringFromIndex:2];
    if ([cString hasPrefix:@"#"])
        cString = [cString substringFromIndex:1];
    if ([cString length] != 6)
        return [UIColor clearColor];
    
    // 从六位数值中找到RGB对应的位数并转换
    NSRange range;
    range.location = 0;
    range.length = 2;
    
    //R、G、B
    NSString *rString = [cString substringWithRange:range];
    
    range.location = 2;
    NSString *gString = [cString substringWithRange:range];
    
    range.location = 4;
    NSString *bString = [cString substringWithRange:range];
    
    // Scan values
    unsigned int r, g, b;
    [[NSScanner scannerWithString:rString] scanHexInt:&r];
    [[NSScanner scannerWithString:gString] scanHexInt:&g];
    [[NSScanner scannerWithString:bString] scanHexInt:&b];
    
    return [UIColor colorWithRed:((float) r / 255.0f) green:((float) g / 255.0f) blue:((float) b / 255.0f) alpha:1.0f];
}
@end
