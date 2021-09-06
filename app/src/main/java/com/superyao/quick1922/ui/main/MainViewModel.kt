package com.superyao.quick1922.ui.main

import com.superyao.quick1922.data.DataRepository
import com.superyao.quick1922.ui.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    repository: DataRepository
) : BaseViewModel(repository) {
    var screenBrightness = .3f
}