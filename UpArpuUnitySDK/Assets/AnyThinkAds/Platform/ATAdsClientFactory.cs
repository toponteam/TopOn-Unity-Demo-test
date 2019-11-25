using UnityEngine;
using AnyThinkAds.Api;
using AnyThinkAds.Common;

namespace AnyThinkAds
{
    public class ATAdsClientFactory
    {
        public static IATBannerAdClient BuildBannerAdClient()
        {
            #if UNITY_EDITOR
            // Testing UNITY_EDITOR first because the editor also responds to the currently
            // selected platform.
            #elif UNITY_ANDROID
                return new AnyThinkAds.Android.ATBannerAdClient();
            #elif (UNITY_5 && UNITY_IOS) || UNITY_IPHONE
                return new AnyThinkAds.iOS.ATBannerAdClient();
            #else

            #endif
            return null;
        }

        public static IATInterstitialAdClient BuildInterstitialAdClient()
        {
            #if UNITY_EDITOR
            // Testing UNITY_EDITOR first because the editor also responds to the currently
            // selected platform.
            #elif UNITY_ANDROID
                return new AnyThinkAds.Android.ATInterstitialAdClient();
            #elif (UNITY_5 && UNITY_IOS) || UNITY_IPHONE
                return new AnyThinkAds.iOS.ATInterstitialAdClient();
            #else

            #endif
            return null;
        }

        public static IATNativeAdClient BuildNativeAdClient()
        {
           #if UNITY_EDITOR
            // Testing UNITY_EDITOR first because the editor also responds to the currently
            // selected platform.
            #elif UNITY_ANDROID
                return new AnyThinkAds.Android.ATNativeAdClient();
            #elif (UNITY_5 && UNITY_IOS) || UNITY_IPHONE
                return new AnyThinkAds.iOS.ATNativeAdClient();
            #else

            #endif
            return null;
        }

        public static IATNativeBannerAdClient BuildNativeBannerAdClient()
        {
           #if UNITY_EDITOR
            // Testing UNITY_EDITOR first because the editor also responds to the currently
            // selected platform.
            #elif UNITY_ANDROID
                return new AnyThinkAds.Android.ATNativeBannerAdClient();
            #elif (UNITY_5 && UNITY_IOS) || UNITY_IPHONE
                return new AnyThinkAds.iOS.ATNativeBannerAdClient();
            #else

            #endif
            return null;
        }

        public static IATRewardedVideoAdClient BuildRewardedVideoAdClient()
        {
            #if UNITY_EDITOR
            // Testing UNITY_EDITOR first because the editor also responds to the currently
            // selected platform.

            #elif UNITY_ANDROID
                return new AnyThinkAds.Android.ATRewardedVideoAdClient();
            #elif (UNITY_5 && UNITY_IOS) || UNITY_IPHONE
                return new AnyThinkAds.iOS.ATRewardedVideoAdClient();            
            #else
                            
            #endif
            return null;
        }

        public static IATSDKAPIClient BuildSDKAPIClient()
        {
            Debug.Log("BuildSDKAPIClient");
            #if UNITY_EDITOR
                Debug.Log("Unity Editor");
                        // Testing UNITY_EDITOR first because the editor also responds to the currently
                        // selected platform.

            #elif UNITY_ANDROID
                return new AnyThinkAds.Android.ATSDKAPIClient();
            #elif (UNITY_5 && UNITY_IOS) || UNITY_IPHONE
                 Debug.Log("Unity:ATAdsClientFactory::Build iOS Client");
                return new AnyThinkAds.iOS.ATSDKAPIClient();         
            #else

            #endif
            return null;
        }

    }
}