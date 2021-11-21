package com.abubakar.features.di

import com.abubakar.base.Event
import com.abubakar.base.EventLogger
import dagger.Module
import dagger.Provides
import javax.inject.Inject

@Module
class FakeAnalyticsModule {

    @Provides
    fun provideEventLoggerImpl(uiTestEventLoggerImpl: UITestEventLoggerImpl): EventLogger =
        uiTestEventLoggerImpl
}

class UITestEventLoggerImpl @Inject constructor() : EventLogger {
    override fun log(event: Event) {
        println("Event $event fired!!")
    }
}