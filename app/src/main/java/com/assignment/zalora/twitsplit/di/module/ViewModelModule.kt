package com.assignment.zalora.twitsplit.di.module

import android.arch.lifecycle.ViewModel
import android.arch.lifecycle.ViewModelProvider
import com.assignment.zalora.twitsplit.viewmodel.TweetVM
import com.assignment.zalora.twitsplit.viewmodel.ViewModelFactory
import com.assignment.zalora.twitsplit.viewmodel.ViewModelKey
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Module
abstract class ViewModelModule {

    @Binds
    internal abstract fun bindViewModelFactory(factory: ViewModelFactory): ViewModelProvider.Factory

    @Binds
    @IntoMap
    @ViewModelKey(TweetVM::class)
    abstract fun tweetVM(tweetVM: TweetVM): ViewModel

    //Add more ViewModels here
}