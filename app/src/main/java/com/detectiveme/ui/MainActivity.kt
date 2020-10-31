package com.detectiveme.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.LocaleList
import com.detectiveme.R
import com.detectiveme.base.BaseFragment
import com.detectiveme.halper.LocaleHelper

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }

    override fun onBackPressed() {
        super.onBackPressed()
        LocaleHelper().setLocale(this, BaseFragment.lang)
    }
}