package com.superyao.quick1922.utils

import android.app.Activity
import androidx.annotation.FloatRange
import androidx.appcompat.app.AlertDialog
import com.superyao.dev.toolkit.ui.messageDialog

fun Activity.alertDialog(message: String, cancelable: Boolean = true): AlertDialog {
    return messageDialog(getString(android.R.string.dialog_alert_title), message, cancelable)
}

fun Activity.brightness(@FloatRange(from = 0.0, to = 1.0) value: Float) {
    val attributes = window.attributes
    attributes.screenBrightness = value
    window.attributes = attributes
}