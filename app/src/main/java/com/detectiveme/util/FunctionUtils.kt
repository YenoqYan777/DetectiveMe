package com.detectiveme.util

import android.content.Context
import android.content.res.Configuration
import android.content.res.Resources
import android.util.DisplayMetrics
import java.util.*


fun setLocale(lang: String?, context: Context) {
    val myLocale = Locale(lang)
    val res: Resources = context.resources
    val dm: DisplayMetrics = res.displayMetrics
    val conf: Configuration = res.configuration
    conf.locale = myLocale
    res.updateConfiguration(conf, dm)

}