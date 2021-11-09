package com.abubakar.base

interface EventLogger {
    fun log(event: Event)
}

class Event(
    val name: Enum<*>,
    val map: Map<String, String> = emptyMap(),
)