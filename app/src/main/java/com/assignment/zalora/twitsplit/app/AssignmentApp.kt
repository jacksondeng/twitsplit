package com.assignment.zalora.twitsplit.app

import android.app.Activity
import android.app.Application
import com.assignment.zalora.twitsplit.BuildConfig
import com.assignment.zalora.twitsplit.di.component.DaggerAssignmentAppComponent
import com.assignment.zalora.twitsplit.util.aws.AWSProvider
import dagger.android.DispatchingAndroidInjector
import dagger.android.HasActivityInjector
import net.danlew.android.joda.JodaTimeAndroid
import timber.log.Timber
import javax.inject.Inject
import com.amazonaws.mobile.client.UserStateDetails
import com.amazonaws.mobile.client.AWSMobileClient
import com.amazonaws.mobile.client.Callback


class AssignmentApp : Application(), HasActivityInjector {

    @Inject
    lateinit var dispatchingAndroidInjector : DispatchingAndroidInjector<Activity>;

    @Inject
    lateinit var awsProvider : AWSProvider
    override fun activityInjector() : DispatchingAndroidInjector<Activity> {
        return dispatchingAndroidInjector;
    }

    override fun onCreate() {
        super.onCreate();
        // Timber
        if (BuildConfig.DEBUG) {
            Timber.plant(Timber.DebugTree ());
        }
        // Init JodaTime
        JodaTimeAndroid.init(this);
        initDagger();
        initAwsProvider()
    }

    private fun initDagger() {
        // Init dagger
        DaggerAssignmentAppComponent
            .builder()
            .application(this)
            .build()
            .inject(this);
    }

    private fun initAwsProvider(){
        awsProvider.initialize(applicationContext)
    }


}