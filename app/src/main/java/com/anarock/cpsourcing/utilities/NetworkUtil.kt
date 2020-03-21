package com.anarock.cpsourcing.utilities

import android.content.Context
import android.net.ConnectivityManager
import android.os.Build


object NetworkUtil {

    fun isInternetConnected(mContext: Context?): Boolean {
        var isConnected: Boolean = false
        val cm = mContext!!.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val activeNetwork = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            cm.activeNetwork
        } else {
            cm.activeNetworkInfo
        }
        if (activeNetwork != null) {
            // connected to the internet
            isConnected = true
        }
        return isConnected
    }

}