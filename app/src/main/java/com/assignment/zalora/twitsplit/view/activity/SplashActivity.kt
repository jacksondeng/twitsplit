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

class SplashActivity : AppCompatActivity() {

    @Inject
    lateinit var awsProvider: AWSProvider

    override fun onCreate(savedInstanceState: Bundle?) {
        setTheme(R.style.AppTheme)
        super.onCreate(savedInstanceState)
        AndroidInjection.inject(this)
        setContentView(R.layout.activity_splash)
        initAwsProvider()
        observeInstanceState()
    }

    fun initAwsProvider(){
        awsProvider.initialize(this)
    }

    fun observeInstanceState(){
        awsProvider.instanceState.observe(this, Observer {
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

    fun checkIfUserSignedIn() : Boolean{
        Timber.d("UserSignedIn ${awsProvider.isUserSignedIn} ${awsProvider.cachedUserID}")
        return awsProvider.cachedUserID != null
    }

    fun gotoAuth(){
        val intent = Intent(this, AuthActivity::class.java)
        startActivity(intent)
        finish()
    }

    fun gotoMain(){
        val intent = Intent(this, MainActivity::class.java)
        startActivity(intent)
        finish()
    }
}
