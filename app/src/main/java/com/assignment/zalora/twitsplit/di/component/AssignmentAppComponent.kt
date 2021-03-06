package com.assignment.zalora.twitsplit.di.component

import android.app.Application
import javax.inject.Singleton
import com.assignment.zalora.twitsplit.app.AssignmentApp
import com.assignment.zalora.twitsplit.di.module.*
import com.assignment.zalora.twitsplit.view.activity.MainActivity
import com.assignment.zalora.twitsplit.viewmodel.ViewModelFactory
import dagger.BindsInstance
import dagger.Component
import dagger.android.AndroidInjectionModule
import dagger.android.support.AndroidSupportInjectionModule




@Singleton
@Component(modules = arrayOf(AndroidSupportInjectionModule::class, AndroidInjectionModule::class,
    DataModule::class, ActivityBindingModule::class, ViewModelModule::class, NetworkModule::class))

interface AssignmentAppComponent {
    @Component.Builder
    interface Builder {
        @BindsInstance
        fun application(application: Application): Builder
        fun build(): AssignmentAppComponent
    }
    fun inject(app: AssignmentApp)
}
