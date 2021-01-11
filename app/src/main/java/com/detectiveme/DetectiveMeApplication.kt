package com.detectiveme

import android.app.Application
import android.content.Context
import android.content.SharedPreferences
import android.view.View
import android.view.View.GONE
import android.view.View.VISIBLE
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import com.detectiveme.halper.LocaleHelper
import com.google.android.gms.ads.AdListener
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.InterstitialAd
import com.google.android.gms.ads.LoadAdError

class DetectiveMeApplication : Application() {

    private val sharedPrefFile = "kotlinsharedpreference"
    private val LANG_KEY = "lang"

    override fun onCreate() {
        super.onCreate()
        val sharedPreferences: SharedPreferences = this.getSharedPreferences(
            sharedPrefFile,
            Context.MODE_PRIVATE
        )
        if (sharedPreferences.getString(LANG_KEY, "en") != null) {
            LocaleHelper().setLocale(
                applicationContext,
                sharedPreferences.getString(LANG_KEY, "en")!!
            )
        } else {
            LocaleHelper().setLocale(
                applicationContext,
                "en"
            )
        }
    }

    fun initAd(context: Context, progressBar: View, bckgLoading: View) {
        val mInterstitialAd = InterstitialAd(context)
        mInterstitialAd.adUnitId = context.getString(R.string.key_full)
        mInterstitialAd.loadAd(AdRequest.Builder().build())
        val pulse: Animation = AnimationUtils.loadAnimation(context, R.anim.pulse)
        progressBar.startAnimation(pulse)
        progressBar.visibility = VISIBLE
        bckgLoading.visibility = VISIBLE
        mInterstitialAd.adListener = object : AdListener() {
            override fun onAdLoaded() {
                super.onAdLoaded()
                if (mInterstitialAd.isLoaded) {
                    mInterstitialAd.show()
                }
            }

            override fun onAdFailedToLoad(p0: LoadAdError?) {
                super.onAdFailedToLoad(p0)
                progressBar.animation = null
                progressBar.visibility = GONE
                bckgLoading.visibility = GONE
            }

            override fun onAdClosed() {
                super.onAdClosed()
                progressBar.animation = null
                progressBar.visibility = GONE
                bckgLoading.visibility = GONE
            }
        }
    }
}