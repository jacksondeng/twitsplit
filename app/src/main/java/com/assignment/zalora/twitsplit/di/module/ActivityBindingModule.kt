package com.assignment.zalora.twitsplit.di.module

import com.assignment.zalora.twitsplit.AuthActivity
import dagger.android.ContributesAndroidInjector
import com.assignment.zalora.twitsplit.MainActivity
import dagger.Module


@Module
abstract class ActivityBindingModule {
    @ContributesAndroidInjector
    internal abstract fun contributeToMainActivity(): MainActivity

    @ContributesAndroidInjector
    internal abstract fun contributeToAuthActivity(): AuthActivity

}
