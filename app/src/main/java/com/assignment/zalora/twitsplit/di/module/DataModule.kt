package com.assignment.zalora.twitsplit.di.module

import android.app.Activity
import android.app.Application
import android.arch.lifecycle.MutableLiveData
import javax.inject.Singleton
import dagger.Provides
import android.content.Context
import android.content.SharedPreferences
import com.assignment.zalora.twitsplit.adapter.TweetAdapter
import com.assignment.zalora.twitsplit.db.DynamoDbUtils
import com.assignment.zalora.twitsplit.model.TweetsDO
import com.assignment.zalora.twitsplit.util.aws.AWSProvider
import com.assignment.zalora.twitsplit.util.network.NetworkManager
import com.assignment.zalora.twitsplit.view.activity.MainActivity
import dagger.Module


@Module
class DataModule {
    @Provides
    fun provideSharedPreferences(application: Application): SharedPreferences {
        return application.getSharedPreferences("settings", Context.MODE_PRIVATE)
    }

    @Provides
    @Singleton
    fun provideAWSProvider(application: Application) : AWSProvider {
        return AWSProvider(application)
    }

    @Provides
    @Singleton
    fun provideDynamoDbUtils(awsProvider: AWSProvider,networkManager: NetworkManager) : DynamoDbUtils{
        return DynamoDbUtils(awsProvider,networkManager)
    }

    @Provides
    fun provideTweetsAdapter() : TweetAdapter{
        return TweetAdapter()
    }
}