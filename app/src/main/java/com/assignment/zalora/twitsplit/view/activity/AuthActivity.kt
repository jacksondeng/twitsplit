package com.assignment.zalora.twitsplit.view.activity

import android.graphics.Color
import android.os.Bundle
import com.amazonaws.mobile.auth.ui.AuthUIConfiguration
import com.amazonaws.mobile.auth.ui.SignInUI
import com.assignment.zalora.twitsplit.R
import com.assignment.zalora.twitsplit.util.aws.AWSProvider
import dagger.android.AndroidInjection
import dagger.android.support.DaggerAppCompatActivity

import javax.inject.Inject

class AuthActivity : DaggerAppCompatActivity() {

    @Inject
    lateinit var awsProvider: AWSProvider

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        AndroidInjection.inject(this)
        setContentView(R.layout.activity_auth)
        promptLogin()
    }

    fun promptLogin(){
        val ui = awsProvider.instance!!.getClient(this, SignInUI::class.java) as SignInUI
        val config = AuthUIConfiguration.Builder()
            .userPools(true)
            .logoResId(R.drawable.logo)
            .backgroundColor(Color.WHITE)
            .isBackgroundColorFullScreen(true)
            .fontFamily("sans-serif-light") // font
            .canCancel(true)
            .build()
        ui.login(this, MainActivity::class.java).authUIConfiguration(config).execute()
        //finish()
    }

}
