package com.superyao.quick1922.data.local

import android.content.Context
import android.content.Context.MODE_PRIVATE
import com.superyao.dev.toolkit.commitValue
import com.superyao.dev.toolkit.getValue

class SharedPreferencesBase(context: Context) {

    private val setting = context.getSharedPreferences(NAME, MODE_PRIVATE)

    var vibrateWhenScanned: Boolean
        get() = setting.getValue(VIBRATE_WHEN_SCANNED, true)
        set(value) {
            setting.commitValue(VIBRATE_WHEN_SCANNED, value)
        }

    var exitWhenScanned: Boolean
        get() = setting.getValue(EXIT_WHEN_SCANNED, true)
        set(value) {
            setting.commitValue(EXIT_WHEN_SCANNED, value)
        }

    var highestBrightness: Boolean
        get() = setting.getValue(HIGHEST_BRIGHTNESS, false)
        set(value) {
            setting.commitValue(HIGHEST_BRIGHTNESS, value)
        }

    companion object {
        private const val NAME = "settings"

        const val VIBRATE_WHEN_SCANNED = "VIBRATE_WHEN_SCANNED"

        const val EXIT_WHEN_SCANNED = "EXIT_WHEN_SCANNED"

        const val HIGHEST_BRIGHTNESS = "HIGHEST_BRIGHTNESS"
    }
}