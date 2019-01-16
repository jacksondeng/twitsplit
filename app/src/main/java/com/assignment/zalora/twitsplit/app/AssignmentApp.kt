package com.assignment.zalora.twitsplit.app

import android.app.Activity
import android.app.Application
import android.os.Bundle
import com.assignment.zalora.twitsplit.BuildConfig
import com.assignment.zalora.twitsplit.di.component.DaggerAssignmentAppComponent
import com.assignment.zalora.twitsplit.util.aws.AWSProvider
import dagger.android.DispatchingAndroidInjector
import dagger.android.HasActivityInjector
import net.danlew.android.joda.JodaTimeAndroid
import timber.log.Timber
import javax.inject.Inject


class AssignmentApp : Application(), HasActivityInjector,Application.ActivityLifecycleCallbacks {

    @Inject
    lateinit var dispatchingAndroidInjector : DispatchingAndroidInjector<Activity>;

    @Inject
    lateinit var awsProvider : AWSProvider
    override fun activityInjector() : DispatchingAndroidInjector<Activity> {
        return dispatchingAndroidInjector;
    }

    override fun onCreate() {
        super.onCreate();
        registerActivityLifecycleCallbacks(this)
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

    override fun onActivityPaused(activity: Activity?) {

    }

    override fun onActivityResumed(activity: Activity?) {

    }

    override fun onActivityStarted(activity: Activity?) {
    }

    override fun onActivityDestroyed(activity: Activity?) {
    }

    override fun onActivitySaveInstanceState(activity: Activity?, outState: Bundle?) {
    }

    override fun onActivityStopped(activity: Activity?) {
    }

    override fun onActivityCreated(activity: Activity?, savedInstanceState: Bundle?) {

    }


}