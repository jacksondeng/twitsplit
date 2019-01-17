package com.assignment.zalora.twitsplit.view.activity

import android.arch.lifecycle.Observer
import android.content.SharedPreferences
import android.os.Bundle
import com.assignment.zalora.twitsplit.R
import com.assignment.zalora.twitsplit.util.aws.AWSInstanceState
import javax.inject.Inject

class SplashActivity : BaseActivity() {

    @Inject
    lateinit var sharedPreferences: SharedPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        setTheme(R.style.AppTheme)
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)
    }

    override fun onResume() {
        super.onResume()
        if(shouldShowTutorial()){
            gotoTutorial()
        } else {
            checkForUserState()
            observeUserState()
        }
    }

    private fun observeUserState(){
        tweetVM.isUserSignedIn.observe(this, Observer {
            when(it){
                true -> gotoMain()
                false -> gotoAuth()
            }
        })
    }

    // Checking for case where user start app from recent apps , Application class oncreate doesn't get called
    private fun checkForUserState(){
        if(tweetVM.awsProvider.instance != null && tweetVM.awsProvider.instanceState.value == AWSInstanceState.Initialized){
            when(tweetVM.isUserSignedIn.value){
                true-> gotoMain()
                false -> gotoAuth()
            }
        }
    }

    private fun shouldShowTutorial() : Boolean{
        return (sharedPreferences.getBoolean("shouldShowTutorial",true
        ))
    }
}
