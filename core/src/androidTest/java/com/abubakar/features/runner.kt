package com.abubakar.features

import android.app.Application
import android.content.Context
import androidx.test.runner.AndroidJUnitRunner
import com.abubakar.base.*
import com.abubakar.base.di.BaseModule
import com.abubakar.base.di.DaggerBaseComponent
import com.abubakar.features.di.AppInjector
import com.abubakar.features.di.DaggerTestComponent
import com.google.firebase.FirebaseApp


class EspressoTestRunner : AndroidJUnitRunner() {
    override fun newApplication(
        cl: ClassLoader?,
        className: String?,
        context: Context?
    ): Application = super.newApplication(cl, TestApp::class.java.name, context)
}

class TestApp : Application() {

    override fun onCreate() {
        super.onCreate()
        FirebaseApp.initializeApp(applicationContext)

        AppInjector.init(
            DaggerTestComponent.builder().baseComponent(
                DaggerBaseComponent.builder().baseModule(
                    BaseModule(applicationContext, getAppConfig())
                ).build()
            ).build()
        )
    }

    private fun getAppConfig() = AppConfig(
        isDebug = BuildConfig.DEBUG,
        keys = Keys(gatewayKey = ""),
        buildInfo = BuildInfo(
            versionCode = 1,
            version = "1.0.0"
        ),
        env = Environment.PROD
    )

}