using System.Collections;
using System.Collections.Generic;
using UnityEngine;

namespace AnyThinkAds.Api
{
	public class ATConst  {
		public class NETWORK_TYPE{
			public const  int NETWORK_UNKNOW = -1; //未知类型
			public const  int NETWORK_FACEBOOK = 1;//FACEBOOK 
			public const  int NETWORK_ADMOB = 2; 
			public const  int NETWORK_INMOBI = 3;
			public const  int NETWORK_FLURRY = 4;
			public const  int NETWORK_APPLOVIN = 5;
			public const  int NETWORK_MINTEGRAL = 6;
			public const  int NETWORK_MOPUB = 7;
			public const  int NETWORK_GDT = 8; //广点通
			public const  int NETWORK_CHARTBOOST = 9;
			public const  int NETWORK_TAPJOY = 10;
			public const  int NETWORK_IRONSOURCE = 11;
			public const  int NETWORK_UNITYADS = 12;
			public const  int NETWORK_VUNGLE = 13;
			public const  int NETWORK_ADCOLONY = 14;
			public const  int NETWORK_TOUTIAO = 15;	
		}
		public class NEWWORK_GDPR_KEY{
			public const string  ADMOB_KEY_ALLOW_GDPR = "ConsentStaus"; //VALUE is bool  true|false

			public const string  APPLOVIN_KEY_ALLOW_GDPR = "HasUserConstent"; // value is bool true|fase

			public const string  ADCOLONY_KEY_ALLOW_GDPR= "GDPRConstentString";//value is int  1|0
			public const string  ADCOLONY_KEY_ISGDPRSCOPE = "GDPRRequired";//value is bool true|fase

			public const string  FLURRY_KEY_GDPR_IABSTR= "ConsentString";//value is IABstring 
			public const string  FLURRY_KEY_ISGDPRSCOPE = "GDPRScope";//value is bool true|fase

			public const string  INMOBI_KEY_ISGDPRSCOPE= "GDPRScope";//value is int   1|0 
			public const string  INMOBI_KEY_ALLOW_GDPR = "GDPRAvailable";//value is bool true|fase

			public const string  IRONSOURCE_KEY_ALLOW_GDPR = "Consent";//value is bool true|fase


			public const string  MINTEGRAL_KEY_ALLOW_GDPR= "GDPRAvailable";//value is int   1|0 

	
			public const string  MOPUB_KEY_ALLOW_GDPR = "Consent";//value is bool true|fase

			public const string  TAPJOY_KEY_ALLOW_GDPR = "userConsent";//value is int   1 同意收集|0 
			public const string  TAPJOY_KEY_ISGDPRSCOPE = "subjectGDPR";//value is bool true|fase

			public const string  UNITYADS_KEY_ALLOW_GDPR = "GDPRConsent";//value is bool true|fase


			public const string  VUNGLE_KEY_ALLOW_GDPR = "Consent";//value is bool true|fase

			public const string  CHARTBOOST_KEY_ALLOW_GDPR = "restrictData";//value is bool true|fase


		}

        public const string SCENARIO = "Scenario";//value is string
        public const string USERID_KEY = "UserId";//value is string
        public const string USER_EXTRA_DATA = "UserExtraData"; //value is string
        public const string USE_REWARDED_VIDEO_AS_INTERSTITIAL = "UseRewardedVideoAsInterstitial";//value is string
        public const string USE_REWARDED_VIDEO_AS_INTERSTITIAL_YES = "1";
        public const string USE_REWARDED_VIDEO_AS_INTERSTITIAL_NO = "0";

        public const string WIDTH = "Width";//value is string
        public const string HEIGHT = "Height";//value is string
    }
}
