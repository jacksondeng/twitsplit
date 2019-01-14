package com.assignment.zalora.twitsplit.util.aws

import android.arch.lifecycle.MutableLiveData
import android.content.Context
import com.amazonaws.auth.AWSCredentialsProvider
import com.amazonaws.mobile.auth.core.IdentityManager
import com.amazonaws.mobile.client.AWSMobileClient
import com.amazonaws.mobile.client.AWSStartupHandler
import com.amazonaws.mobile.client.AWSStartupResult
import com.amazonaws.mobile.config.AWSConfiguration
import timber.log.Timber
import javax.inject.Singleton
import com.amazonaws.mobile.auth.core.IdentityHandler




@Singleton
class AWSProvider {

    val identityManager: IdentityManager
        get() = IdentityManager.getDefaultIdentityManager()
    var configuration: AWSConfiguration ?= null
    var credentialsProvider : AWSCredentialsProvider ?= null
    var instance: AWSMobileClient ?= null
    var instanceState : MutableLiveData<AWSInstanceState> = MutableLiveData()
    var cachedUserID : String ?= null
    var isUserSignedIn : Boolean = false

    constructor(){
        instanceState.postValue(AWSInstanceState.NotInitialized)
    }

    @Synchronized
    fun initialize(context: Context) {
        if (instance == null) {
            AWSMobileClient.getInstance().initialize(context) {
                // Obtain the reference to the AWSCredentialsProvider and AWSConfiguration objects
                credentialsProvider = AWSMobileClient.getInstance().credentialsProvider
                configuration = AWSMobileClient.getInstance().configuration
                instance = AWSMobileClient.getInstance()

                // Use IdentityManager#getUserID to fetch the identity id.
                IdentityManager.getDefaultIdentityManager().getUserID(object : IdentityHandler {
                    override fun onIdentityId(identityId: String) {
                        isUserSignedIn = IdentityManager.getDefaultIdentityManager().isUserSignedIn
                        cachedUserID = identityId
                        instanceState.postValue(AWSInstanceState.Initialized)
                    }

                    override fun handleError(exception: Exception) {
                    }
                })
            }.execute()
        }
    }
}