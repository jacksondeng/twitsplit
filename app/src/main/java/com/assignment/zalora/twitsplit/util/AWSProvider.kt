package com.assignment.zalora.twitsplit.util

import android.content.Context
import com.amazonaws.auth.AWSCredentialsProvider
import com.amazonaws.mobile.auth.core.IdentityManager
import com.amazonaws.mobile.client.AWSMobileClient
import com.amazonaws.mobile.config.AWSConfiguration

class AWSProvider {
    companion object {
        var instance: AWSMobileClient? = null
        val identityManager: IdentityManager
            get() = IdentityManager.getDefaultIdentityManager()
        val credentialsProvider: AWSCredentialsProvider?
            get() = instance?.credentialsProvider
        val configuration: AWSConfiguration?
            get() = instance?.configuration

        @Synchronized
        fun initialize(context: Context) {
            if (instance == null) {
                AWSMobileClient.getInstance().initialize(context).execute()
                instance = AWSMobileClient.getInstance()
            }
        }
    }
}