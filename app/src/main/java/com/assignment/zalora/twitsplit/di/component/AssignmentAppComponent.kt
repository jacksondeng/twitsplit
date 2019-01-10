package com.assignment.zalora.twitsplit.di.component

import android.app.Application
import javax.inject.Singleton
import com.assignment.zalora.twitsplit.AssignmentApp
import dagger.BindsInstance
import dagger.Component
import dagger.android.AndroidInjectionModule
import dagger.android.support.AndroidSupportInjectionModule




@Singleton
@Component(modules = arrayOf(AndroidSupportInjectionModule::class, AndroidInjectionModule::class))
interface AssignmentAppComponent {
    @Component.Builder
    interface Builder {
        @BindsInstance
        fun application(application: Application): Builder
        fun build(): AssignmentAppComponent
    }
    fun inject(app: AssignmentApp)
}
