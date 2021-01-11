package com.detectiveme.halper

import android.app.Activity
import android.content.Context
import android.content.res.Configuration
import java.util.*


class LocaleHelper {

    fun setLocale(context: Context, language: String) {
        val locale = Locale(language)
        val config = Configuration(context.resources.configuration)
        Locale.setDefault(locale)
        config.setLocale(locale)
        context.resources.updateConfiguration(
            config,
            context.resources.displayMetrics
        )
    }
}