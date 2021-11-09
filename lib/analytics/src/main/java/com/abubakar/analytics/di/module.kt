package com.abubakar.analytics.di

import com.abubakar.analytics.firebase.FirebaseEventLogger
import com.abubakar.base.EventLogger
import dagger.Binds
import dagger.Module

@Module
interface AnalyticsModule {

    @Binds
    fun provideAnalytics(firebaseEventLogger: FirebaseEventLogger): EventLogger
}