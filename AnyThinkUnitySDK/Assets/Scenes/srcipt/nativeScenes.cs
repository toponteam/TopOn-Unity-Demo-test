using System.Collections;
using System.Collections.Generic;
using UnityEngine;
using UnityEngine.SceneManagement;
using AnyThinkAds.Api;
using UnityEngine.UI;

using AnyThinkAds.ThirdParty.MiniJSON;


public class nativeScenes : MonoBehaviour {




#if UNITY_ANDROID
    static string mPlacementId_native_all = "b5aa1fa2cae775";

#elif UNITY_IOS || UNITY_IPHONE
    static string mPlacementId_native_all = "b5b0f5663c6e4a";//gdt template
    // static string mPlacementId_native_all = "b5e4613e50cbf2";

#endif


	// Use this for initialization
	void Start () {
	}
	
	// Update is called once per frame
	void Update () {
		
	}

	public void gotoMainScenes(){
		Debug.Log ("Developer gotoMainScenes");
		AnyThinkAds.Demo.ATManager.printLogI ("Developer gotoMainScenes....");
		SceneManager.LoadScene ("demoMainScenes");
	}
	public void gotoVideoScenes(){
		Debug.Log ("Developer gotoVideoScenes");
		AnyThinkAds.Demo.ATManager.printLogI ("Developer gotoVideoScenes....");
		SceneManager.LoadScene ("videoMainScenes");
	}


    static ATNativeCallbackListener callbackListener;

	public void loadNative() {
        Debug.Log ("Developer load native, placementId = " + mPlacementId_native_all);
   

        if(callbackListener == null) {
        	callbackListener = new ATNativeCallbackListener();
            ATNativeAd.Instance.setListener(callbackListener);
        }

        //new in v5.6.6
        Dictionary<string, object> jsonmap = new Dictionary<string, object>();
        ATSize nativeSize = new ATSize(320, 250, false);
        #if UNITY_ANDROID
            nativeSize = new ATSize(960, 150);
            jsonmap.Add(ATNativeAdLoadingExtra.kATNativeAdLoadingExtraNativeAdSizeStruct, nativeSize);
        #elif UNITY_IOS || UNITY_IPHONE
            jsonmap.Add(ATNativeAdLoadingExtra.kATNativeAdLoadingExtraNativeAdSizeStruct, nativeSize);
        
        #endif
        ATNativeAd.Instance.loadNativeAd(mPlacementId_native_all, jsonmap);

    }

    public int ColorToInt (Color c)
	{
		int retVal = 0;
		retVal |= Mathf.RoundToInt(c.r * 255f) << 24;
		retVal |= Mathf.RoundToInt(c.g * 255f) << 16;
		retVal |= Mathf.RoundToInt(c.b * 255f) << 8;
		retVal |= Mathf.RoundToInt(c.a * 255f);
		return retVal;
	}




	public void showNative(){
		Debug.Log ("Developer show native....");
		ATNativeConfig conifg = new ATNativeConfig ();

		string bgcolor = "#ffffff";
		string textcolor = "#000000";
		#if UNITY_ANDROID

		int rootbasex = 100, rootbasey = 100;
		//父框架
		int x = rootbasex,y = rootbasey,width = 300*3,height = 200*3,textsize = 17;
		conifg.parentProperty = new ATNativeItemProperty(x,y,width,height,bgcolor,textcolor,textsize);

		//adlogo 
		x = 0*3;y = 0*3;width = 30*3;height = 20*3;textsize = 17;
		conifg.adLogoProperty  = new ATNativeItemProperty(x,y,width,height,bgcolor,textcolor,textsize);


		//adicon
		x = 0*3;y = 50*3-50;width = 60*3;height = 50*3;textsize = 17;
		conifg.appIconProperty  = new ATNativeItemProperty(x,y,width,height,bgcolor,textcolor,textsize);

		//ad cta 
		x = 0*3;y = 150*3;width = 300*3;height = 50*3;textsize = 17;
		conifg.ctaButtonProperty  = new ATNativeItemProperty(x,y,width,height,"#ff21bcab","#ffffff",textsize);

		//ad desc
		x = 60*3;y = 100*3;width = 240*3-20;height = 50*3-10;textsize = 10;
		conifg.descProperty  = new ATNativeItemProperty(x,y,width,height,bgcolor,"#777777",textsize);


		//ad image
		x = 60*3;y = 0*3+20;width = 240*3-20;height = 100*3-10;textsize = 17;
		conifg.mainImageProperty  = new ATNativeItemProperty(x,y,width,height,bgcolor,textcolor,textsize);

		//ad title 
		x = 0*3;y = 100*3;width = 60*3;height = 50*3;textsize = 12;
		conifg.titleProperty  = new ATNativeItemProperty(x,y,width,height,bgcolor,textcolor,textsize);

		#elif UNITY_IOS || UNITY_IPHONE

		int rootbasex = 30, rootbasey = 90, totalWidth = Screen.width - 60, totalHeight = 350;
		//ad frame
		int x = rootbasex,y = rootbasey,width = totalWidth,height = totalHeight,textsize = 17;
		conifg.parentProperty = new ATNativeItemProperty(x,y,width,height,"clearColor",textcolor,textsize, true);

		//adlogo
		x = totalWidth - 30 - 15 - 15;y = totalHeight - 30 - 15;width = 30;height = 30;textsize = 17;
		conifg.adLogoProperty  = new ATNativeItemProperty(x,y,width,height,"nil",textcolor,textsize, true);

		//adicon
		x = 0;y = 0;width = 90;height = 90;textsize = 34;
		conifg.appIconProperty  = new ATNativeItemProperty(x,y,width,height,bgcolor,textcolor,textsize, true);

		//ad title
		x = width + 5;y = 0;width = totalWidth - 30 - x;height = 15 + 3;textsize = 18;
		conifg.titleProperty  = new ATNativeItemProperty(x,y,width,height,"clearColor",textcolor,textsize, true);

		//ad desc
		x = x;y = y + height + 5;width = width;height = 13;textsize = 10;
		conifg.descProperty  = new ATNativeItemProperty(x,y,width,height,bgcolor,"#777777",textsize, true);

		//ad cta
		x = x;y = y + height + 5;width = width;height = 15 + 3;textsize = 15;
		conifg.ctaButtonProperty  = new ATNativeItemProperty(x,y,width,height,"#ff21bcab","#ffffff",textsize, true);

		//ad image
		x = 0;y = y + height + 5;width = totalWidth - 30;height = totalHeight - y;textsize = 17;
		conifg.mainImageProperty  = new ATNativeItemProperty(x,y,width,height,bgcolor,textcolor,textsize, true);

		#endif

		ATNativeAdView anyThinkNativeAdView = new ATNativeAdView(conifg);
        AnyThinkAds.Demo.ATManager.anyThinkNativeAdView = anyThinkNativeAdView;


			
		Debug.Log("Developer renderAdToScene--->");
        ATNativeAd.Instance.renderAdToScene(mPlacementId_native_all, anyThinkNativeAdView);

	}
	public void cleanView(){
		Debug.Log ("Developer cleanView native....");
        ATNativeAd.Instance.cleanAdView(mPlacementId_native_all,AnyThinkAds.Demo.ATManager.anyThinkNativeAdView);
	}


	public void isAdReady(){
        bool isReady = ATNativeAd.Instance.hasAdReady(mPlacementId_native_all);
		Debug.Log("Developer isAdReady native....:" + isReady);
	}



    class ATNativeCallbackListener : ATNativeAdListener
    {

        public void onAdLoaded(string placementId)
        {
            Debug.Log("Developer onAdLoaded------:" + placementId);
           
        }
        public void onAdLoadFail(string placementId, string code, string message)
        {
            Debug.Log("Developer onAdLoadFail------:" + placementId + "--code:" + code + "--msg:" + message);
        }
        public void onAdImpressed(string placementId, ATCallbackInfo callbackInfo)
        {
            Debug.Log("Developer onAdImpressed------:" + placementId + "->" + Json.Serialize(callbackInfo.toDictionary()));
        }
        public void onAdClicked(string placementId, ATCallbackInfo callbackInfo)
        {
            Debug.Log("Developer onAdClicked------:" + placementId + "->" + Json.Serialize(callbackInfo.toDictionary()));
        }
        public void onAdVideoStart(string placementId)
        {
            Debug.Log("Developer onAdVideoStart------:" + placementId);
        }
        public void onAdVideoEnd(string placementId)
        {
            Debug.Log("Developer onAdVideoEnd------:" + placementId);
        }
        public void onAdVideoProgress(string placementId, int progress)
        {
            Debug.Log("Developer onAdVideoProgress------:" + placementId);
        }

        public void onAdCloseButtonClicked(string placementId, ATCallbackInfo callbackInfo)
        {
            Debug.Log("Developer onAdCloseButtonClicked------:" + placementId + "->" + Json.Serialize(callbackInfo.toDictionary()));
        }
    }
		
}
