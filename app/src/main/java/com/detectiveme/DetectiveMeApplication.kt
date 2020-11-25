package com.detectiveme

import android.app.Application
import android.content.Context
import android.content.res.Configuration
import android.view.View
import android.view.View.GONE
import android.view.View.VISIBLE
import android.widget.ProgressBar
import androidx.constraintlayout.widget.ConstraintLayout
import com.google.android.gms.ads.AdListener
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.InterstitialAd
import com.google.android.gms.ads.LoadAdError

class DetectiveMeApplication : Application(){

    fun initAd(context: Context, progressBar: View) {
        val mInterstitialAd = InterstitialAd(context)
        mInterstitialAd.adUnitId = context.getString(R.string.key_full)
        mInterstitialAd.loadAd(AdRequest.Builder().build())
        progressBar.visibility = VISIBLE
        mInterstitialAd.adListener = object: AdListener() {
            override fun onAdLoaded() {
                super.onAdLoaded()
                if (mInterstitialAd.isLoaded) {
                    mInterstitialAd.show()
                }
            }

            override fun onAdFailedToLoad(p0: LoadAdError?) {
                super.onAdFailedToLoad(p0)
                progressBar.visibility = GONE
            }
            override fun onAdClosed() {
                super.onAdClosed()
                progressBar.visibility = GONE
            }
        }
    }
}