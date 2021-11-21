package com.abubakar.imdb.movies

import android.app.Application
import com.abubakar.base.AppConfig
import com.abubakar.base.BuildInfo
import com.abubakar.base.Environment
import com.abubakar.base.Keys
import com.abubakar.features.di.AppInjector
import com.google.firebase.FirebaseApp

class App : Application() {

    override fun onCreate() {
        super.onCreate()
        FirebaseApp.initializeApp(applicationContext)

        AppInjector.init(applicationContext, getAppConfig())
    }

    private fun getAppConfig() = AppConfig(
        isDebug = BuildConfig.DEBUG,
        keys = Keys(gatewayKey = BuildConfig.WEB_KEY),
        buildInfo = BuildInfo(
            versionCode = BuildConfig.VERSION_CODE,
            version = BuildConfig.VERSION_NAME
        ),
        env = Environment.PROD
    )
}