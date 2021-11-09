package com.abubakar.networking

import com.abubakar.base.ApiGatewayKey
import com.abubakar.base.AppConfig
import dagger.Module
import dagger.Provides
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.Response
import okhttp3.logging.HttpLoggingInterceptor
import java.util.concurrent.TimeUnit
import javax.inject.Inject

@Module
class NetworkModule {

    @Provides
    fun getOkHttpClient(config: AppConfig, interceptor: RequestInterceptor): OkHttpClient {
        return OkHttpClient.Builder()
            .connectTimeout(15, TimeUnit.SECONDS)
            .readTimeout(20, TimeUnit.SECONDS)
            .addInterceptor(HttpLoggingInterceptor().apply {
                if (config.isDebug) level =
                    HttpLoggingInterceptor.Level.BODY else HttpLoggingInterceptor.Level.NONE
            })
            .addInterceptor(interceptor)
            .build()
    }

}

private const val ApiKey = "api_key"

class RequestInterceptor @Inject constructor(private val gatewayKey: ApiGatewayKey) : Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response {
        val request = chain.request()
        return chain.proceed(
            request.newBuilder()
                .url(
                    request.url.newBuilder()
                        .addQueryParameter(ApiKey, gatewayKey())
                        .build()
                )
                .build()
        )
    }

}
