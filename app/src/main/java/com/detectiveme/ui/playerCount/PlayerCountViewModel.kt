package com.detectiveme.ui.playerCount

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.detectiveme.base.BaseViewModel

class PlayerCountViewModel(application: Application) : BaseViewModel(application) {
    private val _totalPlayers = MutableLiveData<Int>(2)
    val totalPlayer: LiveData<Int>
        get() = _totalPlayers

    private val _totalSpies = MutableLiveData<Int>(1)
    val totalSpies: LiveData<Int>
        get() = _totalSpies

    private val _totalMins = MutableLiveData<Int>(1)
    val totalMins: LiveData<Int>
        get() = _totalMins

    fun updateTotalPlayers(isIncreased: Boolean) {
        when (isIncreased) {
            true -> {
                _totalPlayers.value = _totalPlayers.value?.plus(1)
            }
            else -> {
                _totalPlayers.value = _totalPlayers.value?.minus(1)
            }
        }
    }

    fun updateTotalSpies(isIncreased: Boolean) {
        when (isIncreased) {
            true -> {
                _totalSpies.value = _totalSpies.value?.plus(1)
            }
            else -> {
                _totalSpies.value = _totalSpies.value?.minus(1)
            }
        }
    }

    fun updateTotalMins(isIncreased: Boolean) {

        when (isIncreased) {
            true -> {
                _totalMins.value = _totalMins.value?.plus(1)
            }
            else -> {
                _totalMins.value = _totalMins.value?.minus(1)
            }
        }
    }

}