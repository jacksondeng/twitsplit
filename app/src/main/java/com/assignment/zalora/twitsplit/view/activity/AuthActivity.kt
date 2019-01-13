package com.assignment.zalora.twitsplit.view.activity

import android.arch.lifecycle.Observer
import android.os.Bundle
import com.amazonaws.mobile.auth.ui.SignInUI
import com.assignment.zalora.twitsplit.R
import com.assignment.zalora.twitsplit.util.aws.AWSInstanceState
import com.assignment.zalora.twitsplit.util.aws.AWSProvider
import dagger.android.AndroidInjection
import dagger.android.support.DaggerAppCompatActivity

import kotlinx.android.synthetic.main.activity_auth.*
import timber.log.Timber
import javax.inject.Inject

class AuthActivity : DaggerAppCompatActivity() {

    @Inject
    lateinit var awsProvider: AWSProvider

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        AndroidInjection.inject(this)
        setContentView(R.layout.activity_auth)
        setSupportActionBar(toolbar)
        observeInstanceState()
        awsProvider.instanceState.postValue(AWSInstanceState.Initialized)
    }

    fun observeInstanceState(){
        awsProvider.instanceState.observe(this, Observer {
                instanceState ->
                Timber.d("instanceState $instanceState")
                when(instanceState){
                    AWSInstanceState.Initialized ->{
                        promptLogin()
                    }
                }
        })
    }

    fun promptLogin(){
        val ui = awsProvider.instance!!.getClient(this, SignInUI::class.java) as SignInUI
        ui.login(this, MainActivity::class.java).execute()
    }

}
