package com.superyao.quick1922

import android.app.Application
import com.superyao.quick1922.log.DebugTree
import dagger.hilt.android.HiltAndroidApp
import timber.log.Timber

@HiltAndroidApp
class App : Application() {

    override fun onCreate() {
        super.onCreate()
        instance = this
        if (BuildConfig.DEBUG) {
            System.setProperty("kotlinx.coroutines.debug", "on")
            Timber.plant(DebugTree("DEV-LeakCanary"))
        }
    }

    companion object {
        lateinit var instance: App
            private set
    }
}