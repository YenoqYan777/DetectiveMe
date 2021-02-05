package com.detectiveme.halper

import android.app.Activity
import android.content.Context
import android.content.res.Configuration
import android.os.Build
import android.os.LocaleList
import java.util.*


class LocaleHelper {

    fun setLocale(context: Context, localeToSet: String) {
        val locale = Locale(localeToSet.toLowerCase())
        val resources = context.resources
        val config = resources.configuration
        when {
            Build.VERSION.SDK_INT >= Build.VERSION_CODES.N -> {
                val list = LocaleList.forLanguageTags(localeToSet.toLowerCase())
                config.setLocales(list)
            }
            Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1 -> config.setLocale(locale)
            else -> config.locale = locale
        }

        resources.updateConfiguration(config, resources.displayMetrics)
        val appContext = context.applicationContext
        if (context !== appContext) {
            setLocale(appContext, localeToSet)
        }
    }
}