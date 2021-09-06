package com.superyao.quick1922.utils

import android.app.Activity
import androidx.annotation.FloatRange

fun Activity.brightness(@FloatRange(from = 0.0, to = 1.0) value: Float) {
    val attributes = window.attributes
    attributes.screenBrightness = value
    window.attributes = attributes
}