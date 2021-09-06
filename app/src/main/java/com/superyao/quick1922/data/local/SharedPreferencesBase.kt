package com.superyao.quick1922.data.local

import android.content.Context
import android.content.Context.MODE_PRIVATE
import com.superyao.dev.toolkit.commitValue
import com.superyao.dev.toolkit.getValue

class SharedPreferencesBase(context: Context) {

    private val setting = context.getSharedPreferences(NAME, MODE_PRIVATE)

    var vibrateOnScanned: Boolean
        get() = setting.getValue(VIBRATE_ON_SCANNED, true)
        set(value) {
            setting.commitValue(VIBRATE_ON_SCANNED, value)
        }

    var autoFinishActivity: Boolean
        get() = setting.getValue(AUTO_FINISH_ACTIVITY, true)
        set(value) {
            setting.commitValue(AUTO_FINISH_ACTIVITY, value)
        }

    fun removeValue(key: String) = setting.edit().remove(key).commit()

    fun clearSettings() = setting.edit().clear().commit()

    companion object {
        private const val NAME = "settings"

        // 掃描成功後是否震動
        const val VIBRATE_ON_SCANNED = "VIBRATE_ON_SCANNED"

        // 是否自動關閉 Activity
        const val AUTO_FINISH_ACTIVITY = "AUTO_FINISH_ACTIVITY"
    }
}