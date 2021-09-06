package com.superyao.quick1922.service

import android.service.quicksettings.TileService

class QuickStartTileService : TileService() {
    override fun onClick() {
        super.onClick()
        val launchIntent = packageManager.getLaunchIntentForPackage(packageName)
        startActivityAndCollapse(launchIntent)
    }
}