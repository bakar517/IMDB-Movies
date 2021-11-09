package com.abubakar.analytics.firebase

import com.abubakar.base.Event
import com.abubakar.base.EventLogger
import javax.inject.Inject

class FirebaseEventLogger @Inject constructor() : EventLogger {

    // we can define different type of implementations such as Firebase, Microsoft etc
    //right now I am just printing in logs
    override fun log(event: Event) {
        println("${event.name} fired!")
    }
}