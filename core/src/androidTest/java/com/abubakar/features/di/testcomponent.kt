package com.abubakar.features.di

import com.abubakar.analytics.di.AnalyticsModule
import com.abubakar.base.di.BaseComponent
import com.abubakar.features.service.ApiService
import com.abubakar.networking.NetworkModule
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import dagger.Component
import dagger.Module
import dagger.Provides
import dev.zacsweers.moshix.adapters.JsonString
import javax.inject.Singleton

@Singleton
@Component(
    modules = [
        NetworkModule::class,
        MockServiceModule::class,
        InternalModule::class,
        FakeExperimentModule::class,
        FakeAnalyticsModule::class,
    ], dependencies = [BaseComponent::class]
)

interface TestComponent : CoreComponent

@Module
class MockServiceModule {
    @Provides
    fun apiService(): ApiService = FakeApiService()

    @Provides
    fun provideMoshi(): Moshi = Moshi.Builder()
        .add(KotlinJsonAdapterFactory())
        .add(JsonString.Factory())
        .build()

}

