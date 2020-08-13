package com.detectiveme.util

import android.annotation.SuppressLint
import android.content.Context
import android.content.res.Configuration
import android.content.res.Resources
import android.graphics.PorterDuff
import android.util.DisplayMetrics
import android.view.MotionEvent
import android.view.View
import com.detectiveme.ui.selectLang.SelectLangFragmentDirections
import java.util.*

@SuppressLint("ClickableViewAccessibility")
fun buttonEffect(button: View) {
    button.setOnTouchListener { v, event ->
        when (event.action) {
            MotionEvent.ACTION_DOWN -> {
                v.background.setColorFilter(-0x1f0b8adf, PorterDuff.Mode.SRC_ATOP)
                v.invalidate()
            }
            MotionEvent.ACTION_UP -> {
                v.background.clearColorFilter()
                v.invalidate()
            }
        }
        false
    }
}


fun setLocale(lang: String?, context: Context) {
    val myLocale = Locale(lang)
    val res: Resources = context.resources
    val dm: DisplayMetrics = res.displayMetrics
    val conf: Configuration = res.configuration
    conf.locale = myLocale
    res.updateConfiguration(conf, dm)

}