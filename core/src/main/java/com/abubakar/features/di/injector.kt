package com.abubakar.features.di

import android.content.Context
import androidx.activity.ComponentActivity
import androidx.annotation.VisibleForTesting
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.get
import com.abubakar.base.AppConfig
import com.abubakar.base.di.BaseModule
import com.abubakar.base.di.DaggerBaseComponent

object AppInjector {

    private lateinit var component: CoreComponent

    fun init(context: Context, appConfig: AppConfig) {
        val baseComponent = DaggerBaseComponent
            .builder()
            .baseModule(BaseModule(context, appConfig))
            .build()
        init(
            DaggerCoreComponent
                .factory()
                .create(baseComponent)
        )
    }

    @VisibleForTesting
    internal fun init(component: CoreComponent) {
        AppInjector.component = component
    }

    fun component() = component
}

val ComponentActivity.retainedComponent: RetainedComponent
    get() = ViewModelProvider(this, AppInjector.component().retainedComponentFactory).get()