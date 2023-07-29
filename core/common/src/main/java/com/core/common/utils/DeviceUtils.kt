package com.core.common.utils

import android.app.Activity
import android.content.Context
import android.util.DisplayMetrics
import android.view.View
import android.view.WindowManager
import android.view.inputmethod.InputMethodManager

object DeviceUtils {
    fun closeKeyboard(activity: Activity?, view: View?) {
        activity?.let {
            val inputMethodManager =
                activity.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            val windowToken = view?.windowToken ?: activity.window.decorView.windowToken
            inputMethodManager.hideSoftInputFromWindow(windowToken, 0)
        }
    }

    fun openKeyboard(activity: Activity?, view: View?, softInputMode: Int? = null) {
        activity?.let { safeActivity ->
            val imm =
                safeActivity.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            imm.showSoftInput(view, InputMethodManager.SHOW_IMPLICIT)
            softInputMode?.let { safeSoftInputMode ->
                safeActivity.window.setSoftInputMode(safeSoftInputMode)
            }
        }
    }

    fun getDeviceHeight(activity: Activity): Int {
        return if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.R) {
            activity.windowManager.maximumWindowMetrics.bounds.height()
        } else {
            @Suppress("DEPRECATION")
            (activity.getSystemService(Context.WINDOW_SERVICE) as WindowManager)
                .run { DisplayMetrics().also { defaultDisplay.getRealMetrics(it) } }
                .run { return heightPixels }
        }
    }

}