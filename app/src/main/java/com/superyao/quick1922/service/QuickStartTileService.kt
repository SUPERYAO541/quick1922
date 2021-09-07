package com.superyao.quick1922.service

import android.service.quicksettings.TileService
import timber.log.Timber

class QuickStartTileService : TileService() {
    override fun onClick() {
        super.onClick()
        try {
            val launchIntent = packageManager.getLaunchIntentForPackage(packageName)
            startActivityAndCollapse(launchIntent)
        } catch (e: Exception) {
            Timber.e(e)
        }
    }
}