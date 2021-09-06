package com.superyao.quick1922.log

import timber.log.Timber

class DebugTree(private val prefixTag: String? = null) : Timber.DebugTree() {

    /**
     * Timber.plant(DebugTree())
     */

    override fun log(priority: Int, tag: String?, message: String, t: Throwable?) {
        if (prefixTag != null) {
            super.log(priority, "$prefixTag $tag", message, t)
        } else {
            super.log(priority, tag, message, t)
        }
    }

    override fun createStackElementTag(element: StackTraceElement) = String.format(
        "[%s][%s][%s]",
        super.createStackElementTag(element),
        element.methodName,
        element.lineNumber
    )
}