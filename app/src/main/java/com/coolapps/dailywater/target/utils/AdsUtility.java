package com.coolapps.dailywater.target.utils;

import android.app.Activity;
import android.content.Context;
import android.widget.LinearLayout;
import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdSize;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.InterstitialAd;

public class AdsUtility {
    public static String Interstitial = "ca-app-pub-3940256099942544/1033173712";
    private static String admBanner = "ca-app-pub-3940256099942544/6300978111";
    public static String admobAppId = "ca-app-pub-3940256099942544~3347511713";
    public static InterstitialAd mInterstitialAd;

    public static void admobBannerCall(Activity acitivty, LinearLayout linerlayout) {
        AdView adView = new AdView(acitivty);
        adView.setAdUnitId(admBanner);
        adView.setAdSize(AdSize.SMART_BANNER);
        adView.loadAd(new AdRequest.Builder().build());
        linerlayout.addView(adView);
    }

    public static void InterstitialAdmob(Context context) {
        mInterstitialAd = new InterstitialAd(context);
        mInterstitialAd.setAdUnitId(Interstitial);
        mInterstitialAd.setAdListener(new AdListener() {
            public void onAdClosed() {
                AdsUtility.requestNewInterstitial();
            }
        });
        requestNewInterstitial();
    }

    protected static void requestNewInterstitial() {
        mInterstitialAd.loadAd(new AdRequest.Builder().build());
    }

    public static void showIntestitialAds() {
        if (mInterstitialAd.isLoaded()) {
            mInterstitialAd.show();
        }
    }
}
