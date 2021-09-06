package com.superyao.quick1922.ui.base

import androidx.lifecycle.ViewModel
import com.superyao.quick1922.data.DataRepository
import com.superyao.quick1922.data.local.SharedPreferencesBase
import timber.log.Timber

abstract class BaseViewModel(private val repository: DataRepository) : ViewModel() {

    override fun onCleared() {
        Timber.d(javaClass.simpleName)
        super.onCleared()
    }

    fun sharedPreferences(): SharedPreferencesBase {
        return repository.localData.sharedPreferences
    }
}