package com.assignment.zalora.twitsplit.view.activity

import android.app.Activity
import android.arch.lifecycle.Observer
import android.content.SharedPreferences
import android.graphics.Color
import android.os.Bundle
import com.amazonaws.mobile.client.*
import com.assignment.zalora.twitsplit.R
import com.assignment.zalora.twitsplit.util.aws.AWSProvider
import dagger.android.AndroidInjection
import dagger.android.support.DaggerAppCompatActivity
import timber.log.Timber
import javax.inject.Inject


class AuthActivity : BaseActivity() {

    @Inject
    lateinit var awsProvider: AWSProvider

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_auth)
        promptLogin(this@AuthActivity)
    }


    private fun promptLogin(activity: Activity){
        awsProvider.instance!!.showSignIn(
            activity,
            SignInUIOptions.builder()
                .logo(R.drawable.logo)
                .backgroundColor(Color.WHITE)
                .canCancel(false)
                .build(),
            object : Callback<UserStateDetails> {
                override fun onResult(userStateDetails: UserStateDetails) {
                    Timber.d("UserStateDetails $userStateDetails")
                }

                override fun onError(e: Exception) {
                    Timber.d("UserStateDetails $e")
                }
            }
        )

        tweetVM.isUserSignedIn.observe(this, Observer {
            when(it){
                true -> {
                    gotoMain()
                    finish()
                }
            }
        })
    }



    override fun onDestroy() {
        super.onDestroy()
    }

}
