package com.detectspycamera.detectspycamera;

import android.util.DisplayMetrics;
import android.view.Display;
import android.widget.FrameLayout;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdSize;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.InterstitialAd;
import com.google.android.gms.ads.MobileAds;
import com.mopub.common.MoPub;
import com.mopub.common.SdkConfiguration;
import com.mopub.common.SdkInitializationListener;
import com.mopub.mobileads.GooglePlayServicesAdapterConfiguration;
import com.mopub.mobileads.MoPubErrorCode;
import com.mopub.mobileads.MoPubInterstitial;
import com.mopub.mobileads.MoPubView;

import java.util.HashMap;

import static com.mopub.common.logging.MoPubLog.LogLevel.DEBUG;
import static com.mopub.common.logging.MoPubLog.LogLevel.INFO;

public class AdsManager {

    public AppCompatActivity context;
    public FrameLayout adContainerView;
    public MoPubView moPubView;
    public MoPubInterstitial mInterstitial;
    private InterstitialAd mInterstitialAd;

    public AdsManager() {
    }

    public AdsManager(AppCompatActivity context, FrameLayout adContainerView) {
        this.context = context;
        this.adContainerView = adContainerView;
    }

    public MoPubView loadMobUpBanner() {
        HashMap<String, String> config = new HashMap<>();
        config.put("npa", "1");
        SdkConfiguration.Builder configBuilder = new SdkConfiguration.Builder(context.getString(R.string.mo_banner_id));
        configBuilder.withMediatedNetworkConfiguration(GooglePlayServicesAdapterConfiguration.class.getName(), config);

        if (BuildConfig.DEBUG) {
            configBuilder.withLogLevel(DEBUG);
        } else {
            configBuilder.withLogLevel(INFO);
        }

        MoPub.initializeSdk(context, configBuilder.build(), new SdkInitializationListener() {
            @Override
            public void onInitializationFinished() {

                moPubView = new MoPubView(context);
                moPubView.setAdUnitId(context.getString(R.string.mo_banner_id));
                moPubView.setAdSize(MoPubView.MoPubAdSize.MATCH_VIEW);
                adContainerView.addView(moPubView);
                moPubView.loadAd();

                moPubView.setBannerAdListener(new MoPubView.BannerAdListener() {
                    @Override
                    public void onBannerLoaded(MoPubView banner) {

                    }

                    @Override
                    public void onBannerFailed(MoPubView banner, MoPubErrorCode errorCode) {
                        loadAdMobBanner();
                    }

                    @Override
                    public void onBannerClicked(MoPubView banner) {

                    }

                    @Override
                    public void onBannerExpanded(MoPubView banner) {

                    }

                    @Override
                    public void onBannerCollapsed(MoPubView banner) {

                    }
                });

                loadMoPubInterstitial();
            }
        });

        return moPubView;
    }

    public MoPubInterstitial loadMoPubInterstitial() {
        mInterstitial = new MoPubInterstitial(context, context.getString(R.string.mo_int_id));
        mInterstitial.setInterstitialAdListener(new MoPubInterstitial.InterstitialAdListener() {
            @Override
            public void onInterstitialLoaded(MoPubInterstitial interstitial) {
                interstitial.show();
            }

            @Override
            public void onInterstitialFailed(MoPubInterstitial interstitial, MoPubErrorCode errorCode) {
                loadAdMobInterstitialAd();
            }

            @Override
            public void onInterstitialShown(MoPubInterstitial interstitial) {

            }

            @Override
            public void onInterstitialClicked(MoPubInterstitial interstitial) {

            }

            @Override
            public void onInterstitialDismissed(MoPubInterstitial interstitial) {

            }
        });
        mInterstitial.load();

        return mInterstitial;
    }

    public void loadAdMobBanner() {
        adContainerView.removeAllViews();
        MobileAds.initialize(context, context.getResources().getString(R.string.app_id));
        AdView adView = new AdView(context);
        adView.setAdUnitId(context.getString(R.string.admob_banner_unit));
        adContainerView.addView(adView);
        // Create an ad request. Check your logcat output for the hashed device ID
        // to get test ads on a physical device, e.g.,
        // "Use AdRequest.Builder.addTestDevice("ABCDE0123") to get test ads on this
        // device."
        AdRequest adRequest =
                new AdRequest.Builder().addTestDevice("6916E80F023231DAB0E0D33AB04D0058")
                        .build();

        AdSize adSize = getAdSize();
        // Step 4 - Set the adaptive ad size on the ad view.
        adView.setAdSize(adSize);

        // Step 5 - Start loading the ad in the background.
        adView.loadAd(adRequest);

        adView.setAdListener(new AdListener() {
            @Override
            public void onAdLoaded() {
                // Code to be executed when an ad finishes loading.
            }

            @Override
            public void onAdFailedToLoad(int errorCode) {

            }

            @Override
            public void onAdOpened() {
                // Code to be executed when an ad opens an overlay that
                // covers the screen.
            }

            @Override
            public void onAdClicked() {
                // Code to be executed when the user clicks on an ad.
            }

            @Override
            public void onAdLeftApplication() {
                // Code to be executed when the user has left the app.
            }

            @Override
            public void onAdClosed() {
                // Code to be executed when the user is about to return
                // to the app after tapping on an ad.
            }
        });
    }

    public AdSize getAdSize() {
        // Step 2 - Determine the screen width (less decorations) to use for the ad width.
        Display display = context.getWindowManager().getDefaultDisplay();
        DisplayMetrics outMetrics = new DisplayMetrics();
        display.getMetrics(outMetrics);

        float widthPixels = outMetrics.widthPixels;
        float density = outMetrics.density;

        int adWidth = (int) (widthPixels / density);

        // Step 3 - Get adaptive ad size and return for setting on the ad view.
        return AdSize.getCurrentOrientationAnchoredAdaptiveBannerAdSize(context, adWidth);
    }

    public void loadAdMobInterstitialAd() {
        MobileAds.initialize(context, context.getResources().getString(R.string.app_id));
        mInterstitialAd = new InterstitialAd(context);
        mInterstitialAd.setAdUnitId(context.getString(R.string.admob_Inerstitial_ID));
        mInterstitialAd.loadAd(new AdRequest.Builder().addTestDevice("6916E80F023231DAB0E0D33AB04D0058").build());
        mInterstitialAd.setAdListener(new AdListener() {
            @Override
            public void onAdLoaded() {
                // Code to be executed when an ad finishes loading.
                mInterstitialAd.show();

            }

            @Override
            public void onAdFailedToLoad(int errorCode) {

            }

            @Override
            public void onAdOpened() {
                // Code to be executed when the ad is displayed.
            }

            @Override
            public void onAdClicked() {
                // Code to be executed when the user clicks on an ad.
            }

            @Override
            public void onAdLeftApplication() {
                // Code to be executed when the user has left the app.
            }

            @Override
            public void onAdClosed() {

                // Code to be executed when the interstitial ad is closed.
            }
        });
    }
}
