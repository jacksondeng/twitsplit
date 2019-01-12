package com.assignment.zalora.twitsplit.util

import android.arch.lifecycle.MutableLiveData
import android.content.Context
import com.amazonaws.auth.AWSCredentialsProvider
import com.amazonaws.mobile.auth.core.IdentityManager
import com.amazonaws.mobile.client.AWSMobileClient
import com.amazonaws.mobile.config.AWSConfiguration
import timber.log.Timber
import javax.inject.Singleton


@Singleton
class AWSProvider {
    val identityManager: IdentityManager
        get() = IdentityManager.getDefaultIdentityManager()
    val credentialsProvider: AWSCredentialsProvider?
        get() = instance?.credentialsProvider
    val configuration: AWSConfiguration?
        get() = instance?.configuration

    var instance: AWSMobileClient ?= null
    var instanceState : MutableLiveData<AWSInstanceState> = MutableLiveData()

    constructor(){
        instanceState.postValue(AWSInstanceState.Uninitialized)
    }

    @Synchronized
    fun initialize(context: Context) {
        if (instance == null) {
            AWSMobileClient.getInstance().initialize(context).execute()
            instance = AWSMobileClient.getInstance()
            instanceState.postValue(AWSInstanceState.Initialized)
        }
    }
}