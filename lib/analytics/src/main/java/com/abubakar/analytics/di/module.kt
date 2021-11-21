package com.abubakar.analytics.di

import com.abubakar.analytics.firebase.EventLoggerImpl
import com.google.firebase.analytics.FirebaseAnalytics
import com.abubakar.base.EventLogger
import com.google.firebase.analytics.ktx.analytics
import com.google.firebase.ktx.Firebase
import dagger.Module
import dagger.Provides

@Module
class AnalyticsModule {

    @Provides
    fun provideFirebaseAnalytics(): FirebaseAnalytics = Firebase.analytics

    @Provides
    fun provideEventLoggerImpl(firebaseEventLogger: EventLoggerImpl): EventLogger = firebaseEventLogger

}