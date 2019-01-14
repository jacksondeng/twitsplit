package com.assignment.zalora.twitsplit.util.network


import android.net.ConnectivityManager
import android.content.Context

class NetworkManager(var context: Context) {

    private var connectivityManager: ConnectivityManager? = null
    private var isConnected : Boolean = false

    fun isOnline(): Boolean {
        val cm = context.getSystemService(Context.CONNECTIVITY_SERVICE) as? ConnectivityManager
        val activeNetwork = cm?.activeNetworkInfo
        return activeNetwork != null && activeNetwork.isConnected
    }
}