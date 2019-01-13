package com.assignment.zalora.twitsplit.di.module

import com.assignment.zalora.twitsplit.view.activity.AuthActivity
import com.assignment.zalora.twitsplit.view.activity.BaseActivity
import dagger.android.ContributesAndroidInjector
import com.assignment.zalora.twitsplit.view.activity.MainActivity
import com.assignment.zalora.twitsplit.view.activity.SplashActivity
import dagger.Module


@Module
abstract class ActivityBindingModule {

    @ContributesAndroidInjector
    internal abstract fun contributeToSplashActivity(): SplashActivity

    @ContributesAndroidInjector
    internal abstract fun contributeToBaseActivity(): BaseActivity

    @ContributesAndroidInjector
    internal abstract fun contributeToMainActivity(): MainActivity

    @ContributesAndroidInjector
    internal abstract fun contributeToAuthActivity(): AuthActivity

}
