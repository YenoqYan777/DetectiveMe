package com.detectiveme.base

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.navigation.NavDirections
import com.detectiveme.util.Event
import com.detectiveme.util.NavigationCommand

open class BaseViewModel(application: Application) : AndroidViewModel(application) {
    private val mNavigation = MutableLiveData<Event<NavigationCommand>>()
    val navigation: LiveData<Event<NavigationCommand>> = mNavigation
//    private val _landData = MutableLiveData<String>("en")
//    val langData : MutableLiveData<String> get() = _landData

    fun navigate(navDirections: NavDirections) {
        mNavigation.value =
            Event(NavigationCommand.To(navDirections))
    }
//
//    fun changeLang(lang: String){
//        _landData.value = lang
//    }

    fun navigateBack() {
        mNavigation.value =
            Event(NavigationCommand.Back)
    }
}