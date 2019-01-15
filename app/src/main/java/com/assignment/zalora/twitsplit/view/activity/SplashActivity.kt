package com.assignment.zalora.twitsplit.view.activity

import android.arch.lifecycle.Observer
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.support.v7.app.AppCompatActivity;
import com.assignment.zalora.twitsplit.R
import com.assignment.zalora.twitsplit.util.aws.AWSInstanceState
import com.assignment.zalora.twitsplit.util.aws.AWSProvider
import dagger.android.AndroidInjection

import kotlinx.android.synthetic.main.activity_splash.*
import timber.log.Timber
import javax.inject.Inject

class SplashActivity : BaseActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        setTheme(R.style.AppTheme)
        super.onCreate(savedInstanceState)
        AndroidInjection.inject(this)
        setContentView(R.layout.activity_splash)
        observeInstanceState()
    }

    private fun observeInstanceState(){
        tweetVM.awsProvider.instanceState.observe(this, Observer {
                instanceState ->
            when(instanceState){
                AWSInstanceState.Initialized ->{
                    if(checkIfUserSignedIn()){
                        gotoMain()
                    }else{
                        gotoAuth()
                    }
                }
            }
        })
    }

}
