using System.Collections;
using System.Collections.Generic;
using UnityEngine;

namespace AnyThinkAds.Api
{
    public interface ATRewardedVideoListener
    {
		/***
		 * The Ad load successfully (note: for Android, all callback methods are not in the main thread of Unity)
		 */
		void onRewardedVideoAdLoaded(string placementId);
		/***
		 * The Ad load fail (note: for Android, all callback methods are not in the main thread of Unity)
		 */
		void onRewardedVideoAdLoadFail(string placementId,string code, string message);
		/***
		 * The Ad play (note: for Android, all callback methods are not in the main thread of Unity)
		 */
		void onRewardedVideoAdPlayStart(string placementId, ATCallbackInfo callbackInfo);
		/***
		 * The Ad play end (note: for Android, all callback methods are not in the main thread of Unity)
		 */
		void onRewardedVideoAdPlayEnd(string placementId, ATCallbackInfo callbackInfo);
		/***
		 * The Ad play fail（note: for Android, all callback methods are not in the main thread of Unity）
		 * @param code error code
		 * @param message error message
		 */
		void onRewardedVideoAdPlayFail(string placementId,string code, string message);
		/**
		 * The Ad close（note: for Android, all callback methods are not in the main thread of Unity）
		 * @param isReward 
		 */
		void onRewardedVideoAdPlayClosed(string placementId,bool isReward, ATCallbackInfo callbackInfo);
		/***
		 * The Ad click（note: for Android, all callback methods are not in the main thread of Unity）
		 */
		void onRewardedVideoAdPlayClicked(string placementId, ATCallbackInfo callbackInfo);
		/**
         * The Ad reward（note: for Android, all callback methods are not in the main thread of Unity）
         */
		void onReward(string placementId, ATCallbackInfo callbackInfo);


		void startLoadingADSource(string placementId, ATCallbackInfo callbackInfo);

		void finishLoadingADSource(string placementId, ATCallbackInfo callbackInfo);

		void failToLoadADSource(string placementId,ATCallbackInfo callbackInfo,string code, string message);

		void startBiddingADSource(string placementId, ATCallbackInfo callbackInfo);

		void finishBiddingADSource(string placementId, ATCallbackInfo callbackInfo);

		void failBiddingADSource(string placementId,ATCallbackInfo callbackInfo,string code, string message);







    }

	public interface ATRewardedVideoExListener : ATRewardedVideoListener {
		/***
		 * The Ad play again (note: for Android, all callback methods are not in the main thread of Unity)
		 */
		void onRewardedVideoAdAgainPlayStart(string placementId, ATCallbackInfo callbackInfo);
		/***
		 * The Ad play end again(note: for Android, all callback methods are not in the main thread of Unity)
		 */
		void onRewardedVideoAdAgainPlayEnd(string placementId, ATCallbackInfo callbackInfo);
		/***
		 *  The Ad play fail again(note: for Android, all callback methods are not in the main thread of Unity)
		 */
		void onRewardedVideoAdAgainPlayFail(string placementId, string code, string message);

		/***
		 * The Ad click again(note: for Android, all callback methods are not in the main thread of Unity)
		 */
		void onRewardedVideoAdAgainPlayClicked(string placementId, ATCallbackInfo callbackInfo);
		/**
         * The Ad reward again(note: for Android, all callback methods are not in the main thread of Unity)
         */
		void onAgainReward(string placementId, ATCallbackInfo callbackInfo);

	}
}
