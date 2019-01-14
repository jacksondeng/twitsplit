package com.assignment.zalora.twitsplit.util.network

import android.net.NetworkInfo
import android.content.Context.CONNECTIVITY_SERVICE
import android.support.v4.content.ContextCompat.getSystemService
import android.net.ConnectivityManager
import android.content.Intent
import android.content.BroadcastReceiver
import android.content.Context
import com.assignment.zalora.twitsplit.app.AssignmentApp
import android.support.v4.content.ContextCompat.getSystemService




class NetworkManager(var context: Context) {

    private var connectivityManager: ConnectivityManager? = null
    private var isConnected : Boolean = false

    fun isOnline(): Boolean {
        try {
            connectivityManager = context
                .getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager

            val networkInfo = connectivityManager?.getActiveNetworkInfo()
            isConnected = networkInfo != null && networkInfo.isAvailable() &&
                    networkInfo.isConnected()
            return isConnected

        } catch (e: Exception) {
            println("CheckConnectivity Exception: " + e.message)
        }

        return isConnected
    }
}