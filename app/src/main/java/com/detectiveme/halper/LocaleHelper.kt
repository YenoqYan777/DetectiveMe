package com.detectiveme.halper

import android.app.Activity
import android.content.res.Configuration
import java.util.*


class LocaleHelper {

    fun setLocale(context: Activity, language: String) {
        val locale = Locale(language)
        val config = Configuration(context.resources.configuration)
        Locale.setDefault(locale)
        config.setLocale(locale)
        context.baseContext.resources.updateConfiguration(
            config,
            context.baseContext.resources.displayMetrics
        )
    }
}