package com.superyao.quick1922.ui.base

import androidx.lifecycle.ViewModel
import com.superyao.quick1922.data.DataRepository
import com.superyao.quick1922.data.local.SharedPreferencesBase
import dagger.hilt.android.lifecycle.HiltViewModel
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
open class BaseViewModel @Inject constructor(
    private val repository: DataRepository
) : ViewModel() {

    override fun onCleared() {
        Timber.d(javaClass.simpleName)
        super.onCleared()
    }

    fun sharedPreferences(): SharedPreferencesBase {
        return repository.localData.sharedPreferences
    }
}