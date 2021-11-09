package com.abubakar.base.di

import android.content.Context
import com.abubakar.base.AppConfig
import dagger.Module
import dagger.Provides
import javax.inject.Inject

@Module
class BaseModule @Inject constructor(
    private val context: Context,
    private val config: AppConfig
) {

    @Provides
    fun provideContext() = context

    @Provides
    fun provideConfig() = config

}