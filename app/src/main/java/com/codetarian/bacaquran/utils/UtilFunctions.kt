package com.codetarian.bacaquran.utils

import android.util.Log

object UtilFunctions {
    fun loge(message: String) {
        Log.e("THIS ERROR", "ERROR -> $message")
    }

    fun setTimeStamp() =  System.currentTimeMillis().toString();
}