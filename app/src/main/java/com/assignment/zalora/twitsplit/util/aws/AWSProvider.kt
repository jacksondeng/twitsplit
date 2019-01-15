package com.assignment.zalora.twitsplit.util.aws

import android.arch.lifecycle.MutableLiveData
import android.content.Context
import com.amazonaws.auth.AWSCredentials
import com.amazonaws.mobile.auth.core.IdentityManager
import com.amazonaws.mobile.config.AWSConfiguration
import javax.inject.Singleton
import com.amazonaws.mobile.client.*
import com.assignment.zalora.twitsplit.util.state.AuthState
import timber.log.Timber


@Singleton
class AWSProvider(){

    var configuration: AWSConfiguration ?= null
    var awsCredentials : AWSCredentials?= null
    var instance: AWSMobileClient ?= null
    var instanceState : MutableLiveData<AWSInstanceState> = MutableLiveData()
    var cachedUserID : String ?= null
    var username : String ?= null
    var isUserSignedIn : MutableLiveData<Boolean> = MutableLiveData()

    init {
        instanceState.postValue(AWSInstanceState.NotInitialized)
    }

    @Synchronized
    fun initialize(context: Context) {
        if (instance == null) {

            AWSMobileClient.getInstance().initialize(context, object : Callback<UserStateDetails> {
                override fun onResult(userStateDetails: UserStateDetails) {
                    configuration = AWSMobileClient.getInstance().configuration
                    instance = AWSMobileClient.getInstance()
                    awsCredentials = AWSMobileClient.getInstance().awsCredentials

                    when (userStateDetails.userState) {
                        UserState.SIGNED_IN -> {
                            Timber.d("testt SignedIn")
                            isUserSignedIn.postValue(true)
                            instanceState.postValue(AWSInstanceState.Initialized)
                            retrieveUserInfo()
                        }

                        UserState.SIGNED_OUT -> {
                            Timber.d("testt SignedOut")
                            isUserSignedIn.postValue(false)
                            instanceState.postValue(AWSInstanceState.Initialized)
                        }
                        else -> {
                            Timber.d("testt SignedOut")
                            AWSMobileClient.getInstance().signOut()
                            isUserSignedIn.postValue(false)
                            instanceState.postValue(AWSInstanceState.Initialized)
                        }
                    }
                }

                override fun onError(e: Exception) {
                    Timber.d("testt failed $e")
                    Timber.d("testt SignedOut")
                    AWSMobileClient.getInstance().signOut()
                    isUserSignedIn.postValue(false)
                    instanceState.postValue(AWSInstanceState.Initialized)
                }
            })


        }
    }

    fun retrieveUserInfo(){
        cachedUserID = instance!!.identityId
        username = instance!!.username
        isUserSignedIn.postValue(instance!!.isSignedIn)
        awsCredentials = instance!!.awsCredentials
        Timber.d("testt Cached $cachedUserID")
        Timber.d("testt Cached {${instance!!.username}}")
        Timber.d("testt Cached {${instance!!.isSignedIn}}")
    }


}