package com.abubakar.features.di

import android.content.Context
import com.abubakar.base.BaseUrl
import com.abubakar.features.models.ImageUrlJsonAdapter
import com.serjltt.moshi.adapters.Wrapped
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import dagger.Module
import dagger.Provides
import dev.zacsweers.moshix.adapters.JsonString
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory

@Module
class RetrofitModule {

    @Provides
    fun provideMoshi(context: Context): Moshi = Moshi.Builder()
        .add(Wrapped.ADAPTER_FACTORY)
        .add(JsonString.Factory())
        .add(ImageUrlJsonAdapter.Factory(context))
        .build()


    @Provides
    fun provideRetrofit(
        okHttpClient: OkHttpClient,
        moshi: Moshi,
        baseUrl: BaseUrl,
    ): Retrofit =
        Retrofit.Builder()
            .baseUrl(baseUrl())
            .client(okHttpClient)
            .addConverterFactory(MoshiConverterFactory.create(moshi))
            .build()
}
