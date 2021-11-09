package com.abubakar.base

interface Experiment {

    suspend fun booleanKey(key: String, default: Boolean): Boolean
}