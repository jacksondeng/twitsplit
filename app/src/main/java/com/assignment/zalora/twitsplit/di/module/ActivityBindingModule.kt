package com.assignment.zalora.twitsplit.di.module

import com.assignment.zalora.twitsplit.view.activity.*
import dagger.android.ContributesAndroidInjector
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

    @ContributesAndroidInjector
    internal abstract fun contributeToTweetDetailsActivity(): TweetDetailsActivity
}
