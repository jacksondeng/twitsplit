package com.assignment.zalora.twitsplit.di.module

import android.app.Application
import javax.inject.Singleton
import dagger.Provides
import android.content.Context
import android.content.SharedPreferences
import com.assignment.zalora.twitsplit.db.DynamoDbUtils
import com.assignment.zalora.twitsplit.util.AWSProvider
import dagger.Module


@Module
class DataModule {
    @Provides
    fun provideSharedPreferences(application: Application): SharedPreferences {
        return application.getSharedPreferences("settings", Context.MODE_PRIVATE)
    }

    @Provides
    @Singleton
    fun provideAWSProvider() : AWSProvider{
        return AWSProvider()
    }

    @Provides
    @Singleton
    fun provideDynamoDbUtils(awsProvider: AWSProvider) : DynamoDbUtils{
        return DynamoDbUtils(awsProvider)
    }
}