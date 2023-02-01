package com.smartyhome.app.utils

import android.content.Context
import android.net.ConnectivityManager
import java.lang.Exception

object NetworkUtils {
    fun isNetworkConnected(context: Context?): Boolean {
        return try {
            val cm = context?.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
            cm.activeNetworkInfo != null && cm.activeNetworkInfo!!.isConnected
        } catch (e: Exception) {
            e.printStackTrace()
            false
        }
    }
}