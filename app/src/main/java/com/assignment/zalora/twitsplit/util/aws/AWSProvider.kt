package com.assignment.zalora.twitsplit.util.aws

import android.arch.lifecycle.MutableLiveData
import android.content.Context
import com.amazonaws.auth.AWSCredentials
import com.amazonaws.mobile.config.AWSConfiguration
import javax.inject.Singleton
import com.amazonaws.mobile.client.*
import timber.log.Timber
import com.amazonaws.mobileconnectors.cognitoidentityprovider.CognitoUserPool




@Singleton
class AWSProvider(var context: Context){

    var configuration: AWSConfiguration ?= null
    var awsCredentials : AWSCredentials?= null
    var instance: AWSMobileClient ?= null
    var instanceState : MutableLiveData<AWSInstanceState> = MutableLiveData()
    var cachedUserID : String ?= null
    var username : String ?= null
    var isUserSignedIn : MutableLiveData<Boolean> = MutableLiveData()

    @Synchronized
    fun initialize(context: Context) {
        if (instance == null) {
            AWSMobileClient.getInstance().initialize(context, object : Callback<UserStateDetails> {
                override fun onResult(userStateDetails: UserStateDetails) {
                    configuration = AWSMobileClient.getInstance().configuration
                    instance = AWSMobileClient.getInstance()
                    awsCredentials = AWSMobileClient.getInstance().awsCredentials
                    initUserStateListeners()
                    if(instance!!.currentUserState().userState == UserState.SIGNED_IN){
                        retrieveUserInfo()
                        isUserSignedIn.postValue(true)
                    } else{
                        signOut()
                        isUserSignedIn.postValue(false)
                    }
                    Timber.d("UserSignedIn Current ${instance!!.currentUserState().userState}")
                }

                override fun onError(e: Exception) {
                    Timber.d("UserSignedIn $e")
                    signOut()
                    isUserSignedIn.postValue(false)
                    instanceState.postValue(AWSInstanceState.Initialized)
                }
            })
        }
    }

    private fun initUserStateListeners() {
        instance!!.addUserStateListener { details ->
            Timber.d("UserSignedIn State ${details.userState}")
            when(details.userState){
                UserState.SIGNED_IN ->{
                    retrieveUserInfo()
                    isUserSignedIn.postValue(true)
                    instanceState.postValue(AWSInstanceState.Initialized)
                }

                UserState.SIGNED_OUT ->{
                    isUserSignedIn.postValue(false)
                    instanceState.postValue(AWSInstanceState.Initialized)
                }
            }
        }
    }

    fun clearCredentials(){
        username = null
        cachedUserID = null
        awsCredentials = null
    }

    fun retrieveUserInfo(){
        // Need to create a userpool instance because aws default SignInUi class doesn't have a way to get username directly
        var userpool = CognitoUserPool(context, AWSConfiguration(context))
        userpool.currentUser.userId
        username = userpool.getCurrentUser().getUserId()
        cachedUserID = instance!!.identityId
        awsCredentials = instance!!.awsCredentials
    }

    fun signOut(){
        clearCredentials()
        instance!!.signOut()
    }
}