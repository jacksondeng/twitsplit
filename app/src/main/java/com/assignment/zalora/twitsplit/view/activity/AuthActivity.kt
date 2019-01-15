package com.assignment.zalora.twitsplit.view.activity

import android.app.Activity
import android.graphics.Color
import android.os.Bundle
import com.amazonaws.mobile.auth.core.SignInStateChangeListener
import com.amazonaws.mobile.auth.ui.AuthUIConfiguration
import com.amazonaws.mobile.auth.ui.SignInUI
import com.amazonaws.mobile.client.*
import com.assignment.zalora.twitsplit.R
import com.assignment.zalora.twitsplit.util.aws.AWSProvider
import com.assignment.zalora.twitsplit.util.state.AuthState
import dagger.android.AndroidInjection
import dagger.android.support.DaggerAppCompatActivity

import javax.inject.Inject
import timber.log.Timber


class AuthActivity : DaggerAppCompatActivity(),AuthState {

    @Inject
    lateinit var awsProvider: AWSProvider

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        AndroidInjection.inject(this)
        setContentView(R.layout.activity_auth)
        initUserStateListeners()
        promptLogin(this@AuthActivity)
    }


    fun promptLogin(activity: Activity){
        AWSMobileClient.getInstance().showSignIn(
            activity,
            SignInUIOptions.builder()
                .nextActivity(MainActivity::class.java)
                .logo(R.drawable.logo)
                .backgroundColor(Color.WHITE)
                .canCancel(false)
                .build(),
            object : Callback<UserStateDetails> {
                override fun onResult(userStateDetails: UserStateDetails) {

                }

                override fun onError(e: Exception) {
                }
            }
        )
    }

    private fun initUserStateListeners() {
        AWSMobileClient.getInstance().addUserStateListener(object : UserStateListener {
            override fun onUserStateChanged(details: UserStateDetails) {
                when(details.userState){
                    UserState.SIGNED_IN ->{
                        awsProvider.isUserSignedIn.postValue(true)
                        awsProvider.retrieveUserInfo()
                        signedIn()
                    }

                    else ->{
                        awsProvider.isUserSignedIn.postValue(false)
                        signedOut()
                    }
                }
            }
        })
    }

    override fun signedIn() {
        Timber.d("testt SignedIn")
        finish()
    }


    override fun signedOut() {

    }

}
