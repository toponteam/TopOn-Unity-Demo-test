using System.Collections;
using System.Collections.Generic;
using UnityEngine;
using UnityEngine.SceneManagement;
using AnyThinkAds.Api;
public class MainScenes : MonoBehaviour {

	// Use this for initialization
	void Start () {
        AnyThinkAds.Demo.ATManager.loadToolsPlugins ();
	}
	
	// Update is called once per frame
	void Update () {
		
	}

	public void gotoNativeScenes(){
//		DebugConsole.Log ("gotoNativeScenes");
		Debug.Log ("Developer gotoNativeScenes");
		#if UNITY_ANDROID
            AnyThinkAds.Demo.ATManager.msgTools.printLogI ("gotoNativeScenes....");
		#endif

		SceneManager.LoadScene ("nativeMainScenes");
	}
	public void gotoVideoScenes(){
//		DebugConsole.Log ("gotoVideoScenes");
		Debug.Log ("Developer gotoVideoScenes");
		#if UNITY_ANDROID
            AnyThinkAds.Demo.ATManager.msgTools.printLogI ("gotoVideoScenes....");
		#endif
		SceneManager.LoadScene ("videoMainScenes");
	}

    public void gotoBannerScences(){
        Debug.Log("Developer gotoBannerScenes");
        SceneManager.LoadScene("bannerMainScenes");
    }

    public void gotoInterstitialScences()
    {
        Debug.Log("Developer gotoInterstitialScences");
        SceneManager.LoadScene("interstitialMainScenes");
    }

    public void gotoNativeBannerScene() {
    	Debug.Log("Developer gotoNativeBannerScene");
        SceneManager.LoadScene("NativeBannerScene");
    }

    private class InitListener : ATSDKInitListener
    {
        public void initSuccess()
        {
			Debug.Log("Developer Develop callback SDK initSuccess");
        }
        public void initFail(string msg)
        {
			Debug.Log("Developer callback SDK initFail:" + msg);
        }
    }

	public void initSDK(){
//		DebugConsole.Log ("init sdk");
		Debug.Log("Developer Version of the runtime: " + Application.unityVersion);
		Debug.Log ("Developer init sdk");
		Debug.Log(" Developer Screen size : {" + Screen.width + ", " + Screen.height + "}");
        ATSDKAPI.setChannel("unity3d_test_channel");
        ATSDKAPI.initCustomMap(new Dictionary<string, string> { { "unity3d_data", "test_data" } });
        ATSDKAPI.setLogDebug(true);
       

#if UNITY_ANDROID
       ATSDKAPI.initSDK("a5aa1f9deda26d", "4f7b9ac17decb9babec83aac078742c7", new InitListener());
#elif UNITY_IOS || UNITY_IPHONE
        ATSDKAPI.initSDK("a5b0e8491845b3", "7eae0567827cfe2b22874061763f30c9", new InitListener());
#endif
}

	public void showGDPRAuth(){
		Debug.Log ("Developer showGDPRAuth");
	
		addNetworkGDPR();
        ATSDKAPI.showGDPRAuth();


	}

	private void addNetworkGDPR(){
		Debug.Log ("Developer addNetworkGDPR");
		//gdpr
		Dictionary<string,object> dictionary ;

		#if UNITY_ANDROID
		//admob
		dictionary = new Dictionary<string,object> ();
		dictionary.Add (ATConst.NEWWORK_GDPR_KEY.ADMOB_KEY_ALLOW_GDPR, "true");//是否同意gdpr 
		ATSDKAPI.addNetworkGDPRInfo (ATConst.NETWORK_TYPE.NETWORK_ADMOB,dictionary);
		 
		//inmobi
		dictionary = new Dictionary<string,object> ();
		dictionary.Add (ATConst.NEWWORK_GDPR_KEY.INMOBI_KEY_ALLOW_GDPR, "true");//是否同意gdpr 
		dictionary.Add (ATConst.NEWWORK_GDPR_KEY.INMOBI_KEY_ISGDPRSCOPE, "1");//是否gdpr地区  1:是
		ATSDKAPI.addNetworkGDPRInfo (ATConst.NETWORK_TYPE.NETWORK_INMOBI,dictionary);


		dictionary = new Dictionary<string,object> ();
		//iba string
		dictionary.Add (ATConst.NEWWORK_GDPR_KEY.FLURRY_KEY_GDPR_IABSTR, "");//iba串 符合iba协议的
		dictionary.Add (ATConst.NEWWORK_GDPR_KEY.FLURRY_KEY_ISGDPRSCOPE, "true");//是否在gdpr地区
		ATSDKAPI.addNetworkGDPRInfo (ATConst.NETWORK_TYPE.NETWORK_FLURRY,dictionary);


		dictionary = new Dictionary<string,object> ();
		dictionary.Add (ATConst.NEWWORK_GDPR_KEY.APPLOVIN_KEY_ALLOW_GDPR, "true");//是否同意gdpr 
		ATSDKAPI.addNetworkGDPRInfo (ATConst.NETWORK_TYPE.NETWORK_APPLOVIN,dictionary);


		dictionary = new Dictionary<string,object> ();
		dictionary.Add (ATConst.NEWWORK_GDPR_KEY.MINTEGRAL_KEY_ALLOW_GDPR, "1");//是否同意gdpr  1同意
		ATSDKAPI.addNetworkGDPRInfo (ATConst.NETWORK_TYPE.NETWORK_MINTEGRAL,dictionary);


		dictionary = new Dictionary<string,object> ();
		dictionary.Add (ATConst.NEWWORK_GDPR_KEY.MOPUB_KEY_ALLOW_GDPR, "true");//是否同意gdpr 
		ATSDKAPI.addNetworkGDPRInfo (ATConst.NETWORK_TYPE.NETWORK_MOPUB,dictionary);



		dictionary = new Dictionary<string,object> ();
		dictionary.Add (ATConst.NEWWORK_GDPR_KEY.CHARTBOOST_KEY_ALLOW_GDPR, "true");//是否同意gdpr 
		ATSDKAPI.addNetworkGDPRInfo (ATConst.NETWORK_TYPE.NETWORK_CHARTBOOST,dictionary);


		dictionary = new Dictionary<string,object> ();
		dictionary.Add (ATConst.NEWWORK_GDPR_KEY.TAPJOY_KEY_ALLOW_GDPR, "1");//是否同意gdpr 
		dictionary.Add (ATConst.NEWWORK_GDPR_KEY.TAPJOY_KEY_ISGDPRSCOPE, "true");//是否在gdpr地区
		ATSDKAPI.addNetworkGDPRInfo (ATConst.NETWORK_TYPE.NETWORK_TAPJOY,dictionary);


		dictionary = new Dictionary<string,object> ();
		dictionary.Add (ATConst.NEWWORK_GDPR_KEY.IRONSOURCE_KEY_ALLOW_GDPR, "true");//是否同意gdpr 
		ATSDKAPI.addNetworkGDPRInfo (ATConst.NETWORK_TYPE.NETWORK_IRONSOURCE,dictionary);


		dictionary = new Dictionary<string,object> ();
		dictionary.Add (ATConst.NEWWORK_GDPR_KEY.UNITYADS_KEY_ALLOW_GDPR, "true");//是否同意gdpr 
		ATSDKAPI.addNetworkGDPRInfo (ATConst.NETWORK_TYPE.NETWORK_UNITYADS,dictionary);


		dictionary = new Dictionary<string,object> ();
		dictionary.Add (ATConst.NEWWORK_GDPR_KEY.VUNGLE_KEY_ALLOW_GDPR, "true");//是否同意gdpr 
		ATSDKAPI.addNetworkGDPRInfo (ATConst.NETWORK_TYPE.NETWORK_VUNGLE,dictionary);


		dictionary = new Dictionary<string,object> ();
		dictionary.Add (ATConst.NEWWORK_GDPR_KEY.ADCOLONY_KEY_ALLOW_GDPR, "1");//是否同意gdpr  1同意
		dictionary.Add (ATConst.NEWWORK_GDPR_KEY.ADCOLONY_KEY_ISGDPRSCOPE, "true");//是否在gdpr地区
		ATSDKAPI.addNetworkGDPRInfo (ATConst.NETWORK_TYPE.NETWORK_ADCOLONY,dictionary);

		#elif UNITY_IOS || UNITY_IPHONE
        //Admob
        ATSDKAPI.addNetworkGDPRInfo (ATConst.NETWORK_TYPE.NETWORK_ADMOB, new Dictionary<string, object>{{"consent_status", 2}, {"under_age", false}});
         
        //Inmobi
        ATSDKAPI.addNetworkGDPRInfo (ATConst.NETWORK_TYPE.NETWORK_INMOBI, new Dictionary<string, object>{{"gdpr", "0"}, {"consent_string", "true"}});

        //Flurry
        ATSDKAPI.addNetworkGDPRInfo (ATConst.NETWORK_TYPE.NETWORK_FLURRY, new Dictionary<string, object>{{"scope_flag", false}, {"consent_string", ""}});

        //Applovin
        ATSDKAPI.addNetworkGDPRInfo (ATConst.NETWORK_TYPE.NETWORK_APPLOVIN, new Dictionary<string, object>{{"under_age", "0"}, {"consent_status", "0"}});

        //Mintegral
        ATSDKAPI.addNetworkGDPRInfo (ATConst.NETWORK_TYPE.NETWORK_MINTEGRAL, new Dictionary<string, object>{{"0", "1"}});

        //Mopub
        ATSDKAPI.addNetworkGDPRInfo (ATConst.NETWORK_TYPE.NETWORK_MOPUB, new Dictionary<string, object>{{"value", "1"}});

        //Chartboost
        ATSDKAPI.addNetworkGDPRInfo (ATConst.NETWORK_TYPE.NETWORK_CHARTBOOST, new Dictionary<string, object>{{"value", true}});
        
        //Tapjoy
        ATSDKAPI.addNetworkGDPRInfo (ATConst.NETWORK_TYPE.NETWORK_TAPJOY, new Dictionary<string, object>{{"consent_value", "1"}, {"gdpr_subjection", false}});

        //Ironsource
        ATSDKAPI.addNetworkGDPRInfo (ATConst.NETWORK_TYPE.NETWORK_IRONSOURCE, new Dictionary<string, object>{{"value", true}});

        //UnityAds
        ATSDKAPI.addNetworkGDPRInfo (ATConst.NETWORK_TYPE.NETWORK_UNITYADS, new Dictionary<string, object>{{"value", true}});

        //Vungle
        ATSDKAPI.addNetworkGDPRInfo (ATConst.NETWORK_TYPE.NETWORK_VUNGLE, new Dictionary<string, object>{{"value", 1}});

        //AdColony
        ATSDKAPI.addNetworkGDPRInfo (ATConst.NETWORK_TYPE.NETWORK_ADCOLONY, new Dictionary<string, object>{{"gdpr_consideration_flag", 1}, {"consent_string", ""}});
        #endif


	}
}
 