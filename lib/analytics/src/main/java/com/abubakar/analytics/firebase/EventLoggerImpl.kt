package com.abubakar.analytics.firebase

import com.abubakar.analytics.toBundle
import com.abubakar.base.Event
import com.abubakar.base.EventLogger
import com.google.firebase.analytics.FirebaseAnalytics
import javax.inject.Inject

class EventLoggerImpl @Inject constructor(
    private val firebaseAnalytic: FirebaseAnalytics,
) : EventLogger {

    override fun log(event: Event) {
        firebaseAnalytic.logEvent(event.name.toString(), event.map.toBundle())
    }
}

