package com.assignment.zalora.twitsplit

import android.app.Activity
import android.app.Application
import com.assignment.zalora.twitsplit.di.component.DaggerAssignmentAppComponent
import dagger.android.DispatchingAndroidInjector
import dagger.android.HasActivityInjector
import net.danlew.android.joda.JodaTimeAndroid
import timber.log.Timber
import javax.inject.Inject


class AssignmentApp : Application(), HasActivityInjector {

    @Inject
    lateinit var dispatchingAndroidInjector : DispatchingAndroidInjector<Activity>;


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
    }

    fun initDagger() {
        // Init dagger
        DaggerAssignmentAppComponent
            .builder()
            .application(this)
            .build()
            .inject(this);
    }

}