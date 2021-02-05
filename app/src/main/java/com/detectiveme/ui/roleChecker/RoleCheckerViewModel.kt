package com.detectiveme.ui.roleChecker

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.detectiveme.R
import com.detectiveme.base.BaseViewModel
import java.util.*

class RoleCheckerViewModel(application: Application) : BaseViewModel(application) {
    companion object{
        var wordToShow = ""
    }




    fun getRandomWordToShow(list: List<String>) {
        val random = Random()
        val ranNum: Int = random.nextInt(list.size)
        wordToShow = list[ranNum]
    }
}