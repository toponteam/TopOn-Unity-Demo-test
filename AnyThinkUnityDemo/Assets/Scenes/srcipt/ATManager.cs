using UnityEngine;
using System;
using System.Collections.Generic;
using AnyThinkAds.Android;
using AnyThinkAds.Api;
using System.Runtime.InteropServices;


#if UNITY_IPHONE || UNITY_ANDROID

#if UNITY_IPHONE



#elif UNITY_ANDROID

using MSGTOOLS 		= ATMsgTools;

#endif


namespace AnyThinkAds.Demo
{
	public class ATManager 
	{
		#if UNITY_ANDROID
			public static MSGTOOLS msgTools;
		#endif



		private static ATSDKAPIClient sdkInitHelper;
		public static void loadToolsPlugins ()
		{
			#if UNITY_ANDROID
				msgTools = new MSGTOOLS ();
				init();
			#endif
		}
			

		public static void printLogI (string msg)
		{
			#if UNITY_ANDROID
				msgTools.printLogI (msg);
			#endif
		}


		public static void init(){
			Debug.Log ("manager init---->");
		

			if (sdkInitHelper == null) {
				sdkInitHelper = new ATSDKAPIClient ();
			}
		
		}
		public static ATNativeAdView anyThinkNativeAdView ;

	}
}


#endif
