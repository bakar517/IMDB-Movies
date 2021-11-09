package com.abubakar.base.di

import android.content.Context
import com.abubakar.base.AppConfig
import dagger.Component


@Component(modules = [BaseModule::class])
interface BaseComponent {
    fun provideContext(): Context

    fun provideConfig(): AppConfig
}

