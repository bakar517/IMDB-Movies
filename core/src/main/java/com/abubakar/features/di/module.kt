package com.abubakar.features.di

import com.abubakar.base.ErrorLogger
import com.abubakar.base.Navigator
import com.abubakar.features.details.UIMapper
import com.abubakar.features.details.UIMapperImpl
import com.abubakar.features.navigation.RealNavigation
import com.abubakar.features.service.ApiService
import com.abubakar.features.util.ErrorLoggerImp
import com.bumptech.glide.annotation.GlideModule
import com.bumptech.glide.module.AppGlideModule
import dagger.Binds
import dagger.Module
import dagger.Provides
import kotlinx.coroutines.CoroutineDispatcher
import retrofit2.Retrofit
import retrofit2.create

@GlideModule
class MyGlideModule : AppGlideModule()


@Module
class InternalModule {

    @Provides
    fun providesIoDispatchers(dispatchers: RealDispatchers): Dispatchers = dispatchers

    @Provides
    fun providesKotlinDispatcher(): CoroutineDispatcher = kotlinx.coroutines.Dispatchers.IO

    @Provides
    fun provideErrorLog(logImp: ErrorLoggerImp): ErrorLogger = logImp

    @Provides
    fun provideUIMapperImp(uiMapperImp: UIMapperImpl): UIMapper = uiMapperImp

}

@Module
class ServiceModule {
    @Provides
    fun apiService(retrofit: Retrofit): ApiService = retrofit.create()
}

@Module
interface RetainedModule {
    @Binds
    fun bindRealNavigator(realNavigation: RealNavigation): Navigator

}