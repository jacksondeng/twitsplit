package com.assignment.zalora.twitsplit.di.module

import android.app.Application
import android.content.Context
import android.content.SharedPreferences
import com.assignment.zalora.twitsplit.util.network.NetworkManager
import dagger.Module
import dagger.Provides

@Module
class NetworkModule(){
    @Provides
    fun provideNetworkManager(application: Application): NetworkManager {
        return NetworkManager(application)
    }
}