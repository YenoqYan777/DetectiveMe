package com.detectiveme.ui

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.detectiveme.R
import com.detectiveme.halper.LocaleHelper


class MainActivity : AppCompatActivity() {
    private val sharedPrefFile = "kotlinsharedpreference"
    private val LANG_KEY = "lang"
    private lateinit var sharedPreferences: SharedPreferences
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
       sharedPreferences = this.getSharedPreferences(
            sharedPrefFile,
            Context.MODE_PRIVATE
        )
        if (sharedPreferences.getString(LANG_KEY, "en") != null) {
            LocaleHelper().setLocale(
                this,
                sharedPreferences.getString(LANG_KEY, "en")!!
            )
        } else{
            LocaleHelper().setLocale(
                this,
                "en"
            )
        }
    }

    override fun onRestart() {
        LocaleHelper().setLocale(this, sharedPreferences.getString(LANG_KEY, "en")!!)
        super.onRestart()
    }
    override fun onResume() {
        LocaleHelper().setLocale(this, sharedPreferences.getString(LANG_KEY, "hy")!!)
        super.onResume()
    }

}