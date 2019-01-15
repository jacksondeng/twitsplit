package com.assignment.zalora.twitsplit.util.aws

import android.arch.lifecycle.MutableLiveData
import android.content.Context
import com.amazonaws.auth.AWSCredentials
import com.amazonaws.mobile.auth.core.IdentityManager
import com.amazonaws.mobile.config.AWSConfiguration
import javax.inject.Singleton
import com.amazonaws.mobile.client.*
import com.assignment.zalora.twitsplit.util.extension.SingleLiveEvent
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
    var isUserSignedIn : SingleLiveEvent<Boolean> = SingleLiveEvent()

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
                    initUserStateListeners()

                    when (userStateDetails.userState) {
                        UserState.SIGNED_IN -> {
                            isUserSignedIn.postValue(true)
                            retrieveUserInfo()
                        }

                        UserState.SIGNED_OUT -> {
                            isUserSignedIn.postValue(false)
                            instanceState.postValue(AWSInstanceState.Initialized)
                        }
                    }

                    instanceState.postValue(AWSInstanceState.Initialized)
                }

                override fun onError(e: Exception) {
                    Timber.d("UserSignedIn $e")
                    instance!!.signOut()
                    isUserSignedIn.postValue(false)
                    instanceState.postValue(AWSInstanceState.Initialized)
                }
            })


        }
    }

    private fun initUserStateListeners() {
        AWSMobileClient.getInstance().addUserStateListener(object : UserStateListener {
            override fun onUserStateChanged(details: UserStateDetails) {
                when(details.userState){
                    UserState.SIGNED_IN ->{
                        awsCredentials = instance!!.awsCredentials
                        isUserSignedIn.postValue(true)
                        retrieveUserInfo()
                    }

                    UserState.SIGNED_OUT -> {
                        username = null
                        cachedUserID = null
                        awsCredentials = null
                        isUserSignedIn.postValue(false)
                    }
                }
            }
        })
    }

    fun retrieveUserInfo(){
        cachedUserID = instance!!.identityId
        username = instance!!.username
        isUserSignedIn.postValue(instance!!.isSignedIn)
        awsCredentials = instance!!.awsCredentials
    }

    fun signOut(){
        instance!!.signOut()
    }


}